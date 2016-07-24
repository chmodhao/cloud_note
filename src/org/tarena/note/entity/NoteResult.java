package org.tarena.note.entity;

import java.io.Serializable;

/**
 * @author kehao
 * 
 */
public class NoteResult implements Serializable {
	private static final long serialVersionUID = 4369093828381283109L;
	private int status;//状态 0 正常
	private String msg;//消息
	private Object data;//数据
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
