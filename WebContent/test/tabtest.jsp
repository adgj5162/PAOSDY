<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="/CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/CSS/bootstrap/css/bootstrap-theme.css">


<script src="/js/jquery-3.4.1.min.js"></script>
<script src="/CSS/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>

<div class="container">

  <h2>탭</h2>

  <ul id="myTab" class="nav nav-tabs" role="tablist">
    <li role="presentation" class=""><a href="#home" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="false">Home</a></li>
    <li role="presentation" class="active"><a href="#profile" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile" aria-expanded="true">Profile</a></li>
    <li role="presentation" class="dropdown">
      <a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown" aria-controls="myTabDrop1-contents">Dropdown <span class="caret"></span></a>
      <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1" id="myTabDrop1-contents">
        <li><a href="#dropdown1" tabindex="-1" role="tab" id="dropdown1-tab" data-toggle="tab" aria-controls="dropdown1">@fat</a></li>
        <li><a href="#dropdown2" tabindex="-1" role="tab" id="dropdown2-tab" data-toggle="tab" aria-controls="dropdown2">@mdo</a></li>
      </ul>
    </li>
  </ul>
  <div id="myTabContent" class="tab-content">
    <div role="tabpanel" class="tab-pane fade" id="home" aria-labelledby="home-tab">
      <p>Home content</p>
    </div>
    <div role="tabpanel" class="tab-pane fade active in" id="profile" aria-labelledby="profile-tab">
      <p>Profile content</p>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="dropdown1" aria-labelledby="dropdown1-tab">
      <p>@fat content</p>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="dropdown2" aria-labelledby="dropdown2-tab">
      <p>@mdo content</p>
    </div>
  </div>

</div>




<ul class="nav nav-tabs">
    <li><a href="#">1번 메뉴</a></li>
    <li class="active"><a href="#">2번 메뉴</a></li>
    <li><a href="#">3번 메뉴</a></li>
    <li><a href="#">4번 메뉴</a></li>
</ul>

</body>
</html>