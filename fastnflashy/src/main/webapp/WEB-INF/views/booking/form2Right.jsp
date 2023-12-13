<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="form2">
	<div class="schedule-info">
		<p>${schedule.team1_name}
			VS ${schedule.team2_name}
			<c:set var="originalString" value="${schedule.schedule_start}" />
			<c:set var="subString"
				value="(${fn:substring(originalString,6,8)}.${fn:substring(originalString,10,12)})" />
			${subString} | 월드컵 경기장
		</p>
	</div>
	<div class="my-select">
		<p>My예매정보></p>
		<hr
			style="border: none; height: 3px; background-color: black; width: 80%;">
		<table>
			<tr>
				<td>일시</td>
				<td>${schedule.schedule_start}</td>
			</tr>
			<tr>
				<td>선택좌석
					(<span class="number-of-seat"></span>)매
				</td>
				<td>
					<div>
					</div>
				</td>
			</tr>
			<tr>
				<td>티켓금액</td>
				<td class="sub-total"></td>
			</tr>
			<tr>
				<td>수수료</td>
				<td class="fee"></td>
			</tr>
		</table>
	</div>
	<div class="booking-buttons">
		<input type="button" value="이전단계" id="toForm1"> <input
			type="button" value="다음단계" id="toForm3">
	</div>
</div>