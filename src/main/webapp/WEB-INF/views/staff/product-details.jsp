<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Product -->
<div class="cr-main-content mb-3">
    <div class="container-fluid">
        <!-- region Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/staff/product-dashboard">Biblio</a></li>
                    <li><a href="${pageContext.request.contextPath}/staff/product-dashboard">Danh sách sản phẩm</a></li>
                    <li>Chi tiết sản phẩm</li>
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
                                                src="${imageUrl}"
                                                alt="product-image"
                                                class="product-image"
                                                loading="lazy"
                                        />
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="slider slider-nav thumb-image">
                            <c:forEach var="imageUrl" items="${book.imageUrls}">
                                <div class="thumbnail-image">
                                    <div class="thumbImg">
                                        <img src="${imageUrl}" alt="product-thumbnail" loading="lazy"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xxl-8 col-xl-7 col-md-6 col-12 mb-24">
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
                                    <c:when test="${book.avgRating >= i}">
                                        <i class="ri-star-fill"></i>
                                    </c:when>
                                    <c:when test="${book.avgRating > i - 1 && book.avgRating < i}">
                                        <i class="ri-star-half-line"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="ri-star-line"></i>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <p>( ${book.reviewCount} đánh giá )</p>
                    </div>
                    <div class="list">
                        <ul>
                            <li>
                                <label>Tác giả <span>:</span></label>
                                ${book.authors[0].name}
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
                                <label>Ngôn ngữ <span>:</span></label>
                                ${book.languages}
                            </li>
                        </ul>
                    </div>
                    <div class="cr-product-price">
                        <span class="new-price price-value">${(1 - book.discount / 100) * book.sellingPrice}</span>
                        <span class="old-price price-value">${book.sellingPrice}</span>
                        <span class="discount-percent">${book.discount}</span>
                    </div>
                    <div class="cr-size-weight">
                        <h5><span>Tình trạng</span> :</h5>
                        <div class="cr-kg">
                            <ul>
                                <li class="active-color">${book.condition}</li>
                            </ul>
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
                <div class="cr-paking-delivery">
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
                                Thông tin tác giả
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
                                <div class="cr-description">
                                    <div class="cr-description">
                                        <p>
                                            ${book.description}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div
                                class="tab-pane"
                                id="additional"
                                role="tabpanel"
                                aria-labelledby="additional-tab"
                        >
                            <div class="cr-tab-content">
                                <div class="list">
                                    <ul>
                                        <li>
                                            <label>Danh mục <span>:</span></label>
                                            ${book.category}
                                        </li>
                                        <li>
                                            <label>Kho <span>:</span></label>
                                            ${book.quantity}
                                        </li>
                                        <li>
                                            <label>Tác giả <span>:</span></label>
                                            ${book.authors[0].name}
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
                                            <label>Ngôn ngữ <span>:</span></label>
                                            ${book.languages}
                                        </li>
                                        <li>
                                            <label>Trọng lượng <span>:</span></label>
                                            ${book.weight} gram
                                        </li>
                                        <li>
                                            <label>Kích thước <span>:</span></label>
                                            ${book.size}
                                        </li>
                                        <li>
                                            <label>Số trang <span>:</span></label>
                                            ${book.hardcover}
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
                                                <img src="${pageContext.request.contextPath}${review.imageUrl}"
                                                     alt="review"
                                                     loading="lazy"/>
                                            </div>
                                            <div class="review-item__content">
                                                <div class="header">
                                                    <div class="header__left">
                                                        <span class="name">${review.customerName}</span>
                                                        <div class="rating">
                                                            <c:forEach var="i" begin="1" end="5">
                                                                <i class="<c:choose>
                                                                             <c:when test='${i <= review.rating}'>ri-star-s-fill</c:when>
                                                                             <c:otherwise>ri-star-line</c:otherwise>
                                                                         </c:choose>">
                                                                </i>
                                                            </c:forEach>
                                                        </div>

                                                        <span class="hidden-review-label
                                                                        <c:if test="${!review.isHidden}">d-none</c:if>">
                                                                Đánh giá bị ẩn
                                                        </span>

                                                    </div>
                                                    <div class="header__right">
                                                        <button type="button"
                                                                class="action-btn action-btn__show
                                                                        <c:if test="${!review.isHidden}">d-none</c:if>"
                                                        >
                                                            <i class="ri-eye-line"></i>
                                                        </button>

                                                        <button type="button"
                                                                class="action-btn action-btn__hide
                                                                        <c:if test="${review.isHidden}">d-none</c:if>"
                                                        >
                                                            <i class="ri-eye-off-line"></i>
                                                        </button>

                                                        <button type="button"
                                                                class="action-btn action-btn__response
                                                                     <c:if test="${review.isHidden || !empty review.responseContent}">d-none</c:if>">
                                                            <i class="ri-reply-line"></i>
                                                        </button>

                                                    </div>
                                                </div>
                                                <span class="date">${review.createdAt}</span>

                                                <div class="review-content">
                                                        ${review.content}
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
                                <div class="cr-description">
                                    <c:forEach var="author" items="${book.authors}">
                                        <div class="author-item">
                                            <div class="author-item--avatar">
                                                <img src="${author.avatar}" alt="${author.name}" loading="lazy">
                                            </div>
                                            <div class="author-item--info">
                                                <span class="name">${author.name} (Tác giả)</span>
                                                <p>${author.introduction}</p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <c:forEach var="translator" items="${book.translators}">
                                        <div class="author-item">
                                            <div class="author-item--avatar">
                                                <img src="${translator.avatar}" alt="${translator.name}" loading="lazy">
                                            </div>
                                            <div class="author-item--info">
                                                <span class="name">${translator.name} (Dịch giả)</span>
                                                <p>${translator.introduction}</p>
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

