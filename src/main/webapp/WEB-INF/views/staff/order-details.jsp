<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/staff/product-dashboard">Biblio</a></li>
                    <li><a href="${pageContext.request.contextPath}/staff/order-dashboard">Danh sách đơn hàng</a></li>
                    <li>Chi tiết đơn hàng</li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card">
                    <div class="cr-card-header">
                        <div class="cr-card-info">
                            <p id="order-id" class="cr-card-id" data-order-id="${order.id}">Mã đơn hàng #${order.id}</p>
                            <p class="cr-card-customer">
                                <i class="ri-account-pin-box-line"></i>
                                ${order.customer.fullName}
                            </p>
                            <p class="cr-card-phone-number">
                                <i class="ri-phone-line"></i>
                                ${order.customer.phoneNumber}
                            </p>
                            <p class="cr-card-location">
                                <i class="ri-map-pin-line"></i>
                                ${order.shipping.address.fullAddress}
                            </p>
                            <p class="cr-card-note">
                                <i class="ri-sticky-note-line"></i>
                                ${order.note}
                            </p>
                            <p class="cr-card-shipping">
                                <i class="ri-truck-line"></i>
                                ${order.shipping.shippingUnit}
                            </p>
                        </div>
                        <div>
                            <p id="order-status"
                               class="cr-card-status cr-card-status--${order.statusStyle}"
                               data-status="${order.status}">
                                ${order.status.description}
                            </p>
                        </div>
                    </div>

                    <div class="cr-card-content">
                        <div class="stepper">
                            <div class="stepper__step">
                                <div class="stepper__step-icon">
                                    <i class="ri-survey-line"></i>
                                </div>
                                <div class="stepper__step-text">
                                    Đơn hàng đã đặt
                                </div>
                                <div class="stepper__step-date">
                                </div>
                            </div>

                            <div class="stepper__step">
                                <div class="stepper__step-icon">
                                    <i class="ri-pencil-line"></i>
                                </div>
                                <div class="stepper__step-text">
                                    Xác nhận đơn hàng
                                </div>
                                <div class="stepper__step-date">
                                </div>
                            </div>

                            <div class="stepper__step">
                                <div class="stepper__step-icon">
                                    <i class="ri-truck-line"></i>
                                </div>
                                <div class="stepper__step-text">
                                    Chờ giao hàng
                                </div>
                                <div class="stepper__step-date">
                                </div>
                            </div>

                            <div class="stepper__step">
                                <div class="stepper__step-icon">
                                    <i class="ri-check-line"></i>
                                </div>
                                <div class="stepper__step-text">
                                    Hoàn tất đơn hàng
                                </div>
                                <div class="stepper__step-date">
                                </div>
                            </div>

                            <div class="stepper__step">
                                <div class="stepper__step-icon">
                                    <i class="ri-star-line"></i>
                                </div>
                                <div class="stepper__step-text">
                                    Đánh giá
                                </div>
                                <div class="stepper__step-date">

                                </div>
                            </div>

                            <div class="stepper__line">
                                <div class="stepper__line-background">

                                </div>
                                <div class="stepper__line-foreground">

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="cr-card-content">
                        <div class="cr-card-content-header">
                            <p class="heading">Danh sách sản phẩm</p>
                        </div>
                        <div class="cr-card-content-body">
                            <div class="table-responsive">
                                <table
                                        id="order-product-data-table"
                                        class="table table-hover"
                                >
                                    <thead>
                                    <tr>
                                        <th width="5%"></th>
                                        <th>Sản phẩm</th>
                                        <th>Khuyến mãi</th>
                                        <th>Số lượng</th>
                                        <c:if test="${order.status == 'REQUEST_REFUND' || order.status == 'REFUNDED'}">
                                            <th>Số lượng hoàn trả</th>
                                        </c:if>
                                        <th>Giá</th>
                                        <th>Tổng tiền</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="product" items="${order.products}">
                                        <tr class="product-row" data-href="${pageContext.request.contextPath}/staff/product-details?id=${product.bookTemplateId}">
                                            <td>
                                                <img
                                                        class="tbl-thumb"
                                                        src="${pageContext.request.contextPath}${product.imagePath}"
                                                        alt="${product.title}"
                                                />
                                            </td>
                                            <td>${product.title}</td>
                                            <td>
                                                <span class="discount-percent status status__complete_delivery">
                                                    ${product.discountPercent}
                                                </span>
                                            </td>
                                            <td>${product.quantity}</td>
                                            <c:if test="${order.status == 'REQUEST_REFUND' || order.status == 'REFUNDED'}">
                                                <td>
                                                    <c:forEach var="returnItem" items="${returnBook.items}">
                                                        <c:if test="${returnItem.title == product.title}">
                                                            ${returnItem.quantity}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                            </c:if>
                                            <td class="price-value">${product.sellingPrice}</td>
                                            <td class="price-value">${product.totalPrice}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <c:if test="${order.status == 'REQUEST_REFUND' || order.status == 'REFUNDED'}">
                        <div class="cr-card-content row">
                            <input id="return-book-id" value="${returnBook.id}" hidden>
                            <div class="col-xxl-4 col-xl-5 col-md-6 col-12 mb-24">
                                <div class="vehicle-detail-banner banner-content clearfix">
                                    <div class="banner-slider">
                                        <div class="slider slider-for">
                                            <c:forEach var="imageUrl" items="${returnBook.proof}">
                                                <div class="slider-banner-image">
                                                    <div class="zoom-image-hover">
                                                        <img
                                                                src="${imageUrl}"
                                                                alt="product-image"
                                                                class="product-image"
                                                        />
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="slider slider-nav thumb-image">
                                            <c:forEach var="imageUrl" items="${returnBook.proof}">
                                                <div class="thumbnail-image">
                                                    <div class="thumbImg">
                                                        <img src="${imageUrl}" alt="product-thumbnail"/>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xxl-8 col-xl-7 col-md-6 col-12 mb-24">
                                <div class="return-reason-container">
                                    <div class="return-reason-header">
                                        <p class="return-reason-title">Lý do trả hàng: ${returnBook.reason.value}</p>
                                        <p class="cr-card-date">
                                            <i class="ri-calendar-2-line"></i>
                                                ${returnBook.createdAt}
                                        </p>
                                    </div>
                                    <div class="return-reason-body">
                                        <p class="return-reason-label">Mô tả chi tiết:</p>
                                        <p class="return-reason-description">
                                                ${returnBook.description}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <div class="cr-card-content">
                        <div class="price-summary">
                            <div class="summary-row">
                                <div class="summary-label">Tổng tiền hàng</div>
                                <div class="summary-value price-value">${order.totalPrice}</div>
                            </div>
                            <div class="summary-row">
                                <div class="summary-label">Phí vận chuyển</div>
                                <div class="summary-value price-value">${order.shipping.shippingFee}</div>
                            </div>
                            <c:forEach var="promotion" items="${order.promotions}">
                                <div class="summary-row">
                                    <c:if test="${promotion.promotionType == 'FREESHIP'}">
                                        <div class="summary-label">Giảm giá phí vận chuyển</div>
                                    </c:if>
                                    <c:if test="${promotion.promotionType == 'VOUCHER'}">
                                        <div class="summary-label">Voucher từ Shop</div>
                                    </c:if>
                                    <div class="summary-value price-value minus-value">${promotion.discountAmount}</div>
                                </div>
                            </c:forEach>
                            <div class="summary-row total-row">
                                <div class="summary-label">Thành tiền</div>
                                <div class="summary-value price-value total-value">${order.finalPrice}</div>
                            </div>
                            <div class="summary-row">
                                <div class="summary-label">Phương thức thanh toán</div>
                                <div class="summary-value">${order.paymentMethod}</div>
                            </div>
                        </div>
                    </div>

                    <div class="btn-container cr-card-content d-grid gap-3 d-md-flex justify-content-md-end
                        <c:if
                            test="${order.status != 'REQUEST_REFUND' && order.status != 'WAITING_CONFIRMATION'}">d-none
                        </c:if>"
                    >
                        <button id="btn-cancel" class="btn btn-outline-danger">Từ chối</button>
                        <button id="btn-confirm" class="cr-btn-primary">Xác nhận</button>
                    </div>

                    <div class="btn-container-transport cr-card-content d-grid gap-3 d-md-flex justify-content-md-end
                         <c:if test="${order.status != 'PACKING'}">d-none</c:if>">
                        <button id="btn-transport" class="cr-btn-primary">Tiến hành vận chuyển</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</div>

