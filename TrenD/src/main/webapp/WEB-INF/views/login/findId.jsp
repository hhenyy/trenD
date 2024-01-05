<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2023-12-31
  Time: 오후 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>아이디 찾기</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        function check() {
            if ($.trim($("#userName").val()) == "") {
                alert("성명을 입력하세요.");
                $("#userName").val("").focus();
                return false;
            }
            if ($.trim($("#userEmail").val()) == "") {
                alert("이메일을 입력하세요.");
                $("#userEmail").val("").focus();
                return false;
            }
        }
    </script>
    <jsp:include page="../include/metalink.jsp"/>
</head>
<jsp:include page="../include/header.jsp"/>
<body class="d-flex align-items-center py-4 bg-body-tertiary">
<main class="form-findid w-500 m-auto">
    <c:if test="${empty findId}">
        <form method="post" action="findIdCheck" onsubmit="return check()">

            <br>
            <h1 class="h4 mb-3" align="center">
                <br> <b>트렌D</b>&nbsp;아이디 찾기
            </h1>
            <br>
            <div class="form-floating">
                <input type="text" class="form-control" id="userName"
                       name="userName" placeholder="name"> <label for="userName">성명</label>
            </div>
            <div class="form-floating">
                <input type="text" class="form-control" id="userEmail"
                       name="userEmail" placeholder="email"> <label
                    for="userEmail">이메일</label>
            </div>

            <button class="btn w-100 pr-100 fw-bold" type="submit">아이디
                찾기</button>
            <br>
            <br>
            <p class="mt-4 mb-3 text-body-secondary" align="center"
               style="font-size: 90%">
                비밀번호가 기억나지 않으신가요?<br> <a href="findPw" style="color: gray">비밀번호
                찾기</a><br>
            </p>
        </form>
    </c:if>

    <c:if test="${!empty findId}">
        <h1 class="h4 mb-3 fw-bold" align="center">아이디 찾기 결과</h1>
        <tr>
            <th>회원님의 아이디는</th>
            <td>${findId}입니다.</td>
        </tr>
        <br>
        <br>
        <button class="btn w-100 pr-100 fw-bold" type="button"
                onclick="location.href='loginform'">확인</button>
        <br>
        <br>
        <p class="mt-4 mb-3 text-body-secondary" align="center"
           style="font-size: 90%">
            비밀번호가 기억나지 않으신가요?<br> <a href="findPw" style="color: gray">비밀번호
            찾기</a><br>
        </p>
    </c:if>

    <p class="mt-5 mb-3 text-body-secondary" align="center"
       style="font-size: 80%">&copy; 2023. 1조</p>

</main>
</body>
</html>
