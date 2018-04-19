package com.zlin.property.control;


import com.zlin.property.net.NetWorkManager;

public class FuResponse {

	private String code;
	private String message;
	private String result;


	public FuResponse() {
	}

	public FuResponse(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return NetWorkManager.kNetWork_Code_Success.equals(code);
	}

}
