<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

        <li class="nav-item">
            <a class="nav-link " href="${pageContext.request.contextPath}/">
                <i class="bi bi-newspaper"></i>
                <span>실시간 트렌드</span>
            </a>
        </li><!-- End Dashboard Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#components-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-list-stars"></i><span>커뮤니티</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="components-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/trend/posts">
                        <i class="bi bi-circle"></i><span>트렌드 게시판</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/community/posts">
                        <i class="bi bi-circle"></i><span>자유 게시판</span>
                    </a>
                </li>
             </ul>
        </li><!-- End Components Nav -->


        <li class="nav-item">
            <a class="nav-link collapsed" href="${pageContext.request.contextPath}/Statistics">
                <i class="ri ri-bar-chart-2-line"></i>
                <span>통계</span>
            </a>
        </li>

<!--
        <li class="nav-heading">Team</li>

        <li class="nav-item">
            <a class="nav-link collapsed" href="https://github.com/sun4red">
                <i class="bi bi-person"></i>
                <span>선홍</span>
            </a>
        </li>
-->
    </ul>

</aside><!-- End Sidebar-->