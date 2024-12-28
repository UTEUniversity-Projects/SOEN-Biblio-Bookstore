<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5>Thông tin nhân viên</h5>
                <ul>
                    <li><a href="#">Biblio</a></li>
                    <li>Thông tin nhân viên</li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-xxl-3 col-xl-4 col-md-12">
                <div class="vendor-sticky-bar">
                    <div class="col-xl-12">
                        <div class="cr-card">
                            <div class="cr-card-content">
                                <div class="cr-vendor-block-img">
                                    <div class="cr-vendor-block-detail">
                                        <div class="profile-img">
                                            <img class="v-img" src="/assets/owner/img/clients/3.jpg"
                                                 alt="vendor image">
                                            <span class="tag-label online"></span>
                                        </div>
                                        <h5 class="name">Nguyễn Văn An</h5>
                                        <p>( nvan@gmail.com )</p>
                                        <div class="cr-settings">
                                            <a href="staff-update.html" class="cr-btn-primary m-r-10">Chỉnh sửa</a>
                                        </div>
                                    </div>
                                    <div class="cr-vendor-info">
                                        <ul>
                                            <li><span class="label">Họ và tên :</span>&nbsp;Nguyễn Văn An</li>
                                            <li><span class="label">Giới tính :</span>&nbsp;Nam</li>
                                            <li><span class="label">Ngày sinh :</span>&nbsp;26/12/2000</li>
                                            <li><span class="label">Số điện thoại :</span>&nbsp;075786998</li>
                                            <li><span class="label">Ngày tham gia :</span>&nbsp;05/06/2022</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xxl-9 col-xl-8 col-md-12">

                <div class="cr-card vendor-profile">
                    <div class="cr-card-content vendor-details mb-m-30">

                        <div class="row">
                            <div class="col-sm-12">
                                <h3>Quản lý thông tin</h3>
                                <br>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="cr-vendor-detail">
                                    <h6>Cá nhân</h6>
                                    <ul>
                                        <label>Họ và tên</label>
                                        <div class="col-12">
                                            <input name="text" class="form-control here slug-title" type="text"
                                                   placeholder="Nguyễn Văn An">
                                        </div>
                                        <label>Giới tính</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Nam</option>
                                                <option>Nữ</option>
                                            </select>
                                        </div>
                                        <label>Ngày sinh</label>
                                        <div class="col-12">
                                            <div class="input-group date" id="datepicker">
                                                <input type="text" class="form-control" id="date"
                                                       placeholder="DD/MM/YYYY"/>
                                                <span class="input-group-text bg-light d-block"
                                                      style="cursor: pointer;">
															<i class="ri-calendar-2-line"></i>
														</span>
                                            </div>
                                        </div>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="cr-vendor-detail">
                                    <h6>Tài khoản</h6>
                                    <ul>
                                        <label>Tên tài khoản</label>
                                        <div class="col-12">
                                            <input name="text" class="form-control here slug-title" type="text"
                                                   placeholder="dotnvan">
                                        </div>
                                        <label>Địa chỉ email</label>
                                        <div class="col-12">
                                            <input name="text" class="form-control here slug-title" type="text"
                                                   placeholder="nvan@gmail.com">
                                        </div>
                                        <label>Số điện thoại</label>
                                        <div class="col-12">
                                            <input name="text" class="form-control here slug-title" type="text"
                                                   placeholder="075768xxx">
                                        </div>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="cr-vendor-detail">
                                    <h6>Địa chỉ 1</h6>
                                    <ul>
                                        <label>Quốc gia</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Việt Nam</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Tỉnh / Thành phố</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Tp. Hồ Chí Minh</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Quận / Huyện</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Quận 9</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Phường / Xã</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Tăng Nhơn Phú A</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Chi tiết</label>
                                        <div class="col-12">
                                            <input name="text" class="form-control here slug-title" type="text"
                                                   placeholder="Số nhà, Tên đường, ...">
                                        </div>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-12">
                                <div class="cr-vendor-detail">
                                    <h6>Địa chỉ 2</h6>
                                    <ul>
                                        <label>Quốc gia</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Việt Nam</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Tỉnh / Thành phố</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Tp. Hồ Chí Minh</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Quận / Huyện</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Quận 9</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Phường / Xã</label>
                                        <div class="col-12">
                                            <select class="form-control form-select">
                                                <option>Tăng Nhơn Phú A</option>
                                                <option>B</option>
                                                <option>C</option>
                                            </select>
                                        </div>
                                        <label>Chi tiết</label>
                                        <div class="col-12">
                                            <input name="text" class="form-control here slug-title" type="text"
                                                   placeholder="Số nhà, Tên đường, ...">
                                        </div>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
