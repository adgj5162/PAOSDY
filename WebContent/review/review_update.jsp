<%@page import="com.goods.review.db.ReviewDTO"%>
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
var je = jQuery.noConflict();

je(document).ready(function(){
	je(function() {
		je('div.star2').raty({
			score: function() {
			    return $(this).attr('data-score');},
			path : './img',
			width : 200,
			click : function(score, evt) {
				je("#starRating").val(score);
				je("#displayStarRating").html(score);
			}
		});
	});
});


$(document).ready(function(){
	$('form').submit(function(){
		$(".error").hide();
		var review_starpoint = $('#review_starpoint').val();
		var review_title = $('#review_title').val();
		var review_content = $('#review_content').val();
		
		if($(':radio[name="review_starpoint"]:checked').length < 1){
			$("#err01").show();
			$("#err01").text("별점평가를 완료하십시오");
			return false;
		}
		if(review_title == ""){
			$("#err02").show();
			$("#err02").text("후기제목란을 입력하십시오");
			$("#review_title").focus();
			return false;
		}
		if(review_content == ""){
			$("#err03").show();
			$("#err03").text("후기내용을 입력하십시오");
			$("#review_content").focus();
			return false;
		}
	});
});	
</script>
</head>
<body>
<%
	ReviewDTO rdto = (ReviewDTO)request.getAttribute("rdto");
	String id = (String)session.getAttribute("member_id");
	int review_starpoint = rdto.getReview_starpoint();
%>
<h1> 구매후기 수정 </h1>
<div class="container-fluid">
	<form action="./ReviewUpdateAction.re" method="post" id="review_update_form" enctype="multipart/form-data">
	<input type="hidden" value="<%=rdto.getReview_item_num() %>" name="review_item_num">
<div class="row">
	<div class="col-md-2">
		<h3>작성자</h3> 
		<input type="text" name="review_member_id" value="<%=id %>" readonly class="form-control"><br>
		<img src="./review_upload/<%=rdto.getReview_image() %>" name="review_image" height="150" width="200"><br>
	</div>	
</div>
<div class="row">
	<div class="col-md-5">
		<h3>제목</h3> 
		<input type="text" name="review_title" id="review_title" class="form-control" value="<%=rdto.getReview_title()%>">
		<span class="error" id="err02"></span><br>
	</div>
	
	<div class="col-md-5 col-md-offset-2">
		<h3>별점</h3>
		<div class="star2" data-score="<%=review_starpoint%>"><input type="hidden" name="review_starpoint" id="starRating" value="<%=review_starpoint%>">
		</div>
		<span class="error" id="err01"></span><br>
	</div>
</div>	
	
	<h3>구매후기</h3> <br>
	<div class="form-inline">
	<textarea rows="4" cols="100" name="review_content" id="review_content" class="form-control"><%=rdto.getReview_content()%></textarea>
		<div class="form-group">	
			<input type="button" value="수정하기" class="btn btn-paosdy btn-lg" onclick="review_update_submit()"><br>
			<input type="button" value="취소" class="btn btn-paosdy btn-lg" onclick="writeReview();">
		</div>
	</div>
	<span class="error" id="err03"></span><br>
<div class="row">	
	<div class="filebox">
		<label for="review_image" class="btn btn-paosdy btn-lg">그림 업로드</label><br>
		<input type="hidden" name="review_num" value="<%=rdto.getReview_num() %>">
		<input type="hidden" name="existing_file" value="<%=rdto.getReview_image()%>">
		<input type="file" name="review_image" id="review_image" accept='image/jpeg,image/gif,image/png'><br>
	</div>
	<p>첨부파일을 변경할 시 클릭하여 변경할 수 있습니다.<br>
	사진은 최대 20MB 이하의 JPG, PNG, GIF 파일만 첨부 가능합니다.</p>
	<span class="error" id="err04"></span><br>
</div>	

	</form>

</div>

</body>
</html>