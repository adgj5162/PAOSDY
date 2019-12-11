package com.goods.review.action;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.review.db.ReviewDAO;
import com.goods.review.db.ReviewDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ReviewUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AdminReviewUpdateAction_execute()______________________");
		
		request.setCharacterEncoding("UTF-8");
		
		String realPath= request.getRealPath("/upload");
		System.out.println("파일이 저장되는 위치 : "+realPath);
		// request.getContextPath();

		// 업로드할 파일의 크기지정
		int maxSize = 10 * 1024 * 1024; // 10mb
		
		// MultipartRequest 객체생성
		MultipartRequest multi = new MultipartRequest(
				request, realPath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy()); //
		
		int review_num = Integer.parseInt(multi.getParameter("review_num"));
        String existFile = multi.getParameter("existing_file");    // 기존 첨부 파일
		System.out.println(existFile);
        
		ReviewDTO rdto = new ReviewDTO();
		rdto.setReview_num(review_num);
		rdto.setReview_member_id(multi.getParameter("review_member_id"));
		rdto.setReview_starpoint(Integer.parseInt(multi.getParameter("review_starpoint")));
		rdto.setReview_title(multi.getParameter("review_title"));
		rdto.setReview_content(multi.getParameter("review_content"));
		rdto.setReview_image(multi.getFilesystemName("review_image"));
		rdto.setReview_item_num(Integer.parseInt(multi.getParameter("review_item_num")));
		
		Enumeration<String> fileNames = multi.getFileNames();
        if(fileNames.hasMoreElements())
        {
            String fileName = fileNames.nextElement();
            String updateFile = multi.getFilesystemName(fileName);
            
            if(updateFile == null){   // 수정시 새로운 파일을 첨부 안했다면 기존 파일명을 세팅
                rdto.setReview_image(existFile);
            }else{    // 새로운 파일을 첨부했을 경우
                rdto.setReview_image(updateFile);
            }    
        }

		ReviewDAO rdao = new ReviewDAO();
		int check = rdao.updateReview(rdto);
		
		if(check == -1){
		}
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("./ReviewList.re?item_num="+rdto.getReview_item_num());
		forward.setRedirect(true);
		return forward;
	}
	
}
