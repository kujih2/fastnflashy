<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Random" %>
<%
    Random random = new Random();
    int randomNumber = 1000 + random.nextInt(9000); // 1000에서 9999 사이의 난수 생성
    request.setAttribute("randomNumber", randomNumber);
%>

<script>
    function validateForm() {
        var confirmationCode = document.forms["deleteForm"]["confirmation_code"].value;
        if (confirmationCode == null || confirmationCode.trim() == "") {
            alert("코드를 입력해야 합니다.");
            return false;
        }
        return true;
    }
</script>



<form action="boardDelete.do" method="post" name="deleteForm">
    <input type="hidden" name="user_num" value="${board.mem_num}" />
    <p>삭제 확인 코드: <%= randomNumber %></p>
    <input type="text" name="confirmation_code" placeholder="코드 입력" />
    <input type="submit" value="삭제 확인" />
    
</form>
