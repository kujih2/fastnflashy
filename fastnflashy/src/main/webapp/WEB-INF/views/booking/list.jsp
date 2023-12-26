<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예매 메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/booking.css">

</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<jsp:include page="/WEB-INF/views/booking/bookingHeader.jsp"/>
		
		<div class="content-main" style="display:table;clear:both;">
			<div>
			<input type="button" value="전체" onclick="" class="booking-ctg-btn"> 
			<input type="button" value="축구" onclick="" class="booking-ctg-btn"> 
			<input type="button" value="야구" onclick="" class="booking-ctg-btn"> 
			<input type="button" value="배구" onclick="" class="booking-ctg-btn"> 
			<input type="button" value="농구" onclick="" class="booking-ctg-btn">
			</div>
			<c:if test="${user_auth==9}">
			<div class="align-right">
				<input type="button" value="경기등록" onclick="location.href='registerBookingForm.do'">
			</div>
			</c:if>
			<div>
				<c:forEach var="match" items="${list}">
				<div class="each-match-cover">
				<div class="each-match">
					${match.schedule_start}<br>
					${match.schedule_team1} VS ${match.schedule_team2}<br>
					<c:choose>
						<c:when test="${match.team_category==0}">
						축구
						</c:when>
						<c:when test="${match.team_category==1}">
						야구
						</c:when>
						<c:when test="${match.team_category==2}">
						배구
						</c:when>
						<c:when test="${match.team_category==3}">
						농구
						</c:when>
					</c:choose>
					
				</div>
				<div class="each-match-btn-cover">
				<input class="each-match-btn" type="button" value="예매가능" onclick="location.href='bookingForm.do?schedule_num=${match.schedule_num}'">
				</div>
				</div>
				</c:forEach>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
		
	</div>
</body>
</html>