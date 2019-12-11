<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PAOSDY</title>
<link rel="stylesheet" href="./CSS/main/layout.css">
<link rel="stylesheet" href="./CSS/main/slider.css">

<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/bootstrap/css/bootstrap-theme.css">

</head>
<body>

<div class="container-fluid">
	<div class="col-md-12 header">
		<jsp:include page="/main/header.jsp" />
	</div>
<div class="col-md-12 section" style="padding-left: 0;">
  <div class="slider">
    <div class="slider__item">
      <img src="./img/soap2.jpg">
      <span class="slider__caption">
      	파오스디비누는 소량으로 매일 제작하고 있습니다 .<br>
      	건조를 끝내고 포장 후 고객에게 정성스럽게 보내드립니다.</span>
    </div>
    <div class="slider__item">
      <img src="./img/soap3.jpg">
      <span class="slider__caption">
      	파오스디비누는 소량으로 매일 제작하고 있습니다 .<br>
      	건조를 끝내고 포장 후 고객에게 정성스럽게 보내드립니다.</span>
    </div>
    <div class="slider__item">
      <img src="./img/soap4.jpg">
      <span class="slider__caption">
      	파오스디비누는 소량으로 매일 제작하고 있습니다 .<br>
      	건조를 끝내고 포장 후 고객에게 정성스럽게 보내드립니다.</span>
    </div>
    <div class="slider__item">
      <img src="./img/soap1.jpg">
      <span class="slider__caption">
     	 파오스디비누는 소량으로 매일 제작하고 있습니다 .<br>
      	건조를 끝내고 포장 후 고객에게 정성스럽게 보내드립니다.</span>
    </div>
  </div>
    <div class="slider__switch slider__switch--prev" data-ikslider-dir="prev">
      <span><svg xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 20 20"><path d="M13.89 17.418c.27.272.27.71 0 .98s-.7.27-.968 0l-7.83-7.91c-.268-.27-.268-.706 0-.978l7.83-7.908c.268-.27.7-.27.97 0s.267.71 0 .98L6.75 10l7.14 7.418z"/></svg></span>
    </div>
    <div class="slider__switch slider__switch--next" data-ikslider-dir="next">
      <span><svg xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 20 20"><path d="M13.25 10L6.11 2.58c-.27-.27-.27-.707 0-.98.267-.27.7-.27.968 0l7.83 7.91c.268.27.268.708 0 .978l-7.83 7.908c-.268.27-.7.27-.97 0s-.267-.707 0-.98L13.25 10z"/></svg></span>
    </div>
   </div>
	</div>
	<div class="col-md-12 footer">
		<jsp:include page="/main/footer.jsp" />
	</div>

<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./CSS/bootstrap/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="./js/slider.js"></script>
<script type="text/javascript">
$(".container-fluid").ikSlider({
	  speed: 500
	});
	$(".container-fluid").on('changeSlide.ikSlider', function (evt) { console.log(evt.currentSlide); });
</script>

</body>
</html>