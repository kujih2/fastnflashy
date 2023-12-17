package kr.schedule.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class ModifyScheduleAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ScheduleDAO dao =ScheduleDAO.getInstance();
		
		ScheduleVO vo = new ScheduleVO();
		vo.setSchedule_num(Integer.parseInt(request.getParameter("schedule_num")));
		vo.setTeam_category(Integer.parseInt(request.getParameter("team_category")));
		vo.setSchedule_start(request.getParameter("schedule_start"));
		vo.setSchedule_end(request.getParameter("schedule_end"));
		vo.setSchedule_status(Integer.parseInt(request.getParameter("schedule_status")));
		vo.setSchedule_team1(Integer.parseInt(request.getParameter("schedule_team1")));
		vo.setSchedule_team2(Integer.parseInt(request.getParameter("schedule_team2")));
		
		//입력한 경기 시작일 또는 경기 종료일이 이미 경기일정에 존재할경우
		int check = dao.checkModify(vo);
		if(check == 0) {
			dao.modifySchedule(vo);
			
					//Refresh 정보를 응답 헤더에 추가
					response.addHeader("Refresh", "2;url=schedule.do?category=9");
					request.setAttribute("accessMsg", "성공적으로 수정되었습니다.");
					request.setAttribute("accessUrl", "schedule.do?category=9");
					
					return "/WEB-INF/views/common/notice.jsp";
			}else if(check  == 1) {
				request.setAttribute("checkMsg","입력한 경기 시작일 또는 경기 종료일이 이미 경기일정에 존재합니다.");
				return"/WEB-INF/views/match/check.jsp";
			}else if(check  == 2) {
				request.setAttribute("checkMsg","선택한 종목에 맞지 않는 팀을 입력했습니다.");
				return"/WEB-INF/views/match/check.jsp";
			}else if(check  == 3) {
				request.setAttribute("checkMsg","선택하신 팀번호 중에 존재하지 않는 번호가 존재합니다.");
				return"/WEB-INF/views/match/check.jsp";
			}else {
				request.setAttribute("checkMsg","오류");
				return "/WEB-INF/views/match/check.jsp";
			}
	}
	
}
