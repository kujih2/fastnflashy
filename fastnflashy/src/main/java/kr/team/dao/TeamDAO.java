package kr.team.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.schedule.vo.ScheduleVO;
import kr.team.vo.TeamVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class TeamDAO {
	private static TeamDAO instance = new TeamDAO();
	public static TeamDAO getInstance() {
		return instance;
	}
	private TeamDAO() {}
	
//승률 최신화
	public void updateOdds2() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM match_team";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				String odds2="0.0";
				int win = rs.getInt("team_win");
				int lose = rs.getInt("team_lose2");
				int draw = rs.getInt("team_draw");
				double odds = 0.0;
				if(win !=0) {
					odds = (win*1.0/(win+lose+draw)) * 100.0; 
					odds2 = String.format("%.1f", odds);
				}
				sql="UPDATE match_team SET team_odds2 = ? WHERE team_num = ?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setDouble(1, Double.valueOf(odds2));
				pstmt2.setInt(2,rs.getInt("team_num"));
				
				pstmt2.executeUpdate();
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//순위 조회
	public List<TeamVO> selectRank(int team_category,String rank) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<TeamVO> list = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT DENSE_RANK() OVER(ORDER BY "+ rank +" DESC)team_rank,team_num,team_category,team_name,team_photo,team_win,team_lose2,team_draw,team_odds2 FROM match_team WHERE team_category=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, team_category);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<TeamVO>();
			while(rs.next()) {
				TeamVO team = new TeamVO();
				team.setTeam_rank(rs.getInt("team_rank"));
				team.setTeam_num(rs.getInt("team_num"));
				team.setTeam_category(rs.getInt("team_category"));
				team.setTeam_name(rs.getString("team_name"));
				team.setTeam_photo(rs.getString("team_photo"));
				team.setTeam_win(rs.getInt("team_win"));
				team.setTeam_lose2(rs.getInt("team_lose2"));
				team.setTeam_draw(rs.getInt("team_draw"));
				team.setTeam_odds2(rs.getString("team_odds2"));
				team.setTeam_playCount(rs.getInt("team_win")+rs.getInt("team_lose2")+rs.getInt("team_draw"));
				list.add(team);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//팀 전체 조회
	public List<TeamVO> selectTeams(int team_category) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<TeamVO> list = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM match_team WHERE team_category= ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, team_category);
			rs = pstmt.executeQuery();
			list = new ArrayList<TeamVO>();
			while(rs.next()) {
				TeamVO team = new TeamVO();
				team.setTeam_num(rs.getInt("team_num"));
				team.setTeam_name(rs.getString("team_name"));
				team.setTeam_photo(rs.getString("team_photo"));
				team.setTeam_category(team_category);
				list.add(team);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//팀정보 조회
		public TeamVO selectTeamDetail(int team_num, int team_category) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql=null;
			ResultSet rs = null;
			TeamVO team = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM (SELECT DENSE_RANK() OVER(ORDER BY team_odds2 DESC)team_rank,team_num,team_category,team_name,team_photo,team_win,team_lose2,team_draw,team_odds2"
						+ " FROM match_team WHERE team_category=?) WHERE team_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, team_category);
				pstmt.setInt(2, team_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					team = new TeamVO();
					team.setTeam_rank(rs.getInt("team_rank"));
					team.setTeam_num(rs.getInt("team_num"));
					team.setTeam_category(rs.getInt("team_category"));
					team.setTeam_name(rs.getString("team_name"));
					team.setTeam_photo(rs.getString("team_photo"));
					team.setTeam_win(rs.getInt("team_win"));
					team.setTeam_lose2(rs.getInt("team_lose2"));
					team.setTeam_draw(rs.getInt("team_draw"));
					team.setTeam_odds2(rs.getString("team_odds2"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return team;
		}
	//해당 팀의 최근 전적
		public List<ScheduleVO> resultTeam(int team_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String sql=null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			List<ScheduleVO> list = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM match_schedule JOIN match_result USING (schedule_num)"
						+ " WHERE schedule_team1 = ? OR schedule_team2 = ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, team_num);
				pstmt.setInt(2, team_num);
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
					vo.setSchedule_start(StringUtil.DetailDateFormats2(rs.getString("schedule_start")));
					
					list.add(vo);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs3, pstmt3, null);
				DBUtil.executeClose(rs2, pstmt2, null);
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		//해당 팀의 예정 경기 조회
		public List<ScheduleVO> selectScheduledGame(int team_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			String sql = null;
			List<ScheduleVO> list = null;
			list = new ArrayList<ScheduleVO>();
			
			try {
				conn=DBUtil.getConnection();
				sql = "SELECT * FROM match_schedule WHERE schedule_status=2 AND(schedule_team1 = ? OR schedule_team2 = ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, team_num);
				pstmt.setInt(2, team_num);
				rs = pstmt.executeQuery();
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
					vo.setSchedule_start(StringUtil.DetailDateFormats2(rs.getString("schedule_start")));
					
					list.add(vo);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs3, pstmt3, null);
				DBUtil.executeClose(rs2, pstmt2, null);
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	//선수정보 조회
		public List<TeamVO> selectPlayer(int team_category) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			List<TeamVO> list = null;
			list = new ArrayList<TeamVO>();
			
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM match_player WHERE team_category = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, team_category);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					TeamVO vo = new TeamVO();
					vo.setPlayer_name(rs.getString("player_name"));
					vo.setPlayer_backnum(rs.getString("player_backnum"));
					vo.setPlayer_height(rs.getString("player_height"));
					vo.setPlayer_position(rs.getString("player_position"));
					vo.setPlayer_age(rs.getString("player_age"));
					list.add(vo);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
}
