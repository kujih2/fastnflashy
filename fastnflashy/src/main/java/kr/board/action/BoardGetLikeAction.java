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

public class BoardGetLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 전송된 데이터 반환
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		BoardDAO dao = BoardDAO.getInstance();
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			
			BoardLikeVO likeVO = new BoardLikeVO();
			likeVO.setBoard_num(board_num);
			likeVO.setMem_num(user_num);
			
			BoardLikeVO db_like = dao.selectLike(likeVO);
					
			if(db_like != null) {//board_like 테이블에 데이터 존재
				if(db_like.getLike_status()==1) {//데이터 상에 이미 좋아요가 눌러져 있는 경우
					mapAjax.put("status", "Liked");
				}else {//데이터 상에 이미 싫어요 눌러져 있는 경우
					mapAjax.put("status", "Disliked");
				}	
			}else {//좋아요 싫어요 아무것도 안눌러져 있는 상황
				mapAjax.put("status", "noLike");
				
			}	
			
		}
		mapAjax.put("likecount", dao.selectLikeCount(board_num));
		mapAjax.put("dislikecount", dao.selectDislikeCount(board_num));
		
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
