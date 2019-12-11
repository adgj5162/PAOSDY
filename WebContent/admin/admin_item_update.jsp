<%@page import="com.item.db.ItemDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/JavaScript"  src=http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js></script>
<script type="text/javascript">

	
		function thumbnail_preview(input){
			if (input.files && input.files[0]) {
				  var reader = new FileReader();
				  
				  reader.onload = function (e) {
				   $('#thumbnail').attr('src', e.target.result);  
				  }
				  
				  reader.readAsDataURL(input.files[0]);
				  }
		}
		
		function contentimage_preview(input){
			if (input.files && input.files[0]) {
				  var reader = new FileReader();
				  
				  reader.onload = function (e) {
				   $('#contentimage').attr('src', e.target.result);  
				  }
				  
				  reader.readAsDataURL(input.files[0]);
				  }
		}
				  
		

	$(document).ready(function(){
		
		var item_thumbnail = $('#item_thumbnail').val();
		var item_contentimage = $('#item_contentimage').val();
		var old_item_thumbnail = $('#old_item_thumbnail').val();
		var old_item_contentimage = $('#old_item_contentimage').val();
		
		
		
		$('#item_thumbnail').change(function(){
			if($(this).val()!=""){
				var ext = $(this).val().split(".").pop().toLowerCase();
				if($.inArray(ext, ["gif","jpg","jpeg","png"]) == -1){
					alert("gif,jpg,jpeg,png 파일만 업로드 해주세요");
					$(this).val("");
					return;
				}
				if($('#item_thumbnail')[0].files[0].size > 10 * 1024 * 1024){
					alert("썸네일 이미지는 10MB를 초과할 수 없습니다");
					$(this).val("");
					return;
				}
			}
			
			thumbnail_preview(this);
			
		});
		
		$('#item_contentimage').change(function(){
			if($(this).val()!=""){
				var ext = $(this).val().split(".").pop().toLowerCase();
				if($.inArray(ext, ["gif","jpg","jpeg","png"]) == -1){
					alert("gif,jpg,jpeg,png 파일만 업로드 해주세요");
					$(this).val("");
					return;
				}
				if($(this)[0].files[0].size > 100 * 1024 * 1024){
					alert("상세설명 이미지는 100MB를 초과할 수 없습니다");
					$(this).val("");
					return;
				}
			}
			contentimage_preview(this);
			
		});
		
		$('#submit').click(function(){
			var item_name = $('#item_name').val();
			var item_price = $('#item_price').val();
			var item_color = $('#item_color').val();
			var item_size = $('#item_size').val();
			var item_amount = $('#item_amount').val();
			var item_thumbnail = $('#item_thumbnail').val();
			var item_contentimage = $('#item_contentimage').val();
			var old_item_thumbnail = $('#old_item_thumbnail').val();
			var old_item_contentimage = $('#old_item_contentimage').val();
			
			console.log(item_contentimage == "" && old_item_contentimage == "");
			
			
			if(item_name == ""){
				$('#item_name').focus();
				$('#item_name_span').text("상품이름을 입력해주세요");
				return false;
			}
			if(item_price == ""){
				$('#item_price').focus();
				$('#item_price_span').text("가격을 입력해주세요");
				return false;
			}
			if(item_amount == ""){
				$('#item_amount').focus();
				$('#item_amount_span').text("수량을 입력해주세요");
				return false;
			}
			if(item_color == ""){
				$('#item_color').focus();
				$('#item_color_span').text("색상을 입력해주세요");
				return false;
			}
			if(item_size == ""){
				$('#item_size').focus();
				$('#item_size_span').text("사이즈를 입력해주세요");
				return false;
			}
			if(item_thumbnail == "" && old_item_thumbnail == "null"){
				$('#item_thumbnail').focus();
				$('#item_thumbnail_span').text("썸네일을 등록 해주세요");
				return false;
			}
			if(item_contentimage == "" && old_item_contentimage == "null"){
				$('#item_contentimage').focus();
				$('#item_contentimage_span').text("상세페이지 이미지를 등록해주세요");
				return false;
			}
			
		});
		  
	});	

	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<link href="./CSS/item/admininput.css" rel="stylesheet">

<script src="./js/jquery-3.4.1.min.js"></script>
</head>
<body>

<jsp:include page="Include/admin_header.jsp"/>
	<div id="content-wrapper">
	
      <div class="container-fluid">
<div class="col-md-12">
<%
	request.setCharacterEncoding("UTF-8");
	ItemDTO idto = (ItemDTO)request.getAttribute("idto");
