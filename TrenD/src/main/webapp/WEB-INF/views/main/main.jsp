<%--
  Created by IntelliJ IDEA.
  User: skinl
  Date: 2023-12-28
  Time: 오후 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>
<main id="main" class="main">
    사용자 ID :   ${sessionScope.userId}
    사용자 Name : ${sessionScope.userName}

</main>
<jsp:include page="../include/footer.jsp"/>

</body>

</html>
