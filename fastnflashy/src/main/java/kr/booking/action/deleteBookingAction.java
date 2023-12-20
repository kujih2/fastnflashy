package kr.booking.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.booking.dao.BookingDAO;
import kr.controller.Action;

public class deleteBookingAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int booked_num = Integer.parseInt(request.getParameter("booked_num"));
		int package_num = Integer.parseInt(request.getParameter("booked_package"));
		
		BookingDAO dao = BookingDAO.getInstance();
		dao.deleteBooking(booked_num, package_num);
		
		return "/WEB-INF/views/booking/deleteBooking.jsp";
	}

}
