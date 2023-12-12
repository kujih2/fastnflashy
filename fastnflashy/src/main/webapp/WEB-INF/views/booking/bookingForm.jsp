<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/booking.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script>
$(function(){
	$('#seat td').click(function(){
		var seatClasses = $(this).attr('class')
		var classNames = seatClasses.split(' ')
		var rowName=classNames[0]
		var colName = $(this).parent().attr('class');
		
		if($(this).hasClass('selected-seat')){
			$(this).removeClass('selected-seat')
			$('.'+colName+rowName).remove()
		}else{
			$(this).addClass('selected-seat');	
			$('#my_select').append('<span class="' + colName + rowName + '">'+colName+'행 '+rowName+'열<br></span>')
		}
		
	});
});
</script>
</head>
<body>
	<div class="content-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="booking-main">
			<div class="booking-form-left">
				<h2>티켓 예매하기</h2>
				<p>${schedule.team1_name} VS ${schedule.team2_name}</p>
				<div>
				<%@ include file="seatTable.html" %>
				</div>
			</div>
			<div class="booking-form-right">
				<div>
					<p>관람일 : ${schedule.schedule_start}</p>
				</div>
				<div id="my_select">
					<p>선택좌석</p>
				</div>
				<div class="booking-buttons">
					<input type="button" value="좌석선택완료">
					<input type="button" value="취소">
					<input type="button" value="다시선택">
				</div>
			</div>
		</div>
	</div>

</body>
</html>