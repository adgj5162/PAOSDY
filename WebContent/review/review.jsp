<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="-1"/>
<title>PAOSDY</title>
<link href="./CSS/review/reviewWrite.css" rel="stylesheet">
<script type="text/javascript" src="./js/jquery.raty.js"></script>
<script type="text/javascript">
// 별점 API
var jq = jQuery.noConflict();

jq(document).ready(function(){
	jq(function() {
		jq('div.star1').raty({
			score : 0,
			path : './img',
			width : 200,
			click : function(score, evt) {
				jq("#starRating").val(score);
				jq("#displayStarRating").html(score);
			}
		});
	});
});

function review_getinfo(){
	
	$(".error").hide();
	var review_starpoint = $('#review_starpoint').val();
	var review_title = $('#review_title').val();
	var review_content = $('#review_content').val();
	var review_image = $('#review_image').val(); 		
	var review_point = $('#starRating').val();	
	
	if(review_title == ""){
		$("#err02").show();
		$("#err02").text("후기제목란을 입력하십시오");
		$("#review_title").focus();
		return false;
	}
	if(review_point == 0){
		$("#err01").show();
		$("#err01").text("별점평가를 완료하십시오");
		return false;
	}
	if(review_content == ""){
		$("#err03").show();
		$("#err03").text("후기내용을 입력하십시오");
		$("#review_content").focus();
		return false;
	}
	if(review_image == ""){
		$("#err04").show();
		$("#err04").text("이미지를 등록하십시오");
		return false;
	}
	
	var review_item_num = $('#review_item_num').val();
	var review_member_id = $('#review_member_id').val();
	var review_title = $('#review_title').val();
	var review_content = $('#reivew_content').val();
	var review_image = $('#review_image').val();
	
	review_submit();
	
}
</script>
</head>
<body>
<%
	String id =(String)session.getAttribute("member_id");

%>

<h2> 구매후기 작성 </h2>
<div class="container-fluid">
		<form action="./ReviewWriteAction.re" method="post" enctype="multipart/form-data" id="review_form">
		<input type="hidden" name="review_item_num" value="<%=request.getParameter("item_num")%>"><br>
  <div class="row">
	<div class="col-md-2">
		<h3>작성자</h3> 
		<input type="text" name="review_member_id" value="<%=id %>" readonly class="form-control"><br>
	</div>
  </div>
  <div class="row">
	<div class="col-md-5">
		<h3>제목</h3> 
		<input type="text" name="review_title" id="review_title" placeholder="후기제목을 입력하세요" class="form-control"><br><br>
		<span class="error" id="err02"></span>
	</div>
	
	
	<div class="col-md-5 col-md-offset-2">	
		<h3>별점</h3>
		<div class="star1"><input type="hidden" name="review_starpoint" id="starRating" value="0"/>
		</div>
		<span class="error" id="err01"></span><br>
	</div>
  </div>
 
				
		<h3>구매후기</h3> <br>
		<div class="form-inline">
		<textarea rows="4" cols="100" name="review_content" id="review_content" placeholder="상세내용을 입력하세요" class="form-control"></textarea>		 
		  <div class="form-group">	
			<input type="button" value="등록하기" id="review_form_btn" class="btn btn-paosdy btn-lg" onclick="review_getinfo();"><br>
		  	<input type="button" value="취소" class="btn btn-paosdy btn-lg" onclick="writeReview();">
		  </div>
		</div>
		<span class="error" id="err03"></span>
	
	 <div class="row">		
		<div class="filebox">
			<label for="review_image" class="btn btn-paosdy btn-lg">그림 업로드</label><br>
			<input type="file" name="review_image" id="review_image" accept='image/jpeg,image/gif,image/png'><br>
		</div>
		<p>사진은 최대 20MB 이하의 JPG, PNG, GIF 파일만 첨부 가능합니다.</p>
		<span class="error" id="err04"></span><br><br>
  </div>
	

		

		
		</form>
  </div>

</body>
</html>