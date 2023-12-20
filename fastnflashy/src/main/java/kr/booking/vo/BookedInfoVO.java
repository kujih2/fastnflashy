package kr.booking.vo;

import java.sql.Date;

public class BookedInfoVO {
	private int schedule_num;		//각 경기를 식별하는 번호
	private int seat_id;			//예매시 부여되는 고유 좌석번호
	private String seat_col;		//절대적 좌석 행번호
	private int seat_row;			//절대적 좌석 열번호
	private int seat_status;		//좌석 예매 상태 0취소 1예매됨
	private int booked_num;			//고유 예매 번호
	private int booked_package;		//여러장 구매한경우 같은 구매상품으로 묶기위한 식별번호
	private int user_num;			//회원번호
	private Date booked_regdate;	//예매일
	private String booked_name;		//예매자 이름
	private String booked_email;	//예매자 이메일
	private String booked_ip;		//예매자 IP
	private int booked_price;		//표가격
	private int count_of_book;		//예매한 표 개수
	
	
	public int getCount_of_book() {
		return count_of_book;
	}
	public void setCount_of_book(int count_of_book) {
		this.count_of_book = count_of_book;
	}
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
	public int getBooked_num() {
		return booked_num;
	}
	public void setBooked_num(int booked_num) {
		this.booked_num = booked_num;
	}
	public int getBooked_package() {
		return booked_package;
	}
	public void setBooked_package(int booked_package) {
		this.booked_package = booked_package;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public Date getBooked_regdate() {
		return booked_regdate;
	}
	public void setBooked_regdate(Date booked_regdate) {
		this.booked_regdate = booked_regdate;
	}
	public String getBooked_name() {
		return booked_name;
	}
	public void setBooked_name(String booked_name) {
		this.booked_name = booked_name;
	}
	public String getBooked_email() {
		return booked_email;
	}
	public void setBooked_email(String booked_email) {
		this.booked_email = booked_email;
	}
	public String getBooked_ip() {
		return booked_ip;
	}
	public void setBooked_ip(String booked_ip) {
		this.booked_ip = booked_ip;
	}
	public int getBooked_price() {
		return booked_price;
	}
	public void setBooked_price(int booked_price) {
		this.booked_price = booked_price;
	}
	
	
}
