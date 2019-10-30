
package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;






@SpringBootApplication
@EnableScheduling
//加入此说明  不然会报错xxx的type of  xxx  没有被找到
@ComponentScan(value = {"com.task","com.service.impl","com.dao"})

public class Application {
		public static void main(String[] args) {
			  
			SpringApplication.run(Application.class, args);
			
				}
}

