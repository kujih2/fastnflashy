package kr.main.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.booking.dao.BookingDAO;
import kr.controller.Action;
import kr.magazin.dao.MagazinDAO;
import kr.magazin.vo.MagazinVO;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//match
		int team_category = 9;
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = now.format(formatter);
		
		ScheduleDAO dao = ScheduleDAO.getInstance();
		//경기 현황 최신화
		 	dao.changeStatus();
		//경기 결과 삽입
			dao.insertResult();
		//오늘의 모든 경기 조회
		 List<ScheduleVO> scheduleList = dao.selectSchedule(team_category,date);
		 request.setAttribute("scheduleList", scheduleList);
		 
		
		//booking
		BookingDAO dao2 = BookingDAO.getInstance();
		List<ScheduleVO> list = dao2.getListOfRegistedMatch();
			
		request.setAttribute("list",list);
		
		
		//Board
		BoardDAO dao3 = BoardDAO.getInstance();
		List<BoardVO> list2 = dao3.getMostLikedListBoard(1, 7);
		
		request.setAttribute("list2", list2);
		
		//Magazin
		MagazinDAO dao4 = MagazinDAO.getInstance();
		List<MagazinVO> list3 = dao4.getMostHit(1, 3);
		
		List<MagazinVO> list4 = dao4.getRandomHeadlineList(1);
		
		List<MagazinVO> list5 = dao4.getNewListMagazin(1, 4);
				
		request.setAttribute("list3", list3);
		request.setAttribute("list4", list4);
		request.setAttribute("list5", list5);
		
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}

}





