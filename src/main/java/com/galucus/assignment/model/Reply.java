package com.galucus.assignment.model;

public class Reply {
	private String authToken;
	private Object data;
	private String msg;
	private boolean isError;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setInfoMsg(String msg) {
		this.msg = msg;
	}
	
	public void setErrorMsg(String msg) {
		this.msg = msg;
		this.isError = true;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String token) {
		this.authToken= token;
	}
}
