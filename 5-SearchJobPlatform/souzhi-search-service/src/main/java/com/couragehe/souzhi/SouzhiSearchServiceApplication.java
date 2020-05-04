package com.couragehe.souzhi;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.couragehe.souzhi.search.mapper")
public class SouzhiSearchServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(SouzhiSearchServiceApplication.class, args);
    }



}
