package kr.magazin.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinReplyVO;
import kr.util.PageUtil;

public class MagazinListReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		String rowCount = request.getParameter("rowCount");
		if(rowCount == null) {
			rowCount = "10";
		}
		
		int mg_board_num = Integer.parseInt(
							request.getParameter("mg_board_num"));
		
		MagazinDAO dao = MagazinDAO.getInstance();
		int count = dao.getReplyMagazinCount(mg_board_num);
		
		//ajax 방식으로 목록을 표시하기 때문에 PageUtil은 페이지수 표시가 목적이
		//아니라 목록 데이터의 페이지 처리를 위해 rownum 번호를 구하는 것이 목적
		//currentPage,count,rowCount
		PageUtil page = new PageUtil(Integer.parseInt(pageNum),count,
									 Integer.parseInt(rowCount));
		
		List<MagazinReplyVO> list = null;
		if(count > 0) {
			list = dao.getListReplyMagazin(page.getStartRow(),
										   page.getEndRow(),
										   mg_board_num);
		}else {
			list = Collections.emptyList();
		}
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		//로그인한 사람이 작성자인지 체크위해 전송
		mapAjax.put("user_num", user_num);
		
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
	
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}