<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- main content -->
<style>
    .error-message {
        color: red;
        font-size: 0.7em;
    }
    .ip-padding {
        padding-bottom: 20px;
    }
    .table-disabled {
        position: relative;
        opacity: 0.6; /* Làm mờ bảng */
        pointer-events: none; /* Ngăn mọi sự kiện tương tác */
    }

    .table-disabled::after {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        //background: rgba(255, 255, 255, 0.6); /* Lớp phủ trắng */
        z-index: 1;
    }
    .cr-btn-stop {
        /*@include ease3;*/
        height: 35px;
        padding: 6px 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f90c4c;
        color: #ffffff;
        font-size: 16px;
        border: 1px solid #64b496;
        cursor: pointer;

        &:hover {
            background-color: #ffffff;
            color: #25a984;
            border: 1px solid #25a984;
        }

        &:focus {
            outline: none;
            box-shadow: none;
        }
    }

</style>
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5>Thông tin khuyến mãi</h5>
                <ul>
                    <li><a href="index.html">Biblio</a></li>
                    <li>Thông tin khuyến mãi</li>
                </ul>
            </div>
        </div>
        <c:if test="${promotion.type == 'DISCOUNT'}">
            <form action="${pageContext.request.contextPath}/owner/promotion-details" method="POST" class="promotionForm">
                <input type="hidden" name="formType" value="editDiscount"/>

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
                                            <div class="form-group ip-padding">
                                                <label>Mã</label>
                                                <div class="col-12">
                                                    <input name="code" value="${promotion.code}" style="background-color: #f5f5f5;" class="form-control here slug-title" type="text" onblur="validateInput(this)" readonly />
                                                    <span class="error-message codeError"></span>
                                                </div>
                                            </div>

                                            <!-- Tiêu đề -->
                                            <div class="form-group ip-padding">
                                                <label>Tiêu đề</label>
                                                <div class="col-12">
                                                    <input name="title" value="${promotion.title}"  disabled class="form-control here slug-title" type="text" onblur="validateInput(this)" />
                                                    <span class="error-message titleError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group ip-padding">
                                                <label>Phần trăm giảm (%)</label>
                                                <div class="col-12">
                                                    <input name="percentDiscount" value="${promotion.percentDiscount}" disabled class="form-control here slug-title" type="number" onblur="validateInput(this)" />
                                                    <span class="error-message percentDiscountError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group ip-padding">
                                                <label>Đối tượng áp dụng</label>
                                                <div class="select-oject-use-voucher">
                                                    <select class="select-item" disabled name="selectOject" id="select-object-discount" onblur="validateInput(this)">
                                                        <option value="1" ${selectedType == 'BOOK' ? 'selected' : ''}>Sản phẩm cụ thể</option>
                                                        <option value="2" ${selectedType == 'CATEGORY' ? 'selected' : ''}>Giảm theo Category</option>
                                                        <option value="3" ${selectedType == 'SUBCATEGORY' ? 'selected' : ''}>Giảm theo Sub Category</option>
                                                        <option value="4" ${selectedType == 'WHOLE' ? 'selected' : ''}>Toàn bộ sản phẩm</option>
                                                    </select>
                                                    <span class="error-message selectOjectError"></span>
                                                </div>
                                            </div>

                                            <!-- Mô tả -->
                                            <div class="form-group ip-padding">
                                                <label>Mô tả</label>
                                                <div class="col-12">
                                                    <textarea name="description" class="form-control" rows="4" disabled onblur="validateInput(this)">${promotion.description}</textarea>
                                                    <span class="error-message descriptionError"></span>
                                                </div>
                                            </div>

                                            <!-- Thời gian áp dụng -->
                                            <div class="form-group row ip-padding">
                                                <label>Thời gian áp dụng</label>
                                                <div class="col-12">
                                                    <input type="text" name="dateeffective" disabled style="width: 100%;" onblur="validateInput(this)" />
                                                    <span class="error-message dateeffectiveError"></span>
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
                                    <table class="table table-hover table_item_to_discount table-disabled" >
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
                        <c:if test="${isBeforeExpiration}">
                            <button type="submit" class="btn-voucher cr-btn-primary" data-editing="false">Chỉnh sửa</button>
                            <button type="button" class="btn-voucher cr-btn-secondary cancel-btn" style="display: none; margin-left: 10px;" onclick="cancelEdit(this)">Hủy</button>
                            <button type="button" class="btn-voucher cr-btn-stop stop-promotion-btn" style="margin-left: 10px;" onclick="stopPromotion()">Ngừng khuyến mãi</button>

                        </c:if>
                    </div>
                </div>

            </form>
        </c:if>

        <c:if test="${promotion.type == 'VOUCHER'}">
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <div class="col-md-12">
                        <div class="cr-cat-list cr-card card-default mb-24px">
                            <div class="cr-card-content">
                                <div class="cr-cat-form">
                                    <h5>Thông tin Voucher</h5>
                                    <form action="${pageContext.request.contextPath}/owner/promotion-details" method="POST" class="promotionForm">
                                        <input type="hidden" name="formType" value="editVoucher"/>

                                        <!-- Mã -->
                                        <div class="form-group ip-padding">
                                            <label>Mã</label>
                                            <div class="col-12">
                                                <input name="code" value="${promotion.code}" class="form-control here slug-title" style="background-color: #f5f5f5;" readonly type="text" onblur="validateInput(this)" />
                                                <span class="error-message codeError"></span>
                                            </div>
                                        </div>

                                        <!-- Tiêu đề -->
                                        <div class="form-group ip-padding">
                                            <label>Tiêu đề</label>
                                            <div class="col-12">
                                                <input name="title" value="${promotion.title}" class="form-control here slug-title" disabled type="text" onblur="validateInput(this)" />
                                                <span class="error-message titleError"></span>
                                            </div>
                                        </div>

                                        <!-- Số tiền giảm -->
                                        <div class="form-group ip-padding">
                                            <label>Số tiền giảm</label>
                                            <div class="col-12">
                                                <input name="discountLimit" value="${promotion.discountLimit}" class="form-control here slug-title" disabled type="number" onblur="validateInput(this)" />
                                                <span class="error-message discountLimitError"></span>
                                            </div>
                                        </div>

                                        <!-- Số tiền yêu cầu trên hóa đơn -->
                                        <div class="form-group ip-padding">
                                            <label>Số tiền yêu cầu trên hóa đơn</label>
                                            <div class="col-12">
                                                <input name="minValueApplied" value="${promotion.minValueToApplied}" class="form-control here slug-title" disabled type="number" onblur="validateInput(this)" />
                                                <span class="error-message minValueAppliedError"></span>
                                            </div>
                                        </div>

                                        <!-- Số lượng khuyến mãi -->
                                        <div class="form-group ip-padding">
                                            <label>Số lượng khuyến mãi</label>
                                            <div class="col-12">
                                                <input
                                                        name="quantity"
                                                        class="form-control here slug-title"
                                                        type="number"
                                                        <c:if test="${promotion.quantity == -1}">value="" disabled</c:if>
                                                        <c:if test="${promotion.quantity != -1}">value="${promotion.quantity}" disabled</c:if>
                                                        onblur="validateInput(this)" />
                                                <label>
                                                    <input
                                                            type="checkbox"
                                                            name="unlimited"
                                                            value="true"
                                                            style="margin-top: 5px"
                                                            disabled
                                                            <c:if test="${promotion.quantity == -1}">checked</c:if> />
                                                    Không giới hạn
                                                </label>

                                                <span class="error-message quantityError"></span>
                                            </div>

                                        </div>

                                        <!-- Mô tả -->
                                        <div class="form-group ip-padding">
                                            <label>Mô tả</label>
                                            <div class="col-12">
                                                <textarea name="description" class="form-control" rows="4" disabled onblur="validateInput(this)">${promotion.description}</textarea>
                                                <span class="error-message descriptionError"></span>
                                            </div>
                                        </div>

                                        <!-- Thời gian áp dụng -->
                                        <div class="form-group row ip-padding">
                                            <label>Thời gian áp dụng</label>
                                            <div class="col-12">
                                                <input type="text" name="dateeffective" disabled style="width: 100%;" onblur="validateInput(this)" />
                                                <span class="error-message dateeffectiveError"></span>
                                            </div>
                                        </div>

                                        <div class="row ip-padding">
                                            <div class="col-12 d-flex">
                                               <c:if test="${isBeforeExpiration}">
                                                   <button type="submit" class="btn-voucher cr-btn-primary" data-editing="false">Chỉnh sửa</button>
                                                   <button type="button" class="btn-voucher cr-btn-secondary cancel-btn" style="display: none; margin-left: 10px;" onclick="cancelEdit(this)">Hủy</button>
                                                   <button type="button" class="btn-voucher cr-btn-stop stop-promotion-btn" style="margin-left: 10px;" onclick="stopPromotion()">Ngừng khuyến mãi</button>

                                               </c:if>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${promotion.type == 'FREESHIP'}">
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <div class="team-sticky-bar">
                        <div class="col-md-12">
                            <div class="cr-cat-list cr-card card-default mb-24px">
                                <div class="cr-card-content">
                                    <div class="cr-cat-form">
                                        <h3>Thông tin Freeship</h3>
                                        <form action="${pageContext.request.contextPath}/owner/promotion-details" method="POST" class="promotionForm">
                                            <input type="hidden" name="formType" value="editFreeShip" />
                                            <div class="form-group ip-padding">
                                                <label>Mã</label>
                                                <div class="col-12">
                                                    <input name="code" class="form-control here slug-title" type="text" value="${promotion.code}" style="background-color: #f5f5f5;" readonly onblur="validateInput(this)" />
                                                    <span class="error-message codeError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group ip-padding">
                                                <label>Tiêu đề</label>
                                                <div class="col-12">
                                                    <input name="title" class="form-control here slug-title" type="text" value="${promotion.title}" disabled onblur="validateInput(this)" />
                                                    <span class="error-message titleError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group ip-padding">
                                                <label>Số tiền giảm</label>
                                                <div class="col-12">
                                                    <input name="discountLimit" class="form-control here slug-title" type="number" value="${promotion.discountLimit}" disabled onblur="validateInput(this)" />
                                                    <span class="error-message discountLimitError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group ip-padding">
                                                <label>Số lượng khuyến mãi</label>
                                                <div class="col-12">
                                                    <input
                                                            name="quantity"
                                                            class="form-control here slug-title"
                                                            type="number"
                                                            <c:if test="${promotion.quantity == -1}">value="" disabled</c:if>
                                                            <c:if test="${promotion.quantity != -1}">value="${promotion.quantity}" disabled</c:if>
                                                            onblur="validateInput(this)" />
                                                    <label>
                                                        <input
                                                                type="checkbox"
                                                                name="unlimited"
                                                                value="true"
                                                                style="margin-top: 5px"
                                                                disabled
                                                                <c:if test="${promotion.quantity == -1}">checked</c:if> />
                                                        Không giới hạn
                                                    </label>

                                                    <span class="error-message quantityError"></span>
                                            </div>
                                                <div class="form-group ip-padding">
                                                    <label>Mô tả</label>
                                                    <div class="col-12">
                                                        <textarea name="description" class="form-control" rows="4" disabled onblur="validateInput(this)">${promotion.description}</textarea>
                                                        <span class="error-message descriptionError"></span>
                                                    </div>
                                                </div>
                                            <div class="form-group row ip-padding">
                                                <label>Thời gian áp dụng</label>
                                                <div class="col-12">
                                                    <input type="text" name="dateeffective" disabled style="width: 100%;" onblur="validateInput(this)" />
                                                    <span class="error-message dateeffectiveError"></span>
                                                </div>
                                            </div>

                                                <div class="row ip-padding">
                                                    <div class="col-12 d-flex">
                                                        <c:if test="${isBeforeExpiration}">
                                                            <button type="submit" class="btn-voucher cr-btn-primary" data-editing="false">Chỉnh sửa</button>
                                                            <button type="button" class="btn-voucher cr-btn-secondary cancel-btn" style="display: none; margin-left: 10px;" onclick="cancelEdit(this)">Hủy</button>
                                                            <button type="button" class="btn-voucher cr-btn-stop stop-promotion-btn" style="margin-left: 10px;" onclick="stopPromotion()">Ngừng khuyến mãi</button>
                                                        </c:if>

                                                    </div>
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
        </c:if>
    </div>
