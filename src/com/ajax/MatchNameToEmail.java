package com.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.db.MemberDAO;

/**
 * Servlet implementation class MatchIdtoEmail
 */
@WebServlet("/MatchNameToEmail")
public class MatchNameToEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("member_name");
		String email = request.getParameter("member_email");
		MemberDAO mdao = new MemberDAO();
		String id = mdao.matchNameToEmail(name, email);
		PrintWriter out = response.getWriter();
		System.out.println("name: " + name + "email : " + email);
		out.print(id);
	
	}

}
