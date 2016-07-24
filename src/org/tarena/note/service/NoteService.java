package org.tarena.note.service;

import org.tarena.note.entity.Note;
import org.tarena.note.entity.NoteResult;

public interface NoteService {
	NoteResult loadNotesByBookId(String notebookId);
	NoteResult addNoteByNoteBookId(Note note);
	NoteResult saveNoteByNoteId(Note note);
	NoteResult LoadNoteBody(String noteId);
	NoteResult abandonNoteByNoteId(String noteId);
	NoteResult queryCountByBookId(String bookId);
}
