<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>칼럼 글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/magazin.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#magazinUpdate_form').submit(function(){
		let magazin = document.querySelectorAll('input[type="text"],textarea');
		for(let i=0;i<magazin.length;i++){
			if($('#sports_category').val() == 0){
				alert('카테고리를 선택하세요.');
				return false;
			}
			if(magazin[i].value.trim()==''){
				let label = document.querySelector('label[for="'+magazin[i].id+'"]');
				alert(label.textContent + ' 을 입력하세요');
				magazin[i].value = '';
				magazin[i].focus();
				return false
			}
			if($('#mg_photo1').val() == '' && $('#mg_photo2').val() == ''){
				alert('최소 1개이상의 이미지는 있어야합니다.');
				return false;
			}	
		}
	});
	$('.file_del').click(function(){
		let choice = confirm('삭제하시겠습니까?');
		if(choice){
			$.ajax({
				url:'deletePhoto.do',
				type:'post',
				data:{mg_board_num:${magazin.mg_board_num}},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요');
					}else if(param.result == 'success'){
						$('#file_detail').hide();
					}else if(param.result == 'wrongAccess'){
						alert('잘못된 접속입니다.');
					}else{
						alert('파일 삭제 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');	
				}
			});
		}
	});
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/magazin/magazinHeader.jsp"/>
	<div class="content-main">
		<form action="magazinUpdate.do" method="post"
		       enctype="multipart/form-data" id="magazinUpdate_form" >
		       
			<select id="sports_category" name="sports_category" size="1" class="align-left">
				<option value="0" ${magazin.sports_category == 0 ? 'selected' : '0'}>카테고리</option>
				<option value="1" ${magazin.sports_category == 1 ? 'selected' : '1'}>축구</option>
				<option value="2" ${magazin.sports_category == 2 ? 'selected' : '2'}>야구</option>
				<option value="3" ${magazin.sports_category == 3 ? 'selected' : '3'}>배구</option>
				<option value="4" ${magazin.sports_category == 4 ? 'selected' : '4'}>농구</option>
			</select>
			<ul>
				<li>
					<label for="mg_title">제목</label>
					<input type="text" name="mg_title" id="mg_title" 
							value="${magazin.mg_title}" placeholder="제목을 입력하세요" maxlength="50">
				</li>
			</ul>
			<hr size="1" noshade="noshade" width="100%">
			<ul>	
				<li>
					<label for="mg_content">내용</label>
					<textarea rows="5" cols="30" name="mg_content" id="mg_content"
								placeholder="내용을 입력하세요">${magazin.mg_title}</textarea>
				</li>
			</ul>
			<hr size="1" noshade="noshade" width="100%">
			<ul>	
				<li>
					<label for="mg_photo1">이미지1</label>
					<input type="file" name="mg_photo1"
					  id="mg_photo1" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<label for="mg_photo2">이미지2</label>
					<input type="file" name="mg_photo2"
					  id="mg_photo2" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
				<c:if test="${!empty magazin.mg_photo1}">
					<div id="file_detail">
						<input type="button" value="이미지1삭제" class="file_del">
						(${magazin.mg_photo1})파일이 등록되어 있습니다.
					</div>
					</c:if>
					<c:if test="${!empty magazin.mg_photo2}">
					<div id="file_detail">
						<input type="button" value="이미지2삭제" class="file_del">
						${magazin.mg_photo2}파일이 등록되어 있습니다.
					</div>
					</c:if>		
				</li>
			</ul>
			<hr size="1" noshade="noshade" width="100%">
			<div class="align-center">
				<input type="submit" value="수정완료">
				<input type="button" value="취소" 
				                  onclick="location.href='magazinList.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>








