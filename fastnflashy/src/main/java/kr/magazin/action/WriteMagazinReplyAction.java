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

public class WriteMagazinReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//전송 데이터 인코딩
			request.setCharacterEncoding("UTF-8");
			//자바빈 생성
			MagazinReplyVO reply = new MagazinReplyVO(); 
			reply.setMem_num(user_num); 	//회원번호(댓글작성자)
			reply.setMg_re_content(request.getParameter("mg_re_content"));
			reply.setMg_re_ip(request.getRemoteAddr());
			reply.setMg_board_num(Integer.parseInt(
									request.getParameter("mg_board_num")));
			
			MagazinDAO dao = MagazinDAO.getInstance();
			dao.insertReplyMagazin(reply);
			
			mapAjax.put("result", "success");
		}
		//JSON문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
