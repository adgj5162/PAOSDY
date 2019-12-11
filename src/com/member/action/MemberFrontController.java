package com.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







public class MemberFrontController extends HttpServlet {

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
		

		
		Action action=null;
		ActionForward forward=null;
		
		if(command.equals("/Main.me")){ // 메인 페이지
			//메인페이지로 이동
			forward = new ActionForward();
			
			forward.setPath("./main/main.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/MemberJoin.me")){ // 가입페이지로 이동
			// 회원가입 페이지 이동(View)
			forward = new ActionForward();
			
			forward.setPath("./member/member_join_form.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/MemberJoinAction.me")){ // 가입처리
			
			// 회원가입 처리(pro)
			action = new MemberJoinAction();
			try {
				forward = action.execute(request, response);
				} catch (Exception e) {
				e.printStackTrace();				
				
				}
		
		}else if(command.equals("/MemberLogin.me")){	// 로그인페이지로 이동
			
			forward = new ActionForward();
			
			forward.setPath("./member/member_login_form.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/MemberLoginAction.me")){ // 로그인 처리
			
			action = new MemberLoginAction();
			try {
				forward = action.execute(request, response);
				} catch (Exception e) {
				e.printStackTrace();				
				
				}
			
		}else if(command.equals("/MemberUpdate.me")){
			
			action = new MemberUpdate();
			try {
				forward = action.execute(request, response);
				} catch (Exception e) {
				e.printStackTrace();				
				}
			
		}else if(command.equals("/MemberUpdateAction.me")){
			
			action = new MemberUpdateAction();
			try {
				forward = action.execute(request, response);
				} catch (Exception e) {
				e.printStackTrace();				
				}
			
		}else if(command.contains("/Password.me")){
			
			forward = new ActionForward();
			
			forward.setPath("./member/password_update_form.jsp");
			forward.setRedirect(false);
		
		
		}else if(command.contains("/PasswordUpdateAction.me")){
			
			action = new PasswordUpdateAction();
			try {
				forward = action.execute(request, response);
				} catch (Exception e) {
				e.printStackTrace();				
				}
		}else if(command.contains("/MemberDelete.me")){
			
			forward = new ActionForward();
			
			forward.setPath("./member/member_delete.jsp");
			forward.setRedirect(false);
		
		
		}else if(command.equals("/MemberDeleteAction.me")){
		
			action = new MemberDeleteAction();
			try {
				forward = action.execute(request, response);
				} catch (Exception e) {
				e.printStackTrace();				
				}
			
		}else if(command.equals("/MemberInfo.me")){
			
			action = new MemberInfo();
			try {
				forward = action.execute(request, response);
				} catch (Exception e) {
				e.printStackTrace();				
				}
			
		}else if(command.equals("/AdminMemberList.me")){

			action = new MemberListAction();
			try {
			   	forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/Logout.me")){
			
			action = new MemberLogoutAction();
			try {
			   	forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberSearch.me")){
			
			action = new MemberSearchAction();
			
			try {
			   	forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberPasswordFind.me")){
			forward = new ActionForward();
			forward.setPath("./member/member_password_find.jsp");
			forward.setRedirect(false);
		} else if (command.equals("/CallBack.me")){
			forward = new ActionForward();
			forward.setPath("./member/callback.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/Intro.me")){
			forward = new ActionForward();
			forward.setPath("./main/intro.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/MemberIdFind.me")){
			forward = new ActionForward();
			forward.setPath("./member/member_id_find.jsp");
			forward.setRedirect(false);
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
