package kr.booking.vo;

public class BookedSeatVO {
	private int schedule_num;		//각 경기를 식별하는 번호
	private int seat_id;			//예매시 부여되는 고유 좌석번호
	private String seat_col;		//절대적 좌석 행번호
	private int seat_row;			//절대적 좌석 열번호
	private int seat_status;		//좌석 예매 상태 0취소 1예매됨
	
	
	public int getSchedule_num() {
		return schedule_num;
	}
	public void setSchedule_num(int schedule_num) {
		this.schedule_num = schedule_num;
	}
	public int getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(int seat_id) {
		this.seat_id = seat_id;
	}
	public String getSeat_col() {
		return seat_col;
	}
	public void setSeat_col(String seat_col) {
		this.seat_col = seat_col;
	}
	public int getSeat_row() {
		return seat_row;
	}
	public void setSeat_row(int seat_row) {
		this.seat_row = seat_row;
	}
	public int getSeat_status() {
		return seat_status;
	}
	public void setSeat_status(int seat_status) {
		this.seat_status = seat_status;
	}
}
