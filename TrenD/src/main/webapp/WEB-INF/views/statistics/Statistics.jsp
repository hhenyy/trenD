<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>통계</title>
<jsp:include page="../include/metalink.jsp" />

<!-- anychart 라이브러리 -->
<script
	src="https://cdn.anychart.com/releases/8.12.0/js/anychart-core.min.js"></script>
<script
	src="https://cdn.anychart.com/releases/8.12.0/js/anychart-tag-cloud.min.js"></script>
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
<script>
 anychart.onDocumentReady(
		function () {
	$("#container1").hide();
	$("button").hide();

    // create data   
    var data = 
    	${json}		//[{"x" : 값,"value" : 값},{"x" : 값,"value" : 값}]의 형태로 값을 받음
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
    	if(e.point.get("value")>0){
      //var url = "/StatisticsDetail?cateNm=" + e.point.get("x");
      //location.href=url; 
      detail(e.point.get("x"),"연령")
      //팝업처리? 아니면 ajax나 rest api?
      //restapi를 위해선 상세통계 페이지까지 여기서 다 함께 처리해야 함
      //각 단어를 클릭 시 location.href가 아니라 ajax 함수를 호출하도록 해야 함. 
      //해당 함수는 매개변수가 두 개. 카테고리 명과 value값을 받도록 해야 됨. 이 함수는 상세통계 페이지에서 버튼을 눌렀을 때 이동하는 동작을 동시에 처리하도록 만들어야
      //매개변수 두 개를 받아 컨트롤러에 전달 후, 해당 컨트롤러에서 리스트를 생성하여 model로 공유하는 것이 아닌 restapi 방식으로 받아오도록 처리해야 됨
    	}
    });
 
}
		); 

