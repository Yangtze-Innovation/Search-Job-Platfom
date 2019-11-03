package com.kaikeba.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kaikeba.beans.Student;

public class TestMain {

	public static void main(String[] args) {
		
		ApplicationContext factory = new ClassPathXmlApplicationContext("spring_config.xml");
		Student stu = (Student)factory.getBean("student");
		System.out.println(stu.getSname()+" "+stu.getAge());
		System.out.println(stu.getTeacher().getTname());

	}

}
