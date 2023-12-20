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
				sql="UPDATE match_schedule SET schedule_status=0 ,schedule_regmatch=0 WHERE schedule_num = ?";
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
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		
		String sql = null;
		String sub_sql = "";
		String sub_sql2 = "";
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
							result = 0;//team1
						}else if(score1<score2) {
							result = 1;//team2
						}else if(score1 == score2) {
							result = 2;//team1,team2
						}
					}else if(ctg == 1) {//야구 일때
						score1 = random.nextInt(13);//0~12
						score2 = random.nextInt(13);	
						

						if(score1>score2) {
							result = 0;
						}else if(score1<score2) {
							result = 1;
						}else if(score1 == score2) {
							result = 2;
						}
					}else if(ctg == 2) {//배구 일때
						score1 = random.nextInt(4);//0~3
						if(score1==3) {//배구는 5판 3선 무승부가 없음.(team1의 승리)
							score2 =random.nextInt(3);//0~2
							result = 0;
						}else{//팀2의 승리
							score2=3;
							result = 1;
						}	
					}else if(ctg == 3) {//농구 무승부 없다.
						 do{
							score1 = random.nextInt(100);//0~99
							score2 = random.nextInt(100);
						}while(score1 == score2);
						if(score1>score2) {
							result = 0;
						}else if(score1<score2) {
							result = 1;
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
					
					if(result==0) {
						sub_sql += "team_win=team_win+1";//1팀 승
						sub_sql2 += "team_lose2=team_lose2+1";//2팀 패
					}else if(result==1) {
						sub_sql += "team_lose2=team_lose2+1";//1팀 패
						sub_sql2 += "team_win=team_win+1";//2팀 승
					}else{
						sub_sql += "team_draw=team_draw+1";//1팀 승
						sub_sql2 += "team_draw=team_draw+1";//2팀 승
					}
			
					sql = "UPDATE match_team SET " + sub_sql + " WHERE team_num = ?";//1팀
					pstmt4 = conn.prepareStatement(sql);
					pstmt4.setInt(1, rs.getInt("schedule_team1"));
					pstmt4.executeUpdate();
					
					sql = "UPDATE match_team SET " + sub_sql2 + " WHERE team_num = ?";//2팀
					pstmt5 = conn.prepareStatement(sql);
					pstmt5.setInt(1, rs.getInt("schedule_team2"));
					pstmt5.executeUpdate();
					
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
	//경기일정이 있는 날짜만 조회
	public List<ScheduleVO> ableDate() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ScheduleVO> dateList = null;
		String sql = null;

		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql="SELECT schedule_start FROM match_schedule";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 실행
			rs = pstmt.executeQuery();
			dateList = new ArrayList<ScheduleVO>();
			while(rs.next()) {
				ScheduleVO vo = new ScheduleVO();
				vo.setSchedule_date('"'+StringUtil.ScheduleDateFormat(rs.getString("schedule_start"))+'"'+",");
				dateList.add(vo);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return dateList;
	}
	//경기 일정 삽입 유효성체크
	public int checkInsert(ScheduleVO schedule) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		String sql=null;
		int check = 0;
		try {
			///커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			//SQL문 작성 - 입력한 경기 시작일 또는 경기 종료일이 이미 경기일정에 존재할경우
			sql = "SELECT * FROM match_schedule WHERE (schedule_start <= ? AND ? <= schedule_end) OR "
					+ "(schedule_start <= ? and  ? <=schedule_end)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1,schedule.getSchedule_start());
			pstmt.setString(2,schedule.getSchedule_start());
			pstmt.setString(3,schedule.getSchedule_end());
			pstmt.setString(4,schedule.getSchedule_end());
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				check=1;
			}
			
			//SQL문 작성 - 입력한 팀1, 팀2의 종목이 맞지 않을 때
			sql = "SELECT team_category FROM  match_team WHERE team_num = ? OR team_num = ?";
			pstmt2 = conn.prepareStatement(sql); 
			//?에 데이터 바인딩
			pstmt2.setInt(1, schedule.getSchedule_team1());
			pstmt2.setInt(2, schedule.getSchedule_team2());
			//SQL문 실행
			rs2 = pstmt2.executeQuery();
			while(rs2.next()) {
				if(rs2.getInt("team_category") != schedule.getTeam_category()){
					check=2;
				}
			 }
			
			//SQL문 작성 - 입력한 팀1,팀2의 팀번호가 존재하지 않는 팀번호일 때
			sql = "SELECT * FROM match_team WHERE team_num = ?";
			pstmt3 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt3.setInt(1, schedule.getSchedule_team1());
			//SQL문 실행
			rs3 = pstmt3.executeQuery();
			if(!rs3.next()) {
				check = 3;
			}
			
			//SQL문 작성 - 입력한 팀1,팀2의 팀번호가 존재하지 않는 팀번호일 때
			sql = "SELECT * FROM match_team WHERE team_num = ?";
			pstmt4 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt4.setInt(1, schedule.getSchedule_team2());
			//SQL문 실행
			rs4 = pstmt4.executeQuery();
			if(!rs4.next()) {
				check = 3;
			}
			//모든 SQL문이 정상적으로 수행
			conn.commit();
			}catch(Exception e) {
			//SQL문장이 하나라도 실패하면 
			 conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs4, pstmt4, conn);
			DBUtil.executeClose(rs3, pstmt3, conn);
			DBUtil.executeClose(rs2, pstmt2, conn);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return check;
	}
	//경기 일정 수정 유효성체크
		public int checkModify(ScheduleVO schedule) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			PreparedStatement pstmt4 = null;
			
			ResultSet rs = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			ResultSet rs4 = null;
			String sql=null;
			int check = 0;
			try {
				///커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//오토 커밋 해제
				conn.setAutoCommit(false);
				//SQL문 작성 - 입력한 경기 시작일 또는 경기 종료일이 이미 경기일정에 존재할경우
				sql = "SELECT * FROM match_schedule WHERE schedule_num !=? AND ((schedule_start <= ? AND ? <= schedule_end) OR "
						+ "(schedule_start <= ? and  ? <=schedule_end))";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, schedule.getSchedule_num());
				pstmt.setString(2,schedule.getSchedule_start());
				pstmt.setString(3,schedule.getSchedule_start());
				pstmt.setString(4,schedule.getSchedule_end());
				pstmt.setString(5,schedule.getSchedule_end());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					check=1;
				}
				//SQL문 작성 - 입력한 팀1, 팀2의 종목이 맞지 않을 때
				sql = "SELECT team_category FROM  match_team WHERE team_num = ? OR team_num = ?";
				pstmt2 = conn.prepareStatement(sql); 
				//?에 데이터 바인딩
				pstmt2.setInt(1, schedule.getSchedule_team1());
				pstmt2.setInt(2, schedule.getSchedule_team2());
				//SQL문 실행
				rs2 = pstmt2.executeQuery();
				while(rs2.next()) {
					if(rs2.getInt("team_category") != schedule.getTeam_category()){
						check=2;
					}
				 }
				
				//SQL문 작성 - 입력한 팀1,팀2의 팀번호가 존재하지 않는 팀번호일 때
				sql = "SELECT * FROM match_team WHERE team_num = ?";
				pstmt3 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt3.setInt(1, schedule.getSchedule_team1());
				//SQL문 실행
				rs3 = pstmt3.executeQuery();
				if(!rs3.next()) {
					check = 3;
				}
				
				//SQL문 작성 - 입력한 팀1,팀2의 팀번호가 존재하지 않는 팀번호일 때
				sql = "SELECT * FROM match_team WHERE team_num = ?";
				pstmt4 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt4.setInt(1, schedule.getSchedule_team2());
				//SQL문 실행
				rs4 = pstmt4.executeQuery();
				if(!rs4.next()) {
					check = 3;
				}
				//모든 SQL문이 정상적으로 수행
				conn.commit();
			}catch(Exception e) {
				//SQL문장이 하나라도 실패하면 
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs4, pstmt4, conn);
				DBUtil.executeClose(rs3, pstmt3, conn);
				DBUtil.executeClose(rs2, pstmt2, conn);
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return check;
		}
	//수정할 경기 조회
	public ScheduleVO modifySelect(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql=null;
		ScheduleVO vo = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql="SELECT * FROM match_schedule WHERE schedule_num = ?";
			 //PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, schedule_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new ScheduleVO();
				vo.setTeam_category(rs.getInt("team_category"));
				vo.setSchedule_start(rs.getString("schedule_start"));
				vo.setSchedule_end(rs.getString("schedule_end"));
				vo.setSchedule_status(rs.getInt("schedule_status"));
				vo.setSchedule_team1(rs.getInt("schedule_team1"));
				vo.setSchedule_team2(rs.getInt("schedule_team2"));
			}
		}catch(Exception e) {
			throw new Exception (e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	//경기 일정 수정
	public void modifySchedule(ScheduleVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		int regmatch=0;
		String sql=null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql="UPDATE match_schedule SET team_category=?,schedule_start=?,schedule_end=?,schedule_status=?,"
					+ " schedule_team1=?,schedule_team2=?,schedule_regmatch=? WHERE schedule_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, vo.getTeam_category());
			pstmt.setString(2, vo.getSchedule_start());
			pstmt.setString(3, vo.getSchedule_end());
			pstmt.setInt(4, vo.getSchedule_status());
			pstmt.setInt(5, vo.getSchedule_team1());
			pstmt.setInt(6, vo.getSchedule_team2());
			if(vo.getSchedule_status() == 3) {
				regmatch = 0;
			}
			pstmt.setInt(7, regmatch);
			pstmt.setInt(8, vo.getSchedule_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//경기 일정 삭제
	public  void deleteSchedule(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM match_schedule WHERE schedule_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, schedule_num);
			//SQL문 작성
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//경기 상세 조회
	public ScheduleVO selectDetail(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ScheduleVO vo = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션할당
			conn = DBUtil.getConnection();
			//SQL문 작성 
			sql = "SELECT * FROM match_schedule JOIN match_result USING(schedule_num) WHERE schedule_num = ?";
			//PreaparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, schedule_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(!rs.next()) {//경기결과가 아직 없는경우(종료된 경기가 아닌경우)
				//SQL문 작성
				sql = "SELECT * FROM match_schedule  WHERE schedule_num = ?";
				//PreparedStatement 객체 생성
				pstmt2 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt2.setInt(1, schedule_num);
				//SQL문 실행
				rs2 = pstmt2.executeQuery();
				if(rs2.next()) {
					vo = new ScheduleVO();
					vo.setSchedule_num(rs2.getInt("schedule_num"));
					vo.setTeam_category(rs2.getInt("team_category"));
					vo.setSchedule_start(StringUtil.DetailDateFormats(rs2.getString("schedule_start")));
					vo.setSchedule_status(rs2.getInt("schedule_status"));
					
					int team1 = (rs2.getInt("schedule_team1"));
					vo.setSchedule_team1(rs2.getInt("schedule_team1"));
					int team2 = (rs2.getInt("schedule_team2"));
					vo.setSchedule_team2(rs2.getInt("schedule_team2"));
					
					sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
					pstmt3 = conn.prepareStatement(sql);
					pstmt3.setInt(1, team1);
					rs3 = pstmt3.executeQuery();
					if(rs3.next()) {
						vo.setTeam1_name(rs3.getString("team_name"));
						vo.setTeam1_photo(rs3.getString("team_photo"));
					}
					sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
					pstmt4 = conn.prepareStatement(sql);
					pstmt4.setInt(1, team2);
					rs4 = pstmt4.executeQuery();
					if(rs4.next()) {
						vo.setTeam2_name(rs4.getString("team_name"));
						vo.setTeam2_photo(rs4.getString("team_photo"));
						}
				}
			}
			else{//종료된 경기라면
					vo = new ScheduleVO();
				vo.setSchedule_num(rs.getInt("schedule_num"));
				vo.setTeam_category(rs.getInt("team_category"));
				vo.setSchedule_start(StringUtil.DetailDateFormats(rs.getString("schedule_start")));
				vo.setSchedule_status(rs.getInt("schedule_status"));
				int team1 = (rs.getInt("schedule_team1"));
				vo.setSchedule_team1(rs.getInt("schedule_team1"));
				int team2 = (rs.getInt("schedule_team2"));
				vo.setSchedule_team2(rs.getInt("schedule_team2"));
				
				sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, team1);
				rs2 = pstmt2.executeQuery();
				if(rs2.next()) {
					vo.setTeam1_name(rs2.getString("team_name"));
					vo.setTeam1_photo(rs2.getString("team_photo"));
				}
				sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, team2);
				rs3 = pstmt3.executeQuery();
				if(rs3.next()) {
					vo.setTeam2_name(rs3.getString("team_name"));
					vo.setTeam2_photo(rs3.getString("team_photo"));
					}
				
				vo.setResult_team1Score(rs.getInt("result_team1Score"));
				vo.setResult_team2Score(rs.getInt("result_team2Score"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs4, pstmt4, conn);
			DBUtil.executeClose(rs3, pstmt3, conn);
			DBUtil.executeClose(rs2, pstmt2, conn);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	//해당 경기의 팀1과 팀2가  최근 경기한 전적
	public List<ScheduleVO> recentResultSchedule(int schedule_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String sql = null;  
		List<ScheduleVO> list = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM match_schedule JOIN match_result USING(schedule_num) WHERE"
					+ " (schedule_team1 = (select schedule_team1 from match_schedule where schedule_num = ?) OR schedule_team2 = (select schedule_team1 from match_schedule where schedule_num = ?))"
					+ " AND (schedule_team1 = (select schedule_team2 from match_schedule where schedule_num = ?) OR schedule_team2 = (select schedule_team2 from match_schedule where schedule_num = ?))"
					+ " ORDER BY schedule_start DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, schedule_num);
			pstmt.setInt(2, schedule_num);
			pstmt.setInt(3, schedule_num);
			pstmt.setInt(4, schedule_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<ScheduleVO>();
			while(rs.next()) {
				ScheduleVO vo = new ScheduleVO();
				int team1 = (rs.getInt("schedule_team1"));
				vo.setSchedule_team1(rs.getInt("schedule_team1"));
				int team2 = (rs.getInt("schedule_team2"));
				vo.setSchedule_team2(rs.getInt("schedule_team2"));
				
				sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, team1);
				rs2 = pstmt2.executeQuery();
				if(rs2.next()) {
					vo.setTeam1_name(rs2.getString("team_name"));
					vo.setTeam1_photo(rs2.getString("team_photo"));
				}
				sql="SELECT team_name,team_photo FROM match_team WHERE team_num = ?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, team2);
				rs3 = pstmt3.executeQuery();
				if(rs3.next()) {
					vo.setTeam2_name(rs3.getString("team_name"));
					vo.setTeam2_photo(rs3.getString("team_photo"));
					}
				vo.setResult_team1Score(rs.getInt("result_team1Score"));
				vo.setResult_team2Score(rs.getInt("result_team2Score"));
				vo.setResult_match(rs.getInt("result_match"));
				vo.setSchedule_start(StringUtil.DetailDateFormats(rs.getString("schedule_start")));
				
				list.add(vo);
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
}
