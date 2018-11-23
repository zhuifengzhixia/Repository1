package com.example.ssmcrud.service;


import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface TestService {

    void Test();

    void TestPageInfo(PageInfo pageInfo);
}
