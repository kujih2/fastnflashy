<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<script type="text/javascript">
	window.onload=function(){
		let myForm = document.getElementById('write_form');
		//이벤트 연결
		myForm.onsubmit=function(){
			let title = document.getElementById('title');
			if(title.value.trim()==''){
				alert('제목을 입력하세요');
				title.value='';
				title.focus();
				return false;
				
			}
			let content = document.getElementById('content');
			if(content.value.trim()==''){
				alert('내용을 입력하세요');
				content.value = '';
				content.focus();
				return false;
			}
		}
			
	};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>게시판 글쓰기</h2>
		<form id="write_form" action="boardWrite.do" method="post" enctype="multipart/form-data">
			<ul>
				<%-- <li>
					<input type="radio" id="soccer" name="category" value="soccer">
				    <label for="soccer">축구</label>
					
					<input type="radio" id="baseball" name="category" value="baseball">
				    <label for="baseball">야구</label>
					
					<input type="radio" id="volleyball" name="category" value="volleyball">
				    <label for="volleyball">배구</label>
					
					<input type="radio" id="basketball" name="category" value="basketball">
				    <label for="basketball">농구</label>
				</li>	    
				--%>
					<li>
						<label for="category">스포츠 카테고리</label> 
						<select id="category" name="category">
							<option value="1">축구</option>
							<option value="2">야구</option>
							<option value="3">배구</option>
							<option value="4">농구</option>
						</select>
					</li>
					
					<li>
					<label for="title">제목</label>
					<input type="text" name="title" id="title" maxlength="50">				
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content" id="content"></textarea>
				</li>
				<li>
					<label for="filename">파일</label>
					<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
				</li>
			</ul>
		
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="목록" onclick="location.href='boardList.do'">
		</div>
		</form>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>