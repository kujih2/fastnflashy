package kr.team.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.team.dao.TeamDAO;
import kr.team.vo.TeamVO;

public class TeamRankAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int category = Integer.parseInt(request.getParameter("team_category"));
		
		TeamDAO dao = TeamDAO.getInstance();
		dao.updateOdds2();
		//match_team 전체 조회
		List<TeamVO> list = dao.selectRank(category);
		request.setAttribute("rankList", list);
	
		
		return "/WEB-INF/views/match/teamRank.jsp";
	}

}