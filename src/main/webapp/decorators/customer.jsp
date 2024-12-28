<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commons/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta
            name="keywords"
            content="ecommerce, market, shop, mart, cart, deal, multipurpose, marketplace"
    />
    <meta name="description" content="Biblio."/>

    <title>
        <c:choose>
            <c:when test="${breadcrumb != null}">
                ${breadcrumb}
            </c:when>
            <c:otherwise>
                Biblio
            </c:otherwise>
        </c:choose>
    </title>

    <!-- App favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/commons/img/logo/collapse-logo.png"/>

    <!-- Icon CSS -->
    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/assets/customer/css/vendor/materialdesignicons.min.css"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/remixicon.css"/>

    <!-- Vendor -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/animate.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/aos.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/range-slider.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/swiper-bundle.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/jquery.slick.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/vendor/slick-theme.css"/>

    <!-- Main CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/css/style.css?v=1"/>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
          rel="stylesheet">
</head>

<body>

<div id="cr-overlay">
    <div class="page-loading">
        <div>
            <ul>
                <li>
                    <svg fill="currentColor" viewBox="0 0 90 120">
                        <path d="M90,0 L90,120 L11,120 C4.92486775,120 0,115.075132 0,109 L0,11 C0,4.92486775 4.92486775,0 11,0 L90,0 Z M71.5,81 L18.5,81 C17.1192881,81 16,82.1192881 16,83.5 C16,84.8254834 17.0315359,85.9100387 18.3356243,85.9946823 L18.5,86 L71.5,86 C72.8807119,86 74,84.8807119 74,83.5 C74,82.1745166 72.9684641,81.0899613 71.6643757,81.0053177 L71.5,81 Z M71.5,57 L18.5,57 C17.1192881,57 16,58.1192881 16,59.5 C16,60.8254834 17.0315359,61.9100387 18.3356243,61.9946823 L18.5,62 L71.5,62 C72.8807119,62 74,60.8807119 74,59.5 C74,58.1192881 72.8807119,57 71.5,57 Z M71.5,33 L18.5,33 C17.1192881,33 16,34.1192881 16,35.5 C16,36.8254834 17.0315359,37.9100387 18.3356243,37.9946823 L18.5,38 L71.5,38 C72.8807119,38 74,36.8807119 74,35.5 C74,34.1192881 72.8807119,33 71.5,33 Z"></path>
                    </svg>
                </li>
                <li>
                    <svg fill="currentColor" viewBox="0 0 90 120">
                        <path d="M90,0 L90,120 L11,120 C4.92486775,120 0,115.075132 0,109 L0,11 C0,4.92486775 4.92486775,0 11,0 L90,0 Z M71.5,81 L18.5,81 C17.1192881,81 16,82.1192881 16,83.5 C16,84.8254834 17.0315359,85.9100387 18.3356243,85.9946823 L18.5,86 L71.5,86 C72.8807119,86 74,84.8807119 74,83.5 C74,82.1745166 72.9684641,81.0899613 71.6643757,81.0053177 L71.5,81 Z M71.5,57 L18.5,57 C17.1192881,57 16,58.1192881 16,59.5 C16,60.8254834 17.0315359,61.9100387 18.3356243,61.9946823 L18.5,62 L71.5,62 C72.8807119,62 74,60.8807119 74,59.5 C74,58.1192881 72.8807119,57 71.5,57 Z M71.5,33 L18.5,33 C17.1192881,33 16,34.1192881 16,35.5 C16,36.8254834 17.0315359,37.9100387 18.3356243,37.9946823 L18.5,38 L71.5,38 C72.8807119,38 74,36.8807119 74,35.5 C74,34.1192881 72.8807119,33 71.5,33 Z"></path>
                    </svg>
                </li>
                <li>
                    <svg fill="currentColor" viewBox="0 0 90 120">
                        <path d="M90,0 L90,120 L11,120 C4.92486775,120 0,115.075132 0,109 L0,11 C0,4.92486775 4.92486775,0 11,0 L90,0 Z M71.5,81 L18.5,81 C17.1192881,81 16,82.1192881 16,83.5 C16,84.8254834 17.0315359,85.9100387 18.3356243,85.9946823 L18.5,86 L71.5,86 C72.8807119,86 74,84.8807119 74,83.5 C74,82.1745166 72.9684641,81.0899613 71.6643757,81.0053177 L71.5,81 Z M71.5,57 L18.5,57 C17.1192881,57 16,58.1192881 16,59.5 C16,60.8254834 17.0315359,61.9100387 18.3356243,61.9946823 L18.5,62 L71.5,62 C72.8807119,62 74,60.8807119 74,59.5 C74,58.1192881 72.8807119,57 71.5,57 Z M71.5,33 L18.5,33 C17.1192881,33 16,34.1192881 16,35.5 C16,36.8254834 17.0315359,37.9100387 18.3356243,37.9946823 L18.5,38 L71.5,38 C72.8807119,38 74,36.8807119 74,35.5 C74,34.1192881 72.8807119,33 71.5,33 Z"></path>
                    </svg>
                </li>
                <li>
                    <svg fill="currentColor" viewBox="0 0 90 120">
                        <path d="M90,0 L90,120 L11,120 C4.92486775,120 0,115.075132 0,109 L0,11 C0,4.92486775 4.92486775,0 11,0 L90,0 Z M71.5,81 L18.5,81 C17.1192881,81 16,82.1192881 16,83.5 C16,84.8254834 17.0315359,85.9100387 18.3356243,85.9946823 L18.5,86 L71.5,86 C72.8807119,86 74,84.8807119 74,83.5 C74,82.1745166 72.9684641,81.0899613 71.6643757,81.0053177 L71.5,81 Z M71.5,57 L18.5,57 C17.1192881,57 16,58.1192881 16,59.5 C16,60.8254834 17.0315359,61.9100387 18.3356243,61.9946823 L18.5,62 L71.5,62 C72.8807119,62 74,60.8807119 74,59.5 C74,58.1192881 72.8807119,57 71.5,57 Z M71.5,33 L18.5,33 C17.1192881,33 16,34.1192881 16,35.5 C16,36.8254834 17.0315359,37.9100387 18.3356243,37.9946823 L18.5,38 L71.5,38 C72.8807119,38 74,36.8807119 74,35.5 C74,34.1192881 72.8807119,33 71.5,33 Z"></path>
                    </svg>
                </li>
                <li>
                    <svg fill="currentColor" viewBox="0 0 90 120">
                        <path d="M90,0 L90,120 L11,120 C4.92486775,120 0,115.075132 0,109 L0,11 C0,4.92486775 4.92486775,0 11,0 L90,0 Z M71.5,81 L18.5,81 C17.1192881,81 16,82.1192881 16,83.5 C16,84.8254834 17.0315359,85.9100387 18.3356243,85.9946823 L18.5,86 L71.5,86 C72.8807119,86 74,84.8807119 74,83.5 C74,82.1745166 72.9684641,81.0899613 71.6643757,81.0053177 L71.5,81 Z M71.5,57 L18.5,57 C17.1192881,57 16,58.1192881 16,59.5 C16,60.8254834 17.0315359,61.9100387 18.3356243,61.9946823 L18.5,62 L71.5,62 C72.8807119,62 74,60.8807119 74,59.5 C74,58.1192881 72.8807119,57 71.5,57 Z M71.5,33 L18.5,33 C17.1192881,33 16,34.1192881 16,35.5 C16,36.8254834 17.0315359,37.9100387 18.3356243,37.9946823 L18.5,38 L71.5,38 C72.8807119,38 74,36.8807119 74,35.5 C74,34.1192881 72.8807119,33 71.5,33 Z"></path>
                    </svg>
                </li>
                <li>
                    <svg fill="currentColor" viewBox="0 0 90 120">
                        <path d="M90,0 L90,120 L11,120 C4.92486775,120 0,115.075132 0,109 L0,11 C0,4.92486775 4.92486775,0 11,0 L90,0 Z M71.5,81 L18.5,81 C17.1192881,81 16,82.1192881 16,83.5 C16,84.8254834 17.0315359,85.9100387 18.3356243,85.9946823 L18.5,86 L71.5,86 C72.8807119,86 74,84.8807119 74,83.5 C74,82.1745166 72.9684641,81.0899613 71.6643757,81.0053177 L71.5,81 Z M71.5,57 L18.5,57 C17.1192881,57 16,58.1192881 16,59.5 C16,60.8254834 17.0315359,61.9100387 18.3356243,61.9946823 L18.5,62 L71.5,62 C72.8807119,62 74,60.8807119 74,59.5 C74,58.1192881 72.8807119,57 71.5,57 Z M71.5,33 L18.5,33 C17.1192881,33 16,34.1192881 16,35.5 C16,36.8254834 17.0315359,37.9100387 18.3356243,37.9946823 L18.5,38 L71.5,38 C72.8807119,38 74,36.8807119 74,35.5 C74,34.1192881 72.8807119,33 71.5,33 Z"></path>
                    </svg>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="cr-sidebar-overlay"></div>

