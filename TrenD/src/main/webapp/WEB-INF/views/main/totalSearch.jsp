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

    <div class="row align-items-top col-10" style="margin: 0 auto">

        <div class="pagetitle">

            <h1>통합검색</h1>

 <div class="title_left">
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"></li>

                    </ol>
                </nav>
            </div>


        </div><!-- End Page Title -->

        <!-- 통합검색(트렌드 출력 공간) -->
        <div id="trendResult">

            <h4 style="font-family: 'Nunito', sans-serif; color: #253d1e;">트랜드 검색 결과</h4>


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

                <!-- script로 trend 리스트 출력 -->

                </tbody>

            </table>

            <div style="display: flex; justify-content: center; margin-bottom: 10px;">
            <!-- Pagination with icons -->
            <nav aria-label="Page navigation example">
                <ul class="pagination" id="trendPage">

                    <!-- script로 trend 페이징 출력 -->

                </ul>
            </nav><!-- End Pagination with icons -->
            </div>
        </div>
        <!-- 통합검색(트렌드 출력 공간) end-->
        <!-- 통합검색(커뮤니티 출력 공간) -->
        <div id="commResult">

            <h4 style="font-family: 'Nunito', sans-serif; color: #253d1e;">커뮤니티 검색 결과</h4>

            <table class="table">
                <thead>
                <tr>
                    <th scope="col">No</th>
                    <th scope="col">머리말</th>
                    <th scope="col">제목</th>
                    <th scope="col">조회수</th>
                    <th scope="col">등록일</th>
                </tr>
                </thead>

                <tbody id="commSearchResult">

                <!-- script로 comm 리스트 출력 -->

                </tbody>

            </table>

            <div style="display: flex; justify-content: center; margin-bottom: 10px;">
            <!-- Pagination with icons -->
            <nav aria-label="Page navigation example">
                <ul class="pagination" id="commPage">


                    <!-- script로 comm 페이징 출력 -->


                </ul>
            </nav><!-- End Pagination with icons -->
            </div>
            <!-- 통합검색(커뮤니티 출력 공간) end-->
        </div>
    </div>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>

<script>
    document.addEventListener('DOMContentLoaded', function () {

        const keyword = "${keyword}";
        const page = 1;

        commSearchResult(keyword, page);
        trendSearchResult(keyword, page);
    });

    function trendSearchResult(keyword, page) {

        fetch("/search/trend?keyword=${keyword}&page=" + page)


            .then(response => response.json())
            .then(data => {

                console.log(data);

                const tableBody = document.querySelector('#trendSearchResult');
                console.log(tableBody);
                tableBody.innerHTML = '';

                var row = '';

                data.searchResult.forEach((trend) => {
                    var formattedDate = new Date(trend.trDate).toLocaleString();

                    row +=

                        "<tr><td>"
                        +
                        trend.trNo
                        +
                        "</td><td><a href = 'post?trNo=" + trend.trNo + "'>"
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

                var start = parseInt(data.startPage);
                var end = parseInt(data.endPage);
                var pageRange = parseInt(data.pageRange);
                console.log(start);
                console.log(end);


                const trendPage = document.querySelector('#trendPage');
                console.log(trendPage);
                trendPage.innerHTML = '';
                var pagination = '';

                pagination += '<li class="page-item"><a class="page-link" href=' +
                    "javascript:trendSearchResult('${keyword}'" + "," + (start - pageRange) + ")"
                    +
                    ' aria-label="Previous"><span aria-hidden="true">'
                    +
                    '&laquo;'
                    +
                    '</span></a></li>';


                for (let i = start; i <= end; i++) {
                    console.log(i);

                    pagination +=
                        "<li class='page-item'><a class='page-link' href='" +
                        "javascript:trendSearchResult('${keyword}'" + "," + i + ")" +
                        "' onclick='trendSearchResult(\"" + keyword + "\", " + i + ")'>" +
                        i +
                        "</a></li>";
                }

                pagination +=

                    '<li class="page-item"><a class="page-link" href=' +
                    "javascript:trendSearchResult('${keyword}'" + "," + (start + pageRange) + ")" +
                    ' aria-label="Next"><span aria-hidden="true">' +
                    '&raquo;'
                    + '</span></a></li>';

                trendPage.innerHTML = pagination;

            })
            .catch(error => console.error('Error fetching data:', error));
    }


    function commSearchResult(keyword, page) {

        fetch("/search/comm?keyword=${keyword}&page=" + page)


            .then(response => response.json())
            .then(data => {

                console.log(data);

                const tableBody = document.querySelector('#commSearchResult');
                console.log(tableBody);
                tableBody.innerHTML = '';

                var row = '';

                data.searchResult.forEach((trend) => {
                    var formattedDate = new Date(trend.trDate).toLocaleString();

                    row +=

                        "<tr><td>"
                        +
                        trend.trNo
                        +
                        "</td><td>"
                        +
                        trend.categoryVO.cateNm
                        +
                        "</td><td><a href = 'post?trNo=" + trend.trNo + "'>"
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

                var start = parseInt(data.startPage);
                var end = parseInt(data.endPage);
                var pageRange = parseInt(data.pageRange);
                console.log(start);
                console.log(end);


                const commPage = document.querySelector('#commPage');
                console.log(commPage);
                commPage.innerHTML = '';
                var pagination = '';

                pagination += '<li class="page-item"><a class="page-link" href=' +
                    "javascript:commSearchResult('${keyword}'" + "," + (start - pageRange) + ")"
                    +
                    ' aria-label="Previous"><span aria-hidden="true">'
                    +
                    '&laquo;'
                    +
                    '</span></a></li>';


                for (let i = start; i <= end; i++) {
                    console.log(i);

                    pagination +=
                        "<li class='page-item'><a class='page-link' href='" +
                        "javascript:commSearchResult('${keyword}'" + "," + i + ")" +
                        "' onclick='commSearchResult(\"" + keyword + "\", " + i + ")'>" +
                        i +
                        "</a></li>";
                }

                pagination +=

                    '<li class="page-item"><a class="page-link" href=' +
                    "javascript:commSearchResult('${keyword}'" + "," + (start + pageRange) + ")" +
                    ' aria-label="Next"><span aria-hidden="true">' +
                    '&raquo;'
                    + '</span></a></li>';

                commPage.innerHTML = pagination;

            })
            .catch(error => console.error('Error fetching data:', error));
    }


</script>

</html>
