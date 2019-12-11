package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.db.MemberDAO;


public class AdminMemberDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		
		ActionForward forward = new ActionForward();

		// 삭제 회원 아이디 정보 객체 저장
		String deletemember = request.getParameter("delete_member");
		
		System.out.println(deletemember);
		
		MemberDAO mdao = new MemberDAO();
		
		mdao.admindeletemember(deletemember);	
		
		
		
		
		forward.setPath("./AdminMemberList.me");
		forward.setRedirect(true);

		return forward;
	
	}

	
	
}
