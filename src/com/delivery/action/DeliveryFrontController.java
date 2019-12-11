package com.delivery.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class DeliveryFrontController extends HttpServlet{
	
	// GET/POST 방식 상관없이 모두 사용
		protected void doProcess(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			System.out.println("doProcess() 호출!");
			
			// 가상주소를 가져오기
			//StringBuffer requestURL = request.getRequestURL();
			//System.out.println(requestURL);
			
			
			String requestURI = request.getRequestURI();
			System.out.println(requestURI);
			
			// /binu  (프로젝트명)
			String contextPath = request.getContextPath();
			System.out.println(contextPath);
			// /join.me (가상주소)
			String command = requestURI.substring(contextPath.length());
			System.out.println("command : "+command);
			

			
			Action action = null;
			ActionForward forward =null;

		
			if(command.equals("/DeliveryList.de")){
				// 주문 배송지 목록
				action = new DeliveryListAction();
				try {
					
					forward = action.execute(request, response);
					} catch (Exception e) {
					e.printStackTrace();				
					
					}
				
			}else if(command.equals("/DeliveryBook.de")){ // 주소록 페이지
				//메인페이지로 이동
				forward = new ActionForward();
				
				forward.setPath("./delivery/adress_book.jsp");
				forward.setRedirect(false);
				
			}else if(command.equals("/DeliverySave.de")){
			
				// 주소록 저장 
				action = new DeliverySaveAction();
				try {
					
					forward = action.execute(request, response);
					} catch (Exception e) {
					e.printStackTrace();				
					
					}
			}else if(command.equals("/DeliveryDelete.de")){
				action = new DeliveryDeleteAction();
				
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(command.equals("/updateAdressBook.de")){
				action = new UpdateAdressBook();
				
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(command.equals("/UpdateAdressBookAction.de")){
				action = new UpdateAdressBookAction();
				
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			
			
			
			
			/////////////////////////////////////////////////
	        // 페이지 이동
			if(forward != null){
				// 페이지 이동정보가 있다.
				if(forward.isRedirect()){
					// 이동방식 true
					response.sendRedirect(forward.getPath());
				}else{
					// 이동방식 false
					RequestDispatcher dis =
							request.getRequestDispatcher(forward.getPath());
					dis.forward(request, response);				
				}
				
			}
		
			
			
			
			
			
			
			
			
			
		}
			
			@Override
			protected void doGet(HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
			   System.out.println("doGet() 호출!");
			   doProcess(request, response);
			
			}
			@Override
			protected void doPost(HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
				System.out.println("doPost() 호출!");
				doProcess(request, response);
			}
			
			
}