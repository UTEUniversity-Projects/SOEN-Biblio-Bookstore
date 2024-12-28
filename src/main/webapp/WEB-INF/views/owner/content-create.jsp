<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
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
</style>
<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5><b>Thêm nội dung trang</b></h5>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/owner/ecommerce"><b>Biblio</b></a></li>
                    <li><b>Thêm nội dung trang</b></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card card-default card-3d-deep">
                    <div class="cr-card-content">
                        <div class="col-12">
                            <label>Tiêu đề nội dung</label>
                            <input id="text" name="text" class="form-control here slug-title" type="text"
                                   placeholder="Điều khoản khách hàng">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xxl-9 col-xl-8 col-md-12" style="padding-left: 10px">
                <div class="cr-card card-3d-deep">
                    <div class="cr-card-content">
                        <div class="row">
                            <div class="d-flex col-sm-12 editor-area">
                                <h5>Soạn thảo giới thiệu</h5>
                                <button id="review-btn" class="cr-btn default-btn color-primary">Xem trước</button>
                            </div>
                            <div id="editor" class="edit-introduction">
                                Mô tả về tác giả
                            </div>
                            <div class="review-area">
                                <div id="review-container" style="display: none;">
                                    <h5>Giới thiệu tác giả</h5>
                                    <div id="review-content" class="review-introduction"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/owner/js/extension/quill-editor.js" defer></script>