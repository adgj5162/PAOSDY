package com.item.action;

public class ActionForward {
	// 컨트롤러?��?�� ?��?���?�? ?��?��?��?�� ?��?��?��?�� ?��?��?��보�?? ???��?��?�� 객체
	String path;
	boolean isRedirect;
	// true - sendRedirect
	// false - forward
	
	
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
