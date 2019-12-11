package admin.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.db.MemberDAO;
import com.member.db.MemberDTO;

import admin.member.action.Action;
import admin.member.action.ActionForward;



public class AdminMemberListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		
		
		
		// 세션값 제어 (로그인 상태 이면서 관리자일때만 처리가능)

		HttpSession session = request.getSession();
		String member_id = (String) session.getAttribute("member_id");

		ActionForward forward = new ActionForward();
		if (member_id == null || !member_id.equals("admin")) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);

			return forward;
		}
		
		
		
		
		// 한 페이지에서 보여줄 글의 개수 설정
				int pageSize = 10;
				
				// 현 페이지가 몇페이지 인지를 가져오기
				String pageNum = request.getParameter("pageNum");
				if (pageNum == null || pageNum.equals("null")) {
					pageNum = "1"; // pageNum의 값이 없을경우 무조건 1페이지
				}
				// 시작행 구하는 작업
				int currentPage = Integer.parseInt(pageNum);
				int startRow = (currentPage - 1) * pageSize + 1;
				// => 1 11 21 31 ....
				// 끝행 구하는 작업
				int endRow = currentPage * pageSize;
				
				

		// DB 처리객체 MemberDAO 생성
		MemberDAO mdao = new MemberDAO();
		// getMemberList(); => 회원정보 목록
		int membercount = mdao.getMemberCount();
		
		List<MemberDTO> memberList = null;
		if (membercount != 0) {

			memberList = mdao.getMemberList(startRow, pageSize);
		}
		
		int pageCount = membercount / pageSize + (membercount % pageSize == 0 ? 0 : 1);

		// 한화면에 보여줄 페이지 번호 계산
		int pageBlock = 2;

		// 시작페이지
		// 1~10 =>1 11~20 => 11 21~30 => 21
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
		// 끝페이지
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		

		// 회원목록을 저장
		request.setAttribute("memberList", memberList);
		
		// 정보(pageNum,pageCount,pageBlock,startPage,endPage)
		request.setAttribute("membercount", membercount );		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		// 페이지 이동
		// ./member/list.jsp
		forward.setPath("/admin/admin_member_list.jsp");
		forward.setRedirect(false);

		return forward;
	}

	
	
}
