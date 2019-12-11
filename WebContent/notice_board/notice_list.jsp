<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.notice.db.NoticeBoardDTO"%>
<%@page import="java.util.List"%>
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
   <%
      request.setCharacterEncoding("UTF-8");

      String pageNum = (String)request.getAttribute("pageNum");
      int count = (Integer) request.getAttribute("count");
      int pageCount = (Integer) request.getAttribute("pageCount");
      int pageBlock = (Integer) request.getAttribute("pageBlock");
      int startPage = (Integer) request.getAttribute("startPage");
      int endPage = (Integer) request.getAttribute("endPage");
      List<NoticeBoardDTO> noticeList = (List)request.getAttribute("noticeList");
      String member_id = (String) session.getAttribute("member_id");
   %>
<div class="container-fluid">

<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">
   
   
   <div class="col-md-8 col-md-offset-2">
   <br><br><br><br>
   <h1>공지사항</h1>
   </div>
   
   <div class="col-md-8 col-md-offset-2">
      <table class="table-hover table mt-2">
         <tr>
            <th>글 번호</th>      
            <th>작성자</th>
            <th>제목</th>   
            <th>작성일</th>      
         </tr>
         <tbody>
         <%if(noticeList != null){
            for(int i=0; i < noticeList.size();i++){
            NoticeBoardDTO nbdto = noticeList.get(i);
            DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            %>
         <tr>
            <td><%=nbdto.getNoticeboard_num() %> </td>
            <td>관리자</td>
            <td><a href="./NoticeBoardContent.nb?noticeboard_num=<%=nbdto.getNoticeboard_num()%>&pageNum=<%=pageNum %>"><%=nbdto.getNoticeboard_title() %></a></td>
            <td><%=format.format(nbdto.getNoticeboard_date()) %></td>
         </tr>
         <%}
         }%>
         </tbody>
      </table>
   </div>
      
   <div class="col-md-8 col-md-offset-2" align="center">
      <%
      if(count != 0) {
         // 이전
         if (startPage > pageBlock) {
         %>
         <a href="./NoticeList.nb?pageNum=<%=startPage - pageBlock%>">[이전]</a>
         <%
         }

         // 1...10  11..20  21...30
         for (int i = startPage; i <= endPage; i++) {
         %>
         <a href="./NoticeList.nb?pageNum=<%=i%>">[<%=i%>]
         </a>
         <%
         }

         // 다음
         if (endPage < pageCount) {
         %>
         <a href="./NoticeList.nb?pageNum=<%=startPage + pageBlock%>">[다음]</a>
         <%
            }
      }
   %>
   </div>
   <%if(member_id != null && member_id.equals("admin")){ %>
   <div class="col-md-8 col-md-offset-2" align="right">
   <input type="button" value="글쓰기" onclick="location.href='./NoticeBoardWrite.nb'" class="btn btn-paosdy btn-lg">   
   </div>
   <%} %>
   
   </div>

<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>
</body>
</html>