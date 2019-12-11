<%@page import="com.member.db.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/item/navtab.css" rel="stylesheet">
<link href="./CSS/member/join.css" rel="stylesheet">
<link href="./CSS/member/info.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>


</head>
<body>

<div class="container-fluid row">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">

<%
	//Kim pro 2019-10-02

   // 세션값 (id) 가져오기 
   // 없을경우 로그인페이지로 이동
   String member_id = (String)session.getAttribute("member_id");
   if(member_id == null){
	   response.sendRedirect("./MemberLogin.me");
   }
   // 회원정보객체를 가져오기 MemberUpdate 객체에서 생성한 정보
   
   // 전달받은 회원정보 객체를 form 태그에 정보를 표시 
	 MemberDTO mdto = (MemberDTO)request.getAttribute("mdto");   
   
	// Kim pro 2019-10-02

%>


<jsp:include page="/member/member_navtab.jsp"/>
<div class="col-md-6 col-md-offset-3">
<fieldset class="field">

	  <legend>회원 정보</legend>
   
   <table class="table-striped infot">
    <tr>
      <th>아이디</th><td><%=member_id %></td>
    </tr>
    <tr>
      <th>이 름</th><td><%=mdto.getMember_name() %></td>
    </tr>
    <tr>
      <th>생년월일</th><td><%=mdto.getMember_birth().substring(0, 4)+"/"
                +mdto.getMember_birth().substring(4,6)+"/"
             +mdto.getMember_birth().substring(6,8)%></td>
    </tr>
    <tr>
      <th>성 별</th><td><%=mdto.getMember_gender() %></td>
    </tr>
    <tr>
      <th>E-mail</th><td><%=mdto.getMember_email() %></td>
    </tr>
    <tr>
      <th>휴대전화</th><td><%=mdto.getMember_phone().substring(0,3)+"-"
               +mdto.getMember_phone().substring(3,7)+"-"
               +mdto.getMember_phone().substring(7,11)%></td>
    </tr>
    <tr>
      <th>POINT</th><td><%=mdto.getMember_point()%></td>
    </tr>
    <tr>
      <th>우편번호</th><td><%=mdto.getMember_postcode() %></td>
    </tr>
    <tr>
      <th>주 소</th><td><%=mdto.getMember_addr1()%></td>
    </tr>
    <tr>
      <th>상세주소</th><td><%=mdto.getMember_addr2()%></td>
    </tr>
    <tr>
      <th>가입일자</th><td><%=mdto.getMember_regdate()%></td>
    </tr>
    
    
   </table>
     
  </fieldset>
</div>
	    
	    


</div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>

</body>
</html>