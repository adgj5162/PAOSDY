<%@page import="com.order.db.OrderDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

</head>
<body>
<%
   request.setCharacterEncoding("UTF-8");
%>
<script type="text/javascript">

   function goExcel(){
   
      if($('#excelStat option:selected').val() == "주문완료") { location.href="./Listexcell.ador?stat=2";}
      if($('#excelStat option:selected').val() == "상품준비") { location.href="./Listexcell.ador?stat=3";}
      if($('#excelStat option:selected').val() == "배송중") { location.href="./Listexcell.ador?stat=4";}
      if($('#excelStat option:selected').val() == "배송완료") { location.href="./Listexcell.ador?stat=5";}
      if($('#excelStat option:selected').val() == "구매확정") { location.href="./Listexcell.ador?stat=6";}
      if($('#excelStat option:selected').val() == "취소/반품") { location.href="./Listexcell.ador?stat=15";}
   }
   
   
   function order_stat_select(order_num,i,order_status){
      console.log(order_num);
            var order_stat;
            var confirm_stat;
      
         if($('#order_stat_select'+i+' option:selected').val() == "상품준비"){
            order_stat = 3;
            confirm_stat = confirm("상품준비 상태로 변경하시겠습니까?");
            if(order_status == 3){
               alert("이미 상품준비 상태입니다");
               confirm_stat = false;
            }
         }else if($('#order_stat_select'+i+' option:selected').val() == "배송중"){
            order_stat = 4;
            confirm_stat = confirm("배송중 상태로 변경하시겠습니까?");
            
            if(order_status == 4){
               alert("이미 배송중 상태입니다");
               confirm_stat = false;
            }
            
            if(confirm_stat){
               var order_trans_num = prompt("운송장 번호를 입력하세요");
               if(order_trans_num != 0){
               $.ajax({
                  url : "./OrderTransNumUpdate",
                  type : "post",
                  data : {
                     order_num : order_num,
                     order_trans_num : order_trans_num
                  },
                  error : function(error) {
                     alert("error 관리자에게 문의하세요~^^");
                  }
               });
               }else{
            	   alert("운송장 번호를 입력하지 않으셨습니다");
            	   confirm_stat = false;
               }
               
               
            }
         }else if($('#order_stat_select'+i+' option:selected').val() == "배송완료"){
            order_stat = 5;
            confirm_stat = confirm("배송완료 상태로 변경하시겠습니까?");
            
            if(order_status == 5){
               alert("이미 배송완료 상태입니다");
               confirm_stat = false;
            }
         }else if($('#order_stat_select'+i+' option:selected').val() == "취소/반품"){
            order_stat = 15;
            confirm_stat = confirm("취소/반품 상태로 변경하시겠습니까?");
            
            if(order_status == 15){
               alert("이미 취소/반품 상태입니다");
               confirm_stat = false;
            }
         }
         
         if(confirm_stat){
            $.ajax({
               url : "./OrderStatUpdate",
               type : "post",
               data : {
                  order_num : order_num,
                  order_stat : order_stat
               },
               success : function(data) {
                  location.reload();
               },
               error : function(error) {
                  alert("error 관리자에게 문의하세요~^^");
               }
            });
         }
   }


</script>
   
   

   
   <%
   

   
      // 관리자 로그인 상태일때만 진행 페이지
      String member_id = (String) session.getAttribute("member_id");

      if (member_id == null || !member_id.equals("admin")) {
         response.sendRedirect("./MemberLogin.me");
      }
      
      // 저장된 주문목록 가져오기
      
      ArrayList<OrderDTO> orderList = (ArrayList<OrderDTO>)request.getAttribute("orderList");
      int count = (Integer) request.getAttribute("ordercount");
      String pageNum = (String) request.getAttribute("pageNum");
      
      int pageCount = (Integer) request.getAttribute("pageCount");
      int pageBlock = (Integer) request.getAttribute("pageBlock");
      int startPage = (Integer) request.getAttribute("startPage");
      int endPage = (Integer) request.getAttribute("endPage");
      String stat = (String) request.getAttribute("stat");
      String search = (String) request.getAttribute("search");
      
   %>

<jsp:include page="Include/admin_header.jsp"/>
	<div id="content-wrapper">   
<div class="container-fluid">
      
<div class="col-md-12">   

<ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="./AdminMain.adme">관리자 메인으로</a>
        </li>
        <li class="breadcrumb-item"><a href="./AdminOrderList.ador">전체 보기</a></li>
         <li class="breadcrumb-item"><a href="./AdminOrderList.ador?stat=2">주문완료</a></li>
         <li class="breadcrumb-item"><a href="./AdminOrderList.ador?stat=3">상품준비</a></li>
         <li class="breadcrumb-item"><a href="./AdminOrderList.ador?stat=4">배송중</a></li>
         <li class="breadcrumb-item"><a href="./AdminOrderList.ador?stat=5">배송완료</a></li>
         <li class="breadcrumb-item"><a href="./AdminOrderList.ador?stat=6">구매확정</a></li>
         <li class="breadcrumb-item"><a href="./AdminOrderList.ador?stat=14">취소/반품 신청</a></li>
         <li class="breadcrumb-item"><a href="./AdminOrderList.ador?stat=15">취소/반품</a></li>
