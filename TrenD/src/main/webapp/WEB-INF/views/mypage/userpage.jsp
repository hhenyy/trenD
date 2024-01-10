<%--
작업자 : 정소옥
수정일자 : 2024-01-08
설명 :  작성한 게시글, 댓글을 불러와 띄워주는 페이지
        관리자일 경우 모든 게시글과 댓글을 불러온다.
        제목 클릭시 해당 게시글로 이동.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>마이페이지</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function () {
            // 초기에는 boardlist 보이고 replylist 감춤
            boardlist(1);

            $("#board").click(function () {
                // boardlist 보이고 replylist 감춤
                $("#boardbody").show();
                $("#replybody").hide();
                boardlist(1); // page 인자 추가
            });

            $("#reply").click(function () {
                // boardlist 감추고 replylist 보임
                $("#boardbody").hide();
                $("#replybody").show();
                replylist(1); // page 인자 추가
            });
        });

        // 게시판 목록
        function boardlist(page) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/mypage/boardlist/" + page,
                success: function (result) {
                    console.log(result);
                    if (result.boardlist.length === 0) {
                        // 작성된 내용이 없을 때의 처리
                        $("#boardbody").html("<tr><td colspan='5'>작성된 내용이 없습니다.</td></tr>");
                        $("#pagination").html("");  // 페이지네이션 숨기기
                    } else {
                        var boardContent = "<tr><th class='table-success' style='width: 15%'>머릿말</th>";
                        if (result.isAdmin) {
                            boardContent += "<th class='table-success' style='width: 20%'>작성자</th>";
                        }
                        boardContent += "<th class='table-success' style='width: " + (result.isAdmin ? "40%" : "60%") + "'>제목</th>";
                        boardContent += "<th class='table-success' style='width: 15%'>날짜</th>";
                        boardContent += "<th class='table-success' style='width: 10%'>조회수</th></tr>";
                        var content = "";  // content 변수 선언

                        $.each(result.boardlist, function (index, item) {
                            var linkUrl = ""; // 동적 URL을 저장할 변수
                            if (item.categoryVO && item.categoryVO.cateCd) {
                                linkUrl = '/post?trNo=' + item.trNo;
                            }
                            content += "<tr><td>" + (item.categoryVO ? item.categoryVO.cateNm : 'N/A') + "</td>";
                            if (result.isAdmin) {
                                content += "<td>" + item.userVO.userName + "(" + item.userVO.userId + ")" + "</td>";
                            }
                            // 클릭 이벤트 추가
                            content += "<td><a href='javascript:boardcontent(" + item.trNo + "," + page + ",\"" + linkUrl + "\")'>" + item.trSubject + "</a></td>";
                            content += "<td>" + formatDateTime(item.trDate) + "</td>";
                            content += "<td>" + item.trReadCount + "</td></tr>";
                        });
                        $("#replybody").hide();  // replybody 감추기
                        $("#boardbody").show();  // boardbody 보이기
                        $("#boardbody").html(boardContent + content);  // content를 boardContent에 추가

                        // 페이지네이션 업데이트
                        updatePagination(result.pageCount, page, 'boardlist');
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }

        // 클릭했을 경우의 함수
        function boardcontent(trNo, page, linkUrl) {
            $.ajax({
                type: "GET",
                url: linkUrl, // 선택한 글의 URL
                success: function (result) {
                    console.log(result);
                    // 페이지 이동
                    window.location.href = linkUrl;
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
                    if (result.replylist.length === 0) {
                        // 작성된 내용이 없을 때의 처리
                        $("#replybody").html("<tr><td colspan='3'>작성된 내용이 없습니다.</td></tr>");
                        $("#pagination").html("");  // 페이지네이션 숨기기
                    } else {
                        var replyContent = "<tr><th class='table-success'>내용</th>";
                        if (result.isAdmin) {
                            replyContent += "<th class='table-success'>작성자</th>";
                        }
                        replyContent += "<th class='table-success'>작성일</th></tr>";
                        $.each(result.replylist, function (index, item) {
                            var linkUrl = ""; // 동적 URL을 저장할 변수
                            if (item.categoryVO && item.categoryVO.cateCd) {
                                // 트랜드 게시판
                                if (item.categoryVO.cateCd === 't') {
                                    linkUrl = '/trendPost?trNo=' + item.trNo;
                                } else {
                                    // 커뮤니티 게시판
                                    linkUrl = '/commPost?trNo=' + item.trNo;
                                }
                            }
                            // item.trReContent가 null 또는 undefined인 경우에 대한 처리
                            var trReContent = item.trReContent != null ? item.trReContent : 'N/A';

                            // 클릭 시에 cateCd 값을 전달
                            replyContent += "<tr><td><a href='javascript:replycontent(" + item.trNo + "," + page + ",\"" + item.cateCd + "\")'>" + trReContent + "</a></td>";
                            if (result.isAdmin) {
                                replyContent += "<td>" + item.userVO.userName + "(" + item.userVO.userId + ")" + "</td>";
                            }
                            replyContent += "<td>" + formatDateTime(item.trReDate) + "</td></tr>";
                        });
                        $("#boardbody").hide();  // boardbody 감추기
                        $("#replybody").show();  // replybody 보이기
                        $("#replybody").html(replyContent);  // replybody를 업데이트

                        // 페이지네이션 업데이트
                        updatePagination(result.pageCount, page, 'replylist');
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }

        // 클릭했을 경우의 함수
        function replycontent(trNo, page, cateCd) {
            var linkUrl = "/";
            linkUrl += 'post?trNo=' + trNo;

            $.ajax({
                type: "GET",
                url: linkUrl,
                success: function (result) {
                    console.log(result);
                    // 페이지 이동
                    window.location.href = linkUrl;
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }





        // 페이지네이션 업데이트 함수
        function updatePagination(totalPages, currentPage, listType) {
            var paginationHtml = '<nav aria-label="Page navigation"><ul class="pagination">';

            // 이전 버튼
            var prevPage = currentPage - 10;
            if (prevPage < 1) {
                prevPage = 1;
            }
            paginationHtml += '<li class="page-item ' + (currentPage === 1 ? 'disabled' : '') + '"><a class="page-link" href="javascript:' + listType + '(' + prevPage + ')">이전</a></li>';

            // 페이지 숫자 버튼
            for (var i = 1; i <= totalPages; i++) {
                var activeClass = i === currentPage ? 'active' : '';
                paginationHtml += '<li class="page-item ' + activeClass + '"><a class="page-link" href="javascript:' + listType + '(' + i + ')">' + i + '</a></li>';
            }

            // 다음 버튼
            var nextPage = currentPage + 10;
            if (nextPage > totalPages) {
                nextPage = totalPages;
            }
            paginationHtml += '<li class="page-item ' + (currentPage === totalPages ? 'disabled' : '') + '"><a class="page-link" href="javascript:' + listType + '(' + nextPage + ')">다음</a></li>';

            paginationHtml += '</ul></nav>';
            $("#pagination").html(paginationHtml);
        }

        // 날짜 년-월-일 시:분 으로 출력하기
        function formatDateTime(dateTimeString) {
            var date = new Date(dateTimeString);
            var year = date.getFullYear();
            var month = ("0" + (date.getMonth() + 1)).slice(-2);
            var day = ("0" + date.getDate()).slice(-2);
            var hours = ("0" + date.getHours()).slice(-2);
            var minutes = ("0" + date.getMinutes()).slice(-2);

            return year + "-" + month + "-" + day + " " + hours + ":" + minutes;
        }

    </script>
    <style>
        #pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .btn-group {
            width: 100%;
            text-align: center;
            margin: auto;
        }

        .btn {
            width: 50%;
        }

        /* 댓글 테이블의 열 너비 조정 */
        #replybody th, #replybody td {
            text-align: center;
        }
        /* 내용 열의 너비 */
        #replybody th:nth-child(1), #replybody td:nth-child(1) {
            width: 60%;
        }
        /* 작성자 열의 너비 */
        #replybody th:nth-child(2), #replybody td:nth-child(2) {
            width: 20%;
        }
        /* 작성일 열의 너비 */
        #replybody th:nth-child(3), #replybody td:nth-child(3) {
            width: 20%;
        }
    </style>
    <jsp:include page="../include/metalink.jsp"/>
</head>
<body>
<%-- header --%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sidebar.jsp" %>

<main id="main" class="main">

    <div class="btn-group d-flex mt-3 mb-4" role="group" aria-label="Basic outlined example">
        <button type="button" class="btn btn-success" id="board" onclick="boardlist(1);">게시글</button>
        <button type="button" class="btn btn-success" id="reply" value="댓글" onclick="replylist(1);">댓글</button>
    </div>
    <table align="center" width="800px" class="table table-hover">
        <%-- 트랜드 글목록 --%>
        <tbody id="boardbody"></tbody>
        <%-- 트랜드 댓글목록 --%>
        <tbody id="replybody"></tbody>
    </table>
    <!-- 페이지네이션 -->
    <div id="pagination"></div>
</main>


<%-- footer --%>
<%@ include file="../include/footer.jsp" %>
</body>
</html>