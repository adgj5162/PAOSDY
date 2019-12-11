package com.ask.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;
import com.ask.db.CommentDTO;

public class AskCommentUpdate implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskUpdate_execute()**********");
		
		//ID 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		//리스트에서 답글번호, 페이지 번호 전달
		int CommentNum = Integer.parseInt(request.getParameter("ask_num"));
		String pageNum = request.getParameter("ask_pageNum");
		
		
		// AskDAO 객체 
		// 답변글번호에 해당하는 정보를 가져오는 메서드 사용
		AskDAO askdao = new AskDAO();
		CommentDTO codto = askdao.getComment(CommentNum);
		
		
		// 정보 저장 (글 정보,pageNum)
		request.setAttribute("codto", codto);
		request.setAttribute("pageNum", pageNum);		
		
		
		// 페이지 이동(./ask/ask_comment_update.jsp)	
		forward.setPath("./ask/ask_comment_update.jsp");
		forward.setRedirect(false);		
		return forward;
	}

}
