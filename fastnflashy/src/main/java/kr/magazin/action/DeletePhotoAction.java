package kr.magazin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.util.FileUtil;

public class DeletePhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인 안된 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 2) {//칼럼회원이 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";	
		}
		
		//전송된 데이터 인코딩
		request.setCharacterEncoding("utf-8");
		
		int mg_board_num = 
			Integer.parseInt(request.getParameter("mg_board_num"));
		String mg_photo1 = request.getParameter("mg_photo1");
		String mg_photo2 = request.getParameter("mg_photo2");
		
		MagazinDAO dao = MagazinDAO.getInstance();
		MagazinVO db_magazin = dao.getMagazin(mg_board_num);
		
		//로그인 번호 작성자 번호 일치여부 체크
		if(user_num!=db_magazin.getMem_num()) {
			mapAjax.put("result", "wrongAccess");
		}else {
			dao.deletePhoto(mg_board_num,mg_photo1,mg_photo2);
			
			//파일 삭제
			FileUtil.removeFile(request, db_magazin.getMg_photo1());
			FileUtil.removeFile(request, db_magazin.getMg_photo2());
			
			mapAjax.put("result", "success");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
