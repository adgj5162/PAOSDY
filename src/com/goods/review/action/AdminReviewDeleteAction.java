package com.goods.review.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.review.db.ReviewDAO;
import com.goods.review.db.ReviewDTO;

public class AdminReviewDeleteAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

      System.out.println("AdminReviewDeleteAction_execute()-------------------------------");
      
      String pageNum = request.getParameter("pageNum");
      int review_num = Integer.parseInt(request.getParameter("review_num"));
      int item_num = Integer.parseInt(request.getParameter("item_num"));
      
      ReviewDAO rdao = new ReviewDAO();
      rdao.deleteReview(review_num);
      
      
      ActionForward forward = new ActionForward();
      forward.setPath("./ReviewList.re?item_num="+item_num);
      forward.setRedirect(true);
      return forward;
   }
   
}