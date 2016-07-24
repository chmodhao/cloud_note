package org.tarena.note.service.impl.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.UserService;

@SuppressWarnings("deprecation")
public class TestLoginService {
	ClassPathXmlApplicationContext ctx=null;
	UserService userService=null;
	@Before
	public void inti(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		userService=ctx.getBean("userServiceImpl",UserService.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void test1(){
		NoteResult result=userService.checkLogin("admin", "admin");
		Assert.assertEquals(0,result.getStatus());
	}
	@Test
	public void test2(){
		NoteResult result=userService.checkLogin("admin1", "admin");
		Assert.assertEquals(1,result.getStatus());
	}
	@Test
	public void test3(){
		NoteResult result=userService.checkLogin("admin", "admin1");
		Assert.assertEquals(2,result.getStatus());
	}
}
