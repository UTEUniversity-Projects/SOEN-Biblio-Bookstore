<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Product -->
<div class="cr-main-content mb-3">
    <div class="container-fluid">
        <!-- region Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <div class="page-title">
                    <h5>Chi tiết sản phẩm</h5>
                </div>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/owner/ecommerce"><b>Biblio</b></a></li>
                    <li><a href="${pageContext.request.contextPath}/owner/product/list"><b>Sản phẩm</b></a></li>
                    <li><b>Chi tiết sản phẩm</b></li>
                </ul>
            </div>
        </div>
        <!-- endregion -->

        <div
                class="row mb-minus-24"
                data-aos="fade-up"
                data-aos-duration="2000"
                data-aos-delay="600"
        >
            <div class="col-xxl-4 col-xl-5 col-md-6 col-12 mb-24">
                <div class="vehicle-detail-banner banner-content clearfix">
                    <div class="banner-slider">
                        <div class="slider slider-for">
                            <c:forEach var="imageUrl" items="${book.imageUrls}">
                                <div class="slider-banner-image">
                                    <div class="zoom-image-hover">
                                        <img
                                                src="${pageContext.request.contextPath}${imageUrl}"
                                                alt="product-image"
                                                class="product-image"
                                        />
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="slider slider-nav thumb-image">
                            <c:forEach var="imageUrl" items="${book.imageUrls}">
                                <div class="thumbnail-image thumbnail-sub-image">
                                    <div class="thumbImg">
                                        <img src="${pageContext.request.contextPath}${imageUrl}"
                                             class="mini-image-shadow" alt="product-thumbnail"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xxl-8 col-xl-7 col-md-6 col-12 mb-24">
                <h2 class="product-title">
                    ${book.title}
                </h2>
                <div class="row">
                    <div class="col-6">
                        <div class="author-translator-line">
                            <p>
                                <c:forEach var="author" items="${book.authors}" varStatus="status">
                                    <span class="name name-inline">${author.name}</span>
                                    <i>(tác giả)</i>
                                    <c:if test="${status.last}">
                                        <c:if test="${not empty book.translators}">
                                            ,
                                        </c:if>
                                    </c:if>
                                    <c:if test="${!status.last}">
                                        ,
                                    </c:if>
                                </c:forEach>
                                <c:forEach var="translator" items="${book.translators}" varStatus="status">
                                    <span class="name name-inline">${translator.name}</span>
                                    <i>(dịch giả)</i>
                                    <c:if test="${!status.last}">
                                        ,
                                    </c:if>
                                </c:forEach>
                            </p>
                        </div>
                        <div class="d-flex align-items-center">
                            <p class="book-avg-rate">${book.avgRating}</p>
                            <div class="cr-star book-review-star">
                                <c:forEach var="i" begin="1" end="5" step="1">
                                    <c:choose>
                                        <c:when test="${book.avgRating >= i}">
                                            <i class="ri-star-fill" style="color: #FFD43B"></i>
                                        </c:when>
                                        <c:when test="${book.avgRating > i - 1 && book.avgRating < i}">
                                            <i class="ri-star-half-line" style="color: #FFD43B"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="ri-star-line" style="color: slategray"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                            <p>(${book.reviewCount} đánh giá)</p>
                            <p class="ms-2 me-2">|</p>
                            <p class="book-sold-count">${book.booksCount} lượt bán</p>
                        </div>
                        <div class="cr-card-customer mini-card-3d-deep" style="margin-top: 20px;">
                            <div class="cr-size-and-weight mt-0 pt-0">
                                <div class="book-format">
                                    ${book.format}
                                </div>
                                <div class="cr-product-price pt-0">
                                    <span class="current-price">
                                        ${book.sellingPrice}
                                            <sup>₫</sup>
                                    </span>
                                </div>
                                <div class="d-flex align-items-center">
                                    <div class="old-price">${book.originPrice}₫</div>
                                    <p class="ms-2 me-2">|</p>
                                    <div class="d-flex align-items-center" style="color: #2b3647;">
                                        <i class="ri-community-line me-1" style="font-size: 20px;"></i>
                                        <p style="color: #2b3647;">${book.publisher}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cr-size-and-weight">
                            <div class="row cr-size-weight">
                                <div class="d-flex col-8">
                                    <h5><span>Tình trạng</span> :</h5>
                                    <div class="cr-kg">
                                        <ul>
                                            <li class="active-color">${book.condition}</li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-4 cr-settings">
                                    <a onclick="rowAction(${book.id}, 'update'); return false;"
                                       class="cr-btn-primary m-r-10 rounded">Cập nhật</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="cr-card vendor-profile rounded-3 ms-3">
                            <div class="cr-card-content">
                                <div>
                                    <h3><b>Thống kê tổng quan</b></h3>
                                    <div class="cr-vendor-info">
                                        <ul>
                                            <div class="d-flex align-items-center">
                                                <li>
                                                    <i class="ri-shopping-basket-line color-primary ps-1 pe-1 icon-large"></i>
                                                    <span class="label">Số đơn :</span>&nbsp;
                                                    <b>${book.salesCount}</b> đơn
                                                </li>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <li>
                                                    <i class="ri-book-mark-line color-success ps-1 pe-1 icon-large"></i>
                                                    <span class="label">Đã bán :</span>&nbsp;
                                                    <b>${book.booksCount}</b> quyển
                                                </li>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <li>
                                                    <i class="ri-money-dollar-circle-line color-warning ps-1 pe-1 icon-large"></i>
                                                    <span class="label">Doanh thu :</span>&nbsp;
                                                    <b class="value-books-sold-this-month">${book.revenue}₫</b>
                                                </li>
                                            </div>
                                        </ul>
                                    </div>
                                </div>
                                <div class="mt-3">
                                    <h3><b>Thống kê tháng này</b></h3>
                                    <div class="cr-vendor-info">
                                        <ul>
                                            <div class="d-flex align-items-center">
                                                <li class="d-flex">
                                                    <i class="ri-shopping-basket-line color-primary ps-1 pe-1 icon-large"></i>
                                                    <span class="label">Số đơn :</span>&nbsp;
                                                    <p><b>${book.salesCountThisMonth}</b> đơn</p>
                                                    <div class="label-card align-items-center ms-3">
                                                        <c:choose>
                                                            <c:when test="${book.perSalesCountThisMonth == 0}">
                                                                <div class="up mt-0 d-flex">
                                                                    <i class="ri-arrow-up-line me-1 icon-growth non-growth"></i>
                                                                    <p class="non-growth">${book.perSalesCountThisMonth}%</p>
                                                                </div>
                                                            </c:when>
                                                            <c:when test="${book.perSalesCountThisMonth > 0}">
                                                                <div class="up mt-0 d-flex">
                                                                    <i class="ri-arrow-up-line me-1 icon-growth"></i>
                                                                    <p class="up-growth">${book.perSalesCountThisMonth}%</p>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="down mt-0 d-flex">
                                                                    <i class="ri-arrow-down-line me-1 icon-growth"></i>
                                                                    <p class="down-growth">${-book.perSalesCountThisMonth}%</p>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </li>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <li class="d-flex">
                                                    <i class="ri-book-mark-line color-success ps-1 pe-1 icon-large"></i>
                                                    <span class="label">Đã bán :</span>&nbsp;
                                                    <p><b>${book.booksCountThisMonth}</b> quyển</p>
                                                    <div class="label-card align-items-center ms-3">
                                                        <c:choose>
                                                            <c:when test="${book.perBooksCountThisMonth == 0}">
                                                                <div class="up mt-0 d-flex">
                                                                    <i class="ri-arrow-up-line me-1 icon-growth non-growth"></i>
                                                                    <p class="non-growth">${book.perBooksCountThisMonth}%</p>
                                                                </div>
                                                            </c:when>
                                                            <c:when test="${book.perBooksCountThisMonth > 0}">
                                                                <div class="up mt-0 d-flex">
                                                                    <i class="ri-arrow-up-line me-1 icon-growth"></i>
                                                                    <p class="up-growth">${book.perBooksCountThisMonth}%</p>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="down mt-0 d-flex">
                                                                    <i class="ri-arrow-down-line me-1 icon-growth"></i>
                                                                    <p class="down-growth">${-book.perBooksCountThisMonth}%</p>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </li>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <li class="d-flex">
                                                    <i class="ri-money-dollar-circle-line color-warning ps-1 pe-1 icon-large"></i>
                                                    <span class="label">Doanh thu :</span>&nbsp;
                                                    <b class="value-books-sold-this-month">${book.revenueThisMonth}₫</b>
                                                    <div class="label-card align-items-center ms-3">
                                                        <c:choose>
                                                            <c:when test="${book.perRevenueThisMonth == 0}">
                                                                <div class="up mt-0 d-flex">
                                                                    <i class="ri-arrow-up-line me-1 icon-growth non-growth"></i>
                                                                    <p class="non-growth">${book.perRevenueThisMonth}%</p>
                                                                </div>
                                                            </c:when>
                                                            <c:when test="${book.perRevenueThisMonth > 0}">
                                                                <div class="up mt-0 d-flex">
                                                                    <i class="ri-arrow-up-line me-1 icon-growth"></i>
                                                                    <p class="up-growth">${book.perRevenueThisMonth}%</p>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="down mt-0 d-flex">
                                                                    <i class="ri-arrow-down-line me-1 icon-growth"></i>
                                                                    <p class="down-growth">${-book.perRevenueThisMonth}%</p>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </li>
                                            </div>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <hr class="my-4">
                <div class="slider slider-nav">
                    <div>
                        <div class="slider-info-title text-center">
                            Số trang
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-file-copy-2-line"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.hardcover} trang
                        </div>
                        <%--                    <div class="cr-product-price">--%>
                        <%--                        <span class="new-price price-value">${(1 - book.discount / 100) * book.sellingPrice}</span>--%>
                        <%--                        <span class="old-price price-value">${book.sellingPrice}</span>--%>
                        <%--                        <span class="discount-percent">${book.discount}</span>--%>
                        <%--                    </div>--%>
                    </div>
                    <div>
                        <div class="slider-info-title text-center">
                            Nhà xuất bản
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-community-line"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.publisher}
                        </div>
                    </div>
                    <div>
                        <div class="slider-info-title text-center">
                            Ngày xuất bản
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-calendar-2-line"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.publicationDate}
                        </div>
                    </div>
                    <div>
                        <div class="slider-info-title text-center">
                            Kích thước
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-git-repository-line"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.size}
                        </div>
                    </div>
                    <div>
                        <div class="slider-info-title text-center">
                            Trọng lượng
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-inbox-line"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.weight} kg
                        </div>
                    </div>
                    <div>
                        <div class="slider-info-title text-center">
                            Ngôn ngữ
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-earth-line"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.languages}
                        </div>
                    </div>
                    <div>
                        <div class="slider-info-title text-center">
                            ISBN-10
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-barcode-line"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.codeISBN10}
                        </div>
                    </div>
                    <div>
                        <div class="slider-info-title text-center">
                            ISBN-13
                        </div>
                        <div class="slider-icon text-center">
                            <i class="ri-barcode-fill"></i>
                        </div>
                        <div class="slider-info-value text-center">
                            ${book.codeISBN13}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div
                class="row"
                data-aos="fade-up"
                data-aos-duration="2000"
                data-aos-delay="600"
        >
            <div class="col-12">
                <div class="cr-paking-delivery card-3d-deep">
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button
                                    class="nav-link active"
                                    id="description-tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#description"
                                    type="button"
                                    role="tab"
                                    aria-controls="description"
                                    aria-selected="true"
                            >
                                Mô tả sản phẩm
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button
                                    class="nav-link"
                                    id="additional-tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#additional"
                                    type="button"
                                    role="tab"
                                    aria-controls="additional"
                                    aria-selected="false"
                            >
                                Thông tin chi tiết
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button
                                    class="nav-link"
                                    id="review-tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#review"
                                    type="button"
                                    role="tab"
                                    aria-controls="review"
                                    aria-selected="false"
                            >
                                Đánh giá sản phẩm
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button
                                    class="nav-link"
                                    id="author-tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#author"
                                    type="button"
                                    role="tab"
                                    aria-controls="author"
                                    aria-selected="false"
                            >
                                Tác giả, Dịch giả
                            </button>
                        </li>
                    </ul>
                    <div class="tab-content" id="myTabContent">
                        <div
                                class="tab-pane fade show active"
                                id="description"
                                role="tabpanel"
                                aria-labelledby="description-tab"
                        >
                            <div class="cr-tab-content">
                                <div class="description-content">
                                    ${book.description}
                                </div>
                            </div>
                        </div>
                        <div
                                class="tab-pane"
                                id="additional"
                                role="tabpanel"
                                aria-labelledby="additional-tab"
                        >
                            <div class="row cr-tab-content">
                                <div class="list info-details col-5 col-md-offset">
                                    <ul class="custom-char-list">
                                        <li>
                                            <label>Danh mục <span>:</span></label>
                                            ${book.category}
                                        </li>
                                        <li>
                                            <label>Số lượng <span>:</span></label>
                                            ${book.quantity} quyển
                                        </li>
                                        <li>
                                            <label>Tác giả <span>:</span></label>
                                            <c:forEach var="author" items="${book.authors}" varStatus="status">
                                                <span class="name name-inline">${author.name}</span>
                                                <c:if test="${!status.last}">
                                                    ,
                                                </c:if>
                                            </c:forEach>
                                        </li>
                                        <li>
                                            <label>Dịch giả <span>:</span></label>
                                            <c:forEach var="translator" items="${book.translators}" varStatus="status">
                                                <span class="name name-inline">${translator.name}</span>
                                                <c:if test="${!status.last}">
                                                    ,
                                                </c:if>
                                            </c:forEach>
                                        </li>
                                        <li>
                                            <label>NXB <span>:</span></label>
                                            ${book.publisher}
                                        </li>
                                        <li>
                                            <label>Ngày XB <span>:</span></label>
                                            ${book.publicationDate}
                                        </li>
                                        <li>
                                            <label>Tái bản <span>:</span></label>
                                            lần thứ ${book.edition}
                                        </li>
                                    </ul>
                                </div>
                                <div class="list info-details col-5">
                                    <ul class="custom-char-list">
                                        <li>
                                            <label>Trọng lượng <span>:</span></label>
                                            ${book.weight} kg
                                        </li>
                                        <li>
                                            <label>Kích thước <span>:</span></label>
                                            ${book.size}
                                        </li>
                                        <li>
                                            <label>Số trang <span>:</span></label>
                                            ${book.hardcover} trang
                                        </li>
                                        <li>
                                            <label>Định dạng <span>:</span></label>
                                            ${book.format}
                                        </li>
                                        <li>
                                            <label>ISBN-10 <span>:</span></label>
                                            ${book.codeISBN10}
                                        </li>
                                        <li>
                                            <label>ISBN-13 <span>:</span></label>
                                            ${book.codeISBN13}
                                        </li>
                                        <li>
                                            <label>Độ tuổi <span>:</span></label>
                                            ${book.recommendedAge}
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div
                                class="tab-pane fade"
                                id="review"
                                role="tabpanel"
                                aria-labelledby="review-tab"
                        >
                            <div class="cr-tab-content-from">
                                <ul class="review-list">
                                    <c:forEach var="review" items="${book.reviews}">
                                        <li class="review-item" data-review-id="${review.id}">
                                            <div class="review-item__image">
                                                <img src="${review.imageUrl}"
                                                     alt="review"/>
                                            </div>
                                            <div class="review-item__content">
                                                <div class="header">
                                                    <div class="header__left">
                                                        <span class="customer-name name">${review.customerName}</span>
                                                        <div class="rating">
                                                            <c:forEach var="i" begin="1" end="5">
                                                                <i class="<c:choose>
                                                                             <c:when test='${i <= review.rating}'>ri-star-s-fill</c:when>
                                                                             <c:otherwise>ri-star-line</c:otherwise>
                                                                         </c:choose>">
                                                                </i>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                                <span class="date">${review.createdAt}</span>

                                                <div class="review-content">
                                                    <span>${review.content}</span>
                                                </div>

                                                <c:if test="${not empty review.responseContent}">
                                                    <div class="response-review">
                                                        <div class="response-title">Phản Hồi Của Người Bán</div>
                                                        <div class="response-text">${review.responseContent}</div>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div
                                class="tab-pane fade"
                                id="author"
                                role="tabpanel"
                                aria-labelledby="author-tab"
                        >
                            <div class="cr-tab-content">
                                <div class="description">
                                    <c:forEach var="author" items="${book.authors}">
                                        <div class="row d-flex author-item introduction-area">
                                            <div class="col-3">
                                                <div class="col-xl-12">
                                                    <div class="cr-vendor-block-detail">
                                                        <div class="avatar-preview cr-preview">
                                                            <div class="imagePreview cr-div-preview">
                                                                <img class="cr-image-preview image-shadow rounded-3"
                                                                     src="${pageContext.request.contextPath}${author.avatar}"
                                                                     alt="edit">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-8">
                                                <span class="name"><b>${author.name}</b> (<i>Tác giả</i>)</span>
                                                <div class="introduction-content">
                                                    <span>${author.introduction}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <c:forEach var="translator" items="${book.translators}">
                                        <div class="row d-flex author-item introduction-area">
                                            <div class="col-3">
                                                <div class="col-xl-12">
                                                    <div class="cr-vendor-block-detail">
                                                        <div class="avatar-preview cr-preview">
                                                            <div class="imagePreview cr-div-preview">
                                                                <img class="cr-image-preview image-shadow rounded-3"
                                                                     src="${pageContext.request.contextPath}${translator.avatar}"
                                                                     alt="edit">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-8">
                                                <span class="name"><b>${translator.name}</b> (<i>Dịch giả</i>)</span>
                                                <div class="introduction-content">
                                                    <span>${translator.introduction}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>const contextPath = "<%=request.getContextPath() %>";</script>
