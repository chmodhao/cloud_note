package org.tarena.note.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
 * @author kehao
 * User实体类
 */
public class User implements Serializable {
	private static final long serialVersionUID = 3665142843330825607L;
	private String cn_user_id;
	private String cn_user_name;
	private String cn_user_password;
	private String cn_user_token;
	private String cn_user_desc;
	private String cn_user_email;
	private Integer cn_user_active_status;
	private String cn_user_validate_code;
	private Timestamp cn_user_create_time;
	
	/**获取用户id
	 * @return
	 */
	public String getCn_user_id() {
		return cn_user_id;
	}
	/**设置用户id
	 * @param cn_user_id
	 */
	public void setCn_user_id(String cn_user_id) {
		this.cn_user_id = cn_user_id;
	}
	/**
	 * 返回用户名
	 * @return
	 */
	public String getCn_user_name() {
		return cn_user_name;
	}
	/**设置用户名
	 * @param cn_user_name
	 */
	public void setCn_user_name(String cn_user_name) {
		this.cn_user_name = cn_user_name;
	}
	/**获取密码
	 * @return
	 */
	public String getCn_user_password() {
		return cn_user_password;
	}
	/**
	 * 设置密码
	 * @param cn_user_password
	 */
	public void setCn_user_password(String cn_user_password) {
		this.cn_user_password = cn_user_password;
	}
	/**
	 * 获取令牌
	 * @return
	 */
	public String getCn_user_token() {
		return cn_user_token;
	}
	/**
	 * 设置令牌
	 * @param cn_user_token
	 */
	public void setCn_user_token(String cn_user_token) {
		this.cn_user_token = cn_user_token;
	}
	/**
	 * 获取用户昵称
	 * @return
	 */
	public String getCn_user_desc() {
		return cn_user_desc;
	}
	/**
	 * 设置用户昵称
	 * @param cn_user_desc
	 */
	public void setCn_user_desc(String cn_user_desc) {
		this.cn_user_desc = cn_user_desc;
	}
	/**
	 * 获取用户邮箱
	 * @return
	 */
	public String getCn_user_email() {
		return cn_user_email;
	}
	/**
	 * 设置用户邮箱
	 * @param cn_user_email
	 */
	public void setCn_user_email(String cn_user_email) {
		this.cn_user_email = cn_user_email;
	}
	/**
	 * 获取用户激活状态
	 * @return
	 */
	public Integer getCn_user_active_status() {
		return cn_user_active_status;
	}
	/**
	 * 设置用户激活状态
	 * @param cn_user_active_status
	 */
	public void setCn_user_active_status(Integer cn_user_active_status) {
		this.cn_user_active_status = cn_user_active_status;
	}
	public String getCn_user_validate_code() {
		return cn_user_validate_code;
	}
	public void setCn_user_validate_code(String cn_user_validate_code) {
		this.cn_user_validate_code = cn_user_validate_code;
	}
	public Timestamp getCn_user_create_time() {
		return cn_user_create_time;
	}
	public void setCn_user_create_time(Timestamp cn_user_create_time) {
		this.cn_user_create_time = cn_user_create_time;
	}
	
	@Transient
    public Date getLastActivateTime() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(cn_user_create_time);
        cl.add(Calendar.DATE , 2);
        
        return cl.getTime();
    }
}
