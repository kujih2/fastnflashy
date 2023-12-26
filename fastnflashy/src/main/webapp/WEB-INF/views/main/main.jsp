<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/match.css">
<style>

.match-bar{
	width:100%;
	height:150px;
}
.magazine{
   float:left;
	width:65%;
	height:480px;
	margin-top: 80px;
}
.booking{
	margin-left:66.5%;
	width:33%;
	height:280px;
	margin-top: 80px;
	border:none;
}
.board{
 	float:left;
	width:55%;
	height:500px;
	margin-top:-200px;
	margin-left:50px;
	border:none;
}
.hit-magazine{
	margin-left:66.5%;
	width:35%;
	height:500px;
	margin-top: 20px;
}
.recommend-magazine{

	width:35%;
	height:500px;
	margin-left:66.5%; 
	margin-top: 20px;
	margin-bottom:100px;
}



</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	
		<div class="match-bar">
			<div class="matchMain-content">
				<c:if test="${!empty scheduleList}">
					<c:forEach var="schedule" items="${scheduleList}" begin="0" end="3">
						<div class="matchMain-box" onclick="location.href='${pageContext.request.contextPath}/match/detailSchedule.do?num=${schedule.schedule_num}'">
							<c:if test="${schedule.team_category == 0 }"><h4>축구</h4></c:if>
							<c:if test="${schedule.team_category == 1 }"><h4>야구</h4></c:if>
							<c:if test="${schedule.team_category == 2 }"><h4>배구</h4></c:if>
							<c:if test="${schedule.team_category == 3 }"><h4>농구</h4></c:if>
							<hr>
							<div class="matchMain-team">
								<img src="${pageContext.request.contextPath}/images/teams/${schedule.team1_photo}" width="30"><br>
								${schedule.team1_name}
							</div>
							<div class="matchMain-status">
								<span>VS</span><br>
								<span>${schedule.schedule_time}</span>
										<c:if test="${schedule.schedule_status==0}">
								 			<span style="color:red">종료</span>
							 			</c:if>
							 			<c:if test="${schedule.schedule_status==1}">
								 			<span style="color:blue">진행중</span>
							 			</c:if>
							 			<c:if test="${schedule.schedule_status==2}">
								 			<span style="color:blue">예정</span>
							 			</c:if>
							 			<c:if test="${schedule.schedule_status==3}">
								 			<span style="color:red">경기취소</span>
							 			</c:if>
							</div>
							<div class="matchMain-team2">
								<img src="${pageContext.request.contextPath}/images/teams/${schedule.team2_photo}" width="30"><br>
								${schedule.team2_name}
							</div>
						
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty scheduleList}">
					<h3>오늘의 경기는 없습니다.</h3>
				</c:if>
			</div>
		</div>
		<div class="magazine">
		<h2>헤드라인</h2>
	    <c:if test="${not empty list4}">
        <table>
            <c:forEach var="magazin" items="${list4}">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/magazin/magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><img src="${pageContext.request.contextPath}/upload/magazin/${magazin.mg_photo1}" width="150" height="100"></a></td>
                        <td><a href="${pageContext.request.contextPath}/magazin/magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><b>${magazin.mg_title}</b><br><br>${magazin.mg_content}</a></td>
                    </tr>
            </c:forEach>
        </table>
    	</c:if>
		</div>
		<div class="booking">
		<div style="padding:10px;overflow:auto; height:280px;">
		<c:forEach var="match" items="${list}">
				<div class="each-match-cover" style="padding:5px;border:1px solid black; height:40px;border-radius:3px;margin:10px;">
				<div class="each-match" style="float:left; font-size:14px;text-align:center;width:65%;">
					${match.schedule_start} |
					${match.schedule_team1} VS ${match.schedule_team2}<br>
					<c:choose>
						<c:when test="${match.team_category==0}">
						축구
						</c:when>
						<c:when test="${match.team_category==1}">
						야구
						</c:when>
						<c:when test="${match.team_category==2}">
						배구
						</c:when>
						<c:when test="${match.team_category==3}">
						농구
						</c:when>
					</c:choose>
					
				</div>
				<div class="each-match-btn-cover" style="float:right;width:30%;">
				<input class="each-match-btn" style="background:skyblue;border:none;border-radius:3px;"type="button" value="예매가능" onclick="location.href='${pageContext.request.contextPath}/booking/bookingForm.do?schedule_num=${match.schedule_num}'">
				</div>
				</div>
				</c:forEach>
		
		</div>
		</div>
		<div class="hit-magazine">
		<h2>가장많이본 칼럼</h2>
	    <c:if test="${not empty list3}">
        <table>
            <c:forEach var="magazin" items="${list3}">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/magazin/magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><img src="${pageContext.request.contextPath}/upload/magazin/${magazin.mg_photo1}" width="150" height="100"></a></td>
                        <td><a href="${pageContext.request.contextPath}/magazin/magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><b>${magazin.mg_title}</b><br><br>${magazin.mg_content}</a></td>
                    </tr>
            </c:forEach>
        </table>
    	</c:if>
		
		</div>
		<div class="board">
			<table>
				<tr>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<th>추천수</th>
				</tr>
				<c:forEach var="board" items="${list2}">
				<tr>
				
					<td><a href="${pageContext.request.contextPath}/board/boardDetail.do?board_num=${board.board_num}">
					<b>[
					<c:if test="${board.board_category == 1}">축구</c:if>
					<c:if test="${board.board_category == 2}">야구</c:if>
					<c:if test="${board.board_category == 3}">배구</c:if>
					<c:if test="${board.board_category == 4}">농구</c:if>
					]</b>
					${board.title}</a></td>
				
					<td>${board.mem_id}</td>
					<td>${board.hit}</td>
					<td>${board.net_likes}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div class="recommend-magazine">
		<h2>최신 칼럼</h2>
	    <c:if test="${not empty list5}">
        <table>
            <c:forEach var="magazin" items="${list5}">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/magazin/magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><img src="${pageContext.request.contextPath}/upload/magazin/${magazin.mg_photo1}" width="150" height="100"></a></td>
                        <td><a href="${pageContext.request.contextPath}/magazin/magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><b>${magazin.mg_title}</b><br><br>${magazin.mg_content}</a></td>
                    </tr>
            </c:forEach>
        </table>
    	</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>







