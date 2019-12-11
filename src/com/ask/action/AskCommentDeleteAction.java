package com.ask.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.el.parser.AstAnd;

import com.ask.db.AskDAO;
import com.ask.db.AskDTO;

public class AskCommentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskDeleteAction_execute()**********");
		
		//ID 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("");
			forward.setRedirect(true);
			return forward;
		}
		
		
		//삭제를 위해 답글번호 받아옴
		int CommentNum = Integer.parseInt(request.getParameter("ask_comment_num"));

		
		//AskDAO, AskDTO 객체 생성후 답변삭제 메서드 실행
		AskDAO askdao = new AskDAO();
		AskDTO askdto = new AskDTO();
		askdao.deleteComment(CommentNum);
			
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		
		//결과 출력 후 페이지 이동
		
			forward.setPath("./AskList.qa?ask_item_num="+item_num);	
			
			forward.setRedirect(true);
			return forward;

	}

}
