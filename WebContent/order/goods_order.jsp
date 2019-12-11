<%@page import="com.item.db.ItemDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.goods.basket.db.BasketDTO"%>
<%@page import="com.delivery.db.DeliveryDTO"%>
<%@page import="com.member.db.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/fonts.css" rel="stylesheet">
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/order/goods_order.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">

<script src="https://code.jquery.com/jquery-3.4.1.js"
   integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
   crossorigin="anonymous"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdn.bootpay.co.kr/js/bootpay-3.0.6.min.js" type="application/javascript"></script>
<!-- 결제모듈 스크립트 파일 -->
<!-- 우편주소 api 스크립트 코드 시작 -->
<!-- jQuery와 Postcodify를 로딩한다 -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>

<!-- "검색" 단추를 누르면 팝업 레이어가 열리도록 설정한다 -->
<script> $(function() { $("#postcodify_search_button").postcodifyPopUp(); }); </script>
<!-- 우편주소 api 스크립트 코드 끝 -->


<!-- 결제스크립트 -->
<script src="https://nsp.pay.naver.com/sdk/js/naverpay.min.js"></script>
<script type="text/javascript"
   src="https://pay.kcp.co.kr/plugin/payplus_web.jsp"></script>
<!-- 결제 스크립트 -->

<%
   //정보 가져오기
   
   MemberDTO mdto = (MemberDTO) request.getAttribute("mdto");
   List deliveryList = (List) request.getAttribute("deliveryList");
   List basketList = (List) request.getAttribute("basketList");
   ItemDTO idto = (ItemDTO) request.getAttribute("idto");
   
   /**메일자르기**/
   String mail = mdto.getMember_email();
   int atIdx = mail.indexOf("@"); //먼저 @의 인덱스를 찾는다
   String mail1 = mail.substring(0, atIdx); //@앞부분을 추출
   String mail2 = mail.substring(atIdx+1); //뒷부분을 추출
   /**메일자르기**/
   
   /**연락처자르기**/
   String phone1 = mdto.getMember_phone().substring(0,3);
   String phone2 = mdto.getMember_phone().substring(3,7);
   String phone3 = mdto.getMember_phone().substring(7,11);
   /**연락처자르기**/
%>

<script type="text/javascript">
   
$(document).ready(function(){
   $('#delivery_list').change(function(){
      "<c:forEach var="delist" varStatus="status" items="${deliveryList }">"
     
         if($('#delivery_list option:selected').val()=="${delist.delivery_place_name}"){
         $('input[name=order_recipient_name]').val("${delist.delivery_name }"); //이름
         $('input[name=order_recipient_phone]').val("${delist.delivery_phone }"); //휴대전화2
         $('input[name=postcode]').val("${delist.delivery_postcode}"); //postcode
         $('input[name=addr1]').val("${delist.delivery_addr1}"); //addr1
         $('input[name=addr2]').val("${delist.delivery_addr2}"); //addr2
         }
      "</c:forEach>"
   });
   
   $('#address_self').click(function(){
      $('input[name=order_recipient_name]').val(""); //이름
      $('input[name=order_recipient_phone]').val(""); //휴대전화2
      $('input[name=postcode]').val(""); //postcode
      $('input[name=addr1]').val(""); //addr1
      $('input[name=addr2]').val(""); //addr2
   });
   
   $('#address_member').click(function(){
      $('input[name=order_recipient_name]').val("<%=mdto.getMember_name()%>"); //이름
      $('input[name=order_recipient_phone]').val("<%=phone1%>-<%=phone2%>-<%=phone3%>"); //휴대전화2
      $('input[name=postcode]').val("<%=mdto.getMember_postcode()%>"); //postcode
      $('input[name=addr1]').val("<%=mdto.getMember_addr1()%>"); //addr1
      $('input[name=addr2]').val("<%=mdto.getMember_addr2()%>"); //addr2
   });
});

