<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 찾기</title>

<link href="./CSS/main/layout.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">

<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
<script src="./js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	var matchIdtoEmail = "";
	var code = "";
	var member_id = "";
	var member_email = "";
	var passReg = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;
	$(document).ready(function() {

		$('#member_email').keyup(function() {
			member_id = $('#member_id').val();
			member_email = $('#member_email').val();
			$.ajax({
				url : "./MatchPasswordToEmail",
				type : "post",
				dataType : "text",
				data : {
					member_id : member_id,
					member_email : member_email
				},
				success : function(data) {
					if (data == 1) {
						matchIdtoEmail = "ok";
					} else {
						matchIdtoEmail = "";
					}
				},
				error : function(error) {
					alert("error");
				}
			});
		});

		$('#email_check').click(function() {
			if (matchIdtoEmail == "ok") {
				$.ajax({
					url : "./SendEmail",
					type : "post",
					data : {
						member_email : member_email
					},
					success : function(data) {
						code = data;
						alert("email 인증번호를 확인하세요");
					},
					error : function(error) {
						alert("error");
					}
				});
			} else {
				alert("일치하는 이메일이 없습니다.");
			}
		});
		

	});
</script>
<script type="text/javascript">
	function password_find_form_submit(){
		if(!passReg.test(document.getElementById('member_password').value)){
			alert("비밀번호 양식이 맞지않습니다.");
			return false;
		}
		
		if(!(document.getElementById('member_password').value == 
			document.getElementById('member_password2').value) ){
			alert("비밀번호가 일치하지 않습니다");
			return false;
		}
		alert("비밀번호가 변경되었습니다");
		
	}
	
	function code_confirm(){
		if($('#email_confirm').val() == code){
			alert("인증성공 했습니다");
			$('#password_find_form').append('변경할 비밀번호 <input type="password" name="member_password" id="member_password"><br>');
			$('#password_find_form').append('비밀번호 확인 <input type="password" name="member_password2" id="member_password2"><br>');
			$('#password_find_form').append('<input type="submit" value="수정하기">')
		}else{
			alert("인증번호가 일치하지 않습니다");
		}
		
	}
</script>
</head>
<body>
	<h1>Find PW</h1>
	<form action="./PasswordFind" method="post" id="password_find_form" onsubmit="return password_find_form_submit()">
		아이디 <input type="text" id="member_id" name="member_id"><br>
		이메일 <input type="text" id="member_email" name="member_email">
		<input type="button" id="email_check" name="email_check" value="인증번호받기"><br>
		인증번호 <input type="text" id="email_confirm" name="email_confirm"><input type="button" value="인증" onclick="code_confirm();"><br>
	</form>


</body>
</html>