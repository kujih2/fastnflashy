<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="form1">
	<h2>티켓 예매하기</h2>
	<p>${schedule.team1_name}VS${schedule.team2_name}</p>
	<div>
		<jsp:include page="/WEB-INF/views/booking/seatTable.jsp" />
	</div>
</div>