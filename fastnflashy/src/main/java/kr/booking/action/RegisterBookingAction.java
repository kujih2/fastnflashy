package kr.booking.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.booking.dao.BookingDAO;
import kr.controller.Action;

public class RegisterBookingAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		BookingDAO dao = BookingDAO.getInstance();
		dao.registerSaleOfMatch(Integer.parseInt(request.getParameter("schedule_num")));
		
		return "/WEB-INF/views/booking/registerBooking.jsp";
	}

}


