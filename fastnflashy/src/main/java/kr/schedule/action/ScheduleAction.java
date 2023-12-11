package kr.schedule.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;

public class ScheduleAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int team_category=Integer.parseInt(request.getParameter("category"));
		ScheduleDAO dao = ScheduleDAO.getInstance();
		dao.selectSchedule(team_category);
		
		return "/WEB-INF/views/match/schedule.jsp";
		
	}

}
