<%@page import="com.delivery.db.DeliveryDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
   function deleteCheck(index){
      if(confirm("정말 삭제하시겠습니까?")){
         location.href="./DeliveryDelete.de?delete_addr="+index;
      }
   }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/main/fonts.css" rel="stylesheet">
<link href="./CSS/item/navtab.css" rel="stylesheet">
<link href="./CSS/item/itemlist.css" rel="stylesheet">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">


<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>

<style type="text/css">
   tr{text-align: center; }   
   td{text-align: center; }
</style>
</head>
<body>

<div class="container-fluid row">

<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">
<jsp:include page="/member/member_navtab.jsp"/>
     
   <div class="col-md-8 col-md-offset-2">
   <br><br><br><br>
   <h1>주소록</h1>
   <hr>
   </div>
   <div class="col-md-8 col-md-offset-2" align="center">
     <table class="table-hover table mt-2">
        <tr>
          <th>배송지명</th>
          <th>수령인</th>
          <th>전화번호</th>
          <th>우편번호</th>
          <th>주소</th>
          <th>수정 / 삭제</th>
        </tr>
        
        <% 
   ArrayList<DeliveryDTO> deliveryList = (ArrayList<DeliveryDTO>)request.getAttribute("deliveryList");
        if(deliveryList == null){
           
        }else{
        for(int i=0;i<deliveryList.size();i++){ 
           DeliveryDTO ddto = deliveryList.get(i);
   
         
            
           %>
       
<!--              ddto.setDelivery_place_name(rs.getString("delivery_place_name")); -->
<!--             ddto.setDelivery_name(rs.getString("delivery_name")); -->
<!--             ddto.setDelivery_phone(rs.getString("delivery_phone")); -->
<!--             ddto.setDelivery_postcode(rs.getString("delivery_postcode")); -->
<!--             ddto.setDelivery_addr1(rs.getString("delivery_addr1")); -->
<!--             ddto.setDelivery_addr2(rs.getString("delivery_addr2")); -->
            
        <tr>
          <td><%=ddto.getDelivery_place_name() %></td>
          <td><%=ddto.getDelivery_name() %></td>
          <td><%=ddto.getDelivery_phone().substring(0,3)+"-"
                +ddto.getDelivery_phone().substring(3,7)+"-"
                +ddto.getDelivery_phone().substring(7,11)%></td>
          <td><%=ddto.getDelivery_postcode() %></td>
          <td><%=ddto.getDelivery_addr1()+", "+ddto.getDelivery_addr2() %></td>


          <td><input type="button" value="수정" onclick="window.open('./updateAdressBook.de?index=<%=ddto.getDelivery_index()%>','주소록 수정','width=550,height=400,location=no,status=no')" class="btn btn-paosdy">
                <input type="button" value="삭제" onclick="deleteCheck(<%=ddto.getDelivery_index()%>)" class="btn btn-paosdy"></td>
        </tr>
        <% 
           } 
        }
        
        %>
        
     
     </table>
        <div align="right">
      <input type="button" value="주소록 추가" onclick="window.open('./DeliveryBook.de','주소록 추가','width=550,height=450,location=no,status=no');" class="btn btn-paosdy btn-lg">
      </div>
     </div>
</div>

<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>
</div>
</body>
</html>