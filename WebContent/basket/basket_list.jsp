<%@page import="com.goods.basket.db.BasketDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/fonts.css" rel="stylesheet">
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/basket/basket_list.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">

<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"> 
// 합계계산
$(document).ready(function() {
   update_amounts();
   $('.qty').change(function() {
      update_amounts();
   });
   function update_amounts() {
      var sum = 0;
      var total_point = 0;
      $('tr').each(function() {
         var qty = parseFloat($(this).find('.qty').val() || 0, 10);
         var price = parseFloat($(this).find('.price').val() || 0, 10);
         var amount = (qty * price);
         var basket_point = amount / 50;
         $(this).find('.basket_point').text(''+basket_point);
         $(this).find('.amount').text(''+amount);
         sum += amount;
         total_point += basket_point;
      });
      $('em.total').text(''+sum);
      // 제품이 하나도 없을 때 주문버튼 숨기기
      if(sum == 0){$('#order_basket').hide();}
      
      $('em.total_point').text(''+total_point);
      if(sum >= 5000){
         $('em.deliver_fee').text(''+0);
         $('span.deliver_fee').text(0);
         $('em.total_sum').text(sum);
      }else{
         $('em.deliver_fee').text(''+2500);
         $('span.deliver_fee').text(2500);
         sum += 2500;
         $('em.total_sum').text(sum);
      }
      console.log($('.amount').text());
   }
});

// 상품삭제
function basket_delete(basket_num){
      $.ajax({
         url : "./BasketDelete.bas",
         type : 'POST',
         data : {basket_num : basket_num},
         error : function() {alert('장바구니 상품 삭제 실패!!');},
         success: function(data){
            $('#basket_table').html(data);
         }
      });
   };

// 상품전체삭제
$(document).ready(function(){
   $('#delete_basket_all').click(function(){
      $.ajax({
         url : "./BasketDeleteAll.bas",
         type : "POST",
         data : {id : $('.basket_member_id').val()},
         error : function() {alert('장바구니 상품 삭제 실패!!');},
         success : function(data){
            $('#basket_table').html(data);
         }
      });
   });
});


// hidden값으로 값 넘기기
$(document).ready(function(){
   $('form').submit(function(){
      var total_point = $('em.total_point').val();
      $('input[name=total_point]').val(total_point);
      var total = $('em.total').val();
      $('input[name=total]').val(total);
      var total_sum = $('em.total_sum').val();
      $('input[name=total_sum]').val(total_sum);
      var deliver_fee = $('em.deliver_fee').val();
      $('input[name=deliver_fee]').val(deliver_fee);
      
      //구매가
      var basket_order_price = $('.amount').text();
      $('input[name=basket_order_price]').text(basket_order_price);
      
      
      
      //적립금
      var basket_point = $('.basket_point').text();
      $('input[name=basket_point]').text(basket_point);
   });
   //console.log($('.amount').text());
});

function update_basket_amount(basket_num,i){
   var basket_amount = $('#basket_amount'+i).val();
   location.href="./BasketUpdate.bas?basket_num="+basket_num+"&basket_amount="+basket_amount;
   alert("수량이 수정되었습니다");
}

</script>
</head>
<body>
<div id="basket_table" class="container-fluid row">
<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>
<%
int count = (Integer)request.getAttribute("count");
List<BasketDTO> basketList = (List<BasketDTO>)request.getAttribute("basketList");


%>
<div class="col-md-8 col-md-offset-2">
<h1> Cart </h1>
<form action="./Order.or" method="post" name="form">
<table id="forRowspan" class="table-hover table mt-2">
<tr class="basket_th">
 <td>상품정보</td>
 <td>판매가</td>
 <td class="qwer">수량</td>
 <td>구매가</td>
 <td>적립금</td>
 <td class="asdf">주문관리</td>
 <td>배송비</td>
</tr>
<%if(basketList == null){%>
<tr><td colspan="7">
장바구니에 담긴 상품이 없습니다
</td></tr>
<%}else{
   for(int i=0; i<basketList.size(); i++){ 
      BasketDTO basdto = (BasketDTO)basketList.get(i);
      int basket_num = basdto.getBasket_num();
      String basket_member_id = (String)request.getAttribute("basket_member_id");
%>
<tr>
 <td align="left">
 <a href="./ItemDetailAction.ite?item_num=<%=basdto.getBasket_item_num()%>">
    <img src="./upload/<%=basdto.getBasket_thumbnail() %>" width="80" height="80">
 <%=basdto.getBasket_item_name() %>&nbsp;<%=basdto.getBasket_size() %>사이즈&nbsp;<%=basdto.getBasket_color() %>색상</a>
 <input type="hidden" name="basket_num" id="basket_num" value="<%=basdto.getBasket_num() %>">
 <input type="hidden" name="total_point">
 <input type="hidden" name="total">
 <input type="hidden" name="total_sum">
 <input type="hidden" name="basket_order_price">
 <input type="hidden" name="basket_point">
 
 </td>
 <td><p><%=basdto.getBasket_price() %>원</p></td>
 <td class="qwer">
  <div>
   <input type="number" id="basket_amount<%=i %>" value="<%=basdto.getBasket_amount() %>" min="1" max="100" step="1" class="qty" name="qty" onkeyup="this.value = <%=basdto.getBasket_amount()%>">
   <input type="button" class="btn btn-paosdy btn-lg" value="수정" onclick="update_basket_amount(<%=basdto.getBasket_num()%>,<%=i%>);" class="btn btn-paosdy">
    <input type="hidden" value="<%=basdto.getBasket_price() %>" class="price" id="price" name="price" readonly >
    <input type="hidden" value="<%=basket_member_id %>" class="basket_member_id">
  </div>   
 </td>
 <td align="center"><p><span id="amount<%=i %>" class="amount"></span>원</p></td> <%//TODO 구매가 %>
 <td><p>+<span class="basket_point" id="basket_point<%=i %>"></span>원</p></td><%//TODO 각 적립금 %>
 <td class="asdf">   
  <div>
    <input type="button" value="상품삭제" class="basket_delete btn btn-paosdy" onclick="basket_delete(<%=basdto.getBasket_num()%>)">
  </div>   
 </td>
 <td class="deliver"><p><span class="deliver_fee" id="deliver_fee<%=i %>"></span>원</p></td>
</tr>
<%
   }
}
%>
</table>
<hr>
<div class="col-md-10">
총 상품가격
<em class="total" id="total"></em>&nbsp;원&nbsp;+&nbsp;배송비 
<em class="deliver_fee" id="deliver_fee"></em>&nbsp;원&nbsp;=
<em class="total_sum" id="total_sum"></em>&nbsp;원
<br>
적립금 총합 : <em class="total_point"></em>&nbsp;원
</div>

<div class="basket_button">
<input type="button" value="전체삭제" name="delete_basket_all" id="delete_basket_all" class="delete_basket_all btn-paosdy btn-lg">
<input type="button" value="쇼핑 계속하기" onclick="location.href='./ItemListAction.ite'" class="btn btn-paosdy btn-lg">
<input type="submit" value="주문하기" id="order_basket" class="btn btn-paosdy btn-lg">
</div>
</form>
</div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>
</body>
</html>