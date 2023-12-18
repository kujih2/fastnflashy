<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/boardLike.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${board.title}</h2>
		<ul class="datail-info">
			<li>
				<c:if test="${!empty board.photo }">
				<img src="${pageContext.request.contextPath }/upload/${board.photo}" 
											width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty board.photo }">
				<img src="${pageContext.request.contextPath }/images/face.png" 
											width="40" height="40" class="my-photo">
				</c:if>
			</li>
			<li>
				${board.mem_id}<br>
				조회 : ${board.hit}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty board.filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${board.filename}" class="detail-img">
		</div>
		</c:if>
		<p>
			${board.content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<%--    좋아요 --%>
			<li class="align-right">
				<img id="fav_like" class="likeicon" data-num="${board.board_num}" data-likestatus="1"
								src="${pageContext.request.contextPath }/images/like.png" width="50">
				
				좋아요
				<span id="likecount"></span>	
			</li>
			<li class="align-left">
				<img id="fav_dislike" class="likeicon" data-num="${board.board_num}" data-likestatus="2"
								src="${pageContext.request.contextPath }/images/dislike.png" width="50">
				
				싫어요
				<span id="dislikecount"></span>	
			</li>
			<li>
				<c:if test="${!empty board.modify_date}">
					최근 수정일 : ${board.modify_date}
				</c:if>
				작성일 : ${board.reg_date}
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<c:if test="${user_num == board.mem_num }">
				<input type="button" value="수정" onclick="location.href='boardUpdateForm.do?board_num=${board.board_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('boardDelete.do?board_num=${board.board_num}');
						}
					};
				</script>
				</c:if>
			</li>
			
		</ul>
		<%--댓글 PART --%>
		
		<%-- 작업 필요함 --%>
	</div>
</div>
</body>
</html>































