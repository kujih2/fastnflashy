<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기록/순위</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/match.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
    //이벤트 연결
    $('.category').click(function(){
    	location.href='teamList.do?team_category='+$(this).attr('data-num');
    });
});
</script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/match/matchHeader.jsp"/>
<div class="content-main">
	<h2>팀/구단</h2>
	
	<div class="align-center">
			<input type="button" name="category" class="category" value="축구" data-num="0">
		   	<input type="button" name="category" class="category" value="야구" data-num="1">
		   	<input type="button" name="category" class="category" value="배구" data-num="2">
		   	<input type="button" name="category" class="category" value="농구" data-num="3">
		 </div>
		 <div class="align-center">
		 	<c:forEach var="teamList" items="${teamList}">
		 	<div>
		 	<a href="#">
		 		${teamList.team_photo}
		 		${teamList.team_name}
		 		</a>
		 	</div>
		 	</c:forEach>
		 </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>