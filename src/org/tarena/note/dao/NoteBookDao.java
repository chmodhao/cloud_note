package org.tarena.note.dao;

import java.util.List;

import org.tarena.note.entity.NoteBook;

public interface NoteBookDao {
	/**
	 * 根据用户ID查询用户笔记本
	 * @param id
	 * @return
	 */
	List<NoteBook> findByUserId(String id);
	/**
	 * 添加笔记本
	 * @param notebook
	 * @return
	 */
	int addNoteBookByUserId(NoteBook notebook);
	NoteBook findByNameAndUserId(NoteBook notebook);
	int deleteByBookId(String bookId);
	int modfifyByBookId(NoteBook notebook);
	
	NoteBook find3(String bookid);
	NoteBook find4(String bookid);
}
