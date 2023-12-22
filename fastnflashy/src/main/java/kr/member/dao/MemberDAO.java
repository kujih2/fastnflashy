package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import kr.board.vo.BoardVO;
import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {

	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	//ID 중복체크 및 로그인 처리
	public MemberVO checkMember(String id) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM member LEFT OUTER JOIN member_detail USING(mem_num) WHERE mem_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_auth(rs.getInt("mem_auth"));
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_photo(rs.getString("mem_photo"));
				member.setMem_email(rs.getString("mem_email"));
			}
			

		}catch(Exception e) {
			
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
			
		}
		return member;
	}
	
	//회원가입
	public void insertMember(MemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0; // 시퀀스 번호 저장
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "SELECT member_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql = "INSERT INTO member (mem_num,mem_id,mem_auth) VALUES(?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, member.getMem_id());
			pstmt2.setInt(3, member.getMem_auth());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO member_detail (mem_num,mem_name,mem_pw,mem_tel,mem_email,mem_zipcode,mem_address1,mem_address2,mem_gender) VALUES(?,?,?,?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, member.getMem_name());
			pstmt3.setString(3, member.getMem_pw());
			pstmt3.setString(4, member.getMem_tel());
			pstmt3.setString(5, member.getMem_email());
			pstmt3.setString(6, member.getMem_zipcode());
			pstmt3.setString(7, member.getMem_address1());
			pstmt3.setString(8, member.getMem_address2());
			pstmt3.setInt(9, member.getMem_gender());
			pstmt3.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt2, conn);
		}
	}
	
	//회원탈퇴(회원 정보 삭제)
	public void deleteMember(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "UPDATE member SET mem_auth=0 WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM member_detail WHERE mem_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, mem_num);
			pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//내가 쓴 게시글 불러오기
	public List<BoardVO> getBoard(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql=null;
		List<BoardVO> list = null;
		
		try {
			conn = DBUtil.getConnection();

			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	
}
