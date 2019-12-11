<%@page import="com.order.db.OrderDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/main/fonts.css" rel="stylesheet">
<link href="./CSS/item/navtab.css" rel="stylesheet">
<link href="./CSS/order/order_list.css" rel="stylesheet">


<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">

   function OrderStatUpdateTo6(order_num){
      $.ajax({
         url : "./OrderStatUpdate",
         type : "post",
         data : {
            order_num : order_num,
            order_stat : 6
         },
         success : function(data) {
            location.reload();
         },
         error : function(error) {
            alert("error 관리자에게 문의하세요~^^");
         }
      });
   }

</script>
</head>
<body>
<%
   //해당 주문 목록 정보를 저장
   //request.setAttribute("orderList", orderList);
   List<OrderDTO> orderList = (List<OrderDTO>) request.getAttribute("orderList");
   
   String pageNum = request.getParameter("pageNum");
   if(pageNum == null){
      pageNum = "1"; //pageNum의 값이 없을경우 무조건 1페이지
   }
%>

<div class="container-fluid row">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>
<jsp:include page="/member/member_navtab.jsp"/>
<div class="col-md-12 section">

<div class="col-md-8 section col-md-offset-2">

<br><br>
<h1>주문목록</h1>
<table class="table-hover table mt-2">
   <tr>
      <th>주문번호</th>
      <th>상품명</th>
      <th>결재방법</th>
      <th>주문금액</th>
      <th>주문상태</th>
      <th>주문일시</th>
      <th>운송장 번호</th>
      <th>취소/반품 신청</th>
   </tr>
   <%for(int i=0;i<orderList.size();i++){
      OrderDTO odto = orderList.get(i);%>
   <tr>
      <td class="www"><a href="./OrderDetail.or?trade_num=<%=odto.getOrder_trade_num()%>"><%=odto.getOrder_trade_num() %></a></td>
      <td><a href="./ItemDetailAction.ite?item_num=<%=odto.getOrder_item_num()%>"><%=odto.getOrder_item_name() %></a></td>
      <td><%=odto.getOrder_pay_type() %></td>
      <td><%=odto.getOrder_price() %>원</td>
      
      <%
      //주문상태
      //0 "대기중", 1 "발송준비", 2 "발송완료", 3 "배송중"
      //4 "배송완료", 5 "주문취소"
      String status = "";
      if(odto.getOrder_status() == 1){status = "결제대기";}
      else if(odto.getOrder_status() == 2){status = "주문완료";}
      else if(odto.getOrder_status() == 3){status = "상품준비";}
      else if(odto.getOrder_status() == 4){status = "배송중";}
      else if(odto.getOrder_status() == 5){status = "배송완료";}
      else if(odto.getOrder_status() == 6){status = "구매확정";}
      else if(odto.getOrder_status() == 14){status = "취소/반품 신청";}
      else if(odto.getOrder_status() == 15){status = "취소/반품";}
      %>
      <td><%=status %>
      <%if(odto.getOrder_status() == 5 || odto.getOrder_status() == 4){ %>
         <input type="button" value="구매확정" onclick="OrderStatUpdateTo6('<%=odto.getOrder_trade_num()%>')" class="btn btn-paosdy">
      <%} %> 
      </td>
      <td><%=odto.getOrder_date() %></td>
      <td><%=odto.getOrder_trans_num() %></td>
      <td>
         <%if(odto.getOrder_status() != 6 && odto.getOrder_status() != 15 && odto.getOrder_status() != 14){ %>
            <input type="button" value="취소 / 반품 신청" onclick="window.open('./CancleRequest.or?order_num=<%=odto.getOrder_trade_num() %>','new','width=500,height=400,top=200,left=400,location=no,scroll=no')" class="btn btn-paosdy">
         <%} %>
      </td>
   </tr>
   <%} %>
</table>

<%
   int count = Integer.parseInt(request.getParameter("count"));
   int currentPage = Integer.parseInt(request.getParameter("currentPage"));
   int pageSize = Integer.parseInt(request.getParameter("pageSize"));
   
//페이지 출력
   if(count != 0){
      //전체 페이지수 계산
      
      int pageCount=count/pageSize+(count%pageSize==0 ? 0:1);
      
      //한화면에 보여줄 페이지 계산
      int pageBlock = 5;
      
      //시작페이지
      int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
      //끝페이지
      int endPage=startPage+pageBlock-1;
      if(endPage > pageCount){
         endPage = pageCount;
      }
      %><div align="center" style="margin: 10px auto 10px auto; width: 75%" ><%
      // 이전
      if(startPage>pageBlock){
         %>
         <a href="./OrderList.or?pageNum=<%=startPage-pageBlock%>">[이전]&nbsp;</a>
         <%
      }
      // 1...10 11...20 21...30
      for(int i=startPage; i<=endPage; i++){
         if(Integer.parseInt(pageNum) == i){
         %>
         <a href="./OrderList.or?pageNum=<%=i%>" <%if(Integer.parseInt(pageNum)==i){ %>style="font-weight: bold; color: black; font-size: 1.2em;<%}%>">[<%=i %>]</a>
         <%
         }else{
         %>
         <a href="./OrderList.or?pageNum=<%=i%>">[<%=i %>]</a>
         <%
         }
      }
      // 다음
      if(endPage<pageCount){
         %>
         <a href="./OrderList.or?pageNum=<%=startPage+pageBlock %>">&nbsp;[다음]</a>
         <%
      }
      %></div>
    <%
   }

%>
   </div>
   </div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>
</body>
</html>