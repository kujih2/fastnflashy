package kr.booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.booking.vo.BookedInfoVO;
import kr.member.vo.MemberVO;
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
	//경기 좌석정보 조회하기1(예약된 좌석 개수 세기)
	public int getNumOfBookedSeats(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM BOOKED_SEAT WHERE schedule_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,schedule_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count++;
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return count;
	}
	//경기 좌석정보 조회하기2
	public List<BookedInfoVO> getInfoOfSeats(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<BookedInfoVO> list = new ArrayList<BookedInfoVO>();
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM BOOKED_SEAT WHERE schedule_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,schedule_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BookedInfoVO vo = new BookedInfoVO();
				vo.setSeat_col(rs.getString("seat_col"));
				vo.setSeat_row(rs.getInt("seat_row"));
				list.add(vo);
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return list;
	}
	//회원 잔고 조회하기
	public MemberVO getBalanceOfMember(int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		MemberVO member = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM member JOIN member_detail USING(mem_num) WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_name(rs.getString("mem_name"));
				member.setMem_email(rs.getString("mem_email"));
				member.setMem_zipcode(rs.getString("mem_zipcode"));
				member.setMem_address1(rs.getString("mem_address1"));
				member.setMem_address2(rs.getString("mem_address2"));
				member.setMem_regdate(rs.getDate("mem_regdate"));
				member.setMem_gender(rs.getInt("mem_gender"));
				member.setMem_balance(rs.getInt("mem_balance"));
				member.setMem_subscription(rs.getInt("mem_subscription"));
				member.setMem_photo(rs.getString("mem_photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return member;
	}
	
	//예매 등록하기
	public void insertBooked(List<BookedInfoVO> list,int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		String sql = null;
		Integer seatId = null;
		Integer num = 0;//패키지 번호 저장
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			if(list.size()>1) {
				sql = "SELECT booked_package_seq.nextval FROM dual";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					num = rs.getInt(1);
				}
				
			}
			
			sql = "SELECT booked_seat_seq.nextval FROM dual";
			pstmt2 = conn.prepareStatement(sql);
			
			sql = "INSERT INTO booked_seat (schedule_num,seat_id,seat_col,seat_row) VALUES(?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			
			sql = "INSERT INTO booked_info (booked_num,booked_package,seat_id,mem_num,booked_name,booked_email,booked_ip,booked_price) VALUES(booked_info_seq.nextval,?,?,?,?,?,?,?)";
			pstmt4 = conn.prepareStatement(sql);
			
			for(int i=0;i<list.size();i++) {
				BookedInfoVO vo = list.get(i);
				
				rs = pstmt2.executeQuery();
				if(rs.next()) {
					seatId = rs.getInt(1);
				}
				
				pstmt3.setInt(1, schedule_num);
				pstmt3.setInt(2,seatId);
				pstmt3.setString(3,vo.getSeat_col());
				pstmt3.setInt(4,vo.getSeat_row());
				pstmt3.addBatch();
				
				
				pstmt4.setInt(1, num);
				pstmt4.setInt(2,seatId);
				pstmt4.setInt(3,vo.getUser_num());
				pstmt4.setString(4, vo.getBooked_name());
				pstmt4.setString(5, vo.getBooked_email());
				pstmt4.setString(6, vo.getBooked_ip());
				pstmt4.setInt(7, vo.getBooked_price());
				pstmt4.addBatch();
				
				if(i%1000==0) {
					pstmt3.executeBatch();
					pstmt4.executeBatch();
				}
			}
			pstmt3.executeBatch();
			pstmt4.executeBatch();
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
			
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(rs, pstmt4, conn);
		}
		
		
	}
	
	//나의 예매정보 불러오기
	public List<BookedInfoVO> getMyBook(int user_num,int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<BookedInfoVO> list = null;
		
		try {
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
			
	}
	
	
}