</script>
<%//TODO 입력제어%>
<script type="text/javascript">

   
function order_fr_ck(){
   if(document.order_form.total_sum.value == 2500){
      alert("주문중인 상품이 없습니다. 쇼핑 페이지로 이동합니다.")
      location.href="./ItemListAction.ite";
      return false;
   }
   
   if(document.order_form.order_recipient_name.value==""){
      alert("수령인명을 입력해주세요.");
      document.order_form.order_recipient_name.focus();
      return false;
   }

   if(document.order_form.order_recipient_phone.value==""){
        alert("수령인의 연락처를 입력해주세요");
        document.order_form.order_recipient_phone.focus();
        return false;
   }
   if(document.order_form.postcode.value=="" || 
         document.order_form.addr1.value=="" ){
        alert("상품을 수령할 주소를 입력해주세요");
        document.order_form.postcode.focus();
        return false;
   }
   if(document.order_form.addr2.value==""){
      alert("상세 주소를 입력해주세요")
      document.order_form.addr2.focus();
      return false;
   }
   
   if(document.order_form.order_payer_name.value==""){
        alert("주문자명을 입력해주세요.");
        document.order_form.order_payer_name.focus();
        return false;
   }
   if(document.order_form.order_payer_phone1.value=="" || 
         document.order_form.order_payer_phone2.value=="" || 
         document.order_form.order_payer_phone3.value==""){
        alert("주문자의 연락처를 입력해주세요");
        document.order_form.order_payer_phone1.focus();
        return false;
   }
   
   if($('input[name=order_payment]').is(':checked') == false){
      alert("결제 수단을 선택하세요.");
      return false;
   }

   if($('#all_agree').is(':checked') == false){
      alert("개인정보 제3자 제공 동의하셔야 결제가 진행됩니다.");
      return false;
   }
   
   if($('#agree_order').is(':checked') == false){
      alert("구매 진행에 동의하셔야 결제가 진행됩니다.");
      return false;   
   }
   var order_payment = $('input[name="order_payment"]:checked').val();
   
   $(document).ready(function(){
      $.ajax({
         url : './Payment.or',  
         type : 'POST',
         data : {total_sum : $('#total_sum').val(),
               order_payment : order_payment,
               order_payer_email1 : $('#email1').val(),
               order_payer_email2 : $('#email2').val(),
               order_payer_name : $('#order_payer_name').val(),
               order_payer_phone1 : $('#phone1').val(),
               order_payer_phone2 : $('#phone2').val(),
               order_payer_phone3 : $('#phone3').val(),
               order_recipient_phone : $('#order_recipient_phone').val(),
               postcode : $('#postcode').val(),
               addr1 : $('#addr1').val(),
               addr2 : $('#addr2').val(),
               memo : $('#memo').val(),
               rest_point : $('#rest_point').val(),
               new_point : $('#new_point').val(),
               order_recipient_name : $('#order_recipient_name').val(),
               item_name : $('#item_name0').val()},
         success : function(data){
            $('body').html(data);
         }      
      });
   });   
}

</script>
<script type="text/javascript">
$(document).ready(function() {
   if(<%=mdto.getMember_point() %><5000){
      $('input[name=use_point]').attr("readonly",true).attr("disabled",true);
   }else{
      $('input[name=use_point]').attr("readonly",false).attr("disabled",false);
   }
   
});


function use_point_fun() {
   
   if(document.getElementById("use_point").value > <%=mdto.getMember_point() %>){
      document.getElementById("use_point_alert").innerHTML="적립금보다 많은 금액을 사용할 수 없습니다.";
      document.getElementById("use_point").value = <%=mdto.getMember_point() %>;
      $('#discount').text(<%=mdto.getMember_point() %>+"원");
      var money = $('#total_prepared').val() - <%=mdto.getMember_point() %>;
      $('#total_pay_lcs').text(돈);
      $('#total_sum').val(돈);
      
      return false;
   }
   
   if(Number(document.getElementById("use_point").value) > Number(document.getElementById("total_prepared").value)){
      document.getElementById("use_point_alert").innerHTML="결제금액보다 많은 금액을 사용할 수 없습니다.";
      document.getElementById("use_point").value = document.getElementById("total_prepared").value
      $('#discount').text(document.getElementById("total_prepared").value+"원");
      var money = $('#total_prepared').val() - document.getElementById("total_prepared").value;
      $('#total_pay_lcs').text(돈);
      $('#total_sum').val(돈);
      
      return false;
   }
   
   if(document.getElementById("use_point").value <= <%=mdto.getMember_point() %>){
      document.getElementById("use_point_alert").innerHTML="보유 적립금 "+(<%=mdto.getMember_point() %>-document.getElementById("use_point").value)+"남았습니다.";
      document.getElementById("rest_point").value=(<%=mdto.getMember_point() %>-document.getElementById("use_point").value);
      
   }
}


