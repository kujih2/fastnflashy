package kr.team.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.team.dao.TeamDAO;
import kr.team.vo.TeamVO;

public class TeamListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int category = Integer.parseInt(request.getParameter("team_category"));
		TeamDAO dao = TeamDAO.getInstance();
		List<TeamVO> list = dao.selectTeams(category);
		
		request.setAttribute("teamList", list);
		return "/WEB-INF/views/match/teamList.jsp";
	}

}
