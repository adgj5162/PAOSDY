package admin.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.db.ItemDAO;
import com.item.db.ItemDTO;

import admin.item.action.Action;
import admin.item.action.ActionForward;

public class AdminItemUpdateForm implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemUpdateAdminForm_execute()");
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		
		
		ItemDAO idao = new ItemDAO();
		
		ItemDTO idto = new ItemDTO();
		
		idto = idao.getItem(item_num);
		request.setAttribute("idto", idto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/admin_item_update.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
