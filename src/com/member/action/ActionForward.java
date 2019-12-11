package com.member.action;

public class ActionForward {
	// �씠�룞�젙蹂�?�� ���옣媛앹�? 
	
	// �럹�씠吏� �씠�룞二쇱?��
	private String path;
	// �럹�씠吏� �씠�룞諛⑹?��
	private boolean isRedirect;
	// true - sendRedirect 諛⑹?���쑝濡� �씠�룞
	// false - forward 諛⑹?���쑝濡� �씠�룞 
	
	
	
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
