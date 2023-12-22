package kr.booking.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.booking.dao.BookingDAO;
import kr.booking.vo.BookedInfoVO;
import kr.controller.Action;
import kr.schedule.vo.ScheduleVO;
import kr.util.PageUtil;

public class ListOfMyBookingAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		BookingDAO dao = BookingDAO.getInstance();
		int count = dao.getBookingCount(user_num);
		//페이지처리
		PageUtil page = new PageUtil(Integer.parseInt(pageNum),count,10,10,"listOfMyBooking.do");
		List<BookedInfoVO> list1 = dao.getMyBookList1(user_num,page.getStartRow(),page.getEndRow());
		List<ScheduleVO> list2 = dao.getMyBookList2(user_num,page.getStartRow(),page.getEndRow());
		
		
		request.setAttribute("list1",list1);
		request.setAttribute("list2",list2);
		request.setAttribute("page", page.getPage());
		
		
		return "/WEB-INF/views/booking/listOfMyBooking.jsp";
	}

}
