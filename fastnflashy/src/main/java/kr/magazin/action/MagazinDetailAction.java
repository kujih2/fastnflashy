package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.util.StringUtil;

public class MagazinDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int mg_board_num = Integer.parseInt(request.getParameter("mg_board_num"));
		
		MagazinDAO dao = MagazinDAO.getInstance();
		//죄회수 증가
		dao.updateReadcount(mg_board_num);
		
		MagazinVO magazin = dao.getMagazin(mg_board_num);
		
		magazin.setMg_ip(StringUtil.useNoHtml(magazin.getMg_title()));
		magazin.setMg_content(StringUtil.useBrNoHtml(magazin.getMg_content()));
		
		request.setAttribute("magazin", magazin);
		
		return "/WEB-INF/views/magazin/magazinDetail.jsp";
	}

}

//magazin.setMg_content(StringUtil.shortWords(10, magazin.getMg_content()));