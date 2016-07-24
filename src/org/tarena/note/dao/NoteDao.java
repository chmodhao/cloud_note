package org.tarena.note.dao;

import java.util.List;

import org.tarena.note.entity.Note;

public interface NoteDao {
//	List<Note> findByNotebookId(String notebookId);//select
//	int findByBookIdAndUserIdAndNoteTitle(Note note);//用于检查笔记是否重名//select
//	Note findByNoteId(String noteId);//返回笔记内容//select
	
//	int saveNote(Note note);//保存笔记内容//update
//	int abandonNoteByNoteId(Note note);//临时删除笔记至回收站//update
	
	int addNoteByNoteBookId(Note note);//添加笔记
	int queryCountByBookId(String bookId);//查询笔记本中是否还有未删除笔记
	int deleteByBookId(String BookId);
	
	//动态SQL
	List<Note> find(Note note);
	int update(Note note);
	int delete(String[] array);
	List<Note> find1();
	List<Note> find2();
}
