package com.couragehe.souzhi;

import com.couragehe.souzhi.search.SouzhiSearchWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SouzhiSearchWebApplication.class)
public class SouzhiSearchWebApplicationTests {

    @Test
    public void contextLoads(){
        System.out.println("fad");
    }

}
