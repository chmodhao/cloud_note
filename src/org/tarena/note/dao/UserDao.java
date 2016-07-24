package org.tarena.note.dao;


import java.util.Map;

import org.tarena.note.entity.User;

public interface UserDao {
	User findByName(String name);
	int save(User user);
	int updateToken(Map<String, Object> data);
	User findByEmail(String email);
	int updateActiveStatus(Map<String,Object> data);
}
