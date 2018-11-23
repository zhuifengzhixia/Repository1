package com.example.ssmcrud.mapper;

import com.example.ssmcrud.dto.Demo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoMapper {

    /**
     *
     * @return
     */
    List<Demo> querybyid();
}
