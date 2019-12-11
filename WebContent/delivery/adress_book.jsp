<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/main/fonts.css" rel="stylesheet">


<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">




<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>



</head>
<body>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
 <script type="text/javascript">
 
   // Kim pro 2019-10-10
 
 
    function check1(){
      
      if(document.fr.delivery_place_name.value == ""){
         alert("배송지명을 입력해주세요");
         document.fr.delivery_place_name.focus();
         return false;
      }
      
      if(document.fr.delivery_name.value == ""){
         alert("수령인을 입력해주세요");
         document.fr.delivery_name.focus();
         return false;
      }
      
      if(document.fr.delivery_phone.value == ""){
         alert("전화번호를 입력해주세요");
         document.fr.delivery_phone.focus();
         return false;
      }
      
      if(document.fr.sample6_postcode.value == ""){
         alert("우편번호가 없습니다.");
         document.fr.sample6_postcode.focus();
         return false;
      }
      
      if(document.fr.sample6_address.value == ""){
         alert("주소를 입력해주세요");
         document.fr.delivery_phone.focus();
         return false;
      }
      
      if(document.fr.sample6_detailAddress.value == ""){
         alert("상세주소를 입력해주세요");
         document.fr.sample6_detailAddress.focus();
         return false;
      }
      
   }
 
      
      
      
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
    
    
      // Kim pro 2019-10-10
 }
</script>

   <%
      //세션 객체안에 있는 id값을 가져와서 로그인여부 판단.
      String member_id =(String)session.getAttribute("member_id");
   
       if(member_id == null){
          response.sendRedirect("./MemberLogin.me");
       }
   
      // 아이디가 있을경우  " ㅇㅇㅇㅇ님이 로그인하셨습니다" 페이지 출력
   %>
   
<div align="center">
 <fieldset>
    <legend> <h2>주소록 추가</h2> </legend>
    
    <form action="DeliverySave.de" method="post" name="fr" onsubmit="return check1();">
<input type="hidden" name="member_id" value="<%=member_id%>"><br>
   <table>
      <tr>
         <td align="right">배송지명 &nbsp;</td>
         <td><input type="text" name="delivery_place_name" id="delivery_place_name"></td>
      </tr>
      <tr>
         <td align="right">수령인 &nbsp;</td>
         <td><input type="text" name="delivery_name" id="delivery_name"></td>
      </tr>
      <tr>
         <td align="right">휴대전화 &nbsp;</td>
         <td><input type="text" name="delivery_phone" id="delivery_phone" placeholder="숫자만 입력하세요" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" maxlength="11"></td>
       </tr>
       <tr>
          <td align="right">우편번호 &nbsp;</td>
          <td><input type="text" name="delivery_postcode" id="sample6_postcode" readonly="readonly"></td>
       </tr>
       <tr>
          <td align="right">주소 &nbsp;</td>
          <td><input type="text" name="delivery_addr1" id="sample6_address" placeholder="주소" readonly="readonly"> 
            <input type="button" onclick="sample6_execDaumPostcode()" value="주소 검색"></td>
      </tr>
      <tr>
         <td align="right">상세주소 &nbsp;</td>
         <td><input type="text" name="delivery_addr2" id="sample6_detailAddress" placeholder="상세주소"></td>
       </tr>
      
      <tr><td colspan="2" align="center">
         <br>
          <input type="submit" value="등록하기">
          <input type="reset" value="다시쓰기">
          <input type="reset" value="취소" onclick="window.close()">
      </td></tr>
   </table>
    </form>
    
  </fieldset>
      
</div>
      
</body>
</html>