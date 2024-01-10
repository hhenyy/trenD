<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>


    <!-- anychart 라이브러리 -->
    <script
            src="https://cdn.anychart.com/releases/v8/js/anychart-base.min.js"></script>
    <script src="https://cdn.anychart.com/releases/v8/js/anychart-ui.min.js"></script>
    <script
            src="https://cdn.anychart.com/releases/v8/js/anychart-exports.min.js"></script>
    <link
            href="https://cdn.anychart.com/releases/v8/css/anychart-ui.min.css"
            type="text/css" rel="stylesheet">
    <link
            href="https://cdn.anychart.com/releases/v8/fonts/css/anychart-font.min.css"
            type="text/css" rel="stylesheet">

    <!-- 제이쿼리 -->
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <style type="text/css">
        html, body, #container {
            width: 100%;
            height: 100%;
            margin: 19px 0px 0px 50px;
            padding: 1px 0px;
        }

        #button {
            width: 50%;
            margin: 5px 0px 5px 600px;
        }

        button {
            margin: 5px 10px 5px 10px;
        }
    </style>
    <script>

        console.log(${list})
        anychart.onDocumentReady(function () {

            // create pie chart with passed data
            var chart = anychart.pie(${list});
            //데이터를 '문자열',숫자  이런 식으로 받네??

            // configure tooltips
            chart.tooltip().format("접속횟수:{%value}");

            // set chart title text settings
            chart
                .title('${value} 별 통계')
                // set chart radius
                .radius('43%')
                // create empty area in pie chart
                .innerRadius('30%');

            // set container id for the chart
            chart.container('container');
            // initiate chart drawing
            chart.draw();
        });


        function move(a) {

            var category = document.getElementById('category').value;
            location.href = "/StatisticsDetail?value=" + a + "&cateNm=" + category


            /* $.ajax({
                url : "/StatisticsDetail",
                data :
                success : function(result){
                }
            }) 	 */
        }

    </script>


    <jsp:include page="../include/metalink.jsp"/>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>

<div id="container"></div>
<div id="button">
    <button type="button" class="btn btn-primary" id="age" value="연령"
            onclick="move(this.value)">연령
    </button>
    <!-- this로 현재 위치의 DOM 객체를 지정 가능. 그 중 value값을 뽑아냄. -->
    <button type="button" class="btn btn-secondary" value="성별" id="gender"
            onclick="move(this.value)">성별
    </button>
    <button type="button" class="btn btn-info" value="지역"
            onclick="move(this.value)">지역
    </button>
    <button type="button" class="btn btn-light"
            onclick="location.href='/Statistics'">처음으로
    </button>
    <input type="hidden" id="category" value=${category }>
</div>
<%@ include file="../include/footer.jsp" %>
</body>
</html>