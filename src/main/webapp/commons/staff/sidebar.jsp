<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- sidebar -->
<div class="cr-sidebar-overlay"></div>
<div class="cr-sidebar" data-mode="light">
    <div class="cr-sb-logo">
        <a href="${pageContext.request.contextPath}/staff/product-dashboard" class="sb-full">
            <img src="${pageContext.request.contextPath}/assets/commons/img/logo/biblio.png" alt="logo"/>
        </a>
        <a href="${pageContext.request.contextPath}/staff/product-dashboard" class="sb-collapse">
            <img src="${pageContext.request.contextPath}/assets/commons/img/logo/collapse-logo.png" alt="logo"/>
        </a>
    </div>
    <div class="cr-sb-wrapper">
        <div class="cr-sb-content">
            <ul class="cr-sb-list">
                <li class="cr-sb-item sb-drop-item">
                    <a href="javascript:void(0)" class="cr-drop-toggle">
                        <i class="ri-dashboard-3-line"></i>
                        <span class="condense">
                            Dashboard
                            <i class="drop-arrow ri-arrow-down-s-line"></i>
                        </span>
                    </a>
                    <ul class="cr-sb-drop condense">
                        <li>
                            <a href="${pageContext.request.contextPath}/staff/product-dashboard" class="cr-page-link drop">
                                <i class="ri-checkbox-blank-circle-line"></i>
                                Danh sách sản phẩm
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/staff/order-dashboard" class="cr-page-link drop">
                                <i class="ri-checkbox-blank-circle-line"></i>
                                Danh sách đơn hàng
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/staff/support-customer-dashboard" class="cr-page-link drop">
                                <i class="ri-checkbox-blank-circle-line"></i>
                                Trung tâm hỗ trợ
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="cr-sb-item-separator"></li>
            </ul>
        </div>
    </div>
</div>