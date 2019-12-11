package com.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.basket.db.BasketDTO;

public class ItemBasketDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemBasketDeleteAction_execute");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		List basketList = (List)session.getAttribute("sessionBasketList");
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		int item_amount = Integer.parseInt(request.getParameter("item_amount"));
		String item_color = request.getParameter("item_color");
		String item_size = request.getParameter("item_size");
		
		for(int i=0; i<basketList.size(); i++){
			BasketDTO bdto = (BasketDTO)basketList.get(i);
			
			if((bdto.getBasket_item_num() == item_num) &&
				(bdto.getBasket_amount() == item_amount) &&
				(bdto.getBasket_color().equals(item_color)) &&
				(bdto.getBasket_size().equals(item_size))){
				basketList.remove(bdto);
				break;
			}
		}
		
		request.setAttribute("item_num", item_num);
		session.setAttribute("sessionBasketList", basketList);
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("./item/item_basket.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