</div>

<div class="modal fade" id="hideReviewModal" tabindex="-1" aria-labelledby="hideReviewModalLabel" aria-hidden="true">
    <input class="review-id" value="" hidden>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="hideReviewModalLabel-promotion">Bạn có chắc muốn dừng chương trình này không?</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Không</button>
                <button type="button" class="btn btn-primary" id="confirmHideReview-promotion" onclick="confirmStopPromotion()">Có</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script>
    $(function() {
        const fixedStartDate = moment().subtract(7, 'days').startOf('day'); // Ngày bắt đầu cố định
        const promotionStatus = '${promotion.status}'; // Trạng thái từ server (EFFECTIVE hoặc COMING_SOON)

        // Giá trị từ server
        const effectiveDate = moment('${promotion.effectiveDate}', 'YYYY-MM-DDTHH:mm:ss'); // Format kiểu ISO nếu cần
        const expirationDate = moment('${promotion.expirationDate}', 'YYYY-MM-DDTHH:mm:ss'); // Format kiểu ISO nếu cần

        if (promotionStatus === 'COMING_SOON') {
            // Nếu trạng thái là COMING_SOON: Cho chỉnh cả ngày bắt đầu và ngày kết thúc
            $('input[name="dateeffective"]').daterangepicker({
                timePicker: true,
                singleDatePicker: false,
                startDate: effectiveDate.isValid() ? effectiveDate : fixedStartDate, // Giá trị bắt đầu từ server
                endDate: expirationDate.isValid() ? expirationDate : moment().startOf('hour').add(32, 'hour'), // Giá trị kết thúc từ server
                minDate: moment(), // Ngày nhỏ nhất là hôm nay
                autoUpdateInput: true,
                locale: {
                    format: 'M/DD/YYYY hh:mm A' // Hiển thị cả năm
                }
            });
        } else if (promotionStatus === 'EFFECTIVE' || promotionStatus === 'USED_OUT') {
            // Nếu trạng thái là EFFECTIVE: Chỉ chỉnh ngày kết thúc
            $('input[name="dateeffective"]').daterangepicker({
                singleDatePicker: true, // Chỉ chọn một ngày
                timePicker: true, // Hiển thị bộ chọn giờ
                startDate: expirationDate.isValid() ? expirationDate : moment(), // Mặc định ngày kết thúc
                minDate: moment(), // Ngày nhỏ nhất là hôm nay
                locale: {
                    format: 'M/DD/YYYY hh:mm A' // Hiển thị cả năm
                }
            });
        }
    });
</script>

<script>
    const selectedIds = ${selectedIds != null ? selectedIds : '[]'};
</script>

<script src="${pageContext.request.contextPath}/assets/owner/js/validator-promotion-details.js" defer></script>

<script src="${pageContext.request.contextPath}/assets/owner/js/add-promotion.js" defer></script>



