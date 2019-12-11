<%@page import="com.goods.review.action.ActionForward"%>
<%@page import="com.goods.review.db.ReviewDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="-1"/>
<title>PAOSDY</title>
<link href="./CSS/review/reAdList.css" rel="stylesheet">
<script type="text/javascript" src="./js/jquery.raty.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function() {	// 별점 api
		$('div.star').raty({
			path : './img',
			width : 200,
			readOnly: true,
			score: function() {
			    return $(this).attr('data-score');}
		});
	});
});

$(document).ready(function(){
	$('.review_content').hide();
	$('div.ReviewTitle').click(function(){
	var num = $(this).val();
// 	$('div').hide();
		$(this).next().toggle('fast',function(){
			
		});
	});
});


</script>
<title>Insert title here</title>
</head>
<body>
<%



	request.setCharacterEncoding("UTF-8");
	

	int count = (Integer)request.getAttribute("count");
	int pageSize = (Integer)request.getAttribute("pageSize");
	int currentPage = (Integer)request.getAttribute("currentPage");
	int pageBlock = (Integer)request.getAttribute("pageBlock");
	String pageNum = (String)request.getAttribute("pageNum");
%>	
<!-- <p>작성된 후기가 없습니다</p> -->
	<%
		List<ReviewDTO> reviewList = (List<ReviewDTO>) request.getAttribute("reviewList");
		String id = (String)session.getAttribute("member_id");
		
	%>
	<h1>Review</h1>
	<%if(reviewList != null){
		for(int i = 0; i < reviewList.size(); i++) {
			ReviewDTO rdto = reviewList.get(i);
			int review_starpoint = rdto.getReview_starpoint();
	%>
	  
   <div class="container-fluid row ReviewTitle">
	<div class="col-md-5" id="re_title">
		<%=rdto.getReview_title()%>
	</div>	
	<div class="col-md-2">
		<div class="star" data-score="<%=review_starpoint%>" style="width:200px;"></div>
	</div>
	<div class="col-md-2">	
		 <%=rdto.getReview_member_id()%>
	</div>
	<div class="col-md-3">	
		<%=rdto.getReview_date()%> 
	</div>	 
  </div>
	
	
  <div class="container-fluid row review_content">
	<hr class="hr2">
	  <div class="col-md-4">			
		<img src="./review_upload/<%=rdto.getReview_image()%>" height="150" width="200">
	  </div>	
	  <div class="col-md-6">
		<%=rdto.getReview_content().replace("\r\n", "<br>")%>
	  </div>	
		<%if(id != null && (rdto.getReview_member_id().equals(id))){ System.out.println("!@!#@!@#");%>
			<div class="col-md-1">
			<input type="button" value="수정" name="update_review" id="ur" class="btn btn-paosdy btn-lg"
			onclick="review_update(<%=rdto.getReview_num()%>)">
			<input type="button" value="후기 삭제" name="delete_review" id="dr" class="btn btn-paosdy btn-lg"
         	onclick="review_delete(<%=rdto.getReview_num()%>)">
			</div>
		<%} %>
		
		<%if(id.equals("admin")){ %>
			<input type="button" value="후기 삭제" name="delete_review" id="dr" class="btn btn-paosdy btn-lg"
         	onclick="review_delete(<%=rdto.getReview_num()%>)">
			</div>
		 <%} %>	
	</div>
	
	
	
	
	

	<%
		}
	}	
	%>
	
<!-- <input type="button" value="작성하기" onclick="window.open('./ReviewWrite.re','문의사항 작성','width=550,height=700,location=no,status=no,scrollbars=yes');"> -->

	<%
	// 페이지 출력
	if(count != 0){
		//전체 페이지수 계산
		
		int pageCount=count/pageSize+(count%pageSize==0 ? 0:1);
		//시작페이지
		int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
		//끝페이지
		int endPage=startPage+pageBlock-1;
		if(endPage > pageCount){
			endPage = pageCount;
		}
		%>
		
		<div align="center" style="margin: 10px auto 10px auto; width: 75%" >
		<div>
		<%
		// 이전
		if(startPage>pageBlock){
			%>
			<span onclick="review_page(<%=startPage-pageBlock%>);"> Pre </span>
			<%
		}
		%></div><%
		%><div><%
		// 1...10 11...20 21...30
		for(int i=startPage; i<=endPage; i++){
			if(Integer.parseInt(pageNum) == i){
			%>
			<span style="font-weight: bold; color: black; font-size: 1.2em;">[<%=i %>]</span>
			<%
			}else{
			%>
			<span onclick="review_page(<%=i%>);">[<%=i %>]</span>
			<%
			}
		}
		%></div><%
		%><div><%
		// 다음
		if(endPage<pageCount){
			%>
			<span onclick="review_page(<%=startPage+pageBlock%>);"> Next </span>
			<%
		}
		%></div>
		</div>
		<%
	}
%>
<div class="col-md-1 col-md-offset-10">
<input type="button" value="후기 작성하기" class="btn btn-paosdy btn-lg" onclick="writeReview();" >
</div>
<!-- <input type="button" value="후기 작성하기" class="btn btn-info" onclick="writeReview();" > -->


</body>
</html>