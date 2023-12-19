<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>칼럼 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/magazin.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
window.onload=function(){
	let categoryValue = "";
	
	if(${magazin.sports_category == 1}){
		categoryValue = "축구";
	}else if(${magazin.sports_category == 2}){
		categoryValue = "야구";

	}else if(${magazin.sports_category == 3}){
		categoryValue = "배구";

	}else if(${magazin.sports_category == 4}){
		categoryValue = "농구";
	}
	let categoryInfoElements = document.getElementsByClassName("categoryInfo");
	 	for (let i = 0; i < categoryInfoElements.length; i++) {
	        categoryInfoElements[i].innerHTML = categoryValue;
	    	}
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/magazin/magazinHeader.jsp"/>
	<div class="content-main">
		<h4>칼럼 : <span class="categoryInfo"></span></h4>
		<h2>${magazin.mg_title}</h2>
		<div>${magazin.mem_name} <span class="categoryInfo"></span>칼럼니스트</div>
		<ul class="detail-info">
			<li>
				작성일 : ${magazin.mg_reg_date}
				<c:if test="${!empty magazin.mg_modify_date}">
					수정됨 : ${magazin.mg_modify_date}
				</c:if>
			</li>
			<li class="align-right">
			조회수 : ${magazin.mg_hit} 
			<c:if test="${user_num == magazin.mem_num}">
			<input type="button" value="수정" 
					onclick="location.href='magazinUpdateForm.do?mg_board_num=${magazin.mg_board_num}'">
			</c:if>
			<c:if test="${user_auth == 9}">
			<input type="button" value="[관리자]칼럼삭제" id="delete_but">
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				//이벤트 연결
				delete_btn.onclick=function(){
					let choice =confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('magaiznDeleteForm.do?mg_board_num=${magaizn.board_num}')
					}
				}
			</script>
			
			</c:if>
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%"><br>
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${magazin.mg_photo1}" class="detail-img">
		</div>
		<p class="align-center">
			${magazin.mg_content}
		</p><br>
		<c:if test="${!empty magazin.mg_photo2}">
			<div class="align-center">
				<img src="${pageContext.request.contextPath}/upload/${magazin.mg_photo2}" class="detail-img">
			</div>
		</c:if>
		<p>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty magazin.mem_photo}">
				<img src="${pageContext.request.contextPath}/upload/${magazin.mem_photo}"
															width="100" class="my-photo">
				</c:if>
				<c:if test="${empty magazin.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png"
												width="100" class="my-photo">
				</c:if>
			</li>
			<li>
				<h2>${magazin.mem_name}</h2>
				칼럼리스트<br>
				${magazin.mem_email}
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		
	</div>
</div>
</body>
</html>