package com.member.action;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLogoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("MemberLogoutAction----------------------");
		
		// 세션초기화 
		HttpSession session = request.getSession();
		session.invalidate();
		
		
		// "로그아웃 성공" 출력 (자바스크립트)
		// Main.me 페이지로 이동
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("  alert('로그아웃이 되었습니다.');");
		out.println("  location.href='./Main.me'; ");
		out.println("</script>");
						
		// 위에서 자바스크립트로 이동했기 때문에  컨트롤러를 통한 
		// 이중 이동을 방지하기위해 null값을 리턴
		return null;
	}
	
	
}
