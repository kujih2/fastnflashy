package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class BoardDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO db_board = dao.getBoard(board_num);
		if(user_num != db_board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
        int userEnteredCode = Integer.parseInt(request.getParameter("confirmation_code"));
        int generatedCode = (Integer)request.getSession().getAttribute("randomNumber");

        if (userEnteredCode == generatedCode) {
            // 난수가 일치하면 삭제 로직 수행
            dao.deleteBoard(board_num);
            response.sendRedirect("boardList.jsp"); //게시물 목록 페이지로 리디렉트
        } else {
            // 난수가 일치하지 않으면 에러 메시지와 함께 다시 시도하도록 안내
            request.setAttribute("notice_msg", "코드가 일치하지 않습니다.");
            request.setAttribute("notice_url", request.getContextPath()+"/board/boardDetail.do?board_num="+board_num);
       
            
        }
		 
        return "/WEB-INF/views/common/alert_singleView.jsp";


        
        /*
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteBoard(board_num);
		//파일 삭제
		FileUtil.removeFile(request, db_board.getFilename());
		
		return "redirect:/board/boardList.do";   */
	}

}
