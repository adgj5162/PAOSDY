package admin.sales.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.item.db.ItemDTO;
import com.order.db.OrderDTO;

public class SalesDAO {
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


   // getItem()
   public List getItemNum(){
      List itemList = new ArrayList();
      
      try {
         con = getCon();
         sql = "select * from item";
         pstmt = con.prepareStatement(sql);
         
         rs = pstmt.executeQuery();
         
         while(rs.next()){
            ItemDTO idto = new ItemDTO();
            idto.setItem_num(rs.getInt("item_num"));
            idto.setItem_color(rs.getString("item_color"));
            idto.setItem_size(rs.getString("item_size"));
            idto.setItem_name(rs.getString("item_name"));
            
            itemList.add(idto);
         }
         
         System.out.println("아이템 정보 가져오기");
      
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         closeDB();
      }
      return itemList;
   }
   // getItem()
   
   

   //getOrderAmoPri(ItemDTO idto) 주문내역

   public Vector getOrderAmoPri(ItemDTO idto,String sdate,String edate){
      Vector svec = new Vector();
      
      int[] sumAmoPri = new int[2];
      OrderDTO odto = new OrderDTO();
   

      
      try {
      
      con = getCon();
      
         sql = "select sum(order_amount),sum(order_price),order_item_num,order_size,order_color,order_item_name "
               + "from order1 "
               + "where order_status=6 and order_item_num=? and order_size=? and order_color=? and order_date between ? and ? "
               + "group by order_item_num,order_size,order_color,order_item_name";
         pstmt = con.prepareStatement(sql);
         pstmt.setInt(1, idto.getItem_num());
         pstmt.setString(2, idto.getItem_size());
         pstmt.setString(3, idto.getItem_color());
         pstmt.setString(4, sdate);
         pstmt.setString(5, edate);
      rs = pstmt.executeQuery();
      

      if(rs.next()){
         System.out.println("값있을때");
          sumAmoPri[0] = rs.getInt("sum(order_amount)");
         sumAmoPri[1] = rs.getInt("sum(order_price)");         
         
         odto.setOrder_item_num(rs.getInt("order_item_num"));
         odto.setOrder_size(rs.getString("order_size"));
         odto.setOrder_color(rs.getString("order_color"));
         odto.setOrder_item_name(rs.getString("order_item_name"));
                  
         svec.add(sumAmoPri);
         svec.add(odto);
               
      }else{
         System.out.println("값없을때");
         sumAmoPri[0] = 0;
         sumAmoPri[1] = 0;         
         
         odto.setOrder_item_num(idto.getItem_num());
         odto.setOrder_size(idto.getItem_size());
         odto.setOrder_color(idto.getItem_color());
         odto.setOrder_item_name(idto.getItem_name());
                  
         svec.add(sumAmoPri);
         svec.add(odto);
      }
      
      System.out.println("상품수량 들고오기");
      
   } catch (Exception e) {
            e.printStackTrace();
   }finally{
      closeDB();
   }



   return svec;
   }

   //getOrderAmoPri(ItemDTO idto)
   public List<Vector> getSearchOrderAmoPri(ItemDTO idto,String column,String sdate,String edate){
      
      System.out.println("@@@@@@@@@@@시작");
      
      List<Vector> salesList = new ArrayList();
      
      
      
   
      try {
      
      con = getCon();
      
         sql = "select sum(order_amount),sum(order_price),order_item_num,order_size,order_color,order_item_name "
               + "from order1 ";
               
         System.out.println("@@@@@@@@@@@시작"+idto.getItem_name());
        
         switch (column) {
         case "order_item_name":         
         sql += "where order_status=6 and order_item_name=? and order_date between ? and ? ";      
         break;      
        
         case "order_color":
         sql += "where order_status=6 and order_color=? and order_date between ? and ? ";   
         break;
        
         case "order_size":
         sql += "where order_status=6 and order_size=? and order_date between ? and ? ";   
         break;        
         }
         
         sql += "group by order_item_num,order_size,order_color,order_item_name";
       
         
         pstmt = con.prepareStatement(sql);
        
         System.out.println("들고온 칼럼값"+column);
         
         switch (column) {
         case "order_item_name":
            pstmt.setString(1, idto.getItem_name());
            
            break;
         case "order_color":
            pstmt.setString(1, idto.getItem_color());
            break;
         case "order_size":
            pstmt.setString(1, idto.getItem_size());
            System.out.println("템사이즈"+idto.getItem_size());
            break;
         }
         pstmt.setString(2, sdate);
         pstmt.setString(3, edate);
         
         rs = pstmt.executeQuery();
         
        
      
      
      while(rs.next()){
         System.out.println(rs.getString("order_item_name"));
         
         Vector svec = new Vector();
         
         System.out.println("값있을때");
         int[] sumAmoPri = new int[2];
         
         sumAmoPri[0] = rs.getInt("sum(order_amount)");
         sumAmoPri[1] = rs.getInt("sum(order_price)");         
         
         OrderDTO odto = new OrderDTO();
         
         odto.setOrder_item_num(rs.getInt("order_item_num"));
         odto.setOrder_size(rs.getString("order_size"));
         odto.setOrder_color(rs.getString("order_color"));
         odto.setOrder_item_name(rs.getString("order_item_name"));
                  
         svec.add(sumAmoPri);
         svec.add(odto);
         
         salesList.add(svec);
         
         
         

         for(int i=0;i<salesList.size();i++){
            svec = salesList.get(i);
//            
            int[] salesSum = (int[])svec.get(0);
//            
//            System.out.println(salesSum[0]);
//            System.out.println(salesSum[1]);
//            
//            odto = (OrderDTO)svec.get(1);
            System.out.println(odto.getOrder_item_num());
//            System.out.println(odto.getOrder_item_name());
//            System.out.println(odto.getOrder_color());
//            System.out.println(odto.getOrder_size());
         }
         
      }  
         
         
    
     
   
      
      
      
      
      System.out.println("상품수량 들고오기");
      
   } catch (Exception e) {
            e.printStackTrace();
   }finally{
      closeDB();
   }



   return salesList;
   }

   
   //getSearchList()
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   

}