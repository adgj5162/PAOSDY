package com.notice.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NoticeBoardFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		
		// /binu  (프로젝트명)
		String contextPath = request.getContextPath();
		System.out.println(contextPath);
		// /join.me (가상주소)
		String command = requestURI.substring(contextPath.length());
		System.out.println("command : "+command);

		
		Action action=null;
		ActionForward forward=null;
		
		if(command.equals("/NoticeBoardWrite.nb")){
			forward = new ActionForward();
			forward.setPath("./notice_board/notice_board_write_form.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/NoticeBoardWriteAction.nb")){
			action = new NoticeBoardWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(command.equals("/NoticeList.nb")){
			action = new NoticeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if(command.equals("/NoticeBoardContent.nb")){
			action = new NoticeBoardContentAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}if(command.equals("/NoticeDeleteAction.nb")){
			action = new NoticeDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if(command.equals("/NoticeBoardUpdate.nb")){
			action = new NoticeBoardUpdate();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if(command.equals("/NoticeUpdateAction.nb")){
			action = new NoticeUpdateAction();
			
			try {
				forward=action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
