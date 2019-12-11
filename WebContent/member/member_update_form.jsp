<%@page import="com.member.db.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- 2019 10 01 khy -->

<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/member/join.css" rel="stylesheet">
<link href="./CSS/item/navtab.css" rel="stylesheet">
<link href="./CSS/main/fonts.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">

<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">
   var member_id = "";
   var member_name = "";
   var member_password ="";
   var member_password2 = "";
   var member_email="";
   var email_confirm= "";
   var code = "";
   
   var idReg = /^[a-zA-Z]{1}[a-zA-Z0-9._-]{2,18}[a-zA-Z\d]{1}$/;
   var passReg = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/;
   var nameReg = /[가-힣]{2,4}/;
   var emailReg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
   var phoneReg =/^\d{11}$/;
   var birthReg =/^\d{8}$/;
   
   var id_check ="";
   var name_check ="";
   var password_check = "";
   var email_check ="";
   
   
   function email_send(email){
       member_email = $("#member_email").val();
       if(email != member_email){
	         if(emailReg.test(member_email)){
	            $.ajax({
	               url: "./SendEmail",
	               type : "post",
	               data : {member_email : member_email},
	               success: function(data){
	               alert("E-mail이 발송되었습니다.");
	               code = data;
	               $('#email_confirm').removeAttr("readonly");
	               },
	               error : function(error){
	                  alert("error");
	               }
	            });
	         } else {
	            alert("E-mail 형식이 맞지 않습니다.");
	            document.getElementById('member_email').focus()
	         }
       }else{
      	 alert("기존이메일은 인증할 필요가 없습니다");
       }
    }
   
   
   $(document).ready(function(){
      
      $('#member_id').keyup(function() {
         $('#id_txt').html("");
         $('#td_txt').css("color","black");
      });
      
      
      
      //idcheck
      $('#id_check').click(function (){
         member_id = $("#member_id").val();
         if(!idReg.test(member_id)){
            $('#id_msg').html("아이디는 영문자 시작 특수문자(_,-,.)포함 4~20자로 작성 해주세요. ");
            $('#id_msg').css("color","red");
         } else {
            $.ajax({
               url: "./MemberIdCheck",
               type :"post",
               data : {member_id : member_id},
               success:function(data){
                  if(data == 1){
                     $('#member_id').attr('readonly','true');
                     $('#id_msg').html('사용가능한 아이디 입니다.');
                     $("#id_msg").css("color","blue");
                     id_check = "ok";
                  }else if(data == 0){
                     $('#id_msg').html('중복된 아이디 입니다.');
                     $("#id_msg").css("color","red");
                  }
               },
               error : function(error){
                  alert("error");
               }
            });
         }
      });
      //idcheck
      
      //passcheck
      $('#member_password').keyup(function (){
         member_password = $('#member_password').val();
         member_password2 = $('#member_password2').val("");
         $('#pass_txt2').html("");
      });
      //passcheck
      
      
      //passcheck2
      $('#member_password2').keyup(function (){
         member_password2 = $('#member_password2').val();
         if(member_password==member_password2){
            $('#pass_msg2').html("ok");
            password_check = 'ok';
         }else {
            $('#pass_msg2').html("비밀번호 확인");
            password_check = "no";
         }
      });
      
      //passcheck2
      
      //namecheck
      $('#member_name').keyup(function (){
         member_name = $('#member_name').val();
         if(nameReg.test(member_name)){
            $('#name_msg').html("ok");
            name_check = "ok";;
            
         } else {
            $('#name_msg').html("한글 2~4 입력");
            name_check = "no";
         }
         
      });
      //namecheck
      
      
      //emailcheck
      $('#email_confirm').keyup(function (){
         email_confirm = $('#email_confirm').val();
         if(email_confirm == code){
            $('#email_confirm_msg').html("ok");
            email_check="ok";
         } else {
            $('#email_confirm_msg').html("인증번호가 맞지 않습니다.");
            email_check="no";
         }
      });
      //emailcheck
   });

