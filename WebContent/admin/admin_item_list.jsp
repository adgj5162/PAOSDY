<%@page import="com.item.db.ItemDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<script type="text/javascript">
	function deleteCheck(Item_num){
		console.log(Item_num);
		if(confirm("정말 삭제하시겠습니까?") == true){
			location.href="./ItemDeleteAdminAction.adite?item_num="+Item_num
		}
	}
</script>
</head>
<body>

<jsp:include page="Include/admin_header.jsp"/>
<div id="content-wrapper">

<div class="container-fluid">


<div class="col-md-12">	


<%
	request.setCharacterEncoding("UTF-8");
	
	List itemList = (List)request.getAttribute("itemList");
	int count = (Integer)request.getAttribute("count");
	int pageSize = (Integer)request.getAttribute("pageSize");
	int currentPage = (Integer)request.getAttribute("currentPage");
	int pageBlock = (Integer)request.getAttribute("pageBlock");
	String pageNum = (String)request.getAttribute("pageNum");
%>	

	 <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="./AdminMain.adme">관리자 메인으로</a>
        </li>
        <li class="breadcrumb-item active">상품리스트</li>
     </ol>
<%-- 	  <h1> 현재 가입 회원 [ 전체 가입자 : <%=count %> 명] </h1> --%>
	<div class="card mb-3">
      <div class="card-header">
         <i class="fas fa-table"></i>
           	상품리스트</div>
      <div class="card-body">
        <div class="table-responsive"> 

	<table id="list" class="table itemt">
		<tr>
			<td>아이템번호</td>
			<td>썸네일</td>
			<td>상품이름</td>
			<td>상품분류</td>
			<td>가격</td>
			<td>할인된가격</td>
			<td>색상</td>
			<td>사이즈</td>
			<td>상품수량</td>
			<td>판매량</td>
			<td>수정</td>
		</tr>
		<%
			String correntPage="1";
	         if(itemList!=null){
			
			for (int i = 0; i < itemList.size(); i++) {
				ItemDTO idto = (ItemDTO)itemList.get(i);
		%>
		<tr>
			<td><%=idto.getItem_num() %></td>
			<td><img src="./upload/<%=idto.getItem_thumbnail()%>" width="50" height="50"></td>
			<td><%=idto.getItem_name() %></td>
			<td><%=idto.getItem_category() %></td>
			<td>￦<%=idto.getItem_price() %></td>
			<td>￦<%=idto.getItem_saleprice()%></td>
			<td><%=idto.getItem_color()%></td>
			<td><%=idto.getItem_size()%></td>
			<td><%=idto.getItem_amount()%></td>
			<td><%=idto.getItem_sold()%></td>
			<td>
				<button onclick="location.href='./ItemUpdateAdminForm.adite?item_num=<%=idto.getItem_num()%>'" class="btn btn-primary">수정</button>
				<button onclick="deleteCheck(<%=idto.getItem_num()%>);" class="btn btn-primary">삭제</button>
			</td>
		</tr>	
		<%
		
			}
		}	
		%>
	</table>
	</div>
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
		
		<div align="center" style="margin: 10px auto 10px auto; width: 75%">
		<%
		if(startPage>pageBlock){
			%>
			<a href="./ItemListAdminAction.adite?pageNum=<%=startPage-pageBlock%>">[이전]</a>
			<%
		}
		for(int i=startPage; i<=endPage; i++){
			if(Integer.parseInt(pageNum) == i){
			%>
			<a href="./ItemListAdminAction.adite?pageNum=<%=i%>" style="font-weight: bold; color: black; font-size: 1.2em;">[<%=i %>]</a>
			<%
			}else{
			%>
			<a href="./ItemListAdminAction.adite?pageNum=<%=i%>">[<%=i %>]</a>
			<%
			}
		}
		if(endPage<pageCount){
			%>
			<div><a href="./ItemListAdminAction.adite?pageNum=<%=startPage+pageBlock %>">[다음]</a></div>
			<%
		}
		}
		%>
 	</div>
    </div>
    
    
</div>
</div>

<jsp:include page="Include/admin_footer.jsp"/>


</body>
</html>