package kr.magazin.vo;

import java.sql.Date;

public class MagazinReplyVO {
	private int mg_re_num;				//댓글번호
	private int mg_isDeleted;			//댓글 삭제여부
	private String mg_re_content;		//댓글내용
	private Date mg_re_reg_date;		//댓글 작성일
	private Date mg_re_modify_date;		//댓글 수정일
	private String mg_re_ip;			//댓글 IP
	private int mg_board_num;			//게시판 번호
	private int mem_num;				//작성자 번호
	
	
	
	public int getMg_re_num() {
		return mg_re_num;
	}
	public void setMg_re_num(int mg_re_num) {
		this.mg_re_num = mg_re_num;
	}
	public int getMg_isDeleted() {
		return mg_isDeleted;
	}
	public void setMg_isDeleted(int mg_isDeleted) {
		this.mg_isDeleted = mg_isDeleted;
	}
	public String getMg_re_content() {
		return mg_re_content;
	}
	public void setMg_re_content(String mg_re_content) {
		this.mg_re_content = mg_re_content;
	}
	public Date getMg_re_reg_date() {
		return mg_re_reg_date;
	}
	public void setMg_re_reg_date(Date mg_re_reg_date) {
		this.mg_re_reg_date = mg_re_reg_date;
	}
	public Date getMg_re_modify_date() {
		return mg_re_modify_date;
	}
	public void setMg_re_modify_date(Date mg_re_modify_date) {
		this.mg_re_modify_date = mg_re_modify_date;
	}
	public String getMg_re_ip() {
		return mg_re_ip;
	}
	public void setMg_re_ip(String mg_re_ip) {
		this.mg_re_ip = mg_re_ip;
	}
	public int getMg_board_num() {
		return mg_board_num;
	}
	public void setMg_board_num(int mg_board_num) {
		this.mg_board_num = mg_board_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
}
