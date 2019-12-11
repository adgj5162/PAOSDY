package com.ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.order.db.OrderDAO;

/**
 * Servlet implementation class OrderTransNumUpdate
 */
@WebServlet("/OrderTransNumUpdate")
public class OrderTransNumUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("OrderTransNumUpdate_execute()");
		
		String order_num = request.getParameter("order_num");
		String order_trans_num = request.getParameter("order_trans_num");
		
		OrderDAO odao = new OrderDAO();
		odao.OrderTransNumUpdate(order_num, order_trans_num);
	}

}
