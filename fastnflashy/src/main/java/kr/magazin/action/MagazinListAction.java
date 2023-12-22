package kr.magazin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.util.PageUtil;

public class MagazinListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		//카테고리 버튼 처리
		
		int sports_category = 0; // 기본값
		String sc_param = request.getParameter("sports_category");
		
		if(sc_param != null) {
			try {
				sports_category = Integer.parseInt(sc_param);
			}catch(NumberFormatException e) {
				sports_category = 0;
			}
		}
		
		MagazinDAO dao = MagazinDAO.getInstance();
		int count = dao.getMagazinCount(keyfield, keyword,sports_category);
		
		//페이지처리
		PageUtil page = new PageUtil(keyfield, keyword,Integer.parseInt(pageNum),count,20,10,"magazinList.do","&sports_category="+sports_category);
		
		List<MagazinVO> list = null;
		if(count > 0) {
			list = dao.getListMagazin(page.getStartRow(), page.getEndRow(), keyfield, keyword,sports_category);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());

		return "/WEB-INF/views/magazin/magazinList.jsp";
	}

}
