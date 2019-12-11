package com.order.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderFrontController extends HttpServlet{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doProcess!");
		
		//가상 주소를 가져와서 처리
		String requestURI = request.getRequestURI();
		System.out.println(" requestURI : "+requestURI);
		String contextPath = request.getContextPath();
		System.out.println(" contextPath : "+contextPath);
		String command = requestURI.substring(contextPath.length());
		System.out.println(" command : "+command);
		
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/Order.or")){
			action = new OrderAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/OrderAdd.or")){ //주문리스트
			action = new OrderAddAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/OrderFinish.or")){ //결제완료 페이지
			forward = new ActionForward();
			forward.setPath("./order/order_finish.jsp"); 
			forward.setRedirect(false);
		}else if(command.equals("/OrderList.or")){ //주문리스트
			action = new OrderListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/OrderDetail.or")){ //주문상세보기
			action = new OrderDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Payment.or")){ //주문상세보기
			forward = new ActionForward();
			forward.setPath("./order/payment1.jsp"); 
			forward.setRedirect(false);
		}else if(command.equals("/CancleRequest.or")){
			forward = new ActionForward();
			forward.setPath("./order/cancle.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/CancleRequestAction.or")){
			action = new CancleRequestAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//가상주소를 가지고 이동
		if(forward != null){
			if(forward.isRedirect()){//true
				response.sendRedirect(forward.getPath());
			}else{//false
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}
		}
	}
		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
}
