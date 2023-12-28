<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <title>커뮤니티 글 작성</title>



</head>
<body>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sidebar.jsp" %>
<main id="main" class="main">


    <form action="commInsert" method="post" id="commForm">

        <div align="center">


            <div class="col-sm-10">
                <select class="form-select" aria-label="Default select example" name="cateCd">
                    <option selected>카테고리를 선택하세요</option>
                    <c:forEach var="c" items="${categoryList}">
                        <option value="${c.cateCd}">${c.cateNm}</option>
                    </c:forEach>
                </select>
            </div>


            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="subject" name="trSubject" placeholder="제목">
                <label for="subject">제목</label>
            </div>
            <div class="form-floating mb-3">
            <textarea class="form-control" placeholder="내용" id="content" name="trContent"
                      style="height: 100px;"></textarea>
                <label for="content">내용</label>
            </div>
            <div>
                <button type="submit" class="btn btn-success rounded-pill" onclick="submitForm()">작성완료</button>
                <button type="button" class="btn btn-light rounded-pill" onclick="cancelForm()">취소</button>
            </div>

            <script>
                function submitForm() {
                        alert('글 저장이 완료되었습니다.');
                   }

                function cancelForm() {
                    var cancel = confirm('글 작성을 취소하시겠습니까?');
                    if (cancel) {
                        window.history.back();
                    }
                }

            </script>

        </div>

    </form>


</main>


<%@ include file="../include/footer.jsp" %>

</body>
</html>
