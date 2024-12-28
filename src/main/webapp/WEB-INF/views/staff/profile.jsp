<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="">
                <h5>Thông tin cá nhân</h5>
            </div>
        </div>
        <div class="row">
            <div class="col-xl-3 col-lg-12">
                <div class="vendor-sticky-bar">
                    <div class="col-xl-12">
                        <div class="cr-card">
                            <div class="cr-card-content">
                                <div class="cr-vendor-block-img">
                                    <div class="cr-vendor-block-detail">
                                        <div class="profile-img">
                                            <div class="avatar-previews">
                                                <div class="avatar-customer-img" style="width: 150px; height: 150px;"
                                                     id="avatar-customer-edit"
                                                >
                                                    <img src="${pageContext.request.contextPath}${staff.avatar}"
                                                         alt="avatar" style="width: 100%; height: 100%; object-fit: cover">
                                                </div>
                                            </div>
                                            <input type="file" id="avatarUpload" style="display: none;" accept="image/*"
                                                   disable>
                                        </div>
                                    </div>
                                    <div class="cr-vendor-info textbox-customer-info">
                                        <ul>
                                            <li>
                                                <label>Tên :</label>
                                                <div class="form-group p-b-15">
                                                    <input type="text" class="form-control" id="name-customer-edit"
                                                           value="${staff.fullName}" disabled
                                                           style="background-color: white">
                                                </div>
                                            </li>
                                            <li>
                                                <label>Email :</label>
                                                <div class="form-group p-b-15">
                                                    <input type="text" class="form-control" id="email-customer-edit"
                                                           value="${staff.emailAddress}" disabled
                                                           style="background-color: white">
                                                </div>
                                            </li>
                                            <li>
                                                <label>Điện thoại :</label>
                                                <div class="form-group p-b-15">
                                                    <input type="text" class="form-control" id="phone-customer-edit"
                                                           value="${staff.phoneNumber}" disabled
                                                           style="background-color: white">
                                                </div>
                                            </li>
                                            <li>
                                                <div class="col-md-12 col-sm-12 ">
                                                    <div class="form-group">
                                                        <label>Quốc gia :</label>
                                                        <div class="col-12">
                                                            <c:forEach var="address" items="${staff.addresses}"
                                                                       varStatus="status">
                                                                <c:if test="${status.index == 0}">
                                                                    <input class="form-control" type="text"
                                                                           value="${address.nation}" disabled
                                                                           style="background-color: white">
                                                                </c:if>

                                                            </c:forEach>

                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <label>Ngày tham gia :</label>
                                                <div class="form-group p-b-15">
                                                    <input type="text" class="form-control"
                                                           value="${staff.joinAt}" disabled
                                                           style="background-color: white">
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-9 col-lg-12">
                <div class="cr-card vendor-profile">
                    <div class="cr-card-content vendor-details mb-m-30">
                        <div class="row cr-vendor-info details">
                            <div class="col-sm-12">
                                <h3>Thông tin chi tiết</h3>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="form-group">
                                    <label>Ngày sinh</label>
                                    <div class="col-12">
                                        <input type="text" class="form-control" value="${staff.dateOfBirth}"
                                               style="width: 100%; background-color: white" disabled>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="form-group">
                                    <label>Giới tính</label>
                                    <div class="select-oject-customer">
                                        <input type="text" class="form-control" value="${staff.gender}"
                                               style="width: 100%; background-color: white" disabled">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12 ">
                                <div class="form-group">
                                    <label>Tên đăng nhập</label>
                                    <div class="col-12">
                                        <input id="username-customer-edit"
                                               class="form-control here slug-title" type="text"
                                               value="${staff.username}" disabled style="background-color: white">
                                    </div>
                                </div>
                            </div>
                            <c:forEach var="address" items="${staff.addresses}" varStatus="status">
                                <label>Địa chỉ ${status.index + 1}:</label>
                                <div class="col-md-4 col-sm-12 ">
                                    <div class="form-group">
                                        <label>Tỉnh / Thành Phố</label>
                                        <div class="col-12">
                                            <input id="cost-customer-edit"
                                                   class="form-control" type="text" value="${address.province}" disabled
                                                   style="background-color: white">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 col-sm-12 ">
                                    <div class="form-group">
                                        <label>Quận / Huyện</label>
                                        <div class="col-12">
                                            <input id="cost-customer-edit"
                                                   class="form-control" type="text" value="${address.district}" disabled
                                                   style="background-color: white">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 col-sm-12 ">
                                    <div class="form-group">
                                        <label>Phường / Xã</label>
                                        <div class="col-12">
                                            <input id="cost-customer-edit"
                                                   class="form-control" type="text" value="${address.village}" disabled
                                                   style="background-color: white">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12 col-sm-12">
                                    <div class="form-group">
                                        <label>Địa chỉ cụ thể</label>
                                        <div class="col-12">
                                            <input name="text" id="address-customer-edit"
                                                   class="form-control here slug-title" type="text"
                                                   value="${address.detail}, ${address.village}, ${address.district}, ${address.province}"
                                                   disabled style="background-color: white">
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <div style="padding-bottom: 56px">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>