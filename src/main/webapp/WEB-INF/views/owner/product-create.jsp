<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <div class="page-title">
                    <h5>Thêm sản phẩm</h5>
                </div>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/owner/ecommerce"><b>Biblio</b></a></li>
                    <li><a href="${pageContext.request.contextPath}/owner/product/list"><b>Sản phẩm</b></a></li>
                    <li><b>Thêm sản phẩm</b></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <form id="productCreateForm">
                <div class="col-md-12">
                    <div class="cr-card card-default card-3d-deep">
                        <div class="cr-card-content">
                            <div class="row cr-product-uploads">
                                <div class="col-lg-4 mb-991">
                                    <div class="cr-vendor-img-upload">
                                        <div class="cr-vendor-main-img">
                                            <div class="avatar-upload">
                                                <div class="avatar-edit">
                                                    <input type='file' id="imgMain" name="imgMain" class="cr-image-upload"
                                                           accept=".png, .jpg, .jpeg">
                                                    <label><i class="ri-pencil-line"></i></label>
                                                </div>
                                                <div class="avatar-preview cr-preview">
                                                    <div class="imagePreview cr-div-preview">
                                                        <img class="cr-image-preview image-shadow rounded-2"
                                                             src="${pageContext.request.contextPath}/assets/owner/img/product/preview.jpg"
                                                             alt="edit">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="thumb-upload-set colo-md-12">
                                                <div class="thumb-upload">
                                                    <div class="thumb-edit">
                                                        <input type='file' id="thumbUpload01" name="thumb01"
                                                               class="cr-image-upload"
                                                               accept=".png, .jpg, .jpeg">
                                                        <label><i class="ri-pencil-line"></i></label>
                                                    </div>
                                                    <div class="thumb-preview cr-preview">
                                                        <div class="image-thumb-preview">
                                                            <img class="image-thumb-preview cr-image-preview"
                                                                 src="${pageContext.request.contextPath}/assets/owner/img/product/preview-2.jpg"
                                                                 alt="edit">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="thumb-upload">
                                                    <div class="thumb-edit">
                                                        <input type='file' id="thumbUpload02" name="thumb02"
                                                               class="cr-image-upload"
                                                               accept=".png, .jpg, .jpeg">
                                                        <label><i class="ri-pencil-line"></i></label>
                                                    </div>
                                                    <div class="thumb-preview cr-preview">
                                                        <div class="image-thumb-preview">
                                                            <img class="image-thumb-preview cr-image-preview"
                                                                 src="${pageContext.request.contextPath}/assets/owner/img/product/preview-2.jpg"
                                                                 alt="edit">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="thumb-upload">
                                                    <div class="thumb-edit">
                                                        <input type='file' id="thumbUpload03" name="thumb03"
                                                               class="cr-image-upload"
                                                               accept=".png, .jpg, .jpeg">
                                                        <label><i class="ri-pencil-line"></i></label>
                                                    </div>
                                                    <div class="thumb-preview cr-preview">
                                                        <div class="image-thumb-preview">
                                                            <img class="image-thumb-preview cr-image-preview"
                                                                 src="${pageContext.request.contextPath}/assets/owner/img/product/preview-2.jpg"
                                                                 alt="edit">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="thumb-upload">
                                                    <div class="thumb-edit">
                                                        <input type='file' id="thumbUpload04" name="thumb04"
                                                               class="cr-image-upload"
                                                               accept=".png, .jpg, .jpeg">
                                                        <label><i class="ri-pencil-line"></i></label>
                                                    </div>
                                                    <div class="thumb-preview cr-preview">
                                                        <div class="image-thumb-preview">
                                                            <img class="image-thumb-preview cr-image-preview"
                                                                 src="${pageContext.request.contextPath}/assets/owner/img/product/preview-2.jpg"
                                                                 alt="edit">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="thumb-upload">
                                                    <div class="thumb-edit">
                                                        <input type='file' id="thumbUpload05" name="thumb05"
                                                               class="cr-image-upload"
                                                               accept=".png, .jpg, .jpeg">
                                                        <label><i class="ri-pencil-line"></i></label>
                                                    </div>
                                                    <div class="thumb-preview cr-preview">
                                                        <div class="image-thumb-preview">
                                                            <img class="image-thumb-preview cr-image-preview"
                                                                 src="${pageContext.request.contextPath}/assets/owner/img/product/preview-2.jpg"
                                                                 alt="edit">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="thumb-upload">
                                                    <div class="thumb-edit">
                                                        <input type='file' id="thumbUpload06" name="thumb06"
                                                               class="cr-image-upload"
                                                               accept=".png, .jpg, .jpeg">
                                                        <label><i class="ri-pencil-line"></i></label>
                                                    </div>
                                                    <div class="thumb-preview cr-preview">
                                                        <div class="image-thumb-preview">
                                                            <img class="image-thumb-preview cr-image-preview"
                                                                 src="${pageContext.request.contextPath}/assets/owner/img/product/preview-2.jpg"
                                                                 alt="edit">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-8">
                                    <div class="row g-3 ps-3">
                                        <div class="col-md-12">
                                            <label for="title" class="form-label">Tên sản phẩm<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="title" name="title" type="text" class="form-control slug-title">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="sellingPrice" class="form-label">Giá bán<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input  id="sellingPrice" name="sellingPrice" type="text" class="form-control slug-title"
                                                   placeholder="Đơn vị: VNĐ">
                                        </div>
                                        <div class="col-md-2">
                                            <label for="quantity" class="form-label">Số lượng<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="quantity" name="quantity" type="number" class="form-control" placeholder="100">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="authorIds" class="form-label">Tác giả<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <select id="authorIds" name="authorIds" class="form-control form-select select2" multiple="multiple">
                                                <c:forEach var="author" items="${init.authors}">
                                                    <option class="option-value" value="${author.id}">${author.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="subCategoryId" class="form-label">Thể loại<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <select id="subCategoryId" name="subCategoryId" class="form-control form-select select2">
                                                <c:forEach var="category" items="${init.categories}">
                                                    <optgroup label="${category.name}">
                                                        <c:forEach var="subCategory" items="${category.subCategories}">
                                                            <option class="option-value" value="${subCategory.id}">${subCategory.name}</option>
                                                        </c:forEach>
                                                    </optgroup>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="translatorIds" class="form-label">Dịch giả</label>
                                            <select id="translatorIds" name="translatorIds" class="form-control form-select select2"  multiple="multiple">
                                                <c:forEach var="translator" items="${init.translators}">
                                                    <option class="option-value" value="${translator.id}">${translator.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="conditionCode" class="form-label">Tình trạng<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <select id="conditionCode" name="conditionCode" class="form-control form-select select2">
                                                <c:forEach var="condition" items="${init.conditions}">
                                                    <option class="option-value" value="${condition.key}">${condition.value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="publisherId" class="form-label">Nhà xuất bản<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <select id="publisherId" name="publisherId" class="form-control form-select select2">
                                                <c:forEach var="publisher" items="${init.publishers}">
                                                    <option class="option-value" value="${publisher.id}">${publisher.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="formatCode" class="form-label">Định dạng<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <select id="formatCode" name="formatCode" class="form-control form-select select2">
                                                <c:forEach var="format" items="${init.formats}">
                                                    <option class="option-value" value="${format.key}">${format.value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="publicationDate" class="form-label">Ngày xuất bản<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="publicationDate" name="publicationDate" type="date" class="form-control"
                                                   placeholder="dd/mm/yyyy"/>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="languageCodes" class="form-label">Ngôn ngữ<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <select id="languageCodes" name="languageCodes" class="form-control form-select select2" multiple="multiple">
                                                <c:forEach var="language" items="${init.languages}">
                                                    <option class="option-value" value="${language.key}">${language.value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="ageRecommendCode" class="form-label">Độ tuổi khuyến nghị<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <select id="ageRecommendCode" name="ageRecommendCode" class="form-control form-select select2">
                                                <c:forEach var="ageRecommend" items="${init.ageRecommends}">
                                                    <option class="option-value" value="${ageRecommend.key}">${ageRecommend.value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <label for="codeISBN10" class="form-label">ISBN10<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="codeISBN10" name="codeISBN10" type="text" class="form-control slug-title">
                                        </div>
                                        <div class="col-md-3">
                                            <label for="codeISBN13" class="form-label">ISBN13<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="codeISBN13" name="codeISBN13" type="text" class="form-control slug-title" >
                                        </div>
                                        <div class="col-md-3">
                                            <label for="edition" class="form-label">Tái bản<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="edition" name="edition" type="text" class="form-control slug-title"
                                                   placeholder="3">
                                        </div>
                                        <div class="col-md-3">
                                            <label for="hardcover" class="form-label">Số trang<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="hardcover" name="hardcover" type="text" class="form-control slug-title"
                                                   placeholder="125">
                                        </div>
                                        <div class="col-md-3">
                                            <label for="length" class="form-label">Chiều dài<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="length" name="length" type="text" class="form-control slug-title"
                                                   placeholder="Đơn vị: cm">
                                        </div>
                                        <div class="col-md-3">
                                            <label for="width" class="form-label">Chiều rộng<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="width" name="width" type="text" class="form-control slug-title"
                                                   placeholder="Đơn vị: cm">
                                        </div>
                                        <div class="col-md-3">
                                            <label for="height" class="form-label">Chiều cao<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="height" name="height" type="text" class="form-control slug-title"
                                                   placeholder="Đơn vị: cm">
                                        </div>
                                        <div class="col-md-3">
                                            <label for="weight" class="form-label">Khối lượng<sup class="color-secondary" style="font-size: 100%">*</sup></label>
                                            <input id="weight" name="weight" type="text" class="form-control slug-title"
                                                   placeholder="Đơn vị: kg">
                                        </div>
                                        <hr>
                                        <div class="col-md-12">
                                            <div class="row ps-3">
                                                <div class="d-flex col-sm-12 editor-area">
                                                    <h5>Soạn thảo mô tả</h5>
                                                    <button id="review-btn" class="cr-btn default-btn color-primary">Xem trước</button>
                                                </div>
                                                <div id="editor" name="description" class="edit-description">
                                                    Mô tả sản phẩm
                                                </div>
                                                <div class="review-area">
                                                    <div id="review-container d-none">
                                                        <h5>Mô tả sản phẩm</h5>
                                                        <div id="review-content" class="review-description"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="col-md-12">
                                            <div class="row ps-3">
                                                <div class="d-flex col-sm-12 editor-area justify-content-end">
                                                    <button id="createButton" type="button" class="cr-btn default-btn color-primary">Thêm sách</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Toast container -->
<div id="toast" class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1055;"></div>

<script>const contextPath = "<%=request.getContextPath() %>";</script>
<script src="${pageContext.request.contextPath}/assets/owner/js/manage/init-global-variables.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/extension/quill-editor.js" defer></script>
<script src="${pageContext.request.contextPath}/assets/owner/js/manage/manage-product.js" defer></script>

<script>
    $(document).ready(function() {
        $('.select2').select2();
    });
</script>

<style>
    .select2-selection__rendered {
        color: #2b3647;
        font-family: 'Nunito', sans-serif;
        font-size: 90%;
        font-weight: bold;
    }
    .select2-results__option {
        color: #7a7a7a;
        font-family: 'Nunito', sans-serif;
        font-size: 90%;
        font-weight: 500;
    }
    .form-label {
        color: #484d54;
        font-size: 13px;
        font-weight: bold;
    }
    .form-control {
        border-radius: 10px;
        border: 1px solid #aaa;
        color: #2b3647;
    }
    .form-control:focus {
        border-color: #007bff;
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        outline: none;
        color: #2b3647;
    }
    .page-title {
        margin-right: 20px;
    }
    .editor-area {
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px
    }
    .review-area {
        padding-top: 30px;
        padding-bottom: 20px;
    }
    .author-name {
        font-family: 'Nunito', sans-serif;
        font-size: 90%;
        font-weight: bold;
        color: #2b3647;
    }
    div .edit-description p{
        width: 100%;
        margin: 0;
        text-align: justify;
        box-sizing: border-box;
        font-size: 110%;
    }
    h5 {
        font-weight: bold;
        color: #2b3647;
    }
    .review-description p {
        width: 100%;
        margin: 0;
        padding-left: 10px;
        padding-right: 10px;
        text-align: justify;
        box-sizing: border-box;
        font-size: 90%;
        color: slategray;
    }
    .card-3d-deep {
        background: #fff;
        border-radius: 10px;
        padding: 20px;
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
    #review-content {
        white-space: pre-wrap;
        word-wrap: break-word;
    }
</style>