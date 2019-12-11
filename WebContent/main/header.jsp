<%@page import="com.goods.basket.db.BasketDAO"%>
<%@page import="com.goods.basket.db.BasketDTO"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<link href="./CSS/main/fonts.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Fredoka+One|Noto+Serif+KR&display=swap&subset=korean" rel="stylesheet">

<%String id = (String)session.getAttribute("member_id"); %>


   <div class="row snl">
       
      <div class="col-sm-12 col-xs-12 col-md-2 logo">
         <a href="./Main.me"><!-- <img src="./img/Logo150l80.jpg"> -->
         <div id="logo">
         PAOSDY
         </div>
         
         </a>
         <div class="row hidden-md hidden-lg">
         <ul id="Mnav">
                <li class="fonts"><a href="./Intro.me">Intro</a></li>
                <li class="fonts"><a href="./ItemListAction.ite">Product</a></li>
                <li class="fonts"><a href="./NoticeList.nb">Notice</a></li>
                <%if("admin".equals(id)){ %>
                <li class="fonts"><a href="./AdminMain.adme">Admin</a></li>
                <%} %>
   
         </ul>
         </div>
      </div>

      
      <div class="col-md-8 col-lg-9 snl">
         <div class="col-md-7 searchBar hidden-xs hidden-sm">
         <input type="search" placeholder="search" class="col-md-5 col-lg-3 Tsearch fonts"><input type="button" class="searchButton">
         </div>
         <div class="col-md-5 hidden-xs hidden-sm">
         <ul id="login">
            <%if(id==null){ %>
            <li class="fonts"><a href="./MemberLogin.me">Login</a></li>
            <li>|</li>
            <li class="fonts"><a href="./MemberJoin.me">Join Us</a></li>
            <%}else{%>
            <li class="fonts"><a href="./MemberInfo.me"><%=id %>'s info</a></li>
            <li>|</li>
            <li class="fonts"><a href="./Logout.me">Logout</a></li>
            
            <%} %>
         </ul>
         </div>
         
         <div class="row">
            
            <div class="col-md-12 nav hidden-xs hidden-sm">
               <ul id="Mnav">
                <li class="fonts"><a href="./Intro.me">Intro</a></li>
                <li class="fonts"><a href="./ItemListAction.ite">Product</a></li>
                <li class="fonts"><a href="./NoticeList.nb">Notice</a></li>
                <%if("admin".equals(id)){ %>
                <li class="fonts"><a href="./AdminMain.adme">Admin</a></li>
                <%} %>
   
               </ul>
            </div>
         

         </div>
      </div>
      <div class="hidden-xs hidden-sm cartarea">
         <a href="./BasketList.bas" class="fonts cart">
         
         <%

         if(id == null){%>
         
         <img src="./img/shopping-cart-line.png" width="40">
         
         <%}else{
            %>Cart(<%
            BasketDAO bdao = new BasketDAO();
            int basketCount = bdao.getbasketCount(id);
            if(basketCount == 0){
               %>0<%
            }else{
               %><%=basketCount%><%
            }
            %>)<%
         }
         %>

         
         </a>
      </div>
   </div>

