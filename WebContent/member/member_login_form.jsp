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
      
      <form action="./MemberLoginAction.me" method="post">
      <h1>LOGIN</h1><br>   
        <div class="form-group">
          <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="아이디" name="member_id">
        </div>
        <div class="form-group">
          <input type="password" class="form-control" id="exampleInputPassword1" placeholder="비밀번호" name="member_password">
        </div>
        <button type="submit" class="btn btn-paosdy btn-lg btn-block">Login</button>
      <div align="center" style="float: inherit;">
        <button type="button" class="btn btn-paosdy btn-lg" onclick="location.href='./MemberJoin.me'">Create Account</button>
        <button type="button" class="btn btn-paosdy btn-lg" onclick="window.open('./MemberIdFind.me','아이디찾기','width=600 height=300')">Find ID?</button>
        <button type="button" class="btn btn-paosdy btn-lg" onclick="window.open('./MemberPasswordFind.me','비밀번호찾기','width=700 height=400')">Forgot Password?</button>
      </div>
      </form>
      
      <!-- //네이버아이디로로그인 버튼 노출 영역 -->
      <div id="naverIdLogin"></div>
<!-- 네이버아디디로로그인 초기화 Script -->
      <script type="text/javascript">
         var naverLogin = new naver.LoginWithNaverId(
            {
               clientId: "bgpp4MGt8zAWcTgB8xXF",
               callbackUrl: "http://itwillbs11.cafe24.com/PAOSDY/CallBack.me",
               isPopup: true, /* 팝업을 통한 연동처리 여부 */
               loginButton: {color: "green", type: 3, height: 30} /* 로그인 버튼의 타입을 지정 */
            }
         );
         
         naverLogin.init();
         
      </script>
            
      
      
   </div>
</div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>


</div>





</body>
</html>