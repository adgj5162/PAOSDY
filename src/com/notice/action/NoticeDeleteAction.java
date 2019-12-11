package com.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.db.NoticeBoardDAO;

public class NoticeDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		int noticeboard_num = Integer.parseInt(request.getParameter("noticeboard_num"));
		NoticeBoardDAO mbdao = new NoticeBoardDAO();
		mbdao.deleteNotice(noticeboard_num);
		response.sendRedirect("./NoticeList.nb?pageNum="+pageNum);
		
		return null;
	}

}