<%@include file="/commons/customer/header.jsp" %>

<!-- Breadcrumb -->
<section class="section-breadcrumb">
    <div class="cr-breadcrumb-image">
        <div class="container-xl">
            <div class="row">
                <div class="col-lg-12">
                    <div class="cr-breadcrumb-title">
                        <span>
                            <a href="home">Trang chủ</a>
                            <c:if test="${breadcrumb != null}">
                                / ${breadcrumb}
                            </c:if>
                        </span>
                        <h2>${breadcrumb}</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<decorator:body/>
<%@include file="/commons/customer/footer.jsp" %>

<a href="#Top" class="back-to-top result-placeholder">
    <i class="ri-arrow-up-line"></i>
    <div class="back-to-top-wrap">
        <svg viewBox="-1 -1 102 102">
            <path d="M50,1 a49,49 0 0,1 0,98 a49,49 0 0,1 0,-98"/>
        </svg>
    </div>
</a>

<!-- Model -->
<c:forEach var="book" items="${books}">
    <div
            class="modal fade quickview-modal"
            id="quickview-${book.id}"
            aria-hidden="true"
            tabindex="-1"
    >
        <div class="modal-dialog modal-dialog-centered cr-modal-dialog">
            <div class="modal-content">
                <button
                        type="button"
                        class="cr-close-model btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-5 col-sm-12 col-xs-12">
                            <div class="zoom-image-hover modal-border-image">
                                <img
                                        src="${pageContext.request.contextPath}${book.imageUrl}"
                                        alt="product-tab-2"
                                        class="product-image"
                                />
                            </div>
                        </div>
                        <div class="col-md-7 col-sm-12 col-xs-12">
                            <div class="cr-size-and-weight-contain">
                                <h2 class="heading">
                                        ${book.title}
                                </h2>
                            </div>
                            <div class="cr-size-and-weight">
                                <div class="cr-review-star">
                                    <div class="cr-star">
                                        <c:forEach var="i" begin="1" end="5" step="1">
                                            <c:choose>
                                                <c:when test="${book.reviewRate >= i}">
                                                    <i class="ri-star-fill"></i>
                                                </c:when>
                                                <c:when test="${book.reviewRate > i - 1 && book.reviewRate < i}">
                                                    <i class="ri-star-half-line"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="ri-star-line"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                    <p>( ${book.numberOfReviews} Reviews)</p>
                                </div>
                                <div class="cr-product-price">
                                    <span class="new-price price-value">${book.sellingPrice}</span>
                                    <span class="old-price price-value">${book.sellingPrice}</span>
                                </div>
                                <div class="cr-size-weight">
                                    <h5><span>Tình trạng</span> :</h5>
                                    <div class="cr-kg">
                                        <ul>
                                            <li class="active-color">${book.condition}</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="cr-add-card">
                                    <div class="cr-qty-main">
                                        <input
                                                type="text"
                                                value="1"
                                                minlength="1"
                                                maxlength="20"
                                                class="quantity"
                                        />
                                        <button type="button" class="plus">+</button>
                                        <button type="button" class="minus">-</button>
                                    </div>
                                    <div class="cr-add-button">
                                        <button type="button"
                                                class="cr-button cr-btn-secondary cr-shopping-bag add-to-cart-btn"
                                                data-book-id="${book.id}">
                                            Thêm vào giỏ hàng
                                        </button>
                                    </div>
                                    <div class="cr-buy-button">
                                        <button type="button" class="cr-button">
                                            Mua ngay
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<!-- Cart -->
<div class="cr-cart-overlay"></div>
<div class="cr-cart-view">

    <div class="cr-cart-inner">
        <div class="cart-loading mx-auto my-auto hidden">
            <div aria-label="Orange and tan hamster running in a metal wheel" role="img" class="wheel-and-hamster">
                <div class="hamster">
                    <div class="hamster__body">
                        <div class="hamster__head">
                            <div class="hamster__ear"></div>
                            <div class="hamster__eye"></div>
                            <div class="hamster__nose"></div>
                        </div>
                        <div class="hamster__limb hamster__limb--fr"></div>
                        <div class="hamster__limb hamster__limb--fl"></div>
                        <div class="hamster__limb hamster__limb--br"></div>
                        <div class="hamster__limb hamster__limb--bl"></div>
                        <div class="hamster__tail"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="cr-cart-top">
            <div class="cr-cart-title">
                <h6>Giỏ hàng của tôi</h6>
                <button type="button" class="close-cart">×</button>
            </div>
            <c:choose>
            <c:when test="${account == null}">
                <div class="flex items-center justify-center flex-col pt-[100px] pb-[20px] h-full w-full">
                    <p class="text-[16px]">Vui lòng đăng nhập để xem giỏ hàng</p>
                    <div class="empty-cart">
                        <svg viewBox="656 573 264 182" version="1.1" xmlns="http://www.w3.org/2000/svg"
                             xmlns:xlink="http://www.w3.org/1999/xlink">
                            <rect id="bg-line" stroke="none" fill-opacity="0.2" fill="#FFE100" fill-rule="evenodd"
                                  x="656" y="624" width="206" height="38" rx="19"></rect>
                            <rect id="bg-line" stroke="none" fill-opacity="0.2" fill="#FFE100" fill-rule="evenodd"
                                  x="692" y="665" width="192" height="29" rx="14.5"></rect>
                            <rect id="bg-line" stroke="none" fill-opacity="0.2" fill="#FFE100" fill-rule="evenodd"
                                  x="678" y="696" width="192" height="33" rx="16.5"></rect>
                            <g id="shopping-bag" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"
                               transform="translate(721.000000, 630.000000)">
                                <polygon id="Fill-10" fill="#FFA800" points="4 29 120 29 120 0 4 0"></polygon>
                                <polygon id="Fill-14" fill="#FFE100"
                                         points="120 29 120 0 115.75 0 103 12.4285714 115.75 29"></polygon>
                                <polygon id="Fill-15" fill="#FFE100"
                                         points="4 29 4 0 8.25 0 21 12.4285714 8.25 29"></polygon>
                                <polygon id="Fill-33" fill="#FFA800"
                                         points="110 112 121.573723 109.059187 122 29 110 29"></polygon>
                                <polygon id="Fill-35" fill-opacity="0.5" fill="#FFFFFF"
                                         points="2 107.846154 10 112 10 31 2 31"></polygon>
                                <path d="M107.709596,112 L15.2883462,112 C11.2635,112 8,108.70905 8,104.648275 L8,29 L115,29 L115,104.648275 C115,108.70905 111.7365,112 107.709596,112"
                                      id="Fill-36" fill="#FFE100"></path>
                                <path d="M122,97.4615385 L122,104.230231 C122,108.521154 118.534483,112 114.257931,112 L9.74206897,112 C5.46551724,112 2,108.521154 2,104.230231 L2,58"
                                      id="Stroke-4916" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <polyline id="Stroke-4917" stroke="#000000" stroke-width="3" stroke-linecap="round"
                                          stroke-linejoin="round" points="2 41.5 2 29 122 29 122 79"></polyline>
                                <path d="M4,50 C4,51.104 3.104,52 2,52 C0.896,52 0,51.104 0,50 C0,48.896 0.896,48 2,48 C3.104,48 4,48.896 4,50"
                                      id="Fill-4918" fill="#000000"></path>
                                <path d="M122,87 L122,89" id="Stroke-4919" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <polygon id="Stroke-4922" stroke="#000000" stroke-width="3" stroke-linecap="round"
                                         stroke-linejoin="round" points="4 29 120 29 120 0 4 0"></polygon>
                                <path d="M87,46 L87,58.3333333 C87,71.9 75.75,83 62,83 L62,83 C48.25,83 37,71.9 37,58.3333333 L37,46"
                                      id="Stroke-4923" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M31,45 C31,41.686 33.686,39 37,39 C40.314,39 43,41.686 43,45"
                                      id="Stroke-4924" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M81,45 C81,41.686 83.686,39 87,39 C90.314,39 93,41.686 93,45"
                                      id="Stroke-4925" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M8,0 L20,12" id="Stroke-4928" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M20,12 L8,29" id="Stroke-4929" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M20,12 L20,29" id="Stroke-4930" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M115,0 L103,12" id="Stroke-4931" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M103,12 L115,29" id="Stroke-4932" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                                <path d="M103,12 L103,29" id="Stroke-4933" stroke="#000000" stroke-width="3"
                                      stroke-linecap="round"></path>
                            </g>
                            <g id="glow" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"
                               transform="translate(768.000000, 615.000000)">
                                <rect id="Rectangle-2" fill="#000000" x="14" y="0" width="2" height="9"
                                      rx="1"></rect>
                                <rect fill="#000000"
                                      transform="translate(7.601883, 6.142354) rotate(-12.000000) translate(-7.601883, -6.142354) "
                                      x="6.60188267" y="3.14235449" width="2" height="6" rx="1"></rect>
                                <rect fill="#000000"
                                      transform="translate(1.540235, 7.782080) rotate(-25.000000) translate(-1.540235, -7.782080) "
                                      x="0.54023518" y="6.28207994" width="2" height="3" rx="1"></rect>
                                <rect fill="#000000"
                                      transform="translate(29.540235, 7.782080) scale(-1, 1) rotate(-25.000000) translate(-29.540235, -7.782080) "
                                      x="28.5402352" y="6.28207994" width="2" height="3" rx="1"></rect>
                                <rect fill="#000000"
                                      transform="translate(22.601883, 6.142354) scale(-1, 1) rotate(-12.000000) translate(-22.601883, -6.142354) "
                                      x="21.6018827" y="3.14235449" width="2" height="6" rx="1"></rect>
                            </g>
                            <polygon id="plus" stroke="none" fill="#7DBFEB" fill-rule="evenodd"
                                     points="689.681239 597.614697 689.681239 596 690.771974 596 690.771974 597.614697 692.408077 597.614697 692.408077 598.691161 690.771974 598.691161 690.771974 600.350404 689.681239 600.350404 689.681239 598.691161 688 598.691161 688 597.614697"></polygon>
                            <polygon id="plus" stroke="none" fill="#EEE332" fill-rule="evenodd"
                                     points="913.288398 701.226961 913.288398 699 914.773039 699 914.773039 701.226961 917 701.226961 917 702.711602 914.773039 702.711602 914.773039 705 913.288398 705 913.288398 702.711602 911 702.711602 911 701.226961"></polygon>
                            <polygon id="plus" stroke="none" fill="#FFA800" fill-rule="evenodd"
                                     points="662.288398 736.226961 662.288398 734 663.773039 734 663.773039 736.226961 666 736.226961 666 737.711602 663.773039 737.711602 663.773039 740 662.288398 740 662.288398 737.711602 660 737.711602 660 736.226961"></polygon>
                            <circle id="oval" stroke="none" fill="#A5D6D3" fill-rule="evenodd" cx="699.5" cy="579.5"
                                    r="1.5"></circle>
                            <circle id="oval" stroke="none" fill="#CFC94E" fill-rule="evenodd" cx="712.5" cy="617.5"
                                    r="1.5"></circle>
                            <circle id="oval" stroke="none" fill="#8CC8C8" fill-rule="evenodd" cx="692.5" cy="738.5"
                                    r="1.5"></circle>
                            <circle id="oval" stroke="none" fill="#3EC08D" fill-rule="evenodd" cx="884.5" cy="657.5"
                                    r="1.5"></circle>
                            <circle id="oval" stroke="none" fill="#66739F" fill-rule="evenodd" cx="918.5" cy="681.5"
                                    r="1.5"></circle>
                            <circle id="oval" stroke="none" fill="#C48C47" fill-rule="evenodd" cx="903.5" cy="723.5"
                                    r="1.5"></circle>
                            <circle id="oval" stroke="none" fill="#A24C65" fill-rule="evenodd" cx="760.5" cy="587.5"
                                    r="1.5"></circle>
                            <circle id="oval" stroke="#66739F" stroke-width="2" fill="none" cx="745" cy="603"
                                    r="3"></circle>
                            <circle id="oval" stroke="#EFB549" stroke-width="2" fill="none" cx="716" cy="597"
                                    r="3"></circle>
                            <circle id="oval" stroke="#FFE100" stroke-width="2" fill="none" cx="681" cy="751"
                                    r="3"></circle>
                            <circle id="oval" stroke="#3CBC83" stroke-width="2" fill="none" cx="896" cy="680"
                                    r="3"></circle>
                            <polygon id="diamond" stroke="#C46F82" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round" fill="none"
                                     points="886 705 889 708 886 711 883 708"></polygon>
                            <path d="M736,577 C737.65825,577 739,578.34175 739,580 C739,578.34175 740.34175,577 742,577 C740.34175,577 739,575.65825 739,574 C739,575.65825 737.65825,577 736,577 Z"
                                  id="bubble-rounded" stroke="#3CBC83" stroke-width="1" stroke-linecap="round"
                                  stroke-linejoin="round" fill="none"></path>
                        </svg>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
            <ul class="crcart-pro-items">
