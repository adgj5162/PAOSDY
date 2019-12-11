package com.ask.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;
import com.ask.db.AskDTO;
import com.ask.db.CommentDTO;

public class AskCommentWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskAnswerWriteAction_execute()**********");

		//한글처리
		request.setCharacterEncoding("UTF-8");
		
		//관리자 세션값 체크(생략)
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")){
			forward.setPath("./Main.me");
			forward.setRedirect(true);
			return forward;
		}
		
		
		//객체 생성 후 값 저장
		AskDTO askdto = new AskDTO();
		
		CommentDTO codto = new CommentDTO();
		codto.setComment_num(Integer.parseInt(request.getParameter("ask_num")));
		System.out.println("@@@@@@@@@@@@@@@@@@@"+codto.getComment_num());
		
		
		
		
		String askPageNum = request.getParameter("pageNum");
		
		
		//askdto.setAsk_item_num(Integer.parseInt(request.getParameter("ask_item_num")));
		
		
		codto.setComment_member_id(id); //전달받은 세션ID값(admin)
		codto.setComment_category("ask"); //답변의 종류-후기_review/문의_ask로 구분
		codto.setComment_content(request.getParameter("ask_comment_content"));
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		//답변 저장 메서드 실행
		AskDAO askdao = new AskDAO();
		askdao.InsertAskComment(codto, askdto);
		
		
		//글 작성성공 메세지 띄우고 페이지 이동("./AskList.qa")
		
		
			forward.setPath("./AskList.qa?ask_item_num="+item_num);	
			
			forward.setRedirect(true);
			return forward;
	}

}
