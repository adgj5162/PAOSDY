<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 찾기</title>
<script src="./js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
   var matchIdtoEmail = "";
   var code = "#########";
   var member_id = "";
   var member_email = "";
   var member_name = "";
   $(document).ready(function() {

      $('#member_email').change(function() {
         member_name = $('#member_name').val();
         member_email = $('#member_email').val();
         $.ajax({
            url : "./MatchNameToEmail",
            type : "post",
            dataType : "text",
            data : {
               member_name : member_name,
               member_email : member_email
            },
            success : function(data) {
               if (data == "") {
                  member_id = data;
                  matchIdtoEmail = "no";
                  alert(member_email);
               } else {
                  member_id = data;
                  matchIdtoEmail = "ok";
               }
            },
            error : function(error) {
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
      
      $('#member_name').keyup(function() {
		$('#member_email').val("");
    	  matchIdtoEmail = "no";
	
	});

   });
</script>
<script type="text/javascript">
   function code_confirm() {
      if ($('#email_confirm').val() == code) {
         alert("인증성공 했습니다");
         $('#password_find_form').append('아이디  : ');
         $('#password_find_form').append(member_id);
         $('#password_find_form')
               .append(
                     '<input type="button" value="확인" onclick="window.close()">');
         $('#confirm').remove();
      } else {
         alert("인증번호가 일치하지 않습니다");
      }

   }
</script>
</head>
<body>
   <h1>아이디 찾기</h1>
   <form action="./PasswordFind" method="post" id="password_find_form"
      onsubmit="return password_find_form_submit()">
      이름<input type="text" id="member_name" name="member_name"><br>
      이메일 <input type="text" id="member_email" name="member_email">
      <input type="button" id="email_check" name="email_check"
         value="인증번호받기"><br> 인증번호 <input type="text"
         id="email_confirm" name="email_confirm">
         <input type="button" id="confirm" value="인증" onclick="code_confirm();"><br>
   </form>


</body>
</html>