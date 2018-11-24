var total_pages, navigatePage;

//1.页面加载完成后，直接发送一个ajax请求，取到分页数据
$(document).ready(function () {
    //去首页
    to_page(1);

    /**
     * 点击新增按钮弹出模态框
     */
    $("#emp_add_modal_btn").click(function () {

        //清除表单数据(表单重置)
        $("#empAddModal form")[0].reset();
        //发送ajax请求, 获取部门信息,显示在下拉列表中
        getDepts();

        //弹出模态框
        $("#empAddModal").modal({
            backdrop: "static"
        });
    });

    /**
     * 校验表单数据
     */
    function validate_add_form(empNameInput, emailInput){
        //1.拿到要校验的表单数据,使用正则表达式
        //校验用户名
        if(empNameInput != null){
            var empName = $(empNameInput).val();
            var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
            if(!regName.test(empName)){
                // alert("用户名为2-5为中文或6-16位英文和数字的组合");
                //显示校验信息
                showValidateMsg(empNameInput, "error", "用户名为2-5为中文或者6-16位英文和数字的组合");
                return false;
            }else {
                showValidateMsg(empNameInput, "success", "");
            }
        }


        //校验邮箱
        if(emailInput != null){
            var email = $(emailInput).val();
            var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if(!regEmail.test(email)){
                // alert("邮箱格式不正确");
                //显示校验信息
                showValidateMsg(emailInput, "error", "邮箱格式不正确");
                return false;
            }else{
                //显示校验信息
                showValidateMsg(emailInput, "success", "");
            }
        }
        return true;
    }

    /***
     * 显示校验信息
     */
    function showValidateMsg(ele, status, msg){
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");
        if(status == "success"){
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        }else if(status == "error"){
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }


    /**
     * 校验用户名是否可用
     */
    $("#empName_add_input").change(function () {

        var empName = this.value;
        //发送ajax校验用户名是否可用
        $.ajax({
            url: '/emp/check_emp_name',
            data: 'empName='+empName,
            type: 'post',
            success: function (res) {
                if(res == true){
                    showValidateMsg("#empName_add_input", "success", "用户名可用");
                    $("#emp_save_btn").attr("ajax_va","success");
                }else {
                    showValidateMsg("#empName_add_input", "error", "用户名不可用");                    $("#emp_save_btn").attr("ajax_va","success");
                    $("#emp_save_btn").attr("ajax_va","error");
                }
            },
            error: function (err) {
                console.log(err);
            }
        })
    });


    /**
     * 保存新增员工信息
     */
    $("#emp_save_btn").click(function () {

        //0.先对要提交给服务器的数据进行校验
        if(!validate_add_form("#empName_add_input","#email_add_input")){
            return ;
        }
        //1.模态框中填写的表单数据提交给服务器进行保存

        //判断之前的用户名校验是否成功
        if ($(this).attr("ajax_va") == "error"){
            return ;
        }
        //2.发送ajax请求保存员工数据
        $.ajax({
            type: 'post',
            url: '/emp/add_emp_info',
            data: $("#empAddModal form").serialize(),
            success: function (res) {
                // console.log(res);
                // alert("处理成功")

                //员工保存成功
                //1.关闭模态框
                $("#empAddModal").modal('hide');
                //2.页面跳转到最后一页,显示刚才保存的数据
                to_page(total_pages+1);
            },
            error: function (err) {
                console.log(err);
            }
        })
    });


    /**
     * 查出所有的部门信息并显示在下拉列表中
     */
    function getDepts() {
        $.ajax({
            url: '/emp/get_dept_names',
            type: 'get',
            success: function (res) {
                // console.log(res);
                $(".dept_select").empty();
                //显示部门信息在下拉列表中
                $.each(res, function (index, item) {
                    var optionEle = $("<option></option>").append(item.deptName).attr("value", item.deptId);
                    $(".dept_select").append(optionEle);
                })

            },
            error: function (err) {
                console.log(err);
            }
        })
    }

    /***
     * 跳转页面
     */
    function to_page(pn) {
        $.ajax({
            type: 'get',
            url: '/emp/list',
            data: 'pn='+pn,
            success: function (result) {
                //console.log(result);
                //1.解析并显示员工数据
                build_emps_table(result);
                //2.解析并显示分页信息
                build_page_info(result);
                //3.解析显示分页条数据
                build_page_nav(result);
            },
            error: function (err) {
                console.log(err);
            }
        });
        navigatePage = pn;
        $("#check_all").prop("checked", false);
    }

    /***
     * 解析并显示员工数据
     * @param result
     */
    function build_emps_table(result) {

        //清空table表格数据
        $("#emp_table tbody").empty();
        var emps = result.list;
        //console.log(emps);
        $.each(emps, function (index, item) {
            //复选框
            var checkBox = $("<td><input type='checkbox' class='check_item'></td>");
            //拿取表数据数据
            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var genderTd = $("<td></td>").append(item.gender=="M"?"男":"女");
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            //按钮
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit-btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil"));
            var deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete-btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash"));
            var btnTd = $("<td></td>").append(editBtn).append("&nbsp;&nbsp").append(deleteBtn);
            $("<tr></tr>").append(checkBox)
                .append(empIdTd)
                .append(empNameTd)
                .append(genderTd)
                .append(emailTd)
                .append(deptNameTd)
                .append(btnTd)
                .appendTo("#emp_table tbody");
        })
    }

    /**
     * 解析并显示分页信息
     */
    function build_page_info(result) {

        //清空分页信息原有数据
        $("#page_info_area").empty();

        var pageNumLabel = $("<label></label>").addClass("label label-default").append(result.pageNum);
        var totalPagesLabel = $("<label></label>").addClass("label label-default").append(result.pages);
        var totalsLabel = $("<label></label>").addClass("label label-default").append(result.total);

        total_pages = result.total;

        $("#page_info_area").append("当前 ").append(pageNumLabel)
            .append(" 页, 总共 ").append(totalPagesLabel)
            .append(" 页, 总共 ").append(totalsLabel)
            .append(" 条记录");
    }
    /***
     * 解析并显示分页条数据
     * @param result
     */
    function build_page_nav(result) {

        //page_nav_area

        //清空分页条原有数据
        $("#page_nav_area").empty();

        var ul = $("<ul></ul>").addClass("pagination");

        //构建元素
        //首页
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页"));
        //前一页
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        if(result.hasPreviousPage == false){
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        }else {
            //为元素添加点击翻页的事件
            firstPageLi.click(function () {
                to_page(1);
            });
            prePageLi.click(function () {
                to_page(result.pageNum - 1);
            });
        }

        ul.append(firstPageLi).append(prePageLi);
        $.each(result.navigatepageNums, function (index, item) {
            var numLi = $("<li></li>").append($("<a></a>").append(item));
            if(result.pageNum == item){
                numLi.addClass("active");
            }
            numLi.click(function () {
                to_page(item);
            })
            ul.append(numLi);
        })

        //构建元素
        //后一页
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        //末页
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页"));
        if(result.hasNextPage == false){
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        }else {
            //为元素添加点击翻页的事件
            nextPageLi.click(function () {
                to_page(result.pageNum + 1);
            });
            lastPageLi.click(function () {
                to_page(result.pages);
            });

        }

        ul.append(nextPageLi).append(lastPageLi);

        //把ul加入到nav元素中
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_area");
    }


    /***
     * 给修改按钮绑定事件
     */
    $(document).on("click", ".edit-btn", function () {
        //alert("123");
        //1.查出员工信息
        //getEmp($());
        //console.log($(this)[0].parentNode.parentNode.firstChild.textContent);
        //console.log(456);
        //员工id
        //var empId = $(this)[0].parentNode.parentNode.firstChild.textContent;
        var empId = $(this).parents("tr").find("td:eq(1)").text();
        getEmp(empId);
        //给模态框添加一个属性，方便更新是获取员工id
        $("#empUpdateModal").attr("empId",empId);
        //2.查出部门信息
        getDepts();
        //3.显示模态框
        $("#empUpdateModal").modal({
            backdrop: "static"
        });
    });

    /***
     * 根据id获取员工信息并显示在模态框中
     * @param id
     */
    function getEmp(id) {
        $.ajax({
            url: '/emp/'+id,
            type: 'get',
            success: function (res) {
                //console.log(res);
                //显示员工信息到模态框中
                $("#empName_udpate_static").text(res.empName);
                $("#email_update_input").val(res.email);
                $("#empUpdateModal input[name='gender']").val([res.gender]);
                $("#empUpdateModal select").val(res.dId);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    /****
     * 点击更新按钮
     */
    $("#emp_update_btn").click(function () {

        //alert("更新");
        //1.校验表单数据
        //0.先对要提交给服务器的数据进行校验
        if(!validate_add_form(null,"#email_update_input")){
            return ;
        }
        //判断之前的用户名校验是否成功
        if ($(this).attr("ajax_va") == "error"){
            return ;
        }

        //2.获取到要更新的员工的id
        var empId = $("#empUpdateModal").attr("empId");
        //alert(empId);
        //console.log($("#empUpdateModal form").serialize());
        //3.发送ajax请求
        $.ajax({
            url: '/emp/'+empId,
            type: 'PUT',
            data: $("#empUpdateModal form").serialize(),
            success: function (res) {
                //console.log(res);
                if(res){
                    //1.关闭模态框
                    $("#empUpdateModal").modal('hide');
                    //2.刷新之前的页面
                    to_page(navigatePage);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    /***
     * 给列表的每一行记录的删除按钮添加点击事件
     */
    $(document).on("click", ".delete-btn", function () {
        //alert("删除");
        //0.弹出是否确认删除对话框
        var empName = $(this).parents("tr").find("td:eq(2)").text();
        if(confirm("确认删除【"+ empName +"】 吗？")){
            //1.获取到要删除的员工的id
            //console.log($(this).parents("tr").find("td:eq(0)").text());
            var empId = $(this).parents("tr").find("td:eq(1)").text();
            //2.发送ajax请求
            $.ajax({
                url: '/emp/' + empId,
                type: "DELETE",
                success: function (res) {
                    if(res){
                        //2.刷新之前的页面
                        to_page(navigatePage);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }

    });

    /**
     * 点击 全选复选框
     */
    $("#check_all").click(function () {
        //attr用于处理自定义属性
        //prop用于处理DOM固有属性
        //console.log($(this).prop("checked"));
        $(".check_item").prop("checked", $(this).prop("checked"));
    });

    /**
     * 选择了所有的复选框时，全选复选框也要勾选,反之亦然
     */
    $(document).on("click", ".check_item", function () {
        //alert($(".check_item:checked").length);
        if($(".check_item:checked").length == $(".check_item").length){
            $("#check_all").prop("checked", true);
        }else {
            $("#check_all").prop("checked", false);
        }
    });


    /***
     * 批量删除
     */
    $("#emp_delete_all").click(function () {
        //1.获取到要删除的员工名字，弹出是否删除确认框
        //console.log($(".check_item:checked"));
        var empNames = ""; //存储要删除的员工信息的名字
        var empIds = [];// 存储要删除的员工信息的id
        if($(".check_item:checked").length > 0){
            $(".check_item:checked").each(function (index,item) {
                //console.log($(this).parents("tr").find("td:eq(2)").text());
                empNames += $(this).parents("tr").find("td:eq(2)").text() + ",";
                var id = parseInt($(this).parents("tr").find("td:eq(1)").text());
                empIds.push(id);
            });
            empNames =  empNames.substring(0, empNames.length-1);
            if(confirm("确认删除【" + empNames + "】嘛")){
                //2.获取到要删除的员工id
                //console.log(empIds.toString());
                //3.发送ajax请求
                $.ajax({
                    url: '/emp/massdel?ids='+ empIds.toString(),
                    type: 'DELETE',
                    success: function (res) {
                        if(res){
                            to_page(navigatePage);
                        }
                    },
                    error: function (err) {
                        console.log(err);
                    }
                });
            }
        }else {
            alert("没有选择要删除的员工！");
        }

        //console.log(empNames);
        //console.log(empIds);

    });

});

