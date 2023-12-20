<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 예매</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/booking.css">
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<jsp:include page="/WEB-INF/views/booking/bookingHeader.jsp" />

		<div class="content-main">
		<table>
		<tr>
			<td>예매일</td>
			<td>예약번호</td>
			<td>경기명</td>
			<td>경기일</td>
			<td>취소가능일</td>
			<td>현재상태</td>
		</tr>
<%@ page import="java.util.List" %>
<%@ page import="kr.booking.vo.BookedInfoVO" %>
<%@ page import="kr.schedule.vo.ScheduleVO" %>
<%
List<BookedInfoVO> list1 = (List<BookedInfoVO>)request.getAttribute("list1");
List<ScheduleVO> list2 = (List<ScheduleVO>)request.getAttribute("list2");

	for(int i=0;i<list1.size();i++){
	BookedInfoVO book = list1.get(i);
	ScheduleVO schedule = list2.get(i);
%>
	
	<tr>
		<td><%= book.getBooked_regdate() %></td>
		<td><a href="${pageContext.request.contextPath}/booking/bookingDetail.do?booked_num=<%=book.getBooked_num()%>&booked_package=<%=book.getBooked_package()%>"><%= book.getBooked_num() %></a></td>
		<td><%= schedule.getTeam1_name()%> VS <%= schedule.getTeam2_name()%></td>
		<td><%= schedule.getSchedule_start() %>(<%=book.getCount_of_book() %>매)</td>
		<td><%= schedule.getSchedule_start() %>-4시간</td>
		<% if(book.getSeat_status()==1){%><td>예매</td><%}%>
		<% if(book.getSeat_status()==0){%><td>취소</td><%}%>
		
	</tr>
	
<%
	}

%>
		</table>

		</div>
	</div>

</body>
</html>