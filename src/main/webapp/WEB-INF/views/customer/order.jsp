<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/customer/scss/style.css">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- Modal -->
<div class="modal fade" id="cancelOrderModal" tabindex="-1" aria-labelledby="cancelOrderModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cancelOrderModalLabel">Xác nhận hủy đơn hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn hủy đơn hàng này không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" id="confirmCancelOrder" class="btn btn-success">Xác nhận</button>
            </div>
        </div>
    </div>
</div>

<div class="bg-gray-100 py-5">
    <div class="max-w-[1024px] mx-auto">
        <!-- Thanh bộ lọc trạng thái -->
        <div id="order-filters" class="flex justify-evenly items-center bg-white py-3 mb-5 border border-gray-200">
            <c:set var="currentStatus" value="${param.status != null ? param.status : 'all'}"/>
            <a href="?status=all" class="tag ${currentStatus == 'all' ? 'text-[#26aa99] font-bold' : 'text-gray-500'}">Tất
                cả</a>
            <a href="?status=WAITING_CONFIRMATION"
               class="tag ${currentStatus == 'WAITING_CONFIRMATION' ? 'text-[#26aa99] font-bold' : 'text-gray-500'}">Đang
                xử lý</a>
            <a href="?status=COMPLETE_DELIVERY"
               class="tag ${currentStatus == 'COMPLETE_DELIVERY' ? 'text-[#26aa99] font-bold' : 'text-gray-500'}">Hoàn
                tất giao hàng</a>
            <a href="?status=CANCELED"
               class="tag ${currentStatus == 'CANCELED' ? 'text-[#26aa99] font-bold' : 'text-gray-500'}">Đã hủy</a>
        </div>

        <!-- Danh sách đơn hàng -->
        <div id="order-list" class="mt-5">
            <!-- Lặp qua danh sách các đơn hàng -->
            <c:forEach var="order" items="${orders}">
                <div class="order-item mb-5 p-4 bg-white border border-gray-200 rounded relative"
                     data-order-id="${order.id}">
                    <!-- Thời gian đơn hàng nằm góc trên bên trái -->
                    <div class="absolute top-3 left-3 text-sm text-gray-600">
                            ${order.orderDate}
                    </div>

                    <!-- Trạng thái -->
                    <div class="flex justify-end mb-3 bottom-container">
                        <a href="${pageContext.request.contextPath}/order-detail?orderId=${order.id}"
                           class="text-sm cursor-pointer status status--${order.statusStyle}"
                           data-status="${order.status}">
                                ${order.status.description}
                        </a>
                    </div>

                    <!-- Sản phẩm -->
                    <c:forEach var="orderProduct" items="${order.products}" varStatus="loopStatus">
                        <div class="${loopStatus.index > 0 ? 'my-hidden' : ''} flex items-center mb-3 border-bottom">
                            <div class="flex items-center book-item w-full">
                                <!-- Hình ảnh sản phẩm -->
                                <div class="flex-shrink-0">
                                    <img src="${pageContext.request.contextPath}${orderProduct.imagePath}"
                                         alt="${orderProduct.title}">
                                </div>

                                <!-- Thông tin sản phẩm -->
                                <div class="book-info flex justify-between items-center w-full pl-4">
                                    <!-- Tên sản phẩm -->
                                    <div>
                                        <h4 style="margin-bottom: 20px; font-size: 24px;">${orderProduct.title}</h4>
                                        <p>${orderProduct.publisherName}</p>
                                    </div>

                                    <!-- Thông tin giá bán và số lượng nằm bên phải -->
                                    <div class="text-right">
                                        <p class="price-value">${orderProduct.sellingPrice}</p>
                                        <p>x${orderProduct.quantity}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- Nút Xem thêm/Thu gọn -->
                    <c:if test="${fn:length(order.products) > 1}">
                        <div class="flex justify-start w-full cursor-pointer text-[#26aa99]"
                             onclick="toggleItems('${order.id}')">
                            <span id="toggle-text-${order.id}">Xem thêm ▼</span>
                        </div>
                    </c:if>

                    <!-- Tổng tiền -->
                    <div class="text-right">
                        <h3 class="text-lg font-bold">Thành tiền:
                            <span class="price-value">${order.finalPrice}</span>
                        </h3>
                    </div>

                    <div class="button-container mt-5 text-right">
                        <!-- Buttons for orders with specific statuses -->
                        <c:if test="${order.status == 'COMPLETE_DELIVERY'}">
<%--                            <button class="px-4 py-2 bg-[#26aa99] rounded text-white hover:bg-[#158d7d]"--%>
<%--                                    onclick="alert('Chức năng đang được phát triển!'); return false;">Đặt lại đơn hàng--%>
<%--                            </button>--%>
                            <button class="px-4 py-2 bg-[#ff9800] rounded text-white hover:bg-[#e58900] transition"
                                    onclick="window.location.href='${pageContext.request.contextPath}/return-order?orderId=${order.id}'">
                                Hoàn trả
                            </button>
                        </c:if>

                        <c:if test="${order.status == 'WAITING_CONFIRMATION'}">
                            <button class="btn-cancel-order px-4 py-2 bg-[#e91e4c] rounded text-white hover:bg-[#d0173f]"
                                    data-order-id="${order.id}">Hủy đơn hàng
                            </button>
                        </c:if>

<%--                        <c:if test="${order.status == 'CANCELED'}">--%>
<%--                            <button class="px-4 py-2 bg-[#26aa99] rounded text-white hover:bg-[#158d7d]"--%>
<%--                                    onclick="alert('Chức năng đang được phát triển!'); return false;">Mua lại đơn hàng--%>
<%--                            </button>--%>
<%--                        </c:if>--%>

                        <!-- Nút luôn hiển thị -->
                        <button
                                class="px-4 py-2 rounded text-black border border-gray-200 hover:bg-gray-100"
                                onclick="window.location.href='${pageContext.request.contextPath}/contact-us'">
                            Liên hệ người bán
                        </button>
                    </div>
                </div>
            </c:forEach>

            <!-- No orders message -->
            <c:if test="${empty orders}">
                <p class="text-center text-gray-500">Không có đơn hàng nào để hiển thị.</p>
            </c:if>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/customer/js/cancel-order.js" type="module"></script>
<script defer src="${pageContext.request.contextPath}/assets/customer/js/order-list.js"></script>