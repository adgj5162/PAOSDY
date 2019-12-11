package com.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.order.db.OrderDAO;

public class OrderDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OrderDetailAction_execute()-------------------");
		
		
		//세션값 처리
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		/*
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		*/
		
		
		//파라미터로 전달한 값 저장
		//trade_num=<%=odto.getO_trade_num()%>
		String trade_num = request.getParameter("trade_num");
		
		//OrderDAO 객체 생성
		//orderDetail(trade_num);
		OrderDAO odao = new OrderDAO();
		List orderDetailList = odao.orderDetail(trade_num);
		
		
		
		//주문 상세페이지 정보를 저장
		request.setAttribute("orderDetailList", orderDetailList);
		
		
		//페이지 이동(./goods_order/order_detail.jsp)
		forward.setPath("./order/order_detail.jsp");
		forward.setRedirect(false);
		return forward;
	}

}