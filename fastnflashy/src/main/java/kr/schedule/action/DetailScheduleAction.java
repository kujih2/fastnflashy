package kr.schedule.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class DetailScheduleAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int schedule_num = Integer.parseInt(request.getParameter("num"));
		ScheduleDAO dao = ScheduleDAO.getInstance();
		ScheduleVO vo = new ScheduleVO();
		//경기상세조회
		vo = dao.selectDetail(schedule_num);
		request.setAttribute("detailSchedule", vo);
		
		//최근 전적
		List<ScheduleVO> recentResultList = dao.recentResultSchedule(schedule_num);
		request.setAttribute("recentResultList", recentResultList);
		
		return "/WEB-INF/views/match/detailSchedule.jsp";
	}

}
