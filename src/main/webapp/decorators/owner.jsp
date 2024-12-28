<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="keywords" content="owner, dashboard, ecommerce, panel"/>
        <meta name="description" content="Biblio - Owner."/>

        <title>Owner</title>

        <!-- App favicon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/commons/img/logo/collapse-logo.png"/>

        <!-- Icon CSS -->
        <link href="${pageContext.request.contextPath}/assets/owner/css/vendor/materialdesignicons.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/assets/owner/css/vendor/remixicon.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/assets/owner/css/vendor/owl.carousel.min.css" rel="stylesheet"/>

        <!-- Font -->
        <link rel="preconnect" href="https://fonts.googleapis.com"/>
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
        <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:ital,opsz,wght@0,6..12,200..1000;1,6..12,200..1000&display=swap" rel="stylesheet" />
        <link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>

        <!-- Vendor CSS -->
        <link
                href="${pageContext.request.contextPath}/assets/owner/css/vendor/datatables.bootstrap5.min.css"
                rel="stylesheet"
        />
        <link
                href="${pageContext.request.contextPath}/assets/owner/css/vendor/responsive.datatables.min.css"
                rel="stylesheet"
        />
        <link href="${pageContext.request.contextPath}/assets/owner/css/vendor/daterangepicker.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/assets/owner/css/vendor/simplebar.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/assets/owner/css/vendor/bootstrap.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/assets/owner/css/vendor/apexcharts.css" rel="stylesheet"/>
        <link
                href="${pageContext.request.contextPath}/assets/owner/css/vendor/jquery-jvectormap-1.2.2.css"
                rel="stylesheet"
        />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/owner/css/vendor/animate.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/owner/css/vendor/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/owner/css/vendor/aos.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/owner/css/vendor/range-slider.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/owner/css/vendor/swiper-bundle.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/owner/css/vendor/jquery.slick.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/owner/css/vendor/slick-theme.css" />

        <!-- Extension CSS -->
        <link href="${pageContext.request.contextPath}/assets/owner/css/extension/quill-snow.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/assets/owner/css/extension/select2.css" rel="stylesheet"/>

        <!-- Main CSS -->
        <link id="main-css" href="${pageContext.request.contextPath}/assets/owner/css/style.css" rel="stylesheet"/>
</head>
<body>
<div id="toast"></div>
<main class="wrapper sb-default ecom">
    <!-- Loader -->
    <div id="cr-overlay">
        <div class="loader"></div>
    </div>
    <%@include file="/commons/owner/header.jsp" %>
    <%@include file="/commons/owner/sidebar.jsp" %>
    <%@include file="/commons/owner/notify-bar.jsp" %>
    <decorator:body/>
    <%@include file="/commons/owner/footer.jsp" %>
</main>
        <script> const contextPath = "<%=request.getContextPath() %>";</script>

        <!-- region Vendor Custom JS -->
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/jquery-3.6.4.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/simplebar.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/apexcharts.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/jquery-jvectormap-world-mill-en.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/owl.carousel.min.js"></script>
        <!-- Data Tables -->
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/jquery.datatables.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/datatables.bootstrap5.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/datatables.responsive.min.js"></script>
        <!-- Calendar -->
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/jquery.simple-calendar.js"></script>
        <!-- Date Range Picker -->
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/daterangepicker.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/daterangepicker.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/vendor/date-range.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/date-range/daterange.js"></script>
        <!-- Extension -->
        <script src="${pageContext.request.contextPath}/assets/owner/js/extension/lib-lottie.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/extension/lib-quill.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/extension/lib-select2.js"></script>
        <!-- endregion -->

        <!-- region Main Custom -->
        <script src="${pageContext.request.contextPath}/assets/owner/js/main.js" type="module" defer></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/data/ecommerce-chart-data.js"></script>
        <script src="${pageContext.request.contextPath}/assets/owner/js/toast.js"></script>
</body>

</html>
