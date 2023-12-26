<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/YSCstyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/boardLike.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/boardReply.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${board.title}</h2>
		<ul class="datail-info">
			<li>
				<c:if test="${!empty board.mem_photo }">
				<img src="${pageContext.request.contextPath }/images/profile/${board.mem_photo}" 
											width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty board.mem_photo }">
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
			<img src="${pageContext.request.contextPath}/upload/${board.filename}" class="detail-img" width="100" height="100">
		</div>
		</c:if>
		<p>
			${board.content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			
			
			
			<%-- 좋아요 싫어요 --%>
			<li class="alignToright align-right">
				<img id="fav_like" class="likeicon" data-num="${board.board_num}" data-likestatus="1"
								src="${pageContext.request.contextPath }/images/like.png" width="50">
				
				좋아요
				<span id="likecount" class="likecountcontrol"></span>	
			</li>
			<li class="alignToleft">
				<img id="fav_dislike" class="likeicon" data-num="${board.board_num}" data-likestatus="2"
								src="${pageContext.request.contextPath }/images/dislike.png" width="50">
				
				싫어요
				<span id="dislikecount" class="likecountcontrol"></span>	
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
					let boardNum = ${board.board_num};
					//이벤트 연결
					delete_btn.onclick=function(){
						
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							<%--location.replace('boardDelete.do?board_num=${board.board_num}'); --%>
							location.replace ('boardDeleteConfirmForm.do?board_num=${board.board_num}');

						}
					};
				</script>
				</c:if>
			</li>
			
		</ul>
		<%--댓글 PART --%>
		<div id="reply_div">
			<span class="re-title">댓글 달기</span>
			<form id="re_form">
				<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
				<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content"
				<c:if test="${empty user_num}">disabled="disabled"</c:if>><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user_num}">
					<div id="re_first">
						<span class="letter-count">300/300</span>
					</div>
					<div id="re_second" class="align-right">
						<input type="submit" value="전송">
					</div>
				</c:if>
			</form>
		</div>
		<!-- 댓글 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!-- 댓글 목록 출력 끝 -->
		
		<%-- 댓글 끝--%>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>































