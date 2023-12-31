package kr.board.vo;

import java.sql.Date;

public class BoardVO {
	private int board_num;			//게시글 번호
	private String title;			//글 제목
	private int board_category;		//게시판 카테고리 번호
	private String content;			//게시글 내용
	private int hit;				//게시글 조회수
	private int isdeleted;			//게시글 삭제여부
	private Date reg_date;			//게시글 등록일자
	private Date modify_date;		//게시글 수정일
	private String filename;		//파일명
	private String ip;				//작성자 ip주소
	private int mem_num;			//작성자 회원번호
	   
	private String mem_id;			//회원 아이디
	private String photo;			//회원 프로필 사진
	
	private int net_likes;			//좋아요 싫어요 합산 값
	
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getBoard_category() {
		return board_category;
	}
	public void setBoard_category(int board_category) {
		this.board_category = board_category;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getNet_likes() {
		return net_likes;
	}
	public void setNet_likes(int net_likes) {
		this.net_likes = net_likes;
	}
	
	
	
	
}
