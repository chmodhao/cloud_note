package org.tarena.note.service;

import org.tarena.note.entity.NoteBook;
import org.tarena.note.entity.NoteResult;

public interface BookService {
	NoteResult loadBooksByUserId(String userId);
	NoteResult addNoteBookByUserId(NoteBook notebook);
	NoteResult deleteByBookId(String BookId);
	NoteResult modfifyByBookId(NoteBook notebook);
}
