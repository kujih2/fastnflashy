package kr.main.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.booking.dao.BookingDAO;
import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//match
		int team_category = 9;
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = now.format(formatter);
		
		ScheduleDAO dao = ScheduleDAO.getInstance();
		//경기 현황 최신화
		 	dao.changeStatus();
		//경기 결과 삽입
			dao.insertResult();
		//오늘의 모든 경기 조회
		 List<ScheduleVO> scheduleList = dao.selectSchedule(team_category,date);
		 request.setAttribute("scheduleList", scheduleList);
		 
		
		//booking
		BookingDAO dao2 = BookingDAO.getInstance();
		List<ScheduleVO> list = dao2.getListOfRegistedMatch();
			
		request.setAttribute("list",list);
		
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}

}





