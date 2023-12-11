package kr.booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import kr.util.DBUtil;

public class BookingDAO {

	private static BookingDAO instance = new BookingDAO();
	public static BookingDAO getInstance() {
		return instance;
	}
	private BookingDAO() {}
	
	//등록가능한 경기 목록 불러오기
	public List<MatchVO> getListMatch() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MatchVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM match_schedule WHERE TO_DATE(schedule_start, 'YYYY-MM-DD HH24:MI') > SYSDATE";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<MatchVO>();
			while(rs.next()) {
				MatchVO match = new MatchVO();
				match
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	return list;
	}
	
	
}
