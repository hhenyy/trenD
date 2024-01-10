<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- ======= Header ======= -->
<header id="header" class="header fixed-top d-flex align-items-center">

    <div class="d-flex align-items-center justify-content-between">
        <a href="${pageContext.request.contextPath}/" class="logo d-flex align-items-center">
<%--            <img src="assets/img/logo.png" alt="">--%>
            <span class="d-none d-lg-block">TrenD</span>
        </a>
        <i class="bi bi-list toggle-sidebar-btn"></i>
    </div>
    <!-- End Logo -->

    <div class="search-bar">
        <form class="search-form d-flex align-items-center" method="get"
              action="${pageContext.request.contextPath}/totalSearch" id = "totalSearch">
            <input type="text" name="keyword" placeholder="검색"
                   value="${keyword}" title="Enter search keyword" id="keyword">

            <button type="submit" title="Search">
                <i class="bi bi-search"></i>
            </button>
        </form>

        <script>
            $(function(){
                $("#totalSearch").submit(function(){
                    if($("#keyword").val()==""){
                        alert("검색어를 입력하세요");
                        $("#keyword").focus();
                        return false;
                    }
                });
            });

        </script>

    </div>
    <!-- End Search Bar -->

    <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">

            <li class="nav-item d-block d-lg-none">
                <a class="nav-link nav-icon search-bar-toggle " href="#">
                    <i class="bi bi-search"></i>
                </a>
            </li>
            <!-- End Search Icon-->

            <c:choose>
                <c:when test="${not empty sessionScope.userName}">
                    <li class="nav-item dropdown pe-3">
                        <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#"
                           data-bs-toggle="dropdown">
                            <span class="d-none d-md-block dropdown-toggle ps-2">${sessionScope.userName}</span>
                        </a>
                        <!-- End Profile Iamge Icon -->
                        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                            <li class="dropdown-header">
                                <h6>${sessionScope.userName}</h6>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="dropdown-item d-flex align-items-center"
                                   href="${pageContext.request.contextPath}/mypage/userpage">
                                    <i class="bi bi-box-arrow-right"></i>
                                    <span>마이페이지</span>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item d-flex align-items-center"
                                   href="${pageContext.request.contextPath}/editUserForm">

                                    <i class="bi bi-box-arrow-right"></i>
                                    <span>회원정보수정</span>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item d-flex align-items-center"
                                   href="${pageContext.request.contextPath}/logOut">
                                    <i class="bi bi-box-arrow-right"></i>
                                    <span>로그아웃</span>
                                </a>
                            </li>
                        </ul><!-- End Profile Dropdown Items -->
                    </li>
                </c:when>
                <c:otherwise>
                    <!-- 로그인이 안 된 경우에 대한 처리 -->
                    <li class="nav-item dropdown pe-3">
                        <a class="nav-link nav-profile d-flex align-items-center pe-0" href="/loginform">
                            로그인
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>

            <!-- End Profile Nav -->


        </ul>
    </nav><!-- End Icons Navigation -->

</header>
<!-- End Header -->
<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/chart.js/chart.umd.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/echarts/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/quill/quill.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/tinymce/tinymce.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>