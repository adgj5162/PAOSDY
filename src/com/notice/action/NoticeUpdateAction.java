package com.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.db.NoticeBoardDAO;
import com.notice.db.NoticeBoardDTO;

public class NoticeUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String pageNum = request.getParameter("pageNum");
		NoticeBoardDTO nbdto = new NoticeBoardDTO();
		nbdto.setNoticeboard_content(request.getParameter("content"));
		int noticeboard_num = Integer.parseInt(request.getParameter("noticeboard_num"));
		nbdto.setNoticeboard_num(noticeboard_num);
		nbdto.setNoticeboard_title(request.getParameter("title"));
		NoticeBoardDAO mbdao = new NoticeBoardDAO();
		mbdao.noticeBoardUpdate(nbdto);
		response.sendRedirect("./NoticeBoardContent.nb?pageNum="+pageNum+"&noticeboard_num="+noticeboard_num);
		return null;
	}

}
