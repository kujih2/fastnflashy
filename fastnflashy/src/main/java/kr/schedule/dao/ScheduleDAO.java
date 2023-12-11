package kr.schedule.dao;

public class ScheduleDAO {
	
	private static ScheduleDAO instance = new ScheduleDAO();
	public static ScheduleDAO getInstance() {
		return instance;
	}
	private ScheduleDAO() {}
	
	//경기일정 삽입
	//경기일정 수정
	//경기일정 조회
	//경기결과 삽입
	
}
