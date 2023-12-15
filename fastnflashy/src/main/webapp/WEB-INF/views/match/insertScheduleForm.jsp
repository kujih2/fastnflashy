<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경기일정 추가</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
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
					<label for="schedule_start">경기 종료일</label>
					<input type="text" name="schedule_end" id="schedule_end">
				</li>
				<li>
					<label>경기현황</label>
					<input type="radio" name="schedule_status" id="schedule_status0" value="0">종료
					<input type="radio" name="schedule_status" id="schedule_status1" value="1">진행중
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