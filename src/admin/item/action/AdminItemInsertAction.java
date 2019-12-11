package admin.item.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.item.db.ItemDAO;
import com.item.db.ItemDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import admin.item.action.Action;
import admin.item.action.ActionForward;

public class AdminItemInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemInsertAction_execute()--------------");
		
		HttpSession session = request.getSession();
		String id = (String)request.getAttribute("member_id");
		
		/*if(!id.equals("admin")){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println(	"history.back();");
			out.println("</script>");
		}*/
		
		String fileName="";
		String originalFileName="";
		
		
		//문의 �??�� ?��?�� ?��로드?�� �??��경로(upload ?��?�� ?��?��)
				String realPath = request.getRealPath("/upload");
				System.out.println("?��?��?�� ???��?��?�� ?��?�� ?���? : "+realPath);
				
				File folder = new File(realPath);
				
				//문의?��?�� ?��로드 ?��?���? ?��?�� 경우 ?��?��?���? ?��?��
				if(!folder.exists()){
					try {
						folder.mkdir(); //?��?�� ?��?��
					    System.out.println("?��?��?��로드?�� ?��?���? ?��?��?��?��?��?��?��.");
					} catch (Exception e) {
						e.getStackTrace();
					} 
				}else{
					System.out.println("?���? ?��?��?��로드?�� ?��?���? 존재?��?��?��.");
				}
		
		int maxSize = 100*1024*1024;
		// MultipartRequest객체 ?��?��
		MultipartRequest multi = new MultipartRequest(
				request,realPath,maxSize,"UTF-8",
				new DefaultFileRenamePolicy()
				);
		
		//?��?�� ?��로드?�� ?��름을 �??��?���?
		Enumeration formNames = multi.getFileNames();
		String formName = (String)formNames.nextElement();
		//?��료�? 많을 겨우?��?�� 반복�? ?��?�� 처리
		System.out.println(formName);
		//?��?��?�� ?���?(?��버에 ?��릴때 ?���?)
		fileName = multi.getFilesystemName(formName);
		
		System.out.println("itemsale: "+multi.getParameter("item_saleprice"));
		System.out.println((multi.getParameter("item_saleprice") != ""));
		ItemDTO idto = new ItemDTO();
		
		idto.setItem_name(multi.getParameter("item_name"));
		idto.setItem_price(Integer.parseInt(multi.getParameter("item_price")));
		idto.setItem_category(multi.getParameter("item_category"));
		idto.setItem_amount(Integer.parseInt(multi.getParameter("item_amount")));
		idto.setItem_color(multi.getParameter("item_color"));
		idto.setItem_size(multi.getParameter("item_size"));
		idto.setItem_thumbnail(multi.getFilesystemName("item_thumbnail"));
		idto.setItem_contentimage(multi.getFilesystemName("item_contentimage"));
		
		ItemDAO idao = new ItemDAO();
		
		
		
		idao.insertItem(idto);
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("./ItemListAdminAction.adite");
		forward.setRedirect(true);
		return forward;
	}

}
