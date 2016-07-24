package org.tarena.note.aop.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.aop.MyService;

public class TestMyService {
	private MyService myservice;
	@Before
	public void init(){
		String conf="org/tarena/note/aop/spring-aop-annotation.xml";
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext(conf);
		myservice=(MyService) ctx.getBean("myservice");
		ctx.close();
	}
	@Test
	public void test1(){
		System.out.println(myservice.getClass().getName());
		myservice.f1();
	}
	@Test
	public void test2(){
		myservice.f2();
	}
}
