
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/staff/product-dashboard">Carrot</a></li>
                    <li>Danh sách đơn hàng</li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card" id="ordertbl">
                    <div class="cr-card-header">
                        <h4 class="cr-card-title">Danh sách đơn hàng</h4>
                        <div class="header-tools">
                            <a href="javascript:void(0)" class="m-r-10 cr-full-card">
                                <i class="ri-fullscreen-line"></i>
                            </a>
                            <div class="cr-date-range dots m-r-10">
                                <span></span>
                            </div>
                            <div>
                                <button
                                        type="button"
                                        class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                        data-bs-toggle="dropdown"
                                        aria-haspopup="true"
                                        aria-expanded="false"
                                        data-display="static"
                                >
                                    <span id="selected-text">Tất cả</span>
                                    <span class="sr-only">
                                        <i class="ri-arrow-down-s-line"></i>
                                    </span>
                                </button>
                                <div class="dropdown-menu dropdown-menu__status">
                                    <a class="dropdown-item" href="#" data-value="ALL">
                                        Tất cả
                                    </a>
                                    <a
                                            class="dropdown-item"
                                            href="#"
                                            data-value="WAITING_CONFIRMATION"
                                    >
                                        Chờ xác nhận
                                    </a>
                                    <a class="dropdown-item" href="#" data-value="PACKING">
                                        Đang đóng gói
                                    </a>
                                    <a class="dropdown-item" href="#" data-value="SHIPPING">
                                        Đang vận chuyển
                                    </a>
                                    <a
                                            class="dropdown-item"
                                            href="#"
                                            data-value="COMPLETE_DELIVERY"
                                    >
                                        Đã hoàn thành
                                    </a>
                                    <a class="dropdown-item" href="#" data-value="CANCELED">
                                        Đã hủy
                                    </a>
                                    <a
                                            class="dropdown-item"
                                            href="#"
                                            data-value="REQUEST_REFUND"
                                    >
                                        Yêu cầu hoàn trả
                                    </a>
                                    <a class="dropdown-item" href="#" data-value="REFUNDED">
                                        Đã hoàn trả
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cr-card-content card-default">
                        <div class="table-responsive">
                            <table id="order-data-table" class="table table-hover">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Mã</th>
                                    <th>Khách hàng</th>
                                    <th>Ngày lập đơn</th>
                                    <th>Tổng tiền</th>
                                    <th>Thanh toán</th>
                                    <th>Trạng thái</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="order" items="${orders}" varStatus="status">
                                    <tr class="order-row" data-href="${pageContext.request.contextPath}/staff/order-details?id=${order.id}">
                                        <td class="token">${status.index + 1}</td>
                                        <td>${order.id}</td>
                                        <td>${order.customerName}</td>
                                        <td>${order.orderDate}</td>
                                        <td class="price-value">${order.totalPrice}</td>
                                        <td>${order.paymentMethod}</td>
                                        <td data-status="${order.status}">
                                            <span class="status status__${order.statusStyle}">
                                                    ${order.status.description}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
