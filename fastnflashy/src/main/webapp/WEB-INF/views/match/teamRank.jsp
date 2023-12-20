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
    	location.href='teamRank.do?team_category='+$(this).attr('data-num');
    });
});
</script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/match/matchHeader.jsp"/>
<div class="content-main">
	<h2>기록/순위</h2>
	
	<div class="align-center">
			<input type="button" name="category" class="category" value="축구" data-num="0">
		   	<input type="button" name="category" class="category" value="야구" data-num="1">
		   	<input type="button" name="category" class="category" value="배구" data-num="2">
		   	<input type="button" name="category" class="category" value="농구" data-num="3">
		 </div>
		 <div class="align-center">
		 	<table>
		 		<tr>
		 			<th>순위</th>
		 			<th>팀명</th>
		 			<th>팀사진</th>
		 			<th>승률</th>
		 			<th>승</th>
		 			<th>패</th>
		 			<th>무</th>
		 		</tr>
		 		<c:forEach var="teamRank" items="${rankList}">
			 		<tr>
			 			<th>${teamRank.team_rank}</th>
			 			<th>${teamRank.team_name}</th>
			 			<th>${teamRank.team_photo}</th>
			 			<th>${teamRank.team_odds2}</th>
			 			<th>${teamRank.team_win}</th>
			 			<th>${teamRank.team_lose2}</th>
			 			<th>${teamRank.team_draw}</th>
			 		</tr>
		 		</c:forEach>
		 	</table>
		 </div>
</div>
</div>
</body>
</html>