<!-- region Modal -->
<%--CancelOrderModal--%>
<div class="modal fade" id="cancelOrderModal" tabindex="-1" aria-labelledby="cancelOrderModalLabel" aria-hidden="true">
    <input class="order-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cancelOrderModalLabel">Thông báo tới khách hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="feedbackForm">
                    <div class="mb-3">
                        <label for="cancelContent" class="form-label">Lý do từ chối</label>
                        <textarea id="cancelContent" name="cancelContent" class="form-control"
                                  placeholder="Nhập lý do ..."
                                  rows="4">
                        </textarea>
                        <p id="error-message">Vui lòng nhập lý do!</p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn cr-btn-primary" id="sendReason">Gửi</button>
            </div>
        </div>
    </div>
</div>

<%--ConfirmModal--%>
<div class="modal fade" id="confirmOrderModal" tabindex="-1" aria-labelledby="confirmOrderModalLabel"
     aria-hidden="true">
    <input class="order-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmOrderModalLabel">Bạn có chắc muốn xác nhận đơn hàng này không?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn cr-btn-primary" id="confirmOrder">Xác nhận</button>
            </div>
        </div>
    </div>
</div>

<%--TransportModal--%>
<div class="modal fade" id="transportOrderModal" tabindex="-1" aria-labelledby="transportOrderModalLabel"
     aria-hidden="true">
    <input class="order-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="transportOrderModalLabel">Bạn có chắc muốn vận chuyển đơn hàng này
                    không?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button
                        type="button"
                        class="btn cr-btn-primary"
                        id="transportOrder"
                        data-action="confirm">Xác nhận
                </button>
            </div>
        </div>
    </div>
</div>

<!-- endregion -->

<!-- region VENDOR JS -->
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/jquery.zoom.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/mixitup.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/range-slider.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/aos.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/swiper-bundle.min.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/vendor/slick.min.js" defer></script>
<!-- endregion -->
<script src="${pageContext.request.contextPath}/assets/commons/js/format-discount-percent.js"></script>
<script src="${pageContext.request.contextPath}/assets/staff/js/order-details.js" defer></script>