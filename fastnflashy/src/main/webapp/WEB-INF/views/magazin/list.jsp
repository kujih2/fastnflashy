<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매거진</title>
<link rel="stylesheet" href="${pageContent.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp">
	<div class="list-space align-right">
		<input type="button" value="칼럼작성"
				onclick="location.href='columnForm.do'"
			<c:if test="${!empty user_num && user_num == 2}"></c:if>
		>
	</div>
</div>
</body>
</html>