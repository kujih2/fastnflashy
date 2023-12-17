package kr.schedule.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;

public class DeleteScheduleAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		//로그인이 되지 않았거나 관리자로 로그인이 아닌 경우
		if(user_num == null || user_auth != 9) {
			return "redirect:/member/loginForm.do";
		}
		int schedule_num = Integer.parseInt(request.getParameter("schedule_num"));
		ScheduleDAO dao = ScheduleDAO.getInstance();
		dao.deleteSchedule(schedule_num);
		
		//Refresh 정보를 응답 헤더에 추가
		response.addHeader("Refresh", "2;url=schedule.do?category=9");
		request.setAttribute("accessMsg", "성공적으로 삭제되었습니다.");
		request.setAttribute("accessUrl", "schedule.do?category=9");
		
		return "/WEB-INF/views/common/notice.jsp";
		
	}
	
}