//주문자 동의 상세설명
$(document).ready(function(){
   $('span#agree_detail').hide();
   $('a#agree_detail_btn').click(function(){
      $('span#agree_detail').toggle();
      if ($('span#agree_detail').is(':visible')) {
         $('a#agree_detail_btn').text('닫기');                
     } else {
        $('a#agree_detail_btn').text('자세히');                
     }
   });
   
   $(window).on('load',function(){
      var money = $('#total_prepared').val();
      $('#total_pay_lcs').text(money+"원");
   });
   
});


function usePoint(point){
   $('#discount').text(point+"원");
   var money = $('#total_prepared').val() - point;
   $('#total_pay_lcs').text(money+"원");
   
   $('#total_sum').val(돈);
}



</script>


</head>
<body>
<div class="container-fluid">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>
<div class="col-md-8 col-md-offset-2">

<h1 id="order">Order / Payment</h1>

<hr>
<form name="order_form" id="order_form">

<h2>Product</h2>   

<table class="table-hover table mt-2" id="order_product">
   <tr>
      <th>상품 정보</th>
      <th>수량</th>
      <th>상품 금액</th>
      <th>적립금</th>
      <th>주문금액</th>
   </tr>

   <%
   int total_point=0;
   int total=0;
   
   for(int i=0;i<basketList.size();i++){
      BasketDTO bdto = (BasketDTO)basketList.get(i);
      int basket_num = bdto.getBasket_num();

      total += bdto.getBasket_amount() * bdto.getBasket_price();
      total_point = (total * 2) / 100;
   %>
   
   <tr>
      <td align="left">
         <img src="./upload/<%=bdto.getBasket_thumbnail() %>" width="80" height="80">
            상품명: <%=bdto.getBasket_item_name() %>
              옵션 : <%=bdto.getBasket_size()%>, <%=bdto.getBasket_color()%>

         <input type="hidden" name="item_name" id="item_name<%=i %>" value="<%=bdto.getBasket_item_name() %>
               <%if(basketList.size() != 1){ %>외 <%=(basketList.size()-1) %>개 상품 <%} %>">
         <input type="hidden" name="size" value="<%=bdto.getBasket_size()%>">
         <input type="hidden" name="color" value="<%=bdto.getBasket_color()%>">
         <input type="hidden" name="price" value="<%=bdto.getBasket_price() %>">
         <input type="hidden" name="item_name" value="<%=bdto.getBasket_item_name() %>">
         <input type="hidden" name="amount" value="<%=bdto.getBasket_amount()%>">

      </td>
      <td><span><%=bdto.getBasket_amount()%>개</span></td>
      <td><span><%=bdto.getBasket_price() %>원</span></td>
      <td><span><%=((bdto.getBasket_amount() * bdto.getBasket_price()) * 2) / 100 %>원</span></td>
      <td><span><%=bdto.getBasket_amount() * bdto.getBasket_price()%>원</span></td>
   </tr>
   <%}
   int total_sum = total+(total>5000 ? 0 : 2500);
   total_point = total*2/100;
   %>
   <tr>
      <td colspan="5" style="text-align: right;">
   <div class="order_font">   
      총 상품 금액: <%=total %>원<br>
      <input type="hidden" id="total_prepared" value="<%=total+(total>5000? 0 : 2500) %>">
      <input type="hidden" name="total_sum" id="total_sum" value="<%=total+(total>5000 ? 0 : 2500)%>">
                  
         총 적립 예정금액: <%=total*2/100%>원<br>
         배송비: <%=total>5000 ? 0 : 2500%>원<br>
         할인금액: <span id="discount">0원</span><br>
    <div id="total_pay_lcs1">총 결제 금액: <span id="total_pay_lcs" name="total_pay_lcs"></span></div>
   </div>      
      </td>
   </tr>
</table>

<hr>
<h2>Discount</h2> 
<!-- 추가 할인 -->
<br>
<table border="1" id="discount" class="table-hover table mt-2">
   <tr>
      <td class="use_po">
       <p>
         보유 적립금 사용
       </p>
      </td>
      <td>5,000원 이상 보유 시 사용 가능합니다. (총 보유 적립금 <%=mdto.getMember_point() %>원)<br>
      보유 적립금 사용 <input type="text" name="use_point" id="use_point" onblur="use_point_fun();" onkeyup="usePoint(this.value);">원
      <br>
      <div id="use_point_alert"></div>
      <input type="hidden" name="rest_point" id="rest_point" value="0">
      <input type="hidden" name="new_point" id="new_point" value="<%=total*2/100%>">
      </td>   
   </tr>
