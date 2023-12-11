package kr.schedule.vo;

public class ScheduleVO {
	private int schedule_num;        //경기 번호
	private int team_category;		 //경기 종목(축구:0,야구:1,배구:2,농구:3)	
	private String schedule_start;   //경기 시작일
	private String schedule_end;	 //경기 종료일
	private int schedule_status;	 //경기 현황(0:종료,1:진행중,2:예정,3:취소)
	private int schedule_team1;		 //경기 팀 1	
	private int schedule_team2;		 //경기 팀 2
	
	private int result_num;			//경기 결과 식별번호
	private int result_team1Score;	//팀1의 점수
	private int result_team2Score;	//팀2의 점수
	private int result_match;		//경기 결과(0:팀1의 승리,1:팀2의 승리,2:무승부)
	
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