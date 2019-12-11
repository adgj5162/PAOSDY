package com.order.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.delivery.db.DeliveryDAO;
import com.delivery.db.DeliveryDTO;
import com.goods.basket.db.BasketDAO;
import com.goods.basket.db.BasketDTO;
import com.item.db.ItemDAO;
import com.item.db.ItemDTO;
import com.member.db.MemberDAO;
import com.order.db.OrderDAO;
import com.order.db.OrderDTO;

public class OrderAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("OrderAction_execute()---------------------------");

		

		//TODO 세션값 풀어주기
		//세션값 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("member_id");
		ActionForward forward = new ActionForward();
		
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}

		
		//정보 저장
		//상품 정보 가져오기 ItemDAO
		ItemDAO idao = new ItemDAO();
		
		
		//장바구니 정보 가져오기(BasketDAO-메서드)
		BasketDAO badao = new BasketDAO();
		List basketList = badao.getBasketList(id);
		
		
		//사용자 정보 가져오기 MemberDAO
		MemberDAO mdao = new MemberDAO();
		com.member.db.MemberDTO mdto = mdao.getMember(id);

		
		//사용자 배송지 정보 가져오기 DeliveryDAO
		DeliveryDAO dedao = new DeliveryDAO();
		List<DeliveryDTO> deliveryList = (ArrayList<DeliveryDTO>)dedao.getDeliveryList(id);

		
		//필요한 정보를 저장(장바구니 목록, 장바구니에 있는 상품 정보, 회원정보)
		//=> request 객체에 저장
		request.setAttribute("basketList", basketList);
		request.setAttribute("deliveryList", deliveryList);
		request.setAttribute("mdto", mdto);
		
		
		
		//페이지 이동("./goods_order/goods_buy.jsp")
		forward.setPath("/order/goods_order.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
