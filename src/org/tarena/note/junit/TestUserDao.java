package org.tarena.note.junit;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.dao.UserDao;
import org.tarena.note.entity.User;

public class TestUserDao {
	@Test
	public void test1(){
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao dao=ctx.getBean("userDao",UserDao.class);
		User user=dao.findByName("admin");
		System.out.println(user);
		ctx.close();
	}
}
