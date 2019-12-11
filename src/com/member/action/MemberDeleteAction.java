package com.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.db.MemberDAO;


public class MemberDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		
		HttpSession session = request.getSession();
		String member_id = (String) session.getAttribute("member_id");
		
		ActionForward forward = new ActionForward();
		if(member_id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			
			return forward;
		}
		// id,pass정보 저장
		Shautills shautills = new Shautills();
		
		String member_password = shautills.encryptSha(request.getParameter("member_password"));	
		
		// DB 처리 객체 MemberDAO
		MemberDAO mdao = new MemberDAO();
		// deleteMember(id,pass)
		int check = mdao.deleteMember(member_id, member_password);	
		
		// check 0,-1 / 비밀번호 에러,아이디가없음
		response.setContentType("text/html; charset=UTF-8");
		
		if(check == 0){
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println(" alert('비밀번호가 틀립니다.'); ");
			out.println(" history.back(); ");
			out.println("</script>");
			
			out.close();
			
			return null;
			
		}else if(check == -1){
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println(" alert('ID가 잘못되었습니다.'); ");
			out.println(" history.back(); ");
			out.println("</script>");
			
			out.close();
			
			return null;
		}
		
		// 세션값 초기화(탈퇴된 회원의 세션id정보를 삭제)
		session.invalidate();
		//session.removeAttribute(arg0);

		// check 1 / 탈퇴성공
		// 자바스크립트를 사용하여 메시지 출력후 페이지 이동
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println(" alert('회원 탈퇴 처리 되었습니다.'); ");
		out.println(" location.href='./Main.me' ");
		out.println("</script>");
		
		out.close();	
		
		return null;
	}
	
	

}
