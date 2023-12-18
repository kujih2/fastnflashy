package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardLikeVO;
import kr.board.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private BoardDAO() {}
	
	/*
	 * 게시글 파트
	 */
	
	//글등록
	public void insertBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO board (board_num,title,board_category,content,"
					             + "filename,ip,mem_num) VALUES"
					             + "(board_seq.nextval,?,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, board.getTitle());
			pstmt.setInt(2, board.getBoard_category());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFilename());
			pstmt.setString(5, board.getIp());
			pstmt.setInt(6,board.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
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
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE mem_id LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM board JOIN member USING(mem_num) " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%"+keyword+"%");
			}
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	//전체 글/검색 글 목록
	public List<BoardVO> getListBoard(int start,int end,String keyfield,String keyword)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE ? ";
				else if(keyfield.equals("2")) sub_sql += "WHERE mem_id LIKE ? ";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE ? ";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM board JOIN member USING(mem_num) " 
					+ sub_sql + " ORDER BY board_num DESC)a) WHERE rnum >= ? AND rnum <= ?" ;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				//HTML을 허용하지 않음
				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setMem_id(rs.getString("mem_id"));
				board.setBoard_category(rs.getInt("board_category"));
				
				
				list.add(board);
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return list;
	}
	
	//글 상세
	public BoardVO getBoard(int board_num)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO board = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM board JOIN member USING(mem_num)"
					+ " LEFT OUTER JOIN member_detail USING(mem_num)"
					+ " WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, board_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setModify_date(rs.getDate("modify_date"));
				board.setFilename(rs.getString("filename"));
				board.setMem_num(rs.getInt("mem_num"));
				board.setMem_id(rs.getString("mem_id"));
				board.setPhoto(rs.getString("mem_photo"));
				   
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
				
		return board;
	}
	//조회수 증가
	public void updateReadCount(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE board SET hit=hit+1 WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, board_num);
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	 //파일 삭제 
		public void deleteFile(int board_num)throws Exception{ 
			Connection conn = null; 
			PreparedStatement pstmt = null; String sql = null;
	 
			try {
				// 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				// SQL문 작성
				sql = "UPDATE board SET filename='' WHERE board_num=?";
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setInt(1, board_num);
				// SQL문 실행
				pstmt.executeUpdate();
	
			 }catch(Exception e) { 
				 throw new Exception(e); 
			 } finally {
			 DBUtil.executeClose(null, pstmt, conn); }
			} 

		// 글 수정
		public void	 updateBoard(BoardVO board)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
		 
			try {
				// 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();

				if (board.getFilename() != null) {
					sub_sql += ",filename=?";
				}
				// SQL문 작성
				sql = "UPDATE board SET title=?,content=?,modify_date=SYSDATE,ip=?" + sub_sql + " WHERE board_num=?";
				// PreparedStatement 객체 설정
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setString(++cnt, board.getTitle());
				pstmt.setString(++cnt, board.getContent());
				pstmt.setString(++cnt, board.getIp());
				if (board.getFilename() != null) {
					pstmt.setString(++cnt, board.getFilename());
				}
				pstmt.setInt(++cnt, board.getBoard_num());
				//SQL문 실행
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}

		//글 삭제 
		public void deleteBoard(int board_num) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt3 = null;
			String sql = null;

			try {
				// 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				// 오토커밋 해제
				conn.setAutoCommit(false);

				// 좋아요삭제
				sql="DELETE FROM board_like WHERE board_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, board_num);
				pstmt.executeUpdate();
				// 댓글 삭제
				
				// 부모글 삭제
				sql = "DELETE FROM board WHERE board_num=?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, board_num);
				pstmt3.executeUpdate();
				// 모든 SQL문 실행이 성공하면 conn.commit();
				
				conn.commit();
			} catch (Exception e) {
				// 하나라도 SQL문이 실패하면
				conn.rollback();
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt3, conn);
			}
		}

	/*
	 * 좋아요 싫어요 파트 
	 */
		
	// 좋아요 등록
		public void insertLike(BoardLikeVO likeVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO board_like (board_num,mem_num,like_status) VALUES (?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, likeVO.getBoard_num());
			pstmt.setInt(2, likeVO.getMem_num());
			pstmt.setInt(3, likeVO.getLike_status());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//like_stats 업데이트 해주는 파트
	public void updateLike(BoardLikeVO likeVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE board_like SET like_status=? WHERE board_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, likeVO.getLike_status());
			pstmt.setInt(2, likeVO.getBoard_num());
			pstmt.setInt(3, likeVO.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
			
	}
	//like_stats 업데이트 해주는 파트
	public void toggleLike(BoardLikeVO likeVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE board_like SET like_status = CASE WHEN like_status = 1 THEN 2 ELSE 1 END "
				+ "WHERE board_num = ? AND mem_num = ?";	
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, likeVO.getBoard_num());
			pstmt.setInt(2, likeVO.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
			
	}
	//회원번호와 게시물 번호를 이용한 정보(좋/싫 선택 여부) 
		public BoardLikeVO selectLike(BoardLikeVO likeVO)throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BoardLikeVO like = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM board_like WHERE board_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, likeVO.getBoard_num());
				pstmt.setInt(2, likeVO.getMem_num());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					like = new BoardLikeVO();
					like.setBoard_num(rs.getInt("board_num"));
					like.setMem_num(rs.getInt("mem_num"));
					like.setLike_status(rs.getInt("like_status"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return like;
		}
	//좋아요 개수  
	public int selectLikeCount(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
		
			sql = "SELECT COUNT(*) FROM board_like WHERE board_num=? AND like_status=1";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, board_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	//싫어요 개수
	public int selectDislikeCount(int board_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성

			sql = "SELECT COUNT(*) FROM board_like WHERE board_num=? AND like_status=2";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, board_num);
			// SQL문 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}
	
	
	//좋아요 테이블에서 STATUS 읽어주는 메서드 하나 필요?
	
	
	//좋아요/싫어요 삭제
	public void deleteLike(BoardLikeVO likeVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM board_like WHERE board_num=? AND mem_num=?";
			//PrerparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, likeVO.getBoard_num());
			pstmt.setInt(2, likeVO.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//내가 선택한 좋아요 목록  (sql문장에 like_status 1인 애들 뽑는거 추가시켰음)
	public List<BoardVO> getListBoardLike(int start,int end,int mem_num)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM board JOIN member USING(mem_num)"
					+ " JOIN board_like l USING(board_num) WHERE l.mem_num=? AND l.like_status=1 ORDER BY board_num DESC)a)"
					+ " WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				board.setReg_date(rs.getDate("reg_date"));
				board.setMem_id(rs.getString("mem_id"));
				
				list.add(board);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
	/*
	 * 댓글  파트
	 */
	
	//댓글 등록
	//댓글 개수
	//댓글 목록
	//댓글 상세(댓글 수정, 삭제 시 작성자 회원번호 체크 용도로 사용(
	//댓글 수정
	//댓글 삭제
	
}


































