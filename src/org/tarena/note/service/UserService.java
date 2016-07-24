package org.tarena.note.service;

import java.io.UnsupportedEncodingException;

import org.tarena.note.entity.NoteResult;
import org.tarena.note.entity.User;

public interface UserService {
	public NoteResult checkLogin(String name, String password);

	public NoteResult checkLogin(String auther)
			throws UnsupportedEncodingException;

	public NoteResult registUser(User user);

	public NoteResult registUser(String author)
			throws UnsupportedEncodingException;
	public NoteResult active(String email,String validateCode);
}
