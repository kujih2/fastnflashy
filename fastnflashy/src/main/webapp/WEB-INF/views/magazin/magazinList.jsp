<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매거진</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/magazin.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/magazin/magazinHeader.jsp"/>
	<div class="content-main">
		<div class="list-space align-right">
		<c:if test="${!empty user_num && user_auth == 2}">
			<input type="button" value="칼럼쓰기"
				onclick="location.href='magazinWriteForm.do'">
		</c:if>		
		</div>
	</div>
</div>
</body>
</html>