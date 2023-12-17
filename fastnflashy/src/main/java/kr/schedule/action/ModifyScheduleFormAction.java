package kr.schedule.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class ModifyScheduleFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int schedule_num = Integer.parseInt(request.getParameter("num")); 
		
		ScheduleDAO dao = ScheduleDAO.getInstance();
		ScheduleVO vo = new ScheduleVO();
		//수정할 정보 불러오기
		vo = dao.modifySelect(schedule_num);
		
		//경기가 있는 날짜만 가져오기
		List<ScheduleVO> dateList = dao.ableDate();
		
		request.setAttribute("vo", vo);
		request.setAttribute("schedule_num", schedule_num);
		request.setAttribute("ableDateList", dateList);
		
		return "/WEB-INF/views/match/modifyScheduleForm.jsp";
	}

}
