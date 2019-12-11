package com.goods.review.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.review.db.ReviewDAO;
import com.goods.review.db.ReviewDTO;

public class ReviewUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("AdminReviewUpdate_execute()---------------------");
		
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		
		ReviewDAO rdao = new ReviewDAO();
		ReviewDTO rdto = rdao.getReview(review_num);
		
		request.setAttribute("rdto", rdto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./review/review_update.jsp");
		forward.setRedirect(false);
		return forward;
	}
}
