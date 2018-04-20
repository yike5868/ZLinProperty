package com.zlin.property.control;


import com.alibaba.fastjson.JSON;

public class FuResponse {

	private Boolean success = true;
	private Boolean hasSuccess = false;
	private String errMessage = "";
	private Object data;
	String code;
	public FuResponse() {
	}
	public FuResponse(String message, String code) {
		this.errMessage = message;
		this.code = code;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public Boolean getHasSuccess() {
		return hasSuccess;
	}

	public void setHasSuccess(Boolean hasSuccess) {
		this.hasSuccess = hasSuccess;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public Object getData() {
		return data;
	}

	public static <T> T getData(String text, Class<T> clazz) {
		return JSON.parseObject(text,clazz);
	}

	public void setData(Object data) {
		this.data = data;
	}
}
