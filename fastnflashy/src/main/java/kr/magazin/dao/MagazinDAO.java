package kr.magazin.dao;

public class MagazinDAO {
	//싱글턴 패턴
	private static MagazinDAO instance = new MagazinDAO();
	
	public static MagazinDAO getInstance() {
		return instance;
	}
	
	private MagazinDAO() {}
	
	//칼럼니스트 - 칼럼 등록
	//전체 레코드수/검색 레코드수
	//전체글/검색 글 목록
	//글상세
	//조회수 증가
	//파일 삭제
	//칼럼니스트 - 칼럼 수정
	//관리자 - 칼럼 삭제
	//칼럼 반응 등록
	//칼럼 반응 개수
	//댓글 등록
	//댓글 개수
	//댓글 목록
	//댓글 상세
	//댓글 수정
	//댓글 삭제
	
}
