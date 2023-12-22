package kr.magazin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinReplyVO;

public class MagaiznDeleteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩ㅁ
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int mg_re_num = Integer.parseInt(request.getParameter("mg_re_num"));
		
		Map<String,String> mapAjax = new HashMap<String, String>();
		MagazinDAO dao = MagazinDAO.getInstance();
		MagazinReplyVO db_reply = dao.getReplyMagazin(mg_re_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num != null && user_num == db_reply.getMem_num()) {
			//로그인이 되어있고 로그인한 회원번호와 작성자 회원번호 일치
			dao.magazinDeleteReply(mg_re_num);
			
			mapAjax.put("result", "success");
		}else {//로그인한 회원번호 작성자 회원번호 불일치
			mapAjax.put("result", "wrongAccess");
		}
		//JSON 문자열 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
