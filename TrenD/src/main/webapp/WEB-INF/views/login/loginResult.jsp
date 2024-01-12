<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2023-12-28
  Time: 오후 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${result == 1}">
    <script>
        alert("존재하지 않는 회원 입니다.");
        history.go(-1);
    </script>
</c:if>

<c:if test="${result == 2}">
    <script>
        alert("탈퇴한 계정입니다.");
        history.go(-1);
    </script>
</c:if>

<c:if test="${result == 3}">
    <script>
        alert("회원 정보가 일치하지 않습니다.");
        history.go(-1);
    </script>
</c:if>
