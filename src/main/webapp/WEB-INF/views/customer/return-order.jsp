<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/scss/style.css">--%>

<!-- Return Order Form -->
<section class="section-return py-5 bg-light">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8">

                <!-- Form Sản phẩm -->
                <%--                <form action="${pageContext.request.contextPath}/return-order" method="POST"--%>
                <%--                      enctype="multipart/form-data">--%>
                <div id="order-id" class="order-item mb-5 p-4 bg-white border border-gray-200 rounded relative"
                     data-order-id="${orderDetail.id}">
                    <div class="form-logo text-center mb-4">
                        <h2 style="font-size: 32px; font-weight: bold; ">Chọn sách hoàn trả</h2>
                    </div>

                    <div id="product-list" class="return-product-box p-4 rounded-lg shadow-sm bg-white ">
                        <hr style="border-top: 3px solid #004437; width: 100%; margin: 10px 0;"/>

                        <c:forEach var="product" items="${orderDetail.products}" varStatus="loopStatus">
                            <div class="${loopStatus.index > 0 ? 'my-hidden' : ''} flex items-center mb-3 border-bottom">
                                <div class="product-item d-flex justify-content-between align-items-center py-3 w-full"
                                     style="margin-bottom: 20px;">
                                    <div class="d-flex align-items-center">
                                        <div class="product-image me-3">
                                            <img src="${pageContext.request.contextPath}${product.imagePath}"
                                                 alt="${product.title}" class="img-fluid" style="width: 150px;"/>
                                        </div>
                                        <div>
                                            <h4 style="margin-bottom: 20px; font-size: 24px;">${product.title}</h4>
                                            <p>${product.publisherName}</p>
                                        </div>
                                    </div>
                                    <div class="product-price text-right d-flex flex-column">
                                        <span class="mt-5 quantity text-muted">x${product.quantity}</span>
                                        <span class="price-value new-price text-success fw-bold">${product.sellingPrice}</span>

                                        <!-- Input số lượng hoàn trả -->
                                        <div class="mt-10 d-flex align-items-center">
                                            <label for="return-quantity-${product.bookTemplateId}"
                                                   class="form-label text-muted me-2">
                                                Số lượng hoàn trả:
                                            </label>
                                            <input
                                                    type="number"
                                                    id="return-quantity-${product.bookTemplateId}"
                                                    class="form-control return-quantity"
                                                    name="returnQuantities[${product.bookTemplateId}]"
                                                    value="0"
                                                    min="0"
                                                    max="${product.quantity}"
                                                    style="width: 60px;"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <!-- Nút Xem thêm/Thu gọn -->
                        <c:if test="${fn:length(orderDetail.products) > 1}">
                            <div class="flex justify-start w-full cursor-pointer text-[#26aa99]"
                                 onclick="toggleItems()">
                                <span id="toggle-text">Xem thêm ▼</span>
                            </div>
                        </c:if>

                    </div>

                </div>

                <!-- Form Lý do hoàn trả -->
                <div class="return-reason-form p-4 bg-white shadow rounded">
                    <!-- Return Form Details -->
                    <div class="form-group mt-4">
                        <label for="reason" class="form-label">Lý do hoàn trả:</label>
                        <select id="reason" name="reason" class="form-control rounded" required>
                            <option value="">Chọn lý do</option>
                            <c:forEach var="reason" items="${EReasonReturn}">
                                <option value="${reason.name()}">
                                        ${reason.value}
                                </option>
                            </c:forEach>
                        </select>
                        <div id="reason-error" class="error-message">Không được bỏ trống</div>
                    </div>

                    <div class="form-group mt-4">
                        <label for="description" class="form-label">Mô tả chi tiết:</label>
                        <textarea id="description"
                                  name="description"
                                  rows="4"
                                  class="form-control rounded"
                                  placeholder="Mô tả chi tiết lý do hoàn trả..."
                                  required
                        ></textarea>
                        <div id="description-error" class="error-message">Không được bỏ trống</div>
                    </div>

                    <!-- Upload Image Section -->
                    <div class="form-group mt-4">
                        <label for="uploadImage" class="form-label">Thêm hình ảnh (tối đa 5):</label>
                        <input type="file" id="uploadImage" class="form-control" accept="image/*" multiple required>
                        <div id="file-error" class="error-message">Không được bỏ trống</div>
                    </div>

                    <!-- Submit Button -->
                    <div class="w-100 mt-10 mx-1 d-flex gap-3 justify-content-center">
                        <button type="submit" class="cr-btn-secondary">
                            Quay lại
                        </button>
                        <button type="submit" id="btn-confirm" class="cr-button">
                            Yêu cầu hoàn trả
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script defer src="${pageContext.request.contextPath}/assets/customer/js/order-list.js"></script>
<script defer src="${pageContext.request.contextPath}/assets/customer/js/return-book.js" type="module"></script>
