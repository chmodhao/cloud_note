package org.tarena.note.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tarena.note.dao.UserDao;
import org.tarena.note.entity.NoteResult;
import org.tarena.note.entity.User;
import org.tarena.note.exception.NoteException;
import org.tarena.note.service.UserService;
import org.tarena.note.util.NoteUtil;
import org.tarena.note.util.SendEmail;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public NoteResult checkLogin(String name, String password) {
		NoteResult result = new NoteResult();
		try {
			User user = userDao.findByName(name);
			if (user == null) {
				result.setStatus(1);
				result.setMsg("用户名错误");
			} else {
				if (user.getCn_user_password().equals(NoteUtil.md5(password))) {
					result.setStatus(0);
					result.setMsg("用户名和密码正确");
					if(user.getCn_user_active_status()==0){//检查账户激活状态
						//传出userId和token
						String token=NoteUtil.createToken();
						Map<String,Object> data=new HashMap<String, Object>();
						data.put("userToken", token);
						data.put("userId", user.getCn_user_id());
						result.setData(data);
						userDao.updateToken(data);
					}else{
						result.setStatus(2);
						result.setMsg("账号未激活，请前往注册邮箱激活!");
					}
				} else {
					result.setStatus(2);
					result.setMsg("密码错误");
				}
			}
		} catch (Exception e) {
			throw new NoteException("用户名检查出错");
		}
		return result;
	}

	public NoteResult checkLogin(String author) throws UnsupportedEncodingException {
		String base64_msg = author.split(" ")[1];
		byte[] output = Base64.decodeBase64(base64_msg);
		String msg = new String(output, "utf-8");
		return checkLogin(msg.split(":")[0], msg.split(":")[1]);
	}
	public NoteResult registUser(final User user) {
		NoteResult result =new NoteResult();
		if(userDao.findByName(user.getCn_user_name())!=null){
			result.setStatus(1);
			result.setMsg("用户名被占用");
		}else{
			if(userDao.findByEmail(user.getCn_user_email())!=null){
				result.setStatus(1);
				result.setMsg("邮箱被注册");
			}else{
				user.setCn_user_id(NoteUtil.createId());
				user.setCn_user_password(NoteUtil.md5(user.getCn_user_password()));
				user.setCn_user_active_status(1);
				user.setCn_user_validate_code(NoteUtil.encode2hex(user.getCn_user_email()));
				user.setCn_user_create_time(new Timestamp(System.currentTimeMillis()));
				userDao.save(user);
				//TODO throws Exception
//				String s=null;
//				s.length();
				//发送激活邮件
				// /邮件的内容
				final StringBuffer sb = new StringBuffer(
						"点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
				sb.append("<a href = http://192.168.1.60:8088/note/user/active.do?email=");
				sb.append(user.getCn_user_email());
				sb.append("&validateCode=");
				sb.append(user.getCn_user_validate_code());
				sb.append(" >");
				sb.append(user.getCn_user_email());sb.append("&validateCode=");sb.append(user.getCn_user_validate_code());
				sb.append("</a>");
				System.out.println("-------------------------------"+sb.toString());
				result.setStatus(0);
				result.setMsg("注册成功");
				// 发送邮件
				Thread t=new Thread (){//使用后台线程缩短ajax响应时间
					public void run(){
						SendEmail.send(user.getCn_user_email(), sb.toString());
					}
				};
				//TODO--临时调试关闭
				t.start();
			}
		}
		return result;
	}

	public NoteResult registUser(String author) throws UnsupportedEncodingException {
		String base64_msg = author.split(" ")[1];
		byte[] output = Base64.decodeBase64(base64_msg);
		String msg = new String(output, "utf-8");
		User user =new User();
		user.setCn_user_name(msg.split(":")[0]);
		user.setCn_user_password(msg.split(":")[1]);
		user.setCn_user_desc(msg.split(":")[2]);
		user.setCn_user_email(msg.split(":")[3]);
		return registUser(user);
	}

	public NoteResult active(String email, String validateCode) {
		// 数据访问层，通过email获取用户信息
			User user = userDao.findByEmail(email);
			NoteResult result =new NoteResult();
				// 验证用户是否存在
				if (user != null) {
					// 验证用户激活状态
					if (user.getCn_user_active_status()!= 0) {
						// /没激活
						Date currentTime = new Date();// 获取当前时间
						// 验证链接是否过期
						currentTime.before(user.getCn_user_create_time());
						if (currentTime.before(user.getLastActivateTime())) {
							// 验证激活码是否正确
							if (validateCode.equals(user.getCn_user_validate_code())) {
								// 激活成功， //并更新用户的激活状态，为已激活
								user.setCn_user_active_status(0);;// 把状态改为激活
								HashMap<String,Object> data=new HashMap<String,Object>();
								data.put("userId", user.getCn_user_id());
								data.put("userActiveStatus", 0);
								userDao.updateActiveStatus(data);
								result.setStatus(0);
								result.setMsg("已激活");
							} else {
								result.setStatus(1);
								result.setMsg("激活码不正确");
							}
						} else {
							result.setStatus(1);
							result.setMsg("激活码已过期！");
						}
					} else {
						result.setStatus(1);
						result.setMsg("邮箱已激活，请登录！");
					}
				} else {
					result.setStatus(1);
					result.setMsg("该邮箱未注册（邮箱地址不存在）！");
				}
		return result;
	}
	
}
