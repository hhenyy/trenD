<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<title>Insert title here</title>
<meta content="" name="description">
<meta content="" name="keywords">

<!-- Favicons -->
<link href="assets/img/favicon.png" rel="icon">
<link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
<link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
<link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
<link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">

<!-- Template Main CSS File -->
<link href="assets/css/style.css" rel="stylesheet">

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

<!-- Template Main JS File -->
<script src="assets/js/main.js"></script>
<%@ include file="include/header.jsp"%>
</head>
<body>
<div id="container"></div>
<!-- 워드클라우드 출력 -->
<%@ include file="include/footer.jsp"%>
</body>
</html>