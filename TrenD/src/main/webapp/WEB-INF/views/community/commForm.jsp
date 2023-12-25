<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>커뮤니티 글 작성</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="assets/img/favicon.png" rel="icon">
    <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="assets/css/style.css" rel="stylesheet">

    <%@ include file="../include/header.jsp" %>

</head>

<body>

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


<!-- Vendor JS Files -->
<script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="assets/vendor/chart.js/chart.umd.js"></script>
<script src="assets/vendor/echarts/echarts.min.js"></script>
<script src="assets/vendor/quill/quill.min.js"></script>
<script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="assets/vendor/tinymce/tinymce.min.js"></script>
<script src="assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="assets/js/main.js"></script>


<%@ include file="../include/footer.jsp" %>

</body>
</html>
