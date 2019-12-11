package admin.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.order.db.OrderDAO;
import com.order.db.OrderDTO;

public class AdminOrderListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		// 세션 값 제어 (로그인 상태이면서 관리자 일때만 처리가능)
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String member_id = (String) session.getAttribute("member_id");
		
		ActionForward forward = new ActionForward();
		
		if(member_id == null || !member_id.equals("admin")){
			
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			
			return forward;
		}
		System.out.println("액션페이지");
		
		// 한페이지에서 보여줄 글 개수
		
		int pageSize = 10;
		
		// 현 페이지가 몇페이지인지 가져오기
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null || pageNum.equals("null")){
			// pageNum의 값이 없는경우 무조건 1페이지
			pageNum = "1";
		}
		
		
		String stat = request.getParameter("stat");
		String search_column = request.getParameter("search_column");
		
		if(stat == null || stat.equals("null")){
			// pageNum의 값이 없는경우 무조건 1페이지
			stat = "0";
		}
		
		// 시작행
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage -1) * pageSize + 1;
		// 1, 11, 21, 31......
		
		// 끝행
		int endRow = currentPage * pageSize;
		
		
		// DB 처리객체
		
		OrderDAO odao = new OrderDAO();
		
		int ordercount = 0;
		List<OrderDTO> orderList = null;
		
		String search = request.getParameter("search");
		System.out.println("search : "+search);
		
		
		if(search == null || search.equals("null")){
			
			// getOrderList(); => 주문목록
			ordercount = odao.getOrderCount(Integer.parseInt(stat));
			System.out.println("오더카운트"+ordercount);
			
			if(ordercount != 0){
				
				orderList = odao.getOrderList(startRow, pageSize, Integer.parseInt(stat));
	
				
			}
		}else{
//			ordercount = odao.getOrderCount(search);
			
//			if(ordercount != 0){
				orderList = odao.OrderSearch(search_column, stat, search, startRow, pageSize);
			ordercount = orderList.size();
		}
		
		int pageCount = ordercount / pageSize + (ordercount % pageSize == 0 ? 0 : 1);
		
		// 한 화면에서 보여줄 페이지 번호 계산
		int pageBlock = 2;
		
		// 시작페이지
		// 1~10 =>1 , 11~20 => 11
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock +1;
		
		// 끝페이지
		int endPage = startPage + pageBlock - 1 ;
		
		if(endPage> pageCount){
			endPage = pageCount;
		}
		
		// 주문 목록을 저장
		
		request.setAttribute("orderList", orderList);
		
		// 정보(pageNum,pageCount,pageBlock,startPage,endPage)
				request.setAttribute("ordercount", ordercount);		
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("pageCount", pageCount);
				request.setAttribute("pageBlock", pageBlock);
				request.setAttribute("startPage", startPage);
				request.setAttribute("endPage", endPage);
				request.setAttribute("stat", stat);
				request.setAttribute("search", search);

				// 페이지 이동
				// ./admin/admin_order_list.jsp
				forward.setPath("./admin/admin_order_list.jsp");
				forward.setRedirect(false);

				return forward;
			
	
	
	}
}
