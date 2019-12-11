package com.goods.basket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.basket.db.BasketDAO;

public class BasketDeleteAllAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BasketDeleteAllAction_execute()----------------------");
		
		String id = (String)request.getParameter("id");
		
		BasketDAO basdao = new BasketDAO();
		basdao.deleteBasketAll(id);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./BasketList.bas");
		forward.setRedirect(true);
		return forward;
	}
	
}
