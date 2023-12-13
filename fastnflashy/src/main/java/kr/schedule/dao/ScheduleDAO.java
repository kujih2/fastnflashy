package kr.schedule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kr.schedule.vo.ScheduleVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

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
			sql="INSERT INTO MATCH_SCHEDULE (schedule_num,team_category,schedule_start,schedule_end,schedule_status,schedule_team1,schedule_team2)"
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
	public List<ScheduleVO> selectSchedule(Integer team_category,String date) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		List<ScheduleVO> list = null;
		String sql = null;
		String sub_sql = "";
		
		String []arr = date.split("-");
		String date1 = arr[0]+arr[1]+arr[2];
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			if(team_category!=null && team_category==9 ) {
				sub_sql="WHERE schedule_start LIKE ? ORDER BY schedule_start DESC";
			}else if(team_category!=null && team_category==0) {
				sub_sql="WHERE team_category=0 AND schedule_start LIKE ? ORDER BY schedule_start DESC";
			}else if(team_category!=null && team_category==1) {
				sub_sql="WHERE team_category=1 AND schedule_start LIKE ? ORDER BY schedule_start DESC";
			}else if(team_category!=null && team_category==2) {
				sub_sql="WHERE team_category=2 AND schedule_start LIKE ? ORDER BY schedule_start DESC";
			}else if(team_category!=null && team_category==3) {
				sub_sql="WHERE team_category=3 AND schedule_start LIKE ? ORDER BY schedule_start DESC";
			}
			sql="SELECT * FROM match_schedule " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, date1+"%");
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<ScheduleVO>();
			while(rs.next()) {
				ScheduleVO schedule = new ScheduleVO();
				int schedule_num = rs.getInt("schedule_num");
				schedule.setSchedule_num(schedule_num);
				schedule.setTeam_category(rs.getInt("team_category"));
				schedule.setSchedule_start(rs.getString("schedule_start"));
				schedule.setSchedule_time(StringUtil.ScheduleTimeFormat(rs.getString("schedule_start")));
				schedule.setSchedule_end(rs.getString("schedule_end"));
				int status = rs.getInt("schedule_status");
				schedule.setSchedule_status(status);
				if(status == 0) {
					sql="SELECT * FROM match_result WHERE schedule_num = ?";
					pstmt2 = conn.prepareStatement(sql);
					pstmt2.setInt(1, schedule_num);
					rs2 = pstmt2.executeQuery();
					if(rs2.next()) {
						schedule.setResult_team1Score(rs2.getInt("result_team1Score"));
						schedule.setResult_team2Score(rs2.getInt("result_team2Score"));
					}
				}
				int team1 = rs.getInt("schedule_team1");
				schedule.setSchedule_team1(team1);
				int team2 = rs.getInt("schedule_team2");
				schedule.setSchedule_team2(team2);
				
					sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
					pstmt3 = conn.prepareStatement(sql);
					pstmt3.setInt(1, team1);
					rs3 = pstmt3.executeQuery();
					if(rs3.next()) {
						schedule.setTeam1_name(rs3.getString("team_name"));
						schedule.setTeam1_photo(rs3.getString("team_photo"));
					}
				sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
				pstmt4 = conn.prepareStatement(sql);
				pstmt4.setInt(1, team2);
				rs4 = pstmt4.executeQuery();
				if(rs4.next()) {
					schedule.setTeam2_name(rs4.getString("team_name"));
					schedule.setTeam2_photo(rs4.getString("team_photo"));
				}
				list.add(schedule);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs3, pstmt3, conn);
			DBUtil.executeClose(rs2, pstmt2, conn);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//경기 현황 최신화
	public void changeStatus() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			//SQL문 작성-끝난 경기 조회
			sql="SELECT schedule_num FROM match_schedule WHERE TO_DATE(schedule_end, 'YYYY-MM-DD HH24:MI') < SYSDATE";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 실행
			rs = pstmt.executeQuery();
			while(rs.next()) {//끝난 경기들이 있다면
				sql="UPDATE match_schedule SET schedule_status=0 WHERE schedule_num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rs.getInt("schedule_num"));
				pstmt.executeUpdate();
			}
			//SQL문 작성 - 진행중인 경기 조회
			sql="SELECT schedule_num FROM match_schedule WHERE TO_DATE(schedule_start, 'YYYY-MM-DD HH24:MI') <= SYSDATE AND TO_DATE(schedule_end, 'YYYY-MM-DD HH24:MI') >= SYSDATE";
			//PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//SQL문 실행
			rs2 = pstmt2.executeQuery();
			while(rs2.next()) { //만약 진행중인 경기들이 있다면
				sql="UPDATE match_schedule SET schedule_status=1 WHERE schedule_num = ?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, rs2.getInt("schedule_num"));
				pstmt3.executeUpdate();
				
			}
			//모든 SQL문이 정상적으로 수행
			conn.commit();
			
		}catch(Exception e) {
			//SQL문장이 하나라도 실패하면 
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs2, pstmt3, conn);
			DBUtil.executeClose(rs2, pstmt2, conn);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//경기결과 삽입
	public void insertResult() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		String sql = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		int score1 = 0;
		int score2 = 0;
		int result = 0;
		int num =0;//경기 번호를 담을 변수
		int ctg =0;//종목을 담을 변수
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			//SQL문 작성
			sql="SELECT * FROM match_schedule WHERE schedule_status = 0 ";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 실행
			rs = pstmt.executeQuery();
			while(rs.next()) {
				num = rs.getInt("schedule_num");
				ctg = rs.getInt("team_category");
				sql="SELECT * FROM match_result WHERE schedule_num =?";
				pstmt2=conn.prepareStatement(sql);
				pstmt2.setInt(1, num);
				rs2 = pstmt2.executeQuery();
				if(!rs2.next()) {//경기결과가 아직 들어가지 않은 경기라면
					Random random = new Random();
					if(ctg == 0) {//축구 일때
						score1 = random.nextInt(7);//0~6
						score2 = random.nextInt(7);
						
						if(score1>score2) {
							result = 0;
						}else if(score1<score2) {
							result = 1;
						}else if(score1 == score2) {
							result = 2;
						}
					}else if(ctg == 1) {//야구 일때
						score1 = random.nextInt(13);//0~12
						score2 = random.nextInt(13);	
						

						if(score1>score2) {
							result = 0;
						}else if(score1<score2) {
							result = 1;
						}else if(score1 == score2) {
						}
					}else if(ctg == 2) {//배구 일때
						score1 = random.nextInt(4);//0~3
						if(score1==3) {//배구는 5판 3선 무승부가 없음.(team1의 승리)
							score2 =random.nextInt(3);//0~2
						}else{//팀2의 승리
							score2=3;
						}	
					}else if(ctg == 3) {//농구
						score1 = random.nextInt(100);//0~99
						score2 = random.nextInt(100);
						
						if(score1>score2) {
							result = 0;
						}else if(score1<score2) {
							result = 1;
						}else if(score1 == score2) {
						}
					}
					
					sql="INSERT INTO match_result (result_num,schedule_num,result_team1Score,result_team2Score,result_match) "
							+ "VALUES(result_seq.nextval,?,?,?,?)";
					pstmt3=conn.prepareStatement(sql);
					pstmt3.setInt(1, num);
					pstmt3.setInt(2, score1);	
					pstmt3.setInt(3, score2);
					pstmt3.setInt(4, result);
					
					pstmt3.executeUpdate();
				}
				
			}
			//모든 SQL문이 정상적으로 수행
			conn.commit();
			
		}catch(Exception e) {
			//SQL문장이 하나라도 실패하면 
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, conn);
			DBUtil.executeClose(rs2, pstmt2, conn);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
}
