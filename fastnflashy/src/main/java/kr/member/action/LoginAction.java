package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = dao.checkMember(id);
		boolean check = false;
		
		
		if(vo!=null) {
			//비밀번호 일치 여부 체크
			check = vo.isCheckedPassword(passwd);
		}
		if(check) {//인증성공
			HttpSession session = request.getSession();
			session.setAttribute("user_num", vo.getMem_num());
			session.setAttribute("user_id", vo.getMem_id());
			session.setAttribute("user_auth", vo.getMem_auth());
			session.setAttribute("user_photo", vo.getMem_photo());
			
			return "redirect:/main/main.do";
		}
		//인증실패
		System.out.println(vo.getMem_num());
		return "/WEB-INF/views/member/login.jsp";
	}

}