</script>








</head>
<body>




<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
   <script type="text/javascript">
 
   // Kim pro 2019-10-01
      
 function sample6_execDaumPostcode() {
   
    new daum.Postcode({
         oncomplete: function(data) {
             // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

             // 각 주소의 노출 규칙에 따라 주소를 조합한다.
             // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
             var addr = ''; // 주소 변수
             var extraAddr = ''; // 참고항목 변수

             //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
             if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                 addr = data.roadAddress;
             } else { // 사용자가 지번 주소를 선택했을 경우(J)
                 addr = data.jibunAddress;
             }



             // 우편번호와 주소 정보를 해당 필드에 넣는다.
             document.getElementById('sample6_postcode').value = data.zonecode;
             document.getElementById("sample6_address").value = addr;
             // 커서를 상세주소 필드로 이동한다.
             document.getElementById("sample6_detailAddress").focus();
         }
     }).open();
   // Kim pro 2019-10-01
   
   
 }
</script>

   <script type="text/javascript">
      function member_join_submit(email) {
    	  
    	  
         if(document.getElementById('member_name').value == ""){
            document.getElementById('member_name').focus();
            alert("이름을 입력하세요");
            return false;
         }
         if(document.getElementById('member_birth').value == "" || !birthReg.test(document.getElementById('member_birth').value)){
            document.getElementById('member_birth').focus();
            alert("생년월일을 입력하세요");
            return false;
         }
         var gender_radio = document.getElementsByName('member_gender');
         if( (gender_radio[0].checked==false) && (gender_radio[1].checked==false) ){
      
            alert("성별을 체크하세요");
            return false;
         }
         
         if(document.getElementById('member_email').value == ""){
        	 document.getElementById('member_email').focus();
        	 alert("e-mail 을 입력해주세요");
        	 return false;
         }
         
         if(email != document.getElementById('member_email').value){
	         if( !(email_check=="ok") ){
	            document.getElementById('member_email').focus();            
	            alert("e-mail 인증이 완료되지 않았습니다.");
	            return false;
	         }
         }
         if(document.getElementById('member_phone').value == "" || !phoneReg.test(document.getElementById('member_phone').value) ){
            alert("전화번호는 하이픈 제외 숫자 11자리로 입력하세요 ex) 01012341234");
            document.getElementById('member_phone').focus();
            return false;
         }if(document.getElementById('sample6_postcode').value==""){
            document.getElementById('sample6_postcode').focus();
            alert("우편번호를 입력하세요.");
            return false;
         }if(document.getElementsByName('member_addr1').value==""){
            document.getElementByName('member_addr1').focus();
            alert("주소를 입력하세요.");
            return false;
         }if(document.getElementsByName('member_addr2').value=="" ){
            document.getElementsByName('member_addr2').focus();
            alert("상세주소를 입력하세요");
            return false;
         }
         
         
      }
   </script>

<%
   // 세션값 (id) 가져오기 
   // 없을경우 로그인페이지로 이동
   String member_id = (String)session.getAttribute("member_id");
   if(member_id == null){
	   response.sendRedirect("./MemberLogin.me");
   }
   // 회원정보객체를 가져오기 MemberUpdate 객체에서 생성한 정보
   
   // 전달받은 회원정보 객체를 form 태그에 정보를 표시 
	 MemberDTO mdto = (MemberDTO)request.getAttribute("mdto");   

%>


