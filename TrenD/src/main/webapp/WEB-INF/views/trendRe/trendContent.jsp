<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">

	<title>Testing CSS</title>
	<meta content="" name="description">
	<meta content="" name="keywords">

	<!-- Favicons -->
	<link href="${pageContext.request.contextPath}/assets/img/favicon.png" rel="icon">
	<link href="${pageContext.request.contextPath}/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

	<!-- Google Fonts -->
	<link href="https://fonts.gstatic.com" rel="preconnect">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	      rel="stylesheet">

	<!-- Vendor CSS Files -->
	<link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.snow.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/vendor/simple-datatables/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/button.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/content.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet">

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="${pageContext.request.contextPath}/js/common.js"></script>
	<script src="${pageContext.request.contextPath}/js/function.js"></script>
	<script>
        window.onload = () => {
            findAllComment();
        }

        // 전체 댓글 조회
        function findAllComment(page) {
            const currentPage = document.querySelector('.paging a.on');
            page = (page) ? page : (currentPage ? Number(currentPage.text) : 1);

            const trNo = ${trNo};
            const uri = `/post/${trNo}/reply`;
            const params = {
                page : page - 1,
                pageListSize : 10,
	            itemPerPage : 10,
                trNo : trNo,
            }

            const response = getJson(uri, params);

            drawComments(response);
            if (response.content.length > 0)
                drawPage(response);
        }

        // 댓글 HTML draw
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
                let sessionId = '<%= session.getAttribute("id") %>';


	commentHtml += '<div class="comment">';
                if (trReLev > 0) commentHtml += '<i class="bi bi-arrow-return-right" style="margin-right: 5px; vertical-align: top;"></i>';

                commentHtml +=
	                '<div class="comment-content" style="margin-left: ' + (trReLev * 10) + 'px; display: inline-block; vertical-align: middle;">' +
	                '<p style="font-weight: bold; color: black">' + id +'</p>' +
	                '<span class="date">' + date + '</span>' +
                    '<div class="cont">' +
	                '<div class="txt_con" style="color: black">' + ((deleted === 'n') ? content : '삭제된 댓글입니다.') + '</div></div>' +
	                '<div class="reply"><p class="func_btns">';

                if (trReLev === 0)
                    commentHtml += "<button type='button' onclick='openReplyInputPopup(" + trReNo + ");' class='btns'><span class='icons icon_reply'>답글</span></button>";

                if (id === sessionId && deleted === 'n') {
                    commentHtml +=
	                    "<button type='button' onclick='openCommentUpdatePopup(" + trReNo + ");' class='btns'><span class='icons icon_modify'>수정</span></button>" +
	                    "<button type='button' onclick='deleteComment(" + trReNo + ");' class='btns'><span class='icons icon_del'>삭제</span></button>";
                }
                commentHtml += '</p></div></div></div>';
            });

            document.querySelector('.cm_list').innerHTML = commentHtml;
        }


        // 페이지네이션 HTML draw
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

			if (endPage !== totalPages)
                html += '<a href="javascript:void(0);" onclick="findAllComment(' + nextPage + ');" class="page_bt next">다음 페이지</a>'

            //랜더링
            const paging = document.querySelector('.paging');
            paging.innerHTML = html;

            // 7. 사용자가 클릭한 페이지 번호(page) 또는 끝 페이지 번호(totalPageCount)에 해당되는 a 태그를 찾아 활성화(active) 처리한 후 클릭 이벤트 제거
            const selectedPage = Array.from(paging.querySelectorAll('a')).find(a => (Number(a.text) === currentPage || Number(a.text) === totalPages));
            selectedPage.classList.add('on');
            selectedPage.removeAttribute('onclick');
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

        // 댓글 저장
        function saveComment() {
            const content = document.getElementById('content');
            isValid(content, '댓글');

            const trNo = ${trNo};
            const params = {
                trNo: trNo,
                trReContent: content.value,
            }

            $.ajax({
                url: '/post/${trNo}/reply',
                type: 'post',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(params),
                async: false,
                success: function (response) {
                    alert('저장되었습니다.');
                    content.value = '';
                    document.getElementById('counter').innerText = '0/300자';
                    findAllComment();
                },
                error: function (request, status, error) {
                    console.log(error)
                }
            })
        }

        // 댓글 수정 팝업 open
        function openCommentUpdatePopup(trReNo) {

            let uri = '/post/' + ${trNo} + '/reply/' + trReNo;

            $.ajax({
                url: uri,
                type: 'get',
                dataType: 'json',
                async: false,
                success: function (response) {
                    console.log(response);
                    document.getElementById('modalWriter').value = response.userVO.userId;
                    document.getElementById('modalContent').value = response.trReContent;
                    document.getElementById('commentUpdateBtn').setAttribute('onclick', "updateComment(" + trReNo + ")");
                    layerPop('commentUpdatePopup');
                },
                error: function (request, status, error) {
                    console.log(error)
                }
            })
        }

        // 댓글 수정 팝업 close
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

            const trNo = ${trNo};
            const params = {
                trNo: trNo,
	            trReNo: trReNo,
                trReContent: content.value,
            }

			const uri = '/post/' + trNo + '/reply/' + trReNo;
            console.log(uri);
            $.ajax({
                url : uri,
                type : 'patch',
                contentType : 'application/json; charset=utf-8',
                dataType : 'json',
                data : JSON.stringify(params),
                async : false,
                success : function (response) {
                    alert('수정되었습니다.');
                    closeCommentUpdatePopup();
                    findAllComment();
                },
                error : function (request, status, error) {
                    console.log(error)
                }
            })
        }

        // 댓글 삭제
        function deleteComment(trReNo) {

            if ( !confirm('선택하신 댓글을 삭제할까요?') ) {
                return false;
            }

            const trNo = ${trNo};

            $.ajax({
                url : '/post/' + trNo + '/reply/' + trReNo,
                type : 'delete',
                dataType : 'json',
                async : false,
                success : function (response) {
                    alert('삭제되었습니다.');
                    findAllComment();
                },
                error : function (request, status, error) {
                    console.log(error)
                }
            })
        }

        //답글 저장
        function saveReply(trReNo) {
            const content = document.getElementById('inputModalContent');
            isValid(content, '댓글');

            const trNo = ${trNo};
            const params = {
                trNo: trNo,
	            trReRef: trReNo,
                trReContent: content.value,
            }

            $.ajax({
                url: '/post/${trNo}/reply',
                type: 'post',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(params),
                async: false,
                success: function (response) {
                    alert('답글이 저장되었습니다.');
                    content.value = '';
                    closeCommentUpdatePopup();
                    findAllComment();
                },
                error: function (request, status, error) {
                    console.log(error)
                }
            })
        }

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
	</script>
