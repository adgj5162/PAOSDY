<%@page import="java.util.Vector"%>
<%@page import="com.ask.db.AskDAO"%>
<%@page import="com.ask.db.CommentDTO"%>
<%@page import="com.ask.db.AskDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./CSS/admin/admin_ask.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<title>PAOSDY</title>
<script type="text/javascript">

$(document).ready(function(){
   //문의사항 본문내용 toggle
   $('tr.ask_content').hide();
   $('td.ask_title').click(function(){
      //$('tr.ask_content').toggle();
      $(this).parent().next().toggle();
      $(this).parent().next().next().toggle();
   });
});

   //답변관리
//답변 작성
function ask_comment(ask_num,i) {
      
      console.log($('#ask_num').val());
      console.log($('#ask_comment_content').val());
      console.log($('#ask_pageNum').val());
      
      $.ajax({
      url: "./AdminAskCommentWrite.adask",
      type : 'POST',
      data : { 
         'ask_num' : ask_num,
         'ask_comment_content' : $('#ask_comment_content'+i).val(),
         pageNum : $('#ask_pageNum').val()
            },
      error : function() {alert('문의 답변 작성 실패!!');},
      success: function(data){
         $("#ask_table").html(data);
         location.reload();
      }
   });
}
   
   
//답변 삭제
function comment_delete(ask_comment_num) {
   $.ajax({
      url: "./AdminAskCommentDelete.adask",
      type : 'POST',
      data : { 'ask_comment_num': ask_comment_num, pageNum : $('#ask_pageNum').val() },
      error : function() {alert('답변 삭제 실패!!');},
      success: function(data){
         $("#ask_table").html(data);
         alert("답변이 삭제 되었습니다");
         location.reload();
      }
   });
}

   //페이징 처리 


//페이지 번호
function pageNumber(ask_pageNum) {
   $.ajax({
      url: "./AdminAskListAction.adask",
      type : "POST",
      data : { ask_pageNum : ask_pageNum },
      error : function() {alert('페이징 처리 실패!!');},
      success: function(data){
         console.log(data);
         $("#ask_table").html(data);
      }
    });
}

//페이징 처리 


   //문의 비밀글
function ask_secret(ask_num) {
   $.ajax({
         url: "./AdminAskModifyAction.adask",
         type : 'POST',
         data : {
            ask_num : ask_num,
            pageNum : $('#ask_pageNum').val() 
            },
         error : function() {alert('문의글 비밀글 변경 실패!!');},
         success: function(data){
            console.log(data)
            $("#ask_table").html(data);
            alert("비밀글로 변경되었습니다");
            location.reload();
         }
      });
   }
   //문의 비밀글
   
   //문의 공개글
   function ask_open(ask_num) {
      $.ajax({
         url: "./AdminAskOpenAction.adask",
         type : 'POST',
         data : {      
            ask_num : ask_num,
            pageNum : $('#ask_pageNum').val() 
            },
         error : function() {alert('문의글 공개글 변경 실패!!');},
         success: function(data){
            console.log(data)
            $("#ask_table").html(data);
            alert("공개글로 변경되었습니다");
            location.reload();
         }
      });
   }
   
   //문의 삭제
   function ask_delete(ask_comment_num) {
      $.ajax({
         url: "./AdminAskDeleteAction.adask",
         type : 'POST',
         data : { 'ask_comment_num': ask_comment_num, pageNum : $('#ask_pageNum').val() },
         error : function() {alert('문의글 삭제 실패!!');},
         success: function(data){
            $("#ask_table").html(data);
            alert("삭제 되었습니다");
            location.reload();
         }
      });
   }
   
   
   
   
</script>
</head>
<body>
<%
String id = (String)session.getAttribute("member_id");

List coList = (List)request.getAttribute("coList");
List askList = (List)request.getAttribute("askList");
List item_name = (List)request.getAttribute("item_name");
AskDTO askdto = (AskDTO)request.getAttribute("askdto");

String ask_pageNum = (String)request.getAttribute("askPageNum");
int ask_count = (Integer)request.getAttribute("askCount");
int ask_pageCount = (Integer)request.getAttribute("askPageCount");
int ask_pageBlock = (Integer)request.getAttribute("askPageBlock");
int ask_startPage = (Integer)request.getAttribute("askStartPage");
int ask_endPage = (Integer)request.getAttribute("askEndPage");

//int ask_num = askdto.getAsk_num();



%>

<jsp:include page="Include/admin_header.jsp"/>
<div id="content-wrapper">
<div class="container-fluid">
      
<div class="col-md-12">   

<ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="./AdminMain.adme">관리자 메인으로</a>
        </li>
        <li class="breadcrumb-item active">문의관리</li>
</ol>

<div class="card mb-3">
      <div class="card-header">
         <i class="fas fa-table"></i>
              Q & A 문의관리</div>
    <div class="card-body">
        <div class="table-responsive"> 
         <div class="row">

