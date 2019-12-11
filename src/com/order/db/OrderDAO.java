package com.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.goods.basket.db.BasketDTO;
import com.item.db.ItemDTO;
import com.order.db.OrderDTO;


public class OrderDAO {

   //DB작업 및 처리 객체
   Connection con = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   String sql=""; 

   //디비 연결 메서드
   private Connection getCon() throws Exception{
      Context init = new InitialContext();
      DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/binu");
      con = ds.getConnection();
      System.out.println("DB 접속 완료 : "+con);
      return con;
   }

   //디비 자원해제 메서드
   public void closeDB(){
      try {
         if(rs != null) rs.close();
         if(pstmt != null) pstmt.close();
         if(con != null) con.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }


   //getOrderCount(id)
   public int getOrderNum(String id){
      int order_count = 0;
      try {
         con = getCon();

         sql = "SELECT COUNT(*) FROM order1 WHERE order_member_id=?";

         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         if(rs.next()){
            order_count = rs.getInt(1); //1: sql 쿼리구문의 실행 결과
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      return order_count;
   }
   //getOrderNum(id)


   //addOrder(odto,basketList,goodsList)
   public void addOrder(OrderDTO odto, List basketList, List itemList){
      int order_num = 0; //주문번호
      int order_trade_num = 0; //결제번호

      Calendar cal = Calendar.getInstance(); 
      //System.out.println("cal : "+cal.toString());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      //mm : 분  / MM : 월

      try {
         con = getCon();

         //order_num 계산하기
         sql = "SELECT MAX(order_num) FROM order1";
         pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if(rs.next())
            order_num = rs.getInt(1)+1;

         //order_num, order_trade_num 동일한 값으로 사용
         order_trade_num = order_num;

         //g_order 테이블에 정보 저장
         //반복(장바구니 상품 개수만큼 테이블에 저장)
         for(int i=0;i<basketList.size();i++){
            BasketDTO badto = (BasketDTO)basketList.get(i);
            ItemDTO idto = (ItemDTO)itemList.get(i); 

            //spl 작성
            
            /*
            + "(order_num, order_member_id, "
                  + "order_item_num, order_price, order_amount, order_size, order_color, "
                  + "order_date, order_postcode, order_addr1, order_addr2, order_member_phone, order_phone, "
                  + "order_memo, order_trade_num, order_trade_date, order_pay_type, order_payer, "
                  + "order_status, order_trans_num, order_name, "
                  + "order_coupon_num, order_point, order_update_date, order_item_name, order_take_name "
            */
            sql = "INSERT INTO order1 "
                  + "VALUES (?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,now(),?,?)";

            //pstmt 생성
            pstmt = con.prepareStatement(sql);

            //???값 넣기
            pstmt.setInt(1, order_num);
            pstmt.setString(2, odto.getOrder_member_id());
            
            pstmt.setInt(3, badto.getBasket_item_num());
            pstmt.setInt(4, badto.getBasket_amount()*idto.getItem_price()); //order_price
            pstmt.setInt(5, badto.getBasket_amount()); //
            pstmt.setString(6, badto.getBasket_size()); //
            pstmt.setString(7, badto.getBasket_color()); //

            pstmt.setString(8, odto.getOrder_postcode());
            pstmt.setString(9, odto.getOrder_addr1());
            pstmt.setString(10, odto.getOrder_addr2());
            pstmt.setString(11, odto.getOrder_member_phone());
            pstmt.setString(12, odto.getOrder_phone());
            

            pstmt.setString(13, odto.getOrder_memo());
            //현재 날짜정보 + 주문번호형태 = trade_num ==> 20190923-1
            pstmt.setString(14, sdf.format(cal.getTime()).toString()+"-"+order_trade_num);
            pstmt.setString(15, odto.getOrder_pay_type());
            pstmt.setString(16, odto.getOrder_payer());
            
            pstmt.setInt(17, 2); //주문상태 - 기본값 1
            pstmt.setString(18, ""); //운송장번호 -> 추후 관리자가 다시 입력(수정하는 형태로)
            pstmt.setString(19, odto.getOrder_name());
            
            pstmt.setInt(20, 0); //쿠폰번호
            pstmt.setInt(21, odto.getOrder_point());
            pstmt.setString(22, idto.getItem_name());
            pstmt.setString(23, odto.getOrder_take_name());


            //실행
            pstmt.executeUpdate();
            order_num++; //주문번호 1씩 증가
         }//for 끝

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
   }
   //addOrder(odto,basketList,goodsList)

   public List<OrderDTO> getOrderList(int status){
      List<OrderDTO> orderList = new ArrayList<OrderDTO>();
      try {
         con = getCon();

         sql = "SELECT * FROM order1 WHERE order_status = ? ORDER BY order_trade_date DESC";
         /*sql = "SELECT "
               + "o_trade_num, o_g_name, o_g_amount, o_g_size, o_g_color, "
               + "sum(o_sum_money) as o_sum_money, o_trans_num, "
               + "o_date, o_status, o_trade_type "
               + "FROM order1 "
               + "WHERE o_m_id=? "
               + "GROUP BY o_trade_num "
               + "ORDER BY o_trade_num DESC";*/

         pstmt = con.prepareStatement(sql);
         pstmt.setInt(1, status);

         rs = pstmt.executeQuery();
         while(rs.next()){
            OrderDTO odto = new OrderDTO();

            odto.setOrder_num(rs.getInt("order_num"));
            odto.setOrder_member_id(rs.getString("order_member_id"));
            odto.setOrder_item_num(rs.getInt("order_item_num"));
            odto.setOrder_price(rs.getInt("order_price"));
            odto.setOrder_amount(rs.getInt("order_amount"));
            odto.setOrder_size(rs.getString("order_size"));;
            odto.setOrder_color(rs.getString("order_color"));
            odto.setOrder_date(rs.getDate("order_date"));
            odto.setOrder_addr1(rs.getString("order_addr1"));
            odto.setOrder_addr2(rs.getString("order_addr2"));
            odto.setOrder_member_phone(rs.getString("order_member_phone"));
            odto.setOrder_phone(rs.getString("order_phone"));
            odto.setOrder_memo(rs.getString("order_memo"));
            odto.setOrder_trade_num(rs.getString("order_trade_num"));
            odto.setOrder_trade_date(rs.getDate("order_trade_date"));
            odto.setOrder_pay_type(rs.getString("order_pay_type"));
            odto.setOrder_payer(rs.getString("order_payer"));
            odto.setOrder_status(rs.getInt("order_status"));
            odto.setOrder_trans_num(rs.getString("order_trans_num"));
            odto.setOrder_name(rs.getString("order_name"));
            odto.setOrder_coupon_num(rs.getInt("order_coupon_num"));
            odto.setOrder_point(rs.getInt("order_point"));
            odto.setOrder_update_date(rs.getDate("order_update_date"));
            odto.setOrder_item_name(rs.getString("order_item_name"));
            odto.setOrder_take_name(rs.getString("order_take_name"));


            orderList.add(odto);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      return orderList;
   }
   
   
   //getOrderList(id)
   public List<OrderDTO> getOrderList(String id, int startRow, int pageSize){
      List<OrderDTO> orderList = new ArrayList<OrderDTO>();
      try {
         con = getCon();

         sql = "SELECT * FROM order1 WHERE order_member_id=? ORDER BY order_trade_date DESC limit ?,?";
         /*sql = "SELECT "
               + "o_trade_num, o_g_name, o_g_amount, o_g_size, o_g_color, "
               + "sum(o_sum_money) as o_sum_money, o_trans_num, "
               + "o_date, o_status, o_trade_type "
               + "FROM order1 "
               + "WHERE o_m_id=? "
               + "GROUP BY o_trade_num "
               + "ORDER BY o_trade_num DESC";*/

         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, id);
         pstmt.setInt(2, startRow);
         pstmt.setInt(3, pageSize);

         rs = pstmt.executeQuery();
         while(rs.next()){
            OrderDTO odto = new OrderDTO();

            odto.setOrder_num(rs.getInt("order_num"));
            odto.setOrder_member_id(rs.getString("order_member_id"));
            odto.setOrder_item_num(rs.getInt("order_item_num"));
            odto.setOrder_price(rs.getInt("order_price"));
            odto.setOrder_amount(rs.getInt("order_amount"));
            odto.setOrder_size(rs.getString("order_size"));;
            odto.setOrder_color(rs.getString("order_color"));
            odto.setOrder_date(rs.getDate("order_date"));
            odto.setOrder_postcode(rs.getString("order_postcode"));
            odto.setOrder_addr1(rs.getString("order_addr1"));
            odto.setOrder_addr2(rs.getString("order_addr2"));
            odto.setOrder_member_phone(rs.getString("order_member_phone"));
            odto.setOrder_phone(rs.getString("order_phone"));
            odto.setOrder_memo(rs.getString("order_memo"));
            odto.setOrder_trade_num(rs.getString("order_trade_num"));
            odto.setOrder_trade_date(rs.getDate("order_trade_date"));
            odto.setOrder_pay_type(rs.getString("order_pay_type"));
            odto.setOrder_payer(rs.getString("order_payer"));
            odto.setOrder_status(rs.getInt("order_status"));
            odto.setOrder_trans_num(rs.getString("order_trans_num"));
            odto.setOrder_name(rs.getString("order_name"));
            odto.setOrder_coupon_num(rs.getInt("order_coupon_num"));
            odto.setOrder_point(rs.getInt("order_point"));
            odto.setOrder_update_date(rs.getDate("order_update_date"));
            odto.setOrder_item_name(rs.getString("order_item_name"));
            odto.setOrder_take_name(rs.getString("order_take_name"));


            orderList.add(odto);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      return orderList;
   }
   //getOrderList(id)


   //orderDetail(trade_num)
   public List orderDetail(String trade_num){
      List orderDetailList = new ArrayList();
      try {
         con = getCon();
         sql = "SELECT * FROM order1 WHERE order_trade_num=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, trade_num);

         rs = pstmt.executeQuery();
         while(rs.next()){
            OrderDTO odto = new OrderDTO();


            odto.setOrder_num(rs.getInt("order_num"));
            odto.setOrder_member_id(rs.getString("order_member_id"));
            odto.setOrder_item_num(rs.getInt("order_item_num"));
            odto.setOrder_price(rs.getInt("order_price"));
            odto.setOrder_amount(rs.getInt("order_amount"));
            odto.setOrder_size(rs.getString("order_size"));;
            odto.setOrder_color(rs.getString("order_color"));
            odto.setOrder_date(rs.getDate("order_date"));
            odto.setOrder_postcode(rs.getString("order_postcode"));
            odto.setOrder_addr1(rs.getString("order_addr1"));
            odto.setOrder_addr2(rs.getString("order_addr2"));
            odto.setOrder_member_phone(rs.getString("order_member_phone"));
            odto.setOrder_phone(rs.getString("order_phone"));
            odto.setOrder_memo(rs.getString("order_memo"));
            odto.setOrder_trade_num(rs.getString("order_trade_num"));
            odto.setOrder_trade_date(rs.getDate("order_trade_date"));
            odto.setOrder_pay_type(rs.getString("order_pay_type"));
            odto.setOrder_payer(rs.getString("order_payer"));
            odto.setOrder_status(rs.getInt("order_status"));
            odto.setOrder_trans_num(rs.getString("order_trans_num"));
            odto.setOrder_name(rs.getString("order_name"));
            odto.setOrder_coupon_num(rs.getInt("order_coupon_num"));
            odto.setOrder_point(rs.getInt("order_point"));
            odto.setOrder_update_date(rs.getDate("order_update_date"));
            odto.setOrder_item_name(rs.getString("order_item_name"));
            odto.setOrder_take_name(rs.getString("order_take_name"));

            orderDetailList.add(odto);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      return orderDetailList;
   }
   //orderDetail(trade_num)




   //updateAmount(basketList)
   public void updateAmount(List basketList){
      try {
         con = getCon();
         //수정(update) 상품의 구매 수량만큼 차감(장바구니에 있는)
         //조건 : 해당 상품만 수정
         for(int i=0;i<basketList.size();i++){
            BasketDTO bdto = (BasketDTO)basketList.get(i);
            sql = "UPDATE item SET item_amount=item_amount-?, item_sold=item_sold+? WHERE item_num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bdto.getBasket_amount());
            pstmt.setInt(2, bdto.getBasket_amount());
            pstmt.setInt(3, bdto.getBasket_item_num());
            pstmt.executeUpdate();
         }
         System.out.println("장바구니에 있는 수량만큼(구매수량) 차감 완료!");
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
   }
   //updateAmount(basketList)
//////////////////////////////////////////////여기아래 어드민
   // getOrderCount()
   
   public int getOrderCount(int stat){

      int count = 0;

      try {
         con = getCon();
         
         if(stat == 0){
            sql="select count(*) from order1";
            pstmt = con.prepareStatement(sql);
         }else{
            sql="select count(*) from order1 where order_status = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, stat);
         }
         
         
         rs = pstmt.executeQuery();
         
         
         if(rs.next()){
            count = rs.getInt(1);
         }
         System.out.println("ordercount완료");
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      
      return count;
      
   }
   
   
   public int getOrderCount(String search){

      int count = 0;

      try {
         con = getCon();
         sql = "select count(*) from order1 where order_member_id like ? or "
         + "order_trade_num like ? or order_trans_num like ? or order_size like ? or "
         + "order_color like ? or order_amount like ? or order_date like ? or "
         + "order_postcode like ? or order_addr1 like ? or order_addr2 like ? or "
         + "order_member_phone like ? or order_memo like ? or order_pay_type like ? or "
         + "order_payer like ? or order_item_name like ? or order_take_name like ?";

         pstmt = con.prepareStatement(sql);
         
         pstmt.setString(1, "%"+search+"%");
         pstmt.setString(2, "%"+search+"%");
         pstmt.setString(3, "%"+search+"%");
         pstmt.setString(4, "%"+search+"%");
         pstmt.setString(5, "%"+search+"%");
         pstmt.setString(6, "%"+search+"%");
         pstmt.setString(7, "%"+search+"%");
         pstmt.setString(8, "%"+search+"%");
         pstmt.setString(9, "%"+search+"%");
         pstmt.setString(10, "%"+search+"%");
         pstmt.setString(11, "%"+search+"%");
         pstmt.setString(12, "%"+search+"%");
         pstmt.setString(13, "%"+search+"%");
         pstmt.setString(14, "%"+search+"%");
         pstmt.setString(15, "%"+search+"%");
         pstmt.setString(16, "%"+search+"%");
         
         rs = pstmt.executeQuery();
         
         
         if(rs.next()){
            count = rs.getInt(1);
         }
         System.out.println("ordercount완료");
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      
      return count;
      
   }
   
   
   public int getOrderCount(int stat, String id){

      int count = 0;

      try {
         con = getCon();
         
         if(stat == 0){
            sql="select count(*) from order1 where order_member_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
         }else{
            sql="select count(*) from order1 where order_status = ? and order_member_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, stat);
            pstmt.setString(2, id);
         }
         
         
         rs = pstmt.executeQuery();
         
         
         if(rs.next()){
            count = rs.getInt(1);
         }
         System.out.println("ordercount완료");
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      
      return count;
      
   }

   // getOrderCount()

//getOrderList(startRow, pageSize) 주문내역

public List<OrderDTO> getOrderList(int startRow, int pageSize, int stat){
List<OrderDTO> orderList = new ArrayList<OrderDTO>();

try {
   con = getCon();
   
   if(stat == 0){
      sql = "select * from order1 order by order_date desc limit ?,?";
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, startRow-1);
      pstmt.setInt(2, pageSize);
   }else{
      sql = "select * from order1 where order_status = ? order by order_date desc limit ?,?";
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, stat);
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);
   }
   
   rs = pstmt.executeQuery();
   

   while(rs.next()){
      
      OrderDTO odto = new OrderDTO();
      odto.setOrder_num(rs.getInt("order_num"));
      odto.setOrder_trade_num(rs.getString("order_trade_num"));
      odto.setOrder_member_id(rs.getString("order_member_id"));
      odto.setOrder_name(rs.getString("order_name"));
      odto.setOrder_item_name(rs.getString("order_item_name"));
      odto.setOrder_amount(rs.getInt("order_amount"));
      odto.setOrder_take_name(rs.getString("order_take_name"));
      odto.setOrder_member_phone(rs.getString("order_member_phone"));
      odto.setOrder_phone(rs.getString("order_phone"));
      odto.setOrder_postcode(rs.getString("order_postcode"));
      odto.setOrder_addr1(rs.getString("order_addr1"));
      odto.setOrder_addr2(rs.getString("order_addr2"));
      odto.setOrder_memo(rs.getString("order_memo"));
      odto.setOrder_date(rs.getDate("order_date"));
      odto.setOrder_status(rs.getInt("order_status"));
      odto.setOrder_size(rs.getString("order_size"));
      odto.setOrder_color(rs.getString("order_color"));
      odto.setOrder_status(rs.getInt("order_status"));
      odto.setOrder_trans_num(rs.getString("order_trans_num"));
      // arrayList 한칸에 정보 저장
      orderList.add(odto);
      
   }
   
   System.out.println("주문 목록 저장 완료");
   
} catch (Exception e) {
         e.printStackTrace();
}finally{
   closeDB();
}



return orderList;
}

//getOrderList(startRow, pageSize)

//OrderSearch(search) 주문내역 검색
	public List<OrderDTO> OrderSearch(String search_column, String stat, String search, int startRow, int pageSize){
		
		List<OrderDTO> ordersearchList = new ArrayList<OrderDTO>();
		
		try {
			
			System.out.println(search);
			System.out.println(search_column);
			System.out.println(stat);
			
			con = getCon();
			
		if(search_column.equals("order_member_id")){
				
			sql = "select * from order1 where order_status=? and order_member_id like ? limit ?,? ";

			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, stat);
			pstmt.setString(2, "%"+search+"%");
			pstmt.setInt(3, startRow-1);
			pstmt.setInt(4, pageSize);

			rs = pstmt.executeQuery();

			}else if(search_column.equals("order_name")){
				
				sql = "select * from order1 where order_status=? and order_name like ? limit ?,? ";
				
			pstmt = con.prepareStatement(sql);
				
			pstmt.setString(1, stat);
			pstmt.setString(2, "%"+search+"%");
			pstmt.setInt(3, startRow-1);
			pstmt.setInt(4, pageSize);

			rs = pstmt.executeQuery();
				
			}else if(search_column.equals("order_addr1")){
				
				sql = "select * from order1 where order_status=? and order_addr1 like ? limit ?,? ";
				
			pstmt = con.prepareStatement(sql);
				
			pstmt.setString(1, stat);
			pstmt.setString(2, "%"+search+"%");
			pstmt.setInt(3, startRow-1);
			pstmt.setInt(4, pageSize);

			rs = pstmt.executeQuery();
			}
			

			while(rs.next()){
				
				OrderDTO odto = new OrderDTO();
				odto.setOrder_trade_num(rs.getString("order_trade_num"));
				odto.setOrder_trans_num(rs.getString("order_trans_num"));
				odto.setOrder_member_id(rs.getString("order_member_id"));
				odto.setOrder_name(rs.getString("order_name"));
				odto.setOrder_item_name(rs.getString("order_item_name"));
				odto.setOrder_amount(rs.getInt("order_amount"));
				odto.setOrder_take_name(rs.getString("order_take_name"));
				odto.setOrder_member_phone(rs.getString("order_member_phone"));
				odto.setOrder_phone(rs.getString("order_phone"));
				odto.setOrder_postcode(rs.getString("order_postcode"));
				odto.setOrder_addr1(rs.getString("order_addr1"));
				odto.setOrder_addr2(rs.getString("order_addr2"));
				odto.setOrder_memo(rs.getString("order_memo"));
				odto.setOrder_date(rs.getDate("order_date"));
				odto.setOrder_status(rs.getInt("order_status"));
				odto.setOrder_size(rs.getString("order_size"));
				odto.setOrder_color(rs.getString("order_color"));
				odto.setOrder_status(rs.getInt("order_status"));
				// arrayList 한칸에 정보 저장
				ordersearchList.add(odto);
				
			}
		} catch (Exception e) {

			e.printStackTrace();
		}finally{
			closeDB();
		}
		
		
		return ordersearchList;
	}
	//OrderSearch(search)

//getOrderReadyList(int startRow, int pageSize) 상품준비 리스트

public List<OrderDTO> getOrderReadyList(int startRow, int pageSize){
List<OrderDTO> orderreadyList = new ArrayList<OrderDTO>();

try {
   con = getCon();
   
   sql = "select * from order1 where order_status=? order by order_date asc limit ?,?";
   
   pstmt = con.prepareStatement(sql);
   
   pstmt.setInt(1, 2);
   pstmt.setInt(2, startRow-1);
   pstmt.setInt(3, pageSize);
   
   rs = pstmt.executeQuery();
   

   while(rs.next()){
      
      OrderDTO odto = new OrderDTO();
      odto.setOrder_trans_num(rs.getString("order_trans_num"));
      odto.setOrder_member_id(rs.getString("order_member_id"));
      odto.setOrder_name(rs.getString("order_name"));
      odto.setOrder_item_name(rs.getString("order_item_name"));
      odto.setOrder_amount(rs.getInt("order_amount"));
      odto.setOrder_take(rs.getString("order_take"));
      odto.setOrder_member_phone(rs.getString("order_member_phone"));
      odto.setOrder_phone(rs.getString("order_phone"));
      odto.setOrder_postcode(rs.getString("order_postcode"));
      odto.setOrder_addr1(rs.getString("order_addr1"));
      odto.setOrder_addr2(rs.getString("order_addr2"));
      odto.setOrder_memo(rs.getString("order_memo"));
      odto.setOrder_date(rs.getDate("order_date"));
      odto.setOrder_status(rs.getInt("order_status"));
      odto.setOrder_size(rs.getString("order_size"));
      odto.setOrder_color(rs.getString("order_color"));
      odto.setOrder_status(rs.getInt("order_status"));
      // arrayList 한칸에 정보 저장
      orderreadyList.add(odto);
      
   }
   
   System.out.println("주문 목록 저장 완료");
   
} catch (Exception e) {
         e.printStackTrace();
}finally{
   closeDB();
}



return orderreadyList;
}

//getOrderList(startRow, pageSize)

//OrderSearch(search) 주문내역 검색
public List<OrderDTO> ReadySearch(String search_column, String search, int startRow, int pageSize){

List<OrderDTO> readysearchList = new ArrayList<OrderDTO>();

try {
   
   System.out.println(search);
   
   con = getCon();
   
if(search_column.equals("order_member_id")){
      
   sql = "select * from order1 where order_status=2 and order_member_id like ? limit ?,? ";

   pstmt = con.prepareStatement(sql);
      
   pstmt.setString(1, "%"+search+"%");
   pstmt.setInt(2, startRow-1);
   pstmt.setInt(3, pageSize);

   rs = pstmt.executeQuery();

   }else if(search_column.equals("order_name")){
      
      sql = "select * from order1 where order_status=2 and order_name like ? limit ?,? ";
      
   pstmt = con.prepareStatement(sql);
      
   pstmt.setString(1, "%"+search+"%");
   pstmt.setInt(2, startRow-1);
   pstmt.setInt(3, pageSize);

   rs = pstmt.executeQuery();
      
   }else if(search_column.equals("order_addr1")){
      
      sql = "select * from order1 where order_status=2 and order_addr1 like ? limit ?,? ";
      
   pstmt = con.prepareStatement(sql);
      
   pstmt.setString(1, "%"+search+"%");
   pstmt.setInt(2, startRow-1);
   pstmt.setInt(3, pageSize);

   rs = pstmt.executeQuery();
   }
   

   while(rs.next()){
      
      OrderDTO odto = new OrderDTO();
      odto.setOrder_trans_num(rs.getString("order_trans_num"));
      odto.setOrder_member_id(rs.getString("order_member_id"));
      odto.setOrder_name(rs.getString("order_name"));
      odto.setOrder_item_name(rs.getString("order_item_name"));
      odto.setOrder_amount(rs.getInt("order_amount"));
      odto.setOrder_take(rs.getString("order_take"));
      odto.setOrder_member_phone(rs.getString("order_member_phone"));
      odto.setOrder_phone(rs.getString("order_phone"));
      odto.setOrder_postcode(rs.getString("order_postcode"));
      odto.setOrder_addr1(rs.getString("order_addr1"));
      odto.setOrder_addr2(rs.getString("order_addr2"));
      odto.setOrder_memo(rs.getString("order_memo"));
      odto.setOrder_date(rs.getDate("order_date"));
      odto.setOrder_status(rs.getInt("order_status"));
      odto.setOrder_size(rs.getString("order_size"));
      odto.setOrder_color(rs.getString("order_color"));
      odto.setOrder_status(rs.getInt("order_status"));
      // arrayList 한칸에 정보 저장
      readysearchList.add(odto);
      
   }
} catch (Exception e) {

   e.printStackTrace();
}finally{
   closeDB();
}


return readysearchList;
}
//OrderSearch(search)


//ordertransList(startRow, pageSize) 배송중 상품 리스트

public List<OrderDTO> ordertransList(int startRow, int pageSize){
List<OrderDTO> ordertransList = new ArrayList<OrderDTO>();

try {
   con = getCon();
   
   sql = "select * from order1 where order_status=? order by order_date asc limit ?,?";
   
   pstmt = con.prepareStatement(sql);
   
   pstmt.setInt(1, 3);
   pstmt.setInt(2, startRow-1);
   pstmt.setInt(3, pageSize);
   
   rs = pstmt.executeQuery();
   

   while(rs.next()){
      
      OrderDTO odto = new OrderDTO();
      odto.setOrder_trans_num(rs.getString("order_trans_num"));
      odto.setOrder_member_id(rs.getString("order_member_id"));
      odto.setOrder_name(rs.getString("order_name"));
      odto.setOrder_item_name(rs.getString("order_item_name"));
      odto.setOrder_amount(rs.getInt("order_amount"));
      odto.setOrder_take(rs.getString("order_take"));
      odto.setOrder_member_phone(rs.getString("order_member_phone"));
      odto.setOrder_phone(rs.getString("order_phone"));
      odto.setOrder_postcode(rs.getString("order_postcode"));
      odto.setOrder_addr1(rs.getString("order_addr1"));
      odto.setOrder_addr2(rs.getString("order_addr2"));
      odto.setOrder_memo(rs.getString("order_memo"));
      odto.setOrder_date(rs.getDate("order_date"));
      odto.setOrder_status(rs.getInt("order_status"));
      odto.setOrder_size(rs.getString("order_size"));
      odto.setOrder_color(rs.getString("order_color"));
      odto.setOrder_status(rs.getInt("order_status"));
      // arrayList 한칸에 정보 저장
      ordertransList.add(odto);
      
   }
   
   System.out.println("주문 목록 저장 완료");
   
} catch (Exception e) {
         e.printStackTrace();
}finally{
   closeDB();
}



return ordertransList;
}

//ordertransList(startRow, pageSize)

//TransSearch(search) 배송중 상품내역 검색
public List<OrderDTO> TransSearch(String search_column, String search, int startRow, int pageSize){
   
   List<OrderDTO> transsearchList = new ArrayList<OrderDTO>();
   
   try {
      
      System.out.println(search);
      
      con = getCon();
      
   if(search_column.equals("order_member_id")){
         
      sql = "select * from order1 where order_status=3 and order_member_id like ? limit ?,? ";

      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();

      }else if(search_column.equals("order_name")){
         
         sql = "select * from order1 where order_status=3 and order_name like ? limit ?,? ";
         
      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();
         
      }else if(search_column.equals("order_addr1")){
         
         sql = "select * from order1 where order_status=3 and order_addr1 like ? limit ?,? ";
         
      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();
      }
      

      while(rs.next()){
         
         OrderDTO odto = new OrderDTO();
         odto.setOrder_trans_num(rs.getString("order_trans_num"));
         odto.setOrder_member_id(rs.getString("order_member_id"));
         odto.setOrder_name(rs.getString("order_name"));
         odto.setOrder_item_name(rs.getString("order_item_name"));
         odto.setOrder_amount(rs.getInt("order_amount"));
         odto.setOrder_take(rs.getString("order_take"));
         odto.setOrder_member_phone(rs.getString("order_member_phone"));
         odto.setOrder_phone(rs.getString("order_phone"));
         odto.setOrder_postcode(rs.getString("order_postcode"));
         odto.setOrder_addr1(rs.getString("order_addr1"));
         odto.setOrder_addr2(rs.getString("order_addr2"));
         odto.setOrder_memo(rs.getString("order_memo"));
         odto.setOrder_date(rs.getDate("order_date"));
         odto.setOrder_status(rs.getInt("order_status"));
         odto.setOrder_size(rs.getString("order_size"));
         odto.setOrder_color(rs.getString("order_color"));
         odto.setOrder_status(rs.getInt("order_status"));
         // arrayList 한칸에 정보 저장
         transsearchList.add(odto);
         
      }
   } catch (Exception e) {

      e.printStackTrace();
   }finally{
      closeDB();
   }
   
   
   return transsearchList;
}
//TransSearch(search) 배송중 상품내역 검색

//orderwaitingList(startRow, pageSize) 구매대기 상품리스트

public List<OrderDTO> orderwaitingList(int startRow, int pageSize){
   List<OrderDTO> orderwaitingList = new ArrayList<OrderDTO>();
   
   try {
      con = getCon();
      
      sql = "select * from order1 where order_status=? order by order_date asc limit ?,?";
      
      pstmt = con.prepareStatement(sql);
      
      pstmt.setInt(1, 4);
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);
      
      rs = pstmt.executeQuery();
      

      while(rs.next()){
         
         OrderDTO odto = new OrderDTO();
         odto.setOrder_trans_num(rs.getString("order_trans_num"));
         odto.setOrder_member_id(rs.getString("order_member_id"));
         odto.setOrder_name(rs.getString("order_name"));
         odto.setOrder_item_name(rs.getString("order_item_name"));
         odto.setOrder_amount(rs.getInt("order_amount"));
         odto.setOrder_take(rs.getString("order_take"));
         odto.setOrder_member_phone(rs.getString("order_member_phone"));
         odto.setOrder_phone(rs.getString("order_phone"));
         odto.setOrder_postcode(rs.getString("order_postcode"));
         odto.setOrder_addr1(rs.getString("order_addr1"));
         odto.setOrder_addr2(rs.getString("order_addr2"));
         odto.setOrder_memo(rs.getString("order_memo"));
         odto.setOrder_date(rs.getDate("order_date"));
         odto.setOrder_status(rs.getInt("order_status"));
         odto.setOrder_size(rs.getString("order_size"));
         odto.setOrder_color(rs.getString("order_color"));
         odto.setOrder_status(rs.getInt("order_status"));
         // arrayList 한칸에 정보 저장
         orderwaitingList.add(odto);
         
      }
      
      System.out.println("주문 목록 저장 완료");
      
   } catch (Exception e) {
            e.printStackTrace();
   }finally{
      closeDB();
   }
   
   
   
   return orderwaitingList;
}

//orderwaitingList(startRow, pageSize)


//WaitingSearch(search) 구매대기 상품내역 검색
public List<OrderDTO> WaitingSearch(String search_column, String search, int startRow, int pageSize){
   
   List<OrderDTO> waitingsearchList = new ArrayList<OrderDTO>();
   
   try {
      
      System.out.println(search);
      
      con = getCon();
      
   if(search_column.equals("order_member_id")){
         
      sql = "select * from order1 where order_status=4 and order_member_id like ? limit ?,? ";

      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();

      }else if(search_column.equals("order_name")){
         
         sql = "select * from order1 where order_status=4 and order_name like ? limit ?,? ";
         
      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();
         
      }else if(search_column.equals("order_addr1")){
         
         sql = "select * from order1 where order_status=4 and order_addr1 like ? limit ?,? ";
         
      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();
      }
      

      while(rs.next()){
         
         OrderDTO odto = new OrderDTO();
         odto.setOrder_trans_num(rs.getString("order_trans_num"));
         odto.setOrder_member_id(rs.getString("order_member_id"));
         odto.setOrder_name(rs.getString("order_name"));
         odto.setOrder_item_name(rs.getString("order_item_name"));
         odto.setOrder_amount(rs.getInt("order_amount"));
         odto.setOrder_take(rs.getString("order_take"));
         odto.setOrder_member_phone(rs.getString("order_member_phone"));
         odto.setOrder_phone(rs.getString("order_phone"));
         odto.setOrder_postcode(rs.getString("order_postcode"));
         odto.setOrder_addr1(rs.getString("order_addr1"));
         odto.setOrder_addr2(rs.getString("order_addr2"));
         odto.setOrder_memo(rs.getString("order_memo"));
         odto.setOrder_date(rs.getDate("order_date"));
         odto.setOrder_status(rs.getInt("order_status"));
         odto.setOrder_size(rs.getString("order_size"));
         odto.setOrder_color(rs.getString("order_color"));
         odto.setOrder_status(rs.getInt("order_status"));
         // arrayList 한칸에 정보 저장
         waitingsearchList.add(odto);
         
      }
   } catch (Exception e) {

      e.printStackTrace();
   }finally{
      closeDB();
   }
   
   
   return waitingsearchList;
}
//WaitingSearch(search) 구매대기 상품내역 검색


//ordercompleteList(startRow, pageSize) 구매완료 리스트

public List<OrderDTO> ordercompleteList(int startRow, int pageSize){
   List<OrderDTO> ordercompleteList = new ArrayList<OrderDTO>();
   
   try {
      con = getCon();
      
      sql = "select * from order1 where order_status=? order by order_date asc limit ?,?";
      
      pstmt = con.prepareStatement(sql);
      
      pstmt.setInt(1, 5);
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);
      
      rs = pstmt.executeQuery();
      

      while(rs.next()){
         
         OrderDTO odto = new OrderDTO();
         odto.setOrder_trans_num(rs.getString("order_trans_num"));
         odto.setOrder_member_id(rs.getString("order_member_id"));
         odto.setOrder_name(rs.getString("order_name"));
         odto.setOrder_item_name(rs.getString("order_item_name"));
         odto.setOrder_amount(rs.getInt("order_amount"));
         odto.setOrder_take(rs.getString("order_take"));
         odto.setOrder_member_phone(rs.getString("order_member_phone"));
         odto.setOrder_phone(rs.getString("order_phone"));
         odto.setOrder_postcode(rs.getString("order_postcode"));
         odto.setOrder_addr1(rs.getString("order_addr1"));
         odto.setOrder_addr2(rs.getString("order_addr2"));
         odto.setOrder_memo(rs.getString("order_memo"));
         odto.setOrder_date(rs.getDate("order_date"));
         odto.setOrder_status(rs.getInt("order_status"));
         odto.setOrder_size(rs.getString("order_size"));
         odto.setOrder_color(rs.getString("order_color"));
         odto.setOrder_status(rs.getInt("order_status"));
         // arrayList 한칸에 정보 저장
         ordercompleteList.add(odto);
         
      }
      
      System.out.println("주문 목록 저장 완료");
      
   } catch (Exception e) {
            e.printStackTrace();
   }finally{
      closeDB();
   }
   
   
   
   return ordercompleteList;
}

//orderwaitingList(startRow, pageSize)

//CompleteSearch(search) 구매완료 리스트 검색
public List<OrderDTO> CompleteSearch(String search_column, String search, int startRow, int pageSize){
   
   List<OrderDTO> completesearchList = new ArrayList<OrderDTO>();
   
   try {
      
      System.out.println(search);
      
      con = getCon();
      
   if(search_column.equals("order_member_id")){
         
      sql = "select * from order1 where order_status=5 and order_member_id like ? limit ?,? ";

      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();

      }else if(search_column.equals("order_name")){
         
         sql = "select * from order1 where order_status=5 and order_name like ? limit ?,? ";
         
      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();
         
      }else if(search_column.equals("order_addr1")){
         
         sql = "select * from order1 where order_status=5 and order_addr1 like ? limit ?,? ";
         
      pstmt = con.prepareStatement(sql);
         
      pstmt.setString(1, "%"+search+"%");
      pstmt.setInt(2, startRow-1);
      pstmt.setInt(3, pageSize);

      rs = pstmt.executeQuery();
      }
      

      while(rs.next()){
         
         OrderDTO odto = new OrderDTO();
         odto.setOrder_trans_num(rs.getString("order_trans_num"));
         odto.setOrder_member_id(rs.getString("order_member_id"));
         odto.setOrder_name(rs.getString("order_name"));
         odto.setOrder_item_name(rs.getString("order_item_name"));
         odto.setOrder_amount(rs.getInt("order_amount"));
         odto.setOrder_take(rs.getString("order_take"));
         odto.setOrder_member_phone(rs.getString("order_member_phone"));
         odto.setOrder_phone(rs.getString("order_phone"));
         odto.setOrder_postcode(rs.getString("order_postcode"));
         odto.setOrder_addr1(rs.getString("order_addr1"));
         odto.setOrder_addr2(rs.getString("order_addr2"));
         odto.setOrder_memo(rs.getString("order_memo"));
         odto.setOrder_date(rs.getDate("order_date"));
         odto.setOrder_status(rs.getInt("order_status"));
         odto.setOrder_size(rs.getString("order_size"));
         odto.setOrder_color(rs.getString("order_color"));
         odto.setOrder_status(rs.getInt("order_status"));
         // arrayList 한칸에 정보 저장
         completesearchList.add(odto);
         
      }
   } catch (Exception e) {

      e.printStackTrace();
   }finally{
      closeDB();
   }
   
   
   return completesearchList;
}
//CompleteSearch(search) 구매완료 리스트 검색

//OrderStatusUpdate(String order_num, int order_stat)

public void OrderStatusUpdate(String order_num, int order_stat){
   
   try {
      con = getCon();
      sql = "update order1 set order_status = ? where order_trade_num = ?";
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, order_stat);
      pstmt.setString(2, order_num);
      pstmt.executeUpdate();
      
      if(order_stat == 6){
    	  
      }
      
      System.out.println("주문상태 변경완료");
   } catch (Exception e) {
      e.printStackTrace();
   } finally {
      closeDB();
   }
   
}

//OrderStatusUpdate(String order_num, int order_stat)   
   

//OrderTransNumUpdate(String order_num, String order_trans_num)

public void OrderTransNumUpdate(String order_num, String order_trans_num){
   
   try {
      con = getCon();
      sql = "update order1 set order_trans_num = ? where order_trade_num = ?";
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, order_trans_num);
      pstmt.setString(2, order_num);
      pstmt.executeUpdate();
      
      System.out.println("운송장번호 등록 완료");
   } catch (Exception e) {
      e.printStackTrace();
   } finally {
      closeDB();
   }
}

//OrderTransNumUpdate(String order_num, String order_trans_num)
   

//updateCancle(String order_trade_num, String order_memo)

public void updateCancle(String order_trade_num, String order_memo){
   try {
      con = getCon();
      sql = "update order1 set order_memo = ?, order_status = ? where order_trade_num = ?";
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, order_memo);
      pstmt.setInt(2, 14);
      pstmt.setString(3, order_trade_num);
      pstmt.executeUpdate();
      
      System.out.println("취소/반품 신청 완료");
      
   } catch (Exception e) {
      e.printStackTrace();
   } finally {
      closeDB();
   }
}

//updateCancle(String order_trade_num, String order_memo)
   
}