package com.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.notice.db.NoticeBoardDAO;
import com.notice.db.NoticeBoardDTO;

public class NoticeBoardWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session =request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		ActionForward forward= new ActionForward();
		if(member_id == null || !(member_id.equals("admin"))){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			
			return forward;
		}
		NoticeBoardDTO mbdto = new NoticeBoardDTO();
		mbdto.setNoticeboard_content(request.getParameter("content"));
		mbdto.setNoticeboard_title(request.getParameter("title"));
		NoticeBoardDAO mbdao = new NoticeBoardDAO();
		mbdao.writeNotice(mbdto);
		forward.setPath("./NoticeList.nb");
		forward.setRedirect(true);
		
		return forward;
	}

}
