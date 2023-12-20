package kr.schedule.vo;

import kr.team.vo.TeamVO;

public class ScheduleVO {
	private int schedule_num;        //경기 번호
	private int team_category;		 //경기 종목(축구:0,야구:1,배구:2,농구:3)	
	private String schedule_start;   //경기 시작일
	private String schedule_time;	 //경기 시작 시간
	private String schedule_date;	 //경기일 (년-월-일)
	private String schedule_end;	 //경기 종료일
	private int schedule_status;	 //경기 현황(0:종료,1:진행중,2:예정,3:취소)
	private int schedule_team1;		 //경기 팀 1	식별번호
	private int schedule_team2;		 //경기 팀 2	식별번호
	
	private String team1_name;		 //경기팀 1 이름
	private String team2_name;		 //경기팀 2 이름
	private String team1_photo;		 //경기팀 1 사진
	private String team2_photo;		 //경기팀 2 사진
	
	private int result_num;			//경기 결과 식별번호
	private int result_team1Score;	//팀1의 점수
	private int result_team2Score;	//팀2의 점수
	private int result_match;		//경기 결과(0:팀1의 승리,1:팀2의 승리,2:무승부)
	
	TeamVO teamVO;
	
	public int getSchedule_num() {
		return schedule_num;
	}
	public void setSchedule_num(int schedule_num) {
		this.schedule_num = schedule_num;
	}
	public int getTeam_category() {
		return team_category;
	}
	public void setTeam_category(int team_category) {
		this.team_category = team_category;
	}
	public String getSchedule_start() {
		return schedule_start;
	}
	public String getSchedule_time() {
		return schedule_time;
	}
	public void setSchedule_time(String schedule_time) {
		this.schedule_time = schedule_time;
	}
	public String getSchedule_date() {
		return schedule_date;
	}
	public void setSchedule_date(String schedule_date) {
		this.schedule_date = schedule_date;
	}
	public void setSchedule_start(String schedule_start) {
		this.schedule_start = schedule_start;
	}
	public String getSchedule_end() {
		return schedule_end;
	}
	public void setSchedule_end(String schedule_end) {
		this.schedule_end = schedule_end;
	}
	public int getSchedule_status() {
		return schedule_status;
	}
	public void setSchedule_status(int schedule_status) {
		this.schedule_status = schedule_status;
	}
	public int getSchedule_team1() {
		return schedule_team1;
	}
	public void setSchedule_team1(int schedule_team1) {
		this.schedule_team1 = schedule_team1;
	}
	public int getSchedule_team2() {
		return schedule_team2;
	}
	public void setSchedule_team2(int schedule_team2) {
		this.schedule_team2 = schedule_team2;
	}
	public String getTeam1_name() {
		return team1_name;
	}
	public void setTeam1_name(String team1_name) {
		this.team1_name = team1_name;
	}
	public String getTeam2_name() {
		return team2_name;
	}
	public void setTeam2_name(String team2_name) {
		this.team2_name = team2_name;
	}
	public String getTeam1_photo() {
		return team1_photo;
	}
	public void setTeam1_photo(String team1_photo) {
		this.team1_photo = team1_photo;
	}
	public String getTeam2_photo() {
		return team2_photo;
	}
	public void setTeam2_photo(String team2_photo) {
		this.team2_photo = team2_photo;
	}
	public int getResult_num() {
		return result_num;
	}
	public void setResult_num(int result_num) {
		this.result_num = result_num;
	}
	public int getResult_team1Score() {
		return result_team1Score;
	}
	public void setResult_team1Score(int result_team1Score) {
		this.result_team1Score = result_team1Score;
	}
	public int getResult_team2Score() {
		return result_team2Score;
	}
	public void setResult_team2Score(int result_team2Score) {
		this.result_team2Score = result_team2Score;
	}
	public int getResult_match() {
		return result_match;
	}
	public void setResult_match(int result_match) {
		this.result_match = result_match;
	}
	
	
}
