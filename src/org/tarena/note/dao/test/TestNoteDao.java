package org.tarena.note.dao.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.dao.NoteDao;
import org.tarena.note.entity.Note;
import org.tarena.note.util.NoteUtil;



public class TestNoteDao {
	NoteDao notedao=null;
	ClassPathXmlApplicationContext ctx=null;
	
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		notedao=ctx.getBean("noteDao",NoteDao.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void test1(){
		Note note=new Note();
		note.setCn_note_id("6d763ac9-dca3-42d7-a2a7-a08053095c08");
		List<Note> list=notedao.find(note);
		for(Note n:list){
			System.out.println(n.getCn_note_title());
		}
	}
	@Test
	public void test2(){
		Note note=new Note();
		note.setCn_note_id(NoteUtil.createId());
		note.setCn_notebook_id("0037215c-09fe-4eaa-aeb5-25a340c6b39b");
		note.setCn_note_title("java");
		note.setCn_note_create_time(System.currentTimeMillis());
		note.setCn_user_id("52f9b276-38ee-447f-a3aa-0d54e7a736e4");
		int count=notedao.addNoteByNoteBookId(note);
		System.out.println(count);
	}
	@Test
	public void test3(){
		Note note=new Note();
		note.setCn_note_id("ef18f483b17b4cfa8763043ef7d7edd1");
		System.out.println(notedao.find(note).get(0).getCn_note_body());
	}
	@Test
	public void test4(){
		Note note=new Note();
		note.setCn_note_id("ef18f483b17b4cfa8763043ef7d7edd1");
		note.setCn_note_body("这是保存笔记第二次测试");
		int count=notedao.update(note);
		System.out.println(count);
	}
}
