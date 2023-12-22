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
    	location.href='teamRank.do?team_category='+$(this).attr('data-num')+'&rank=team_odds2';
    });
    $('.rankButton').click(function(){
    	location.href='teamRank.do?team_category='+$('#team_category').val()+'&rank='+$(this).attr('data-rank');
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
		 			<th>경기수</th>
		 			<th><a href="#" class="rankButton"  data-rank="team_odds2">승률</a></th>
		 			<th><a href="#" class="rankButton"  data-rank="team_win">승</a></th>
		 			<th><a href="#" class="rankButton"  data-rank="team_lose2">패</a></th>
		 			<th><a href="#" class="rankButton"  data-rank="team_draw">무</a></th>
		 		</tr>
		 		<c:forEach var="teamRank" items="${rankList}">
			 		<tr>
			 			<th>${teamRank.team_rank}</th>
			 			<th><img src="${pageContext.request.contextPath}/images/teams/${teamRank.team_photo}" width="30">${teamRank.team_name}</th>
			 			<th>${teamRank.team_playCount}</th>
			 			<th>${teamRank.team_odds2}</th>
			 			<th>${teamRank.team_win}</th>
			 			<th>${teamRank.team_lose2}</th>
			 			<th>${teamRank.team_draw}</th>
			 		</tr>
			 		<input type="hidden" id="team_category" value="${teamRank.team_category}">
		 		</c:forEach>
		 	</table>
		 </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>