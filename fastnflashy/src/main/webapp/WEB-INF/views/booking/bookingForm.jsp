<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/booking.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script>
$(function(){
	$('.booking-header').hide();
	$('.form2').hide();
	$('.form3').hide();
	$('.form4').hide();
	
	//let array = ${array};
	
	var bookedSeats = $(".booked-seat")
	var count = bookedSeats.length;
	for(let i =0;i<count;i++){
	    let col = bookedSeats.eq(i).data('col');
	    let row = bookedSeats.eq(i).data('row');
	    $('tr.'+col+' td.'+row).removeClass('clickable')
	    $('tr.'+col+' td.'+row).addClass('booked-seat')
	}
	var numOfSeat = 0;
	var sumOfSeats = 0;
	var fee = 0;
	var predictedBalance = 0;
	var totalPirce = 0;
	
//form1
	$('#seat td.clickable').click(function(){
		var seatClasses = $(this).attr('class')
		var classNames = seatClasses.split(' ')
		var rowName=classNames[0]
		var colName = $(this).parent().attr('class');
		if($(this).hasClass('selected-seat')){
			$(this).removeClass('selected-seat')
			$('.'+colName+rowName).remove()
		}else{
			$(this).addClass('selected-seat');	
			$('.my-select .listOfSeat').append('<p class="' + colName + rowName + '">'+colName+'행 '+rowName+'열</p>')
			$('#hidden_area').append('<input type="hidden" class="' + colName + rowName + '" name="colName" value="'+colName+'">')
			$('#hidden_area').append('<input type="hidden" class="' + colName + rowName + '" name="rowName" value="'+rowName+'">')
		}
		numOfSeat = $('.selected-seat').length;
		$('#num_of_seat').val(numOfSeat);
		
		$('.my-select span.number-of-seat').text(numOfSeat)
		updateOptions(numOfSeat);
		updateInputForm(numOfSeat);
	});
	
	$('#reselect').click(function(){
		$('#seat td').removeClass('selected-seat');
		$('.my-select div p').remove();
		numOfSeat = $('.selected-seat').length;
		$('.my-select span.number-of-seat').text(numOfSeat)
	})
	
	$('.toForm2').click(function(){
		if($('.selected-seat').length==0){
			alert('좌석을 선택해야 합니다');
			return;
		}
		$('.form1').hide();
		$('.booking-header').show();
		$('.form2').show();
	});
//form2
    $('.ticketSelect').change(function(){
		sumOfSeats = 0;
    	let sumOfPrice = 0;
    	let sumOfFee =0;
    	$('.ticketSelect').each(function(){
            sumOfSeats += parseInt($(this).val());
        });
        if (sumOfSeats > numOfSeat) {
            alert('총 좌석 개수는 ' + numOfSeat + '개 입니다');
            $(this).val('0');
        }
        $('.ticketSelect').each(function(index){
        	   let selectedValue = parseInt($(this).val());
               switch(index){
               	case 0:
               		fee = selectedValue*1000;
               		selectedValue *= 12000;
               		break;
               	case 1:
               		fee = selectedValue*1000;
               		selectedValue *= 6000;
               		break;
               	case 2:
               		fee = selectedValue*1000;
               		selectedValue *= 1500;
               		break;
               }
                    sumOfPrice += selectedValue;
                    sumOfFee +=fee;
                    totalPrice = sumOfPrice+sumOfFee;
                    $('.sub-total').text(sumOfPrice+'원');
                    $('.all-total').text(sumOfPrice+sumOfFee+'원');
                    $('.fee').text(sumOfFee+'원');
                    predictedBalance = parseInt($('#current_balance').text())-(totalPrice)
                    if(predictedBalance<0){
	                    $('#predicted_balance').text('잔액이 부족합니다');
                    	
                    }else{
                	    $('#predicted_balance').text(predictedBalance+'원');
                    }
                    
        })
    });
    
	$('.toForm1').click(function(){
		$('.form2').hide();
		$('.form1').show();
	});
    
    $('.toForm3').click(function(){
    	if(sumOfSeats != numOfSeat){
    		alert('좌석은 ' +numOfSeat+'개 선택해야합니다')
    		return;
    	}

		$('.form2').hide();
		$('.form3').show();
    	
    });
//form3
	$('.toForm2').click(function(){
		$('.form3').hide();
		$('.form2').show();
	});
	$('.toForm4').click(function(){
		let items = document.querySelectorAll('.input-check');
		for(let i=0;i<items.length;i++){
			if(items[i].value.trim()==''){
				let label = document.querySelector('label[for="'+items[i].id+'"]')
				alert(label.textContent+' 항목은 필수 입력')
				items[i].value = '';
				items[i].focus();
				return;
			}
		}
		$('.form3').hide();
		$('.form4').show();
	});
//form4
	$('.toForm3').click(function(){
    	if(sumOfSeats != numOfSeat){
    		return;
    	}
		$('.form4').hide();
		$('.form3').show();
	});
	
	$('#booking_form').submit(function(){
		if(predictedBalance<0){
			alert('잔액이 부족합니다');
			return false;
		}
		$('#hidden_area').append('<input type="hidden" name="confirmed_price" value="'+totalPrice+'">')
});
	
	
function updateOptions(numOfSeat){
	$('.ticketSelect').empty();
	for(var i =0;i<=numOfSeat;i++){
		$('.ticketSelect').append('<option value="' + i + '">' + i + '매</option>');
	}
}
function updateInputForm(numOfSeat){
	$('.input-form').empty();
	for(var i=1;i<=numOfSeat;i++){
		$('.input-form').append('<p>예매자'+i+'</p>'
								+'<ul>'
								+'<li>'
								+	'<label for="name' + i + '">이름</label>'
								+	'<input type="text" name="name'+i+'" id="name'+i+'" maxlength="12" class="input-check">'
								+'</li>'
								+'<li>'
								+	'<label for="email' + i + '">이메일</label>'
								+	'<input type="email" name="email'+i+'" id="email'+i+'" maxlength="15" class="input-check">'
								+'</li>'
								+'</ul>')
		
	}
}
	
});
</script>
</head>
<body>
<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<jsp:include page="/WEB-INF/views/booking/bookingHeader.jsp"/>
	<div class="content-main">
		<div class="booking-main">
		<form id="booking_form" action="booking.do" method="post">
			<div id="hidden_area">
				<input type="hidden" name="schedule_num" value="${schedule_num}">
				<input type="hidden" name="num_of_seat" id="num_of_seat"value="${numOfSeat}">
			</div>
			<div id="booked_seat">
				<input type="hidden" id="num_of_booked_seat" value="${count}">
				<c:forEach var="detail" items="${list}">
					<input type="hidden" class="booked-seat" data-col="${detail.seat_col}" data-row="${detail.seat_row}">
				</c:forEach>
			</div>
			<div class="booking-form-left">
				<jsp:include page="/WEB-INF/views/booking/form1Left.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form2Left.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form3Left.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form4Left.jsp" />
			</div>
			<div class="booking-form-right">
				<jsp:include page="/WEB-INF/views/booking/form1Right.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form2Right.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form3Right.jsp" />
				<jsp:include page="/WEB-INF/views/booking/form4Right.jsp" />
				
				
				
			</div>
		</form>
		</div>

	</div>
			<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	</div>

</body>
</html>