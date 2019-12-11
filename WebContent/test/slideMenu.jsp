<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!DOCTYPE html>  

    <head>  
        <meta charset="utf-8">  
        <title>alzio jquery</title>  
        <script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>  
        <style>  
            * { margin: 0px;    padding: 0px;}  
            body {  
    font-family:Arial, sans-serif;  
    font-size:15px;  
    background-color: #3366FF;  
}  
            #ALL {  
                margin:500px 0 0 200px; width:600px; }  
            h3{font-family:"돋움", Arial, Dotum ,Sans-serif;  
                font-size:16px;  
                font-weight:normal;  
                margin-bottom: 20px  }  
            .menu1 {  
                width: 300px;  
                float:left; }  
            .menu2 {  
                width: 300px;  
                float:left;  
                margin-left:20px;}  
            .head {  
                background: #3399CC;  
                color:#fff;  
                padding: 8px;  
                cursor: pointer;  
                margin-top:3px;  
                font-family:Arial, sans-serif;  
                font-size:24px}  
            .body {  
                display:none;}  
  
  
            .body a {  
              display:block;  
              text-decoration:none;  
              background-color:#8FC9E4;  
              color:#fff;  
              padding:3px 0 3px 10px;}  
            .body a:hover {  
              color: #fff;}  
  
        </style>  
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="/js/jquery-3.4.1.min.js"></script>
	<script src="/CSS/js/bootstrap.min.js"></script>  
	</head>  
  <body>
        <div id="ALL">  
            <table>  
                <tr>  
                  <td valign="top" align="left">  
                        <div class="menu2">  
                        <h3>마우스오버이벤트</h3>  
                            <div class="wrap_sub_menu">  
                                <p class="head">main1</p>  
                                <div class="body">  
                                    <a href="#">sub1</a>  
                                    <a href="#">sub2</a>  
                                    <a href="#">sub3</a>  
                                </div>  
                            </div>  
                            <div class="wrap_sub_menu">  
                                <p class="head">main2</p>  
                                <div class="body">  
                                     <a href="#">sub1</a>  
                                     <a href="#">sub2</a>  
                                     <a href="#">sub3</a>  
                                </div>  
                            </div>  
                            <div class="wrap_sub_menu">  
                                <p class="head">main3</p>  
                                <div class="body">  
                                     <a href="#">sub1</a>  
                                     <a href="#">sub2</a>  
                                     <a href="#">sub3</a>  
                                </div>  
                            </div>  
                        </div>  
                    </td>  
                </tr>  
            </table>  
        </div>  

        <br/><br/><br/><br/><br/>  


        <script type="text/javascript">  
            $(function(){  
 
                  
                // 마우스가 Over 되었을때 펼쳐지고 밖으로 나가면 축소됨 ( 1번 선택 )  
	            $(".wrap_sub_menu").hover(function(){  
                    $(this).css("background", "#336699").children(".body").slideDown(300).siblings(".body").slideUp("slow");  
                    $(this).siblings(".head").css("background", "#3399CC");  
                },
                    function(){  
                        $(this).css("background", "#336699").children(".body").slideUp("slow");  
                        $(this).siblings(".head").css("background", "#c0c0c0");
                });  
                // 마우스가 Over 되었을때 펼쳐지고 다른 메뉴가 아니고 그냥 나가면 펼쳐진 상태로 있음  ( 2번 선택 )  
       	  
    
            }); 
           
            
        </script>  

</body>