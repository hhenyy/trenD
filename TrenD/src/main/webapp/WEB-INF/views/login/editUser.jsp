<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2023-12-30
  Time: 오후 4:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>회원정보 수정</title>

<%--    <script src="./js/user/edit.js"></script>--%>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <jsp:include page="../include/metalink.jsp"/>
</head>


<body class="d-flex align-items-center py-4 bg-body-tertiary">
<jsp:include page="../include/header.jsp"/>


<main class="form-joinin w-1000 m-auto">

    <form name="updateUserform" method="post" action="updateUser"
          onsubmit="return editcheck()">
        <input type="hidden" value="${user.userPw}" name = "userPw">
        <input type="hidden" value="${user.userDelYn}" name = "userDelYn">
        <br>
        <br>

        <h2 class="h6 fw-bold" align="center">필수 입력 항목</h2>
        <hr>

        <div class="row mb-3">
            <label for="userId" class="form-label mb-0" align="left">아이디</label>
            <div class="row mb-0 g-1 align-items-center">
                <div class="col-12 mb-1">
                    <input type="text" class="form-control" id="userId" name="userId" value="${user.userId}"
                           aria-describedby="idGuide" placeholder="6~12자 영대소문자, 숫자 이용" readonly
                           minlength="3" maxlength="12" >
                </div>
            </div>
        </div>

        <div class="row mb-3">
            <button class="btn btn-outline-dark w-100 pr-100 fw-bold" type="button"
                    onclick="location.href='editPw';">비밀번호 변경하기</button>
        </div>

        <div class="row mb-3">
            <label for="userName" class="form-label mb-0" align="left">닉네임</label>
            <div class="row mb-0 g-1 align-items-center">
                <div class="col-12 mb-1">
                    <input type="text" class="form-control" id="userName" name="userName" value="${user.userName}"
                           aria-describedby="nickNameGuide" placeholder="2~8자 한글 영어, 숫자 이용" readonly
                           maxlength="12">
                </div>

            </div>
        </div>

        <div class="row mb-3">
            <label for="userAge" class="form-label" align="left">나이</label>
            <select class="form-select" id="userAge" name="ageCd">
                <c:forEach var="age" items="${ageList}">
                    <c:if test="${age.ageCd eq user.ageVO.ageCd}">
                        <option value="${age.ageCd}" selected>${age.ageNm}</option>
                    </c:if>
                    <c:if test="${age.ageCd ne user.ageVO.ageCd}">
                        <option value="${age.ageCd}">${age.ageNm}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <%--    <div class="form-text mb-2" id="phoneGuide" align="left"></div>--%>

        <div class="row mb-3">
            <label for="userGender" class="form-label" align="left">성별</label>
            <select class="form-select" id="userGender" name="genCd">
                <c:forEach var="gender" items="${genderList}">
                    <c:if test="${gender.genCd eq user.genderVO.genCd}">
                        <option value="${gender.genCd}" selected>${gender.genNm}</option>
                    </c:if>
                    <c:if test="${gender.genCd ne user.genderVO.genCd}">
                        <option value="${gender.genCd}">${gender.genNm}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="row mb-3">
            <label for="userLoc" class="form-label" align="left">지역</label>
            <select class="form-select" id="userLoc" name="locCd">
                <c:forEach var="loc" items="${locList}">
                    <c:if test="${loc.locCd eq user.locationVO.locCd}">
                        <option value="${loc.locCd}" selected>${loc.locNm}</option>
                    </c:if>
                    <c:if test="${loc.locCd ne user.locationVO.locCd}">
                        <option value="${loc.locCd}">${loc.locNm}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="row mb-3">
            <label for="userEmail" class="form-label" align="left">이메일</label> <input
                type="text" class="form-control" id="userEmail" name="userEmail" value="${user.userEmail}"
                placeholder="email@example.com" readonly>
        </div>

        <br>
        <button class="btn btn-outline-dark w-100 pr-100 fw-bold" type="submit">수정</button>

        <p class="mt-5 mb-3 text-body-secondary" align="center"
           style="font-size: 90%">
            계정 삭제를 원하시나요?&nbsp;<a href="delUser" style="color: gray">회원탈퇴</a>
        </p>

        <p class="mt-2 mb-3 text-body-secondary" align="center"
           style="font-size: 80%">&copy; 2023. 1조</p>
    </form>
    <%--<%@ include file="../include/footer.jsp" %>--%>
</main>

</body>
</html>