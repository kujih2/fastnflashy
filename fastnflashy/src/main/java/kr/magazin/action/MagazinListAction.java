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
			list = dao.getListMagazin(page.getStartRow(), page.getEndRow(), keyfield, keyword,sports_category,0);
		}
		
		// 헤드라인 목록 조회
		List<MagazinVO> headlineList = null;
		if (count > 0) {
		    if (Math.random() > 0.5) {
		        // 랜덤하게 두 가지 방법 중 하나 선택 (예: 50% 확률로 랜덤 목록 표시)
		        headlineList = dao.getRandomHeadlineList(1);
		    } else {
		        headlineList = dao.getListMagazin(1, 1, keyfield, keyword, sports_category, 1);
		    }
		}
		
		//조회수많은 칼럼 조회
		PageUtil pageHit = new PageUtil(Integer.parseInt(pageNum),count,4,10,"magazinList.do");
		
		List<MagazinVO> getMostHitList = null;
		if(count > 0) {
			getMostHitList = dao.getMostHit(pageHit.getStartRow(),pageHit.getEndRow());
		}
	    
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("headlineList", headlineList);
		request.setAttribute("getMostHitList", getMostHitList);
		request.setAttribute("page", page.getPage());

		return "/WEB-INF/views/magazin/magazinList.jsp";
	}

}
