package kr.booking.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.booking.dao.BookingDAO;
import kr.booking.vo.BookedInfoVO;
import kr.controller.Action;

public class BookingAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
			
		}
		//전송된 데이터 인코딩처리
		request.setCharacterEncoding("utf-8");
		
		List<BookedInfoVO> list = new ArrayList<BookedInfoVO>();
		
		int seatOfNum = Integer.parseInt(request.getParameter("num_of_seat"));
		System.out.println("seatOfNum : " + seatOfNum);
		String[] colNames = request.getParameterValues("colName");
		System.out.println("colNames : " + colNames.length);
	    String[] rowNameValues = request.getParameterValues("rowName");
	    System.out.println("rowNameValues : " + rowNameValues.length);
	    int[] rowNames = new int[rowNameValues.length];
	    if(rowNameValues !=null) {
	    	for(int i=0;i <rowNameValues.length;i++) {
	    		rowNames[i] = Integer.parseInt(rowNameValues[i]);
	    	}
	    	
	    }
	    
	    String[] ticketTypeValues = request.getParameterValues("ticket-type");
	    
	    int[] ticketTypes = new int[ticketTypeValues.length];
	    if (ticketTypeValues != null) {
	    	for(int i=0;i<ticketTypeValues.length;i++) {
	    		ticketTypes[i] = Integer.parseInt(ticketTypeValues[i]);
	    	}
	    }
	    
	    int[] prices = new int[ticketTypes[0]+ticketTypes[1]+ticketTypes[2]];
	    int pIdx = 0;
	    for(int i=0;i<ticketTypes.length;i++) {
	    	for(int j=0;j<ticketTypes[i];j++) {
	    		if(i==0) {
	    			prices[pIdx] = 12000;
	    			pIdx++;
	    		}else if(i==1) {
	    			prices[pIdx] = 6000;
	    			pIdx++;
	    		}else if(i==2) {
	    			prices[pIdx] = 1500;
	    			pIdx++;
	    		}
	    	}
	    	
	    }
	    //tickettypes[0] : 성인   0
	    //tickettypes[1] : 청소년	 2
	    //tickettypes[2] : 어린이  1
	    //가격들을 배열에 따로 저장하고 다시 가져오기
	    //위의경우 {6000,6000,1500}이 되는것 ㅋㅋ
	    
		for(int i=0;i<seatOfNum;i++) {
			BookedInfoVO vo = new BookedInfoVO();
			vo.setSchedule_num(Integer.parseInt(request.getParameter("schedule_num")));
			vo.setSeat_col(colNames[i]);
			vo.setSeat_row(rowNames[i]);
			vo.setUser_num(user_num);
			vo.setBooked_name(request.getParameter("name"+(i+1)));
			vo.setBooked_email(request.getParameter("email"+(i+1)));
			vo.setBooked_ip(request.getRemoteAddr());
			vo.setBooked_price(prices[i]);
			list.add(vo);
		}
		System.out.println(list.size());
		BookingDAO dao = BookingDAO.getInstance();
		dao.insertBooked(list, Integer.parseInt(request.getParameter("schedule_num")));
		
		
		
		
		return "/WEB-INF/views/booking/booking.jsp";
	}

}
