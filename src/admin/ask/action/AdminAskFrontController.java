package admin.ask.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.member.action.AdminMemberDelete;
import admin.member.action.AdminMemberListAction;
import admin.member.action.AdminMemberSearchAction;


public class AdminAskFrontController extends HttpServlet{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 가상주소 가져와서 처리
		String requestURI = request.getRequestURI();
		System.out.println("requestURI : "+requestURI);
		String contextPath = request.getContextPath();
		System.out.println("contextPath : "+contextPath);
		String command = requestURI.substring(contextPath.length());
		System.out.println("command : "+command);
		ActionForward forward = null;
		Action action = null;
		// 가상주소 비교
		
		
		
		if(command.equals("/AdminAskListAction.adask")){
			action = new AdminAskListAdminAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminAskModifyAction.adask")){
			//new AskModifyAction
			action = new AdminAskModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminAskDeleteAction.adask")){
			//new AskDeleteAction
			action = new AdminAskDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminAskCommentWrite.adask")){
			//new AskCommentWriteAction
			action = new AdminAskCommentWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminAskCommentUpdate.adask")){
			//new AskCommentUpdate
			action = new AdminAskCommentUpdate();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminAskCommentUpdateAction.adask")){
			//new AskCommentUpdateAction
			action = new AdminAskCommentUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminAskCommentDelete.adask")){
			//new AskCommentDelete
			action = new AdminAskCommentDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminAskOpenAction.adask")){
			action = new AdminAskOpenAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
