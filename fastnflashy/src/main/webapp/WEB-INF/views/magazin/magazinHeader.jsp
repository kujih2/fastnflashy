<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id ="magazin_nav">
	<ul class="align-center">
		<li>
			<a href="${pageContext.request.contextPath}/magazin/magazinList.do?sports_category=1">축구</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/magazinList.do?sports_category=2">야구</a>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/magazinList.do">홈</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/magazinList.do?sports_category=3">배구</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/magazinList.do?sports_category=4">농구</a>
		</li>
	</ul>
</div>