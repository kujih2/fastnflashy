package kr.member.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class RegisterUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		MemberVO vo = new MemberVO();
		vo.setMem_auth(Integer.parseInt(request.getParameter("auth")));
		vo.setMem_id(request.getParameter("id"));
		vo.setMem_name(request.getParameter("name"));
		vo.setMem_gender(Integer.parseInt(request.getParameter("gender")));
		vo.setMem_pw(request.getParameter("passwd"));
		vo.setMem_tel(request.getParameter("phone"));
		vo.setMem_email(request.getParameter("email"));
		vo.setMem_zipcode(request.getParameter("zipcode"));
		vo.setMem_address1(request.getParameter("address1"));
		vo.setMem_address2(request.getParameter("address2"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.insertMember(vo);
		
		return "/WEB-INF/views/member/registerUser.jsp";
	}

}
