<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5>Danh sách khách hàng</h5>
                <ul>
                    <li><a href="index.html">Biblio</a></li>
                    <li>Danh sách khách hàng</li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card card-default product-list">
                    <div class="cr-card-content">
                        <div class="table-responsive">
                            <table id="customer-data-table" class="table table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tên</th>
                                    <th>Email</th>
                                    <th>Số đơn hàng</th>
                                    <th>Trạng thái</th>
                                    <th>Action</th>
                                </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="customer" items="${customers}">
                                        <tr class="customer-row" data-href="${pageContext.request.contextPath}/owner/customer-profile?id=${customer.id}">
                                            <td>${customer.id}</td>
                                            <td>${customer.fullName}</td>
                                            <td>${customer.email}</td>
                                            <td>${customer.orderCount}</td>
                                            <td class="cod" id="status-${customer.id}">${customer.status}</td> <!-- Thêm id duy nhất -->
                                            <td>
                                                <div class="d-flex justify-content-start">
                                                    <button type="button"
                                                            class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                                            data-bs-toggle="dropdown" aria-haspopup="true"
                                                            aria-expanded="false" data-display="static">
                                                        <span class="sr-only"><i class="ri-settings-3-line"></i></span>
                                                    </button>
                                                    <div class="dropdown-menu">
                                                        <c:choose>
                                                            <c:when test="${customer.status == 'ACTIVE'}">
                                                                <a class="dropdown-item pop-positioned-timeout" href="#"
                                                                   onclick="showConfirmationModal(${customer.id}, 'deactivate'); return false;">Vô hiệu hóa tài khoản</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a class="dropdown-item pop-positioned-timeout" href="#"
                                                                   onclick="showConfirmationModal(${customer.id}, 'activate'); return false;">Mở khóa tài khoản</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
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

<div class="modal fade" id="hideReviewModal" tabindex="-1" aria-labelledby="hideReviewModalLabel" aria-hidden="true">
    <input class="review-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="hideReviewModalLabel">Bạn có chắc muốn khóa tài khoản này không?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Không</button>
                <button type="button" class="btn btn-primary" id="confirmHideReview">Có</button>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/assets/owner/js/manage-customer.js" defer></script>
