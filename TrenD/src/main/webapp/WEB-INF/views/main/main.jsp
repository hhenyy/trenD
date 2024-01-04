<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="../include/metalink.jsp" %>
    <script>
        //구글 트렌드 목록
        $(function () {
            // var test = '${test}'
            // console.log("test", test);
            /*           $.ajax({
                           type: "GET",
                           url: "googlelist",
                           success: function (result) {
                               console.log("result", result);

                               var content = "";
                               $.each(result, function (index, item) {
                                   // var content = "<tr><td>" + (index + 1) + "</td><td>" + item + "</td></tr>";
                                   var content =
                                       "  <div class=\"accordion-item\">\n" +
                                       "                                        <h2 class=\"accordion-header\" id=\"headingOne\">\n" +
                                       "                                            <button class=\"accordion-button\" type=\"button\" data-bs-toggle=\"collapse\"\n" +
                                       "                                                    data-bs-target=\"#collapseOne\" aria-expanded=\"true\"\n" +
                                       "                                                    aria-controls=\"collapseOne\">\n" +
                                       (index + 1) + "\n" + item +
                                       "                                            </button>\n" +
                                       "                                        </h2>\n" +
                                       "                                        <div id=\"collapseOne\" class=\"accordion-collapse collapse show\"\n" +
                                       "                                             aria-labelledby=\"headingOne\" data-bs-parent=\"#accordionExample\">\n" +
                                       "                                            <div class=\"accordion-body\">\n" +
                                       "                                                <strong>This is the first item's accordion body.</strong> It is hidden\n" +
                                       "                                                by default, until the collapse plugin adds the appropriate classes that\n" +
                                       "                                                we use to style each element. These classes control the overall\n" +
                                       "                                                appearance, as well as the showing and hiding via CSS transitions. You\n" +
                                       "                                                can modify any of this with custom CSS or overriding our default\n" +
                                       "                                                variables. It's also worth noting that just about any HTML can go within\n" +
                                       "                                                the <code>.accordion-body</code>, though the transition does limit\n" +
                                       "                                                overflow.\n" +
                                       "                                            </div>\n" +
                                       "                                        </div>\n" +
                                       "                                    </div>"

                                   $("#googlelist").append(content);
                               });
                           },
                           error: function (xhr, status, error) {
                               console.error("Error:", error);
                           }
                       });//ajax end*/

            //SERP_API 구글리스트
            $.ajax({
                type: "GET",
                url: "mylist",
                success: function (result) {
                    console.log("result", result);

                    var content = "";
                    $.each(result.daily_searches, function (index, search) {
                        var date = search.date;

                        var queries = search.searches.map(function (s) {
                            // return s.query;
                            return {
                                query: s.query,
                                traffic: s.traffic
                            };
                        });

                        var searchData = search.searches.map(function (searchItem) {
                            // Extract information from articles
                            var articlesData = searchItem.articles.map(function (article) {
                                return {
                                    title: article.title,
                                    source: article.source,
                                    link: article.link,
                                    snippet: article.snippet,
                                    thumbnail: article.thumbnail,
                                    date: article.date
                                };
                            });

                            return {
                                articles: articlesData
                            };
                        });

                        console.log("searchData", searchData);
                        console.log("index", index);
                        console.log("date", date);
                        console.log("queries", queries);

                        content =
                            "<div class='div_date'>" +
                            "<h2><br>" + date + "</h2>" +
                            "</div>";

                        // Start building the accordion content
                        $.each(queries, function (idx, s) {
                            var accordionId = "accordion" + idx;
                            content +=
                                "  <div class=\"accordion-item\">\n" +
                                "    <h2 class=\"accordion-header\" id=\"heading" + accordionId + "\">\n" +
                                "      <button class=\"accordion-button collapsed\" type=\"button\" data-bs-toggle=\"collapse\"\n" +
                                "              data-bs-target=\"#collapse" + accordionId + "\" aria-expanded=\"false\"\n" +
                                "              aria-controls=\"collapse" + accordionId + "\">\n" +
                                "<div class='col-lg-7'>" +
                                "<p><h4>" + (idx + 1) + "\n" + s.query + "</h4></p>";

                            // Append article information
                            var firstArticle = searchData[idx].articles[0];
                            content +=
                                "<p><a href='" + firstArticle.link + "' target='_blank' style='color: #0d8919;'>" + firstArticle.title + "</a></p>" +
                                //  "<p><strong>Title:</strong> " + firstArticle.title + "</p>" +
                                "<p>" + firstArticle.source + "\n" + firstArticle.date + "/검색수" + s.traffic + "</p>" +
                                "</div>" +
                                "<div class='col-lg-4 text-end'>" +
                                "<img src='" + firstArticle.thumbnail + "' alt='thumbnail' class='img-thumbnail'>" +
                                "</div>" +
                                "      </button>\n" +
                                "    </h2>\n" +
                                "    <div id=\"collapse" + accordionId + "\" class=\"accordion-collapse collapse\"\n" +
                                "         aria-labelledby=\"heading" + accordionId + "\" data-bs-parent=\"#accordionExample\">\n" +
                                "      <div class=\"accordion-body\">"+
                                "<div class='col-lg-12 text-end'>" +
                                "    <button class=\"btn btn-success\" onclick=\"viewCommunityPost('" + s.query + "')\">커뮤니티 글보기</button>"+
                                "</div>"+
                                "<strong>"+"관련 뉴스"+"</strong>";

                            searchData[idx].articles.forEach(function (article) {
                                content +=
                                    "<div><div>" +
                                    "    <p><img src='" + article.thumbnail + "' alt='thumbnail' style='float: right;'></p>" +
                                    "</div>" +
                                    "<p><a href='" + article.link + "' target='_blank' style='color: #0d8919;'>" + article.title + "</a></p>" +
                                    // "<p><strong>Title:</strong> " + article.title + "</p>" +
                                    "<p>" + article.source + "\n" + article.date + "</p>" +
                                    "<p>" + article.snippet + "</p><br></div>";
                            });

                            content +=
                                "      </div>\n" +
                                "    </div>\n" +
                                "  </div>";
                        });  //안쪽 $.each() end

                        $("#mylist").append(content);
                    }); //바깥 $.each() end

                },
                error: function (xhr, status, error) {
                    console.error("Error:", error);
                    console.error("Status:", status);
                    console.error("XHR:", xhr);
                }

            });//ajax end
        });


    </script>
