package kr.magazin.vo;

import java.sql.Date;

public class MagazinVO {
	private int mg_board_num; //칼럼 게시글 고유번호
	private String mg_title; //칼럼 제목
	private int sports_category; //매거진 카테고리
	private String mg_content; //칼럼 내용
	private int mg_hit; //칼럼 조회수
	private Date mg_reg_date; //칼럼 등록일
	private Date mg_modify_date; //칼럼 수정일
	private String mg_photo1; //이미지1
	private String mg_photo2; //이미지2
	private String mg_ip; //아이피
	private int mem_num; //회원 고유 번호
	
	private String mem_name;//회원 이름
	private String mem_email;//회원 이메일
	private String mem_photo;//회원 사진
	
	public int getMg_board_num() {
		return mg_board_num;
	}
	public void setMg_board_num(int mg_board_num) {
		this.mg_board_num = mg_board_num;
	}
	public String getMg_title() {
		return mg_title;
	}
	public void setMg_title(String mg_title) {
		this.mg_title = mg_title;
	}
	public int getSports_category() {
		return sports_category;
	}
	public void setSports_category(int sports_category) {
		this.sports_category = sports_category;
	}
	public String getMg_content() {
		return mg_content;
	}
	public void setMg_content(String mg_content) {
		this.mg_content = mg_content;
	}
	public int getMg_hit() {
		return mg_hit;
	}
	public void setMg_hit(int mg_hit) {
		this.mg_hit = mg_hit;
	}
	public Date getMg_reg_date() {
		return mg_reg_date;
	}
	public void setMg_reg_date(Date mg_reg_date) {
		this.mg_reg_date = mg_reg_date;
	}
	public Date getMg_modify_date() {
		return mg_modify_date;
	}
	public void setMg_modify_date(Date mg_modify_date) {
		this.mg_modify_date = mg_modify_date;
	}
	public String getMg_photo1() {
		return mg_photo1;
	}
	public void setMg_photo1(String mg_photo1) {
		this.mg_photo1 = mg_photo1;
	}
	public String getMg_photo2() {
		return mg_photo2;
	}
	public void setMg_photo2(String mg_photo2) {
		this.mg_photo2 = mg_photo2;
	}
	public String getMg_ip() {
		return mg_ip;
	}
	public void setMg_ip(String mg_ip) {
		this.mg_ip = mg_ip;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	
	
}