<div class="col-md-12">         
<table class="table table-bordered">
<tr>
   <td colspan="9">
      <h4>(총 <%=ask_count %> 건)</h4><br>
      <%if(ask_count != 0){%>   
      <input type="hidden" name="ask_pageNum" id="ask_pageNum" value="<%=ask_pageNum%>"> 
   </td>
</tr>

   <tr>
      <th>번호</th>
      <th>답변여부</th>
      <th>상품명</th>
      <th>구분</th>
      <th>내용(클릭하면 내용 출력됨)</th>
      <th>작성자</th>
      <th>등록일자</th>
      <th>관리</th>

   </tr>
   <%
   
   if(ask_count != 0){
      
      for(int i=0;i<askList.size();i++){ 
         askdto = (AskDTO)askList.get(i);
         CommentDTO codto = (CommentDTO)coList.get(i);%>
   <tr>
      <td><%=askdto.getAsk_num() %></td>
      <td><%if(askdto.getAsk_answer()==1){%>답변완료<%}else{%>답변예정<%}%></td>
      <th><%=item_name.get(i) %></th>
      <td><%=askdto.getAsk_category() %></td>
      
      <!-- 토글클릭 -->
      <td class="ask_title"><%=askdto.getAsk_title() %></td>

      <td><%=askdto.getAsk_member_id() %></td>
      <td><%=askdto.getAsk_date() %></td>
      <td id="ask_secret">
         <%if(askdto.getAsk_open() == 0){ //공개글이면 비밀글 전환 버튼 %>
            <input type="button" value="비밀" onclick="ask_secret(<%=askdto.getAsk_num() %>);" class="btn btn-primary">
         <%}else{%>
            <input type="button" value="공개" onclick="ask_open(<%=askdto.getAsk_num() %>);" class="btn btn-primary">
         <%} %>
            <input type="button" value="삭제" onclick="ask_delete(<%=askdto.getAsk_num() %>);" class="btn btn-primary">
      </td>
   </tr>
   
   
   <!-- 클릭하면 본문/댓글 나타남 코드 시작-->   
   <tr class="ask_content">
      <td>내용</td>
      <td colspan="7">
         <%=askdto.getAsk_content().replace("\r\n", "<br>")%>
         <%if(askdto.getAsk_file()!=null){ %>
            <img src="./ask_upload/<%=askdto.getAsk_file() %>" height="150" width="200"><br>
         <%} %>         
      </td>
   </tr>
   
   <tr class="ask_content">
   <%
   
   if(askdto.getAsk_answer() == 1){ //답변완료일때 답변출력
   %>
      <td>답변</td>
      <td colspan="4"><%=codto.getComment_content()%></td>
      
      <td>담당자</td>
      <td><%=codto.getComment_date() %></td>
      
      <td id="comment_update">
         <input type="button" value="수정" onclick="window.open('./AdminAskCommentUpdate.adask?ask_pageNum=<%=ask_pageNum %>&ask_num=<%=askdto.getAsk_num() %>','문의 답변 수정','width=500,height=500,location=no,status=no,scrollbars=yes');" class="btn btn-primary">
         <input type="button" value="삭제" onclick="comment_delete(<%=askdto.getAsk_num() %>);" class="btn btn-primary">
      </td>
      
   <%}else{%>
      <td colspan="8">
         <form action="">
            <input type="hidden" name="ask_num" id="ask_num" value="<%=askdto.getAsk_num()%>">
            <textarea rows="10" style="width:80%;" name="ask_comment_content" id="ask_comment_content<%=i %>" placeholder="답변을 입력해주세요."></textarea>
            <div class="col-md-2 offset-10" id="rwbtn">
             <input type="button" value="답변쓰기" onclick="ask_comment(<%=askdto.getAsk_num() %>,<%=i%>);" class="btn btn-primary">            
            </div>
         </form>
      </td>
      
   
   <%}} %>
   <!-- 클릭하면 본문/댓글 나타남 코드 끝-->   
   </tr>
   <%} %>
      

</table>
</div>
 <!-- 페이징처리 코드 시작 -->
   <div id="page_control" align="center" style="margin: 10px auto 10px auto; width: 75%">
      <%
      if(ask_count != 0) {
         if (ask_startPage > ask_pageBlock) {%>            
            <a onclick="location.href='./AdminAskListAction.adask?ask_pageNum=<%=ask_startPage-ask_pageBlock%>'"> Pre </a>
       <%}for (int i=ask_startPage ; i<=ask_endPage ; i++) {
         %><a onclick="location.href='./AdminAskListAction.adask?ask_pageNum=<%=i %>'" class="pageNum">[<%=i%>]</a>
         <%}if (ask_endPage < ask_pageCount) {
         %><a onclick="location.href='./AdminAskListAction.adask?ask_pageNum=<%=ask_startPage+ask_pageBlock%>'"> Next </a>
      <%}}%>
   </div>
   <!-- 페이징처리 코드 끝 -->
<%} %>
</div>
</div>
</div>

</div>
</div>

<jsp:include page="Include/admin_footer.jsp"/>


</body>
</html>