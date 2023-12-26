package kr.magazin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinFavVO;

public class MagazinWriteFavAction implements Action{

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
			int mg_board_num = Integer.parseInt(
					      request.getParameter("mg_board_num"));
			MagazinFavVO favVO = new MagazinFavVO();
			favVO.setMg_board_num(mg_board_num);
			favVO.setMem_num(user_num);
			
			MagazinDAO dao = MagazinDAO.getInstance();
			//좋아요 등록 여부 체크
			MagazinFavVO db_fav = dao.magazinSelectFav(favVO);
			
			if(db_fav!=null) {//좋아요 등록 O
				//좋아요 삭제
				dao.magazindeleteFav(db_fav);
				mapAjax.put("status", "noFav");
			}else {//좋아요 등록 X
				//좋아요 등록
				dao.magazininsertFav(favVO);
				mapAjax.put("status", "yesFav");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectFavCount(mg_board_num));
		}
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
	







