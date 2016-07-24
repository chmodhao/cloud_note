package org.tarena.note.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tarena.note.dao.NoteDao;
import org.tarena.note.dao.ShareDao;
import org.tarena.note.entity.Note;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.NoteService;
import org.tarena.note.util.NoteUtil;
@Service
@Transactional
public class NoteServiceImpl implements NoteService {
	@Autowired
	private NoteDao noteDao;
	@Autowired
	private ShareDao shareDao;
	public NoteResult loadNotesByBookId(String notebookId) {
		NoteResult result=new NoteResult();
		if(notebookId!=null&&!notebookId.equals("")){
			Note note=new Note();
			note.setCn_notebook_id(notebookId);
			note.setCn_note_status_id("1");
			List<Note> list=noteDao.find(note);
			if(list.size()>0){
				result.setStatus(0);
				result.setMsg("获取笔记完成");
				result.setData(list);
			}else{
				result.setStatus(1);
				result.setMsg("当前笔记本为空");
				result.setData(list);
			}
		}else{
			result.setStatus(2);
			result.setMsg("笔记本ID为空");
			result.setData(null);
		}
		return result;
	}
	public NoteResult addNoteByNoteBookId(Note note) {
		NoteResult result=new NoteResult();
		if(note.getCn_notebook_id()!=null&&note.getCn_notebook_id()!=""){//格式检查
			if(noteDao.find(note).size()>0){//检查笔记是否重复
				result.setStatus(1);
				result.setMsg("笔记已存在");
				return result;
			}
			String noteId=NoteUtil.createId();
			note.setCn_note_id(noteId);
			//cn_notebook_id//传入
			//cn_user_id//传入
			note.setCn_note_status_id("1");
			note.setCn_note_type_id("2");
			//cn_note_title//传入
			//cn_note_body//传入
			note.setCn_note_body("");
			note.setCn_note_create_time(System.currentTimeMillis());
			note.setCn_note_last_modify_time(System.currentTimeMillis());
			int count =noteDao.addNoteByNoteBookId(note);
			if(count>0){
				result.setStatus(0);
				result.setMsg("添加成功");
				result.setData(noteId);
			}else{
				result.setStatus(2);
				result.setMsg("添加失败");
			}
		}
		return result;
	}
	public NoteResult LoadNoteBody(String noteId) {
		NoteResult result=new NoteResult();
		if(noteId!=null&&!noteId.equals("")){
			Note temp=new Note();
			temp.setCn_note_id(noteId);
			Note note=noteDao.find(temp).get(0);
			result.setStatus(0);
			result.setMsg("以获取到");
			result.setData(note.getCn_note_body());
		}else{
			result.setStatus(1);
			result.setMsg("未获取到");
			result.setData(null);
		}
		return result;
	}
	public NoteResult saveNoteByNoteId(Note note) {
		NoteResult result=new NoteResult();
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		int count =noteDao.update(note);
		if(count >0){
			result.setStatus(0);
			result.setMsg("保存成功");
		}else{
			result.setStatus(1);
			result.setMsg("保存失败");
		}
		return result;
	}
	public NoteResult abandonNoteByNoteId(String noteId){
		NoteResult result=new NoteResult();
		Note note=new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_status_id("2");
		shareDao.deleteByNoteId(noteId);
		int count=noteDao.update(note);
		if(count>0){
			result.setStatus(0);
			result.setMsg("已删除至回收站");
		}else{
			result.setStatus(1);
			result.setMsg("删除至回收站失败");
		}
		return result;
	}
	/**
	 * 检查笔记本中是否有笔记未删除
	 */
	public NoteResult queryCountByBookId(String bookId) {
		NoteResult result=new NoteResult();
		int count=noteDao.queryCountByBookId(bookId);
		result.setData(count);
		if(count>0){
			result.setStatus(1);
			result.setMsg("有尚未删除的笔记");
		}else{
			result.setStatus(0);
			result.setMsg("该笔记本下笔记全部删除");
		}
		return result;
	}
}
