package com.item.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ItemFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �??��주소 �??��???�� 처리
		String requestURI = request.getRequestURI();
		System.out.println("requestURI : "+requestURI);
		String contextPath = request.getContextPath();
		System.out.println("contextPath : "+contextPath);
		String command = requestURI.substring(contextPath.length());
		System.out.println("command : "+command);
		ActionForward forward = null;
		Action action = null;
		// �??��주소 비교
		
		if(command.equals("/ItemInsertAdminForm.ite")){
			forward = new ActionForward();
			forward.setPath("./item/admin_item_input.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/ItemListAction.ite")){
			action = new ItemListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ItemDetailAction.ite")){
			action = new ItemDetailAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ItemBasketAction.ite")){
			action = new ItemBasketAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/ItemBasketDeleteAction.ite")){
			action = new ItemBasketDeleteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		// �??��주소�? �?�?�? ?��?��
		
		if(forward != null){
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}else{
				RequestDispatcher dis = 
						request.getRequestDispatcher(forward.getPath());
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
