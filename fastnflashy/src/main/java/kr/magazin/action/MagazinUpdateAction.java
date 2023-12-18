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
		if(user_num == null) {//로그인 안된 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 2) {//칼럼회원이 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";	
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int mg_board_num = Integer.parseInt(multi.getParameter("mg_board_num"));
		int sports_category = Integer.parseInt(multi.getParameter("sports_category"));
		String mg_photo1 = multi.getFilesystemName("mg_photo1");
		String mg_photo2 = multi.getFilesystemName("mg_photo2");
		
		MagazinDAO dao = MagazinDAO.getInstance();
		//수정전 데이터 반환
		MagazinVO db_magazin = dao.getMagazin(mg_board_num);
		if(user_num != db_magazin.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, mg_photo1);
			FileUtil.removeFile(request, mg_photo2);
			return "/WEB-INF/views/common/notice.jsp";
	}
		
		//로그인한 회원번호와 작성자 회원번호 일치
		MagazinVO magazin = new MagazinVO();
		magazin.setMg_board_num(mg_board_num);
		magazin.setSports_category(sports_category);
		magazin.setMg_title(multi.getParameter("mg_title"));
		magazin.setMg_content(multi.getParameter("mg_content"));
		magazin.setMg_ip(request.getRemoteAddr());
		magazin.setMg_photo1(mg_photo1);
		magazin.setMg_photo2(mg_photo2);
		
		//글 수정
		dao.updateMagazin(magazin);
		
		if(mg_photo1!=null) {//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_magazin.getMg_photo1());
		}
		if(mg_photo2!=null) {//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_magazin.getMg_photo2());
		}
		
		return "redirect:/magazin/magazinDetail.do?mg_board_num=" + mg_board_num;
	}

}
