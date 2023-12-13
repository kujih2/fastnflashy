package kr.schedule.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class ScheduleAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		Integer team_category=Integer.parseInt(request.getParameter("category"));
		String date = request.getParameter("date");
		
		if(date == null) {
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			date = now.format(formatter);
		}
		ScheduleDAO dao = ScheduleDAO.getInstance();
		//경기 현황 최신화
		dao.changeStatus();
		//경기 결과 삽입
		dao.insertResult();
		//경기 조회
		List<ScheduleVO> scheduleList = dao.selectSchedule(team_category,date);
		
		String category = request.getParameter("category");
		request.setAttribute("category", category);
		request.setAttribute("date", date);
		request.setAttribute("scheduleList", scheduleList);
		return "/WEB-INF/views/match/schedule.jsp";
		
	}

}
