<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">

</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/member/memberHeader.jsp"/>
	<div class="content-main">
		<div>
			<h3>관심칼럼 목록</h3>
		</div>
		<div>
			<h3>관심게시글 목록</h3>
		</div>
		<div>
			<h3>내가 쓴 글 목록</h3>
		</div>
		<div>
			<h3>내가 쓴 댓글 목록(칼럼)</h3>
		</div>
		<div>
			<h3>내가 쓴 댓글 목록(게시글)</h3>
		</div>
		<div>
			<h3>나의 예매 목록</h3>
			
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
</div>

</body>
</html>