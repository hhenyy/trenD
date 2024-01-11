<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.td.TrenD.model.TrendVO" %>
<%--
작업자 : 김선홍, 서준혁
수정일자 : 2024-01-07
설명 :  댓글 기능을 추가한 게시글 상세 페이지.
		RESTful API 형태로 댓글 CRUD, 페이징이 가능하다.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	TrendVO post = (TrendVO) request.getAttribute("post");

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String formattedDate = dateFormat.format(post.getTrDate());
%>
<!DOCTYPE html>
<html>

<head>

	<title>TrenD Community Content</title>

	<jsp:include page="../include/metalink.jsp"/>

	<link href="${pageContext.request.contextPath}/css/button.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/content.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet">

	<script src="${pageContext.request.contextPath}/js/common.js"></script>
	<script src="${pageContext.request.contextPath}/js/function.js"></script>
	<%--글 관련 JS--%>
	<script>
		function updateForm() {
			location.href = "/trend/commUpdateForm?trNo=${post.trNo}"
		}

		function deletePost() {
			var check = confirm('글을 삭제하시겠습니까?');

			if (check) {
				location.href = "/trend/deletePost?trNo=${post.trNo}"
				alert('글이 삭제되었습니다.');
			}
		}
	</script>
	<%--댓글 관련 JS--%>
	<script>
		window.onload = () => {
			findAllComment();
		}

		// 댓글 길이 카운팅
		function countingLength(content) {
			if (content.value.length > 300) {
				alert('댓글을 300자 이하로 입력해 주세요.');
				content.value = content.value.substring(0, 300);
				content.focus();
			}
			document.getElementById('counter').innerText = content.value.length + '/300자';
		}

		//----------------------------------------- 댓글 CRUD -----------------------------------------
		// 댓글 저장
		function saveComment() {
			const content = document.getElementById('content');
			isValid(content, '댓글');

			const trNo = ${post.trNo};
			const url = '/replies';
			const method = 'post';
			const params = {
				trNo: trNo,
				trReContent: content.value,
			}
			callApi(url, method, params);
			content.value = '';
			document.getElementById('counter').innerText = '0/300자';
			// let lastPage = calculateLastPage();
			findAllComment();
			
			reload(trNo);
		}

		// 전체 댓글 조회
		function findAllComment(page) {
			const uriPage = new URLSearchParams(location.search).get('page');
			page = (page) ? page : (uriPage ? Number(uriPage) : 1);

			//URL 페이지 범위 제한
			let lastPage = calculateLastPage();
			page = (page > lastPage) ? lastPage : page;
			page = (page < 1) ? 1 : page;

			const trNo = ${post.trNo};
			const uri = `/replies`;
			const params = {
				page: page - 1,
				pageListSize: 10,
				itemPerPage: 10,
				trNo: trNo,
			}

			const response = getJson(uri, params);

			drawComments(response);
			if (response.content.length > 0)
				drawPage(response);
		}

		//댓글 리스트 랜더링
		function drawComments(response) {

			if (response === null) {
				document.querySelector('.cm_list').innerHTML = '<div class="cm_none"><p>등록된 댓글이 없습니다.</p></div>';
				return false;
			}

			let commentHtml = '';

			response.content.forEach(row => {
				let id = row.userVO.userId;
				let date = new Date(row.trReDate).toLocaleDateString();
				let content = row.trReContent;
				let trReNo = row.trReNo;
				let trReLev = row.trReLev;
				let deleted = row.trReDelYn;
				let sessionId = '<%= session.getAttribute("userId") %>';


				commentHtml += '<div class="comment">';
				if (trReLev > 0) commentHtml += '<i class="bi bi-arrow-return-right" style="margin-right: 5px; vertical-align: top;"></i>';

				commentHtml +=
						'<div class="comment-content" style="margin-left: ' + (trReLev * 10) + 'px; display: inline-block; vertical-align: middle;">' +
						'<p style="font-weight: bold; color: black">' + id + '</p>' +
						'<span class="date">' + date + '</span>' +
						'<div class="cont">' +
						'<div class="txt_con" style="color: black">' + ((deleted === 'n') ? content : '삭제된 댓글입니다.') + '</div></div>' +
						'<div class="reply"><p class="func_btns">';

				if (trReLev === 0)
					commentHtml += "<button type='button' onclick='openReplyInputPopup(" + trReNo + ");' class='btns'><span class='icons icon_reply'>답글</span></button>";

				if ((id === sessionId || sessionId === 'admin') && deleted === 'n') {
					commentHtml +=
							"<button type='button' onclick='openCommentUpdatePopup(" + trReNo + ");' class='btns'><span class='icons icon_modify'>수정</span></button>" +
							"<button type='button' onclick='deleteComment(" + trReNo + ");' class='btns'><span class='icons icon_del'>삭제</span></button>";
				}
				commentHtml += '</p></div></div></div>';
			});

			document.querySelector('.cm_list').innerHTML = commentHtml;
		}

		//댓글 페이지네이션 랜더링
		function drawPage(response) {
			let html = '';
			let totalPages = response.totalPages;
			let currentPage = response.currentPage;
			let startPage = response.startPage;
			let endPage = response.endPage;
			let prevPage = startPage - response.pageListSize;
			let nextPage = startPage + response.pageListSize;

			if (startPage > 1)
				html += '<a href="javascript:void(0);" onclick="findAllComment(' + prevPage + ')" class="page_bt prev">이전 페이지</a>'

			//페이지 버튼
			for (let idx = startPage; idx <= endPage; idx++) {
				html += '<a href="javascript:void(0);" onclick="findAllComment(' + idx + ');">' + idx + '</a>'
			}

			//랜더링
			if (endPage !== totalPages)
				html += '<a href="javascript:void(0);" onclick="findAllComment(' + nextPage + ');" class="page_bt next">다음 페이지</a>'
			const paging = document.querySelector('.paging');
			paging.innerHTML = html;

			//현재 페이지 onclick 속성 제거
			const selectedPage = Array.from(paging.querySelectorAll('a')).find(a => (Number(a.text) === currentPage || Number(a.text) === totalPages));
			selectedPage.classList.add('on');
			selectedPage.removeAttribute('onclick');

			//새로고침 시, 댓글 페이지 유지를 위한 History API
			const trNo = new URLSearchParams(location.search).get('trNo');
			history.replaceState({}, '', location.pathname + '?trNo=' + trNo + '&page=' + currentPage);
		}

		// 댓글 수정 팝업 Open
		function openCommentUpdatePopup(trReNo) {

			let uri = '/replies/' + trReNo;

			let response = getJson(uri, {});
			document.getElementById('modalWriter').value = response.userVO.userId;
			document.getElementById('modalContent').value = response.trReContent;
			document.getElementById('commentUpdateBtn').setAttribute('onclick', "updateComment(" + trReNo + ")");
			layerPop('commentUpdatePopup');
		}

		// 댓글 수정 팝업 Close
		function closeCommentUpdatePopup() {
			document.querySelectorAll('#modalContent, #modalWriter').forEach(element => element.value = '');
			document.getElementById('commentUpdateBtn').removeAttribute('onclick');
			layerPopClose('commentUpdatePopup');
		}

		// 댓글 수정
		function updateComment(trReNo) {

			const writer = document.getElementById('modalWriter');
			const content = document.getElementById('modalContent');
			isValid(writer, '작성자');
			isValid(content, '수정할 내용');

			const uri = '/replies';
			const method = 'patch';
			const params = {
				trReNo: trReNo,
				trReContent: content.value,
			}

			callApi(uri, method, params);
			closeCommentUpdatePopup();
			findAllComment();
		}

		// 댓글 삭제
		function deleteComment(trReNo) {
			if (!confirm('선택하신 댓글을 삭제할까요?')) {
				return false;
			}

			const url = '/replies/' + trReNo;
			const method = 'delete';

			callApi(url, method, {});

			findAllComment();
			
			reload(trNo);
		}

		//----------------------------------------- 답글 -----------------------------------------
		// 답글 입력 팝업 open
		function openReplyInputPopup(trReNo) {
			document.getElementById('replyInputBtn').setAttribute('onclick', "saveReply(" + trReNo + ")");
			layerPop('replyInputPopup');
		}

		// 답글 입력 팝업 close
		function closeReplyInputPopup() {
			document.querySelectorAll('#InputModalContent').forEach(element => element.value = '');
			document.getElementById('replyInputBtn').removeAttribute('onclick');
			layerPopClose('replyInputPopup');
		}

		//답글 저장
		function saveReply(trReNo) {
			const content = document.getElementById('inputModalContent');
			isValid(content, '댓글');

			const trNo = ${post.trNo};
			const url = '/replies';
			const method = 'post';
			const params = {
				trNo: trNo,
				trReRef: trReNo,
				trReContent: content.value,
			}

			callApi(url, method, params);

			content.value = '';
			closeReplyInputPopup()
			findAllComment();
			
			reload(trNo);
		}
		
		//그래프 새로고침
		function reload(trNo){
			$.ajax({
				type : "post",	//post 방식 요청. @postmapping으로 받게 됨
				url : "${pageContext.request.contextPath}/reload/"+trNo,

				success : function(result){
					$("#agecontainer").empty()
					$("#gendercontainer").empty()	//호출될 때마다 #container의 내용을 비운다. 따라서 다시 호출됐을 때 그림이 덧그려지지 않게 됨
					
					// create pie chart with passed data
		            var agechart = anychart.pie(result.age2);
		            var genderchart = anychart.pie(result.gender2);
		            //데이터를 '문자열',숫자  이런 식으로 받네??

		            // configure tooltips
		            //agechart.tooltip().format("댓글 수:{%value}");
		            //genderchart.tooltip().format("댓글 수:{%value}");
		            
		            //툴팁 지우기
		            var tooltip = agechart.tooltip();
		            tooltip.enabled(false);
		            
		            var tooltip2 = genderchart.tooltip()
		            tooltip2.enabled(false);

		            // set chart title text settings
		            agechart
		                .title('연령 별 댓글')
		                // set chart radius
		                .radius('43%')
		                // create empty area in pie chart
		                .innerRadius('30%');
		            genderchart
		                .title('성 별 댓글')
		                // set chart radius
		                .radius('43%')
		                // create empty area in pie chart
		                .innerRadius('30%');
		            
		            agechart.width('50%');
		            genderchart.width('50%');

		            // set container id for the chart
		            agechart.container('agecontainer');
		            genderchart.container('gendercontainer');
		            
		            // initiate chart drawing
		            agechart.draw();
		            genderchart.draw();
			 	   
			 	    // initiate chart drawing
				}	
			});	
		}

		//----------------------------------------- 마지막 페이지 계산 -----------------------------------------
		function calculateLastPage() {
			const trNo = ${post.trNo};
			const url = `/replies/count/` + trNo;

			try {
				let totalItems = getJson(url, {});
				return Math.ceil(totalItems / 10);
			} catch (error) {
				console.error("Error fetching data:", error);
				return 0;
			}
		}

		//----------------------------------------- 로그인 체크(로그인 했을 때만 댓글을 달 수 있음) -----------------------------------------
		function redirectToLoginIfNotLoggedIn() {
			let userId = <%= session.getAttribute("userId") %>;

			if (userId != null)
				return false;

			if (confirm("로그인 창으로 이동하시겠습니까?")) {
				location.href = "loginform";
			}
		}
	</script>
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
    <!-- anychart 그리기 -->       
    <script>

        anychart.onDocumentReady(function () {
        console.log(${age})

            // create pie chart with passed data
            var agechart = anychart.pie(${age});
            var genderchart = anychart.pie(${gender});
            //데이터를 '문자열',숫자  이런 식으로 받네??

            // configure tooltips
            //agechart.tooltip().format("댓글 수:{%value}");
            //genderchart.tooltip().format("댓글 수:{%value}");
            
            //툴팁 지우기
            var tooltip = agechart.tooltip();
            tooltip.enabled(false);
            
            var tooltip2 = genderchart.tooltip()
            tooltip2.enabled(false);

            // set chart title text settings
            agechart
                .title('연령 별 댓글')
                // set chart radius
                .radius('43%')
                // create empty area in pie chart
                .innerRadius('30%');
            genderchart
                .title('성 별 댓글')
                // set chart radius
                .radius('43%')
                // create empty area in pie chart
                .innerRadius('30%');
            
            agechart.width('50%');
            genderchart.width('50%');

            // set container id for the chart
            agechart.container('agecontainer');
            genderchart.container('gendercontainer');
            
            // initiate chart drawing
            agechart.draw();
            genderchart.draw();
        });
        </script>    
	
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>


