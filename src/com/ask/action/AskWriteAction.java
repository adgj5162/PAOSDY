package com.ask.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ask.db.AskDAO;
import com.ask.db.AskDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class AskWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AskWriteAction_execute()**********");
		
		
		//ID 세션 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			/*forward.setPath("");
			forward.setRedirect(true);
			return forward;*/
			id="member";
		}
		
		
		//문의 관련 파일 업로드할 가상경로(upload 폴더 생성)
		String realPath = request.getRealPath("/ask_upload");
		System.out.println("파일이 저장되는 실제 위치 : "+realPath);
		
		File ask_folder = new File(realPath);
		
		//문의사항 업로드 폴더가 없을 경우 디렉토리 생성
		if(!ask_folder.exists()){
			try {
				ask_folder.mkdir(); //폴더 생성
			    System.out.println("문의사항 파일업로드용 폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			} 
		}else{
			System.out.println("이미 문의사항 파일업로드용 폴더가 존재합니다.");
		}
		
		//업로드할 파일의 크기 지정 2MB
		int maxSize = 2*1024*1024;
		
		//MultipartRequest 객체 생성
		String fileName="";
		String originalFileName="";
		
		MultipartRequest multi = new MultipartRequest(request, realPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		Enumeration formNames = multi.getFileNames();
		String formName = (String)formNames.nextElement();
		fileName = multi.getFilesystemName(formName);


		
		
		//글의 정보를 저장(폼 데이터)
		AskDTO askdto = new AskDTO();
		askdto.setAsk_member_id(multi.getParameter("member_id")); //회원ID
		askdto.setAsk_item_num(Integer.parseInt(multi.getParameter("ask_item_num"))); //문의사항 해당 제품번호
		askdto.setAsk_title(multi.getParameter("ask_title")); //문의사항 제목
		askdto.setAsk_content(multi.getParameter("ask_content")); //문의사항 본문내용
		askdto.setAsk_open(Integer.parseInt(multi.getParameter("ask_open"))); //문의사항 비밀글여부
		askdto.setAsk_category(multi.getParameter("ask_category")); //문의사항 구분
		//askdto.setAsk_answer(Integer.parseInt(multi.getParameter("ask_answer"))); //문의사항 답변여부
		askdto.setAsk_file(multi.getFilesystemName("ask_file")); //문의사항 업로드파일
		
		//AskDAO 처리 객체 생성 저장
		AskDAO askdao = new AskDAO();
		
		//insertAsk(DTO)
		askdao.insertAsk(askdto);
				
		//글 작성성공 메세지 띄우고 페이지 이동("./AskList.qa")
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("	alert('문의글 작성 성공했습니다!');	");
		out.println("	window.close(); ");
		//팝업창 닫히면서 부모창 새로고침
		out.println("	opener.parent.location.href='./ItemDetailAction.ite?item_num="+askdto.getAsk_item_num()+"'; "); 
		out.println("</script>");
		
		out.close();
		return null;
	}

}
