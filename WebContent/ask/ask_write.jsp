<%@page import="com.ask.db.AskDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<link href="./CSS/main/fonts.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Fredoka+One|Noto+Serif+KR&display=swap&subset=korean" rel="stylesheet">

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">

<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
<title>PAOSDY</title>
<script type="text/javascript">
function ask_formcheck(){
	if(document.ask_form.ask_category.selectedIndex==0){
		alert("구분을 선택해주세요.");
		document.ask_form.ask_category.focus();
		return false;
	}
	if(!document.ask_form.ask_title.value){
		alert("제목을 입력해주세요.");
		document.ask_form.ask_title.focus();
		return false;
	}
	if(!document.ask_form.ask_content.value){
		alert("문의 사항을 입력해주세요.");
		document.ask_form.ask_content.focus();
		return false;
	}
	if(document.ask_form.ask_file.files[0].size > 2*1024*1024){
		alert("용량이 2MB를 넘는 이미지는 업로드가 되지 않습니다.");
		document.ask_form.ask_file.focus();
		return false;
	}
	pathpoint = document.ask_form.ask_file.value.lastIndexOf('.');
	filepoint = document.ask_form.ask_file.value.substring(pathpoint+1,document.ask_form.ask_file.value.length);
	filetype = filepoint.toLowerCase();
	if(!(filetype=='jpg'||filetype=='gif'||filetype=='png'||filetype=='jpeg'||filetype=='bmp')){
		alert('이미지 파일만 선택할 수 있습니다.');
		document.ask_form.ask_file.focus();
		return false;
	}

}

function askSecCk(){
	
	
	if($("input:checkbox[name='ask_open_ck']").is(":checked") == true){
		$('#ask_open').val("1");
	}else{
		$('#ask_open').val("0");
	}
	console.log($('#ask_open').val());
}
</script>
<script type="text/javascript">
/* $('#ask_form').submit(function(){
	opener.parent.location.reload();
	window.close();
	//opener.location.reload(3000);
	//opener.location.replace();
});
 */
</script>
</head>
<body>
<%
	System.out.println("***********************"+session.getAttribute("member_id"));	

%>
<h1>PRODUCT Q & A</h1>

<form action="./AskWriteAction.qa" method="post" name="ask_form" id="ask_form" onsubmit="return ask_formcheck();" enctype="multipart/form-data">
<input type="hidden" name="ask_open" id="ask_open" value="0"> 
<input type="hidden" name="member_id" id="member_id" value=<%=session.getAttribute("member_id") %>>
<input type="hidden" name="ask_item_num" value="<%=request.getParameter("ask_item_num")%>"> 

구분  
<select name="ask_category">
	<option name="ask_category">문의사항 선택</option>
	<option name="ask_category" value="크기">크기</option>
	<option name="ask_category" value="배송">배송</option>
	<option name="ask_category" value="교환">교환</option>
	<option name="ask_category" value="환불">환불</option>
	<option name="ask_category" value="취소">취소</option>
	<option name="ask_category" value="주문결제">주문결제</option>
	<option name="ask_category" value="상품/재입고">상품/재입고</option>
	<option name="ask_category" value="적립금">적립금</option>
	<option name="ask_category" value="회원 관련">회원 관련</option>
	<option name="ask_category" value="기타문의">기타문의</option>
</select>
<br><br>
제목 <input type="text" name="ask_title" placeholder="15자 이내 입력" value="" maxlength="15">

	<label><input type="checkbox" name="ask_open_ck" onchange="askSecCk();">비밀글</label>

<br><br>
내용<br>
<textarea name="ask_content" rows="10" cols="70" placeholder="문의사항을 입력해주세요."></textarea>
<br><br>

<div class="filebox">
<label for="ask_file"  class="btn btn-paosdy btn-lg">문의 파일 업로드</label><br>
<input type="file" name="ask_file" id="ask_file" accept='image/jpeg,image/gif,image/png' style="display: none;">
<br>
</div>
개인정보보호와 관련된 피해를 방지하기 위해 주민번호, 이메일, 연락처, 계좌번호 등의 개인정보와 관련된 내용의 기입은 삼가해 주시기 바랍니다. 확인 시 비밀글 전환이 될 수 있으며, 관련 피해는 binu에서 책임지지 않습니다.
<br><br>
＊용량이 2MB를 넘는 이미지는 업로드가 되지 않습니다.<br>
＊파일명이 한/영문,숫자를 제외한 특수문자, 공백이나 다른 나라 언어가 있을 경우 첨부가 불가능합니다.<br>
<input type="submit" value="등록하기" class="btn btn-paosdy btn-lg">
<input type="reset" value="다시쓰기" class="btn btn-paosdy btn-lg">
</form>
</body>
</html>