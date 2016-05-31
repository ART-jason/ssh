package com.tgb.main;

import static org.junit.Assert.*;

import com.tgb.bean.A;
import com.tgb.bean.B;

public class Test {

	@org.junit.Test
	public void test() {
		BeanFactory bf=new ClassPathXmlApplicationContext("/applicationContext.xml");
		A a = (A) bf.getBean("A");
		System.out.println(a.getName());
		
		
		B b=(B) bf.getBean("B");
		System.out.println(b.getA().getName());
	}

}
