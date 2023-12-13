<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 메인 리스트</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>자유 게시판</h2>
		<div class="list-space align-right"><%-- 글쓰기 버튼 위치 조정 필요!!! --%>
			<input type="button" value="글 쓰기" onclick="location.href='boardWriteForm.do'"
				<c:if test="${empty user_num}">disabled="disabled"</c:if>
			>
		</div>
		<div> <%-- 카테고리 버튼 --%>
			<input type="button" value="전체">  <%--비동기 구현으로 특정 카테고리만 List에 뽑는걸 원함 --%>
			<input type="button" value="축구">
			<input type="button" value="야구">
			<input type="button" value="배구">
			<input type="button" value="농구">
		</div>
		<form id="search_form" action="boardList.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1 }">selected</c:if>>제목</option>	
						<option value="2" <c:if test="${param.keyfield==2 }">selected</c:if>>작성자</option>	
						<option value="3" <c:if test="${param.keyfield==3 }">selected</c:if>>내용</option>	
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword }">
				</li>
				<li>
					<input type="submit" value="검색" >
				</li>
			</ul>
		</form>
		<c:if test="${count == 0}">  
			<div class="result-display">
				표시할 게시물이 없습니다.
			</div>
		</c:if>
		<c:if test="${count > 0}">
			<table>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>날짜</th>
					<th>조회수</th>
					<th>추천수</th>
				</tr>
				<c:forEach var="board" items="${list}">
				<tr>
					<td>${board.board_num}</td>
					<td><a href="boardDetail.do?board_num=${board.board_num}"><b>${board.board_category}</b>${board.title}</a></td><%--카테고리가 실제 종목명으로 표시되게 / 제목 누르면 detail페이지로 링크  --%>
					<td>${board.id}</td>
					<td>${board.reg_date}</td>
					<td>${board.hit}</td>
					<td></td><%--좋아요 + 싫어요 처리 보류 --%>
				</tr>
				</c:forEach>
			</table>
			<div class="align-center">${page}</div>
		</c:if>
		
	
	</div>
</div>
	
	
</body>
</html>