package com.item.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goodlist.db.GoodListDAO;
import com.item.db.ItemDAO;

/**
 * Servlet implementation class addWish
 */
@WebServlet("/checkWish")
public class checkWish extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int page_num = Integer.parseInt(request.getParameter("page_num"));
		String id = request.getParameter("id");
		
		GoodListDAO gdao = new GoodListDAO();
		
		int num = gdao.checkWishList(page_num, id);
		
		PrintWriter script = response.getWriter();
	      script.print(num);
		
		//doGet(request, response);
	}

}
