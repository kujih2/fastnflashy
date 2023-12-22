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
<style>
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
.team-name{
	font-size:20px;
}
#buttons{
	align:center;
	padding-bottom:30px;
}
.category{
	margin-left:30px;
	font-weight:bold;
	width:80px;
	height:40px;
	border-radius:15px;
	border: 1px solid skyblue;
	background-color:skyblue;
	color:white;
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
			<input type="button" name="category" class="category" value="축구" data-num="0">
		   	<input type="button" name="category" class="category" value="야구" data-num="1">
		   	<input type="button" name="category" class="category" value="배구" data-num="2">
		   	<input type="button" name="category" class="category" value="농구" data-num="3">
		 </div>
		 <div class="align-center" id="teams">
		 	<c:forEach var="teamList" items="${teamList}">
				 	<div class="team-list">
				 		<a href="teamDetail.do?team_num=${teamList.team_num}&team_category=${teamList.team_category}">
				 		<img src="${pageContext.request.contextPath}/images/teams/${teamList.team_photo}" width="270"><br>
				 		<b class="team-name">${teamList.team_name}</b>
				 		</a>
				 	</div>
		 	</c:forEach>
		 </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>