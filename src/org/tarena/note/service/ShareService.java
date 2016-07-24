package org.tarena.note.service;

import org.tarena.note.entity.NoteResult;

public interface ShareService {
	NoteResult ShareNoteById(String noteId);
	NoteResult search(String title);
}
