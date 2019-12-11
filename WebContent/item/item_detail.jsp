<%@page import="com.item.db.ItemDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="-1"/>
<title>PAOSDY</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="./js/jquery.raty.js"></script>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/item/itemdetail.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	
	$(window).on('load',function(){
		
		var item_num = $('#item_num').val();
		
		$.ajax({
			url:"./ReviewList.re",
			type:"post",
			data: {item_num : item_num,},
			success:function(data){
				
				$('#review').html(data);
			
			}
			  
		});
		
		$.ajax({
			url:"./AskList.qa",
			type:"post",
			data: {ask_item_num : item_num,},
			success:function(data){
				
				$('#ask').html(data);
			
			}
			  
		});
		
		$.ajax({
			url: "./ItemBasketAction.ite",
			type : "POST",
			data : {NoAdd:1, basket_item_num:item_num},
			error : function() {alert('???관리자에게 문의하세요');},
			success: function(data){
				console.log(data);
				$("#item_basket").html(data);
			}
	    });
	});
	
	$(document).ready(function(){
		$(window).on('load',function(){
			
			$('#isWish').click();
			console.log($('#isWish').val());
		});
	});
	
	function checkWish(page_num, id){
		
		if(id == "null"){
			return false;
		}
		
		$.ajax({
			url:"./checkWish",
			type:"post",
			data: {page_num:page_num, id:id},
			success:function(data){
				
				console.log(data);
				
				if(data == 1){
					$('#isWish').val("1");
					$('#wish').css({"background":"url(./img/heart-line.png)"});
				}else{
					$('#wish').css({"background":"url(./img/heart-fill.png)"});
					$('#isWish').val("2");
				}
				
			
			}
			  
		});
	}
	

	function addWish(page_num, id){
		
		if(id == "null"){
			alert("로그인상태에서만 가능합니다");
			return false;
		}
		
		
		var wishValue = $('#isWish').val();
		var item_name = $('#item_name').val();
		var item_thumbnail = $('#item_thumbnail').val();
		
		$.ajax({
			url:"./addWish",
			type:"post",
			data: {page_num:page_num, id:id, wishValue:wishValue, item_name:item_name, item_thumbnail:item_thumbnail},
			success:function(data){
				
				if($('#isWish').val() == "1"){
					alert("찜목록에 추가 되었습니다");
					$('#wish').css({"background":"url(./img/heart-fill.png)"});
					$('#isWish').val("2");
					return;
				}
				
				if($('#isWish').val() == "2"){
					alert("찜목록에 삭제 되었습니다");
					$('#wish').css({"background":"url(./img/heart-line.png)"});
					$('#isWish').val("1");
					return;
				}
				
			
			}
			  
		});
	  	return false;
	}

	function writeReview(){
		
		var review_inset_open = $('#review_inset_open').val();
		var item_num = $('#item_num').val();
		
		console.log($('#review_inset_open').val());
		console.log($('#item_num').val());

		if($('#member_id').val()=="null"){
			alert("로그인상태에서만 가능합니다");
			return false;
		}
		
		if(review_inset_open != "1"){
			$.ajax({
				url:"./ReviewWrite.re",
				type:"post",
				data: {item_num : item_num},
				success:function(data){
					
					$('#review_insert').html(data);
					var reviewPosition = $('#review_insert').offset();
					
					$('html,body').animate({scrollTop: reviewPosition.top}, 500 );
				
				}
				  
			});
			$('#review_inset_open').val("1");
			console.log($('#review_inset_open').val());
			return false;
		}else{
			$('#review_insert').html("");
			$('#review_inset_open').val("0");
			console.log($('#review_inset_open'));
			return false;
		}
	}
	
	function review_submit(){
		
		var form = $("#review_form")[0];        
        var formData = new FormData(form);
			
			$.ajax({
				url:"./ReviewWriteAction.re",
				type:"post",
				processData: false,
				contentType: false,
				data: formData,
					
				success:function(data){
					
					console.log(data);
					
					$('#review').html(data);
					$('#review_insert').html("");
					$('#review_inset_open').val("0");
					
				}
			});
			
			alert("작성 완료");
	}
	
	
	// 후기 페이징
	
		//페이지 번호
		function review_page(pageNum) {
			console.log(pageNum);
		var item_num = $('#item_num').val();
			
			$.ajax({
				url: "./ReviewList.re",
				type : "post",
				data : { pageNum:pageNum, item_num : item_num },
				error : function() {alert('페이징 처리 실패!!');},
				success: function(data){
					console.log(data);
					$("#review").html(data);
				}
		    });
		}
	
	// 후기 페이징
	
	
	// 후기수정
	
	function review_update(review_num){
		$.ajax({
			url: "./ReviewUpdate.re",
			type : "POST",
			data : {review_num:review_num},
			error : function() {alert('페이징 처리 실패!!');},
			success: function(data){
				console.log(data);
				$("#review_insert").html(data);
				var reviewPosition = $('#review_insert').offset();
				
				$('html,body').animate({scrollTop: reviewPosition.top}, 500 );
				$('#review_inset_open').val("1");
			}
	    });
		
	}
	
	// 후기수정
	
	// 후기삭제
   function review_delete(review_num){
      $.ajax({
         url : "./AdminReviewDeleteAction.re",
         type : "POST",
         data : {review_num : review_num,
        	 	item_num : $('#item_num').val()},
         error : function() {alert('후기 삭제 실패!!');},
         success : function(data){
            $("#review").html(data);
         }
      });
   }
   // 후기삭제
	
	// 후기수정 서브밋
	
	function review_update_submit(){
		
		var item_num = $('#item_num').val();
		
		var form = $("#review_update_form")[0];        
        var formData = new FormData(form);
			
			$.ajax({
				url:"./ReviewUpdateAction.re",
				type:"post",
				processData: false,
				contentType: false,
				data: formData,
					
				success:function(data){
					
					console.log(data);
					
					$('#review').html(data);
					$('#review_insert').html("");
					$('#review_inset_open').val("0");
					
				}
			});
			
			alert("수정 완료");
	}
	
	// 후기수정 서브밋
	
	
	//문의 페이징
	//페이지 번호
	function pageNumber(ask_pageNum) {
		var item_num = $('#item_num').val();
		$.ajax({
			url: "./AskList.qa",
			type : "POST",
			data : { ask_pageNum : ask_pageNum, ask_item_num : item_num },
			error : function() {alert('페이징 처리 실패!!');},
			success: function(data){
				console.log(data);
				$("#ask").html(data);
			}
	    });
	}
	//문의 페이징
	
	
	$(document).ready(function(){
   
   	// Amount 증감
  		$('#item_amount_plus').click(function(){
  			var item_amount = $('#item_amount').val();
  			var item_amount_plus = (item_amount *= 1) +1;
  			if(item_amount_plus == $('#amount').val()){
  				alert("주문가능 수량을 초과 했습니다");
  				$('#item_amount').val(item_amount_plus-1);
  				return false;
  			}
  			$('#item_amount').val(item_amount_plus);
  			$('input[name=item_amount]').val(item_amount_plus);
  		});
  		
  		$('#item_amount_minus').click(function(){
  			var item_amount = $('#item_amount').val();
  			var item_amount_minus = (item_amount *= 1) -1;
  			if(item_amount_minus < 1){
  				item_amount_minus = 1;
  			}
  			$('#item_amount').val(item_amount_minus);
  			$('input[name=item_amount]').val(item_amount_minus);
  		});
  		
  		$('#item_color_select').change(function(){
  			$('input[name=item_color]').val($(this).val());
  		});
  		$('#item_size_select').change(function(){
  			$('input[name=item_size]').val($(this).val());
  		});
  	// Amount 증감
  	
  	
  		$('#item_color_select').change(function(){
  			if($('#item_color_select option:selected').val() != "COLOR" &&
  					($('#item_size_select option:selected').val() != "SIZE")){
  				
  				var basket_item_num = $('#item_num').val();
  				var basket_item_amount = $('#item_amount').val();
  				var basket_item_size = $('#item_size_select option:selected').val();
  				var basket_item_color = $('#item_color_select option:selected').val();
  				var basket_item_price = $('#item_price').val();
  				var basket_item_name = $('#item_name').val();
  				var basket_item_thumbnail = $('#item_thumbnail').val();
  				
  				$.ajax({
  					url: "./ItemBasketAction.ite",
  					type : "POST",
  					data : { basket_item_num:basket_item_num,
  							basket_item_amount:basket_item_amount,
  							basket_item_size:basket_item_size,
  							basket_item_color:basket_item_color,
  							basket_item_price:basket_item_price,
  							basket_item_name:basket_item_name,
  							basket_item_thumbnail:basket_item_thumbnail
  							},
  					error : function() {alert('???관리자에게 문의하세요');},
  					success: function(data){
  						console.log(data);
  						$("#item_basket").html(data);
  					}
  			    });
  				
  				$("#item_color_select option:eq(0)").prop("selected", true);
  				$("#item_size_select option:eq(0)").prop("selected", true);
  				$('#item_amount').val(1);
  				
  			}
  		})
  		
  		$('#item_size_select').change(function(){
  			if($('#item_color_select option:selected').val() != "COLOR" &&
  					($('#item_size_select option:selected').val() != "SIZE")){
  				var basket_item_num = $('#item_num').val();
  				var basket_item_amount = $('#item_amount').val();
  				var basket_item_size = $('#item_size_select option:selected').val();
  				var basket_item_color = $('#item_color_select option:selected').val();
  				var basket_item_price = $('#item_price').val();
  				var basket_item_name = $('#item_name').val();
  				var basket_item_thumbnail = $('#item_thumbnail').val();
  				
  				$.ajax({
  					url: "./ItemBasketAction.ite",
  					type : "POST",
  					data : { basket_item_num:basket_item_num,
  							basket_item_amount:basket_item_amount,
  							basket_item_size:basket_item_size,
  							basket_item_color:basket_item_color,
  							basket_item_price:basket_item_price,
  							basket_item_name:basket_item_name,
  							basket_item_thumbnail:basket_item_thumbnail
  							},
  					error : function() {alert('???관리자에게 문의하세요');},
  					success: function(data){
  						console.log(data);
  						$("#item_basket").html(data);
  					}
  			    });
  				
  				$("#item_color_select option:eq(0)").attr("selected", "selected");
  				$("#item_size_select option:eq(0)").attr("selected", "selected");
  				$('#item_amount').val(1);
  			}
  		})
  		
  		
  	});
  	
  	function deleteItemBasket(item_num,item_color,item_size,item_amount){
  		$.ajax({
  			url: "./ItemBasketDeleteAction.ite",
  			type : "POST",
  			data : {item_num:item_num,
  					item_color:item_color,
  					item_size:item_size,
  					item_amount:item_amount
  			},
  			error : function() {alert('???관리자에게 문의하세요');},
  			success: function(data){
  				console.log(data);
  				$("#item_basket").html(data);
  			}
  	    });
  	}
  	
	
	
