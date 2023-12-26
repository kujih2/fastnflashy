<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기록/순위</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/match.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
    //이벤트 연결
    $('.category').click(function(){
    	location.href='teamList.do?team_category='+$(this).attr('data-num');
    });
    
});
</script>
<style type="text/css">
.team-list{
	float:left;
	width:300px;
	height:300px;
	border:1px solid black;
}
.team-wrap{
	width:1000px;
	border:1px solid black;
}
#teams{
	padding-left:40px;
	margin-top:20px;
	margin-left:40px;
}
.teamList-name{
	font-size:20px;
}
#buttons{
	text-align:center;
	padding-bottom:30px;
}
.content-main{
	display:table;
	clear:both;
}
</style>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/match/matchHeader.jsp"/>
<div class="content-main">
	<h2>팀/구단</h2>
	<div class="align-center" id="buttons">
			<input type="button" name="category" class="category <c:if test="${category == 0}"> clicked</c:if>" value="축구" data-num="0" >
		   	<input type="button" name="category" class="category <c:if test="${category == 1}"> clicked</c:if>" value="야구" data-num="1" >
		   	<input type="button" name="category" class="category <c:if test="${category == 2}"> clicked</c:if>" value="배구" data-num="2" >
		   	<input type="button" name="category" class="category <c:if test="${category == 3}"> clicked</c:if>" value="농구" data-num="3" >
		 </div>
		 <div class="align-center" id="teams">
		 	<c:forEach var="teamList" items="${teamList}">
				 	<div class="team-list">
				 		<a href="teamDetail.do?team_num=${teamList.team_num}&team_category=${teamList.team_category}">
				 		<img src="${pageContext.request.contextPath}/images/teams/${teamList.team_photo}" width="270"><br>
				 		<b class="teamList-name">${teamList.team_name}</b>
				 		</a>
				 	</div>
		 	</c:forEach>
		 </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>