<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팀 상세정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/match.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<style type="text/css">
.content-main{
padding:50px;
display:table;
	clear:both;
}

</style>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/match/matchHeader.jsp"/>
<div class="content-main">
	<h2>팀 상세정보</h2>
	<div class="team-detail1">
		<img src="${pageContext.request.contextPath}/images/teams/${teamDetail.team_photo}" width="250">
		<br>
		${teamDetail.team_name}<br>
		<span style="color:red;">${teamDetail.team_rank}위</span> ${teamDetail.team_win}승 ${teamDetail.team_lose2}패 ${teamDetail.team_draw}무
	</div>
	
	<div class="team-detail3">
		<h3>-소속 선수 정보</h3>
		<table>
			<tr>
				<th>등번호</th>
				<th>이름</th>
				<th>포지션</th>
				<th>나이</th>
				<th>신장</th>
			</tr>
			<c:forEach var="playerList" items="${playerList}">
				<tr>
					<th>${playerList.player_backnum}</th>
					<th>${playerList.player_name}</th>
					<th>${playerList.player_position}</th>
					<th>${playerList.player_age}</th>
					<th>${playerList.player_height}</th>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="team-detail2">
		<h3>-최근 경기 전적</h3>
		<c:if test="${!empty teamResult}">
			<c:forEach var="recentMatch" items="${teamResult}" begin="0" end="3">
					${recentMatch.team1_name}<img src="${pageContext.request.contextPath}/images/teams/${recentMatch.team1_photo}" width="40"> <span class="recent-score" <c:if test="${recentMatch.result_match == 1}">style="color:gray;"</c:if>>${recentMatch.result_team1Score}</span> 
					<span class="recent-start">${recentMatch.schedule_start}</span><span class="recent-score" <c:if test="${recentMatch.result_match == 0}">style="color:gray;"</c:if>>${recentMatch.result_team2Score}</span><img src="${pageContext.request.contextPath}/images/teams/${recentMatch.team2_photo}" width="40"> ${recentMatch.team2_name}<br>
					<hr>
			</c:forEach>
		</c:if>
		<c:if test="${empty teamResult}">
			<h6>최근 경기가 없습니다.</h6>
		</c:if>
	</div>
	
	<div class="team-detail4">
		<h3>-${teamDetail.team_name} 경기 일정</h3>
		<c:if test="${!empty scheduledGame}">
				<c:forEach var="scheduledMatch" items="${scheduledGame}" begin="0" end="3">
					${scheduledMatch.team1_name}<img src="${pageContext.request.contextPath}/images/teams/${scheduledMatch.team1_photo}" width="40"> ${scheduledMatch.schedule_start} <img src="${pageContext.request.contextPath}/images/teams/${scheduledMatch.team2_photo}" width="40"> ${scheduledMatch.team2_name}<br>
					<hr class="small-hr">
				</c:forEach>
		</c:if>
		<c:if test="${empty scheduledGame}">
			<h5>에졍된 경기가 없습니다.</h5>
		</c:if>
	</div>
		
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>