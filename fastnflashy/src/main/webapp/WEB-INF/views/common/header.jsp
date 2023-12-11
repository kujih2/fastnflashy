<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->

<div id="main_nav">
	<ul>
		<li>
			fast&amp;flashy
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/magazin/List.do">매거진</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/item/adminList.do">게시판</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/match/schedule.do?category=9">경기일정</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/booking/list.do">예매</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/item/adminList.do">이벤트</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/item/adminList.do">고객센터</a>
		</li>
		<c:if test="${!empty user_num && user_auth == 9}">
		
		</c:if>
		<c:if test="${!empty user_num}">
		<li><a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a></li>
		</c:if>
		<c:if test="${!empty user_num && !empty user_photo}">
		<li class="menu-profile"><img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo"></li>
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}">
		<li class="menu-profile"><img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo"></li>
		</c:if>
		<c:if test="${!empty user_num}">
		<li class="menu-logout">
			[<span>${user_id}</span>]
			<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</li>
		</c:if>
		<c:if test="${empty user_num}">
		<li><a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a></li>
		<li><a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a></li>
		</c:if>
	</ul>
</div>
<!-- header 끝 -->




