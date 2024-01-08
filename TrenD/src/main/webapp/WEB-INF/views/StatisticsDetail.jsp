<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<div id="button">
		<button type="button" class="btn btn-primary" id="age" value="연령"
			onclick="move(this.value)">연령</button>
		<!-- this로 현재 위치의 DOM 객체를 지정 가능. 그 중 value값을 뽑아냄. -->
		<button type="button" class="btn btn-secondary" value="성별" id="gender"
			onclick="move(this.value)">성별</button>
		<button type="button" class="btn btn-info" value="지역"
			onclick="move(this.value)">지역</button>
		<button type="button" class="btn btn-light"
			onclick="location.href='/Statistics'">처음으로</button>
		<input type="hidden" id="category" value=${category }>
	</div>
	<%@ include file="include/footer.jsp"%>
</body>
</html>