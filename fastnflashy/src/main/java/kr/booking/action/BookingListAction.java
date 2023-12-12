package kr.booking.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.booking.dao.BookingDAO;
import kr.controller.Action;
import kr.schedule.vo.ScheduleVO;

public class BookingListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookingDAO dao = BookingDAO.getInstance();
		List<ScheduleVO> list = dao.getListOfRegistedMatch();
		
		request.setAttribute("list",list);
		
		return "/WEB-INF/views/booking/list.jsp";
	}


}
