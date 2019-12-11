package admin.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.db.ItemDAO;

import admin.item.action.Action;
import admin.item.action.ActionForward;

public class AdminItemListAction implements Action{
		@Override
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

			System.out.println("ItemListAdminAction_execute()");
			
			// ?���? 처리 객체 ?��?��
			ItemDAO idao = new ItemDAO();
			
			
			
			
			// ?��비에 ???��?�� ITEM?�� 개수�? 계산
			int count = idao.getItemCount();
			//*******************************************************
			// ?��?���? 처리
			// ?�� ?��?���??��?�� 보여�? �??�� 개수 ?��?��
			int pageSize = 10;
			// ?��?��면에 보여�? ?��?���? 계산
			int pageBlock = 5;
			// ?��?�� ?��?���?�? 몇페?���? ?���?�? �??��?���?
			String pageNum = request.getParameter("pageNum");
			if(pageNum == null){
				pageNum = "1"; //pageNum?�� 값이 ?��?��경우 무조�? 1?��?���?
			}
			//?��?��?�� 구하?�� ?��?��
			int currentPage = Integer.parseInt(pageNum);
			int startRow = (currentPage - 1)*pageSize;
			//=> 1	11	21	31	...
			//?��?�� 구하?�� ?��?��
			int endRow = currentPage*pageSize;
			//=>10	20	30	40	...
			
			
			
			//*******************************************************
			
			List itemList =null;
			// ?��비에 게시?�� �??�� ?��?��경우?���?
			// ?��비에 �??�� 게시?�� 모든 �??�� ?��보�?? �??��?���?
			if(count != 0){
				// boardList = bdao.getBoardList();
				// ?��비에 게시?���? �??��?���?
				// ?��?��?�� 개수�? �??��?���?
				itemList = idao.getItemList(startRow,pageSize,1);
			}
			
			request.setAttribute("itemList", itemList);
			request.setAttribute("count", count);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pageBlock", pageBlock);
			request.setAttribute("pageNum", pageNum);
			
			ActionForward forward = new ActionForward();
			forward.setPath("/admin/admin_item_list.jsp");
			forward.setRedirect(false);
			return forward;
		}
}
