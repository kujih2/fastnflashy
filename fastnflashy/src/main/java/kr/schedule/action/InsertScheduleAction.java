package kr.schedule.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class InsertScheduleAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자로 로그인한 경우
		request.setCharacterEncoding("utf-8");
		//자바빈(VO) 생성
		ScheduleVO schedule = new ScheduleVO();
		schedule.setTeam_category(Integer.parseInt(request.getParameter("team_category")));
		schedule.setSchedule_start(request.getParameter("schedule_start"));
		schedule.setSchedule_end(request.getParameter("schedule_end"));
		schedule.setSchedule_status(Integer.parseInt(request.getParameter("schedule_status")));
		schedule.setSchedule_team1(Integer.parseInt(request.getParameter("schedule_team1")));
		schedule.setSchedule_team2(Integer.parseInt(request.getParameter("schedule_team2")));
		
		//입력한 경기 시작일 또는 경기 종료일이 이미 경기일정에 존재할경우
		ScheduleDAO dao = ScheduleDAO.getInstance();
		//유효성 체크
		int check = dao.checkInsert(schedule);
		if(check == 0) {
		dao.insertSchedule(schedule);
		
				//Refresh 정보를 응답 헤더에 추가
				response.addHeader("Refresh", "2;url=insertScheduleForm.do");
				request.setAttribute("accessMsg", "성공적으로 등록되었습니다.");
				request.setAttribute("accessUrl", "insertScheduleForm.do");
				
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
