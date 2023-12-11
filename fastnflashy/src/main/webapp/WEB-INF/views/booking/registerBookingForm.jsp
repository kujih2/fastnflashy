<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.schedule.vo.ScheduleVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예매 등록하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#load_match').click(function(){
		initForm();
		$.ajax({
			url:'loadListMatch.do',
			type:'post',
			dataType:'json',
			success:function(param){
				let output = '<table>';
				output += '<tr>';
				output += '<td>경기번호</td><td>팀1</td><td>vs</td><td>팀2</td><td>경기종목</td><td>경기시작일</td>'
				output += '</tr>';
				$(param.list).each(function(index,item){
					output += '<tr class="single-match">';
					output += '<td>' + item.schedule_num + '</td>';
					output += '<td>' + item.schedule_team1 + '</td>';
					output += '<td></td>';
					output += '<td>' + item.schedule_team2 + '</td>';
					output += '<td>'+ item.team_category+'</td>';
					output += '<td>'+ item.schedule_start+'</td>';
					output += '</tr>'
				})
				output +='</table>'
				$('#loaded_matchlist').append(output);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		})
	});//end of click
	function initForm(){
		$('#loaded_matchlist').text('');
	}
});

</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
		<h2>경기 등록하기</h2> 
		<input type="button" value="등록가능 경기목록 불러오기" id="load_match">
		<div id="loaded_matchlist">
		</div>
		<form id="booking_register_form" action="registerBooking.do" method="post">
		<ul>
			<li>
			<label for="schedule">경기일정</label>
			<input type="text" name="schedule" id="schedule" class="input-check" readonly>
			</li>
			<li>
			<label for="stadium">경기장</label>
			<input type="text" name="stadium" id="stadium" class="input-check" readonly>
			</li>
			<li>
			<label for="sport_category">종목</label>
			<input type="text" name="sport_category" id="sport_category" class="input-check" readonly>
			</li>
			<li class="team">
			<label for="team1">경기팀1</label>
			<input type="text" name="team1" id="team1" class="input-check" readonly>
			</li>
			<li class="team">
			<label for="team2">경기팀2</label>
			<input type="text" name="team2" id="team2" class="input-check" readonly>
			</li>
		</ul>
		<div class="align-right">
			<input type="submit" value="등록">
			<input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/booking/list.do'">
		</div>
		</form>
		</div>
	</div>

</body>
</html>