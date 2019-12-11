package com.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.db.MemberDAO;
import com.member.db.MemberDTO;


		// Kim pro 2019-10-01

public class MemberLoginAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		// Parameter �? ???��
		MemberDTO mdto = new MemberDTO();
		
		mdto.setMember_id(request.getParameter("member_id"));
		mdto.setMemeber_password(request.getParameter("member_password"));
	
		// DB처리객체 ?��?��
		
		MemberDAO mdao = new MemberDAO();
		
		int check = mdao.idCheck(mdto);
		
		
		System.out.println("로그?�� ?���? 체크 : "+check);
		
		// idCheck(id,pass)메서?�� ?��?��
		// ?��?��?���? 존재?��경우(?��?��) - 1
		// 비�?번호�? ?���? - 0
		// ?��?��?���? ?��?��경우 (비회?��) - -1
		
		// 각각?�� 경우?�� ?���? 처리 
		// 0, -1  경고메세�? 출력/?��?���? ?��로�?�? 
		
		if(check == 0){
			
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			
			out.println("<script> ");
			out.println("  alert('비밀번호 오류!!'); ");
			out.println("  history.back(); ");
			out.println("</script>");
			
			out.close();
			
			// actionForward�? ?��?��?�� ?��?���? ?��?�� X
			return null;			
		}else if(check == -1){
			
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			
			out.println("<script> ");
			out.println("  alert('없는아이디입니다!!'); ");
			out.println("  history.back(); ");
			out.println("</script>");
			
			out.close();
			
			// actionForward�? ?��?��?�� ?��?���? ?��?�� X
			return null;			
		}
		
		// check == 1
		// 로그?�� -> id값을 ?��?��객체?�� ???��
		
		HttpSession session = request.getSession();
		session.setAttribute("member_id", mdto.getMember_id());
		
		System.out.println("?��?�� id�? : "+ mdto.getMember_id());
		
		// ?��?���? ?��?�� (./Main.me)
		ActionForward forward = new ActionForward();
		
		forward.setPath("./Main.me");
		forward.setRedirect(true);	
		
		return forward;
	}
		
		
		// Kim pro 2019-10-01

	

}
