package com.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.db.MemberDAO;




@WebServlet("/PasswordFind")
public class PasswordFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		request.getParameter("member_email");
		MemberDAO mdao = new MemberDAO();
		String member_id = request.getParameter("member_id");
		String member_password = request.getParameter("member_password");
		mdao.findMemberPassword(member_id, member_password);
		out.print("<script>");
		out.print("window.close()");
		out.print("</script>");
	
	}

}
