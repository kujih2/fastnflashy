package kr.booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.booking.vo.BookedSeatVO;
import kr.schedule.vo.ScheduleVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class BookingDAO {

	private static BookingDAO instance = new BookingDAO();
	public static BookingDAO getInstance() {
		return instance;
	}
	private BookingDAO() {}
	
	//등록가능한 경기 목록 불러오기
	public List<ScheduleVO> getListMatch() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ScheduleVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM match_schedule WHERE schedule_status = 2 AND schedule_regmatch = 0";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<ScheduleVO>();
			while(rs.next()) {
				ScheduleVO schedule = new ScheduleVO();
				schedule.setSchedule_num(rs.getInt("schedule_num"));
				schedule.setTeam_category(rs.getInt("team_category"));
				schedule.setSchedule_start(rs.getString("schedule_start"));
				schedule.setSchedule_end(rs.getString("schedule_end"));
				schedule.setSchedule_status(rs.getInt("schedule_status"));
				schedule.setSchedule_team1(rs.getInt("schedule_team1"));
				schedule.setSchedule_team2(rs.getInt("schedule_team2"));
				
				list.add(schedule);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	return list;
	}
	//경기 등록하기
	public void registerSaleOfMatch(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE match_schedule SET schedule_regmatch=1 WHERE schedule_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, schedule_num);
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//등록된 경기 목록 불러오기
	public List<ScheduleVO> getListOfRegistedMatch() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ScheduleVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM match_schedule WHERE schedule_regmatch = 1";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<ScheduleVO>();
			while(rs.next()) {
				ScheduleVO schedule =new ScheduleVO();
				schedule.setSchedule_num(rs.getInt("schedule_num"));
				schedule.setTeam_category(rs.getInt("team_category"));
				schedule.setSchedule_start(StringUtil.standardFormOfDate(rs.getString("schedule_start")));
				schedule.setSchedule_end(rs.getString("schedule_end"));
				schedule.setSchedule_status(rs.getInt("schedule_status"));
				schedule.setSchedule_team1(rs.getInt("schedule_team1"));
				schedule.setSchedule_team2(rs.getInt("schedule_team2"));
			
				list.add(schedule);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}

	//경기 조희
	public ScheduleVO getInfoOfMatch(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ScheduleVO schedule = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT sch.schedule_num, sch.team_category, sch.schedule_start, sch.schedule_end, sch.schedule_status,sch.schedule_team1,team1.team_name team_name1,sch.schedule_team2,team2.team_name team_name2 FROM match_schedule sch JOIN match_team team1 ON sch.schedule_team1 = team1.team_num JOIN match_team team2 ON sch.schedule_team2 = team2.team_num WHERE sch.schedule_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, schedule_num);;
			rs = pstmt.executeQuery();
			if(rs.next()) {
				schedule = new ScheduleVO();
				schedule.setSchedule_num(schedule_num);
				schedule.setTeam_category(rs.getInt("team_category"));
				schedule.setSchedule_start(StringUtil.standardFormOfDate(rs.getString("schedule_start")));
				schedule.setSchedule_status(rs.getInt("schedule_status"));
				schedule.setSchedule_team1(rs.getInt("schedule_team1"));
				schedule.setTeam1_name(rs.getString("team_name1"));
				schedule.setSchedule_team2(rs.getInt("schedule_team2"));
				schedule.setTeam2_name(rs.getString("team_name2"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return schedule;
	}
	
	//경기 좌석정보 조회하기
//	public BookedSeatVO getInfoOfSeats(int schedule_num) throws Exception{
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		String sql = null;
//		ResultSet rs = null;
//		BookedSeatVO vo = null;
//		
//		try {
//			
//		}catch(Exception e) {
//			throw new Exception(e);
//		}finally {
//			DBUtil.executeClose(rs, pstmt, conn);
//		}
//		
//		
//		return vo;
//	}
	
	
	
	
}
