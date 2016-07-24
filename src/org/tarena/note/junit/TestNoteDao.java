package org.tarena.note.junit;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tarena.note.dao.NoteDao;
import org.tarena.note.entity.Note;

/**
 * 测试动态SQL
 * @author KeHao
 *
 */
@SuppressWarnings("deprecation")
public class TestNoteDao {
	private NoteDao dao;
	private ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		dao=ctx.getBean("noteDao",NoteDao.class);
	}
	@After
	public void destroy(){
		ctx.close();
	}
	@Test
	public void find(){
		Note params=new Note();
		params.setCn_note_title("java");
		params.setCn_note_status_id("1");
		List<Note> note=dao.find(params);
		for(Note n:note){
			System.out.println(n.getCn_note_title()+":"+n.getCn_note_status_id());
		}
	}
	@Test
	public void update(){
		Note note =new Note();
		note.setCn_note_id("1b26b57ca14a411788ad224fb4dedd60");
		note.setCn_note_body("动态SQL测试");
		int count=dao.update(note);
		Assert.assertEquals(1, count);
	}
	@Test
	public void delete(){
		String[] array=new String[]{"0d98ece68bcb435d81c71df452a338c0","c49c26d983624c05978dc233377fb613"};
		int count=dao.delete(array);
		Assert.assertEquals(array.length, count);
	}
	@Test
	public void finf1(){
		List<Note> list=dao.find1();
		System.out.println(list.size());
		for(Note note:list){
			System.out.println(note.getCn_note_title()+":"+note.getBook().getCn_notebook_name());
		}
	}
	@Test
	public void finf2(){
		List<Note> list=dao.find2();
		for(Note note:list){
			System.out.println(note.getCn_note_title()+":"+note.getBook().getCn_notebook_name());
		}
	}
}
