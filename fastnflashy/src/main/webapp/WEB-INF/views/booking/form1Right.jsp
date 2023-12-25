<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="form1">
	<div class="schedule-info">
		<p>관람일 : ${schedule.schedule_start}</p>
	</div>
	<div class="my-select">
		<p class="my-select-info1">선택좌석</p>
		<p class="my-select-info2">
			총 <span class="number-of-seat">0</span>석 선택되었습니다
		</p>
		<hr
			style="border: none; height: 3px; background-color: black; width: 100%;">
		<div class="listOfSeat" id="listOfSeat">
		</div>
	</div>
	<div class="booking-buttons">
		<input type="button" value="좌석선택완료" class="toForm2 toNext">
		<input class="toBefore" type="button" value="취소" 	onclick="location.href='${pageContext.request.contextPath}/booking/list.do'">
		<input type="button" value="다시선택" id="reselect">
	</div>
</div>