</ol>

<div class="card mb-3">
      <div class="card-header">
         <i class="fas fa-table"></i>
              주문 목록</div>
    <div class="card-body">
        <div class="table-responsive"> 
         <div class="row">
  
   <div class="col-md-12">
   <table class="table table-bordered">
        <tr>
          <td>주문번호</td>
          <td>주문자ID</td>
          <td>전화번호</td>
          <td>상품이름</td>
          <td>상품Size</td>
          <td>상품색상</td>
          <td>수 량</td>
          <td>받는 사람</td>
          <td>전화번호</td>
          <td>주 소</td>
          <td>MEMO</td>
          <td>주문날짜</td>
          <td>주문상태</td>
          <td>주문상태 변경</td>
        </tr>
	



        <% 
      if(orderList == null){
         
      }else{

        for(int i=0; i < orderList.size(); i++){ 
           OrderDTO odto = orderList.get(i);

      
          
        
        %>

        <tr>
          <td><%=odto.getOrder_trade_num() %></td>
          <td><%=odto.getOrder_member_id() %></td>
          <td><%=odto.getOrder_member_phone().substring(0,3)+"-"
                +odto.getOrder_member_phone().substring(3,7)+"-"
                +odto.getOrder_member_phone().substring(7,11) %></td>
          <td><%=odto.getOrder_item_name() %></td>
          <td><%=odto.getOrder_size() %></td>
          <td><%=odto.getOrder_color() %></td>
          <td><%=odto.getOrder_amount() %></td>
         <td><%=odto.getOrder_take_name() %></td>
          <td><%=odto.getOrder_phone().substring(0,3)+"-"
                +odto.getOrder_phone().substring(3,7)+"-"
                +odto.getOrder_phone().substring(7,11) %></td>
          <td><%=odto.getOrder_addr1()+","
                +odto.getOrder_addr2() %></td>
          <td><%=odto.getOrder_memo() %></td>
          <td><%=odto.getOrder_date() %></td>
         <td><% String order_stat = "";
         switch(odto.getOrder_status()){
                           
         case 1: order_stat = "결제대기";
                  break;
         case 2: order_stat = "주문완료";
                  break;
         case 3: order_stat = "상품준비";
                  break;
         case 4: order_stat = "배송중 / 운송장 : "+odto.getOrder_trans_num();
                  break;
         case 5: order_stat = "배송완료 / 운송장 : "+odto.getOrder_trans_num();
                  break;
         case 6: order_stat = "구매확정";
                  break;
         case 14: order_stat = "취소/반품 신청";
                  break;
         case 15: order_stat = "취소/반품";
                  break;
               }%> <%= order_stat %></td>
               
         <td>
               <select id="order_stat_select<%=i %>" onchange="order_stat_select('<%=odto.getOrder_trade_num()%>',<%=i%>,<%=odto.getOrder_status()%>)">
                  <option>주문상태 변경</option>
                  <option>상품준비</option>
                  <option>배송중</option>
                  <option>배송완료</option>
                  <option>취소/반품</option>
               </select>
         </td>
      
        </tr>
        <% 
           } 
        }
        %>
     
     </table>
</div>
   <div class="col-md-4">
      <form action="./AdminOrderList.ador?stat=<%=stat %>" method="post">
         
			<select name="search_column">
			<option value="order_member_id">I D</option>
			<option value="order_name">이 름</option>
			<option value="order_addr1">지 역</option>
			</select>
        
         <input type="text" name="search" value="<%if(search != null && !search.equals("null")){%><%=search%><%}%>">

         <input type="submit" value="검색"> 
      </form>
	</div>
        <div class="col-md-8" align="right"> 
         <select id="excelStat">
            <option>주문완료</option>
            <option>상품준비</option>
            <option>배송중</option>
            <option>배송완료</option>
            <option>구매확정</option>
            <option>취소/반품</option>
         </select><input type="button" value="출력" onclick="goExcel();"><br>
        </div> 

  <div align="center" style="margin: 10px auto 10px auto; width: 75%" >    
<%
      if(count != 0) {
         // 이전
         if (startPage > pageBlock) {
         %>
         <a href="./AdminOrderList.ador?pageNum=<%=startPage - pageBlock%>&stat=<%=stat%>&search=<%=search%>">[이전]</a>
         <%
         }

         // 1...10  11..20  21...30
         for (int i = startPage; i <= endPage; i++) {
         %>
         <a href="./AdminOrderList.ador?pageNum=<%=i%>&stat=<%=stat%>&search=<%=search%>">[<%=i%>]
         </a>
         <%
         }

         // 다음
         if (endPage < pageCount) {
         %>
         <a href="./AdminOrderList.ador?pageNum=<%=startPage + pageBlock%>&stat=<%=stat%>&search=<%=search%>">[다음]</a>
         <%
            }
      }
   %>
   </div>
</div>
</div>
</div>

</div>
</div>


<jsp:include page="Include/admin_footer.jsp"/>
</body>
</html>