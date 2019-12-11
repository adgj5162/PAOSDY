package com.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.db.DeliveryDAO;
import com.delivery.db.DeliveryDTO;

public class UpdateAdressBook implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("UpdateAdressBook_execute()");
		
		int index = Integer.parseInt(request.getParameter("index"));
		
		DeliveryDAO ddao = new DeliveryDAO();
		DeliveryDTO ddto = ddao.GetDelivey(index);
		
		request.setAttribute("ddto", ddto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./delivery/adress_book_update.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
