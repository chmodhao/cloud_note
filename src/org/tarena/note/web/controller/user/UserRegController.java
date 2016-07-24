package org.tarena.note.web.controller.user;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.entity.User;
import org.tarena.note.service.UserService;

@Controller
@RequestMapping("/user")
public class UserRegController {
	@Autowired
	private UserService userService;
	/**
	 * 注册，加密传输
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/regist.do")
	@ResponseBody
	public NoteResult excute(HttpServletRequest request) throws UnsupportedEncodingException{
		return userService.registUser(request.getHeader("Authorization"));
	}
	
	/**
	 * 注册，未加密传输
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/regist1.do")
	@ResponseBody
	public NoteResult excute(User user) throws UnsupportedEncodingException{
		return userService.registUser(user);
	}
}
