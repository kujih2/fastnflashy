package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
  
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class BoardWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		BoardVO board = new BoardVO();
		board.setTitle(multi.getParameter("title"));
		board.setBoard_category(Integer.parseInt(multi.getParameter("category")));
		board.setContent(multi.getParameter("content"));
		board.setIp(request.getRemoteAddr());
		board.setFilename(multi.getFilesystemName("filename"));
		board.setMem_num(user_num);
		
		BoardDAO dao = BoardDAO.getInstance();
		
		// 사용자 아이디와 현재 날짜를 기준으로 게시글 수를 조회하는 메서드
		int postCount = dao.boardCountByUsernumInSameDay(board);
				
		// 제한된 게시글 수 설정
		int limit = 5;

		if (postCount >= limit) {
			// 게시글 수가 제한을 초과한 경우
			request.setAttribute("accessMsg", "하루 게시글 작성 한도를 초과했습니다.");
			// 에러 메시지와 함께 게시글 목록 페이지로 리디렉션 또는 포워딩
			return "/WEB-INF/views/common/notice.jsp";
			
		} else {
			// 게시글 작성 로직
			
			dao.insertBoard(board);
		}
				
		
		return "/WEB-INF/views/board/boardWrite.jsp";
	}

}
