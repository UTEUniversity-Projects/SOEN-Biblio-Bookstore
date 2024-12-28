<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .end-row {
        border-bottom: 2px solid #EEEEEE;
        margin-bottom: 20px;
        width: 84%;  /* Điều chỉnh chiều dài đường kẻ */
        margin-left: auto;  /* Căn giữa đường kẻ */
        margin-right: auto; /* Căn giữa đường kẻ */
    }

</style>
<!-- Main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title">
            <div class="cr-breadcrumb">
                <h5>Báo cáo bán hàng</h5>
                <ul>
                    <li><a href="index.html">Biblio</a></li>
                    <li>Báo cáo bán hàng</li>
                </ul>
            </div>
            <!-- Form bao quanh daterange -->
            <form id="financial-report-form">
                <div class="cr-tools">
                    <div class="daterange" id="date-summary-report">
                        <i class="fa fa-calendar"></i>&nbsp;
                        <span></span>
                        <i class="fa fa-caret-down"></i>
                    </div>
                </div>
            </form>
        </div>
        <div class="row">
            <div class="col-xl-12">
                <div class="row">
                    <!-- Khách Hàng -->
                    <div class="col-xl-4 col-md-12">
                        <div class="cr-card">
                            <div class="cr-card-content label-card">
                                <div class="title">
                                    <span class="icon icon-1"><i class="ri-shield-user-line"></i></span>
                                    <div class="growth-numbers">
                                        <h4>Khách Hàng Mới</h4>
                                        <h5 id="customer-count">0</h5> <!-- ID riêng để load data -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Đơn Hàng -->
                    <div class="col-xl-4 col-md-12">
                        <div class="cr-card">
                            <div class="cr-card-content label-card">
                                <div class="title">
                                    <span class="icon icon-2"><i class="ri-shopping-bag-3-line"></i></span>
                                    <div class="growth-numbers">
                                        <h4>Đơn Hàng</h4>
                                        <h5 id="order-count">0</h5> <!-- ID riêng -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Doanh Thu -->
                    <div class="col-xl-4 col-md-12">
                        <div class="cr-card">
                            <div class="cr-card-content label-card">
                                <div class="title">
                                    <span class="icon icon-3"><i class="ri-money-dollar-circle-line"></i></span>
                                    <div class="growth-numbers">
                                        <h4>Doanh Thu</h4>
                                        <h5 id="revenue-count" class="price-value">0</h5> <!-- ID riêng -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="end-row"></div>
        <div class="row">
            <div class="col-xl-4 col-md-12">
                <div class="cr-card">
                    <div class="cr-card-content label-card">
                        <div class="title">
                            <span class="icon icon-3"><i class="ri-money-dollar-circle-line"></i></span>
                            <div class="growth-numbers">
                                <h4>Thống Kê Doanh Thu</h4>
                                <h5 id="chartSumRevenue">0</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xxl-12 col-xl-12">
                <div class="cr-card revenue-overview">
                    <div class="cr-card-header header-575">
                        <h4 class="cr-card-title"></h4>
                        <div class="header-tools" style="font-size: 24px">
                            <a class="a-chart" id="barChartRevenueIcon">
                                <i class="ri-bar-chart-line"></i>
                            </a>
                            <a class="a-chart" id="lineChartRevenueIcon">
                                <i class="ri-line-chart-line"></i>
                            </a>
                            <a
                                    href="javascript:void(0)"
                                    class="m-r-10 cr-full-card"
                                    title="Full Screen"
                            >
                                <i class="ri-fullscreen-line"></i>
                            </a>
                            <div class="cr-tools">
                                <div class="daterange" id="date-list-revenue">
                                    <i class="fa fa-calendar"></i>&nbsp;
                                    <span></span>
                                    <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cr-card-content">
                        <div class="cr-chart-content">
                            <div id="revenueLineChart" class="mb-m-24"></div>
                            <div id="revenueBarChart" class="mb-m-24"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="end-row"></div>
        <div class="row">
            <div class="col-xl-4 col-md-12">
                <div class="cr-card">
                    <div class="cr-card-content label-card">
                        <div class="title">
                            <span class="icon icon-1"><i class="ri-shield-user-line"></i></span>
                            <div class="growth-numbers">
                                <h4>Thống kê khách hàng</h4>
                                <h5 id="chartCountNewCustomer">0</h5> <!-- ID riêng để load data -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xxl-12 col-xl-12">
                <div class="cr-card revenue-overview">
                    <div class="cr-card-header header-575" style="font-size: 24px">
                        <h4 class="cr-card-title">
                        </h4>
                        <div class="header-tools">
                            <a class="a-chart" id="barChartNewCustomerIcon"><i class="ri-bar-chart-line"></i></a>
                            <a class="a-chart" id="lineChartNewCustomerIcon"><i class="ri-line-chart-line"></i></a>
                            <a
                                    href="javascript:void(0)"
                                    class="m-r-10 cr-full-card"
                                    title="Full Screen"
                            ><i class="ri-fullscreen-line"></i
                            ></a>
                            <div class="cr-tools">
                                <div class="daterange" id="date-list-new-customer">
                                    <i class="fa fa-calendar"></i>&nbsp;
                                    <span></span>
                                    <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cr-card-content">
                        <div class="cr-chart-content">
                            <div id="newCustomerBarChart" class="mb-m-24"></div>
                            <div id="newCustomerLineChart" class="mb-m-24"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="end-row"></div>
        <div class="row">
            <div class="col-xl-4 col-md-12">
                <div class="cr-card">
                    <div class="cr-card-content label-card">
                        <div class="title">
                            <span class="icon icon-3"><i class="ri-product-hunt-line"></i></span>
                            <div class="growth-numbers">
                                <h4>Sách đã bán</h4>
                                <h5 id="countBookSold">0</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-md-12">
                <div class="cr-card">
                    <div class="cr-card-content label-card">
                        <div class="title">
                            <span class="icon icon-3"><i class="ri-shield-star-line"></i></span>
                            <div class="growth-numbers">
                                <h4>Sách bán chạy nhất</h4>
                                <h5 id="bestSeller"></h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xxl-12 col-xl-12">
                <div class="cr-card revenue-overview">
                    <div class="cr-card-header header-575">
                        <h4 class="cr-card-title"></h4>
                        <div class="header-tools" style="font-size: 24px">
                            <a
                                    href="javascript:void(0)"
                                    class="m-r-10 cr-full-card"
                                    title="Full Screen"
                            ><i class="ri-fullscreen-line"></i
                            ></a>
                            <div class="cr-tools">
                                <div class="daterange" id="date-top-product">
                                    <i class="fa fa-calendar"></i>&nbsp;
                                    <span></span>
                                    <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cr-card-content">
                        <div class="cr-chart-content">
                            <div id="topProductSoldBarChart" class="mb-m-24"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xxl-12 col-xl-12">
                <div class="cr-card" id="best_seller_tbl">
                    <div class="cr-card-header">
                        <h4 class="cr-card-title">Sản Phẩm</h4>
                        <div class="header-tools">
                            <a href="javascript:void(0)" class="m-r-10 cr-full-card" title="Full Screen"><i
                                    class="ri-fullscreen-line"></i></a>
                        </div>
                    </div>
                    <div class="cr-card-content card-default">
                        <div class="best-seller-table">
                            <div class="table-responsive">
                                <table id="list_product_sold_in_stock_statistical" class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Thể loại</th>
                                        <th>Đã bán</th>
                                        <th>Tồn kho</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!-- Các dòng dữ liệu sẽ được thêm vào đây -->
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="end-row"></div>

            <div class="col-xl-12">
                <div class="row">
                    <div class="col-xl-3 col-md-6">
                        <div class="cr-card">
                            <div class="cr-card-content label-card">
                                <div class="title">
                                    <span class="icon icon-1"><i class="ri-shield-user-line"></i></span>
                                    <div class="growth-numbers">
                                        <h4 style="font-family: Arial, Helvetica, sans-serif">Số khách mua hàng</h4>
                                        <h5 id="countCustomerPurchaseAtTime">0</h5>
                                    </div>
                                </div>
                                <p class="card-groth up">
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6">
                        <div class="cr-card">
                            <div class="cr-card-content label-card">
                                <div class="title">
                                    <span class="icon icon-2"><i class="ri-shopping-bag-3-line"></i></span>
                                    <div class="growth-numbers">
                                        <h4 style="font-family: Arial, Helvetica, sans-serif">Đơn hàng</h4>
                                        <h5 id="countOrderAtTime">0</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6">
                        <div class="cr-card">
                            <div class="cr-card-content label-card">
                                <div class="title">
                                    <span class="icon icon-2"><i class="ri-handbag-line"></i></span>
                                    <div class="growth-numbers">
                                        <h4 style="font-family: Arial, Helvetica, sans-serif">Mua một đơn</h4>
                                        <h5 id="countCustomerPurchaseOneTimeAtTime">0</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-md-6">
                        <div class="cr-card">
                            <div class="cr-card-content label-card">
                                <div class="title">
                                    <span class="icon icon-4"><i class="ri-shopping-bag-line"></i></span>
                                    <div class="growth-numbers">
                                        <h4 style="font-family: Arial, Helvetica, sans-serif">Mua nhiều hơn một đơn</h4>
                                        <h5 id="countCustomerPurchaseThanOneTimeAtTime">0</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xxl-8 col-xl-12">
                <div class="cr-card revenue-overview">
                    <div class="cr-card-header header-575">
                        <h4 class="cr-card-title" style="font-family: Arial, Helvetica, sans-serif">Khách mua hàng</h4>
                        <div class="header-tools">
                            <a href="javascript:void(0)" class="m-r-10 cr-full-card" title="Full Screen"><i
                                    class="ri-fullscreen-line"></i></a>
                            <div class="cr-tools">
                                <div class="daterange" id="date-rate-repeat-purchase">
                                    <i class="fa fa-calendar"></i>&nbsp;
                                    <span></span>
                                    <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="cr-chart-content">
                        <div id="countCustomerPurchaseBarChart" class="mb-m-24"></div>
                    </div>
                </div>
            </div>
            <div class="col-xxl-4 col-xl-6 col-md-12">
                <div class="cr-card" id="campaigns">
                    <div class="cr-card-header">
                        <h4 class="cr-card-title" style="font-family: Arial, Helvetica, sans-serif">Tỉ lệ quay lại mua hàng</h4>
                        <!-- <div class="header-tools">
                            <div class="cr-date-range dots">
                                <span></span>
                            </div>
                        </div> -->
                        <div class="header-tools">
                            <a href="javascript:void(0)" class="m-r-10 cr-full-card" title="Full Screen"><i
                                    class="ri-fullscreen-line"></i></a>
                        </div>
                    </div>
                    <div class="cr-card-content">
                        <div class="cr-chart-content">
                            <div id="donutChartRepeatPurchaseRate"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="end-row"></div>
        <div class="row">
            <div class="col-xl-4 col-md-12">
                <div class="cr-card">
                    <div class="cr-card-content label-card">
                        <div class="title">
                            <span class="icon icon-3"><i class="ri-product-hunt-line"></i></span>
                            <div class="growth-numbers">
                                <h4>Đơn hàng thành công</h4>
                                <h5 id="countOrderSuccess">0</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-md-12">
                <div class="cr-card">
                    <div class="cr-card-content label-card">
                        <div class="title">
                            <span class="icon icon-3"><i class="ri-shield-star-line"></i></span>
                            <div class="growth-numbers">
                                <h4>Đơn hàng hoàn trả</h4>
                                <h5 id="countOrderReturn"></h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-md-12">
                <div class="cr-card">
                    <div class="cr-card-content label-card">
                        <div class="title">
                            <span class="icon icon-3"><i class="ri-shield-star-line"></i></span>
                            <div class="growth-numbers">
                                <h4>Tỷ lệ đơn hàng bị trả</h4>
                                <h5 id="rateOrderReturn"></h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xxl-12 col-xl-12">
                <div class="cr-card revenue-overview">
                    <div class="cr-card-header header-575">
                        <h4 class="cr-card-title"></h4>
                        <div class="header-tools" style="font-size: 24px">
                            <a
                                    href="javascript:void(0)"
                                    class="m-r-10 cr-full-card"
                                    title="Full Screen"
                            ><i class="ri-fullscreen-line"></i
                            ></a>
                            <div class="cr-tools">
                                <div class="daterange" id="date-return-order">
                                    <i class="fa fa-calendar"></i>&nbsp;
                                    <span></span>
                                    <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cr-card-content">
                        <div class="cr-chart-content">
                            <div id="reasonReturnOrderBarChart" class="mb-m-24"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/owner/js/ecommerce.js" defer></script>