</head>
<body>
<jsp:include page="../include/header.jsp"/>
<main id="main" class="main">
	<div>
		<div>
			<h1>trNo : ${trNo}</h1>
		</div>

		<div>
			<div class="content">
				<section>
					<!--/* 댓글 작성 */-->
					<div class="cm_write">
						<fieldset>
							<legend class="skipinfo">댓글 입력</legend>
							<div class="cm_input">
								<p><textarea id="content" name="content" onkeyup="countingLength(this);" cols="90"
								             rows="4" placeholder="댓글을 입력해 주세요."></textarea></p>
								<span><button type="button" class="btns" onclick="saveComment();">등 록</button> <i id="counter">0/300자</i></span>
							</div>
						</fieldset>
					</div>
					<!-- 댓글 렌더링 영역 -->
					<div class="cm_list">

					</div>

					<!--/* 페이지네이션 렌더링 영역 */-->
					<div class="paging">

					</div>
				</section>
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
							<td><textarea id="modalContent" name="modalContent" cols="90" rows="10" placeholder="수정할 내용을 입력해 주세요."></textarea></td>
						</tr>
						</tbody>
					</table>
					<p class="btn_set">
						<button type="button" id="commentUpdateBtn" class="btns btn_st2">수정</button>
						<button type="button" class="btns btn_bdr2" onclick="closeCommentUpdatePopup();">취소</button>
					</p>
				</div>
				<button type="button" class="btn_close" onclick="closeCommentUpdatePopup();"><span><i class="far fa-times-circle"></i></span></button>
			</div>

			<!--/* 답글 입력 popup */-->
			<div id="replyInputPopup" class="popLayer">
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
							<td><textarea id="inputModalContent" name="modalContent" cols="90" rows="10" placeholder="답글을 입력해 주세요."></textarea></td>
						</tr>
						</tbody>
					</table>
					<p class="btn_set">
						<button type="button" id="replyInputBtn" class="btns btn_st2">저장</button>
						<button type="button" class="btns btn_bdr2" onclick="closeReplyInputPopup();">취소</button>
					</p>
				</div>
				<button type="button" class="btn_close" onclick="closeReplyInputPopup();"><span><i class="far fa-times-circle"></i></span></button>
			</div>
		</div>
	</div>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>