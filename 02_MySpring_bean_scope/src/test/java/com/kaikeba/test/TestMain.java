package com.kaikeba.test;

import java.util.ArrayList;
import java.util.List;

import com.kaikeba.beans.Teacher;
import com.kaikeba.util.BeanDefined;
import com.kaikeba.util.BeanFactory;

public class TestMain {

	public static void main(String[] args) throws Exception {
		
		  //1.声明注册bean
		  BeanDefined beanObj = new BeanDefined();
		  beanObj.setBeanId("teacher");
		  beanObj.setClassPath("com.kaikeba.beans.Teacher");
		  //beanObj.setScope("prototype");
		  
		  List beanList = new ArrayList();
		  beanList.add(beanObj);//spring核心配置
		  
		  //2.声明一个Spring提供BeanFacotory
		  BeanFactory factory = new BeanFactory(beanList);
		 
		  
		  //3.开发人员向BeanFactory索要实例对象.
		  Teacher t= (Teacher) factory.getBean("teacher");
		  System.out.println("t="+t);
		  Teacher t2= (Teacher) factory.getBean("teacher");
		  System.out.println("t2="+t2);

	}

}
