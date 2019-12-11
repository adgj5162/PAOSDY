package com.notice.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.db.NoticeBoardDAO;
import com.notice.db.NoticeBoardDTO;


public class NoticeListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		NoticeBoardDAO nbdao = new NoticeBoardDAO();
		
		int count = nbdao.getNoticeCount();
		int pageSize = 10;
		System.out.println("count = " + count);
		String pageNum = request.getParameter("pageNum");
		
		if (pageNum == null) {
			pageNum = "1"; 
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage -1) * pageSize;
		int endRow = currentPage * pageSize;
		List<NoticeBoardDTO> noticeList = null;
	
		if(count != 0){
			noticeList = nbdao.getNoticeList(startRow, pageSize);
		} else {
			noticeList = null;
		}
		int pageCount = count/pageSize+(count%pageSize==0? 0:1);
		
		int pageBlock =5;
		
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
		
		int endPage = startPage + pageBlock-1;
		if(endPage > pageCount){
			endPage = pageCount;
		}
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("noticeList", noticeList);
		
		ActionForward forward = new ActionForward();
		System.out.println("goTo noticelist");
		forward.setPath("./notice_board/notice_list.jsp");
		forward.setRedirect(false);
		return forward;
	}
	
}
