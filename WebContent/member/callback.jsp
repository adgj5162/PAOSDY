<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<meta charset="UTF-8">
<head>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
<script type="text/javascript">
  var naver_id_login = new naver_id_login("bgpp4MGt8zAWcTgB8xXF", "http://itwillbs11.cafe24.com/PAOSDY/CallBack.me");
  // 접근 토큰 값 출력

  
  // 네이버 사용자 프로필 조회
  naver_id_login.get_naver_userprofile("naverSignInCallback()");
  // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
  
  

  
  function naverSignInCallback() {
   var member_email = naver_id_login.getProfileData('email');
   var member_name = naver_id_login.getProfileData('name');
   var member_gender = naver_id_login.getProfileData('gender');
   $.ajax({
      url: "./NaverEmailCheck",
      type :"post",
      data : {member_email : member_email},
      success:function(data){
         if(data == 1){
            window.opener.document.location.href="./Main.me";
            window.close();
         }if(data == 0){
              alert('가입페이지로 이동합니다.');
              window.opener.name = "parentPage";
              document.nil_frm.target="parentPage";
              document.nil_frm.action="./MemberJoin.me";
              document.getElementsByName('member_email')[0].value=member_email;
              document.getElementsByName('member_name')[0].value=member_name;
              document.getElementsByName('member_gender')[0].value=member_gender;
              document.nil_frm.submit();
              window.close();
         }
      },
      error : function(error){
         alert(error);
      }
   });
  }
</script>
   <form method="post" name="nil_frm">
      <input type="hidden" name="member_email" value="">
      <input type="hidden" name="member_name" value="">
      <input type="hidden" name="member_gender" value="">
   </form>
</body>
</html>