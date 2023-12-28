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

        <tbody id="searchResultTableBody">

        <script>

            document.addEventListener('DOMContentLoaded', function() {
                searchResult();
                console.log(data);
            });

            // AJAX 요청을 통해 서버에서 데이터를 가져옴
            function searchResult(){
            fetch('/main/search?keyword=${keyword}')
                .then(response => response.json())
                .then(data => {
                    // 가져온 데이터로 동적으로 테이블 구성
                    const tableBody = document.querySelector('#searchResultTable tbody');

                    data.forEach((trend, index) => {
                        const row = tableBody.insertRow();
                        const cell1 = row.insertCell(0);
                        const cell2 = row.insertCell(1);
                        const cell3 = row.insertCell(2);
                        const cell4 = row.insertCell(3);

                        cell1.innerHTML = trend.trNo;
                        cell2.innerHTML = trend.trSubject;
                        cell3.innerHTML = trend.readCount;
                        cell4.innerHTML = trend.trDate;
                    });
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
