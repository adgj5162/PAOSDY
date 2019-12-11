<%@page import="com.ask.db.CommentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>PAOSDY</title>
<script type="text/javascript">
function comment_formcheck(){
	if(!document.comment_form.comment_content.value){
		alert("답변을 입력해주세요.");
		document.ask_form.ask_content.focus();
		return false;
	}
}
</script>
</head>
<body>

<h1>문의사항 답변 수정</h1>
	<%
	 //String ask_member_id = (String)session.getAttribute("member_id");
	
	 // ./AskAnswerWrite.qa
	 
	 int ask_comment_num = Integer.parseInt(request.getParameter("ask_num"));

	 CommentDTO codto = (CommentDTO)request.getAttribute("codto");
	 String pageNum = (String)request.getAttribute("pageNum");
	%>
<form action="./AskCommentUpdateAction.qa" method="post" name="ask_comment_form" onsubmit="return comment_formcheck()">
<input type="hidden" name="ask_comment_num" value="<%=ask_comment_num%>">

<br>
<%//관리자 %>
<br>
<textarea name="ask_comment_content" placeholder="답변을 입력해주세요."><%=codto.getComment_content() %></textarea>

<input type="submit" value="수정">
<input type="reset" value="다시쓰기">
</form>

</body>
</html>