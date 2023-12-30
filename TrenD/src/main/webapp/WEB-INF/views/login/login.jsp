<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2023-12-21
  Time: 오후 2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <title>로그인</title>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        function check() {
            if ($.trim($("#userId").val()) == "") {
                alert("아이디를 입력하세요.");
                $("#userId").val("").focus();
                return false;
            }
            if ($.trim($("#userPw").val()) == "") {
                alert("비밀번호를 입력하세요.");
                $("#userPw").val("").focus();
                return false;
            }
        }
    </script>



</head>

<body class="d-flex align-items-center justify-content-center min-vh-100 py-4 bg-body-tertiary">
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sidebar.jsp" %>
<%--<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>--%>
<main class="form-signin w-500 m-auto">
<main class="main">
    <form method="post" action="checkLogin" onsubmit="return check()">
<%--        <input type="hidden" name="login_ok" value="1" />--%>
        <%--<div id="logo">
            <img src="./images/logo.png" alt="Logo" width="100px" height="40px"
                 onclick="location.href='home.do'" style="cursor: pointer;">
        </div>--%>
        <br>
        <h1 class="h4 mb-3" align="center">
            댓글의 싸움터 ,<br> <b>트렌D</b>에 오신 것을 환영합니다.
        </h1>
        <br>
        <div class="form-floating">
            <input type="text" class="form-control" id="userId" name="userId"
                   placeholder="id"> <label for="userId">아이디</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="userPw"
                   name="userPw" placeholder="pw"> <label for="userPw">비밀번호</label>
        </div>
        <br>
        <button class="btn w-100 pr-100 fw-bold" type="submit">로그인</button>
        <br> <br>

        <h2 class="h6 fw-bold" align="center">SNS 계정으로 로그인</h2>
        <hr>

<%--        <div class="d-flex justify-content-center column-gap-3">--%>
<%--            <!-- 네이버 로그인-->--%>
<%--            <div id="naver_id_login"></div>--%>
<%--            <script type="text/javascript">--%>

<%--                // .gitignore--%>
<%--                var naver_id_login = new naver_id_login("",--%>
<%--                    "http://localhost/obriProject/loginNaver.do");--%>

<%--                var state = naver_id_login.getUniqState();--%>
<%--                naver_id_login.setButton("green", 1, 40);--%>
<%--                naver_id_login--%>
<%--                    .setDomain("http://localhost/obriProject/login.do");--%>
<%--                naver_id_login.setState(state);--%>
<%--                //	naver_id_login.setPopup();		// 팝업 방식 해제--%>
<%--                naver_id_login.init_naver_id_login();--%>
<%--            </script>--%>

<%--            <!-- 카카오 로그인 -->--%>
<%--            <div id="kakao_id_login">--%>
<%--                <a href="javascript:loginWithKakao()"> <img--%>
<%--                        src="./images/kakao_logo.png" style="width: 40px; height: 40px;" />--%>
<%--                </a>--%>
<%--            </div>--%>
<%--        </div>--%>


        <p class="mt-4 mb-3 text-body-secondary" align="center"
           style="font-size: 90%">
            <a href="findId.do" style="color: gray">아이디 찾기</a>&nbsp;|&nbsp;<a
                href="findPw.do" style="color: gray">비밀번호 찾기</a><br>아직 회원이
            아니신가요?&nbsp;<a href="joinform" style="color: gray">회원가입</a>
        </p>

        <p class="mt-3 mb-3 text-body-secondary" align="center"
           style="font-size: 80%">&copy; 2023. 1조</p>
    </form>
</main>

</body>
</html>
