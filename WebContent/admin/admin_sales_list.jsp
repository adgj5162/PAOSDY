<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.order.db.OrderDTO"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/admin/sales_list.css"	 rel="stylesheet">

</head>
<body>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

Calendar c1 = Calendar.getInstance();

String strToday = sdf.format(c1.getTime());


String sdate=(String)request.getAttribute("sdate");
String edate=(String)request.getAttribute("edate");



	List<Vector> salesList = (List<Vector>)request.getAttribute("salesList");

	int count = (Integer) request.getAttribute("salescount");
	String pageNum = (String) request.getAttribute("pageNum");

	int pageCount = (Integer) request.getAttribute("pageCount");
	int pageBlock = (Integer) request.getAttribute("pageBlock");
	int startPage = (Integer) request.getAttribute("startPage");
	int endPage = (Integer) request.getAttribute("endPage");

	Vector svec = new Vector();
	int[] salesSum = new int[2];
	OrderDTO odto = new OrderDTO();
	int allAmount = 0;
	int allPrice = 0;
	
	for(int i=0;i<salesList.size();i++){
		svec = salesList.get(i);
		
		 salesSum = (int[])svec.get(0);
		 
		 allAmount = allAmount+salesSum[0];	
		 allPrice = allPrice+salesSum[1];
	}
	
	

%>

<jsp:include page="Include/admin_header.jsp"/>

<div id="content-wrapper">

<div class="container-fluid">
<div class="col-md-12"> 

<ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="./AdminMain.adme">관리자 메인으로</a>
        </li>
        <li class="breadcrumb-item active">매출관리</li>
</ol>

<div class="card mb-3">
      <div class="card-header">
         <i class="fas fa-table"></i>
              매출관리</div>
    <div class="card-body">
        <div class="table-responsive"> 
         <div class="row">

<div class="col-md-12">
<form action="./AdminSalesListAction.ads" method="get">


<div class="row ml-1">
<input type="text" name="sdate" value="<%=sdate%>" class="form-control inputSize"> ~  
<input type="text" name="edate" value="<%=edate%>" class="form-control inputSize">
</div>

<br>
<table class="table table-bordered">
<tr>
 <th>상품번호</th>
 <th>상품이름</th>
 <th>상품색상</th>
 <th>상품크기</th>
 <th>판매수랑</th>
 <th>판매가격</th>
</tr>
<%	
	for(int i=0;i<salesList.size();i++){
	svec = salesList.get(i);
	
	 salesSum = (int[])svec.get(0);
	 
	 odto = (OrderDTO)svec.get(1);%>
<tr>	 
 <td class="order_i_num"><%=odto.getOrder_item_num() %></td>	 
 <td><%=odto.getOrder_item_name() %></td>
 <td><%=odto.getOrder_color() %></td>
 <td><%=odto.getOrder_size() %></td>	 
 <td><%=salesSum[0] %></td>
 <td><%=salesSum[1] %></td>	 
	 
</tr>	 
<%}%>

</table>
<div class="row">
<div class="submit col-md-4 ml-1 row"> 
<select name="column" class="form-control optionSize">
 <option value="order_item_name">상품이름</option> 
 <option value="order_color">상품색상</option>
 <option value="order_size">상품크기</option>
</select> 
&nbsp;<input type="text" name="svalue" class="form-control inputSize"> &nbsp;
<input type="submit" value="검색" class="btn btn-primary ss" style="height:40px;"> 
</div>
<div class="col-md-7" align="right">
<p class="p_tag" align="right">총 판매량 : <%=allAmount %>&nbsp;
총합계 : <%=allPrice %></p>
</div>
</div>
</form>

</div>
</div>
 </div>
 </div>
 
</div>
</div>


<hr>
<jsp:include page="Include/admin_footer.jsp"/>
</body>
</html>