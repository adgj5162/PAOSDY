package com.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.db.ItemDAO;

public class ItemListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemListAction_execute()------------");
		
		
		
		
		String order_type = request.getParameter("order_type");
		if(order_type == null){
			order_type = "1";
		}
		int current_order_type = Integer.parseInt(order_type);
		
		ItemDAO idao = new ItemDAO();
		
		int count = idao.getItemCount();
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null){
			pageNum = "1"; //pageNum?�� 값이 ?��?��경우 무조�? 1?��?���?
		}
		int pageSize = 8;
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1)*pageSize;
		//=> 1	11	21	31	...
		//?��?�� 구하?�� ?��?��
		int endRow = currentPage*pageSize;
		//=>10	20	30	40	...
		System.out.println(startRow);
		System.out.println(endRow);
		List itemList = idao.getItemList(startRow, pageSize,current_order_type);
		
		request.setAttribute("itemList", itemList);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setPath("./item/item_list.jsp?pageNum="+pageNum+"&count="+count+"&currentPage="+currentPage+"&pageSize="+pageSize);
		forward.setRedirect(false);
		
		return forward;
	}

}
