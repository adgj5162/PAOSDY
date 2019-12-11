package admin.ask.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;
import com.ask.db.AskDTO;

public class AdminAskModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskModifyAction_execute()**********");
		request.setCharacterEncoding("UTF-8");

		//ID 세션 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MainLogin.me");
			forward.setRedirect(true);
			return forward;
		}
	
		//객체 생성
		AskDTO askdto = new AskDTO();
		
		//값 넣어주기
		askdto.setAsk_open(Integer.parseInt("1"));
		askdto.setAsk_num(Integer.parseInt(request.getParameter("ask_num")));
		
		//받아온 페이지 번호, 아이템번호
		String askPageNum = request.getParameter("pageNum");
		
		
		// 디비 처리객체 DAO
		AskDAO askdao = new AskDAO();
		askdao.closeAsk(askdto);
		
		//비밀글로 변경 성공 메세지 띄우고 페이지 이동("./AskList.qa")
		
		
		
		
		//TODO 원상태로,,,,아무튼 경로 이동 잘하기...화이팅...
	
			forward.setPath("./AdminAskListAction.adask");	
			
			forward.setRedirect(true);
			return forward;

		
	


		
		
	}

}
