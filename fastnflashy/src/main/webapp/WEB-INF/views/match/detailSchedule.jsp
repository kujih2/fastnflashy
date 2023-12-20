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
<div class="content-main">
	<h2>경기 상세</h2>
	<div class="align-center">
		<p>
			${detailSchedule.team1_name}
			<c:if test="${detailSchedule.schedule_status != 0}"> 
				VS
			</c:if>
		<c:if test="${detailSchedule.schedule_status == 0}"> 
			${detailSchedule.result_team1Score} : ${detailSchedule.result_team2Score}
		</c:if>
			${detailSchedule.team2_name}
		</p>
		<c:if test="${detailSchedule.schedule_status == 0}"> 
			경기 종료
		</c:if>
		<c:if test="${detailSchedule.schedule_status == 1}"> 
			진행중
		</c:if>
		<c:if test="${detailSchedule.schedule_status == 2}"> 
			경기 예정
		</c:if>
		<c:if test="${detailSchedule.schedule_status == 3}"> 
			경기 취소
		</c:if>
		${detailSchedule.schedule_start}
	</div>
	<div class="align-center">
	 <h2>최근 양팀의 경기</h2>
		<c:forEach var="recentMatch" items="${recentResultList}">
			<c:if test="${!empty recentMatch}">
				${recentMatch.schedule_start}${recentMatch.team1_name} <span <c:if test="${recentMatch.result_match == 1}">style="color:gray;"</c:if>>${recentMatch.result_team1Score}</span> 
				: <span <c:if test="${recentMatch.result_match == 0}">style="color:gray;"</c:if>>${recentMatch.result_team2Score}</span> ${recentMatch.team2_name}<br>
			</c:if>
			<c:if test="${empty recentMatch}">
				경기한 전적이 없습니다.
			</c:if>
		</c:forEach>
	</div>
</div>
	
</div>
</body>
</html>