package kr.member.vo;

import java.sql.Date;

public class MemberVO {
	private int mem_num;			//회원번호
	private String mem_id;			//아이디
	private int mem_auth;			//회원등급
	private String mem_name;		//이름
	private String mem_pw;			//비밀번호
	private String mem_tel;			//전화번호
	private String mem_email;		//이메일
	private String mem_zipcode;		//우편번호
	private String mem_address1;	//주소
	private String mem_address2;	//상세주소
	private Date mem_regdate;		//가입일
	private int mem_gender;			//성별
	private int mem_balance;		//잔고
	private int mem_subscription;	//구독한 칼럼리스트
	private String mem_photo;			//프로필 사진
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPassword(String userPasswd) {
		//회원등급(mem_auth) :0탈퇴회원, 1일반회원, 2칼럼리스트, 9 관리자
		if(mem_auth>0 && mem_pw.equals(userPasswd)){
			return true;
		}
		return false;
	}
	
	
	
	
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getMem_auth() {
		return mem_auth;
	}
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public String getMem_tel() {
		return mem_tel;
	}
	public void setMem_tel(String mem_tel) {
		this.mem_tel = mem_tel;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_zipcode() {
		return mem_zipcode;
	}
	public void setMem_zipcode(String mem_zipcode) {
		this.mem_zipcode = mem_zipcode;
	}
	public String getMem_address1() {
		return mem_address1;
	}
	public void setMem_address1(String mem_address1) {
		this.mem_address1 = mem_address1;
	}
	public String getMem_address2() {
		return mem_address2;
	}
	public void setMem_address2(String mem_address2) {
		this.mem_address2 = mem_address2;
	}
	public Date getMem_regdate() {
		return mem_regdate;
	}
	public void setMem_regdate(Date mem_regdate) {
		this.mem_regdate = mem_regdate;
	}
	public int getMem_gender() {
		return mem_gender;
	}
	public void setMem_gender(int mem_gender) {
		this.mem_gender = mem_gender;
	}
	public int getMem_balance() {
		return mem_balance;
	}
	public void setMem_balance(int mem_balance) {
		this.mem_balance = mem_balance;
	}
	public int getMem_subscription() {
		return mem_subscription;
	}
	public void setMem_subscription(int mem_subscription) {
		this.mem_subscription = mem_subscription;
	}



	public String getMem_photo() {
		return mem_photo;
	}



	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	
	
	
	
	
}
