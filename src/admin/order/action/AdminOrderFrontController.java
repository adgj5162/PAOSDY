package admin.order.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.order.action.ListExcellSaveAction;








public class AdminOrderFrontController extends HttpServlet{
   protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 가상주소 가져와서 처리
      String requestURI = request.getRequestURI();
      System.out.println("requestURI : "+requestURI);
      String contextPath = request.getContextPath();
      System.out.println("contextPath : "+contextPath);
      String command = requestURI.substring(contextPath.length());
      System.out.println("command : "+command);
      ActionForward forward = null;
      Action action = null;
      // 가상주소 비교
      
      if(command.equals("/AdminOrderList.ador")){ 
      
         // 주문 목록 가져오기
         action = new AdminOrderListAction();
         try {
         forward = action.execute(request, response);
         } catch (Exception e) {
         e.printStackTrace();            
         
         }
   
      }else if(command.equals("/Listexcell.ador")){
         
         action = new ListExcellSaveAction();
         
         try {
               forward = action.execute(request, response);
         } catch (Exception e) {
            e.printStackTrace();
         }
         
      }

      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      if(forward != null){
         if(forward.isRedirect()){
            response.sendRedirect(forward.getPath());
         }else{
            RequestDispatcher dis = 
                  request.getRequestDispatcher(forward.getPath());
            dis.forward(request, response);
         }
      }
   }
   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProcess(request, response);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProcess(request, response);
   }
}