package com.goods.basket.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.basket.db.BasketDAO;

public class BasketDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BasketDeleteAction_execute()------------------------");
		
		//Id 체크
//		HttpSession session = request.getSession();
//		String id = (String)session.getAttribute("id");
//		ActionForward forward = new ActionForward();
//		if(id == null){
//			forward.setPath("./MemberLogin.me");
//			forward.setRedirect(true);
//			return forward;
//		}
		
		int basket_num = Integer.parseInt(request.getParameter("basket_num"));
		
		BasketDAO basdao = new BasketDAO();
		basdao.deleteBasket(basket_num);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./BasketList.bas");
		forward.setRedirect(true);
		return forward;
	}
}