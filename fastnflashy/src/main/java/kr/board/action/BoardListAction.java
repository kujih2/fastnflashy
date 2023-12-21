package kr.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
    
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class BoardListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		//카테고리 버튼 처리 PART
		
		int categoryNum = 0; // 기본값 설정
		String categoryNumParam = request.getParameter("categoryNum");
		
		if (categoryNumParam != null) {
		    try {
		        categoryNum = Integer.parseInt(categoryNumParam);
		    } catch(NumberFormatException e) {
		        // 유효하지 않은 값의 경우 기본값 사용
		        categoryNum = 0;
		    }
		}
		
		BoardDAO dao = BoardDAO.getInstance();
		int count = dao.getBoardCount(keyfield, keyword,categoryNum);
		BoardVO board = new BoardVO();
		int board_num = board.getBoard_num();
		
		//페이지 처리
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum),count,20,10,"boardList.do");
		
		List<BoardVO> list = null;
		if(count>0) {
			list = dao.getListBoard(page.getStartRow(), page.getEndRow(),keyfield,keyword,categoryNum);
			
		}
		
		int totallike = dao.totalLikeCount(board_num);
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		request.setAttribute("totallike", totallike);
		
		//request.setAttribute("categoryNum", categoryNum);
		
		return "/WEB-INF/views/board/boardList.jsp";
	}

}
