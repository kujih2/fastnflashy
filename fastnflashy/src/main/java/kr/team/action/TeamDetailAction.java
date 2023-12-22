package kr.team.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.schedule.vo.ScheduleVO;
import kr.team.dao.TeamDAO;
import kr.team.vo.TeamVO;


public class TeamDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int team_num = Integer.parseInt(request.getParameter("team_num"));
		int team_category = Integer.parseInt(request.getParameter("team_category"));
		TeamDAO dao = TeamDAO.getInstance();
		TeamVO vo = new TeamVO();
		List<ScheduleVO> resultList = new ArrayList<ScheduleVO>();
		List<ScheduleVO> scheduledList = new ArrayList<ScheduleVO>();
		List<TeamVO> playerList = new ArrayList<TeamVO>();
		//팀 상세 정보
		vo = dao.selectTeamDetail(team_num,team_category);
		//팀 최근 전적
		resultList = dao.resultTeam(team_num);
		//팀 예정경기 
		scheduledList = dao.selectScheduledGame(team_num);
		//팀의 소속 선수명단
		playerList = dao.selectPlayer(team_category);
		
		request.setAttribute("teamDetail", vo);
		request.setAttribute("teamResult", resultList);
		request.setAttribute("scheduledGame", scheduledList);
		request.setAttribute("playerList", playerList);
		
		return"/WEB-INF/views/match/teamDetail.jsp";
	}

}
