package kr.booking.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.booking.dao.BookingDAO;
import kr.controller.Action;
import kr.schedule.vo.ScheduleVO;

public class LoadListMatchAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		BookingDAO dao = BookingDAO.getInstance();
		List<ScheduleVO> list = dao.getListMatch();
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("list", list);
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData",ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
