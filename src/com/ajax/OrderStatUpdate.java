package com.ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.order.db.OrderDAO;

/**
 * Servlet implementation class OrderStatUpdate
 */
@WebServlet("/OrderStatUpdate")
public class OrderStatUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("OrderStatUpdate_execute()---------");
		
		String order_num = request.getParameter("order_num");
		int order_stat = Integer.parseInt(request.getParameter("order_stat"));
		
		System.out.println("order_trade_num = "+order_num);
		
		OrderDAO odao = new OrderDAO();
		odao.OrderStatusUpdate(order_num, order_stat);
		
	}

}
