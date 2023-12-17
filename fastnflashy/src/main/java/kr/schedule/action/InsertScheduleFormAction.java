package kr.schedule.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class InsertScheduleFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		ScheduleDAO dao = ScheduleDAO.getInstance();
		//경기가 있는 날짜만 가져오기
		List<ScheduleVO> dateList = dao.ableDate();
		request.setAttribute("ableDateList", dateList);
		return "/WEB-INF/views/match/insertScheduleForm.jsp";
	}

}
