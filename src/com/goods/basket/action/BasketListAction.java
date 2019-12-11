package com.goods.basket.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.basket.db.BasketDAO;
import com.goods.basket.db.BasketDTO;

public class BasketListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BasketListAction_execute()--------------------------------");
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		String basket_member_id = id;
//		String basket_member_id = "id";// 임의로 아이디값 처리 반드시 확인!!
		System.out.println(basket_member_id);
		BasketDAO basdao = new BasketDAO();
		int count = basdao.getbasketCount(basket_member_id /*id*/);
		
		
		// DB에서 글 가져오기
		// getReviewList();
		List<BasketDTO> basketList = null;
		if (count != 0) {
			basketList = basdao.getBasketList(basket_member_id /*id*/);// 임의로 아이디값 처리 반드시 확인!!
		}else{
			basketList = null;
		}
		
		System.out.println(basket_member_id);
		request.setAttribute("count", count);
		request.setAttribute("basketList", basketList);
		request.setAttribute("basket_member_id", basket_member_id);
		
		forward.setPath("./basket/basket_list.jsp");
		forward.setRedirect(false);
		return forward;
	}
	
}
