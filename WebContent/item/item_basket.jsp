<%@page import="com.goods.basket.db.BasketDTO"%>
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
<%
	
	request.setCharacterEncoding("UTF-8");
	int item_num = (Integer)request.getAttribute("item_num");
	List basketList = (List)session.getAttribute("sessionBasketList");
	
%>
	<table class="table prebasket">
	<tr>
		<td>색상</td>
		<td>사이즈</td>
		<td>수량</td>
		<td>가격</td>
		<td></td>
	</tr>
<%
	int sum = 0;
	if(basketList != null){
		for(int i=0; i<basketList.size(); i++){
			BasketDTO bdto = (BasketDTO)basketList.get(i);
			if(bdto.getBasket_item_num() == item_num){
				sum += (bdto.getBasket_amount()*bdto.getBasket_price());
				
%>
				<tr>
					<td><%=bdto.getBasket_color() %></td>
					<td><%=bdto.getBasket_size() %></td>
					<td><%=bdto.getBasket_amount() %></td>
					<td><%=bdto.getBasket_price() %></td>
					<td><input type="image" src="./img/close-circle-line.png" class="btn btn-paosdy" onclick="deleteItemBasket(<%=bdto.getBasket_item_num()%>,'<%=bdto.getBasket_color()%>','<%=bdto.getBasket_size()%>',<%=bdto.getBasket_amount()%>);"></td>
				</tr>
<%
			}
		}
	}
%>
	<tr>
		<td colspan="2">총 가격 합계 : </td>
		<td colspan="3"><%=sum %></td>
	</tr>
	</table>
</body>
</html>