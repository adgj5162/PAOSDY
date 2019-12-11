package com.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.db.MemberDAO;
import com.member.db.MemberDTO;

public class PasswordUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		
		// 전달된 정보(form태그)저장 => MemberDTO

		// 비밀번호 암호화
		
		MemberDTO mdto = new MemberDTO();
		
		mdto.setMember_id(request.getParameter("member_id"));
		mdto.setMemeber_password(request.getParameter("member_password"));
		mdto.setNew_member_password(request.getParameter("new_member_password"));
		
		
		MemberDAO mdao = new MemberDAO();
		
		// updateMember(mdto)
		int check = mdao.passUpdate(mdto); 
		
		System.out.println(check);
		// check = -1, 0, 1

		// 0,-1 비밀번호오류 / 아이디가 없을경우
		// 경고메시지 출력후, 뒤로가기 이동 (자바스크립트)
		response.setContentType("text/html; charset=UTF-8");
		
		if(check==-1){
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("  alert('ID가 잘못되었습니다.');  ");
			out.println("  history.back(); ");
			out.println("</script>");	
			
			out.close();
			
			return null;
			
		}else if(check == 0){
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("  alert('비밀번호가 잘못되었습니다.');  ");
			out.println("  history.back(); ");
			out.println("</script>");	
			
			out.close();
			
			return null;
		} 

		// 1 수정성공 메시지 출력
		// 페이지 이동 메인페이지로 이동 (자바스크립트)
		//check == 1
		
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("  alert('비밀번호를 변경하였습니다.');  ");
		out.println(" location.href='./MemberInfo.me' ");
		out.println("</script>");	
		
		out.close();	

		return null;
	}
	
	

}
