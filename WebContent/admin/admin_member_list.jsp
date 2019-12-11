<%@page import="com.member.db.MemberDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>

<script type="text/javascript">

	function idDelete(delId){
		
		var conf = confirm("정말로 삭제하시겠습니까?");
		
		if(conf){
		location.href="./AdminMemberDelete.adme?delete_member="+delId;
		
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
		// 관리자 로그인 상태일때만 진행 페이지
		String member_id = (String) session.getAttribute("member_id");

		if (member_id == null || !member_id.equals("admin")) {
			response.sendRedirect("./MemberLogin.me");
		}
		// 저장된 회원 목록 가져오기
		// request.setAttribute("memberList", memberList);
		ArrayList<MemberDTO> memberList = (ArrayList<MemberDTO>)request.getAttribute("memberList"); 
		
		int count = (Integer) request.getAttribute("membercount");
		String pageNum = (String) request.getAttribute("pageNum");
		
		int pageCount = (Integer) request.getAttribute("pageCount");
		int pageBlock = (Integer) request.getAttribute("pageBlock");
		int startPage = (Integer) request.getAttribute("startPage");
		int endPage = (Integer) request.getAttribute("endPage");

		// 표를 생성하여  회원 정보를 출력
	%>

	 <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="./AdminMain.nubi">관리자 메인으로</a>
        </li>
        <li class="breadcrumb-item active">회원리스트</li>
     </ol>
<%-- 	  <h1> 현재 가입 회원 [ 전체 가입자 : <%=count %> 명] </h1> --%>
	<div class="card mb-3">
      <div class="card-header">
         <i class="fas fa-table"></i>
           	회원리스트</div>
      <div class="card-body">
        <div class="table-responsive"> 
         <div class="row">
          <div class="col-md-2 my-2">
          <select class="custom-select">
			 <option value="member_id">10</option>
			 <option value="member_id">25</option>
			 <option value="member_id">50</option>
			 <option value="member_id">100</option>
		  </select>	
		  </div>
		  <div class="col-md-4 offset-6">	  
          <form action="./MemberSearch.adme" method="post" class="d-none d-md-inline-block form-inline mr-0 my-2 my-md-0">

			<select name="search_column" class="form-control col-md-3">
			<option value="member_id">I D</option>
			<option value="member_name">이 름</option>
			<option value="member_addr1">지 역</option>
			
			</select>
			<input type="text" name="search" class="form-control col-md-6">
			
			<input type="submit" value="검색" class="btn btn-primary col-md-2">
			
		  </form>
		  </div>
		 </div>
 
	  <table class="table table-bordered" width="100%" cellspacing="0">
	    <thead class="thead-light">
	     <tr>
	       <th>아이디</th>
	       <th>POINT</th>
	       <th>이름</th>
	       <th>성별</th>
	       <th>전화번호</th>
	       <th>생년월일</th>
	       <th>우편번호</th>
	       <th>주소</th>
	       <th>E-mail</th>
	       <th>가입일자</th>
	       <th>비 고</th>
	     </tr>
	    </thead>
	    </tbody>
	     <% for(int i=0;i<memberList.size();i++){ 
	    	 MemberDTO mdto = memberList.get(i);
	    	 //MemberDTO mdto = (MemberDTO) memberList.get(i);
 	    	 
	     	 if(mdto.getMember_id().equals("admin")){
	    		 continue;
	    	 }
	    	 %>
 
	     <tr>
	       <td><%=mdto.getMember_id() %></td>
	       <td><%=mdto.getMember_point() %></td>
	       <td><%=mdto.getMember_name() %></td>
	       <td><%=mdto.getMember_gender() %></td>
	       <td><%=mdto.getMember_phone().substring(0,3)+"-"
	      		 +mdto.getMember_phone().substring(3,7)+"-"
	      		 +mdto.getMember_phone().substring(7,11)%></td>
	       <td><%=mdto.getMember_birth().substring(0, 4)+"/"
	       		+mdto.getMember_birth().substring(4,6)+"/"
	    		+mdto.getMember_birth().substring(6,8) %></td>
	       <td><%=mdto.getMember_postcode() %></td>
	       <td><%=mdto.getMember_addr1()+","+mdto.getMember_addr2() %></td>
	       <td><%=mdto.getMember_email() %></td>
	       <td><%=mdto.getMember_regdate() %></td>
	       <td><input type="button" value="회원삭제" onclick="idDelete('<%=mdto.getMember_id() %>');" class="btn btn-primary"></td>
	       
	     </tr>

	     <% } %>
	   </tbody>
	  </table>
       </div>
       <div class="col-md-5 offset-7" align="center" style="margin: 10px auto 10px auto; width: 75%">
      <%
		if(count != 0) {
			// 이전
			if (startPage > pageBlock) {
			%>
			<a href="./AdminMemberList.adme?pageNum=<%=startPage - pageBlock%>">[이전]</a>
			<%
			}

			// 1...10  11..20  21...30
			for (int i = startPage; i <= endPage; i++) {
			%>
			<a href="./AdminMemberList.adme?pageNum=<%=i%>">[<%=i%>]
			</a>
			<%
			}

			// 다음
			if (endPage < pageCount) {
			%>
			<a href="./AdminMemberList.adme?pageNum=<%=startPage + pageBlock%>">[다음]</a>
			<%
				}
		}
		%>
	   </div>
      </div>
     <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
    </div>	
		

		



</div>
<jsp:include page="Include/admin_footer.jsp"/>



</body>
</html>