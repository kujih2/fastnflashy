package kr.booking.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.booking.dao.BookingDAO;
import kr.booking.vo.BookedInfoVO;
import kr.controller.Action;
import kr.schedule.vo.ScheduleVO;

public class ListOfMyBookingAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		BookingDAO dao = BookingDAO.getInstance();
		List<BookedInfoVO> list1 = dao.getMyBookList1(user_num);
		List<ScheduleVO> list2 = dao.getMyBookList2(user_num);
		
		request.setAttribute("list1",list1);
		request.setAttribute("list2",list2);
		
		
		return "/WEB-INF/views/booking/listOfMyBooking.jsp";
	}

}
