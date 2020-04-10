package com.couragehe.souzhi.crawler.task;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
@EnableScheduling
public class TaskTest {
	
//		@Scheduled(cron = "0/5 * * * * *")
		public void test() {
			System.out.println("定时任务执行");
		}
}
