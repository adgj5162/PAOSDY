package com.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.db.MemberDAO;


/**
 * Servlet implementation class NaverEmailCheck
 */
@WebServlet("/NaverEmailCheck")
public class NaverEmailCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		MemberDAO mdao = new MemberDAO();
		int check = mdao.isJoinedNaverEmail(request.getParameter("member_email"));
		if(check == 1){
			HttpSession session = request.getSession();
			session.setAttribute("member_id", mdao.naverEmailLogin(request.getParameter("member_email")));
		}
		out.print(check);
	}

}
