package com.delivery.action;

public class ActionForward {
	// 이동정보를 저장객체 
	
	// 페이지 이동주소
	private String path;
	// 페이지 이동방식
	private boolean isRedirect;
	// true - sendRedirect 방식으로 이동
	// false - forward 방식으로 이동 
	
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}
