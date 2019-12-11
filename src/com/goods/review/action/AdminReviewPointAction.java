package com.goods.review.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.member.MemberDAO;
import com.goods.member.MemberDTO;

public class AdminReviewPointAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AdminReviewPointAction_execute()-----------------------------");

		String review_member_id = (String)request.getParameter("review_member_id");
//		System.out.println(review_member_id);
		
		MemberDTO mdto = new MemberDTO();
		mdto.setMember_point(Integer.parseInt(request.getParameter("member_point")));
		MemberDAO mdao = new MemberDAO();
		mdao.updatePoint(review_member_id,mdto);
		
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println(" alert('포인트 지급 완료!'); ");
		out.println(" location.href='./AdminReviewList.re' ");
		out.println("</script>");
		
		out.close();
		
		return null;
	}
}
