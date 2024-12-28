<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/staff/product-dashboard">Biblio</a></li>
                    <li>Danh sách sản phẩm</li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card product-list" id="ordertbl">
                    <div class="cr-card-header">
                        <h4 class="cr-card-title">Danh sách sản phẩm</h4>
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
                                            data-value="COMING_SOON"
                                    >
                                        Sắp mở bán
                                    </a>
                                    <a class="dropdown-item" href="#" data-value="ON_SALE">
                                        Đang bán
                                    </a>
                                    <a
                                            class="dropdown-item"
                                            href="#"
                                            data-value="OUT_OF_STOCK"
                                    >
                                        Hết hàng
                                    </a>
                                    <a
                                            class="dropdown-item"
                                            href="#"
                                            data-value="STOP_SELLING"
                                    >
                                        Ngừng kinh doanh
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cr-card-content card-default">
                        <div class="table-responsive">
                            <table id="product-data-table" class="table table-hover">
                                <thead>
                                <tr>
                                    <th width="5%"></th>
                                    <th width="20%">Tiêu đề</th>
                                    <th>Giá bán</th>
                                    <th>Tồn kho</th>
                                    <th>Đã bán</th>
                                    <th>Ngày mở bán</th>
                                    <th>
                                        <span>Trạng thái</span>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="book" items="${books}">
                                    <tr class="product-row" data-href="/staff/product-details?id=${book.id}">
                                        <td>
                                            <img
                                                    class="tbl-thumb"
                                                    src="${pageContext.request.contextPath}${book.imageUrl}"
                                                    alt="Product Image"
                                            />
                                        </td>
                                        <td>${book.title}</td>
                                        <td class="price-value">${book.price}</td>
                                        <td>${book.quantity}</td>
                                        <td>${book.soldCount}</td>
                                        <td>${book.publicationDate}</td>
                                        <td data-status="${book.status}">
                                            <span class="status status__${book.statusStyle}">${book.status.description}</span>
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