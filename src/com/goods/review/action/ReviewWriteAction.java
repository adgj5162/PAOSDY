package com.goods.review.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.review.action.Action;
import com.goods.review.action.ActionForward;
import com.goods.review.db.ReviewDAO;
import com.goods.review.db.ReviewDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ReviewWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ReviewWriteAction_execute()------------------");

		request.setCharacterEncoding("UTF-8");
		
//		HttpSession session = request.getSession();
//		String id = (String)session.getAttribute("id");
//		if(id == null){
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			
//			out.println("<script>");
//			out.println(" alert('로그인한 후에 다시 시도하십시오'); ");
//			out.println(" location.href='./MemberLogin.me'; ");
//			out.println("</script>");
//			out.close();
//			return null;
//		}
		
		// 업로드할 가상경로 (upload 폴더 생성)
	    String realPath = request.getRealPath("/upload");
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
		
		// 업로드할 파일의 크기지정
		int maxSize = 10 * 1024 * 1024; // 10mb
		
		// MultipartRequest 객체생성
		MultipartRequest multi = new MultipartRequest(
				request, realPath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy()); //

		ReviewDTO rdto = new ReviewDTO();
		rdto.setReview_item_num(Integer.parseInt(multi.getParameter("review_item_num")));
		rdto.setReview_member_id(multi.getParameter("review_member_id"));
		rdto.setReview_title(multi.getParameter("review_title"));
		rdto.setReview_content(multi.getParameter("review_content"));
		rdto.setReview_image(multi.getFilesystemName("review_image"));
		rdto.setReview_starpoint(Integer.parseInt(multi.getParameter("review_starpoint")));
		
		ReviewDAO rdao = new ReviewDAO();
		rdao.insertReview(rdto);
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("./ReviewList.re?item_num="+rdto.getReview_item_num());
		forward.setRedirect(true);
		return forward;
	}
}
