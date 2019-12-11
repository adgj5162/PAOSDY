package com.item.ajax;

import java.io.IOException;
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
@WebServlet("/addWish")
public class addWish extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		int page_num = Integer.parseInt(request.getParameter("page_num"));
		String id = request.getParameter("id");
		int wishValue = Integer.parseInt(request.getParameter("wishValue"));
		String item_name = request.getParameter("item_name");
		String item_thumbnail = request.getParameter("item_thumbnail");
		
		
		GoodListDAO gdao = new GoodListDAO();
		
		if(wishValue == 1){
			gdao.insertWishList(page_num, id, item_name, item_thumbnail);
		}else if(wishValue == 2){
			gdao.deleteWishList(page_num, id);
		}
		
		
		//doGet(request, response);
	}

}
