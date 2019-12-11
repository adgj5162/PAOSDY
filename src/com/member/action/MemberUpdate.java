package com.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.db.MemberDAO;
import com.member.db.MemberDTO;

public class MemberUpdate implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {


		// 세션값(id) 확인후 없을경우 -> 로그인 페이지로 이동
		HttpSession session = request.getSession();

		String member_id = (String) session.getAttribute("member_id");

		ActionForward forward = new ActionForward();
		if (member_id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}

		// MemeberDAO 객체 생성
		// getMember(id) => id에 해당하는 회원정보를 가져오기
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(member_id);

		// 해당 회원정보를 (request객체)
		request.setAttribute("mdto", mdto);

		// 페이지 이동 (./member/updateForm.jsp)
		forward.setPath("./member/member_update_form.jsp");
		forward.setRedirect(false);

		return forward;
	}
}
