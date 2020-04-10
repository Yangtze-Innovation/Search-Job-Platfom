package com.couragehe.souzhi;

import com.couragehe.souzhi.crawler.task.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.couragehe.souzhi.crawler.mapper")
public class SouzhiCrawlerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SouzhiCrawlerServiceApplication.class, args);
    }

}
