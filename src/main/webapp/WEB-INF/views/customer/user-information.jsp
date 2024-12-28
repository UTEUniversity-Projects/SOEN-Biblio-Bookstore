<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script defer src="${pageContext.request.contextPath}/assets/customer/js/update-information.js"></script>

<section class="section-profile padding-tb-100">
    <div class="container-md">
        <div class="row justify-center">
            <div class="col-sm-8 col-md-12">
                <div class="profile-card" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="400">
                    <!-- Logo ở trên -->
                    <div class="logo-container">
                        <img src="${pageContext.request.contextPath}/assets/commons/img/logo/biblio.png" alt="logo"
                             class="logo"/>
                    </div>

                    <div class="row">
                        <!-- Phần bên trái: Avatar, Tên, Giới tính, Membership -->
                        <div class="col-md-4">
                            <div class="left-section">
                                <div class="avatar-container">
                                    <img src="${customer.avatar}" alt="avatar" class="avatar"/>
                                </div>
                                <div class="user-info">
                                    <h3 class="name">${customer.fullName}</h3>
                                    <p class="gender">Giới tính: ${customer.gender}</p>
                                    <p class="membership">Membership: ${customer.memberShip}</p>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-8">
                            <form action="${pageContext.request.contextPath}/update-information" method="post" class="profile-form">

                                <div class="row">
                                    <div class="form-group hidden col-sm-12 col-md-6 mb-2">
                                        <label class="label">Tên:</label>
                                        <input type="text" name ="fullName" value="${customer.fullName}" class="form-control pointer-events-none" readonly />
                                    </div>
                                    <div class="form-group hidden gender-options col-sm-12 col-md-6 mb-2">
                                        <label class="label">Giới tính:</label>
                                        <ul>
                                            <li>
                                                <input type="radio" name="gender" value="MALE"
                                                       <c:if test="${customer.gender == 'Nam'}">checked</c:if> /> Nam
                                            </li>
                                            <li>
                                                <input type="radio" name="gender" value="FEMALE"
                                                       <c:if test="${customer.gender == 'Nữ'}">checked</c:if> /> Nữ
                                            </li>
                                        </ul>
                                    </div>
                                </div>


                                <div class="row">
                                    <div class="col-sm-12 col-md-6">
                                        <div class="form-group mb-2">
                                            <label class="label">Email*</label>
                                            <input type="email" value="${customer.email}" readonly
                                                   class="form-control pointer-events-none" id="readonlyEmail" />
                                        </div>
                                    </div>

                                    <div class="col-sm-12 col-md-6">
                                        <div class="form-group mb-2">
                                            <label class="label">Tên đăng nhập*</label>
                                            <input type="text" value="${customer.username}" readonly
                                                   class="form-control pointer-events-none" id="readonlyUsername"/>
                                        </div>
                                    </div>

                                    <!-- Số điện thoại và Ngày sinh -->
                                    <div class="col-sm-12 col-md-6">
                                        <div class="form-group mb-2">
                                            <label class="label">Số điện thoại*</label>
                                            <input type="tel" value="${customer.phoneNumber}" readonly
                                                   class="form-control pointer-events-none" id="readonlyPhoneNumber"/>
                                        </div>
                                    </div>

                                    <div class="col-sm-12 col-md-6">
                                        <div class="form-group mb-2">
                                            <label class="label">Ngày sinh</label>
                                            <input type="date" name="dateOfBirth" value="${customer.dateOfBirth}" readonly
                                                   class="form-control pointer-events-none"/>
                                        </div>
                                    </div>

                                    <!-- Địa chỉ -->
                                    <div class="col-sm-12 col-md-12">
                                        <div class="form-group mb-2">
                                            <p class="font-bold text-xl">Địa chỉ</p>
                                            <ul>
                                                <c:forEach items="${customer.addresses}" var="o">
                                                    <li>
                                                        <p>
                                                            - ${o.detail}, ${o.village}, ${o.district}, ${o.province}, ${o.nation}.</p>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>

                                </div>

                                <div class="button-container mt-3">
                                    <div class="button-group">
                                        <a href="#" class="change-password-button">Đổi mật khẩu</a>
                                        <button type="button" id="edit-button" class="update-button">Chỉnh sửa</button>
                                        <button type="submit" id="save-button" class="update-button hidden" onclick="window.location.href='${pageContext.request.contextPath}/update-information?customerId=${customer.id}'">Lưu</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