<div class="container-fluid row">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12">
<jsp:include page="/member/member_navtab.jsp"/>
 <div class="col-md-9 col-md-offset-3 gg">
  <fieldset class="field">
    <legend><h1> 회원정보 수정 </h1></legend>
    
    <form action="./MemberUpdateAction.me" method="post" onsubmit="return member_join_submit('<%=mdto.getMember_email()%>');">
     <div class="row">
        <div class="form-inline">
         <label for="member_id" class="col-md-2 control-label">아이디</label> 
         <input type="text" name="member_id" id="member_id" class="form-control" value="<%=mdto.getMember_id()%>" readonly="readonly">
      </div><br>
      
      <div class="form-inline">
         <label for="member_name" class="col-md-2 control-label">이 름</label> 
         <input type="text" name="member_name" id="member_name" class="form-control" value="<%=mdto.getMember_name()%>"> <span id="name_msg"></span><br>
      </div><br>
      <div class="form-inline">
         <label for="member_birth" class="col-md-2 control-label">생년월일</label> 
         <input type="text" name="member_birth" id="member_birth" class="form-control" value="<%=mdto.getMember_birth()%>"><br>
      </div><br>
      <div class="form-inline">
        <label for="radio" class="col-md-2 control-label">성별</label>
         <label class="radio-inline">
           <input type="radio" name="member_gender" id="inlineRadio1" value="남" <%if(mdto.getMember_gender().equals("남")){ %>checked="checked"<%} %>>남
         </label>
         <label class="radio-inline">
           <input type="radio" name="member_gender" id="inlineRadio1" value="여" <%if(mdto.getMember_gender().equals("여")){ %>checked="checked"<%} %>>여
         </label>         
      </div><br>
      <div class="form-inline">
         <label for="member_email" class="col-md-2 control-label">E-mail</label> 
         <input type="text" name="member_email" id="member_email" class="form-control" value="<%=mdto.getMember_email()%>"> <input type="button" id="email_check" class="btn btn-paosdy btn-lg" value="e-mail 전송" onclick="email_send('<%=mdto.getMember_email()%>');"><br>
      </div><br>
      <div class="form-inline">
         <label for="email_confirm" class="col-md-2 control-label">인증번호</label> 
         <input type="text" name="email_confirm" id="email_confirm" class="form-control" readonly="readonly"> <span id="email_confirm_msg"></span><br>
      </div><br>            
      <div class="form-inline">
         <label for="member_phone" class="col-md-2 control-label">핸드폰번호</label> 
         <input type="text" name="member_phone" id="member_phone" class="form-control" value="<%=mdto.getMember_phone()%>"><br>
      </div><br>
      <div class="form-inline">
         <label for="sample6_postcode" class="col-md-2 control-label">우편번호</label> 
         <input type="text" name="member_postcode" id="sample6_postcode" placeholder="검색해서 입력해주세요" class="form-control" readonly="readonly" value="<%=mdto.getMember_postcode()%>"><br>
      </div><br>
      <div class="form-inline">
         <label for="sample6_address" class="col-md-2 control-label">주 소</label> 
         <input type="text" name="member_addr1" id="sample6_address" placeholder="검색해서 입력해주세요" class="form-control addr" readonly="readonly" value="<%=mdto.getMember_addr1()%>"> <input type="button" onclick="sample6_execDaumPostcode()" value="주소검색" class="btn btn-paosdy btn-lg"><br>         
      </div><br>      
       <div class="form-inline">
         <label for="sample6_detailAddress" class="col-md-2 control-label"></label> 
         <input type="text" name="member_addr2" id="sample6_detailAddress" class="form-control addr" placeholder="상세주소" value="<%=mdto.getMember_addr2()%>"><br>
      </div><br>
        <div class="form-inline">
         <label for="member_password" class="col-md-2 control-label">비밀번호</label> 
         <input type="password" name="member_password" id="member_password" class="form-control"> <span id="pass_msg"></span><br>
      </div><br>         
      <div class="col-md-4 col-md-offset-3">
          <input type="submit" class="btn btn-paosdy btn-lg" value="회원수정" >
          <input type="reset" class="btn btn-paosdy btn-lg" value="취 소"><br>
      </div>
   </div>
    </form>
    
    
  </fieldset>
 </div>
</div>

<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>

   
   
   
  
</body>
</html>