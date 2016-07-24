package org.tarena.note.service.impl.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.entity.NoteBook;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.BookService;



@SuppressWarnings("deprecation")
public class TestNoteBookService {
	ClassPathXmlApplicationContext ctx=null;
	BookService bookService=null;
	@Before
	public void inti(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		bookService=ctx.getBean("bookServiceImpl",BookService.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void test1(){
		NoteResult result=bookService.loadBooksByUserId("39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
		Assert.assertEquals(0,result.getStatus());
	}
	@Test
	public void test2(){
		NoteBook notebook=new NoteBook();
		notebook.setCn_notebook_name("我的测试4");
		notebook.setCn_notebook_desc("我的测试4");
		notebook.setCn_user_id("52f9b276-38ee-447f-a3aa-0d54e7a736e4");
		notebook.setCn_notebook_type_id("5");
		NoteResult result=bookService.addNoteBookByUserId(notebook);
		Assert.assertEquals(0,result.getStatus());
	}
}
