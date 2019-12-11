package com.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.db.ItemDAO;
import com.item.db.ItemDTO;

public class ItemDetailAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemDetailAction_execute()");
		request.setCharacterEncoding("UTF-8");
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		ItemDAO idao = new ItemDAO();
		ItemDTO idto = idao.getItem(item_num);
		
		request.setAttribute("idto", idto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/item/item_detail.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}
