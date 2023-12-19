package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class MagazinDeleteFormAction implements Action{

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
		int mg_board_num = Integer.parseInt(request.getParameter("mg_board_num"));
		
		request.setAttribute("mg_board_num", mg_board_num);
		
		return "/WEB-INF/views/magazin/magazinDeleteForm.jsp";
	}

}
