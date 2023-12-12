package kr.magazin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.magazin.vo.MagazinVO;
import kr.util.DBUtil;

public class MagazinDAO {
	//싱글턴 패턴
	private static MagazinDAO instance = new MagazinDAO();
	
	public static MagazinDAO getInstance() {
		return instance;
	}
	
	private MagazinDAO() {}
	
	//칼럼니스트 - 칼럼 등록
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
	//전체글/검색 글 목록
	//글상세
	//조회수 증가
	//파일 삭제
	//칼럼니스트 - 칼럼 수정
	//관리자 - 칼럼 삭제
	//칼럼 반응 등록
	//칼럼 반응 개수
	//댓글 등록
	//댓글 개수
	//댓글 목록
	//댓글 상세
	//댓글 수정
	//댓글 삭제
	//댓글 좋아요, 싫어요
	
}
