package com.delivery.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delivery.db.DeliveryDAO;
import com.delivery.db.DeliveryDTO;

public class UpdateAdressBookAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("UpdateAdressBookAction_execute()");
		
		// 한글 처리
				request.setCharacterEncoding("UTF-8");

				// 전달된 정보 값 저장
				
				DeliveryDTO ddto = new DeliveryDTO();
				
				ddto.setDelivery_member_id(request.getParameter("member_id"));
				ddto.setDelivery_name(request.getParameter("delivery_name"));
				ddto.setDelivery_place_name(request.getParameter("delivery_place_name"));
				ddto.setDelivery_phone(request.getParameter("delivery_phone"));
				ddto.setDelivery_postcode(request.getParameter("delivery_postcode"));
				ddto.setDelivery_addr1(request.getParameter("delivery_addr1"));
				ddto.setDelivery_addr2(request.getParameter("delivery_addr2"));
				ddto.setDelivery_index(Integer.parseInt(request.getParameter("index")));
				
				DeliveryDAO ddao = new DeliveryDAO();
				
				
				ddao.updateDelivery(ddto);
				
				
				//페이지 이동
			      response.setContentType("text/html; charset=UTF-8");
			      PrintWriter out = response.getWriter();
			      
			      out.println("<script>");
			      out.println("   alert('수정완료 하였습니다');   ");
			      out.println("   window.close(); ");
			      //팝업창 닫히면서 부모창 새로고침
			      out.println("opener.parent.location.reload(); "); 
			      
			      out.println("</script>");
			      
			      out.close();
			      return null;
		
	}

}
