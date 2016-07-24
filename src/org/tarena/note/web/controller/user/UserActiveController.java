package org.tarena.note.web.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;






import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.service.UserService;

/**
 * @author kehao
 * 用户注册激活模块
 */
@Controller
@RequestMapping("/user")
public class UserActiveController {
	@Autowired
	private UserService userService;
	/**
	 * 登陆检查
	 * @param name
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/active.do")
	@ResponseBody
	public NoteResult excute(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		String email=request.getParameter("email");
		String validateCode=request.getParameter("validateCode");
		NoteResult result= userService.active(email, validateCode);
		try {
			response.sendRedirect("../active_success.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
