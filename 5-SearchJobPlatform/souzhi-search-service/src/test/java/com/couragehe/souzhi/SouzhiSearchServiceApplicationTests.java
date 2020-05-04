package com.couragehe.souzhi;

//import org.junit.jupiter.api.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.spring.annotation.MapperScan;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.couragehe.souzhi.search.mapper")
public class SouzhiSearchServiceApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("123");
    }

}
