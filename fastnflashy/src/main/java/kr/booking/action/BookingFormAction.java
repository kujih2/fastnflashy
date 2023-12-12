package kr.booking.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.booking.dao.BookingDAO;
import kr.controller.Action;
import kr.schedule.vo.ScheduleVO;

public class BookingFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//경기 번호 반환
		int schedule_num = Integer.parseInt(request.getParameter("schedule_num"));
		BookingDAO dao = BookingDAO.getInstance();
		ScheduleVO schedule = dao.getInfoOfMatch(schedule_num);
		request.setAttribute("schedule", schedule);

		return "/WEB-INF/views/booking/bookingForm.jsp";
	}

}
