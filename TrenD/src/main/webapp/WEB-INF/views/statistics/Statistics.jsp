<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>

<!-- anychart 라이브러리 -->
<script src="https://cdn.anychart.com/releases/8.12.0/js/anychart-core.min.js"></script>
<script src="https://cdn.anychart.com/releases/8.12.0/js/anychart-tag-cloud.min.js"></script>
<script>
anychart.onDocumentReady(function () {

    // create data   
    var data = 
    	${json}
    ;

    // create a chart and set the data
    var chart = anychart.tagCloud(data);
    
 // configure tooltips
    chart.tooltip().format("관심도:{%yPercentOfTotal}%\n접속횟수:{%value}");

    // Set starting angle.
    chart.fromAngle(0);

    // Set end angle.
     chart.toAngle(0);
    
    // set the chart title
    chart.title("클릭 시 상세 통계 페이지로 이동");

    // set the container id
    chart.container("container");

 // 워드클라우드의 모양을 결정. 단어가 많아지면 원형으로 표현되지만 단어가 적으면 효과가 적음
      chart.mode("spiral");
 
    // initiate drawing the chart
    chart.draw();
    
 // add an event listener
 //각 단어를 클릭시 해당 단어의 상세 통계 페이지로 이동하도록
    chart.listen("pointClick", function(e){
    	if(e.point.get("value")!=0){
      var url = "/StatisticsDetail?cateNm=" + e.point.get("x");
      location.href=url; //팝업처리? 아니면 ajax나 rest api?
    	}
    });
 
    
});
</script>
<style>
html, body, #container {
    width: 100%;
    height: 100%;
     margin: 19px 0px 0px 50px;
    padding: 1px 0px;
    
}
</style>

<!-- Vendor JS Files -->
<script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="assets/vendor/chart.js/chart.umd.js"></script>
<script src="assets/vendor/echarts/echarts.min.js"></script>
<script src="assets/vendor/quill/quill.min.js"></script>
<script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="assets/vendor/tinymce/tinymce.min.js"></script>
<script src="assets/vendor/php-email-form/validate.js"></script>

	<jsp:include page="../include/metalink.jsp"/>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>
<div id="container"></div>
<!-- 워드클라우드 출력 -->
<%@ include file="../include/footer.jsp"%>
</body>
</html>