package admin.sales.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.db.ItemDTO;
import com.order.db.OrderDTO;

import admin.sales.db.SalesDAO;

public class AdminSalesListAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      System.out.println("AskSalesListAction_execute()**********");
      request.setCharacterEncoding("UTF-8");
      
      System.out.println("AskSalesListAction_execute()**********");
      ActionForward forward = new ActionForward();
      
      // DB에서 값 가져오기
      // 어디에 담냐? 객체 뭐쓰냐? OrderDTO
      // 쓸값? 가져올값? item에서 상품명,color,size들고와야댐
      // orderDTO에서 item이랑 같은게 item_num임
      // 근데 이게 상품명 같고 color 다르고 size다르면 item_num이 다름.
      // 분류는 색상, 사이즈로 해줘야댐
      // 일단 rs로 아이템에거 다 가져와도 되긴 할듯
      
      // item넘버 에 해당하는 오더가 들어가야되
      
      // 가져온게 넘버 이름 총수량 가격 색깔 사이즈 
      
      
      
      SalesDAO sdao = new SalesDAO();
      ItemDTO idto = new ItemDTO();
      
      List itemList = sdao.getItemNum();
      
      Vector svec = new Vector();
      
      List<Vector> salesList = new ArrayList();
      // 전체 아이템리스트 가져온거
      // 여기서 idto로 나눠서 Vector 가져오는걸로 넣어 줘야댐.
      
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

      Calendar c1 = Calendar.getInstance();

      String strToday = sdf.format(c1.getTime());
      
      System.out.println("@@@@ strToday"+strToday);
      
      String sdate=(String)request.getParameter("sdate");
      String edate=(String)request.getParameter("edate");
         
      String column = (String)request.getParameter("column");
      String svalue = (String)request.getParameter("svalue");
      
      System.out.println("@@"+sdate);
      if(sdate==null){
         sdate=strToday;
         edate=strToday;
      }
      
      System.out.println("@@@시작날짜 : "+sdate);
      System.out.println("@@@"+column);
      System.out.println("@@@"+svalue);
      
      if(svalue==null){
         svalue="";
      }
      
      if(svalue!=""){
         
         switch (column) {
         case "order_item_name":
            idto.setItem_name(svalue);
            break;
         case "order_color":
            idto.setItem_color(svalue);
            break;
         case "order_size":
            idto.setItem_size(svalue);
            break;
         }
         System.out.println("들고온 칼럼값"+column);
         salesList = sdao.getSearchOrderAmoPri(idto, column,sdate,edate);
         
/*         for(int i=0;i<salesList.size();i++){
            svec = salesList.get(i);
            
            int[] salesSum = (int[])svec.get(0);
            
            System.out.println(salesSum[0]);
            System.out.println(salesSum[1]);
            
            OrderDTO odto = (OrderDTO)svec.get(1);
            System.out.println(odto.getOrder_item_num());
            System.out.println(odto.getOrder_item_name());
            System.out.println(odto.getOrder_color());
            System.out.println(odto.getOrder_size());
         }
*/         
         
         
         
         
         
         
         
         
      }else{   


      
      for(int i=0;i<itemList.size();i++){
         
         idto = (ItemDTO)itemList.get(i);
         
         String color = idto.getItem_color();
         String size = idto.getItem_size();
         String[] colorarr = color.split("");
         String[] sizearr = size.split("");
         List colorList = new ArrayList();
         List sizeList = new ArrayList();
         int colorcnt = 0;
         int sizecnt = 0;
         String divi = "/";
         
         // color구분하기
         if(color.contains(divi)){
                        
            for(int j=0;j<colorarr.length;j++){               
               if(divi.equals(colorarr[j])){colorcnt++;}
            }
            
            for(int k=0;k<colorcnt;k++){
               colorList = Arrays.asList(color.split(divi));
            }
            
         }else{
            System.out.println("color 하나일때");
            colorList.add(color);
         }
         
         // color구분하기
         
         // size구분하기
         if(size.contains(divi)){
                        
            for(int j=0;j<sizearr.length;j++){               
               if(divi.equals(sizearr[j])){sizecnt++;}
            }
            
            for(int k=0;k<sizecnt;k++){
               sizeList = Arrays.asList(size.split(divi));               
            }
            
         }else{
            System.out.println("size 하나일때");
            sizeList.add(size);
         }
         
         // size구분하기
         
         // color당 size구해서 디비에서 가져오기
         for(int a=0;a<colorList.size();a++){
         idto.setItem_color((String)colorList.get(a));         
            
            for(int b=0;b<sizeList.size();b++){
               idto.setItem_size((String)sizeList.get(b));
                              
               svec = sdao.getOrderAmoPri(idto,sdate,edate);//하나의 vec 가져오기
               
               salesList.add(svec);//총판매 리스트에 백터값(하나의 값 담기)
            }
            

         }
         // color당 size구해서 디비에서 가져오기
                  
      }
      }


   /*   for(int i=0;i<salesList.size();i++){
         svec = salesList.get(i);
         
         int[] salesSum = (int[])svec.get(0);
         
         System.out.println(salesSum[0]);
         System.out.println(salesSum[1]);
         
         OrderDTO odto = (OrderDTO)svec.get(1);
         System.out.println(odto.getOrder_item_num());
         System.out.println(odto.getOrder_item_name());
         System.out.println(odto.getOrder_color());
         System.out.println(odto.getOrder_size());
      }
   */   
      
      
      
      
      
      request.setAttribute("salesList", salesList);
      request.setAttribute("sdate", sdate);
      request.setAttribute("edate", edate);

         forward.setPath("/admin/admin_sales_list.jsp");   
         
         forward.setRedirect(false);
         return forward;

   


      
      
   }

}