</table>
<hr>

<h2>Recipient Info</h2>
<table border="1" id="recipient_info" class="table-hover table mt-2">
   <tr>
      <td class="use_po"><p>배송지 선택</p></td>
      <td>
         <label><input type="radio" name="adress_chk" id="address_self" onClick="this.form.delivery_list.disabled=true" checked>직접 입력</label>&nbsp;
         <label><input type="radio" name="adress_chk" id="address_member" onClick="this.form.delivery_list.disabled=true">기본 배송지(구매자 정보)</label>&nbsp;
         <label><input type="radio" name="adress_chk" id="address_list" onClick="this.form.delivery_list.disabled=false">주소록 
         <select name="delivery_list" id="delivery_list" disabled="disabled">
            <option value="" selected="selected">주소록 선택</option>
            <%for(int i=0;i<deliveryList.size();i++){
               DeliveryDTO deldto = (DeliveryDTO)deliveryList.get(i);%>
            <option id="delivery_list+<%=i%>"><%=deldto.getDelivery_place_name() %></option>
            <%} %>
         </select></label>
         <label><a style="color: blue" onclick="window.open('./DeliveryList.de?order=1','주소록 관리','width=700,height=500,location=no,status=no,scrollbars=yes');" target="_blank">[주소록 편집]</a></label>
      </td>
   </tr>
   <tr>
      <td class="use_po"><p>수령인/배송지명</p></td>
      <td><input type="text" name="order_recipient_name" id="order_recipient_name" value="" placeholder="이름"></td>
   </tr>
   <tr>
      <td class="use_po"><p>휴대전화</p></td>
      <td>
         <input type="text" name="order_recipient_phone" id="order_recipient_phone" value="" placeholder="연락처">
      </td>
   </tr>
   <tr>
      <td class="use_po"><p>배송지 주소</p></td>
      <td><input type="text" name="postcode" class="postcodify_postcode5" id="postcode" value="" placeholder="우편번호" readonly="readonly"> 
               <input type="button" id="postcodify_search_button" value="우편번호 찾기" class="btn btn-paosdy"><br>
               <input type="text" name="addr1" id="addr1" class="postcodify_address" value="" placeholder="도로명 주소" readonly="readonly"> 
               <input type="text" name="addr2" id="addr2" class="postcodify_details" value="" placeholder="상세 주소"> 
      </td>
   </tr>
   <tr>
      <td class="use_po"><p>배송 메모</p></td>
      <td>
         <textarea rows="3" cols="70" name="memo" id="memo" placeholder="택배기사님께 전할 말씀을 남겨주세요"></textarea><br> 
         ※ 택배기사님께 전할 말씀을 남겨주세요.</td>
   </tr>
</table>
<hr>

<h2 id="payment">Payment Info</h2>
<table border="1" id="payment_info" class="table-hover table mt-2">
   <tr>
      <td class="use_po"><p>주문자명</p></td>
      <td><input type="text" value="<%=mdto.getMember_name() %>" name="order_payer_name" id="order_payer_name" placeholder="이름"></td>
   </tr>
   <tr>
      <td class="use_po"><p>휴대전화</p></td>
      <td><input type="text" name="order_payer_phone1" id="phone1" value="<%=phone1%>"> - <input type="text" name="order_payer_phone2" id="phone2" value="<%=phone2%>"> - <input type="text" name="order_payer_phone3" id="phone3" value="<%=phone3%>"></td>
   </tr>
   <tr>
      <td class="use_po"><p>이메일</p></td>
      <td>
         <input type="text" name="order_payer_email1" id="email1" value="<%=mail1%>">@<input type="text" name="order_payer_email2" id="email2" value="<%=mail2%>"> 
            <select id="order_payer_email3" onchange='$("#email2").val($(this).val())'>
               <option value="" selected>직접입력</option>
               <option value="naver.com" <%if(mail2.equals("naver.com")){ %>selected <%} %>>네이버</option>
               <option value="nate.com" <%if(mail2.equals("nate.com")){ %>selected <%} %>>네이트</option>
               <option value="daum.net" <%if(mail2.equals("daum.net")){ %>selected <%} %>>다음</option>
               <option value="gmail.com" <%if(mail2.equals("gmail.com")){ %>selected <%} %>>구글</option>
            </select>
      </td>
   </tr>
