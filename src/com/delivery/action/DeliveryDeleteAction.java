package com.delivery.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.db.DeliveryDAO;




public class DeliveryDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();

		// 삭제 회원 아이디 정보 객체 저장
		System.out.println(request.getParameter("delete_addr"));
		int delete_addr = Integer.parseInt(request.getParameter("delete_addr"));
		
		System.out.println(delete_addr);
		
		DeliveryDAO ddao = new DeliveryDAO();
		
		ddao.DeliveryDelete(delete_addr);	
		
		
		
		
		forward.setPath("./DeliveryList.de");
		forward.setRedirect(true);

		return forward;
	
	}
	
	

}
