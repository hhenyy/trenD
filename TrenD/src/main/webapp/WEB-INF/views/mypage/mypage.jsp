<%--
  Created by IntelliJ IDEA.
  User: nasoo
  Date: 2023-12-21
  Time: 오후 5:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>마이페이지</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="${pageContext.request.contextPath}/assets/img/favicon.png" rel="icon">
    <link href="${pageContext.request.contextPath}/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet">

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

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function () {

            // 초기 버튼 설정
            $("#board").show();       // 내가 적은 게시글
            $("#reply").show();        // 내가 적은 댓글


            $("#board").click(function () {
                boardlist(1);
            });

            $("#reply").click(function () {
                replylist(1);
            });

        });

        // 게시판 목록
        function boardlist(page) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/mypage/boardlist/" + page,
                success: function (result) {
                    console.log(result);
                    if (result.content.length === 0) {
                        // 작성된 내용이 없을 때의 처리
                        $("#boardbody").html("<tr><td colspan='4'>작성된 내용이 없습니다.</td></tr>");
                        $("#pagination").html("");  // 페이지네이션 숨기기
                    } else {
                        var boardContent = "<tr><th>머릿말</th><th>제목</th><th>날짜</th><th>조회수</th></tr>";
                        var content = "";  // content 변수 선언

                        $.each(result.content, function (index, item) {
                            content += "<tr><td>" + (item.category ? item.category.cateNm : 'N/A') + "</td>"
                            content += "<td><a href='javascript:boardcontent(" + item.trNo + "," + page + ")'>" + item.trSubject + "</a></td>"
                            content += "<td>" + item.trDate + "</td>"
                            content += "<td>" + item.trReadCount + "</td></tr>"
                        });
                        $("#replybody").hide();  // replybody 감추기
                        $("#boardbody").show();  // boardbody 보이기
                        $("#boardbody").html(boardContent + content);  // content를 boardContent에 추가

                        // 페이지네이션 업데이트
                        updatePagination(result.totalPages, page, 'boardlist');
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }

        // 댓글 목록
        function replylist(page) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/mypage/replylist/" + page,
                success: function (result) {
                    console.log(result);

                    if (result.content.length === 0) {
                        // 작성된 내용이 없을 때의 처리
                        $("#replybody").html("<tr><td colspan='2'>작성된 내용이 없습니다.</td></tr>");
                        $("#pagination").html("");  // 페이지네이션 숨기기
                    } else {
                        var replyContent = "<tr><th>내용</th><th>작성일</th></tr>";
                        $.each(result.content, function (index, item) {
                            replyContent += "<tr><td><a href='javascript:boardcontent(" + item.trNo + "," + page + ")'>" + item.trend.trContent + "</a></td>";
                            replyContent += "<td>" + item.trReDate + "</td></tr>";
                        });
                        $("#boardbody").hide();  // boardbody 감추기
                        $("#replybody").show();  // replybody 보이기
                        $("#replybody").html(replyContent);  // replybody를 업데이트

                        // 페이지네이션 업데이트
                        updatePagination(result.totalPages, page, 'replylist');
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }

        // 페이지네이션 업데이트 함수
        function updatePagination(totalPages, currentPage, listType) {
            var paginationHtml = '<nav aria-label="Page navigation"><ul class="pagination">';

            for (var i = 1; i <= totalPages; i++) {
                var activeClass = i === currentPage ? 'active' : '';
                paginationHtml += '<li class="page-item ' + activeClass + '"><a class="page-link" href="javascript:' + listType + '(' + i + ')">' + i + '</a></li>';
            }

            paginationHtml += '</ul></nav>';
            $("#pagination").html(paginationHtml);
        }


        //글삭제
        function mydelete(no, page) {
            alert(no);
            alert(page);
        }
    </script>
</head>
<body>
<%-- header --%>
<%@ include file="../include/header.jsp" %>

<main id="main" class="main">
    <div>
        <input type="button" id="board" value="게시글">
        <input type="button" id="reply" value="댓글">
    </div>
    <table align="center" width=800 class="table table-hover">
        <%-- 트랜드 글목록 --%>
        <tbody id="boardbody"></tbody>
        <%-- 트랜드 댓글목록 --%>
        <tbody id="replybody"></tbody>
    </table>
    <!-- 페이지네이션 -->
    <div id="pagination"></div>


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

<%-- footer --%>
<%@ include file="../include/footer.jsp" %>
</body>
</html>