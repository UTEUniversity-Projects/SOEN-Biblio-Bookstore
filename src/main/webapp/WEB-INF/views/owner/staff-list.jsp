<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- main content -->
<div class="cr-main-content">
  <div class="container-fluid">
    <!-- Page title & breadcrumb -->
    <div class="cr-page-title cr-page-title-2">
      <div class="cr-breadcrumb">
        <h5>Danh sách nhân viên</h5>
        <ul>
          <li><a href="${pageContext.request.contextPath}/owner/ecommerce">Biblio</a></li>
          <li>Danh sách nhân viên</li>
        </ul>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="cr-card card-default product-list">
          <div class="cr-card-content">
            <div class="table-responsive">
              <table id="customer-data-table" class="item-data-table table table-hover">
                <thead>
                <tr>
                  <th>Mã</th>
                  <th>Họ và Tên</th>
                  <th>Email</th>
                  <th>Số điện thoại</th>
                  <th>Bắt đầu làm việc</th>
                  <th>Trạng thái</th>
                  <th>Thao tác</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="staff" items="${staffList}">
                  <tr class="staff-row" data-href="/owner/staff-profile?id=${staff.id}">
                    <td>${staff.id}</td>
                    <td>${staff.fullName}</td>
                    <td>${staff.emailAddress}</td>
                    <td>${staff.phoneNumber}</td>
                    <td>${staff.joinAt}</td>
                    <td class="cod" id="status-${staff.id}">${staff.status}</td>
                    <td>
                      <div class="d-flex justify-content-start">
                        <button type="button"
                                class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                data-bs-toggle="dropdown" aria-haspopup="true"
                                aria-expanded="false" data-display="static">
                          <span class="sr-only"><i class="ri-settings-3-line"></i></span>
                        </button>
                        <div class="dropdown-menu">
                          <c:choose>
                            <c:when test="${staff.status == 'ACTIVE'}">
                              <a class="dropdown-item pop-positioned-timeout" href="#" onclick="changeStatus(${staff.id}, 'deactivate'); return false;">Vô hiệu hóa tài khoản</a>
                            </c:when>
                            <c:otherwise>
                              <a class="dropdown-item pop-positioned-timeout" href="#" onclick="changeStatus(${staff.id}, 'activate'); return false;">Mở khóa tài khoản</a>
                            </c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/assets/owner/js/manage/manage-staff.js" defer></script>