	package admin.item.action;

import java.io.File;
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

public class AdminItemUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ItemUpdateAdminAction_execute()");
		
		HttpSession session = request.getSession();
		String id = (String)request.getAttribute("id");
		
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
		
		
		// ?�� ?�� 체인�??�� ?��?�� ?��?��?��름을 ?��?��?��그로 보내?�� 
		String thumbnail_old = multi.getParameter("thumbnail_old");
		String contentimage_old = multi.getParameter("contentimage_old");
		/*if(!(thumbnail_old.equals(multi.getParameter("item_thumbnail")))){
			File f = new File("./upload/"+thumbnail_old);
			f.delete();
		}
		if(!(contentimage_old.equals(multi.getParameter("item_contentimage")))){
			File f = new File("./upload/"+contentimage_old);
			f.delete();
		}*/
		
		System.out.println(fileName);
		
		
		ItemDTO idto = new ItemDTO();
		
		idto.setItem_num(Integer.parseInt(multi.getParameter("item_num")));
		idto.setItem_name(multi.getParameter("item_name"));
		idto.setItem_price(Integer.parseInt(multi.getParameter("item_price")));
		idto.setItem_category(multi.getParameter("item_category"));
		idto.setItem_amount(Integer.parseInt(multi.getParameter("item_amount")));
		idto.setItem_color(multi.getParameter("item_color"));
		idto.setItem_size(multi.getParameter("item_size"));
		idto.setItem_thumbnail(multi.getFilesystemName("item_thumbnail"));
		idto.setItem_contentimage(multi.getFilesystemName("item_contentimage"));
		
		if(multi.getFilesystemName("item_thumbnail") != null){
			idto.setItem_thumbnail(multi.getFilesystemName("item_thumbnail"));
			File f = new File("./upload/"+multi.getParameter("old_item_thumbnail"));
			f.delete();
		}else{
			idto.setItem_thumbnail(multi.getParameter("old_item_thumbnail"));
		}
		
		if(multi.getFilesystemName("item_contentimage") != null){
			idto.setItem_contentimage(multi.getFilesystemName("item_contentimage"));
			File f = new File("./upload/"+multi.getParameter("old_item_contentimage"));
			f.delete();
		}else{
			idto.setItem_contentimage(multi.getParameter("old_item_contentimage"));
		}
		
		
		ItemDAO idao = new ItemDAO();
		idao.updateItem(idto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./ItemListAdminAction.adite");
		forward.setRedirect(true);
		return forward;
	}

}
