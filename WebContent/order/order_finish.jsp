<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/member/login.css" rel="stylesheet">


<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>



<!-- naver sdk -->
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
<!-- naver sdk -->


</head>
<body>

<div class="container-fluid">

<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">   
   <div class="col-md-4 col-md-offset-4 lform">
      
      
      <div align="center">
         <h1>주문이 완료되었습니다</h1>
      </div>
      <div align="center">
         <input type="button" value="쇼핑 계속 하기" onclick="location.href='./ItemListAction.ite'" class="btn btn-paosdy btn-lg">
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" value="주문 내역 보기" onclick="location.href='./OrderList.or'" class="btn btn-paosdy btn-lg">
      </div>
      
      
   </div>
</div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>


</div>





</body>
</html>