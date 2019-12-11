package admin.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;









public class AdminMemberFrontController extends HttpServlet{
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
		
		if(command.equals("/AdminMain.adme")){
			forward = new ActionForward();
			
			forward.setPath("./admin/admin_main.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/AdminMemberList.adme")){

			action = new AdminMemberListAction();
			try {
			   	forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/AdminMemberDelete.adme")){
			
			action = new AdminMemberDelete();
			
			try {
			   	forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberSearch.adme")){
			
			action = new AdminMemberSearchAction();
			
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
