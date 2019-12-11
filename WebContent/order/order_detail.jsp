<%@page import="com.order.db.OrderDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
</head>
<body>
<h1>WebContent/order/order_detail.jsp</h1>
<%
	//주문 상세페이지 정보를 저장
	//request.setAttribute("orderDetailList", orderDetailList);
	List orderDetailList = (List)request.getAttribute("orderDetailList");
%>

<table border="1">
	<tr><td colspan="5"><h1>주문 상세 보기</h1></td></tr>
	<tr>
		<td>상품명</td>
		<td>상품사이즈</td>
		<td>상품색상</td>
		<td>상품주문수량</td>
		<td>주문금액</td>
	</tr>
	
	<%
	int total_money=0;
	
	for(int i=0; i<orderDetailList.size(); i++){
		OrderDTO odto = (OrderDTO)orderDetailList.get(i);
		total_money += odto.getOrder_price();
	}
	
	for(int i=0;i<orderDetailList.size();i++){
		OrderDTO odto = (OrderDTO)orderDetailList.get(i);
		%>
	<tr>
		<td><%=odto.getOrder_name() %></td>
		<td><%=odto.getOrder_size() %></td>
		<td><%=odto.getOrder_color() %></td>
		<td><%=odto.getOrder_amount() %></td>
		<td><%=odto.getOrder_price() %></td>
	</tr>

	<tr>
		<td colspan="5">총 주문금액 : <%=total_money %>원</td>
	</tr>
	<tr><td colspan="5"><h1>배송정보</h1></td></tr>
	<tr>
		<td colspan="2">이름</td>
		<td colspan="3"><%=odto.getOrder_take_name() %></td>
	</tr>
	<tr>
		<td colspan="2">연락처</td>
		<td colspan="3"><%=odto.getOrder_phone().substring(0,3) %>-<%=odto.getOrder_phone().substring(3,7) %>-<%=odto.getOrder_phone().substring(7,11)%></td>
	</tr>
	<tr>
		<td colspan="2">주소</td>
		<td colspan="3"><%=odto.getOrder_addr1() %> <%=odto.getOrder_addr2() %></td>
	</tr>
	
	<tr><td colspan="5"><h1>결제정보</h1></td></tr>
	<tr>
		<td colspan="2">결제방법</td>
		<td colspan="3"><%=odto.getOrder_pay_type() %></td>
	</tr>
	<%} %>
</table>

<a href="./OrderList.or">주문목록보기</a>
</body>
</html>