<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- main content -->
<style>
    .error-message {
        color: red;
        font-size: 0.7em;
    }
    .tbl-600 {
        @include scrollbar;
        overflow: auto;

        >div {
            min-width: 600px;
        }
    }
    /* Màu nền cho dòng được chọn */

</style>
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5>Thêm Promotion</h5>
                <ul>
                    <li><a href="index.html">Biblio</a></li>
                    <li>Thêm Voucher</li>
                </ul>
            </div>
        </div>
        <div class="tab-promotion">
            <div class="tab-list-promotion">
                <div class="tab-item-promotion active" data-tab="1">Discount</div>
                <div class="tab-item-promotion" data-tab="2">Voucher</div>
                <div class="tab-item-promotion" data-tab="3">Freeship</div>
            </div>
        </div>
        <div class="content-promotion">
            <div class="row cr-promotion" data-tab="1">
                <form class="promotionForm">
                    <input type="hidden" name="formType" value="addDiscount"/>

                    <div class="form-content d-flex">
                        <!-- Cột bên trái -->
                        <div class="col-xl-4 col-lg-12 form-column">
                            <div class="team-sticky-bar">
                                <div class="col-md-12">
                                    <div class="cr-cat-list cr-card card-default mb-24px">
                                        <div class="cr-card-content">
                                            <div class="cr-cat-form">
                                                <h3>Thêm Discount</h3>

                                                <!-- Mã -->
                                                <div class="form-group">
                                                    <label>Mã</label>
                                                    <div class="col-12">
                                                        <input name="code" class="form-control here slug-title" type="text" onblur="validateInput(this)" />
                                                        <span class="error-message codeError"></span>
                                                    </div>
                                                </div>

                                                <!-- Tiêu đề -->
                                                <div class="form-group">
                                                    <label>Tiêu đề</label>
                                                    <div class="col-12">
                                                        <input name="title" class="form-control here slug-title" type="text" onblur="validateInput(this)" />
                                                        <span class="error-message titleError"></span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>Phần trăm giảm (%)</label>
                                                    <div class="col-12">
                                                        <input name="percentDiscount" class="form-control here slug-title" type="number" onblur="validateInput(this)" />
                                                        <span class="error-message percentDiscountError"></span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>Đối tượng áp dụng</label>
                                                    <div class="select-oject-use-voucher">
                                                        <select class="select-item" name="selectOject" id="select-object-discount" onblur="validateInput(this)">
                                                            <option selected disabled>- Chọn đối tượng áp dụng -</option>
                                                            <option value="1" >Sản phẩm cụ thể</option>
                                                            <option value="2">Giảm theo Category</option>
                                                            <option value="3">Giảm theo Sub Category</option>
                                                            <option value="4">Toàn bộ sản phẩm</option>
                                                        </select>
                                                        <span class="error-message selectOjectError"></span>
                                                    </div>
                                                </div>
                                                <!-- Mô tả -->
                                                <div class="form-group">
                                                    <label>Mô tả</label>
                                                    <div class="col-12">
                                                        <textarea name="description" class="form-control" rows="4" onblur="validateInput(this)"></textarea>
                                                        <span class="error-message descriptionError"></span>
                                                    </div>
                                                </div>

                                                <!-- Thời gian áp dụng -->
                                                <div class="form-group row">
                                                    <label>Thời gian áp dụng</label>
                                                    <div class="col-12">
                                                        <input type="text" name="datetimes" style="width: 100%;" onblur="validateInput(this)" />
                                                        <span class="error-message   datetimesError"></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Cột bên phải -->
                        <div class="col-xl-8 col-lg-12 form-column" id="list-product-category-subcategory-discount">
                            <div class="cr-cat-list cr-card card-default">
                                <div class="cr-card-content">
                                    <div class="table-responsive tbl-600">
