<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>

<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <div class="d-flex align-products-center">
                    <div class="page-title">
                        <h5>Danh sách sản phẩm</h5>
                    </div>
                    <a href="${pageContext.request.contextPath}/owner/product/create" class="cr-btn default-btn color-primary">
                        Thêm sản phẩm
                    </a>
                </div>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/owner/ecommerce"><b>Biblio</b></a></li>
                    <li><a href="${pageContext.request.contextPath}/owner/product/list"><b>Danh sách sản phẩm</b></a></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card card-default product-list card-3d-deep">
                    <div class="cr-card-content ">
                        <div class="table-responsive">
                            <table id="product_list" class="item-data-table table table-hover rounded-table responsive-table">
                                <thead class="table-secondary">
                                    <tr>
                                        <th style="width: 7%">#</th>
                                        <th style="width: 27%">Tên sản phẩm</th>
                                        <th style="width: 8%">Đã bán</th>
                                        <th style="width: 8%">Đánh giá</th>
                                        <th style="width: 12%">Doanh số</th>
                                        <th style="width: 15%">Giá bán</th>
                                        <th style="width: 12%">Trạng thái</th>
                                        <th style="width: 6%">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="product" items="${books}">
                                        <tr class="item-row" data-id="${product.id}">
                                            <td>
                                                <img
                                                        class="tbl-thumb image-shadow rounded-3"
                                                        src="${pageContext.request.contextPath}${product.imageUrl}"
                                                        alt="Product Image"
                                                />
                                            </td>
                                            <td>
                                                <div>
                                                    <span class="row-highlight">${product.title}</span><br>
                                                    <span class="row-sub-content">${product.publisher}</span>
                                                </div>
                                            </td>
                                            <td><span class="badge bg-primary justify-content-center">${product.booksSold}</span></td>
                                            <td>
                                                <div class="d-flex align-products-center">
                                                    <h6 class="avg-rate">${product.avgRate}</h6>
                                                    <i class="ri-star-fill ps-2" style="color: #FFD43B; font-size: 20px;"></i>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="label-card d-flex align-products-center">
                                                    <c:choose>
                                                        <c:when test="${product.perValueBooksSold == 0}">
                                                            <div class="up mt-0 d-flex">
                                                                <i class="ri-arrow-up-line me-1 icon-growth non-growth"></i>
                                                                <p class="non-growth">${product.perValueBooksSold}%</p>
                                                            </div>
                                                        </c:when>
                                                        <c:when test="${product.perValueBooksSold > 0}">
                                                            <div class="up mt-0 d-flex">
                                                                <i class="ri-arrow-up-line me-1 icon-growth"></i>
                                                                <p class="up-growth">${product.perValueBooksSold}%</p>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="down mt-0 d-flex">
                                                                <i class="ri-arrow-down-line me-1 icon-growth"></i>
                                                                <p class="down-growth">${-product.perValueBooksSold}%</p>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="cr-product-price">
                                                    <span class="new-price">
                                                        ${product.sellingPrice} ₫
                                                    </span>
                                                </div>
                                            </td>
                                            <td>
                                                <span class="status-${product.statusStyle}">${product.statusDisplay}</span>
                                            </td>
                                            <td>
                                                <div class="d-flex justify-content-center">
                                                    <button type="button"
                                                            class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                                            data-bs-toggle="dropdown" aria-haspopup="true"
                                                            aria-expanded="false" data-display="static">
                                                            <span class="sr-only">
                                                                <i class="ri-settings-3-line"></i>
                                                            </span>
                                                    </button>
                                                    <div class="dropdown-menu">
                                                        <a class="dropdown-item" href="#" onclick="rowAction(${product.id}, 'view'); return false;" data-value="view">Chi tiết</a>
                                                        <a class="dropdown-item" href="#" onclick="rowAction(${product.id}, 'update'); return false;" data-value="update">Cập nhật</a>
                                                        <a class="dropdown-item" href="#" onclick="rowAction(${product.id}, 'delete'); return false;" data-value="delete">Ngừng bán</a>
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

<script>const contextPath = "<%=request.getContextPath() %>";</script>
<script src="${pageContext.request.contextPath}/assets/owner/js/event-handler/table-event.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/makeup-data/format-data.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/manage/manage-product.js" defer></script>

<style>
    .page-title {
        margin-right: 20px;
    }
    .non-growth {
        color: slategray;
    }
    .up-growth {
        color: #2bbb93;
    }
    .down-growth {
        color: #f90c4c;
    }
    .icon-growth {
        font-size: 120%;
    }
    .avg-rate {
        margin: 0;
        font-size: 110%;
        font-weight: bold;
        color: #2b3647;
        font-family: Nunito, sans-serif
    }
    .new-price {
        font-family: "Be Vietnam Pro", sans-serif;
        font-size: 110%;
        font-weight: 600;
        color: #64b496;
    }
    table.rounded-table {
        border-radius: 1.5%;
        overflow: hidden;
    }
    table td div span.row-highlight {
        font-family: 'Nunito', sans-serif;
        font-size: 120%;
        font-weight: bold;
        color: #2b3647;
    }
    table td div span.row-sub-content {
        font-family: "Be Vietnam Pro", sans-serif;
        font-size: 90%;
    }
    .card-3d-deep {
        background: #fff;
        border-radius: 10px;
        padding: 20px;
        position: relative;
        overflow: visible;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1),
        0 8px 10px rgba(0, 0, 0, 0.1),
        0 16px 24px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    .card-3d-deep:hover {
        box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1),
        0 12px 20px rgba(0, 0, 0, 0.15),
        0 24px 32px rgba(0, 0, 0, 0.2);
    }
    .image-shadow {
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        object-fit: cover;
    }
    .image-shadow:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
        object-fit: cover;
    }
    table thead {
        pointer-events: none;
    }
    .responsive-table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0;

        td {
            padding: 10px;
            text-align: left;
            position: relative;
            background-color: #fff;
            border: none;
        }

        tr {
            position: relative;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
    }
    .dropdown-menu {
        position: absolute;
        z-index: 1050;
        display: none;
        transform: translate3d(0, 0, 0);
        background-color: #fff;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    }
    .dropdown-menu.show {
        display: block;
    }
    .status-coming_soon {
        background-color: #edf1f7;
        color: gray;
        font-weight: bold;
        border-radius: 10px;
        padding: 4px 10px 4px 10px;
        font-family: Nunito, sans-serif;
    }
    .status-on_sale {
        background-color: #dcfcee;
        color: #30cb83;
        font-weight: bold;
        border-radius: 10px;
        padding: 4px 10px 4px 10px;
        font-family: Nunito, sans-serif;
    }
    .status-out_of_stock {
        background-color: #fcfbdc;
        color: #ffd43b;
        font-weight: bold;
        border-radius: 10px;
        padding: 4px 10px 4px 10px;
        font-family: Nunito, sans-serif;
    }
    .status-stop_selling {
        background-color: #fce3dc;
        color: #ff554f;
        font-weight: bold;
        border-radius: 10px;
        padding: 4px 10px 4px 10px;
        font-family: Nunito, sans-serif;
    }
</style>