<c:if test="${Character.toString(post.trDelYn) eq 'y'}">
	<script>
		alert('해당 글은 삭제되었습니다.');
		window.location.href = '../../..';
	</script>
</c:if>


<main id="main" class="main">
	<div class="row align-items-top col-8" style="margin: 0 auto">


		<div class="pagetitle" style="padding: 0">

			<h1>커뮤니티 게시판</h1>
			
			<div name="info" style="display: flex; justify-content: space-between";>
				<div class="title_left">
					<nav>
						<ol class="breadcrumb">
							<li class="breadcrumb-item">${post.categoryVO.cateNm}</li>
							<li class="breadcrumb-item">${post.userVO.userName}</li>
							<li class="breadcrumb-item"><%=formattedDate%>
							</li>
						</ol>
					</nav>
				</div>

				<div class="title_right">
					<nav>
						<ol class="breadcrumb">
							<c:if test="${sessionScope.userId.equals(post.userVO.userId) || sessionScope.userId.equals('admin')}">
								<li class="breadcrumb-item"><a href="javascript:void(0);" onclick="deletePost()">삭제</a>
								</li>
							</c:if>
							<c:if test="${sessionScope.userId.equals(post.userVO.userId)}">
								<li class="breadcrumb-item"><a href="javascript:void(0);" onclick="updateForm()">수정</a>
								</li>
							</c:if>
							<li class="breadcrumb-item"><a href="#" onclick="goBack()">목록</a></li>
							<script>
								function goBack() {
									window.history.back();
								}
							</script>
						</ol>
					</nav>
				</div>
			</div>
		</div><!-- End Page Title -->
		<!--본문-->
		<div class="card">
			<div class="card-body">
				<h5 class="card-title" style="font-weight: bold;">${post.trSubject}</h5>
			${post.trContent}
			</div>
		</div>
		<c:if test="${count > 0}">
		<div id="agecontainer" style="margin:10px 10px 10px 200px"></div>
		<div id="gendercontainer" style="margin:10px 10px 10px 200px"></div>
		</c:if>
		<!--end 본문-->
		<section style="padding: 0">
			<!--/* 댓글 작성 */-->
			<div class="cm_write">
				<fieldset>
					<legend class="skipinfo">댓글 입력</legend>
					<div class="cm_input">
						<p><textarea id="content" name="content" onclick="redirectToLoginIfNotLoggedIn()" onkeyup="countingLength(this)" cols="90" rows="4" placeholder="댓글을 입력해 주세요."></textarea></p>
						<span><button type="button" class="btns" onclick="saveComment();">등 록</button> <i
								id="counter">0/300자</i></span>
					</div>
				</fieldset>
			</div>
			<!-- 댓글 렌더링 영역 -->
			<div class="cm_list">

			</div>

			<!--/* 페이지네이션 렌더링 영역 */-->
			<div class="paging">

			</div>

			<!--/* 댓글 수정 popup */-->
			<div id="commentUpdatePopup" class="popLayer">
				<h3>댓글 수정</h3>
				<div class="pop_container">
					<table class="tb tb_row tl">
						<colgroup>
							<col style="width:30%;"/>
							<col style="width:70%;"/>
						</colgroup>
						<tbody>
						<tr>
							<th scope="row">작성자<span class="es">필수 입력</span></th>
							<td><input type="text" id="modalWriter" name="modalWriter" readonly/>
							</td>
						</tr>
						<tr>
							<th scope="row">내용<span class="es">필수 입력</span></th>
							<td><textarea id="modalContent" name="modalContent" cols="90" rows="10"
										  placeholder="수정할 내용을 입력해 주세요."></textarea></td>
						</tr>
						</tbody>
					</table>
					<p class="btn_set">
						<button type="button" id="commentUpdateBtn" class="btns btn_st2">수정</button>
						<button type="button" class="btns btn_bdr2" onclick="closeCommentUpdatePopup();">취소</button>
					</p>
				</div>
				<button type="button" class="btn_close" onclick="closeCommentUpdatePopup();"><span><i
						class="far fa-times-circle"></i></span></button>
			</div>

			<!--/* 답글 입력 popup */-->
			<div id="replyInputPopup" class="popLayer" style="margin: 0 auto">
				<h3>답글 입력</h3>
				<div class="pop_container">
					<table class="tb tb_row tl">
						<colgroup>
							<col style="width:30%;"/>
							<col style="width:70%;"/>
						</colgroup>
						<tbody>
						<tr>
							<th scope="row">내용<span class="es">필수 입력</span></th>
							<td><textarea id="inputModalContent" name="modalContent" cols="90" rows="10" onclick="redirectToLoginIfNotLoggedIn()"
										  placeholder="답글을 입력해 주세요."></textarea></td>
						</tr>
						</tbody>
					</table>
					<p class="btn_set">
						<button type="button" id="replyInputBtn" class="btns btn_st2">저장</button>
						<button type="button" class="btns btn_bdr2" onclick="closeReplyInputPopup();">취소</button>
					</p>
				</div>
				<button type="button" class="btn_close" onclick="closeReplyInputPopup();"><span><i
						class="far fa-times-circle"></i></span></button>
			</div>
		</section>
	</div>
</main>

<jsp:include page="../include/footer.jsp"/>

</body>
</html>