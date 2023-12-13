<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	$('.booking-header').hide();
	$('.form2').hide();
	
//form1
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
			$('.my-select div').append('<p class="' + colName + rowName + '">'+colName+'행 '+rowName+'열</p>')
		}
		var numOfSeat = $('.selected-seat').length;
		$('#num_of_seat').val(numOfSeat);
		
		$('.my-select span.number-of-seat').text(numOfSeat)
		$('.fee').text(1000*numOfSeat+'원');
		updateOptions(numOfSeat);
	});
	
	$('#reselect').click(function(){
		$('#seat td').removeClass('selected-seat');
		$('.my-select div p').remove();
		numOfSeat = $('.selected-seat').length;
		$('.my-select span.number-of-seat').text(numOfSeat)
	})
	
	$('#toForm2').click(function(){
		if($('.selected-seat').length==0){
			alert('좌석을 선택해야 합니다');
			return;
		}
		$('.form1').hide();
		$('.booking-header').show();
		$('.form2').show();
	});
//form2
	$('#toForm1').click(function(){
		$('.form2').hide();
		$('.form1').show();
	});
	
function updateOptions(numOfSeat){
	$('.ticketSelect').empty();
	for(var i =0;i<=numOfSeat;i++){
		$('.ticketSelect').append('<option value="' + i + '">' + i + '매</option>');
	}
}
	
});
</script>
</head>
<body>
	<div class="content-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="booking-main">
			<jsp:include page="/WEB-INF/views/booking/bookingHeader.jsp" />

		<form action="" method="post">
			<input type="hidden" id="num_of_seat" value="${numOfSeat}">
			<div class="booking-form-left">
				<jsp:include page="/WEB-INF/views/booking/form1Left.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form2Left.jsp" />
			</div>
			<div class="booking-form-right">
				<jsp:include page="/WEB-INF/views/booking/form1Right.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form2Right.jsp" />
				
				
				
			</div>
		</form>
		</div>

	</div>

</body>
</html>