package kr.schedule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.schedule.vo.ScheduleVO;
import kr.util.DBUtil;

public class ScheduleDAO {
	
	private static ScheduleDAO instance = new ScheduleDAO();
	public static ScheduleDAO getInstance() {
		return instance;
	}
	private ScheduleDAO() {}
	
	//경기일정 삽입
	public void insertSchedule(ScheduleVO schedule) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql="INSERT INTO MATCH_SCHEDULE "
				+ "VALUES(schedule_seq.nextval,?,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, schedule.getTeam_category());
			pstmt.setString(2, schedule.getSchedule_start());
			pstmt.setString(3, schedule.getSchedule_end());
			pstmt.setInt(4, schedule.getSchedule_status());
			pstmt.setInt(5, schedule.getSchedule_team1());
			pstmt.setInt(6, schedule.getSchedule_team2());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//경기일정 수정
	//경기일정 조회
	//경기결과 삽입
	
}
