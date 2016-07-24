package org.tarena.note.junit;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.UserService;
import org.tarena.note.service.impl.UserServiceImpl;
public class TestUserServiceImpl {
	@Test
	public void test1(){
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService UserSer=ctx.getBean("userServiceImpl",UserServiceImpl.class);
		NoteResult result=UserSer.checkLogin("admin", "admin");
		System.out.println(result.getStatus()+" "+result.getMsg());
		ctx.close();
	}
}
