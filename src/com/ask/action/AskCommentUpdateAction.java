package com.ask.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;
import com.ask.db.AskDTO;
import com.ask.db.CommentDTO;

public class AskCommentUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskUpdateAction_execute()**********");

		// 한글처리 
		request.setCharacterEncoding("UTF-8");

		//ID 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		

		// 전달정보를 저장 (pageNum)
		String askPageNum = request.getParameter("pageNum");
		

		//객체 생성 후 값 저장
		CommentDTO codto = new CommentDTO();
		codto.setComment_num(Integer.parseInt(request.getParameter("ask_comment_num")));
		codto.setComment_content(request.getParameter("ask_comment_content"));

		
		AskDAO askdao = new AskDAO();
		askdao.updateComment(codto);

		
		//메세지 출력후 창 닫고 새로고침
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println(" alert('답글 수정 완료 ');");
		out.println("	window.close(); ");
		
		
		//팝업창 닫히면서 부모창 새로고침
		out.println("	opener.parent.location.reload(); "); 
		out.println("</script>");

		out.close();
		return null;
	}

}
