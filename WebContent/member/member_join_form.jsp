<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<!-- 2019 10 01 khy -->

<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/member/join.css" rel="stylesheet">


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
      
      $('#email_check').click(function (){
         member_email = $("#member_email").val();
         if(emailReg.test(member_email)){
            $.ajax({
               url: "./SendEmail",
               type : "post",
               data : {member_email : member_email},
               success: function(data){
               alert("E-mail이 발송되었습니다.");
               code = data;
               },
               error : function(error){
                  alert("error");
               }
            });
         } else {
            alert("E-mail 형식이 맞지 않습니다.");
            document.getElementById('member_email').focus()
         }
      });

      
      //passcheck
      $('#member_password').keyup(function (){
         member_password = $('#member_password').val();
         member_password2 = $('#member_password2').val("");
         $('#pass_txt2').html("");
         if(passReg.test(member_password)){
            $('#pass_msg').html('사용하실 수 있는 비밀번호');
            $('#pass_msg').css('color','blue');
            
         } else{
            $('#pass_msg').html('6~16 특수문자 포함 영문자 입력');
            $('#pass_msg').css('color',"red");
            
         }
      });
      //passcheck
      
      
      //passcheck2
      $('#member_password2').keyup(function (){
         member_password2 = $('#member_password2').val();
         if(member_password==member_password2){
            $('#pass_msg2').html("비밀번호가 일치합니다");
            $('#pass_msg2').css('color',"blue");
            password_check = 'ok';
         }else {
            $('#pass_msg2').html("비밀번호가 일치하지 않습니다");
            $('#pass_msg2').css('color',"red");
            password_check = "no";
         }
      });
      
      //passcheck2
      
      //namecheck
      $('#member_name').keyup(function (){
         member_name = $('#member_name').val();
         if(nameReg.test(member_name)){
            $('#name_msg').html("");
            name_check = "ok";;
            
         } else {
            $('#name_msg').html("한글 2~4 입력");
            $('#name_msg').css('color','red');
            name_check = "no";
         }
         
      });
      //namecheck
      
      
      //emailcheck
      $('#email_confirm').keyup(function (){
         email_confirm = $('#email_confirm').val();
         if(!(code == "") && email_confirm == code){
            $('#email_confirm_msg').html("인증번호가 일치합니다");
            $('#email_confirm_msg').css('color','blue');
            email_check="ok";
         } else {
            $('#email_confirm_msg').html("인증번호가 맞지 않습니다.");
            $('#email_confirm_msg').css('color','red');
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
      function member_join_submit() {
         if ( !(id_check=="ok") ) {
            document.getElementById('member_id').focus();
            return false;
         }
         if(!(member_password == member_password2)){
            document.getElementById('member_password').focus();
            alert("비밀번호가 일치하지 않습니다.");
            return false;
         }
         if(password_check == "no" || password_check == ""){
            document.getElementById('member_password').focus();
            alert("비밀번호가 일치하지 않습니다.");
            return false;
         }
         if(name_check == "no" || name_check ==""){
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
         
         if( !(email_check=="ok") ){
            document.getElementById('member_email').focus();            
            alert("e-mail 인증이 완료되지 않았습니다.");
            return false;
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

<div class="container-fluid row">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12">

 <div class="col-md-9 col-md-offset-3 gg">
  <fieldset class="field">
    <legend> 회 원 가 입 </legend>
    
    <form action="./MemberJoinAction.me" method="post" onsubmit="return member_join_submit();">
     <div class="row">
        <div class="form-inline">
         <label for="member_id" class="col-md-2 control-label">아이디</label> 
         <input type="text" name="member_id" id="member_id" class="form-control">
         <input type="button" id="id_check" class="btn btn-paosdy btn-lg" value="중복체크"><span id="id_msg"></span><br>
      </div><br>
        <div class="form-inline">
         <label for="member_password" class="col-md-2 control-label">비밀번호</label> 
         <input type="password" name="member_password" id="member_password" class="form-control"> <span id="pass_msg"></span><br>
      </div><br>         
      <div class="form-inline">
         <label for="member_password2" class="col-md-2 control-label">비밀번호 확인</label> 
         <input type="password" name="member_password2" id="member_password2" class="form-control"> <span id="pass_msg2"></span><br>
      </div><br>
      <div class="form-inline">
         <label for="member_name" class="col-md-2 control-label">이 름</label> 
         <input type="text" name="member_name" id="member_name" class="form-control"> <span id="name_msg"></span><br>
      </div><br>
      <div class="form-inline">
         <label for="member_birth" class="col-md-2 control-label">생년월일</label> 
         <input type="text" name="member_birth" id="member_birth" class="form-control"><br>
      </div><br>
      <div class="form-inline">
        <label for="radio" class="col-md-2 control-label">성별</label>
         <label class="radio-inline">
           <input type="radio" name="member_gender" id="inlineRadio1" value="남" <c:if test="${param.member_gender eq 'M' }">checked</c:if> >남
         </label>
         <label class="radio-inline">
           <input type="radio" name="member_gender" id="inlineRadio1" value="여" <c:if test="${param.member_gender eq 'F' }">checked</c:if> >여
         </label>         
      </div><br>
      <div class="form-inline">
         <label for="member_email" class="col-md-2 control-label">E-mail</label> 
         <input type="text" name="member_email" id="member_email" class="form-control"> <input type="button" id="email_check" class="btn btn-paosdy btn-lg" value="e-mail 전송"><br>
      </div><br>
      <div class="form-inline">
         <label for="email_confirm" class="col-md-2 control-label">인증번호</label> 
         <input type="text" name="email_confirm" id="email_confirm" class="form-control"> <span id="email_confirm_msg"></span><br>
      </div><br>            
      <div class="form-inline">
         <label for="member_phone" class="col-md-2 control-label">핸드폰번호</label> 
         <input type="text" name="member_phone" id="member_phone" class="form-control"><br>
      </div><br>
      <div class="form-inline">
         <label for="sample6_postcode" class="col-md-2 control-label">우편번호</label> 
         <input type="text" name="member_postcode" id="sample6_postcode" placeholder="검색해서 입력해주세요" class="form-control" readonly="readonly">
         <input type="button" onclick="sample6_execDaumPostcode()" value="주소검색" class="btn btn-paosdy btn-lg"><br>  
      </div><br>
      <div class="form-inline">
         <label for="sample6_address" class="col-md-2 control-label">주 소</label> 
         <input type="text" name="member_addr1" id="sample6_address" placeholder="검색해서 입력해주세요" class="form-control addr" readonly="readonly">        
      </div><br>      
       <div class="form-inline">
         <label for="sample6_detailAddress" class="col-md-2 control-label"></label> 
         <input type="text" name="member_addr2" id="sample6_detailAddress" class="form-control addr" placeholder="상세주소"><br>
      </div><br>
      <div class="col-md-4 col-md-offset-3">
          <input type="submit" class="btn btn-paosdy btn-lg" value="회원가입">
          <input type="button" class="btn btn-paosdy btn-lg" value="취 소" onclick="history.back()"><br>
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