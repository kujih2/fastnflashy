package kr.booking.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.booking.dao.BookingDAO;
import kr.booking.vo.BookedInfoVO;
import kr.controller.Action;
import kr.schedule.vo.ScheduleVO;

public class BookingDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int booked_num = Integer.parseInt(request.getParameter("booked_num"));
		int package_num =Integer.parseInt(request.getParameter("booked_package"));
		
		BookingDAO dao = BookingDAO.getInstance();
		List<BookedInfoVO> list = dao.getMyBookDetail1(booked_num, package_num);
		ScheduleVO schedule = dao.getMyBookDetail2(booked_num);
		
		System.out.println(list);
		request.setAttribute("list", list);
		request.setAttribute("schedule", schedule);
		
		return "/WEB-INF/views/booking/bookingDetail.jsp";
	}

}
