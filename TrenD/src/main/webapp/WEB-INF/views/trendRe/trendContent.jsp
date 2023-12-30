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

        //date format convert
        function dateFormat(date) {
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let hour = date.getHours();
            let minute = date.getMinutes();
            let second = date.getSeconds();

            month = month >= 10 ? month : '0' + month;
            day = day >= 10 ? day : '0' + day;
            hour = hour >= 10 ? hour : '0' + hour;
            minute = minute >= 10 ? minute : '0' + minute;
            second = second >= 10 ? second : '0' + second;

            return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
        }

        // 전체 댓글 조회
        function findAllComment(page) {
            const currentPage = document.querySelector('.paging a.on');
            page = (page) ? page : (currentPage ? Number(currentPage.text) : 1);

            const trNo = ${trNo};
            const uri = `/post/${trNo}/reply`;
            const params = {
                page : page,
                recordSize : 10,
                pageSize : 10,
                trNo : trNo,
            }

            const response = getJson(uri, params);

            drawComments(response.content);
            // drawPage(pagination, page);
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

        // 댓글 HTML draw
        function drawComments(replyList) {
            if ( !replyList.length ) {
                document.querySelector('.cm_list').innerHTML = '<div class="cm_none"><p>등록된 댓글이 없습니다.</p></div>';
                return false;
            }

            let commentHtml = '';

            replyList.forEach(row => {
                let id = row.userVO.userId;
                let date = new Date(row.trReDate);
                let content = row.trReContent;
                let trReNo = row.trReNo;
                commentHtml +=
                    ' <div> ' +
                    '<span class="writer_img"><img src="/images/default_profile.png" width="30" height="30" alt="기본 프로필 이미지"/></span> ' +
                    '<p class="writer"> ' +
                    '<em>' + id + '</em>' +
                    '</p>' +
                    '<span class="date">' + dateFormat(date) + '</span>' +
                    '<div class="cont"><div class="txt_con">' + content + '</div></div>' +
                    '<p class="func_btns">' +
                    "<button type='button' onclick='openCommentUpdatePopup(" + trReNo + ");' class='btns'><span class='icons icon_modify'>수정</span></button>" +
                    "<button type='button' onclick='deleteComment(" + trReNo + ");' class='btns'><span class='icons icon_del'>삭제</span></button>" +
                    '</p>' +
                    '</div>';
            })

            document.querySelector('.cm_list').innerHTML = commentHtml;
        }


        // 페이지네이션 HTML draw
        function drawPage(pagination, page) {

            // 1. 필수 파라미터가 없는 경우, 페이지네이션 HTML을 제거한 후 로직 종료
            if ( !pagination || !page ) {
                document.querySelector('.paging').innerHTML = '';
                throw new Error('Missing required parameters...');
            }

            // 2. 페이지네이션 HTML을 그릴 변수
            let html = '';

            // 3. 첫/이전 페이지 버튼 추가
            if (pagination.existPrevPage) {
                html += `
                        <a href="javascript:void(0);" onclick="findAllComment(1)" class="page_bt first">첫 페이지</a>
                        <a href="javascript:void(0);" onclick="findAllComment(${pagination.startPage - 1})" class="page_bt prev">이전 페이지</a>
                    `;
            }

            // 4. 페이지 번호 추가
            html += '<p>';
            for (let i = pagination.startPage; i <= pagination.endPage; i++) {
                html += `<a href="javascript:void(0);" onclick="findAllComment(${i});">${i}</a>`
            }
            html += '</p>';

            // 5. 다음/끝 페이지 버튼 추가
            if (pagination.existNextPage) {
                html += `
                        <a href="javascript:void(0);" onclick="findAllComment(${pagination.endPage + 1});" class="page_bt next">다음 페이지</a>
                        <a href="javascript:void(0);" onclick="findAllComment(${pagination.totalPageCount});" class="page_bt last">마지막 페이지</a>
                    `;
            }

            // 6. <div class="paging"></div> 태그에 변수 html에 담긴 내용을 렌더링
            const paging = document.querySelector('.paging');
            paging.innerHTML = html;

            // 7. 사용자가 클릭한 페이지 번호(page) 또는 끝 페이지 번호(totalPageCount)에 해당되는 a 태그를 찾아 활성화(active) 처리한 후 클릭 이벤트 제거
            const currentPage = Array.from(paging.querySelectorAll('a')).find(a => (Number(a.text) === page || Number(a.text) === pagination.totalPageCount));
            currentPage.classList.add('on');
            currentPage.removeAttribute('onclick');

            // 8. 페이지 URI 강제 변경
            const postId = new URLSearchParams(location.search).get('id');
            history.replaceState({}, '', location.pathname + `?id=${postId}&page=${currentPage.text}`);
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
				<button type="button" class="btn_close" onclick="closeCommentUpdatePopup();"><span><i
						class="far fa-times-circle"></i></span></button>
			</div>
		</div>
	</div>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>