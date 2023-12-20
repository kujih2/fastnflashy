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
			sql = "SELECT * FROM BOOKED_SEAT WHERE schedule_num=? AND seat_status=1";
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
			sql = "SELECT * FROM BOOKED_SEAT WHERE schedule_num=? AND seat_status=1";
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
		Integer num = null;//패키지 번호 저장
		int cnt = 0;
		
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
			
			if(num!=null) {
				sql = "INSERT INTO booked_info (booked_num,booked_package,seat_id,mem_num,booked_name,booked_email,booked_ip,booked_price) VALUES(booked_info_seq.nextval,?,?,?,?,?,?,?)";
			}else {
				sql = "INSERT INTO booked_info (booked_num,seat_id,mem_num,booked_name,booked_email,booked_ip,booked_price) VALUES(booked_info_seq.nextval,?,?,?,?,?,?)";
			}
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
				
				cnt=0;
				if(num!=null) {
					pstmt4.setInt(++cnt, num);
				}
				pstmt4.setInt(++cnt,seatId);
				pstmt4.setInt(++cnt,vo.getUser_num());
				pstmt4.setString(++cnt, vo.getBooked_name());
				pstmt4.setString(++cnt, vo.getBooked_email());
				pstmt4.setString(++cnt, vo.getBooked_ip());
				pstmt4.setInt(++cnt, vo.getBooked_price());
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
	
	//나의 예매정보 리스트 불러오기1
	public List<BookedInfoVO> getMyBookList1(int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<BookedInfoVO> list1 = new ArrayList<BookedInfoVO>();
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM booked_seat JOIN(SELECT * FROM(SELECT booked_num,booked_package,seat_id,mem_num,booked_regdate,booked_name,booked_email,booked_ip,booked_price,nvl(booked_package,1)*1 as cnt FROM booked_info WHERE booked_package IS NULL UNION ALL SELECT * FROM booked_info JOIN (SELECT MIN(booked_num) booked_num, count(*) cnt FROM booked_info WHERE booked_package IS NOT NULL GROUP BY booked_package) USING(BOOKED_NUM)) WHERE mem_num=?) USING(seat_id) ORDER BY booked_regdate DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BookedInfoVO vo = new BookedInfoVO();
				vo.setSeat_status(rs.getInt("seat_status"));
				vo.setBooked_regdate(rs.getDate("booked_regdate"));
				vo.setBooked_num(rs.getInt("booked_num"));
				vo.setCount_of_book(rs.getInt("cnt"));
				vo.setBooked_package(rs.getInt("booked_package"));
				list1.add(vo);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list1;
			
	}
	//나의 예매정보 리스트 불러오기2
	public List<ScheduleVO> getMyBookList2(int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<ScheduleVO> list2 = new ArrayList<ScheduleVO>();
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT sch.schedule_start,team1.team_name team_name1,team2.team_name team_name2 FROM (SELECT * FROM (SELECT * FROM booked_seat JOIN(SELECT * FROM(SELECT booked_num,booked_package,seat_id,mem_num,booked_regdate,booked_name,booked_email,booked_ip,booked_price,nvl(booked_package,1)*1 as cnt FROM booked_info WHERE booked_package IS NULL UNION ALL SELECT * FROM booked_info JOIN (SELECT MIN(booked_num) booked_num, count(*) cnt FROM booked_info WHERE booked_package IS NOT NULL GROUP BY booked_package) USING(BOOKED_NUM)) WHERE mem_num=?) USING(seat_id)) JOIN match_schedule USING(schedule_num)) sch JOIN match_team team1 ON sch.schedule_team1 = team1.team_num JOIN match_team team2 ON sch.schedule_team2 = team2.team_num";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ScheduleVO vo = new ScheduleVO();
				vo.setSchedule_start(rs.getString(1));
				vo.setTeam1_name(rs.getString(2));
				vo.setTeam2_name(rs.getString(3));
				list2.add(vo);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list2;
	}
	
	//예매 상세정보 불러오기1
	public List<BookedInfoVO> getMyBookDetail1(int booked_num, int booked_package) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql ="";
		ResultSet rs = null;
		List<BookedInfoVO> list = new ArrayList<BookedInfoVO>();
		
		try {
			conn = DBUtil.getConnection();
			if(booked_package==0) sub_sql = " WHERE booked_num=?";
			if(booked_package!=0) sub_sql = " WHERE booked_package=?";
			sql = "SELECT * FROM booked_seat JOIN booked_info USING(seat_id)"+sub_sql;
			pstmt = conn.prepareStatement(sql);
			if(booked_package==0) {
				pstmt.setInt(1, booked_num);
			}else {
				pstmt.setInt(1, booked_package);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BookedInfoVO vo = new BookedInfoVO();
				vo.setBooked_regdate(rs.getDate("booked_regdate"));
				vo.setSeat_status(rs.getInt("seat_status"));
				vo.setBooked_num(rs.getInt("booked_num"));
				vo.setBooked_package(rs.getInt("booked_package"));
				vo.setSeat_col(rs.getString("seat_col"));
				vo.setSeat_row(rs.getInt("seat_row"));
				vo.setBooked_price(rs.getInt("booked_price"));
				vo.setBooked_email(rs.getString("booked_email"));
				vo.setBooked_name(rs.getString("booked_name"));
				
				list.add(vo);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	public ScheduleVO getMyBookDetail2(int booked_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		ScheduleVO schedule = null;
		try {
			conn = DBUtil.getConnection();
			sql="SELECT sch.schedule_start, team1.team_name team_name1, team2.team_name team_name2 FROM (SELECT * FROM booked_seat JOIN booked_info USING(seat_id) JOIN match_schedule USING(schedule_num) WHERE booked_num = ?) sch JOIN match_team team1 ON sch.schedule_team1 = team1.team_num JOIN match_team team2 ON sch.schedule_team2 = team2.team_num";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, booked_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				schedule = new ScheduleVO();
				schedule.setSchedule_start(rs.getString(1));
				schedule.setTeam1_name(rs.getString(2));
				schedule.setTeam2_name(rs.getString(3));
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return schedule;
	}
	
	//예매 취소하기
	public void deleteBooking(int booked_num, int booked_package) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		if(booked_package==0)sub_sql=" WHERE booked_num=?";
		if(booked_package!=0)sub_sql=" WHERE booked_package=?";
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "SELECT * FROM booked_info"+sub_sql;
			pstmt = conn.prepareStatement(sql);
			if(booked_package==0) {
				pstmt.setInt(1, booked_num);
			}else {
				pstmt.setInt(1, booked_package);
			}
			rs = pstmt.executeQuery();
			
			sql = "UPDATE booked_seat SET seat_status=0 WHERE seat_id=?";
			pstmt2 = conn.prepareStatement(sql);
			while(rs.next()) {
				pstmt2.setInt(1, rs.getInt("seat_id"));
				pstmt2.executeUpdate();
			}
			
			conn.commit();
			
			
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	
}
