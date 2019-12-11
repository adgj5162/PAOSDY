package admin.ask.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;

public class AdminAskDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskDeleteAction_execute()**********");
		
		//ID 세션 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		
		// 상품번호 num 저장
		int askNum = Integer.parseInt(request.getParameter("ask_comment_num"));
		//int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		
		System.out.println(askNum+"asdas");
		
		// 디비 처리객체 DAO
		AskDAO askdao = new AskDAO();
		askdao.deleteAsk(askNum);
	
		
		
		
		//TODO 원상태로,,,,아무튼 이동 잘하기...
		
			//삭제 성공 메세지 띄우고 페이지 이동("./AskList.qa")

		
			forward.setPath("./AdminAskListAction.adask");	
			
			forward.setRedirect(true);
			return forward;
		
		



	}

}
