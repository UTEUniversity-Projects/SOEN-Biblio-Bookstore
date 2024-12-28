<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
    .btn-responded {
        background-color: #28a745; /* Màu xanh lá cho "Đã phản hồi" */
        color: white;
    }

    .btn-not-responded {
        background-color: #6c757d; /* Màu xám cho "Chưa phản hồi" */
        color: white;
    }

    .btn-responded:hover {
        background-color: #218838; /* Màu xanh đậm khi hover */
    }

    .btn-not-responded:hover {
        background-color: #5a6268; /* Màu xám đậm khi hover */
    }
</style>

<div class="cr-main-content">
    <div class="container-fluid">
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <ul>
                    <li><a href="index.html">Carrot</a></li>
                    <li>Trung tâm hỗ trợ khách hàng</li>
                </ul>
            </div>
        </div>

        <div class="row">

            <div class="col-md-12">
                <div class="cr-card product-list" id="ordertbl">
                    <div class="cr-card-header">
                        <h4 class="cr-card-title">Trung tâm hỗ trợ khách hàng</h4>
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
                                            data-value="NOT_RESPONDED"
                                    >
                                        Chưa phản hồi
                                    </a>
                                    <a
                                            class="dropdown-item"
                                            href="#"
                                            data-value="RESPONDED"
                                    >
                                        Đã phản hồi
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="cr-card-content card-default">
                        <!-- Bảng Yêu Cầu -->
                        <div class="table-responsive">
                            <table id="support-cus-list" class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Khách hàng</th>
                                    <th>Yêu cầu</th>
                                    <th>Ngày gửi</th>
                                    <th>Trạng thái</th>
                                    <th width="11%">Hành động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="countResponded" value="0" />
                                <c:set var="countNotResponded" value="0" />
                                <c:forEach items="${supportRequests}" var="request">
                                    <tr>
                                        <td><c:out value="${request.customer.fullName}" /></td>
                                        <td><c:out value="${request.title}" /></td>
                                        <td><c:out value="${request.createdAt}"/></td>
                                        <c:choose>
                                            <c:when test="${request.responseSupport != null}">
                                                <td data-status="RESPONDED">
                                                        <span class="status status__completed">
                                                            Đã phản hồi
                                                        </span>
                                                </td>
                                                <c:set var="countResponded" value="${countResponded + 1}" />
                                            </c:when>
                                            <c:otherwise>
                                                <td data-status="NOT_RESPONDED">
                                                        <span class="status status__pending">
                                                            Chưa phản hồi
                                                        </span>
                                                </td>
                                                <c:set var="countNotResponded" value="${countNotResponded + 1}" />
                                            </c:otherwise>
                                        </c:choose>

                                        <td>
                                            <a class="btn
                                                <c:choose>
                                                    <c:when test="${request.responseSupport != null}">
                                                        btn-responded
                                                    </c:when>
                                                    <c:otherwise>
                                                        btn-not-responded
                                                    </c:otherwise>
                                                </c:choose>"
                                               href="${pageContext.request.contextPath}/staff/support-customer-dashboard?action=detail&id=${request.id}">
                                                Phản hồi
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Hiển thị tổng và phần trăm bên ngoài bảng -->
                        <div class="summary">
                            <strong>Tổng cộng:</strong><br />
                            Đã phản hồi: <strong>${countResponded}</strong>,<br />
                            Chưa phản hồi: <strong>${countNotResponded}</strong>,<br />
                            Phần trăm chưa phản hồi:
                            <strong>
                                <c:choose>
                                    <c:when test="${countResponded + countNotResponded > 0}">
                                        <fmt:formatNumber
                                                value="${(countNotResponded * 100.0) / (countResponded + countNotResponded)}"
                                                type="number" maxFractionDigits="2" />
                                        %
                                    </c:when>
                                    <c:otherwise>
                                        0%
                                    </c:otherwise>
                                </c:choose>
                            </strong>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
