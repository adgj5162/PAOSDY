package com.item.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.basket.db.BasketDTO;

public class ItemBasketAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemBasketAction_execute()");
		
		HttpSession session = request.getSession();
		List basketList = (List)session.getAttribute("sessionBasketList");
		String id = (String)session.getAttribute("member_id");
		
		
		
		if(basketList == null){
			basketList = new ArrayList();
		}
		System.out.println(basketList.toString());
		
		int basket_item_num = Integer.parseInt(request.getParameter("basket_item_num"));
		String noAdd = request.getParameter("NoAdd");
		
		if(noAdd == null){
		int basket_item_amount = Integer.parseInt(request.getParameter("basket_item_amount"));
		String basket_item_size = request.getParameter("basket_item_size");
		String basket_item_color = request.getParameter("basket_item_color");
		int basket_item_price = Integer.parseInt(request.getParameter("basket_item_price"));
		String basket_item_name = request.getParameter("basket_item_name");
		String basket_item_thumbnail = request.getParameter("basket_item_thumbnail");
		
		
		BasketDTO bdto = new BasketDTO();
		
		bdto.setBasket_member_id(id);
		bdto.setBasket_item_name(basket_item_name);
		bdto.setBasket_item_num(basket_item_num);
		bdto.setBasket_thumbnail(basket_item_thumbnail);
		bdto.setBasket_amount(basket_item_amount);
		bdto.setBasket_size(basket_item_size);
		bdto.setBasket_color(basket_item_color);
		bdto.setBasket_price(basket_item_price);
		
		
		
		basketList.add(bdto);
		
		session.setAttribute("sessionBasketList", basketList);
		}
		request.setAttribute("item_num", basket_item_num);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./item/item_basket.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
