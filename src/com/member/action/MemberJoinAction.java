package com.member.action;


import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.db.MemberDAO;
import com.member.db.MemberDTO;

public class MemberJoinAction implements Action{

	// Kim pro 2019-10-01
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		// ?���? 처리
		request.setCharacterEncoding("UTF-8");
		
		// 비�?번호 ?��?��?��
//		Shautills shautills = new Shautills();
		
//		String member_password = shautills.encryptSha(request.getParameter("member_password"));
		
//		System.out.println("?��?��?��?�� 비�?번호 "+ member_password);

		
		MemberDTO mdto = new MemberDTO();
		
		mdto.setMember_id(request.getParameter("member_id"));
		mdto.setMemeber_password(request.getParameter("member_password"));
		mdto.setMember_name(request.getParameter("member_name"));
		mdto.setMember_birth(request.getParameter("member_birth"));
		mdto.setMember_gender(request.getParameter("member_gender"));
		mdto.setMember_email(request.getParameter("member_email"));
		mdto.setMember_phone(request.getParameter("member_phone"));	
		mdto.setMember_postcode(request.getParameter("member_postcode"));
		mdto.setMember_addr1(request.getParameter("member_addr1"));
		mdto.setMember_addr2(request.getParameter("member_addr2"));
		
		
		MemberDAO mdao = new MemberDAO();
		
		mdao.insertMember(mdto);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setPath("./MemberLogin.me");
		forward.setRedirect(true);
		
		return forward;


	}

	// Kim pro 2019-10-01
}
