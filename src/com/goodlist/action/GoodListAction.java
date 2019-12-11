package com.goodlist.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goodlist.db.GoodListDAO;

public class GoodListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("GoodListAction_execute()");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		
		if(id == null){
			response.sendRedirect("./index.jsp"); // 메인페이지
		}
		
		GoodListDAO gdao = new GoodListDAO();
		List goodlist = gdao.getGoodList(id);
		
		request.setAttribute("goodlist", goodlist);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./goodlist/goodlist.jsp");
		forward.setRedirect(false);
		return forward;
		
		
		
		
	}
	
}
