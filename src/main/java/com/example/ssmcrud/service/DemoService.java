package com.example.ssmcrud.service;


import com.example.ssmcrud.dto.Demo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DemoService {

        List<Demo> querybyid();
}
