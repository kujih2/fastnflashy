<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경기 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/match.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/match/matchHeader.jsp"/>
<div class="content-main">
	<h2>경기 상세</h2>
 <div class="align-center">
 	<div class="bar"></div>
	<div class="result-match">
		<div class="team-name">${detailSchedule.team1_name}</div>
			<div class="team-img"><img src="${pageContext.request.contextPath}/images/teams/${detailSchedule.team1_photo}"width="150"></div>
		<c:if test="${detailSchedule.schedule_status == 0}"> 
			<div class="team-score">${detailSchedule.result_team1Score} </div>
		</c:if>
		<c:if test="${detailSchedule.schedule_status == 0}"> 
		<div class="status">
			경기 종료
			<br>
			<div class="start">${detailSchedule.schedule_start}</div>
		</div>
		</c:if>
		<c:if test="${detailSchedule.schedule_status == 1}"> 
		<div class="status">
			진행중
			<br>
			<div class="start">${detailSchedule.schedule_start}</div>
		</div>
		</c:if>
		<c:if test="${detailSchedule.schedule_status == 2}"> 
		<div class="status">
			경기 예정
			<br>
			<div class="start">${detailSchedule.schedule_start}</div>
		</div>
		</c:if>
		<c:if test="${detailSchedule.schedule_status == 3}"> 
		<div class="status">
			경기 취소
			<br>
			<div class="start">${detailSchedule.schedule_start}</div>
		</div>
		</c:if>
		<div class="team-score">
			<c:if test="${detailSchedule.schedule_status == 0}"> 
				${detailSchedule.result_team2Score} 
			</c:if>
		</div>
		<div class="team-img"><img src="${pageContext.request.contextPath}/images/teams/${detailSchedule.team2_photo}"width="150"></div>
			<div class="team-name" >${detailSchedule.team2_name}</div>
	</div>
</div>
	
	<div class="recent-match">
	 <h2>최근 양팀의 경기</h2>
	 <c:if test="${!empty recentResultList}">
		<c:forEach var="recentMatch" items="${recentResultList}">
		<div class="recent-name">
				${recentMatch.team1_name} <img src="${pageContext.request.contextPath}/images/teams/${recentMatch.team1_photo}"><span class="recent-score"<c:if test="${recentMatch.result_match == 1}">style="color:gray;"</c:if>>${recentMatch.result_team1Score}</span> 
				<span class="recent-start">${recentMatch.schedule_start}</span> <span class="recent-score" <c:if test="${recentMatch.result_match == 0}">style="color:gray;"</c:if>>${recentMatch.result_team2Score}</span> <img src="${pageContext.request.contextPath}/images/teams/${recentMatch.team2_photo}"> ${recentMatch.team2_name}<br>
		</div>		
		</c:forEach>
	</c:if>
	<c:if test="${empty recentResultList}">
		<h5>최근 양팀의 경기 전적이 없습니다.</h5>
	</c:if>
	</div>
</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>