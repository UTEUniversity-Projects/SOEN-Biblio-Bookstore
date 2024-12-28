<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5>Cập nhật nội dung trang</h5>
                <ul>
                    <li><a href="index.html">Biblio</a></li>
                    <li>Cập nhật nội dung trang</li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card card-default product-list">
                    <div class="cr-card-content ">
                        <label>Tiêu đề nội dung</label>
                        <div class="col-12">
                            <input id="text" name="text" class="form-control here slug-title" type="text"
                                   placeholder="Điều khoản khách hàng">
                        </div>
                        <label>Phần nội dung</label>
                        <div class="col-12">
                            <textarea class="form-control" id="shipping-policy-text" rows="10" readonly>Điều khoản khách hàng.</textarea>
                        </div>
                        <br>
                        <div class="d-flex justify-content-end mt-2">
                            <button type="submit" class="btn cr-btn-primary">Cập nhật</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
