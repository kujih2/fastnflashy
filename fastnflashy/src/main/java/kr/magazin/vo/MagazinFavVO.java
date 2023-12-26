package kr.magazin.vo;

public class MagazinFavVO {
	private int mg_board_num;
	private int mem_num;
	
	public MagazinFavVO() {}
	
	public MagazinFavVO(int mg_board_num, int mem_num) {
		this.mg_board_num = mg_board_num;
		this.mem_num = mem_num;
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
