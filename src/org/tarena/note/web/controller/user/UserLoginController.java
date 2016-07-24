package org.tarena.note.web.controller.user;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.UserService;
/**
 * @author kehao
 * 用户登录模块
 */
@Controller
@RequestMapping("/user")
public class UserLoginController {
	@Autowired
	private UserService userService;
	/**
	 * 登陆检查
	 * @param name
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public NoteResult excute(HttpServletRequest request) throws UnsupportedEncodingException{
		return userService.checkLogin(request.getHeader("Authorization"));
	}
}
