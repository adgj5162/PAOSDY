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
 * Servlet implementation class MemberIdCheck
 */
@WebServlet("/MemberIdCheck")
public class MemberIdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String member_id = request.getParameter("member_id");
		System.out.println("member id " + member_id);
		MemberDAO mdao = new MemberDAO();
		int idcheck = mdao.idCheck(member_id);
		PrintWriter script = response.getWriter();
		script.print(idcheck);
	}

}
