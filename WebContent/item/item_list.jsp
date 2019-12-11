<%@page import="com.item.db.ItemDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.item.db.ItemDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/item/itemlist.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>

<div class="container-fluid">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">

<%
	request.setCharacterEncoding("UTF-8");

	String pageNum = request.getParameter("pageNum");
	if(pageNum == null){
		pageNum = "1"; //pageNum의 값이 없을경우 무조건 1페이지
	}
	String order_type = request.getParameter("order_type");
	if(order_type == null){
		order_type = "1";
	}
	List itemList = (List)request.getAttribute("itemList");
	
%>

<div class="col-md-8 col-md-offset-2">
	<h1>Item List</h1>
		
<ul id="myTab" class="nav nav-tabs" role="tablist">
    <li role="presentation"><a id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="false" <%if(!order_type.equals("1")){%>onclick="location.href='./ItemListAction.ite?pageNum=1&order_type=1'"<%} %>>신상품순</a></li>
    <li role="presentation"><a role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile" aria-expanded="true" <%if(!order_type.equals("2")){%>onclick="location.href='./ItemListAction.ite?pageNum=1&order_type=2'"<%} %>>인기순</a></li>
    <li role="presentation"><a role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile" aria-expanded="true" <%if(!order_type.equals("3")){%>onclick="location.href='./ItemListAction.ite?pageNum=1&order_type=3'"<%} %>>낮은가격순</a></li>
</ul>
	
<table id="list" class="list">
		
		<%
			String commentPage="1";
			for (int i = 0; i < itemList.size(); i++) {
				ItemDTO idto = (ItemDTO)itemList.get(i);
		%>
		<%if((i)%4 == 0 || i == 0){ %><tr><%} %>
		<%System.out.println(idto.getItem_thumbnail()); %>
		<td>
		  <a href="./ItemDetailAction.ite?item_num=<%=idto.getItem_num()%>">
		   <div class="itemLL">
				<table class="itemlist">
				<tr>
				<td colspan="2">
					<img src="./upload/<%=idto.getItem_thumbnail() %>" width="220px" height="220px">
				</td>
				</tr>
				<tr>
					<td colspan="2"><%=idto.getItem_name()%>
					</td>
				</tr>
				<tr>
					<td>￦<%=idto.getItem_price() %></td>
					<td><%if(idto.getItem_saleprice() > 0){ %>￦<%=idto.getItem_saleprice() %> <%} %></td>
				</tr>
				</table>
		   </div>	
		  </a>	
		</td>
		<%if((i+1)%4 == 0 ){ %></tr><%} %>
		<%
		
			}
%>
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
		%>
		<div align="center" style="margin: 10px auto 10px auto; width: 75%"><div class="list_bottom"><%
		// 이전
		if(startPage>pageBlock){
			%>
			<a href="./ItemListAction.ite?pageNum=<%=startPage-pageBlock%>&order_type=<%=order_type %>">[이전]</a>
			<%
		}
		%></div></div>	<%
		%><div class="list_bottom" align="center"><%
		// 1...10 11...20 21...30
		for(int i=startPage; i<=endPage; i++){
			if(Integer.parseInt(pageNum) == i){
			%>
			<a href="./ItemListAction.ite?pageNum=<%=i%>&order_type=<%=order_type %>" <%if(Integer.parseInt(pageNum)==i){ %>style="font-weight: bold; color: black; font-size: 1.2em;<%}%>">[<%=i %>]</a>
			<%
			}else{
			%>
			<a href="./ItemListAction.ite?pageNum=<%=i%>&order_type=<%=order_type %>">[<%=i %>]</a>
			<%
			}
		}
		%></div><%
		%><div class="list_bottom"><%
		// 다음
		if(endPage<pageCount){
			%>
			<div class="list_bottom"><a href="./ItemListAction.ite?pageNum=<%=startPage+pageBlock %>&order_type=<%=order_type %>">[다음]</a></div>
			<%
		}
		%></div><%
	}

%>
</div>
</div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>
	

</body>
</html>