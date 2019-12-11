package com.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.order.db.OrderDAO;

public class CancleRequestAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("CancleRequestAction_execute()");
		
		String order_trade_num = request.getParameter("order_trade_num");
		String order_memo = request.getParameter("order_memo");
		
		OrderDAO odao = new OrderDAO();
		odao.updateCancle(order_trade_num,order_memo);
		
		response.setContentType("text/html; charset=UTF-8");
	      PrintWriter out = response.getWriter();
	            
	      out.println("<script>");
	      out.println("   alert('취소/반품 신청 완료 되었습니다');   ");
	      out.println("   window.close(); ");
	      out.println("   opener.location.reload(); ");
	      out.println("</script>");
	            
	      out.close();
	      return null;
	}

}
