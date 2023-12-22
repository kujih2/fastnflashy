<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>

.match-bar{
	width:100%;
	height:150px;
	border:1px solid black;
}
.magazine{
   float:left;
	width:65%;
	height:480px;
	margin-top: 80px;
	border:1px solid black;
}
.booking{
	margin-left:66.5%;
	width:35%;
	height:280px;
	margin-top: 80px;
	border:1px solid black;
}
.board{
 	float:left;
	width:55%;
	height:500px;
	margin-top:-200px;
	margin-left:50px;
	border:1px solid black;
}
.hit-magazine{
	margin-left:66.5%;
	width:35%;
	height:500px;
	margin-top: 20px;
	border:1px solid black;
}
.recommend-magazine{

	width:35%;
	height:500px;
	margin-left:66.5%; 
	margin-top: 20px;
	border:1px solid black;
	margin-bottom:100px;
}
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	
		<div class="match-bar"></div>
		<div class="magazine"></div>
		<div class="booking"></div>
		<div class="hit-magazine"></div>
		<div class="board"></div>
		<div class="recommend-magazine"></div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>







