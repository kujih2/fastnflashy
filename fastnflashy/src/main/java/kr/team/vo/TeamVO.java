package kr.team.vo;

public class TeamVO {
	private int team_num;
	private int team_category;
	private String team_name;
	private int team_win;
	private int team_lose2;
	private int team_draw;
	private String team_photo;
	private String team_odds2; //승률
	private int team_rank;//순위
	
	public int getTeam_num() {
		return team_num;
	}
	public void setTeam_num(int team_num) {
		this.team_num = team_num;
	}
	public int getTeam_category() {
		return team_category;
	}
	public void setTeam_category(int team_category) {
		this.team_category = team_category;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public int getTeam_win() {
		return team_win;
	}
	public void setTeam_win(int team_win) {
		this.team_win = team_win;
	}
	public int getTeam_lose2() {
		return team_lose2;
	}
	public void setTeam_lose2(int team_lose2) {
		this.team_lose2 = team_lose2;
	}
	public int getTeam_draw() {
		return team_draw;
	}
	public void setTeam_draw(int team_draw) {
		this.team_draw = team_draw;
	}
	public String getTeam_photo() {
		return team_photo;
	}
	public void setTeam_photo(String team_photo) {
		this.team_photo = team_photo;
	}
	public String getTeam_odds2() {
		return team_odds2;
	}
	public void setTeam_odds2(String team_odds) {
		this.team_odds2 = team_odds;
	}
	public int getTeam_rank() {
		return team_rank;
	}
	public void setTeam_rank(int team_rank) {
		this.team_rank = team_rank;
	}
	
	
}
