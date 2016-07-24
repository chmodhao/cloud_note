package org.tarena.note.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tarena.note.dao.NoteBookDao;
import org.tarena.note.dao.NoteDao;
import org.tarena.note.entity.NoteBook;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.BookService;
import org.tarena.note.util.NoteUtil;
@Service
@Transactional
public class BookServiceImpl implements BookService {
	@Autowired
	private NoteBookDao bookDao;
	@Autowired
	private NoteDao noteDao;
	public NoteResult loadBooksByUserId(String userId) {
		NoteResult result=new NoteResult();
		if(userId!=null&&!userId.equals("")){
			List<NoteBook> list=bookDao.findByUserId(userId);
			result.setData(list);
		}
		result.setStatus(0);
		result.setMsg("加载笔记本成功");
		return result;
	}
	public NoteResult addNoteBookByUserId(NoteBook notebook) {
		NoteResult result=new NoteResult();
		if(notebook.getCn_user_id()!=null&&!notebook.getCn_user_id().equals("")){
			if(bookDao.findByNameAndUserId(notebook)!=null){
				result.setStatus(1);
				result.setMsg("添加失败，笔记本名称已存在");
				return result;
			}
			String bookId=NoteUtil.createId();
			notebook.setCn_notebook_id(bookId);
//			cn_notebook_name
//			cn_notebook_desc
//			cn_notebook_type_id
//			cn_user_id
			notebook.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
			int count =bookDao.addNoteBookByUserId(notebook);
			if(count>0){
				//result=loadBooksByUserId(notebook.getCn_user_id());
				result.setMsg("添加成功");
				result.setData(bookId);
			}
		}else{
			result.setStatus(1);
			result.setMsg("添加失败");
		}
		return result;
	}
	/**
	 * 删除笔记本
	 */
	public NoteResult deleteByBookId(String BookId) {
		NoteResult result=new NoteResult();
		//判断笔记本是否为空的操作在前端完成
		noteDao.deleteByBookId(BookId);
		int count = bookDao.deleteByBookId(BookId);
		if(count>0){
			result.setStatus(0);
			result.setMsg("删除笔记本成功");
		}else{
			result.setStatus(1);
			result.setMsg("删除笔记本失败");
		}
		return result;
	}
	public NoteResult modfifyByBookId(NoteBook notebook){
		NoteResult result=new NoteResult();
		NoteBook book=bookDao.findByNameAndUserId(notebook);
		if(book!=null){
			result.setStatus(1);
			result.setMsg("该名称已存在");
			return result;
		}
		int count =bookDao.modfifyByBookId(notebook);
		if(count>0){
			result.setStatus(0);
			result.setMsg("修改成功");
		}else{
			result.setStatus(1);
			result.setMsg("修改失败");
		}
		return result;
	}
}
