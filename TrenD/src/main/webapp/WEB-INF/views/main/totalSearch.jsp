<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>통합검색</title>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>
<main id="main" class="main">

    <table class="table">
        <thead>
        <tr>
            <th scope="col">No</th>
            <th scope="col">제목</th>
            <th scope="col">조회수</th>
            <th scope="col">등록일</th>
        </tr>
        </thead>

        <tbody id="searchResult">

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // 여기에서 실제로 검색에 사용될 키워드를 가져오는 로직을 추가해야 합니다.
                const keyword = ${keyword}; // 예시로 하드코딩된 키워드입니다.

                searchResult(keyword);
            });

            function searchResult(keyword) {
                fetch(`/main/search?keyword=${keyword}`)
                    .then(response => response.json())
                    .then(data => {
                        const tableBody = document.querySelector('#searchResult');
                        tableBody.innerHTML = ''; // 테이블 내용 초기화

                        var row = '';


                        data.forEach((trend) => {



                            row +=

                                "<tr><td>"
                                +
                                trend.trNo
                                +
                                "</td><td><a href = 'commContent?trNo="+trend.trNo+"'>"
                                +
                                trend.trSubject
                                +
                                "</a></td><td>"

                                +
                                trend.trReadCount
                                +
                                "</td><td>"
                                +
                                trend.trDate
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

</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
