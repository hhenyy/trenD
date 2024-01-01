<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>통합검색</title>

    <jsp:include page="../include/metalink.jsp"/>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>
<main id="main" class="main">

    <div class="pagetitle">

        <h1>통합검색</h1>
        <div class="title_left">
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">just search</li>

                </ol>
            </nav>
        </div>


    </div><!-- End Page Title -->

<!-- 통합검색(트렌드 출력 공간) -->

    <table class="table">
        <thead>
        <tr>
            <th scope="col">No</th>
            <th scope="col">제목</th>
            <th scope="col">조회수</th>
            <th scope="col">등록일</th>
        </tr>
        </thead>

        <tbody id="trendSearchResult">

        <script>
            document.addEventListener('DOMContentLoaded', function () {

                const keyword = ${keyword};

                trendSearchResult(keyword);

            });

            function trendSearchResult(keyword) {
                fetch(`/main/search/trend/?keyword=${keyword}`)
                    .then(response => response.json())
                    .then(data => {
                        const tableBody = document.querySelector('#trendSearchResult');
                        tableBody.innerHTML = ''; // 테이블 내용 초기화

                        var row = '';


                        data.forEach((trend) => {
                            var formattedDate = new Date(trend.trDate).toLocaleString();

                            row +=

                                "<tr><td>"
                                +
                                trend.trNo
                                +
                                "</td><td><a href = 'trendContent?trNo=" + trend.trNo + "'>"
                                +
                                trend.trSubject
                                +
                                "</a></td><td>"

                                +
                                trend.trReadCount
                                +
                                "</td><td>"
                                +
                                formattedDate
                                +
                                "</td></tr>";


                        });
                        tableBody.innerHTML = row;
                    })
                    .catch(error => console.error('Error fetching data:', error));
            }
        </script>

        </tbody>

    </table>

    <!-- Pagination with icons -->
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav><!-- End Pagination with icons -->

    <!-- 통합검색(트렌드 출력 공간) end-->


    <!-- 통합검색(커뮤니티 출력 공간) -->

    <table class="table">
        <thead>
        <tr>
            <th scope="col">No</th>
            <th scope="col">제목</th>
            <th scope="col">조회수</th>
            <th scope="col">등록일</th>
        </tr>
        </thead>

        <tbody id="commSearchResult">

        <script>
            document.addEventListener('DOMContentLoaded', function () {

                const keyword = ${keyword};

                commSearchResult(keyword);
            });

            function commSearchResult(keyword) {
                fetch(`/main/search/comm?keyword=${keyword}`)
                    .then(response => response.json())
                    .then(data => {
                        const tableBody = document.querySelector('#commSearchResult');
                        tableBody.innerHTML = ''; // 테이블 내용 초기화

                        var row = '';


                        data.forEach((trend) => {
                            var formattedDate = new Date(trend.trDate).toLocaleString();

                            row +=

                                "<tr><td>"
                                +
                                trend.trNo
                                +
                                "</td><td><a href = 'commContent?trNo=" + trend.trNo + "'>"
                                +
                                trend.trSubject
                                +
                                "</a></td><td>"

                                +
                                trend.trReadCount
                                +
                                "</td><td>"
                                +
                                formattedDate
                                +
                                "</td></tr>";


                        });
                        tableBody.innerHTML = row;
                    })
                    .catch(error => console.error('Error fetching data:', error));
            }
        </script>

        </tbody>

    </table>

    <!-- Pagination with icons -->
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav><!-- End Pagination with icons -->

    <!-- 통합검색(커뮤니티 출력 공간) end-->


</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
