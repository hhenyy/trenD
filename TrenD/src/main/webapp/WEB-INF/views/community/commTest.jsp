<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>커뮤니티 작성 폼</title>
    <jsp:include page="../include/metalink.jsp"/>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>


<main id="main" class="main">

<a href = commForm>글 작성 폼</a>
    <a href="searchTest">검색 페이지 테스트</a>

<table>
    <tr>
        <td>trNO</td>
        <td>userId</td>
        <td>cateCd</td>
        <td>trSubject</td>
        <td>trContent</td>
        <td>trReadCount</td>
        <td>trDate</td>
        <td>trUpdate</td>
        <td>trDelYn</td>

    </tr>
<c:forEach var = "list" items="${commList}">
    <tr>
        <td>${list.trNo}</td>
        <td>${list.userVO.userName}</td>
        <td>${list.cateCd}</td>
        <td><a href = "/post?trNo=${list.trNo}">${list.trSubject}</a></td>
        <td>${list.trContent}</td>
        <td>${list.trReadCount}</td>
        <td>${list.trDate}</td>
        <td>${list.trUpdate}</td>
        <td>${list.trDelYn}</td>

    </tr>

</c:forEach>


</table>


</main>
<jsp:include page="../include/footer.jsp"/>


</body>

</html>
