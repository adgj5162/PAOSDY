<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/item/navtab.css" rel="stylesheet">
<link href="./CSS/member/info.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>

	<%
	
	// Kim pro 2019-10-02
	
		//세션 객체안에 있는 id값을 가져와서 로그인여부 판단.
		String member_id =(String)session.getAttribute("member_id");
	
	    if(member_id == null){
	    	response.sendRedirect("./MemberLogin.me");
	    }
	
		// 아이디가 있을경우  " ㅇㅇㅇㅇ님이 로그인하셨습니다" 페이지 출력
		
		
	// Kim pro 2019-10-02
			
	%>

<div class="container-fluid row">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">
 <jsp:include page="/member/member_navtab.jsp"/>
	
<div class="col-md-6 col-md-offset-3 ce">

<fieldset>

    <legend><h1> 회 원 탈 퇴 </h1></legend>
	<form action="./MemberDeleteAction.me" method="post">
	
	<div class="form-inline">
			<label for="member_id" class="col-md-2 control-label">아이디</label> 
			<input type="text" name="member_id" id="member_id" value="<%=member_id%>" class="form-control" readonly="readonly">
	</div><br>
	
	<div class="form-inline">
			<label for="member_password" class="col-md-2 control-label">비밀 번호</label> 
			<input type="password" name="member_password" id="member_password" class="form-control">
	</div><br>
	<div class="col-md-4 col-md-offset-4">
		<input type="submit" value="회원 탈퇴" class="btn btn-paosdy btn-lg">
	    <input type="reset" value="취 소" class="btn btn-paosdy btn-lg" onclick="history.back();"><br>
	</div>
	</form>

</fieldset>
</div>


</div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div> 

</body>
</html>