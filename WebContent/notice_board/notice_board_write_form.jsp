<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="utf-8">
<head>
	<meta charset="uft-8" />
	<title>PAOSDY</title>
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	
	<link href="./dist/summernote.css" rel="stylesheet">
	<script src="./dist/summernote.js"></script>
    <script>
            $(document).ready(function() {
                $('#summernote').summernote({ // summernote를 사용하기 위한 선언
                    height: "300",
                    width: "800",
					callbacks: { // 콜백을 사용
                        // 이미지를 업로드할 경우 이벤트를 발생
					    onImageUpload: function(files, editor, welEditable) {
						    sendFile(files[0], this);
						}
					}
				});
			});
		</script>
	<script type="text/javascript">
	
        /* summernote에서 이미지 업로드시 실행할 함수 */
	 	function sendFile(file, editor) {
            // 파일 전송을 위한 폼생성
	 		data = new FormData();
	 	    data.append("uploadFile", file);
	 	    $.ajax({ // ajax를 통해 파일 업로드 처리
	 	        data : data,
	 	        type : "POST",
	 	        url : "BoardImageUpload",
	 	        cache : false,
	 	        contentType : false,
	 	        processData : false,
	 	        success : function(data) { // 처리가 성공할 경우
                    // 에디터에 이미지 출력
                    alert(data.url);
	 	        	var url = data.url;
	 	        $(editor).summernote('editor.insertImage', url);
	 	        $('#imageBoard > ul').append('<li><img src="./upload/'+url+'" width="480" height="auto"/></li>')

	 	        }
	 	    });
	 	}
	</script>
<link href="./CSS/main/layout.css" rel="stylesheet">
<link href="./CSS/notice.css" rel="stylesheet">	
</head>
<body>

<div class="container-fluid">

<div class="col-md-12 header"> <jsp:include page="/main/header.jsp"/> </div>

<div class="col-md-12 section">
	
	<div class="col-md-6 col-md-offset-3 mt-4">
		<br><br><br><br>
		<h1>notice-write</h1>
	</div>
	
	<div class="col-md-6 col-md-offset-3 mt-3">
	<form name="writeForm" action="./NoticeBoardWriteAction.nb" method="post">
		<div class="form-group form-inline">
			<label for="title">제목</label>
			<input type="text" name="title" id="title" class="form-control">
		</div>
		<textarea id="summernote" name="content"></textarea>
		
		<input type="submit" class="btn btn-paosdy btn-lg">
	</form>
	</div>
	

</div>
<div class="col-md-12 footer"> <jsp:include page="/main/footer.jsp"/> </div>


</div>		
	
</body>
</html>