</table>


<hr>
<h2>Agreement</h2>
<table border="1" id="agreement" class="table mt-2">
   
   <tr>
      <td class="use_po"><p>결제수단</p></td>
      <td>
         <label><input type="radio" name="order_payment" id="order_payment" value="card">신용카드</label>&nbsp;
         <label><input type="radio" name="order_payment" id="order_payment" value="vbank">가상계좌(무통장)</label>&nbsp; 
         <label><input type="radio" name="order_payment" id="order_payment" value="bank">실시간 계좌이체</label>&nbsp;
         <label><input type="radio" name="order_payment" id="order_payment" value="kakao">카카오페이</label>&nbsp;
         <label><input type="radio" name="order_payment" id="order_payment" value="phone">휴대폰</label>&nbsp;
         <label><input type="radio" name="order_payment" id="order_payment" value="payco">페이코</label>
      </td>
   </tr>
   
   <tr>
      <td class="use_po" id="back_color"><p>주문자 동의</td>
      <td id="back_color"><br><label><input type="checkbox" name="all_agree" id="all_agree">개인정보 제3자 제공 동의(필수)</label>
         배송 등 거래를 위해 판매자에게 개인정보가 공유됩니다. <a id="agree_detail_btn">자세히</a>
         <br>
         <span id="agree_detail" style="width: 50px;"><br>
PAOSDY의 회원계정으로 상품 및 서비스를 구매하고자 할 경우, PAOSDY는 거래 당사자간 원활한 의사소통 및 배송, 상담 등 거래이행을 위하여<br>
필요한 최소한의 개인정보만을 PAOSDY 입점업체 판매자 및 배송업체에 아래와 같이 공유하고 있습니다.<br>
1. PAOSDY는 귀하께서 PAOSDY 입점업체 판매자로부터 상품 및 서비스를 구매하고자 할 경우, <br>
정보통신망 이용촉진 및 정보보호 등에 관한 법률 제 24조의 2(개인정보 공유동의 등)에 따라 아래와 같은 사항은 안내하고 동의를 받아 귀하의 개인정보를 판매자에게 공유합니다. <br>
"개인정보 제3자 공유 동의"를 체크하시면 개인정보 공유에 대해 동의한 것으로 간주합니다.<br>
2. 개인정보를 공유받는자 : <br>
3. 공유하는 개인정보 항목<br>
- 구매자 정보: 성명, 전화번호, ID, 휴대전화 번호, 메일주소, 상품 구매정보<br>
- 수령자 정보: 성명, 전화번호, 휴대전화 번호, 배송지 주소<br>
4. 개인정보를 공유받는 자의 이용 목적 : 판매자와 구매자의 거래의 원활한 진행, 본인의사의 확인, 고객 상담 및 불만처리, 상품과 경품 배송을 위한 배송지 확인 등<br>
5. 개인정보를 공유받는 자의 개인정보 보유 및 이용 기간 : 개인정보 수집 및 이용 목적 달성 시까지 보관합니다.<br>
6. 동의 거부 시 불이익 : 본 개인정보 공유에 동의하지 않으시는 경우, 동의를 거부할 수 있으며, 이 경우 거래가 제한됩니다.<br><br>
         </span>
         <br>
         <label><input type="checkbox" name="agree_order" id="agree_order">위 상품 정보 및 거래 조건을 확인하였으며, 구매 진행에 동의합니다.(필수)</label><br>
         <br>
      </td>
   </tr>
</table>
<div class="col-md-3 col-md-offset-9">
<input type="button" id="pay_button" value="결제하기" class="pay btn btn-paosdy btn-lg" onclick="return order_fr_ck();">
</div>
</form>
<!--    <input type="button" value="결제" onclick="window.open('URL','','width=550,height=700,location=no,status=no,scrollbars=yes');">
 -->
</div>
<div class="col-md-12 footer" id="footer"> <jsp:include page="/main/footer.jsp"/> </div>

</div>

</body>
</html>