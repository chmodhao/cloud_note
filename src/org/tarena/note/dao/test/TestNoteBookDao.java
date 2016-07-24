package org.tarena.note.dao.test;

import java.sql.Timestamp;
import java.util.List;




import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.dao.NoteBookDao;
import org.tarena.note.entity.NoteBook;
import org.tarena.note.util.NoteUtil;



@SuppressWarnings("deprecation")
public class TestNoteBookDao {
	NoteBookDao notebookdao=null;
	ClassPathXmlApplicationContext ctx=null;
	
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		notebookdao=ctx.getBean("noteBookDao",NoteBookDao.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void test1(){
		List<NoteBook> list=notebookdao.findByUserId("39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
		for(NoteBook book:list){
			System.out.println(book.getCn_notebook_name());
		}
	}
	@Test
	public void test2(){
		NoteBook notebook=new NoteBook();
		notebook.setCn_notebook_id(NoteUtil.createId());
		notebook.setCn_notebook_name("我的测试");
		notebook.setCn_notebook_desc("我的测试");
		notebook.setCn_notebook_type_id("5");
		notebook.setCn_user_id("52f9b276-38ee-447f-a3aa-0d54e7a736e4");
		notebook.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		int count =notebookdao.addNoteBookByUserId(notebook);
		Assert.assertEquals(1, count);
	}
}
