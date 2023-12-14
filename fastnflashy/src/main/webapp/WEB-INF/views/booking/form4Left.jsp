<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="form4">
	<p>결제</p>
	<hr	style="border: none; height: 3px; background-color: black; width: 80%;">
	<div>
		<table>
			<tr>
				<td>내 충전금</td>
				<td><span id="current_balance">${member.mem_balance}</span>원</td>
			</tr>
			<tr>
				<td>결제금액</td>
				<td><span class="all-total"></span></td>
			</tr>
			<tr>
				<td>잔액</td>
				<td><span id="predicted_balance"></span></td>
			</tr>
		</table>
	</div>
</div>