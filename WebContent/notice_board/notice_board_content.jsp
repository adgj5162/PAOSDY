<%@page import="com.notice.db.NoticeBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/notice.css" rel="stylesheet">


<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid">

<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">

	<div class="col-md-6 col-md-offset-3 mt-4">
	</div>
	<%
		NoticeBoardDTO nbdto = (NoticeBoardDTO) request.getAttribute("nbdto");
		String member_id = (String)session.getAttribute("member_id");
		String pageNum = request.getParameter("pageNum");
		String noticeboard_num = request.getParameter("noticeboard_num");
	%>
  <div class="row">
	<div class="col-md-6 col-md-offset-3 mt-3">
	<table class="table nc">
		
		<tr>
			<td><h3><%=nbdto.getNoticeboard_title()%></h3></td>
			
		</tr>
		<tr>
			<td><div class="writer">관리자</div></td>
		</tr>
		
		<tr>
			<td><%=nbdto.getNoticeboard_content()%></td>
		</tr>

	</table>
	<hr size="3">
	</div>
  </div>
	
	
  <div class="row">
  	<div class="col-md-6 col-md-offset-3 ">
	<input type="button" value="목록" onclick="location.href='./NoticeList.nb?pageNum=<%=pageNum%>&noticeboard_num=<%=noticeboard_num%>'" class="btn btn-paosdy btn-lg">
	
	<%if( member_id != null &&member_id.equals("admin")){ %>
	<input type="button" value="삭제" onclick="location.href='./NoticeDeleteAction.nb?pageNum=<%=pageNum%>&noticeboard_num=<%=noticeboard_num%>'" class="btn btn-paosdy btn-lg">
	<input type="button" value="수정" onclick="location.href='./NoticeBoardUpdate.nb?pageNum=<%=pageNum%>&noticeboard_num=<%=noticeboard_num%>'" class="btn btn-paosdy btn-lg">
	<%}%>
    </div>
  </div>
	
	
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>
</div>	

	
</body>
</html>