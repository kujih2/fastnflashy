<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경기일정 추가</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
$(function(){
	//경기일정이 있는 날짜만 선택 가능
	 // JavaScript 배열 초기화
   var availableDates = [];

   // 클래스가 "abledate"인 모든 요소에 대해 반복
   document.querySelectorAll('.abledate').forEach(function(element) {
       // 배열에 값을 추가
       availableDates.push(element.value);
   });
	
	function available(date){
		
		var thismonth = date.getMonth()+1;
		var thisday = date.getDate();
		
		if(thismonth<10){
			thismonth = "0"+thismonth;
		}
		if(thisday<10){
			thisday = "0"+thisday;
		}
		ymd = date.getFullYear() + "-" + thismonth + "-" + thisday;
		
		if($.inArray(ymd,availableDates)>=0){
			return[true,"",""];
		}else{
			return[false,"",""];
		}
	}
	
   //input을 datepicker로 선언
   $("#datepicker").datepicker({
       dateFormat: 'yy-mm-dd' //달력 날짜 형태
       ,beforeShowDay: available
       ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
       ,showMonthAfterYear:true // 월- 년 순서가아닌 년도 - 월 순서
       ,changeYear: true //option값 년 선택 가능
       ,changeMonth: true //option값  월 선택 가능                
       ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
       ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
       ,buttonImageOnly: true //버튼 이미지만 깔끔하게 보이게함
       ,buttonText: "선택" //버튼 호버 텍스트              
       ,yearSuffix: "년" //달력의 년도 부분 뒤 텍스트
       ,monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 텍스트
       ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip
       ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 텍스트
       ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 Tooltip
       ,minDate: "-5Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
       ,maxDate: "+5y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)  
   		,onSelect: function (date) {
   		location.href='schedule.do?category=9'+'&date='+$('#datepicker').val();
       }
       
   });                    
   
   //초기값을 오늘 날짜로 설정해줘야 합니다.
   $('#datepicker').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)  
   
  
	//이벤트 연결
	$('#insert_form').submit(function(){
		
		let radio = document.querySelectorAll('input[type=radio]');
	    let checkedRadioCount = $('input[type=radio]:checked').length;

	    if (checkedRadioCount < 2) {
	        for (let i = 0; i < radio.length; i++) {
	           alert('라디오버튼 체크 확인');
	            return false;
	        }
	    }
		let items = document.querySelectorAll('input[type="text"],input[type="number"]');
		for(let i=0;i<items.length;i++){
			if(items[i].value.trim()==''){
				let label =document.querySelector('label[for="'+items[i].id+'"]');
				alert(label.textContent + '항목은 필수 입력');
				items[i].value ='';
				items[i].focus();
				return false;
			}
		}//end of for
		//시작일 또는 종료일의 길이가 12자리보다 짧을 때
		if($('#schedule_start').val().length != 12 && isNaN($('#schedule_start').val()) || $('#schedule_end').val().length != 12 && isNaN($('#schedule_end').val()) ){
			alert('년월일시분의 형식 12자리 숫자로만 적을것. ex)202312110900');
			return false;
		}
		//시작일이 종료일 보다 클 경우 return false
		if(parseInt($('#schedule_start').val()) >=  parseInt($('#schedule_end').val())){
			alert('종료일이 시작일보다 커야 합니다.');
			return false;
		}
		//팀1과 팀2의 팀번호가 같은 경우 return false
		if(parseInt($('#schedule_team1').val()) ==  parseInt($('#schedule_team2').val())){
			alert('team1과 team2의 번호가 달라야 합니다.');
			return false;
		}
		//최소 경기시간보다 짧게 설정된 경우
		if((parseInt($('#schedule_end').val()) -  parseInt($('#schedule_start').val())) < 45){
			alert('최소 경기시간은 45분 이상으로 맞춰주세요.');
			return false;
		}
	});
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>경기일정 추가</h2>
		<c:forEach var="ableDate" items="${ableDateList}">
			<input type="hidden" id="scheduleDate" class="abledate"  value=${ableDate.schedule_date}>
		</c:forEach>
		<div class="align-center"> 
			 <p><input type="text" value="일정 확인하기" id="datepicker"></p>
		</div>
		<form id="insert_form" action="insertSchedule.do" method="post">
			<ul>
				<li>
					<label>경기종목</label>
					<input type="radio" name="team_category" id="team_category0" value="0">축구
					<input type="radio" name="team_category" id="team_category1" value="1">야구
					<input type="radio" name="team_category" id="team_category2" value="2">배구
					<input type="radio" name="team_category" id="team_category3" value="3">농구
				</li>
				<li>
					<label for="schedule_start">경기 시작일</label>
					<input type="text" name="schedule_start" id="schedule_start">
				</li>
				<li>
					<label for="schedule_end">경기 종료일</label>
					<input type="text" name="schedule_end" id="schedule_end">
				</li>
				<li>
					<label>경기현황</label>
					<input type="radio" name="schedule_status" id="schedule_status2" value="2">예정
					<input type="radio" name="schedule_status" id="schedule_status3" value="3">취소
				</li>
				<li>
					<label for="schedule_team1">경기팀 1</label>
					<input type="number" name="schedule_team1" id="schedule_team1">
				</li>
				<li>
					<label for="schedule_team2">경기팀 2</label>
					<input type="number" name="schedule_team2" id="schedule_team2">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="reset" value="취소">
			</div>
		</form>
	</div>
</div>
</body>
</html>