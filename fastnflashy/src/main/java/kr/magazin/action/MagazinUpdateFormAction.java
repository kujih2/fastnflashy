package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.util.StringUtil;

public class MagazinUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인 안된 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 2) {//칼럼회원이 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";	
		}
		//로그인한 회원번호 작성자 번호 체크
		int mg_board_num = Integer.parseInt(request.getParameter("mg_board_num"));
		
		MagazinDAO dao = MagazinDAO.getInstance();
		MagazinVO db_magazin = dao.getMagazin(mg_board_num);
		
		if(user_num != db_magazin.getMem_num()) {
			//번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		db_magazin.setMg_title(StringUtil.parseQuot(db_magazin.getMg_title()));
		
		request.setAttribute("magazin", db_magazin);
		
		return "/WEB-INF/views/magazin/magazinUpdateForm.jsp";
	}

}