<!-- region Modal -->
<%--FeedbackModal--%>
<div class="modal fade" id="feedbackModal" tabindex="-1" aria-labelledby="feedbackModalLabel" aria-hidden="true">
    <input class="review-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="feedbackModalLabel">Phản hồi của bạn</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="feedbackForm">
                    <div class="mb-3">
                        <label for="feedbackContent" class="form-label">Nội dung phản hồi</label>
                        <textarea id="feedbackContent" name="feedbackContent" class="form-control"
                                  placeholder="Nhập nội dung phản hồi..."
                                  rows="4"></textarea>
                        <p id="error-message">Vui lòng nhập nội dung!</p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-primary" id="submitFeedback">Gửi</button>
            </div>
        </div>
    </div>
</div>

<%--HideModal--%>
<div class="modal fade" id="hideReviewModal" tabindex="-1" aria-labelledby="hideReviewModalLabel" aria-hidden="true">
    <input class="review-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="hideReviewModalLabel">Bạn có chắc muốn ẩn đánh giá này không?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-primary" id="confirmHideReview">Ẩn đánh giá</button>
            </div>
        </div>
    </div>
</div>

<%--ShowModal--%>
<div class="modal fade" id="showReviewModal" tabindex="-1" aria-labelledby="showReviewModalLabel" aria-hidden="true">
    <input class="review-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="showReviewModalLabel">Bạn có chắc muốn hiển thị đánh giá này không?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-primary" id="confirmShowReview">Hiện đánh giá</button>
            </div>
        </div>
    </div>
</div>
<!-- endregion -->

<!-- region VENDOR JS -->
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/jquery.zoom.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/mixitup.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/range-slider.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/aos.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/swiper-bundle.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/vendor/slick.min.js" defer></script>
<!-- endregion -->
<script src="${pageContext.request.contextPath}/assets/commons/js/format-discount-percent.js"></script>

<script src="${pageContext.request.contextPath}/assets/staff/js/product-details.js" defer></script>