<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경기일정 추가</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>경기일정 수정</h2>
		<form id="insert_form" action="insertSchedule.do" method="post">
			<ul>
				<li>
					<label for="team_category">경기종목</label>
					<input type="radio" name="team_category" id="team_category" value="0">축구
					<input type="radio" name="team_category" id="team_category" value="1">야구
					<input type="radio" name="team_category" id="team_category" value="2">배구
					<input type="radio" name="team_category" id="team_category" value="3">농구
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
					<label for="schedule_status">경기현황</label>
					<input type="radio" name="schedule_status" id="schedule_status" value="0">종료
					<input type="radio" name="schedule_status" id="schedule_status" value="1">진행중
					<input type="radio" name="schedule_status" id="schedule_status" value="2">예정
					<input type="radio" name="schedule_status" id="schedule_status" value="3">취소
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
				<input type="button" value="홈으로">
			</div>
		</form>
	</div>
</div>
</body>
</html>