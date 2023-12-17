package kr.schedule.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class DetailScheduleAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/WEB-INF/views/match/detailSchedule.jsp";
	}

}
