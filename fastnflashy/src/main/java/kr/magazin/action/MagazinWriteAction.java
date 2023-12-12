package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.util.FileUtil;

public class MagazinWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//비로그인
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 2) {//칼럼회원이 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";	
		}
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		MagazinVO magazin = new MagazinVO();
		magazin.setMg_title(multi.getParameter("mg_title"));
		magazin.setSports_category(Integer.parseInt(multi.getParameter("sports_category")));
		magazin.setMg_content(multi.getParameter("mg_content"));
		magazin.setMg_ip(request.getRemoteAddr());
		magazin.setMg_photo1(multi.getFilesystemName("mg_photo1"));
		magazin.setMg_photo2(multi.getFilesystemName("mg_photo2"));
		magazin.setMem_num(user_num);
		
		MagazinDAO dao = MagazinDAO.getInstance();
		dao.insertMagazin(magazin);
		
		return "/WEB-INF/views/magazin/magazinWrite.jsp";
	}

}
