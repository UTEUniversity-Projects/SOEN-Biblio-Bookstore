<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5>Danh sách khuyến mãi</h5>
                <ul>
                    <li><a href="index.html">Biblio</a></li>
                    <li>Danh sách khuyến mãi</li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="tab-promotion">
                    <div class="tab-list-promotion">
                        <div class="tab-item-promotion active" data-tab="1">Discount</div>
                        <div class="tab-item-promotion" data-tab="2">Voucher</div>
                        <div class="tab-item-promotion" data-tab="3">Freeship</div>
                    </div>
                </div>
                <div class="cr-card card-default ">
                    <div class="cr-card-content promotion-list" data-tab="1" style="width: 100%;">
                        <table class="table table-hover promotion-data-table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Mã</th>
                                <th>Tiêu Đề</th>
                                <th>%</th>
                                <th>Trạng thái</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="promotion" items="${promotions}">
                                <c:if test="${promotion.type == 'DISCOUNT'}">
                                    <tr class="promotion-row" data-href="${pageContext.request.contextPath}/owner/promotion-details?id=${promotion.id}">
                                    <td>
                                        <img class="tbl-thumb" src="/assets/owner/img/product/1.jpg" alt="Product Image">
                                    </td>
                                    <td>${promotion.code}</td>
                                    <td>${promotion.title}</td>
                                    <td>${promotion.percentDiscount} %</td>
                                    <td class="cod" id="status-${promotion.id}">${promotion.status}</td>
                                </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="cr-card-content promotion-list" data-tab="2" style="width: 100%;">
                        <table class="table table-hover promotion-data-table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Mã</th>
                                <th>Tiêu Đề</th>
                                <th>Số tiền giảm</th>
                                <th>Số lượt còn lại</th>
                                <th>Trạng thái</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="promotion" items="${promotions}">
                                <c:if test="${promotion.type == 'VOUCHER'}">
                                    <tr class="promotion-row" data-href="${pageContext.request.contextPath}/owner/promotion-details?id=${promotion.id}">
                                        <td>
                                            <img class="tbl-thumb" src="/assets/owner/img/product/1.jpg" alt="Product Image">
                                        </td>
                                        <td>${promotion.code}</td>
                                        <td>${promotion.title}</td>
                                        <td>${promotion.discountLimit} đ</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${promotion.quantity == -1}">
                                                    Không giới hạn
                                                </c:when>
                                                <c:otherwise>
                                                    ${promotion.quantity}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td class="cod" id="status-${promotion.id}">${promotion.status}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="cr-card-content promotion-list" data-tab="3" style="width: 100%;">
                        <table class="table table-hover promotion-data-table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Mã</th>
                                <th>Tiêu Đề</th>
                                <th>Số tiền giảm</th>
                                <th>Số lượt còn lại</th>
                                <th>Trạng thái</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="promotion" items="${promotions}">
                                <c:if test="${promotion.type == 'FREESHIP'}">
                                    <tr class="promotion-row" data-href="${pageContext.request.contextPath}/owner/promotion-details?id=${promotion.id}">
                                        <td>
                                            <img class="tbl-thumb" src="/assets/owner/img/product/1.jpg" alt="Product Image">
                                        </td>
                                        <td>${promotion.code}</td>
                                        <td>${promotion.title}</td>
                                        <td>${promotion.discountLimit} đ</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${promotion.quantity == -1}">
                                                    Không giới hạn
                                                </c:when>
                                                <c:otherwise>
                                                    ${promotion.quantity}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td class="cod" id="status-${promotion.id}">${promotion.status}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/owner/js/manage-promotion.js" defer></script>

