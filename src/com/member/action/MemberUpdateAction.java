package com.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.db.MemberDAO;
import com.member.db.MemberDTO;


public class MemberUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {


		// 세션 ID값 체크
				HttpSession session = request.getSession();
				String member_id = (String) session.getAttribute("member_id");

				ActionForward forward = new ActionForward();
				if (member_id == null) {
					forward.setPath("./MemberLogin.me");
					forward.setRedirect(true);
					return forward;
				}

				// 한글처리
				request.setCharacterEncoding("UTF-8");

				// 전달된 정보(form태그)저장 => MemberDTO

				MemberDTO mdto = new MemberDTO();
				///////////////////////////////////////
				// 수정하기 위해서 필요한 값
				
				mdto.setMember_name(request.getParameter("member_name"));
				mdto.setMember_email(request.getParameter("member_email"));
				mdto.setMember_phone(request.getParameter("member_phone"));	
				mdto.setMember_postcode(request.getParameter("member_postcode"));
				mdto.setMember_addr1(request.getParameter("member_addr1"));
				mdto.setMember_addr2(request.getParameter("member_addr2"));
				///////////////////////////////////////
				// 수정하기 위해서 체크를 위해 필요한 값
				mdto.setMember_id(member_id);
				
			
				
				mdto.setMemeber_password(request.getParameter("member_password"));
				

				// MemberDAO 객체에 전달해서 처리
				MemberDAO mdao = new MemberDAO();
				// updateMember(mdto)
				int check = mdao.updateMember(mdto); 
				System.out.println(check);
				// check = -1, 0, 1

				// 0,-1 비밀번호오류 / 아이디가 없을경우
				// 경고메시지 출력후, 뒤로가기 이동 (자바스크립트)
				response.setContentType("text/html; charset=UTF-8");
				
				if(check==-1){
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("  alert('ID가 잘못되었습니다.');  ");
					out.println("  history.back(); ");
					out.println("</script>");	
					
					out.close();
					
					return null;
					
				}else if(check == 0){
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("  alert('비밀번호가 잘못되었습니다.');  ");
					out.println("  history.back(); ");
					out.println("</script>");	
					
					out.close();
					
					return null;
				} 

				// 1 수정성공 메시지 출력
				// 페이지 이동 메인페이지로 이동 (자바스크립트)
				//check == 1
				
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("  alert('수정 완료되었습니다.');  ");
				out.println(" location.href='./MemberInfo.me' ");
				out.println("</script>");	
				
				out.close();	

				return null;
			}

	
}
