<%@page import="com.goods.member.MemberDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.goods.review.db.ReviewDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/review/reAdList.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
	
	
<script type="text/javascript">
	$(document).ready(function() {
		$('.adminReview').hide();

		$('.ReviewTitle').click(function() {
			var num = $(this).val();
			 	$('.adminReview').hide();
			$(this).next().toggle('normal', function() {
			});
			// 		return false;
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
%>	
<div class="container-fluid">

<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12">	

	<h1>관리자 후기 관리 페이지</h1>
	
  <div class="container-fluid row ReviewTitle">
  	<div class="col-md-2">
  		상품명
  	</div>
	<div class="col-md-4">
		제목
	</div>	
	<div class="col-md-1">
		별점
	</div>
	<div class="col-md-2">	
		회원 아이디
	</div>
	<div class="col-md-3">	
		후기 작성날짜
	</div>	 
  </div>
	
	
	<hr class="hr1">
	<%
		List<ReviewDTO> reviewList = (List<ReviewDTO>) request.getAttribute("reviewList");
		List item_num = (List)request.getAttribute("item_name");
		String pageNum = (String) request.getAttribute("pageNum");
	
		System.out.println("@@@@@@@@"+reviewList.size());
		
		if(reviewList != null){
		for (int i = 0; i < reviewList.size(); i++) {
			ReviewDTO rdto = (ReviewDTO) reviewList.get(i);
	%>


   
   <div class="container-fluid row ReviewTitle">
     <div class="col-md-2">
  		<%=item_num.get(i) %>
  	</div>
	<div class="col-md-4">
		<%=rdto.getReview_title()%>
	</div>	
	<div class="col-md-1">
		별점<%=rdto.getReview_starpoint()%>점
	</div>
	<div class="col-md-2">	
		 <%=rdto.getReview_member_id()%>
	</div>
	<div class="col-md-3">	
		<%=rdto.getReview_date()%> 
	</div>	 
  </div>
  
	<div class="container-fluid row adminReview">
	<hr class="hr2">
	  <div class="col-md-2 col-md-offset-1">			
		<img src="./review_upload/<%=rdto.getReview_image()%>" height="150" width="200">
	  </div>	
	  <div class="col-md-4">
		<%=rdto.getReview_content().replace("\r\n", "<br>")%>
	  </div>	
	  <div class="col-md-2">	
		<form class="form-inline"
			action="./AdminReviewPointAction.re?review_member_id=<%=rdto.getReview_member_id()%>"
			method="post" onsubmit="fun1()">
			<input type="text" name="member_point" id="point" class="form-control"
				onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" maxlength="4">
			<input type="submit" value="적립금 주기" class="btn btn-primary"><br><br>
			<p>4자리 이하의 숫자만 입력해주십시오</p>
		</form>
	  </div>	
	  <div class="col-md-1">	  
		<input type="button" value="후기글 삭제" name="delete_review" id="dr" class="btn btn-danger"
			onclick="location.href='./AdminReviewDeleteAction.re?review_num=<%=rdto.getReview_num()%>'">
	  </div>

	</div>
	
	<%
		}
	}
	%>

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
			<%}
		}
		%></div>
		</div>
		
	
</div>
	

<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>


</div>	
	
</body>
</html>