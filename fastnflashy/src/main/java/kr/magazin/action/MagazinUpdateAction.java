package kr.magazin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.util.FileUtil;

public class MagazinUpdateAction implements Action{

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
		
		MultipartRequest multi = FileUtil.createFile(request);
		int mg_board_num = Integer.parseInt(multi.getParameter("mg_board_num"));
		String photo1 = multi.getFilesystemName("photo1");
		String photo2 = multi.getFilesystemName("photo2");
		
		MagazinDAO dao = MagazinDAO.getInstance();
		//수정전 데이터 반환
		MagazinVO db_magazin = dao.getMagazin(mg_board_num);
		if(user_num != db_magazin.getMem_num()) {
			//번호 불일치
			FileUtil.removeFile(request, photo1);
			FileUtil.removeFile(request, photo2);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호 일치
		MagazinVO magazin = new MagazinVO();
		magazin.setMg_board_num(mg_board_num);
		magazin.setMg_title(multi.getParameter("title"));
		magazin.setMg_content(multi.getParameter("content"));
		magazin.setMg_photo1(photo1);
		magazin.setMg_photo2(photo2);
		
		//글 수정
		dao.updateMagazin(magazin);
		
		if(photo1!=null) {
			FileUtil.removeFile(request, db_magazin.getMg_photo1());
		}
		if(photo2!=null) {
			FileUtil.removeFile(request, db_magazin.getMg_photo2());
		}
		
		return "redirect:/magazin/magazinDetail.do?mg_board_num=" + mg_board_num;
	}

}