</head>

<body>
<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/sidebar.jsp"/>
<main id="main" class="main">


    <!-- Default Tabs -->
    <%--    <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="date-tab" data-bs-toggle="tab" data-bs-target="#date" type="button"
                        role="tab" aria-controls="date" aria-selected="true">일별 인기 급상승 검색어
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button"
                        role="tab" aria-controls="profile" aria-selected="false">나이/연령별 인기 급상승 검색어
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button"
                        role="tab" aria-controls="contact" aria-selected="false">나의 맞춤 인기 급상승 검색어
                </button>
            </li>
        </ul>--%>


    <div class="tab-content pt-2" id="myTabContent">


        <div class="tab-pane fade show active" id="date" role="tabpanel" aria-labelledby="date-tab">
            <div class="pagetitle">
                <%--  <h1>date<h1>
                                  <nav>
                                      <ol class="breadcrumb">
                                          <li class="breadcrumb-item"><ra href="index.html">Home</a></li>
                                          <li class="breadcrumb-item">Components</li>
                                          <li class="breadcrumb-item active">Accordion</li>
                                      </ol>
                                  </nav>--%>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row justify-content-center">
                    <div class="col-lg-9">

                        <div class="card">
                            <div class="card-body">
                                <%--                                <h5 class="card-title">Default Accordion</h5>--%>
                                <!-- Default Accordion -->
                                <div class="accordion" id="accordionExample">
                                    <div id="mylist"></div>
                                </div><!-- End Default Accordion Example -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>


        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
            <div class="pagetitle">
                <h1>Accordion</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Components</li>
                        <li class="breadcrumb-item active">Accordion</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-6">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Default Accordion</h5>

                                <!-- Default Accordion -->
                                <div class="accordion" id="accordionExample2">
                                    <div id=""></div>
                                </div><!-- End Default Accordion Example -->

                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>


        <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
            <div class="pagetitle">
                <h1>Accordion</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                        <li class="breadcrumb-item">Components</li>
                        <li class="breadcrumb-item active">Accordion</li>
                    </ol>
                </nav>
            </div><!-- End Page Title -->

            <section class="section">
                <div class="row">
                    <div class="col-lg-6">

                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Default Accordion</h5>

                                <!-- Default Accordion -->
                                <div class="accordion" id="accordionExample3">
                                    <div class="accordion-item">
                                        <h2 class="accordion-header" id="headingOne3">
                                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                                    data-bs-target="#collapseOne" aria-expanded="true"
                                                    aria-controls="collapseOne">
                                                Accordion Item #1
                                            </button>
                                        </h2>
                                        <div id="collapseOne3" class="accordion-collapse collapse show"
                                             aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <strong>This is the first item's accordion body.</strong> It is hidden
                                                by
                                                default, until the collapse plugin adds the appropriate classes that we
                                                use
                                                to
                                                style each element. These classes control the overall appearance, as
                                                well as
                                                the
                                                showing and hiding via CSS transitions. You can modify any of this with
                                                custom
                                                CSS or overriding our default variables. It's also worth noting that
                                                just
                                                about
                                                any HTML can go within the <code>.accordion-body</code>, though the
                                                transition
                                                does limit overflow.
                                            </div>
                                        </div>
                                    </div>
                                    <div class="accordion-item">
                                        <h2 class="accordion-header" id="headingTwo3">
                                            <button class="accordion-button collapsed" type="button"
                                                    data-bs-toggle="collapse"
                                                    data-bs-target="#collapseTwo" aria-expanded="false"
                                                    aria-controls="collapseTwo">
                                                Accordion Item #2
                                            </button>
                                        </h2>
                                        <div id="collapseTwo3" class="accordion-collapse collapse"
                                             aria-labelledby="headingTwo"
                                             data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <strong>This is the second item's accordion body.</strong> It is hidden
                                                by
                                                default, until the collapse plugin adds the appropriate classes that we
                                                use
                                                to
                                                style each element. These classes control the overall appearance, as
                                                well as
                                                the
                                                showing and hiding via CSS transitions. You can modify any of this with
                                                custom
                                                CSS or overriding our default variables. It's also worth noting that
                                                just
                                                about
                                                any HTML can go within the <code>.accordion-body</code>, though the
                                                transition
                                                does limit overflow.
                                            </div>
                                        </div>
                                    </div>
                                    <div class="accordion-item">
                                        <h2 class="accordion-header" id="headingThree3">
                                            <button class="accordion-button collapsed" type="button"
                                                    data-bs-toggle="collapse"
                                                    data-bs-target="#collapseThree" aria-expanded="false"
                                                    aria-controls="collapseThree">
                                                Accordion Item #3
                                            </button>
                                        </h2>
                                        <div id="collapseThree3" class="accordion-collapse collapse"
                                             aria-labelledby="headingThree" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <strong>This is the third item's accordion body.</strong> It is hidden
                                                by
                                                default, until the collapse plugin adds the appropriate classes that we
                                                use
                                                to
                                                style each element. These classes control the overall appearance, as
                                                well as
                                                the
                                                showing and hiding via CSS transitions. You can modify any of this with
                                                custom
                                                CSS or overriding our default variables. It's also worth noting that
                                                just
                                                about
                                                any HTML can go within the <code>.accordion-body</code>, though the
                                                transition
                                                does limit overflow.
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- End Default Accordion Example -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>


    </div><!-- End Default Tabs -->
</main><!-- End #main -->

<%@ include file="../include/footer.jsp" %>

</body>
</html>
