package com.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.db.NoticeBoardDAO;
import com.notice.db.NoticeBoardDTO;

public class NoticeBoardUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int noticeboard_num = Integer.parseInt(request.getParameter("noticeboard_num"));
		String pageNum = request.getParameter("pageNum");
		NoticeBoardDAO nbdao = new NoticeBoardDAO();
		NoticeBoardDTO nbdto = nbdao.getNoticeContent(noticeboard_num);
		request.setAttribute("nbdto", nbdto);
		request.setAttribute("pageNum", pageNum);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./notice_board/notice_board_update_form.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
