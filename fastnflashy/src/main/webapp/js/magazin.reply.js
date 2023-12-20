$(function(){
	let rowCount = 10;
	let currentPage;
	let count;
	
	//댓글목록
	
	//페이지 처리 이벤트 연결(다음 댓글보기 버튼클릭 데이터 추가)
	
	//댓글 등록
	$('#re_form').submit(function(event){
		if($('#mg_re_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#mg_re_content').val('').focus();
			return false;
		}
		
		//form 이하의 태그에 입력한 데이터 모두 읽어옴
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeMagazinReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성 성공시 새로 삽입한 글 포함해 첫번째 페이지 게시글 재 호출
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	//textarea에 내용 입력시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){//300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{//300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id') == 'mg_re_content'){
				//등록된 글자수
				$('#re_first .letter-count').text(remain);
			}else{
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
});