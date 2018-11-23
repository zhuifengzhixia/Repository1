package com.example.ssmcrud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.ssmcrud.mapper")
public class SsmcrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsmcrudApplication.class, args);
    }

}
