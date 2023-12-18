package kr.board.vo;

public class BoardLikeVO {
	private int board_num;
	private int mem_num;
	private int like_status;
	
	public BoardLikeVO() {}
	     
	public BoardLikeVO(int board_num, int mem_num) {
		this.board_num = board_num;
		this.mem_num = mem_num;
	}

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public int getLike_status() {
		return like_status;
	}

	public void setLike_status(int like_status) {
		this.like_status = like_status;
	}
	

}
