<%@page import="com.member.db.MemberDTO"%>
<%@page import="com.ask.db.AskDAO"%>
<%@page import="com.ask.db.CommentDTO"%>
<%@page import="com.ask.db.AskDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
  src="https://code.jquery.com/jquery-3.4.1.min.js"
  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  crossorigin="anonymous"></script>
<link href="./CSS/item/ask.css" rel="stylesheet">
<title>PAOSDY</title>
<script type="text/javascript">

$(document).ready(function(){
   //문의사항 본문내용 toggle
   $('tr.ask_content').hide();
   $('td.ask_title').click(function(){
      //$('tr.ask_content').toggle();
//       $('tr.ask_content').hide();
      
      $(this).parent().next().toggle();
      $(this).parent().next().next().toggle();
   });
});

   //문의글 관리
//문의글 비밀글로 변경
   
//문의글 삭제   

   //답변관리   
//답변 삭제
function comment_delete(ask_comment_num,item_num) {
   $.ajax({
      url: "./AskCommentDelete.qa",
      type : 'POST',
      data : { 'ask_comment_num': ask_comment_num, pageNum : $('#ask_pageNum').val(),item_num:item_num },
      error : function() {alert('답변 삭제 실패!!');},
      success: function(data){
         $("#ask_table").html(data);
      }
   });
}

   //페이징 처리 
//페이지 번호
/* function pageNumber(ask_pageNum) {
   $.ajax({
      url: "./AskList.qa",
      type : "POST",
      data : { ask_pageNum : ask_pageNum },
      error : function() {alert('페이징 처리 실패!!');},
      success: function(data){
         console.log(data);
         $("#ask_table").html(data);
      }
    });
} */

//문의 비밀글
function ask_secret(ask_num,item_num) {
   $.ajax({
      url: "./AskModifyAction.qa",
      type : 'POST',
      data : {
         item_num: item_num,         
         ask_num : ask_num,
         pageNum : $('#ask_pageNum').val() 
         },
      error : function() {alert('문의글 비밀글 변경 실패!!');},
      success: function(data){
         console.log(data)
         $("#ask_table").html(data);
         alert("비밀글로 변경되었습니다");
      }
   });
}

//문의 공개글
function ask_open(ask_num,item_num) {
   $.ajax({
      url: "./AskOpenAction.qa",
      type : 'POST',
      data : {
         item_num: item_num,         
         ask_num : ask_num,
         pageNum : $('#ask_pageNum').val() 
         },
      error : function() {alert('문의글 공개글 변경 실패!!');},
      success: function(data){
         console.log(data)
         $("#ask_table").html(data);
         alert("공개글로 변경되었습니다");
      }
   });
}
   
   
   
//문의 삭제
function ask_delete(ask_comment_num,item_num) {
   $.ajax({
      url: "./AskDeleteAction.qa",
      type : 'POST',
      data : { 'ask_comment_num': ask_comment_num, pageNum : $('#ask_pageNum').val(),item_num:item_num },
      error : function() {alert('문의글 삭제 실패!!');},
      success: function(data){
         $("#ask_table").html(data);
      }
   });
}


// 답글 작성
function ask_comment(ask_num,item_num,i) {
      
      console.log($('#ask_comment_content'+i).val());
      console.log($('#ask_pageNum').val());
      
      $.ajax({
      url: "./AskCommentWrite.qa",
      type : 'POST',
      data : { 
         'ask_num' : ask_num,
         'ask_comment_content' : $('#ask_comment_content'+i).val(),
         pageNum : $('#ask_pageNum').val(),
         item_num:item_num
            },
      error : function() {alert('문의 답변 작성 실패!!');},
      success: function(data){
         $("#ask_table").html(data);
      }
   });
}
</script>
</head>
<body>
<%
String id = (String)session.getAttribute("member_id");

List coList = (List)request.getAttribute("coList");
List<AskDTO> askList = (List<AskDTO>)request.getAttribute("askList");
MemberDTO mdto = (MemberDTO)request.getAttribute("mdto");

