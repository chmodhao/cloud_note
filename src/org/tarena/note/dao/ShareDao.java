package org.tarena.note.dao;

import java.util.List;

import org.tarena.note.entity.Share;

public interface ShareDao {
	int save(Share share);
	Share findByNoteId(String noteId);
	List<Share> findLikeTitle(String title);
	int deleteByNoteId(String noteId);
}
