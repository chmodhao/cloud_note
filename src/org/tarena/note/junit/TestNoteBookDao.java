package org.tarena.note.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.dao.NoteBookDao;
import org.tarena.note.entity.Note;
import org.tarena.note.entity.NoteBook;

/**
 * 测试动态SQL
 * @author KeHao
 *
 */
public class TestNoteBookDao {
	private NoteBookDao dao;
	private ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		dao=ctx.getBean("noteBookDao",NoteBookDao.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void find3(){
		NoteBook book=dao.find3("bd6b2217ade9491d9faafaa0a6f7ae80");
		System.out.println(book.getCn_notebook_name());
		System.out.println("---------下属笔记-------");
		for(Note note:book.getNotes()){
			System.out.println(note.getCn_note_title());
		}
	}
	@Test
	public void find4(){
		NoteBook book=dao.find4("bd6b2217ade9491d9faafaa0a6f7ae80");
		System.out.println(book.getCn_notebook_name());
		System.out.println("---------下属笔记-------");
		for(Note note:book.getNotes()){
			System.out.println(note.getCn_note_title());
		}
	}
}
