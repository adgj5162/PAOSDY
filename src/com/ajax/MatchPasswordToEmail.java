package com.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.db.MemberDAO;

import net.email.SendEmail;

/**
 * Servlet implementation class MemberPasswordFind
 */
@WebServlet("/MatchPasswordToEmail")
public class MatchPasswordToEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String member_email = request.getParameter("member_email");
		String member_id = request.getParameter("member_id");
		MemberDAO mdao = new MemberDAO();
		int result = mdao.matchIdtoEmail(member_id, member_email);
		PrintWriter out = response.getWriter();
		out.print(result);
	
	}

}