%>
<ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="./AdminMain.adme">관리자 메인으로</a>
        </li>
        <li class="breadcrumb-item active">상품수정</li>
     </ol>
<%--      <h1> 현재 가입 회원 [ 전체 가입자 : <%=count %> 명] </h1> --%>
   <div class="card mb-3">
      <div class="card-header">
         <i class="fas fa-table"></i>
              상품수정</div>
      <div class="card-body">

   <div class="col-md-8 offset-2">

<fieldset>
	<form action="./ItemUpdateAdminAction.adite" name="item_input" id="item_input" method="post" enctype="multipart/form-data">
		<input type="hidden" name="item_num" value="<%=idto.getItem_num()%>">
		<input type="hidden" id="old_item_thumbnail" name="old_item_thumbnail" value="<%=idto.getItem_thumbnail()%>">
		<input type="hidden" id="old_item_contentimage" name="old_item_contentimage" value="<%=idto.getItem_contentimage()%>">
		
		<div class="row">
		  <div class="col-md-4">
		  	<label for="item_thumbnail" class="control-label">상품 썸네일</label> 
			<div class="filebox">
				<label for="item_thumbnail" class="btn btn-info">업로드</label><br>
				<input type="file" name="item_thumbnail" id="item_thumbnail"><br><span id="item_thumbnail_span" class="img_span"></span>
			</div>
			<img id="thumbnail" src="./upload/<%=idto.getItem_thumbnail()%>" width="100" height="100"><br>
			<hr>
			<label for="item_thumbnail" class="control-label">상품 상세설명 이미지</label> 
			<div class="filebox">
				<label for="item_contentimage" class="btn btn-info">업로드</label><br>
				<input type="file" name="item_contentimage" id="item_contentimage"><br><span id="item_contentimage_span" class="img_span"></span>
			</div>
			<img id="contentimage" src="./upload/<%=idto.getItem_contentimage()%>" width="100" height="100"><br>
		  </div>
			
		  <div class="col-md-8">
				<div class="form-group">
					<label for="item_name" class="col-md-4 control-label">상품이름</label> 
					<input type="text" name="item_name" id="item_name" value="<%=idto.getItem_name() %>" class="form-control"><span id="item_name_span"></span><br>
				</div>
				<div class="form-group">
					<label for="item_price" class="col-md-4 control-label">상품가격</label>
				    <input type="text" name="item_price" id="item_price" value="<%=idto.getItem_price() %>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" placeholder="숫자만입력" class="form-control"><span id="item_price_span"></span><br>
				</div>
				<div class="form-group">
					<label for="item_category" class="col-md-4 control-label">상품분류 </label>
					<select name="item_category" class="form-control" id="item_category">
						<option value="얼굴비누">얼굴비누</option>
						<option value="몸비누">몸비누</option>
						<option value="머리비누">머리비누</option>
						<option value="발비누">발비누</option>
					</select><br>
				</div>
				<div class="form-group">
					<label for="item_price" class="col-md-4 control-label">상품 수량</label>
					<input type="text" name="item_amount" id="item_amount" value="<%=idto.getItem_amount() %>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" placeholder="숫자만입력" class="form-control"><span id="item_amount_span"></span><br>
				</div>
				<div class="form-group">
					<label for="item_color" class="col-md-4 control-label">상품 색상</label>
					<input type="text" name="item_color" id="item_color" value="<%=idto.getItem_color() %>" placeholder="(/)슬래쉬 로 구분" class="form-control"><span id="item_color_span"></span><br>
				</div>
				<div class="form-group">
					<label for="item_size" class="col-md-4 control-label">상품 사이즈</label>
					 <input type="text" name="item_size" id="item_size" value="<%=idto.getItem_size() %>" placeholder="(/)슬래쉬 로 구분" class="form-control"><span id="item_size_span"></span><br>
				</div>				
				<div class="form-group">
					<label for="item_saleprice" class="col-md-4 control-label">할인된 가격</label> 
					<input type="text" name="item_saleprice" id="item_saleprice" value="<%=idto.getItem_saleprice() %>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" placeholder="숫자만입력" id="item_saleprice" class="form-control"><br>
				</div>
		  </div>
			<div class="submit">
				<input type="submit" value="수정" id="submit" class="btn btn-primary">
				<input type="reset" value="취소" onclick="history.back();" class="btn btn-primary">
			</div>
	  </div>
	</form>
</fieldset>
  </div>
  </div>
 </div>
</div>
<jsp:include page="Include/admin_footer.jsp"/>

</body>
</html>