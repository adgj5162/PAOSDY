package admin.item.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.db.ItemDAO;
import com.item.db.ItemDTO;

import admin.item.action.Action;
import admin.item.action.ActionForward;

public class AdminItemDeleteAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemDeleteAdminAction_execute()");
		
		int item_num = Integer.parseInt(request.getParameter("item_num"));
	
		ItemDAO idao = new ItemDAO();
		
		
		
		ItemDTO idto = idao.getItem(item_num);
		idao.deleteItem(item_num);
		
		File f1 = new File("./upload/"+idto.getItem_thumbnail());
		File f2 = new File("./upload/"+idto.getItem_contentimage());
		
		f1.delete();
		f2.delete();
		
		System.out.println("?��?��?��?��?���?");
		
		ActionForward forward = new ActionForward();
		forward.setPath("./ItemListAdminAction.adite");
		forward.setRedirect(true);
		return forward;
	}
}
