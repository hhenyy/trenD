<%--
  Created by IntelliJ IDEA.
  User: klzxc
  Date: 2023-12-22
  Time: 오후 5:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>커뮤니티 작성 폼</title>
</head>
<body>

<a href = commForm>글 작성 폼</a>

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
        <td>${list.userId}</td>
        <td>${list.cateCd}</td>
        <td><a href = "commContent?No=${list.trNo}">${list.trSubject}</a></td>
        <td>${list.trContent}</td>
        <td>${list.trReadCount}</td>
        <td>${list.trDate}</td>
        <td>${list.trUpdate}</td>
        <td>${list.trDelYn}</td>

    </tr>

</c:forEach>
</table>

</body>
</html>
