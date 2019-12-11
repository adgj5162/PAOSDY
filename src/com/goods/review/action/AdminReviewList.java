package com.goods.review.action;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.member.MemberDTO;
import com.goods.review.db.ReviewDAO;
import com.goods.review.db.ReviewDTO;

public class AdminReviewList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("AdminReviewList_execute()-----------------");
		
//		반드시 나중에 주석 해제
		
//		HttpSession session = request.getSession();
//		String id = (String)session.getAttribute("id");
//		ActionForward forward = new ActionForward();
//		if(member_id == null; || !member_id.equals("admin")){
//			forward.setPath("./MemberLogin.me");
//			forward.setRedirect(true);
//			return forward;
//		}
		
		// DB 처리객체 생성
		ReviewDAO rdao = new ReviewDAO();
		
		
		// 글 개수 확인 메서드 생성
		// getReviewCount();
		int count = rdao.getReviewCount(0);
		
		// 페이징 처리
		/***********************************/
		// 한 페이지에서 보여줄 글의 개수 설정
		int pageSize = 5;

		// 현재 페이지가 몇페이지 인지를 가져오기
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1"; // pageNum의 값이 없을경우 무조건 1페이지
		}

		// 시작행 구하는 작업
		int currentPage = Integer.parseInt(pageNum); // 1 이라는 정수형 타입 저장
		int startRow = (currentPage - 1) * pageSize + 1;
		// => 1 11 21 31 ...
		// 끝행 구하는 작업
		int endRow = currentPage * pageSize;
		// => 10 20 30 40...

		/*******************************************/

		// DB에서 글 가져오기
		// getReviewList();
		Vector vec = null;
		if (count != 0) {
			vec = rdao.getReviewList(startRow,pageSize);
		}
		
		int pageCount = count/pageSize+(count%pageSize==0? 0:1);	// 전체글/페이지개수
		
		// 한화면에 보여줄 페이지 계산
		int pageBlock = 5;
		
		// 시작페이지
		// 1~10 => 1	11~20 => 11		21~30 => 21
		
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
		
		// 끝페이지
		int endPage = startPage+pageBlock-1;
		if(endPage > pageCount){
			endPage = pageCount;
		}
		
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("reviewList", vec.get(0));
		request.setAttribute("item_name", vec.get(1));
		
		ActionForward forward = new ActionForward();
		forward.setPath("./review/review_admin_list.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
