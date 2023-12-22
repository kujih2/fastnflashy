package kr.magazin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.magazin.vo.MagazinReplyVO;
import kr.magazin.vo.MagazinVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class MagazinDAO {
	//싱글턴 패턴ㅁ
	private static MagazinDAO instance = new MagazinDAO();
	
	public static MagazinDAO getInstance() {
		return instance;
	}
	
	private MagazinDAO() {}
	
	//칼럼 니스트 - 칼럼 등록
	public void insertMagazin(MagazinVO magazin)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO magazin_board (mg_board_num,mg_title,sports_category,mg_content,"
				+ "mg_photo1,mg_photo2,mg_ip,mem_num) VALUES (magazin_seq.nextval,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, magazin.getMg_title());
			pstmt.setInt(2, magazin.getSports_category());
			pstmt.setString(3, magazin.getMg_content());
			pstmt.setString(4, magazin.getMg_photo1());
			pstmt.setString(5, magazin.getMg_photo2());
			pstmt.setString(6, magazin.getMg_ip());
			pstmt.setInt(7, magazin.getMem_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//전체 레코드수/검색 레코드 수
	public int getMagazinCount(String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "WHERE mg_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE mem_name LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE mg_content LIKE ?";
			}
			sql = "SELECT COUNT(*) FROM magazin_board JOIN member_detail USING(mem_num) " + sub_sql;
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%"+keyword+"%");
			}
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
	//전체글/검색 글 목록
	public List<MagazinVO> getListMagazin(int start, int end,String keyfield,String keyword,int sports_category)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MagazinVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색
				if(keyfield.equals("1")) sub_sql += "WHERE mg_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE mem_name LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE mg_content LIKE ?";
			}
			if(sports_category < 0) {

			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM magazin_board JOIN member_detail USING(mem_num) " + sub_sql
				+ "ORDER BY mg_board_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<MagazinVO>();
			while(rs.next()) {
				MagazinVO magazin = new MagazinVO();
				magazin.setMg_board_num(rs.getInt("mg_board_num"));
				magazin.setMg_title(StringUtil.useNoHtml(rs.getString("mg_title")));
				magazin.setMg_hit(rs.getInt("mg_hit"));
				magazin.setMg_content(StringUtil.shortWords(100, StringUtil.useNoHtml(rs.getString("mg_content"))));
				magazin.setMg_photo1(rs.getString("mg_photo1"));
				
				list.add(magazin);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//글상세
	public MagazinVO getMagazin(int mg_board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MagazinVO magazin = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM magazin_board JOIN member USING(mem_num) "
				+ "LEFT OUTER JOIN member_detail USING(mem_num) "
				+ "WHERE mg_board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mg_board_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				magazin = new MagazinVO();
				magazin.setMg_board_num(rs.getInt("mg_board_num"));
				magazin.setMg_title(rs.getString("mg_title"));
				magazin.setSports_category(rs.getInt("sports_category"));
				magazin.setMg_content(rs.getString("mg_content"));
				magazin.setMg_photo1(rs.getString("mg_photo1"));
				magazin.setMg_photo2(rs.getString("mg_photo2"));
				magazin.setMg_hit(rs.getInt("mg_hit"));
				magazin.setMg_reg_date(rs.getDate("mg_reg_date"));
				magazin.setMg_modify_date(rs.getDate("mg_modify_date"));
				magazin.setMem_name(rs.getString("mem_name"));
				magazin.setMem_email(rs.getString("mem_email"));
				magazin.setMem_photo(rs.getString("mem_photo"));
				magazin.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return magazin;
	}
	//조회수 증가
	public void updateReadcount(int mg_board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE magazin_board SET mg_hit=mg_hit+1 WHERE mg_board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mg_board_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//파일 삭제
	public void deletePhoto(int mg_board_num,String mg_photo1,String mg_photo2)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		
		try {
			conn = DBUtil.getConnection();
			
			if(mg_photo1 != null) {
				sub_sql += "mg_photo1=''";
			}else sub_sql += "mg_photo2=''";
			
			sql = "UPDATE magazin_board SET " + sub_sql + " WHERE mg_board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mg_board_num);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//칼럼니스트 - 칼럼 수정
	public void updateMagazin(MagazinVO magazin)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(magazin.getMg_photo1()!=null) {
				sub_sql += ",mg_photo1=?";
			}
			if(magazin.getMg_photo2()!=null) {
				sub_sql += ",mg_photo2=?";
			};
			//SQL문 작성
			sql ="UPDATE magazin_board SET sports_category=?,mg_title=?,mg_content=?,"
			   + "mg_ip=?,mg_modify_date=SYSDATE" + sub_sql + " WHERE mg_board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, magazin.getSports_category());
			pstmt.setString(++cnt, magazin.getMg_title());
			pstmt.setString(++cnt, magazin.getMg_content());
			pstmt.setString(++cnt, magazin.getMg_ip());
			if(magazin.getMg_photo1() != null) {
				pstmt.setString(++cnt, magazin.getMg_photo1());
			} 
			if(magazin.getMg_photo2() != null) {
				pstmt.setString(++cnt, magazin.getMg_photo2());
			}
			pstmt.setInt(++cnt, magazin.getMg_board_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자 - 칼럼 삭제
	public void magazinDelete(int mg_board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋해제
			conn.setAutoCommit(false);
			
			//좋아요 삭제
			
			//댓글 삭제
			sql = "DELETE FROM magazin_reply WHERE mg_board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mg_board_num);
			pstmt.executeUpdate();
			//부모글 삭제
			sql = "DELETE FROM magazin_board WHERE mg_board_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, mg_board_num);
			pstmt2.executeUpdate();
			
			//모든SQL 성공
			conn.commit();
		}catch(Exception e) {
			//하나라도 실패시
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//칼럼 반응 등록
	
	//칼럼 반응 개수
	//댓글 등록
	public void insertReplyMagazin(MagazinReplyVO magazinReply)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀 로드
			conn = DBUtil.getConnection();
			//SQL 작성
			sql = "INSERT INTO magazin_reply (mg_re_num,"
				+ "mg_re_content,mg_re_ip,mem_num,mg_board_num) "
				+ "VALUES (magazin_reply_seq.nextval,?,?,?,?)";
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			//?데이터 바인딩
			pstmt.setString(1, magazinReply.getMg_re_content());
			pstmt.setString(2, magazinReply.getMg_re_ip());
			pstmt.setInt(3, magazinReply.getMem_num());
			pstmt.setInt(4, magazinReply.getMg_board_num());
			//SQL 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//댓글 개수
	public int getReplyMagazinCount(int mg_board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥션풀 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM magazin_reply JOIN member "
				+ "USING(mem_num) WHERE mg_board_num=?";
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			//?데이터 바인딩
			pstmt.setInt(1, mg_board_num);
			//SQL 실행
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
	//댓글 목록
	public List<MagazinReplyVO> getListReplyMagazin(int start,int end,
									int mg_board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MagazinReplyVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션할당
			conn = DBUtil.getConnection();
			//SQL 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM magazin_reply JOIN member "
				+ "USING (mem_num) WHERE mg_board_num=? "
				+ "ORDER BY mg_re_num DESC)a) WHERE rnum >= ? AND rnum <=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?데이터 바인딩
			pstmt.setInt(1, mg_board_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<MagazinReplyVO>();
			while(rs.next()) {
				MagazinReplyVO reply = new MagazinReplyVO();
				reply.setMg_re_num(rs.getInt("mg_re_num"));
				//날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
				reply.setMg_re_date(
						DurationFromNow.getTimeDiffLabel(
							 rs.getString("mg_re_date")));
				if(rs.getString("mg_re_modifydate")!=null) {
					reply.setMg_re_modifydate(
						DurationFromNow.getTimeDiffLabel(
								rs.getString("mg_re_modifydate")));
				}
				reply.setMg_re_content(StringUtil.useBrNoHtml(
								rs.getString("mg_re_content")));
				reply.setMg_board_num(rs.getInt("mg_board_num"));
				reply.setMem_num(rs.getInt("mem_num"));
				reply.setMem_id(rs.getString("mem_id"));
				
				list.add(reply);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//댓글 상세
	public MagazinReplyVO getReplyMagazin(int mg_re_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MagazinReplyVO reply = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션할당
			conn = DBUtil.getConnection();
			//SQL작성
			sql = "SELECT * FROM magazin_reply WHERE mg_re_num=?";
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			//?데이터 바인딩
			pstmt.setInt(1, mg_re_num);
			//SQL실행 결과과행을 resultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new MagazinReplyVO();
				reply.setMg_re_num(rs.getInt("mg_re_num"));
				reply.setMem_num(rs.getInt("mem_num"));
			}	
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return reply;
	}
	
	//댓글 수정
	public void magazinUpdateReplay(MagazinReplyVO reply)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀 로드
			conn = DBUtil.getConnection();
			//SQL문
			sql = "UPDATE magazin_reply SET mg_re_content=?,"
				+ "mg_re_modifydate=SYSDATE,mg_re_ip=? WHERE mg_re_num=?";
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			//?데이터 바인딩
			pstmt.setString(1, reply.getMg_re_content());
			pstmt.setString(2, reply.getMg_re_ip());
			pstmt.setInt(3, reply.getMg_re_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//댓글 삭제
	public void magazinDeleteReply(int mg_re_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL작성
			sql = "DELETE FROM magazin_reply WHERE mg_re_num=?";
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mg_re_num);
			//SQL 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 좋아요, 싫어요
	
}
