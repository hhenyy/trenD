<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>


    <title>TrenD</title>

    <jsp:include page="../include/metalink.jsp"/>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>

<main id="main" class="main">


    <form action="commUpdate?trNo=${post.trNo}" method="post" id="commForm">
        <input type="hidden" name = "trNo" value = "${post.trNo}">

        <div align="center">


            <div class="col-sm-10">
                <select class="form-select" aria-label="Default select example" name="cateCd">

                    <c:forEach var="c" items="${categoryList}">
                        <c:if test="${post.cateCd.equals(c.cateCd)}">
                            <option selected value="${post.cateCd}">${post.categoryVO.cateNm}</option>
                        </c:if>
                        <c:if test="${!post.cateCd.equals(c.cateCd)}">
                            <option value="${c.cateCd}">${c.cateNm}</option>
                        </c:if>

                    </c:forEach>
                </select>
            </div>


            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="subject" name="trSubject" placeholder="제목"
                       value="${post.trSubject}">
                <label for="subject">제목</label>
            </div>
            <div class="form-floating mb-3">
            <textarea class="form-control" placeholder="내용" id="content" name="trContent"
                      style="height: 100px;">${post.trContent}</textarea>
                <label for="content">내용</label>
            </div>
            <div>
                <button type="submit" class="btn btn-success rounded-pill" onclick="submitForm()">수정완료</button>
                <button type="button" class="btn btn-light rounded-pill" onclick="cancelForm()">취소</button>
            </div>

            <script>
                function submitForm() {
                    alert('글 수정이 완료되었습니다.');
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


<jsp:include page="../include/footer.jsp"/>

</body>
</html>
