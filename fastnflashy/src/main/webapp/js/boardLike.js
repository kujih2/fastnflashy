$(function(){
	//게시물 boardDetail 들어갔을 때 "초기설정" 좋아요 싫어요 설정 불러오기
	function selectLike(){
		$.ajax({
			url:'getLike.do',
			type:'post',
			data:{
				board_num:$('.likeicon').attr('data-num')
			},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){ 
					$('#likecount').text(param.likecount);
					$('#dislikecount').text(param.dislikecount);
				}else {
					var likebutton =  '.likeicon[data-num="' + param.board_num + '"][data-likestatus="1"]';
					var dislikebutton = '.likeicon[data-num="' + param.board_num + '"][data-likestatus="2"]';
					if(param.status=='Liked'){//초기값 좋아요 선택되어 있는것
					  $(likebutton).attr('src', '../images/liked.png');
					}else if(param.status == 'Disliked'){//초기값 싫어요 선택 되어 있는것
					  $(dislikebutton).attr('src', '../images/disliked.png');
					}else if(param.status == 'noLike'){//초기값 좋아요 싫어요 아무것도 선택되어 있지않은것
						$(likebutton).attr('src', '../images/like.png');
						$(dislikebutton).attr('src', '../images/dislike.png');
					}
					
					
				}
				displayLike(param)
				
			},
			error:function(){
				alert('네트워크 오류 발생1');
			}
			
	});
}	
	//좋아요(싫어요) 등록(및 삭제) 이벤트 연결
	$('.likeicon').click(function(){
		//콘솔에서 데이터 확인
		console.log('board_num:', $(this).attr('data-num'));
		console.log('like_status:', $(this).attr('data-likestatus'));
		
			
		$.ajax({
			url:'determineLike.do',
			type:'post',
			data: {
  				  board_num: $(this).attr('data-num'),
   				  like_status:  $(this).attr('data-likestatus')
				  },
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 좋아요를 눌러주세요');
				}else if(param.result == 'success'){
					//좋아요 싫어요 이미지 처리
					displayLike(param);
					//이미지 변경처리
					//changeLikeImage(param);
				}else{
					alert('좋아요 등록 오류 발생2')
				}
			},
			error:function(){
				alert('네트워크 오류 발생3');
			}
		});
	});
	//좋아요 싫어요 이미지 처리
	function displayLike(param){
		console.log('displayLike - param:', param);
		let output,output2;
		if(param.status=='Liked'){//좋아요 선택
			output = '../images/liked.png';
			output2 = '../images/dislike.png';
		}else if(param.status == 'Disliked'){//싫어요 선택
			output = '../images/like.png';
			output2 = '../images/disliked.png';
		}else if(param.status == 'CancelLike'){
			output = '../images/like.png';
			output2 - '../images/dislike.png';
		}else if(param.status == 'CancelDislike'){
			output = '../images/like.png';
			output2 = '../images/dislike.png';
		}
		//문서 객체에 설정
		$('#fav_like').attr('src', output);
		$('#fav_dislike').attr('src', output2);
		$('#likecount').text(param.likecount);
		$('#dislikecount').text(param.dislikecount);
	}
	
    // 이미지 변경 처리
 /*   function changeLikeImage(param) {
	console.log('changeLikeImage - param:', param);
        var targetSelector = '.likeicon[data-num="' + param.board_num + '"][data-likestatus="' + param.like_status + '"]';
        var otherSelector = '.likeicon[data-num="' + param.board_num + '"][data-likestatus="' + (param.like_status === '1' ? '2' : '1') + '"]';
		console.log(typeof param.like_status)
        // 클릭한 버튼의 이미지 변경
        if (param.status=='CancelLike') {
            $(targetSelector).attr('src', '../images/like.png');
        } else if(param.status=='LikeChange'){
            $(targetSelector).attr('src', '../images/disliked.png');
			$(otherSelector).attr('src', '../images/like.png');
        }else if(param.status=='CancelDislike'){
			$(targetSelector).attr('src', '../images/dislike.png');
		}else if(param.status=='DislikeChange'){
			$(targetSelector).attr('src', '../images/dislike.png');
			$(otherSelector).attr('src', '../images/liked.png');
		}
	console.log('Image source changed:', targetSelector);

       
    }
*/
	//초기 데이터 호출
	selectLike();
});





