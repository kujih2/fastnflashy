<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>칼럼삭제</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/magazin.css">
<script type="text/javascript">
window.onload=function(){
	let myForm = document.getElementById('delete_form');
	myForm.onsubmit = function(){
		let mem_pw = document.getElementById('mem_pw');
		if(mem_pw.value.trim()==''){
			alert('비밀번호를 입력하세요');
			passwd.value='';
			passwd.focus();
			return false;
		}
	}
}
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/magazin/magazinHeader.jsp"/>
	<div class="content-main">
		<h2>[관리자] 칼럼삭제</h2>
		<form id="delete_form" action="magazinDelete.do" method="post" class="align-center">
			<input type="hidden" name="mg_board_num" value="${mg_board_num}">
			<h3>비밀번호 입력</h3>
			<ul>
				<li>
					<label for="mem_pw"></label>
					<input type="password" name="mem_pw" id="mem_pw"
							size="10" maxlength="12">
				</li>
			</ul>
				<div class="align-center">
					<input type="submit" value="삭제" id="delete_btn">
					<input type="button" value="취소"
							onclick="'magazinDetail.do?mg_board_num=${magazin.mg_board_num}'">
				</div>
		</form>
	</div>
</div>
</body>
</html>