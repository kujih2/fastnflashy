package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardLikeVO;
import kr.controller.Action;

public class BoardDetermineLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			int like_status = Integer.parseInt(request.getParameter("like_status")); 
			//직금 위에서 user_num,board_num,like_status 는 브라우저 상에서 http request 할때 받아온값들
			
			System.out.println("Received AJAX request - board_num: " + board_num + ", like_status: " + like_status);

			
			
			BoardLikeVO likeVO = new BoardLikeVO();
			likeVO.setBoard_num(board_num);
			likeVO.setMem_num(user_num);
			likeVO.setLike_status(like_status);

			
			BoardDAO dao = BoardDAO.getInstance();
			//좋아요 혹은 싫어요를 눌러서 board_like 테이블에 데이터가 들어가있는지 체크
			BoardLikeVO db_like = dao.selectLike(likeVO);//sql developer에 있는 데이터 읽어오는 것
			
			if(db_like != null) {//좋아요나 싫어요가 눌러져있는 상황
				if(db_like.getLike_status() == 1) {//좋아요가 눌러져있다는 뜻
					
					//좋아요 눌러져있는데 좋아요 또 누른 경우
					if(likeVO.getLike_status()==1) {
					// 이거는 delete 해야함
					dao.deleteLike(likeVO);
					mapAjax.put("status", "CancelLike");
					}else {
					//좋아요 눌러져있는데 싫어요 누른 경우
					dao.toggleLike(likeVO);
					mapAjax.put("status", "Disliked");
					}
				}else {//싫어요가 눌러져있다는 뜻
					//싫어요 눌러져있는데 싫어요 또 누른 경우
					if(likeVO.getLike_status()==2) {
					//delete 해야함
					dao.deleteLike(likeVO);
					mapAjax.put("status", "CancelDislike");
					}else {
					//싫어요 눌러져있는데 좋아요 누른 경우
					dao.toggleLike(likeVO);
					mapAjax.put("status", "Liked");
					}
				}
		
			}else {//좋아요 싫어요 아무것도 안눌러져있다는 상황
				//board_like 테이블에  일단 데이터 집어넣고 like_status 업데이트
				dao.insertLike(likeVO);
				if(likeVO.getLike_status() == 1) {//브라우저 상에서 좋아요 버튼을 눌렀다는뜻
					dao.updateLike(likeVO);
					mapAjax.put("status", "Liked");
				}else {//브라우저 상에서 싫어요 버튼을 누른것
					dao.updateLike(likeVO);
					mapAjax.put("status", "Disliked");
				}
				
							
			}
			
			mapAjax.put("result", "success");
			mapAjax.put("board_num", board_num); // board_num 설정
			mapAjax.put("like_status", like_status); // like_status 설정
			mapAjax.put("likecount", dao.selectLikeCount(board_num));
			mapAjax.put("dislikecount", dao.selectDislikeCount(board_num));
		}
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp"; 

}

}
