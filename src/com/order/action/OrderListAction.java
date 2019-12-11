package com.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.order.db.OrderDAO;
import com.order.db.OrderDTO;

public class OrderListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OrderListAction_execute()---------------------------");
		
		
//		String id = "id";
		//세션값 체크
		//TODO 세션값 풀기
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null){
			pageNum = "1"; //pageNum?�� 값이 ?��?��경우 무조�? 1?��?���?
		}
		int pageSize = 4;
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1)*pageSize;
		//=> 1	11	21	31	...
		//?��?�� 구하?�� ?��?��
		int endRow = currentPage*pageSize;
		
		//주문정보OrderDAO 객체 생성
		OrderDAO odao = new OrderDAO();
		
		int count = odao.getOrderCount(0,id);
		
		//getOrderList(id); 메서드 이용해서 처리
		List<OrderDTO> orderList = odao.getOrderList(id,startRow, pageSize);
		
		//해당 주문 목록 정보를 저장
		request.setAttribute("orderList", orderList);
		
		//페이지 이동(./goods_order/order_list.jsp)		
		forward.setPath("/order/order_list.jsp?pageNum="+pageNum+"&count="+count+"&currentPage="+currentPage+"&pageSize="+pageSize);
		forward.setRedirect(false);
		return forward;
	}

}