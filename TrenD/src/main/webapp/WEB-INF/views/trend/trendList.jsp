<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Testing CSS</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/${pageContext.request.contextPath}/assets/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">

    <%@ include file="../include/header.jsp" %>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {
            loadTrendList(1);
        });

        // $(document).on('click', '#pagingUl li a', function(e) {
        //     e.preventDefault();
        //     var page = $(this).data('page');
        //     loadTrendList(page);
        // });

        function loadTrendList(page) {
            $.ajax({
                url: "/api/trend/list/" + page,
                type: "GET",
                success: function(data) {
                    displayTrendList(data);
                },
                error: function() {
                    alert("데이터를 불러오는 중에 오류가 발생했습니다.");
                }
            });
        }

        function formatDate(dateString) {
            var date = new Date(dateString);
            var options = { year: 'numeric', month: '2-digit', day: '2-digit', weekday: 'long' };
            return date.toLocaleDateString('ko-KR', options);
        }

        function displayTrendList(data) {
            $('#trendTableBody').empty();
            $('#pagingUl').empty();
            var no =  data.listCount-(data.page-1) * 10;
            var row='';

            $.each(data.trendList, function(index, trend) {

                row = '<tr>' +
                    '<td>' + no-- + '</td>' +
                    '<td><a href="#">' + trend.trSubject + '</a></td>' +
                    '<td>' + formatDate(trend.trDate) + '</td>' +
                    '<td>' + trend.trReadCount + '</td>' +
                    '</tr>';
                $('#trendTableBody').append(row);

            });

            // Add Previous Button
            if (data.page > 10) {
                var prevPage = data.page - 10;
                var prevButton = '<a href="javascript:loadTrendList(' + prevPage + ')" data-page="' + prevPage + '">이전</a>';
                $('#pagingUl').append(prevButton);
            }

            // Add Page Numbers
            $.each(Array.from({ length: data.pageCount }, (_, i) => i + 1), function(index, pageNumber) {
                var li = '<a href="javascript:loadTrendList(' + pageNumber + ')" data-page="' + pageNumber + '">' + pageNumber + '</a>';
                $('#pagingUl').append(li);
            });

            // Add Next Button
            if (data.page + 10 <= data.pageCount) {
                var nextPage = data.page + 10;
                var nextButton = '<a href="javascript:loadTrendList(' + nextPage + ')" data-page="' + nextPage + '">다음</a>';
                $('#pagingUl').append(nextButton);
            }
        }
    </script>

</head>

<body>

<main id="main" class="main">

    <h2>Trend List</h2>

    <!-- 게시물 목록 테이블 -->
    <table id="trendTable" align="center" border="1">
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성일</th>
            <th>조회수</th>
        </tr>
        </thead>
        <tbody id="trendTableBody">
        <!-- 데이터가 여기에 추가될 것입니다. -->
        </tbody>
    </table>

    <!-- 페이징 -->
    <div id="pagingDiv" align="center">
        <ul id="pagingUl">
            <!-- 페이징이 여기에 추가될 것입니다. -->
        </ul>
    </div>

</main>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/chart.js/chart.umd.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/echarts/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/quill/quill.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/tinymce/tinymce.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

<%@ include file="../include/footer.jsp" %>

</body>
</html>
