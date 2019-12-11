package com.goods.basket.action;

import java.sql.Array;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.basket.db.BasketDAO;

public class BasketCheckedDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BasketCheckedDeleteAction_execute()--------------------------");
		
		//Id 체크
//		HttpSession session = request.getSession();
//		String id = (String)session.getAttribute("id");
//		ActionForward forward = new ActionForward();
//		if(id == null){
//			forward.setPath("./MemberLogin.me");
//			forward.setRedirect(true);
//			return forward;
//		}
		
		String checkArray = request.getParameter("checkArray");
		checkArray.toString().split(",");
		System.out.println("체크한 상품의 번호 : "+checkArray);
		
		BasketDAO basdao = new BasketDAO();
		basdao.deleteCheckedBasket(checkArray);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./BasketList.bas");
		forward.setRedirect(true);
		return forward;
	}
	
}
