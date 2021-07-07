package com.example.activiti7boot.common.msgreturn;

import com.alibaba.fastjson.annotation.JSONField;

public class ReportHeart  implements java.io.Serializable{	
	private static final long serialVersionUID = 1L;
	
	@JSONField(name = "Success")
	private Boolean success;
	@JSONField(name = "Code")
	private String code;
	@JSONField(name = "Message")
	private String message;
	
	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	public ReportHeart() {}
	public ReportHeart(Boolean success, String code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}
	
	
	

}