</script>

</head>
<body>

<div class="container-fluid">

<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12">	

<%
	request.setCharacterEncoding("UTF-8");
	ItemDTO idto = (ItemDTO)request.getAttribute("idto");
	String id = (String)session.getAttribute("member_id");
%>

<input type="hidden" value="0" id="review_inset_open">
<input type="hidden" value="<%=idto.getItem_num() %>" id="item_num">
<input type="hidden" value="<%=idto.getItem_amount() %>" id="amount">
<input type="hidden" value="<%=idto.getItem_price() %>" id="item_price">
<input type="hidden" value="<%=idto.getItem_name() %>" id="item_name">
<input type="hidden" value="<%=idto.getItem_thumbnail() %>" id="item_thumbnail">
<input type="hidden" value="<%=id %>" id="member_id">

</div>

<div class="col-md-8 col-md-offset-2">
<h1>Item Detail</h1>
<hr>
</div>

<div class = "row">
<div class="col-md-8 col-md-offset-3">
<table class="itemOrder">
	<tr>
		<td rowspan="7" class="itemImg"><div class="mainimg"><img id="mainimg" src="./upload/<%=idto.getItem_thumbnail() %>" width="500" height="500"></div></td>
		<td colspan="2"><div class="itemName"><%=idto.getItem_name() %></div>
		<br><div class="itemCategory"><%=idto.getItem_category() %></div>
		<br><img src="./img/heart-line.png" id="wish" onclick="addWish(<%=idto.getItem_num()%>,'<%=id%>');">
		</td>	
	</tr>

	<tr>
		<td>price : </td>
		<td><%=idto.getItem_price() %></td>
	</tr>
	<tr>
		


		<td>amount : </td>
		<td class="amountInput">
 			<input type="button" value="-" id="item_amount_minus" class="btn btn-paosdy btn-lg">
      		<input type="text" class="form-control amountText" id="item_amount" value=1>
			<input type="button" value="+" id="item_amount_plus" class="btn btn-paosdy btn-lg">
		</td>
	</tr>
	<tr class="option">
		<td>
		color :
		</td>
		<td class="sizeSel">			
			  <select class="form-control option" id="item_color_select">
				<option>COLOR</option>
				<%for(int i=0; i<(idto.getItem_color().split("/")).length; i++){ %>
					<option><%=idto.getItem_color().split("/")[i] %></option>
				<%} %>
			  </select>    			  
		</td>
	</tr>
	<tr class="option">
		<td>
		size :
		</td>
		<td>
			<select class="form-control option" id="item_size_select">
				<option>SIZE</option>
				<%for(int i=0; i<(idto.getItem_size().split("/")).length; i++){ %>
					<option><%=idto.getItem_size().split("/")[i] %></option>
				<%} %>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div style="display: inline;" id="item_basket">
				하이!
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="button" value="Buy" class="btn btn-itemBuy itembtn btn-lg" onclick="location.href='./InsertBasketOrderAction.bas?item_num=<%=idto.getItem_num()%>'">
			<input type="button" value="Add to Cart" class="btn btn-itemBuy itembtn btn-lg" onclick="location.href='./InsertBasketAction.bas?item_num=<%=idto.getItem_num()%>'">
			<input type="hidden" value="1" id="isWish" onclick="checkWish(<%=idto.getItem_num()%>,'<%=id%>');">
			<input type="hidden" value=<%=idto.getItem_name() %> id="item_name">
			<input type="hidden" value=<%=idto.getItem_thumbnail() %> id="item_thumbnail">
		</td>
	</tr>
</table>

</div>
</div>
<div class="row">
<div class="col-md-8 col-md-offset-3">
 <ul>
 	<li><div class="subimg2"><img src="./upload/<%=idto.getItem_contentimage()%>" width="800px" height="auto"></div></li>
 </ul>
</div>
</div>

<div class="row">
<div id="ask" class="col-md-8 col-md-offset-2 aa">
	문의사항
</div>
</div>

<div class="row">

<div class="col-md-8 col-md-offset-2 bb" id="review">	
</div>
</div>
<div class="col-md-8 col-md-offset-2" id="review_insert">
</div>

<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>

</div>



</body>
</html>