String ask_pageNum = (String)request.getAttribute("askPageNum");
int ask_count = (Integer)request.getAttribute("askCount");
int ask_pageCount = (Integer)request.getAttribute("askPageCount");
int ask_pageBlock = (Integer)request.getAttribute("askPageBlock");
int ask_startPage = (Integer)request.getAttribute("askStartPage");
int ask_endPage = (Integer)request.getAttribute("askEndPage");
int ask_ItemNum = (Integer)request.getAttribute("askItemNum");
%>

<section id="ask_table">
<h1>Q & A</h1><br>
<table class="table">

<tr>
   <td colspan="8">

      <b>(총 <%=ask_count %>건)</b><br>
      
      <%if(ask_count != 0){%>   
      <input type="hidden" name="ask_pageNum" id="ask_pageNum" value="<%=ask_pageNum%>"> 
   </td>
</tr>

   <tr>
      <th>번호</th>
      <th style="width:210px;">답변여부</th>
      <th>구분</th>
      <th>내용</th>
      <th>작성자</th>
      <th>등록일자</th>
      <% if(id != null && id.equals("admin")){%>
      <th class="clas">관리</th>
      <%} %>
      
      
      
   </tr>
   <%
   
   if(ask_count != 0){ //글이 없으면
      
      for(int i=0;i<askList.size();i++){ 
         AskDTO askdto = askList.get(i);
         CommentDTO codto = (CommentDTO)coList.get(i);%>
   <tr>
      <td valign="middle"><%=askdto.getAsk_num() %></td>
      <td valign="middle"><%if(askdto.getAsk_answer()==1){%>답변완료<%}else{%>답변예정<%}%></td>
      <td valign="middle"><%=askdto.getAsk_category() %></td>
      
      <!-- 토글클릭 -->
      <td class="ask_title" valign="middle">
      
      <%if(askdto.getAsk_open() == 0 || (id!=null && id.equals("admin")) || (id!=null && id.equals(askdto.getAsk_member_id()))){ //공개글 %>
         <%=askdto.getAsk_title() %>
      
      <%}else{ //비밀글
      %>
         
         <p onclick="alert('비밀글로 작성자만 볼 수 있습니다.');return false;"><img alt="secret" src="./img/key.png" width="20" style="margin-bottom: 4px;"> 비밀글</p>
         
      <%}%>
      </td>
      <td valign="middle"><%=askdto.getAsk_member_id() %></td>
      <td valign="middle"><%=askdto.getAsk_date() %></td>
      
      <%
      
      if(((id != null ) && (id.equals("admin"))) || ((id != null) && (id.equals(askdto.getAsk_member_id())))){ %>
      <td id="ask_secret" valign="middle">
         <%if(askdto.getAsk_open() == 0){ //공개글이면 비밀글 전환 버튼 %>
            <input type="button" value="비밀" onclick="ask_secret(<%=askdto.getAsk_num() %>,<%=ask_ItemNum%>);" class="btn btn-paosdy">
         <%}else{%>
            <input type="button" value="공개" onclick="ask_open(<%=askdto.getAsk_num() %>,<%=ask_ItemNum%>);" class="btn btn-paosdy">
         <%} %>
            <input type="button" value="삭제" onclick="ask_delete(<%=askdto.getAsk_num() %>,<%=ask_ItemNum%>);" class="btn btn-paosdy">
      </td>
      <%}else{%>
      <td>
      </td>
      <%}
      
      //TODO 관리자가 작업못하게 할거면 여기까지 지울것!!
      %>
   </tr>


   <!-- 클릭하면 본문/댓글 나타남 코드 시작-->   
   <tr class="ask_content">
      <td></td>
      
      <%if(askdto.getAsk_open() == 0){ //공개글%>
         <td>
         <%if(askdto.getAsk_file()!=null){ %>
            <img src="./ask_upload/<%=askdto.getAsk_file() %>" height="150" width="200" id="askimg">
         <%} %>   
         </td>
         <td colspan="5"><%=askdto.getAsk_content().replace("\r\n", "<br>")%></td>
         
      
      
      
      
      
      
      
      <%}else{ //비공개글
         if(id!=null && id.equals(askdto.getAsk_member_id()) || (id!=null && id.equals("admin"))){%>
            <td>
            <%if(askdto.getAsk_file()!=null){ %>
            <img src="./ask_upload/<%=askdto.getAsk_file() %>" height="150" width="200" id="askimg">
            <%} %>   
            </td>
         <td colspan="5"><%=askdto.getAsk_content().replace("\r\n", "<br>")%></td>
            <%}else{ %>
            비밀글로 작성자만 볼 수 있습니다.
      <%}}%>
      
      
      
      <td colspan="5">
   </tr>
   <tr class="ask_content">
   <%if(((askdto.getAsk_answer()==1) && ((askdto.getAsk_open() == 0) || (id!= null && id.equals("admin")) || (id!=null && id.equals(askdto.getAsk_member_id()))))){ //답변완료일때 답변출력%>
      <td>답변</td>
      <td colspan="3"><%=codto.getComment_content() %></td>
      <td>담당자</td>
      <td><%=codto.getComment_date() %></td>
      <%if(id != null && id.equals("admin")){ %>
      <td id="comment_update">
         <input type="button" value="수정" onclick="window.open('./AskCommentUpdate.qa?ask_pageNum=<%=ask_pageNum %>&ask_num=<%=askdto.getAsk_num() %>','문의 답변 수정','width=500,height=500,location=no,status=no,scrollbars=yes');" class="btn btn-paosdy">
         <input type="button" value="삭제" onclick="comment_delete(<%=askdto.getAsk_num() %>,<%=ask_ItemNum%>);" class="btn btn-paosdy">
      </td>
      <%} %>
   <%}else{%>
      <td colspan="7">
         <%
         //TODO 관리자가 작업못하게 할거면 여기부터 지울것!!
         
         if(id != null && id.equals("admin")){ %>

            <input type="hidden" name="ask_num" id="ask_num" value="<%=askdto.getAsk_num()%>">
            <textarea rows="10" style="width:70%;" name="ask_comment_content" id="ask_comment_content<%=i%>" placeholder="답변을 입력해주세요."></textarea>
            <input type="button" value="답변쓰기" onclick="ask_comment(<%=askdto.getAsk_num() %>,<%=ask_ItemNum%>,<%=i%>);" class="btn btn-paosdy btn-lg">
            <!-- <input type="button" value="답변쓰기" onclick="location.href='AskListAdminAction.qa'"> -->
            <%
            // TODO 답변쓰기 -> 관리자 페이지의 답변쓰기로 이동
            %>

         <%} 
         
         //TODO 관리자가 작업못하게 할거면 여기까지 지울것!!
         
         
         %>
      </td>
   <%}} %>
   <!-- 클릭하면 본문/댓글 나타남 코드 끝-->   
   </tr>
   <tr>
    <td colspan="8"></td>
   </tr>
   <%} %>
   
   
   
</table>
   
   <!-- 페이징처리 코드 시작 -->
   <div align="center" style="margin: 10px auto 10px auto; width: 75%" >
      <%
      if(ask_count != 0) {
         if (ask_startPage > ask_pageBlock) {%>            
            <a onclick="pageNumber(<%=ask_startPage-ask_pageBlock%>);"> Pre </a>
       <%}for (int i=ask_startPage ; i<=ask_endPage ; i++) {
         %><a onclick="pageNumber(<%=i%>);" class="pageNum">[<%=i%>]</a>
         <%}if (ask_endPage < ask_pageCount) {
         %><a onclick="pageNumber(<%=ask_startPage+ask_pageBlock%>);"> Next </a>
      <%}}%>
     </div> 
   <!-- 페이징처리 코드 끝 -->
   
<%} %>

<br>
<!-- width=550,height=700 팝업창 발생 -->
<%if(id!=null){ //로그인한 회원만 작성 가능%>
   <div class="col-md-1 col-md-offset-10">
    <input type="button" value="작성하기" id="ask_write" onclick="window.open('./AskWrite.qa?ask_item_num=<%=ask_ItemNum %>','문의사항 작성','width=550,height=700,location=no,status=no,scrollbars=yes');" class="btn btn-paosdy btn-lg">
   </div>
<%} %>
</section>
</body>
</html>