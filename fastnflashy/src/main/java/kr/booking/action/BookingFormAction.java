package kr.booking.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.booking.dao.BookingDAO;
import kr.booking.vo.BookedInfoVO;
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
		int count = dao.getNumOfBookedSeats(schedule_num);
		List<BookedInfoVO> list = dao.getInfoOfSeats(schedule_num);
		
//		String array = "";
//		array += "[";
//		for(int i=0;i<list.size();i++) {
//			if(i>0) array+=",";
//			array+= "'"+list.get(i).getSeat_col()+"'";
//		}
//		array +="]";
		
		request.setAttribute("schedule_num", schedule_num);
		request.setAttribute("schedule", schedule);
		request.setAttribute("member", member);
		request.setAttribute("list", list);
		request.setAttribute("count", count);
//		request.setAttribute("array", array);

		return "/WEB-INF/views/booking/bookingForm.jsp";
	}

}