<script src="${pageContext.request.contextPath}/assets/owner/js/manage/manage-product.js" defer></script>

<!-- region VENDOR JS -->
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/jquery.zoom.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/mixitup.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/range-slider.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/aos.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/swiper-bundle.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/slick.min.js" defer></script>
<!-- endregion -->

<script src="${pageContext.request.contextPath}/assets/commons/js/format-discount-percent.js"></script>

<script src="${pageContext.request.contextPath}/assets/owner/js/product-details.js" defer></script>

<style>
    .non-growth {
        color: slategray;
    }

    .up-growth {
        color: #2bbb93;
    }

    .down-growth {
        color: #f90c4c;
    }

    .icon-growth {
        font-size: 120%;
    }

    .icon-large {
        font-size: 20px;
    }

    .value-books-sold-this-month {
        background-color: #ffedde;
        color: #ffa04f;
        font-weight: bold;
        border-radius: 7px;
        padding: 2px;
    }

    .book-format {
        font-size: 14px;
        font-weight: bold;
    }

    .thumbnail-sub-image {
        margin: 10px 5px 20px 0;
    }

    .author-translator-line {
        font-size: 80%;
    }

    .product-title {
        font-family: 'Nunito Sans', sans-serif;
        font-weight: bold;
        font-size: 200%;
        color: #2b3647;
    }

    .page-title {
        margin-right: 20px;
    }

    .book-avg-rate {
        margin-right: 5px;
        font-size: 130%;
        font-weight: bold;
        color: #2b3647;
        font-family: Nunito, sans-serif
    }

    .book-review-star {
        margin-right: 10px;
    }

    .book-sold-count {
        background-color: #dcfcee;
        color: #30cb83;
        font-weight: bold;
        border-radius: 10px;
        padding: 3px 5px 3px 5px;
        font-family: Nunito, sans-serif;
    }

    .customer-name {
        color: #484d54;
    }

    .slider-info-title {
        font-size: 12px;
        color: #485568;
    }

    .slider-info-value {
        font-size: 12px;
        font-weight: bold;
        color: #485568;
    }

    .slider-icon {
        font-size: 24px;
        margin-top: 10px;
        margin-bottom: 10px;
        color: #485568;
    }

    .name-inline {
        color: #64b496;
        font-style: italic;
        font-size: 105%;
        font-weight: 600;
        font-family: Nunito, sans-serif;
    }

    .current-price {
        font-family: "Be Vietnam Pro", sans-serif;
        font-size: 32px;
        font-weight: 600;
        color: #64b496;
    }

    .old-price {
        text-decoration: line-through;
        font-size: 14px;
        color: #7a7a7a;
    }

    .introduction-area {
        margin-top: 20px;
    }

    .introduction-content, .introduction-content p {
        width: 100%;
        padding: 10px;
        font-size: 15px;
        color: slategray;
        text-align: justify;
        box-sizing: border-box;
    }

    .description-content {
        width: 100%;
        padding: 30px;
        text-align: justify;
        box-sizing: border-box;
        font-size: 15px;
        color: slategray;
    }

    .info-details {
        margin: 0 0 30px 30px;
    }

    .custom-char-list {
        list-style: none;
        margin-left: 10px;
    }

    .custom-char-list li::before {
        content: "●";
        color: slategray;
        font-size: 16px;
        margin-right: 10px;
    }

    ul.custom-char-list li label {
        font-size: 14px;
    }

    .card-3d-deep {
        background: #fff;
        border-radius: 10px;
        padding: 20px;
        position: relative;
        overflow: visible;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1),
        0 8px 10px rgba(0, 0, 0, 0.1),
        0 16px 24px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .card-3d-deep:hover {
        box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1),
        0 12px 20px rgba(0, 0, 0, 0.15),
        0 24px 32px rgba(0, 0, 0, 0.2);
    }

    .mini-card-3d-deep {
        background: #fff;
        border-radius: 10px;
        padding: 20px;
        position: relative;
        overflow: visible;
        box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1),
        0 4px 5px rgba(0, 0, 0, 0.1),
        0 8px 12px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .mini-card-3d-deep:hover {
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1),
        0 6px 10px rgba(0, 0, 0, 0.15),
        0 12px 16px rgba(0, 0, 0, 0.2);
    }

    .image-shadow {
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        object-fit: cover;
    }

    .image-shadow:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
        object-fit: cover;
    }

    .mini-image-shadow {
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
        object-fit: cover;
    }

    .mini-image-shadow:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
        object-fit: cover;
    }
</style>