<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예매 메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<div>
			<input type="button" value="전체" onclick=""> 
			<input type="button" value="축구" onclick=""> 
			<input type="button" value="야구" onclick=""> 
			<input type="button" value="배구" onclick=""> 
			<input type="button" value="농구" onclick="">
			</div>
			<c:if test="${user_auth==9}">
			<div class="align-right">
				<input type="button" value="경기등록" onclick="location.href='registerBookingForm.do'">
			</div>
			</c:if>
			<div>
				<c:forEach var="match" items="${list}">
				<div>
					${match.schedule_start}<br>
					${match.schedule_team1} VS ${match.schedule_team2}<br>
					${match.team_category}
				</div>
				<input type="button" value="예매가능">
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>