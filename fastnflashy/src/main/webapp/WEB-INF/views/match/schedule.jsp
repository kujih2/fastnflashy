<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경기 일정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">

</script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="content-main">
	<h2>경기 일정</h2>
		<div class="align-right">
			<c:if test="${!empty user_num && user_auth == 9}">
			<input type="button" value="경기일정 삽입" onclick="location.href='insertScheduleForm.do'">
		   </c:if>
	   </div>
	   <div class="align-center">
		   		<input type="button" name="category" class="category" value="전체" onclick="location.href='schedule.do?category=9'">
		   		<input type="button" name="category" class="category" value="축구" onclick="location.href='schedule.do?category=0'">
		   		<input type="button" name="category" class="category" value="야구" onclick="location.href='schedule.do?category=1'">
		   		<input type="button" name="category" class="category" value="배구" onclick="location.href='schedule.do?category=2'">
		   		<input type="button" name="category" class="category" value="농구" onclick="location.href='schedule.do?category=3'">
		 </div>
   </div>
</div>

</body>
</html>