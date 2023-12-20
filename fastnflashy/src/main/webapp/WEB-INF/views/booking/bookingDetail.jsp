<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제 상세내역</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/booking.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	let prices = $('.price');
	let fee = prices.length*1000;
	let total=0;
	for(let i=0;i<prices.length;i++){
		total += parseFloat(prices[i].textContent);
	}
	$('#fee').text(fee);
	$('#total').text(total+fee);
	
	let booked_nums = $('.booked_num');
	let booked_packages = $('.booked_package')
	
	$('#cancel').click(function(){
		let choice = confirm('취소하시겠습니까?');
		if(choice){
			window.location.href = "deleteBooking.do?booked_num="+booked_nums[0].textContent+"&booked_package="+booked_packages[0].value;
		}
	});
	
	
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/booking/bookingHeader.jsp"/>
	
	<div class="content-main">
	<h2>결제내역</h2>
	<table>
	<tr>
		<td>경기날짜</td>
		<td>${schedule.schedule_start}</td>
		<td>경기팀</td>
		<td>${schedule.team1_name}VS${schedule.team2_name}</td>
	</tr>
	</table>
	<table>
		<tr>
			<td>예약번호</td>
			<td>좌석번호</td>
			<td>가격</td>
			<td>이름</td>
			<td>이메일</td>
			<td>취소여부</td>
		</tr>
		<c:forEach var="book" items="${list}">
		<tr>
			<td class="booked_num">${book.booked_num}<input type="hidden" value="${book.booked_package}" class="booked_package"></td>
			<td>${book.seat_col}행${book.seat_row}열</td>
			<td class="price">${book.booked_price}</td>
			<td>${book.booked_name}</td>
			<td>${book.booked_email}</td>
			<td><c:if test="${book.seat_status==1}">취소가능</c:if><c:if test="${book.seat_status==0}">취소됨</c:if></td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-right">
		<input id="cancel" type="button" value="예매 취소">
	</div>
	<table>
		<tr>
			<td>예매 수수료</td>
			<td id="fee"></td>
			<td>총결제금액</td>
			<td id="total"></td>
		</tr>
	</table>
	
	</div>
</div>

</body>
</html>