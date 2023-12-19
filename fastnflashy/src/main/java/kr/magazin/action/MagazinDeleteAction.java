package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.FileUtil;

public class MagazinDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 안된 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자가 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";	
		}
		request.setCharacterEncoding("utf-8");
		
		int mg_board_num = Integer.parseInt(request.getParameter("mg_board_num"));
		String mem_pw = request.getParameter("mem_pw");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO db_vo = dao.checkMember((String)session.getAttribute("user_id"));
		boolean check = false;
		if(db_vo!=null) {
			check = db_vo.isCheckedPassword(mem_pw);
		}
		MagazinDAO magazinDao = MagazinDAO.getInstance();
		MagazinVO db_magazin = magazinDao.getMagazin(mg_board_num);
		if(check) {
			magazinDao.magazinDelete(mg_board_num);
		}
		request.setAttribute("check", check);
		
		//파일삭제
		FileUtil.removeFile(request, db_magazin.getMg_photo1());
		FileUtil.removeFile(request, db_magazin.getMg_photo2());
		
		return "/WEB-INF/views/magazin/magazinDelete.jsp";
	}

}
