<%@page import="com.goodlist.db.GoodListDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/item/itemlist.css" rel="stylesheet">
<link href="./CSS/item/navtab.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>
<%
   request.setCharacterEncoding("UTF-8");
   List goodlist = (List)request.getAttribute("goodlist");
%>


<div class="container-fluid row">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>
<jsp:include page="/member/member_navtab.jsp"/>
<div class="col-md-8 section col-md-offset-2">

<br><br><br><br>
<h1>찜목록 [ WishList ]</h1>
   <table class="table-hover table mt-2">
      <tr>
         <th width="100">이미지</th>
         <th>상품이름</th>
         <th>추가날짜</th>
         <th>삭제하기</th>
      </tr>
      <%
         for(int i=0; i<goodlist.size(); i++){
            GoodListDTO gdto = (GoodListDTO)goodlist.get(i);
      %>
            
               <tr>
                  <td><img src="./upload/<%=gdto.getGoodlist_item_thumbnail()%>" width="50" height="50"></td>
                  <td><p><a href="./ItemDetailAction.ite?item_num=<%=gdto.getGoodlist_item_num()%>"><%=gdto.getGoodlist_item_name() %></a></p></td>
                  <td><p><%=gdto.getGoodlist_date() %></p></td>
                  <td><p><input type="button" value="삭제하기" onclick="location.href='./deleteGoodListAction.goo?goodlist_num=<%=gdto.getGoodlist_num()%>'" class="btn btn-paosdy"></p></td>
               </tr>
      <%
         }
      %>
   </table>
   <div align="right">
   <input type="button" value="쇼핑하러가기" onclick="location.href='./ItemListAction.ite';" class="btn btn-paosdy btn-lg">
   <input type="button" value="뒤로가기" onclick="history.back();" class="btn btn-paosdy btn-lg">
   </div>
   
   </div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>
   
</body>
</html>