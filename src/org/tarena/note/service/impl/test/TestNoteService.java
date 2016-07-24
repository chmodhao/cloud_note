package org.tarena.note.service.impl.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.entity.Note;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.NoteService;



@SuppressWarnings("deprecation")
public class TestNoteService {
	ClassPathXmlApplicationContext ctx=null;
	NoteService noteService=null;
	@Before
	public void inti(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		noteService=ctx.getBean("noteServiceImpl",NoteService.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void test1(){
		NoteResult result=noteService.loadNotesByBookId("6d763ac9-dca3-42d7-a2a7-a08053095c08");
		Assert.assertEquals(0,result.getStatus());
	}
	@Test
	public void test2(){
		Note note=new Note();
		note.setCn_notebook_id("0037215c-09fe-4eaa-aeb5-25a340c6b39b");
		note.setCn_note_title("java2");
		note.setCn_user_id("52f9b276-38ee-447f-a3aa-0d54e7a736e4");
		NoteResult result=noteService.addNoteByNoteBookId(note);
		System.out.println(result.getMsg()+" "+result.getData());
		Assert.assertEquals(0, result.getStatus());
	}
}
