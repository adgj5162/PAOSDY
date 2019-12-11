<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/main/fonts.css" rel="stylesheet">
<link href="./CSS/member/info.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">

<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('form').submit(function(){
		var order_memo = $('.order_memo').val();
		if(order_memo == ""){
			alert("내용을 입력하세요");
			$('.order_memo').focus();
			return false;
		}
	});
});
</script>

</head>
<body>
<div class="container-fluid row">
<%
	String order_trade_num = request.getParameter("order_num");
%>
<h1>주문 취소 / 반품 신청하기</h1>
<hr>
<form action="./CancleRequestAction.or">
	주문번호 : <input type="text" value="<%=order_trade_num %>" name="order_trade_num" readonly="readonly"><br>
	주문취소 / 반품 사유<br>
<div align="center">
	<textarea rows="8" cols="55" name="order_memo" class="order_memo" placeholder="내용을 입력하세요"></textarea><br>
</div>
<div class="row">
</div>
 <div class="col-md-10">
	<input type="submit" value="신청">
	<input type="reset" value="취소" onclick="window.close();">
 </div>
</form>
</div>
</body>
</html>