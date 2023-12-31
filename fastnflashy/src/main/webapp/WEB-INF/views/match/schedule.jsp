<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경기 일정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/match.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
$(function() {
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
    $('#datepicker').datepicker('setDate', '${date}'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)  
    
    
    //이벤트 연결
    $('.category').click(function(){
    	location.href='schedule.do?category='+$(this).attr('data-num')+'&date='+$('#datepicker').val();
    });
    
});
</script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/match/matchHeader.jsp"/>
<div class="content-main">
	<h2>경기 일정</h2>
		<div class="align-right">
		<c:forEach var="ableDate" items="${ableDateList}">
			<input type="hidden" id="scheduleDate" class="abledate"  value=${ableDate.schedule_date}>
		</c:forEach>
			<c:if test="${!empty user_num && user_auth == 9}">
			<input type="button" value="경기일정 삽입" onclick="location.href='insertScheduleForm.do'">
		   </c:if>
	   </div>
	   <div class="align-center">
	   <p><input type="text" id="datepicker"></p>
		   		<input type="button" name="category" class="category <c:if test="${category == 9}"> clicked</c:if>" value="전체" data-num="9">
		   		<input type="button" name="category" class="category <c:if test="${category == 0}"> clicked</c:if>" value="축구" data-num="0">
		   		<input type="button" name="category" class="category <c:if test="${category == 1}"> clicked</c:if>" value="야구" data-num="1">
		   		<input type="button" name="category" class="category <c:if test="${category == 2}"> clicked</c:if>" value="배구" data-num="2">
		   		<input type="button" name="category" class="category <c:if test="${category == 3}"> clicked</c:if>" value="농구" data-num="3">
		 </div>
		  
		 <c:if test="${!empty scheduleList && (category == 9 || category == 0 )}">
			 <div class="align-center">
			 	<div class="category-bar">축구</div>
			 	  <c:forEach var="schedule" items="${scheduleList}">
			 	  	<c:if test="${schedule.team_category == 0}">
			 	  	<c:if test="${!empty user_num && user_auth == 9  && schedule.schedule_status == 2 || schedule.schedule_status == 3}">
								<input type="button" class="btn" value="일정 수정" onclick="location.href='modifyScheduleForm.do?num=${schedule.schedule_num}'">
		   					</c:if>
			 	  	<div class="schedule-bar" onclick="location.href='detailSchedule.do?num=${schedule.schedule_num}'">
				 	<table class="non-border">
				 		<tr>	
				 			<td>${schedule.schedule_time}</td>
				 			<td>${schedule.team1_name}</td>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team1_photo}" width="70"></td>
				 			<c:if test="${schedule.schedule_status==0}">
					 			<td>${schedule.result_team1Score}</td>
					 			<td style="color:red">종료</td>
					 			<td>${schedule.result_team2Score}</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==1}">
					 			<td style="color:blue">진행중</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==2}">
					 			<td style="color:blue">예정</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==3}">
					 			<td style="color:red">경기취소</td>
				 			</c:if>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team2_photo}" width="70"></td>
				 			<td>${schedule.team2_name}</td>
				 		</tr>
				 	</table>
				 	</div>
				 	</c:if>
				 </c:forEach>
			 </div>
		</c:if>
		
		<c:if test="${!empty scheduleList && (category == 9 || category == 1 )}">
			 <div class="align-center">
			 	<div class="category-bar">야구</div>
			 	  <c:forEach var="schedule" items="${scheduleList}">
			 	  	<c:if test="${schedule.team_category == 1}">
			 	  	<c:if test="${!empty user_num && user_auth == 9  && schedule.schedule_status == 2 || schedule.schedule_status == 3}">
								<input type="button" class="btn" value="일정 수정" onclick="location.href='modifyScheduleForm.do?num=${schedule.schedule_num}'">
		   					</c:if>
			 	  	<div class="schedule-bar" onclick="location.href='detailSchedule.do?num=${schedule.schedule_num}'">
				 	<table class="non-border">
				 		<tr>	
				 			<td>${schedule.schedule_time}</td>
				 			<td>${schedule.team1_name}</td>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team1_photo}" width="70"></td>
				 			<c:if test="${schedule.schedule_status==0}">
					 			<td>${schedule.result_team1Score}</td>
					 			<td style="color:red">종료</td>
					 			<td>${schedule.result_team2Score}</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==1}">
					 			<td style="color:blue">진행중</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==2}">
					 			<td style="color:blue">예정</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==3}">
					 			<td style="color:red">경기취소</td>
				 			</c:if>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team2_photo}" width="70"></td>
				 			<td>${schedule.team2_name}</td>
				 		</tr>
				 	</table>
				 	</div>
				 	</c:if>
				 </c:forEach>
			 </div>
		</c:if>
		
		<c:if test="${!empty scheduleList && (category == 9 || category == 2 )}">
			 <div class="align-center">
			 	<div class="category-bar">배구</div>
			 	  <c:forEach var="schedule" items="${scheduleList}">
			 	  	<c:if test="${schedule.team_category == 2}">
			 	  	<c:if test="${!empty user_num && user_auth == 9  && schedule.schedule_status == 2 || schedule.schedule_status == 3}">
								<input type="button" class="btn" value="일정 수정" onclick="location.href='modifyScheduleForm.do?num=${schedule.schedule_num}'">
		   					</c:if>
			 	  	<div class="schedule-bar" onclick="location.href='detailSchedule.do?num=${schedule.schedule_num}'">
				 	<table class="non-border">
				 		<tr>	
				 			<td>${schedule.schedule_time}</td>
				 			<td>${schedule.team1_name}</td>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team1_photo}" width="70"></td>
				 			<c:if test="${schedule.schedule_status==0}">
					 			<td>${schedule.result_team1Score}</td>
					 			<td style="color:red">종료</td>
					 			<td>${schedule.result_team2Score}</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==1}">
					 			<td style="color:blue">진행중</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==2}">
					 			<td style="color:blue">예정</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==3}">
					 			<td style="color:red">경기취소</td>
				 			</c:if>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team2_photo}" width="70"></td>
				 			<td>${schedule.team2_name}</td>
				 		</tr>
				 	</table>
				 	</div>
				 	</c:if>
				 </c:forEach>
			 </div>
		</c:if>
		
		<c:if test="${!empty scheduleList && (category == 9 || category == 3 )}">
			 <div class="align-center">
			 	<div class="category-bar">농구</div>
			 	  <c:forEach var="schedule" items="${scheduleList}">
			 	  	<c:if test="${schedule.team_category == 3}">
			 	  	<c:if test="${!empty user_num && user_auth == 9  && schedule.schedule_status == 2 || schedule.schedule_status == 3}">
								<input type="button" class="btn" value="일정 수정" onclick="location.href='modifyScheduleForm.do?num=${schedule.schedule_num}'">
		   					</c:if>
			 	  	<div class="schedule-bar" onclick="location.href='detailSchedule.do?num=${schedule.schedule_num}'">
				 	<table class="non-border">
				 		<tr>	
				 			<td>${schedule.schedule_time}</td>
				 			<td>${schedule.team1_name}</td>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team1_photo}" width="70"></td>
				 			<c:if test="${schedule.schedule_status==0}">
					 			<td>${schedule.result_team1Score}</td>
					 			<td style="color:red">종료</td>
					 			<td>${schedule.result_team2Score}</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==1}">
					 			<td style="color:blue">진행중</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==2}">
					 			<td style="color:blue">예정</td>
				 			</c:if>
				 			<c:if test="${schedule.schedule_status==3}">
					 			<td style="color:red">경기취소</td>
				 			</c:if>
				 			<td><img src="${pageContext.request.contextPath}/images/teams/${schedule.team2_photo}" width="70"></td>
				 			<td>${schedule.team2_name}</td>
				 		</tr>
				 	</table>
				 	</div>
				 	</c:if>
				 </c:forEach>
			 </div>
		</c:if>
		
		 <c:if test="${empty scheduleList}">
		 	<div class="align-center">
		 		<h2>경기 일정이 없습니다.</h2>
		 	</div>
		 </c:if>
		 
   </div>
   <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>


</body>
</html>