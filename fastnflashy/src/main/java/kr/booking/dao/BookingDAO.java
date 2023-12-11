package kr.booking.dao;

public class BookingDAO {

	private static BookingDAO instance = new BookingDAO();
	public static BookingDAO getInstance() {
		return instance;
	}
	private BookingDAO() {}
	
	
}
