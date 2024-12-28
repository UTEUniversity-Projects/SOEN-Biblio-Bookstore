<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    div.introduction {
        width: 100%;
        margin: 0;
        padding-left: 10px;
        text-align: justify;
        box-sizing: border-box;
        font-size: 90%;
        color: slategray;
    }
    .page-title {
        margin-right: 20px;
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
</style>
<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <div class="d-flex align-items-center">
                    <div class="page-title">
                        <h5><b>Nội dung trang</b></h5>
                    </div>
                    <a href="${pageContext.request.contextPath}/owner/content-create" class="cr-btn default-btn color-primary">
                        Thêm nội dung
                    </a>
                </div>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/owner/ecommerce"><b>Biblio</b></a></li>
                    <li><b>Nội dung trang</b></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card card-default card-3d-deep">
                    <div class="cr-card-content">
                        <h5>Giới thiệu cửa hàng</h5>
                        <div class="introduction">Giới thiệu cửa hàng.</div>
                        <div class="edit-buttons d-flex justify-content-end mt-2">
                            <a href="${pageContext.request.contextPath}/owner/content-update" class="cr-btn default-btn color-primary">
                                <i class="ri-pencil-line"></i>
                                Cập nhật
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card card-default card-3d-deep">
                    <div class="cr-card-content">
                        <h5>Chính sách vận chuyển</h5>
                        <div class="introduction">Chính sách vận chuyển.</div>
                        <div class="edit-buttons d-flex justify-content-end mt-2">
                            <a href="${pageContext.request.contextPath}/owner/content-update" class="cr-btn default-btn color-primary">
                                <i class="ri-pencil-line"></i>
                                Cập nhật
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="cr-card card-default card-3d-deep">
                    <div class="cr-card-content">
                        <h5>Ưu đãi thành viên</h5>
                        <div class="introduction">Ưu đãi thành viên.</div>
                        <div class="edit-buttons d-flex justify-content-end mt-2">
                            <a href="${pageContext.request.contextPath}/owner/content-update" class="cr-btn default-btn color-primary">
                                <i class="ri-pencil-line"></i>
                                Cập nhật
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
