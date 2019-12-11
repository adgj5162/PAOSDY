package com.delivery.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.delivery.db.DeliveryDAO;
import com.delivery.db.DeliveryDTO;


public class DeliveryListAction implements Action{
	

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		
		
		
	// 세션값 제어 (로그인 상태 이면서 관리자일때만 처리가능)
	HttpSession session = request.getSession();
	String member_id = (String) session.getAttribute("member_id");

	ActionForward forward = new ActionForward();
	
	if (member_id == null ){
		forward.setPath("./MemberLogin.me");
		forward.setRedirect(true);

		return forward;
	}


			

	// DB 처리객체 MemberDAO 생성
	DeliveryDAO ddao = new DeliveryDAO();
	// getMemberList(); => 회원정보 목록
	int addrcount = ddao.getAddrCount();
	System.out.println(addrcount);
	List<DeliveryDTO> deliveryList = null;
	if (addrcount != 0) {

		deliveryList = ddao.getDeliveryList(member_id);
	}
	

	
	
	

	// 회원목록을 저장
	request.setAttribute("deliveryList", deliveryList);
	
	// 정보(pageNum,pageCount,pageBlock,startPage,endPage)
	request.setAttribute("addrcount", addrcount );		
	// 페이지 이동
	// ./member/list.jsp
	String order = request.getParameter("order");
	   if(order != null){
	      forward.setPath("./delivery/order_delivery_list.jsp");
	      forward.setRedirect(false);
	      return forward;
	   }
	
	forward.setPath("./delivery/delivery_list.jsp");
	forward.setRedirect(false);

	return forward;
}

}