<%--                                        <div class="select-parent-add-promotion">--%>
<%--                                            <label>Lọc theo:</label>--%>
<%--                                            <div class="select-add-promotion">--%>
<%--                                                <select class="select-item" id="category-select-discount" name="category">--%>
<%--                                                    <option value="1"></option>--%>
<%--                                                </select>--%>
<%--                                                <span class="categoryError text-danger"></span>--%>
<%--                                            </div>--%>
<%--                                            <div class="select-add-promotion">--%>
<%--                                                <select class="select-item" id="subcategory-select-discount" name="subcategory">--%>
<%--                                                    <option value="1"></option>--%>
<%--                                                </select>--%>
<%--                                                <span class="subcategoryError text-danger"></span>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
                                        <table class="table table-hover table_item_to_discount" >
                                            <thead>
                                            <tr>
                                                <th width="5%"></th>
                                                <th width="15%">Mã</th>
                                                <th width="50%">Tên</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Nút Thêm -->
                    <div class="row mt-4">
                        <div class="col-12 d-flex justify-content-center" style="padding-bottom: 20px">
                            <button type="submit" class="btn-voucher cr-btn-primary" >Thêm</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="row cr-promotion" data-tab="2">
                <div class="col-xl-12 col-lg-12">
                    <div class="team-sticky-bar">
                        <div class="col-md-12">
                            <div class="cr-cat-list cr-card card-default mb-24px">
                                <div class="cr-card-content">
                                    <div class="cr-cat-form">
                                        <h3>Thêm Voucher</h3>

                                            <form class="promotionForm">
                                                <input type="hidden" name="formType" value="addVoucher"/>

                                                <!-- Mã -->
                                                <div class="form-group">
                                                    <label>Mã</label>
                                                    <div class="col-12">
                                                        <input name="code" class="form-control here slug-title" type="text" onblur="validateInput(this)" />
                                                        <span class="error-message codeError"></span>
                                                    </div>
                                                </div>

                                                <!-- Tiêu đề -->
                                                <div class="form-group">
                                                    <label>Tiêu đề</label>
                                                    <div class="col-12">
                                                        <input name="title" class="form-control here slug-title" type="text" onblur="validateInput(this)" />
                                                        <span class="error-message titleError"></span>
                                                    </div>
                                                </div>

                                                <!-- Số tiền giảm -->
                                                <div class="form-group">
                                                    <label>Số tiền giảm</label>
                                                    <div class="col-12">
                                                        <input name="discountLimit" class="form-control here slug-title" type="number" onblur="validateInput(this)" />
                                                        <span class="error-message discountLimitError"></span>
                                                    </div>
                                                </div>

                                                <!-- Số tiền yêu cầu trên hóa đơn -->
                                                <div class="form-group">
                                                    <label>Số tiền yêu cầu trên hóa đơn</label>
                                                    <div class="col-12">
                                                        <input name="minValueApplied" class="form-control here slug-title" type="number" onblur="validateInput(this)" />
                                                        <span class="error-message minValueAppliedError"></span>
                                                    </div>
                                                </div>

                                                <!-- Số lượng khuyến mãi -->
                                                <div class="form-group">
                                                    <label>Số lượng khuyến mãi</label>
                                                    <div class="col-12">
                                                        <input name="quantity" class="form-control here slug-title" type="number" onblur="validateInput(this)" />
                                                        <label><input type="checkbox" name="unlimited" value="true" style="margin-top: 5px" /> Không giới hạn</label>
                                                        <span class="error-message quantityError"></span>
                                                    </div>
                                                </div>

                                                <!-- Mô tả -->
                                                <div class="form-group">
                                                    <label>Mô tả</label>
                                                    <div class="col-12">
                                                        <textarea name="description" class="form-control" rows="4" onblur="validateInput(this)"></textarea>
                                                        <span class="error-message descriptionError"></span>
                                                    </div>
                                                </div>

                                                <!-- Thời gian áp dụng -->
                                                <div class="form-group row">
                                                    <label>Thời gian áp dụng</label>
                                                    <div class="col-12">
                                                        <input type="text" name="datetimes" style="width: 100%;" onblur="validateInput(this)" />
                                                        <span class="error-message dateeffectiveError"></span>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-12 d-flex">
                                                        <button type="submit" class="btn-voucher cr-btn-primary"  onclick="return validateForm()">Thêm</button>
                                                    </div>
                                                </div>

                                            </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row cr-promotion" data-tab="3">
                <div class="col-xl-12 col-lg-12">
                    <div class="team-sticky-bar">
                        <div class="col-md-12">
                            <div class="cr-cat-list cr-card card-default mb-24px">
                                <div class="cr-card-content">
                                    <div class="cr-cat-form">
                                        <h3>Thêm Freeship</h3>
                                        <form class="promotionForm">
                                            <input type="hidden" name="formType" value="addFreeShip" />
                                            <div class="form-group">
                                                <label>Mã</label>
                                                <div class="col-12">
                                                    <input name="code" class="form-control here slug-title" type="text" onblur="validateInput(this)" />
                                                    <span class="error-message codeError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Tiêu đề</label>
                                                <div class="col-12">
                                                    <input name="title" class="form-control here slug-title" type="text" onblur="validateInput(this)" />
                                                    <span class="error-message titleError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Số tiền giảm</label>
                                                <div class="col-12">
                                                    <input name="discountLimit" class="form-control here slug-title" type="number" onblur="validateInput(this)" />
                                                    <span class="error-message discountLimitError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Số lượng khuyến mãi</label>
                                                <div class="col-12">
                                                    <input name="quantity" class="form-control here slug-title" type="number" onblur="validateInput(this)" />
                                                    <label><input type="checkbox" name="unlimited" value="true" style="margin-top: 5px" /> Không giới hạn</label>
                                                    <span class="error-message quantityError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Mô tả</label>
                                                <div class="col-12">
                                                    <textarea name="description" class="form-control" rows="4" onblur="validateInput(this)"></textarea>
                                                    <span class="error-message descriptionError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label>Thời gian áp dụng</label>
                                                <div class="col-12">
                                                    <input type="text" name="datetimes" style="width: 100%;" onblur="validateInput(this)" />
                                                    <span class="error-message dateeffectiveError"></span>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-12 d-flex">
                                                    <button type="submit" class="cr-btn-primary" onclick="return validateForm()">Thêm</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- DataTables -->
<script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.js"></script>


<script src="${pageContext.request.contextPath}/assets/owner/js/add-promotion.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/validator-add-promotion.js" defer></script>


<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

<script>
    $(function() {
        $('input[name="datetimes"]').daterangepicker({
            timePicker: true,
            startDate: moment().startOf('hour'),
            endDate: moment().startOf('hour').add(32, 'hour'),
            minDate: moment(),
            locale: {
                format: 'M/DD/YYYY hh:mm A'
            }
        });
    });
</script>