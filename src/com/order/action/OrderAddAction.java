package com.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.goods.basket.db.BasketDAO;
import com.goods.basket.db.BasketDTO;
import com.item.db.ItemDAO;
import com.member.db.MemberDAO;
import com.mysql.fabric.xmlrpc.base.Array;
import com.order.db.OrderDAO;
import com.order.db.OrderDTO;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;


public class OrderAddAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

      System.out.println("OrderAddAction_execute()---------------------------");
      

      
      //세션값 체크
      HttpSession session = request.getSession();
      String id = (String)session.getAttribute("member_id");
      ActionForward forward = new ActionForward();
      
      if(id == null){
         forward.setPath("./MemberLogin.me");
         forward.setRedirect(true);
         return forward;
      }
      
      
      //한글처리
      request.setCharacterEncoding("UTF-8");
      
      //jsp 페이지에서 전달된 정보를 저장
      int rest_point = Integer.parseInt(request.getParameter("rest_point"));
      int new_point = Integer.parseInt(request.getParameter("new_point"));
      int point = rest_point+new_point;
      
      
      String order_payer_phone1 = request.getParameter("order_payer_phone1");
      String order_payer_phone2 = request.getParameter("order_payer_phone2");
      String order_payer_phone3 = request.getParameter("order_payer_phone3");
      String order_payer_phone = order_payer_phone1+order_payer_phone2+order_payer_phone3;
      String order_recipient_phone = request.getParameter("order_recipient_phone").replace("-", "");
      
      System.out.println(order_payer_phone);
      

      //OrderDTO 객체 생성
      //전달받은 파라미터값 저장
      OrderDTO odto = new OrderDTO();
      odto.setOrder_member_id(id);
      odto.setOrder_name(request.getParameter("order_recipient_name")); //받는사람 ?? 주문자
      odto.setOrder_take_name(request.getParameter("order_recipient_name")); //받는사람 ?? 수령인
      //odto.setOrder_item_name(request.getParameter("item_name"));
      //odto.setOrder_price(Integer.parseInt(request.getParameter("price"))); //개당 가격
      //odto.setOrder_amount(Integer.parseInt(request.getParameter("amount")));
      //odto.setOrder_size(request.getParameter("size"));
      //odto.setOrder_color(request.getParameter("color"));
      odto.setOrder_postcode(request.getParameter("postcode"));
      odto.setOrder_addr1(request.getParameter("addr1"));
      odto.setOrder_addr2(request.getParameter("addr2"));
      odto.setOrder_member_phone(order_payer_phone); //주문자 연락처
      odto.setOrder_phone(order_recipient_phone); //수령인 번호
      odto.setOrder_memo(request.getParameter("memo"));
      
      odto.setOrder_pay_type(request.getParameter("order_payment"));
      odto.setOrder_point(new_point);
      odto.setOrder_payer(request.getParameter("order_payer_name")); //입금자명(결제자명)

      
      //장바구니 정보
      BasketDAO badao = new BasketDAO();
      List basketList = badao.getBasketList(id);
      BasketDTO badto = new BasketDTO();
      List numArr = new ArrayList();
      for(int i=0; i<basketList.size(); i++){
         badto = (BasketDTO)basketList.get(i);
         numArr.add(badto.getBasket_item_num());
      }
      
      badto.getBasket_item_num();
      ItemDAO idao = new ItemDAO();
      List itemList = new ArrayList();
      
      for(int i=0; i<numArr.size(); i++){
         itemList.add(idao.getItem((Integer)numArr.get(i)));
      }
      
      //상품정보
      
      
      //결제 모듈(구현x)
      
      
      //OrderDAO 처리 객체
      //addOrder(주문정보, 장바구니 정보, 상품정보)
      OrderDAO odao = new OrderDAO();
      odao.addOrder(odto,basketList,itemList);
      
      MemberDAO mdao = new MemberDAO();
      mdao.updatePoint(id,point);
      
      
      //구매자 확인 메일/문자 전송 작업(구현x)
       String api_key = "NCSMNWPAYMAPGAEV";
       String api_secret = "HTX6J5WNJYPIBFRKHWGY1PFT9VTKAMIK";
       Message coolsms = new Message(api_key, api_secret);

       // 4 params(to, from, type, text) are mandatory. must be filled
       HashMap<String, String> params = new HashMap<String, String>();
       params.put("to", order_payer_phone); // 수신번호
       params.put("from", "01046044685"); // 발신번호
       params.put("type", "SMS"); // Message type ( SMS, LMS, MMS, ATA )
       params.put("text", "주문해주셔서 감사합니다 [ PAOSDY ]"); // 문자내용    
       params.put("app_version", "JAVA SDK v1.2"); // application name and version

       // Optional parameters for your own needs
       // params.put("image", "desert.jpg"); // image for MMS. type must be set as "MMS"
       // params.put("image_encoding", "binary"); // image encoding binary(default), base64 
       // params.put("mode", "test"); // 'test' 모드. 실제로 발송되지 않으며 전송내역에 60 오류코드로 뜹니다. 차감된 캐쉬는 다음날 새벽에 충전 됩니다.
       // params.put("delay", "10"); // 0~20사이의 값으로 전송지연 시간을 줄 수 있습니다.
       // params.put("force_sms", "true"); // 푸시 및 알림톡 이용시에도 강제로 SMS로 발송되도록 할 수 있습니다.
       // params.put("refname", ""); // Reference name
       // params.put("country", "KR"); // Korea(KR) Japan(JP) America(USA) China(CN) Default is Korea
       // params.put("sender_key", "5554025sa8e61072frrrd5d4cc2rrrr65e15bb64"); // 알림톡 사용을 위해 필요합니다. 신청방법 : http://www.coolsms.co.kr/AboutAlimTalk
       // params.put("template_code", "C004"); // 알림톡 template code 입니다. 자세한 설명은 http://www.coolsms.co.kr/AboutAlimTalk을 참조해주세요. 
       // params.put("datetime", "20140106153000"); // Format must be(YYYYMMDDHHMISS) 2014 01 06 15 30 00 (2014 Jan 06th 3pm 30 00)
       // params.put("mid", "mymsgid01"); // set message id. Server creates automatically if empty
       // params.put("gid", "mymsg_group_id01"); // set group id. Server creates automatically if empty
       // params.put("subject", "Message Title"); // set msg title for LMS and MMS
       // params.put("charset", "euckr"); // For Korean language, set euckr or utf-8
       // params.put("app_version", "Purplebook 4.1") // 어플리케이션 버전

       try {
         JSONObject obj = (JSONObject)coolsms.send(params);
         System.out.println(obj.toString());
                  
       } catch (CoolsmsException e) {
         System.out.println(e.getMessage());
         System.out.println(e.getCode());
       }
      
      
      //상품 전체 개수를 수정(구매한 개수를 제외)
      //->goods 테이블 정보 수정
      odao.updateAmount(basketList);
      
      
      //장바구니 정보 삭제
      BasketDAO bdao = new BasketDAO();
      bdao.deleteBasketAll(id);
      
      //페이지 이동
      forward.setPath("./OrderFinish.or"); 
      forward.setRedirect(true);
      return forward;
   }

}