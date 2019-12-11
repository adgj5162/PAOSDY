package com.goodlist.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goodlist.db.GoodListDAO;

public class deleteGoodListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("deleteGoodListAction_execute()");
		
		int goodlist_num = Integer.parseInt(request.getParameter("goodlist_num"));
		
		GoodListDAO gdao = new GoodListDAO();
		
		gdao.deleteGoodList(goodlist_num);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./GoodListAction.goo");
		forward.setRedirect(true);
		return forward;
	}

}
