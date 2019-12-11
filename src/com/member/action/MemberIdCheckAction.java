package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.db.MemberDAO;

public class MemberIdCheckAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String member_id = request.getParameter("member_id");
		MemberDAO mdao = new MemberDAO();
		int check = mdao.idCheck(member_id);
		System.out.println(check);
		request.setAttribute("check", check);
		return null;
	}
	
}
