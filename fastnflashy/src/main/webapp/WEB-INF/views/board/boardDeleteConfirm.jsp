<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Random" %>
<%
    Random random = new Random();
    int randomNumber = 1000 + random.nextInt(9000); // 1000에서 9999 사이의 난수 생성
    session.setAttribute("randomNumber", randomNumber);
%>

<script>
   function validateForm() {
        var confirmationCode = document.forms["deleteForm"]["confirmation_code"].value;
        if (confirmationCode == null || confirmationCode.trim() == "") {
            alert("코드를 입력해야 합니다.");
            return false;
        }
        
        return true;
        alert("삭제 처리 되었습니다");
    }
</script>

<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">

<div class="page-main">
<form action="boardDelete.do" method="post" name="deleteForm" onsubmit="return validateForm();">
    <div class="content-main">
    <input type="hidden" name="board_num" value="${board_num}">
    <p>삭제 확인 코드: <%= randomNumber %></p>
    <input type="text" name="confirmation_code" placeholder="코드 입력" />
    <input type="submit" value="삭제 확인" />
    </div>
</form>
</div>