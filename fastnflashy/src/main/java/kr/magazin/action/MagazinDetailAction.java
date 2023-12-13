package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;

public class MagazinDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int mg_board_num = Integer.parseInt(request.getParameter("mg_board_num"));
		
		MagazinDAO dao = MagazinDAO.getInstance();

		//죄회수 증가 나중에 추가
		//->
		
		return null;
	}

}
