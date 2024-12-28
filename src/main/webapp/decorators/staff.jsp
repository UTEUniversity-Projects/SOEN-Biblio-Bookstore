<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- region Meta -->
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="keywords" content="staff, dashboard"/>
    <meta name="description" content="Biblio - Staff"/>
    <!-- endregion -->

    <title>Staff</title>

    <!-- Logo -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/commons/img/logo/collapse-logo.png"/>

    <!-- region Icon CSS -->
    <link
            href="${pageContext.request.contextPath}/assets/staff/css/vendor/materialdesignicons.min.css"
            rel="stylesheet"
    />
    <link href="${pageContext.request.contextPath}/assets/staff/css/vendor/remixicon.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/assets/staff/css/vendor/owl.carousel.min.css" rel="stylesheet"/>
    <!-- endregion -->

    <!-- region Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link
            href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet"
    />
    <!-- endregion -->

    <!-- region Vendor CSS -->
    <link
            href="${pageContext.request.contextPath}/assets/staff/css/vendor/datatables.bootstrap5.min.css"
            rel="stylesheet"
    />
    <link
            href="${pageContext.request.contextPath}/assets/staff/css/vendor/responsive.datatables.min.css"
            rel="stylesheet"
    />
    <link href="${pageContext.request.contextPath}/assets/staff/css/vendor/daterangepicker.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/assets/staff/css/vendor/simplebar.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/assets/staff/css/vendor/bootstrap.min.css" rel="stylesheet"/>
    <link
            href="${pageContext.request.contextPath}/assets/staff/css/vendor/jquery-jvectormap-1.2.2.css"
            rel="stylesheet"
    />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/staff/css/vendor/animate.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/staff/css/vendor/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/staff/css/vendor/aos.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/staff/css/vendor/range-slider.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/staff/css/vendor/swiper-bundle.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/staff/css/vendor/jquery.slick.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/staff/css/vendor/slick-theme.css"/>
    <!-- endregion -->

    <!-- Main CSS -->
    <link id="main-css" href="${pageContext.request.contextPath}/assets/staff/css/style.css" rel="stylesheet"/>
</head>
<body>
<div id="toast"></div>
<main class="wrapper sb-default ecom">
    <!-- Loader -->
    <div id="cr-overlay">
        <div class="loader"></div>
    </div>
    <%@include file="/commons/staff/header.jsp" %>
    <%@include file="/commons/staff/sidebar.jsp" %>
    <%@include file="/commons/staff/notify-bar.jsp" %>
    <decorator:body/>
    <%@include file="/commons/staff/footer.jsp" %>
</main>

<script> const contextPath = "<%=request.getContextPath() %>";</script>

<!-- region Vendor Custom JS -->
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/jquery-3.6.4.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/simplebar.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/jquery-jvectormap-1.2.2.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/jquery-jvectormap-world-mill-en.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/owl.carousel.min.js"></script>
<!-- Data Tables -->
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/jquery.datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/datatables.bootstrap5.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/datatables.responsive.min.js"></script>
<!-- Caleddar -->
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/jquery.simple-calendar.js"></script>
<!-- Date Range Picker -->
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/daterangepicker.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/date-range.js"></script>

<!-- endregion JS-->

<!-- region Main Custom JS -->
<script src="${pageContext.request.contextPath}/assets/staff/js/toast.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/main.js"></script>
<!-- endregion -->

</body>
</html>
