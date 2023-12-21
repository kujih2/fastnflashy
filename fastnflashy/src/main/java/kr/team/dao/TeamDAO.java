package kr.team.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.team.vo.TeamVO;
import kr.util.DBUtil;

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
		String sql = "";
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
				list.add(team);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
}