function detail(a,b){
	const value = b
	
	//var category = encodeURIComponent(a);	//  /이 포함된 문자열을 전송하려면 인코딩이 필요. String과 달리 jsp의 변수들은 문자열을 통으로 변환 불가
	//하지만 인코딩을 하게 되면 /이 포함된 문자열이 저장된 변수는 오류를 발생시킴. 왜
	var category = a;
	
	
	$.ajax({
		type : "get",	//post 방식 요청. @postmapping으로 받게 됨
		url : "${pageContext.request.contextPath}/StatisticsDetail/"+value,
		//${pageContext.request.contextPath}는 프로젝트명. /StatisticsDetail는 공통코드. 컨트롤러로 연결. /StatisticsDetail은 요청명
		//다만 컨트롤러 아래에 메소드가 하나뿐이라면 이런 식으로 처리할 필요는 없음
		//컨트롤러에 @RequestMapping("/StatisticsDetail")처럼 맵핑을 하지 않고, 메소드에 달린 @RequestMapping 경로로 바로 요청하면 됨
		
		//url : "${pageContext.request.contextPath}/StatisticsDetail/"+category+"/"+value,
		//url 주소에 변수를 전달할 때, 전송하려는 변수 내부에 /가 포함되면 해당 변수를 경로의 일부로 인식하게 됨
		//자바에선 String 변수를 통해 /을 포함한 문자열을 전송할 수 있지만, 자바스크립트에선 String 변수를 사용하는 것이 불가능
		//이 문제를 해결하려면 어떻게?
		data : {"category":category},	///가 포함된 문자열을 전송해야 하는 category는 json을 통해 전송
		//json을 통해 전송된 데이터는 @PathVariable이 아닌 @RequestParam 매개변수로 받아줘야 함
		
		//공통된 요청 이름값 StatisticsDetail는 @requestMapping으로 받고 있음
		//공통 요청은 requestMapping(컨트롤러)로 받고, 그 아래에 postMapping/getMapping 등이 달린 메소드 존재
		//링크를 통한 값 전달. 컨트롤러에서는 /StatisticsDetail/{category}/{value}와 같은 모양으로 값들을 받게 됨
		
		//contentType: 'application/json',  	// 데이터 타입을 JSON으로 설정
		//data : JSON.stringify(detail),  	// 데이터를 JSON 문자열로 변환하여 전송
		//json 데이터가 아닐 땐 modelattrribute 로 받음. json은 @requestBody로 DTO(DTO가 아니어도 됨. 다량의 데이터를 전달하는 법) 안에 바로 저장시킴
		
		//서버와 클라이언트 간 페이지 변경 없이 @requestBody로만 값을 주고받게 됨
		success : function(result){
			$("#container").hide()
			$("#container1").empty()	//호출될 때마다 #container1의 내용을 비운다. 따라서 다시 호출됐을 때 그림이 덧그려지지 않게 됨
										//ajax 안에 있는 게 훨씬 깔끔. 메소드 호출 시점에 비워버리면 비는ㄴ 순간이 보여버림
			// create pie chart with passed data
	 	    var chart1 = anychart.pie(result.list);
	 	 	//데이터를 '문자열',숫자  이런 식으로 받네??
	 	 			
	 	 	// configure tooltips
	 		chart1.tooltip().format("접속횟수:{%value}");

	 	    // set chart title text settings
	 	    chart1
	 	      .title(result.value+' 별 통계')
	 	      // set chart radius
	 	      .radius('43%')
	 	      // create empty area in pie chart
	 	      .innerRadius('30%');
	 	    
	 	    // set container id for the chart
	 	    chart1.container('container1');
	 	   $("#container1").show()					
	 	   $("button").show()
	 	  	  chart1.draw();  //이 메소드가 작동하면 그래프 그림이 그려짐(여러 개의 명령이 동시에 작동하진 않음)
	 	  	  				  //따라서 버튼 클릭 시 detail 함수를 호출하게 되면, container1 영역에 또 하나의 그림이 그려지는 것
	 	  	  				  //그 아래의 button 영역은 덮어씌워버림. 따라서 hide() 메소드의 실행은 의미 없음. 해당 메소드는 단지 태그 내용을 가리는 것뿐
	 	  	  				  //그림을 지우는 메소드가 없을까?
	 	   
	 	    // initiate chart drawing
	 	    
	 	  
	 	  
	 	  var buttons = 
	 		  '<button type="button" class="btn btn-primary" id="age" value="연령"'
	 		  buttons +=' onclick="javascript:detail(`'+result.category+'`,this.value)">연령</button>'
	 		  buttons +='<button type="button" class="btn btn-secondary" id="gender" value="성별"'
	 		  buttons +=' onclick="javascript:detail(`'+result.category+'`,this.value)">성별</button>'
	 		  buttons +='<button type="button" class="btn btn-info" id="location" value="지역"'
	 		  buttons +=' onclick="javascript:detail(`'+result.category+'`,this.value)">지역</button>'
	 		  buttons +='<button type="button" class="btn btn-light"'
	 		  //매우 다행히도 백틱(`)을 통해 삼중 따옴표를 구현하는 것이 가능. 따라서 "" 안에 '' 안에 ``의 형태로 값을 넣는 것이 가능
	 		  //location.href=""의 형태로 사용되는데, 이때 "" 안의 값이 문자열이라면 한 번 더 감싸줘야 함. 이 코드를 문자열 안에서 구현 중
	 		  //다만 백틱은 ES6부터만 호환됨... 어떻게 해결할 것인가?
	 		  buttons +=' onclick="home()">처음으로</button>'
			<!-- this로 현재 위치의 DOM 객체를 지정 가능. 그 중 value값을 뽑아냄. -->
	 		  console.log(result.category)

	 		  $("#button").html(buttons);		
			
				
			
				
			
	 	  
	 	    
		}	
	});	
 	
 	}

function home(){
	  location.href="/Statistics"
 }



</script>
<style>
html, body, main, #container, #container1 {
	width: 95%;
	height: 95%;
	margin: 0px 0px 0px 0px;
	padding: 0px 0px;
}

#button {
	width: 50%;
	margin: 5px 0px 5px 400px;
}

button {
	margin: 5px 10px 5px 10px;
}
}
</style>

</head>
<body>
	<main id=main class="main">
		<jsp:include page="../include/header.jsp" />
		<jsp:include page="../include/sidebar.jsp" />
		<div id="container"></div>
		<div id="container1"></div>
		<div id="button"></div>
		<!-- 워드클라우드 출력 -->
		<%@ include file="../include/footer.jsp"%>
	</main>
</body>
</html>