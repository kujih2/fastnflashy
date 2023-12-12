<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id ="magazin_nav">
	<ul class="align-center">
		<li>
			<a href="${pageContext.request.contextPath}/magazin/soccer.do">축구</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/baseball.do">야구</a>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/magazinList.do">홈</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/volleyball.do">배구</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/basketball.do">농구</a>
		</li>
	</ul>
</div>