<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>칼럼작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<form action="magazinWrite.do" method="post"
		       enctype="multipart/form-data" id="magazinWrite_form" >
			<select id="sports_category" size="1" class="align-left">
				<option value="">카테고리</option>
				<option value="1">축구</option>
				<option value="2">야구</option>
				<option value="3">배구</option>
				<option value="4">농구</option>
			</select>
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" id="title"
					   placeholder="제목을 입력하세요" maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content" id="content"
								placeholder="내용을 입력하세요"></textarea>
				</li>
				<li>
					<label for="filename">파일</label>
					<input type="file" name="filename"
					  id="filename" accept="image/gif,image/png,image/jpeg">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="작성완료">
				<input type="button" value="취소" 
				                  onclick="location.herf='magazinList.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>