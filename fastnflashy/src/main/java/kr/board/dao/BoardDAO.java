package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.board.vo.BoardVO;
import kr.util.DBUtil;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private BoardDAO() {}
	
	//글등록
	public void insertBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO board (board_num,title,board_category,content,"
					             + "isdeleted,filename,ip,mem_num) VALUES"
					             + "(board_seq.nextval,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setInt(2, board.getBoard_category());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getIsdeleted());
			pstmt.setString(5, board.getFilename());
			pstmt.setString(6, board.getIp());
			pstmt.setInt(7,board.getMem_num());
			
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		
	}

	//전체 레코드수/검색 레코드 수
	public int getBoardCount(String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	//글 상세
	//조회수 증가
	//파일 삭제
	//글 수정
	//글 삭제
	//좋아요 등록
	//좋아요 개수
	//회원번호와 게시물 번호를 이용한 정보(좋ㅇ요 선택 여부)
	//좋아요 삭제
	//내가 선택한 좋아요 목록ㄱ
	//댓글 등록
	//댓글 개수
	//댓글 목록
	//댓글 상세(댓글 수정, 삭제 시 작성자 회원번호 체크 용도로 사용(
	//댓글 수정
	//댓글 삭제
	
}
