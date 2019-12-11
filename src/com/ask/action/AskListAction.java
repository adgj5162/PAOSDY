package com.ask.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;
import com.ask.db.AskDTO;
import com.ask.db.CommentDTO;
import com.member.db.MemberDAO;
import com.member.db.MemberDTO;

public class AskListAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskListAction_execute()**********");
		
		//ID 세션 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		
		//객체 생성
		AskDAO askdao = new AskDAO();
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		
		
		
		int askItemNum = Integer.parseInt(request.getParameter("ask_item_num"));
		
		System.out.println("item_num : "+askItemNum);
		//문의글 개수 확인 메서드
		int askCount = askdao.getAskCount(askItemNum);
		//해당 상세페이지 아이템 번호
		
		
		//페이징처리
		int askPageSize = 5;
		// 현 페이지가 몇페이지 인지를 가져오기
		String askPageNum = request.getParameter("ask_pageNum");
		
		
		if (askPageNum == null || askPageNum.equals("null")) {
			askPageNum = "1"; // pageNum의 값이 없을경우 무조건 1페이지
		}
		
		// 시작행 구하는 작업
		int askCurrentPage = Integer.parseInt(askPageNum);
		int askStartRow = (askCurrentPage - 1) * askPageSize + 1;
		// => 1 11 21 31 ....
		
		// 끝행 구하는 작업
		int askEndRow = askCurrentPage * askPageSize;
		// => 10 20 30 40....
		
		//문의글 리스트 저장
		List<AskDTO> askList = null;
		List coList = new ArrayList();
		if (askCount != 0) {
			askList = askdao.getAskList(askStartRow, askPageSize, askItemNum);
			
			for(int i=0; i<askList.size(); i++){
				AskDTO askdto = new AskDTO();
				askdto = askList.get(i);
				
				CommentDTO codto = askdao.getComment(askdto.getAsk_num());
				coList.add(codto);
			}
		}
		
		
		// 페이지 출력
		int askPageCount = askCount / askPageSize + (askCount % askPageSize == 0 ? 0 : 1);

		// 한화면에 보여줄 페이지 번호 계산
		int askPageBlock = 5;
		// 시작페이지 1~10 =>1 11~20 => 11 21~30 => 21
		int askStartPage = ((askCurrentPage - 1) / askPageBlock) * askPageBlock + 1;
		// 끝페이지
		int askEndPage = askStartPage + askPageBlock - 1;
		if (askEndPage > askPageCount) {
			askEndPage = askPageCount;
		}
		
		

		
		
		
		
		// 정보 저장 (글 , 페이징처리 값)
		// 글 개수, 게시판 정보(DB),페이지
		// 정보(pageNum,pageCount,pageBlock,startPage,endPage)

		request.setAttribute("askCount", askCount);
		request.setAttribute("askList", askList);
		request.setAttribute("askPageNum", askPageNum);
		request.setAttribute("askPageCount", askPageCount);
		request.setAttribute("askPageBlock", askPageBlock);
		request.setAttribute("askStartPage", askStartPage);
		request.setAttribute("askEndPage", askEndPage);
		request.setAttribute("askItemNum", askItemNum);
		
		request.setAttribute("coList", coList);
		request.setAttribute("mdto", mdto);
		
		
		//페이지 이동("./ask/ask_list.jsp")
		forward.setPath("./ask/ask_list.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
