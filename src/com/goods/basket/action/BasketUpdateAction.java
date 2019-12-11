package com.goods.basket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.basket.db.BasketDAO;

public class BasketUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BasketUpdateAction_execute()--------------------");
		
		//Id 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		int basket_num = Integer.parseInt(request.getParameter("basket_num"));
		int basket_amount = Integer.parseInt(request.getParameter("basket_amount"));
		System.out.println(basket_amount);
		BasketDAO basdao = new BasketDAO();
		basdao.updateBasket(basket_num,basket_amount);
		
		forward.setPath("./BasketList.bas?="+id);
		forward.setRedirect(true);
		return forward;
	}
	
	
}
