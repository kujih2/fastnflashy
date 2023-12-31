<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매거진</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/magazin.css">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('search_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		let keyword = document.getElementById('keyword');
		if(keyword.value.trim()==''){
			alert('검색어를 입력하세요');
			keyword.value = '';
			keyword.focus();
			return false;
		}
	};
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/magazin/magazinHeader.jsp"/>
	
	<div class="magazin-headline">
		<div class="list-space align-right">
			<c:if test="${!empty user_num && user_auth == 2}">
				<input type="button" value="칼럼쓰기"
					onclick="location.href='magazinWriteForm.do'">
			</c:if>	
		</div>
	    <c:if test="${not empty headlineList}">
        <table>
            <c:forEach var="magazin" items="${headlineList}">
	    	<h2><a href="magazinDetail.do?mg_board_num=${magazin.mg_board_num}">${magazin.mg_title}</a></h2>
                    <tr>
                        <td><a href="magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><img src="${pageContext.request.contextPath}/upload/magazin/${magazin.mg_photo1}" width="550" height="300"></a></td>
                        <td><a href="magazinDetail.do?mg_board_num=${magazin.mg_board_num}">${magazin.mg_content}</a></td>
                    </tr>
               
            </c:forEach>
        </table>
    	</c:if>
	</div>
	
	<div class="magazin-content-main">
		<h2>주목받는 칼럼</h2>
        <table>
            <c:forEach var="magazin" items="${getMostHitList}">
                    <tr>
                        <td><a href="magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><img src="${pageContext.request.contextPath}/upload/magazin/${magazin.mg_photo1}" width="200" height="150"></a></td>
                        <td><a href="magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><b>${magazin.mg_title}</b><br><br>${magazin.mg_content}</a></td>
                    </tr>
            </c:forEach>
        </table>
	</div>
	
	<div class="magazin-content-main">
		<h2 class="align-left">최신 칼럼</h2>
		<form id="search_form" action="magazinList.do" method="get" class="magazin-search">
			<input type="hidden" name="sports_category" value="${param.sports_category}">
			<ul class="search align-right">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}"></c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}"></c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}"></c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>	
			</ul>
		</form>
		<c:if test="${count == 0}">
		<div class="result-display">
			작성된 칼럼이 없습니다.			
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<c:forEach var="magazin" items="${list}">
			<tr>
				<td><a href="magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><img src="${pageContext.request.contextPath}/upload/magazin/${magazin.mg_photo1}" width="200" height="100"></a></td>
				<td><a href="magazinDetail.do?mg_board_num=${magazin.mg_board_num}"><b>${magazin.mg_title}</b><br><br>${magazin.mg_content}</a></td>
			</tr>
			</c:forEach>
		</table><br>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>