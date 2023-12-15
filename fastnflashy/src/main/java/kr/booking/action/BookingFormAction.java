package kr.booking.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.booking.dao.BookingDAO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.schedule.vo.ScheduleVO;

public class BookingFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		
		//경기 번호 반환
		int schedule_num = Integer.parseInt(request.getParameter("schedule_num"));
		BookingDAO dao = BookingDAO.getInstance();
		ScheduleVO schedule = dao.getInfoOfMatch(schedule_num);
		MemberVO member = dao.getBalanceOfMember(user_num);
		
		request.setAttribute("schedule_num", schedule_num);
		request.setAttribute("schedule", schedule);
		request.setAttribute("member", member);

		return "/WEB-INF/views/booking/bookingForm.jsp";
	}

}