<%--                <c:forEach var="cartItem" items="${cart.cartItems}">--%>
<%--                    <li>--%>
<%--                        <a href="${pageContext.request.contextPath}/book?id=${cartItem.bookId}"--%>
<%--                           class="crside_pro_img"--%>
<%--                        ><img src="${pageContext.request.contextPath}${cartItem.imageUrl}"--%>
<%--                              alt="${cartItem.title}"--%>
<%--                        /></a>--%>
<%--                        <div class="cr-pro-content">--%>
<%--                            <a href="${pageContext.request.contextPath}/book?id=${cartItem.bookId}"--%>
<%--                               class="cart_pro_title"--%>
<%--                            >${cartItem.title}</a--%>
<%--                            >--%>
<%--                            <span class="cart-price">--%>
<%--                                <span class="new-price price-value">${cartItem.sellingPrice}</span>--%>
<%--                                <span class="old-price price-value">${cartItem.sellingPrice}</span>--%>
<%--                            </span>--%>

<%--                            <div class="cr-cart-qty">--%>
<%--                                <div class="cart-qty-plus-minus">--%>
<%--                                    <button type="button" class="minus">-</button>--%>
<%--                                    <input--%>
<%--                                            type="text"--%>
<%--                                            value="${cartItem.quantity}"--%>
<%--                                            minlength="1"--%>
<%--                                            maxlength="20"--%>
<%--                                            class="quantity"--%>
<%--                                            data-book-id="${cartItem.bookId}"--%>
<%--                                    />--%>
<%--                                    <button type="button" class="plus">+</button>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                            <a href="javascript:void(0)" class="remove-item">×</a>--%>
<%--                        </div>--%>
<%--                    </li>--%>
<%--                </c:forEach>--%>
            </ul>

        </div>

        <div class="view-cart mt-auto mb-5 flex items-center justify-center">

            <div class="cart_btn flex items-center justify-center">
                <a href="${pageContext.request.contextPath}/cart" class="cr-button">Xem giỏ hàng</a>
            </div>
        </div>
        </c:otherwise>
        </c:choose>
    </div>
</div>

<script> const contextPath = "<%=request.getContextPath() %>";</script>
<!-- Vendor Custom -->
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/jquery-3.6.4.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/jquery.zoom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/mixitup.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/range-slider.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/aos.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/swiper-bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/slick.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/vendor/tailwind.min.js"></script>

<!-- Main Custom -->
<script src="${pageContext.request.contextPath}/assets/customer/js/main.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/manage-cart.js" type="module"></script>
<script src="${pageContext.request.contextPath}/assets/commons/js/remember-me.js"></script>

</body>
<div id="toast"></div>

</html>