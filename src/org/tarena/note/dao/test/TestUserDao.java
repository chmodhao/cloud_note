package org.tarena.note.dao.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.dao.UserDao;
import org.tarena.note.entity.User;

public class TestUserDao {
	UserDao userdao=null;
	ClassPathXmlApplicationContext ctx=null;
	
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		userdao=ctx.getBean("userDao",UserDao.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void test1(){
		User user=userdao.findByName("admin");
		Assert.assertNotNull(user);
	}
	@Test
	public void test2(){
		User user=userdao.findByName("dddd");
		Assert.assertNull(user);
	}
}
