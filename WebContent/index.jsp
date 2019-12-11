<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>WebContent/index.jsp</h1>
	<!-- 
   index.jsp 페이지
     프로젝트의  시작점으로 사용
   * Model2방식은 jsp페이지를 직접 접근 X
    => 가상주소를 사용
    특정 동작마다 각각의 가상주소를 생성해서 사용 
    ~.me / ~.bo
 -->
	<%	
	   response.sendRedirect("./Main.me");
	%>





</body>
</html>