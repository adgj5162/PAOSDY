package com.goods.basket.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.delivery.db.DeliveryDAO;
import com.delivery.db.DeliveryDTO;
import com.goods.basket.db.BasketDAO;
import com.goods.basket.db.BasketDTO;
import com.member.db.MemberDAO;

public class InsertBasketOrderAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

      System.out.println("InsertBasketOrderAction_execute");
      
      HttpSession session = request.getSession();
      int item_num = Integer.parseInt(request.getParameter("item_num"));
      String id = (String)session.getAttribute("member_id");
      
      if(id == null || id == "null"){
         response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("   alert('로그인 상태에서 가능합니다');   ");
            out.println("   location.href='./MemberLogin.me' ");
            out.println("</script>");
            
            out.close();
      }
      
      List basketList = (List)session.getAttribute("sessionBasketList");
      
      List realBasketList = new ArrayList();
      
      if(basketList != null){
         for(int i=0; i<basketList.size(); i++){
            BasketDTO bdto = (BasketDTO)basketList.get(i);
            System.out.println(bdto.getBasket_item_num());
            System.out.println(item_num);
            if(bdto.getBasket_item_num() == item_num && (bdto.getBasket_member_id()!= null && bdto.getBasket_member_id().equals(id))){
               realBasketList.add(bdto);
               
               
            }
         }
      }
               
         if(realBasketList.size() == 0){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("   alert('상품옵션을 선택해주세요');   ");
            out.println("   history.back(); ");
            out.println("</script>");
            
            out.close();
         }else{
            BasketDAO bdao = new BasketDAO();
            Loop1 : for(int i=0; i<realBasketList.size(); i++){
               BasketDTO bdto = (BasketDTO)realBasketList.get(i);
               
               List memberBasketList = bdao.getBasketList(id);
               if(memberBasketList.size() != 0){
                  for(int j=0; j<memberBasketList.size(); j++){
                     BasketDTO mbdto = (BasketDTO)memberBasketList.get(j);
                     if((mbdto.getBasket_member_id().equals(bdto.getBasket_member_id())) && 
                     (mbdto.getBasket_size().equals(bdto.getBasket_size())) && 
                     (mbdto.getBasket_color().equals(bdto.getBasket_color())) &&
	    			   mbdto.getBasket_num() == bdto.getBasket_num()){
                        bdao.updateBasket(mbdto.getBasket_num(), mbdto.getBasket_amount()+bdto.getBasket_amount());
                        basketList.remove(bdto);
                        continue Loop1;
                     }
                  }
                  bdao.insertBasket(bdto);
               }else{
                  bdao.insertBasket(bdto);
               }
               basketList.remove(bdto);
            }
            }
            
          

            
            session.setAttribute("sessionBasketList", basketList);
         ActionForward forward = new ActionForward();
         forward.setPath("./Order.or");
         forward.setRedirect(true);
         return forward;
      
   }

}