<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.td.TrenD.model.TrendVO" %>
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

        <div class="pagetitle">

            <h1>커뮤니티 게시판</h1>
            <div name="info" style="display: flex; justify-content: space-between" ;>
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
        <!--end 본문-->


        <script>
            function updateForm() {
                location.href = "commUpdateForm?trNo=${post.trNo}"
            }

            function deletePost() {
                var check = confirm('글을 삭제하시겠습니까?');

                if (check) {
                    location.href = "deletePost?trNo=${post.trNo}"
                    alert('글이 삭제되었습니다.');
                } else {

                }
            }

        </script>


        <div>
        </div>


        댓글 공간

    </div>


</main>


<jsp:include page="../include/footer.jsp"/>
</body>
</html>
