package admin.member.action;

public class ActionForward {
	// 컨트롤러에서 페이지를 이동할때 사용하는 이동정보를 저장하는 객체
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
