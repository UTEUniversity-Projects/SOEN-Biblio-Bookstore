<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta charset="UTF-8">
<div class="cr-main-content">
    <div class="container-fluid">
        <div class="cr-page-title">
            <h4 class="cr-card-title">Phản hồi yêu cầu khách hàng</h4>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="cr-card">
                    <div class="cr-card-header">
                        <h4 class="cr-card-title">Chi tiết yêu cầu khách hàng</h4>
                    </div>
                    <div class="cr-card-content">
                        <form id="responseForm" action="${pageContext.request.contextPath}/staff/support-customer-dashboard" method="POST" accept-charset="UTF-8">
                            <input type="hidden" name="action" value="respond">
                            <input type="hidden" name="id" value="${supportRequest.id}">

                            <div class="mb-3">
                                <label for="customerName" class="form-label">Tên khách hàng</label>
                                <input type="text" class="form-control" id="customerName" value="${supportRequest.customer.fullName}" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="requestDate" class="form-label">Ngày gửi yêu cầu</label>
                                <input type="text" class="form-control" id="requestDate" value="${supportRequest.createdAt}" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="requestDetails" class="form-label">Yêu cầu</label>
                                <textarea class="form-control" id="requestDetails" rows="3" readonly>${supportRequest.content}</textarea>
                            </div>

                            <c:choose>
                                <c:when test="${not empty responseSupport}">
                                    <!-- Nếu đã có phản hồi -->
                                    <div class="mb-3">
                                        <label for="responseTitle" class="form-label">Tiêu đề phản hồi</label>
                                        <input type="text" class="form-control" id="responseTitle" value="${responseSupport.title}" readonly>
                                    </div>
                                    <div class="mb-3">
                                        <label for="responseContent" class="form-label">Nội dung phản hồi</label>
                                        <textarea class="form-control" id="responseContent" rows="5" readonly>${responseSupport.content}</textarea>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <!-- Nếu chưa có phản hồi, cho phép nhập phản hồi -->
                                    <div class="mb-3">
                                        <label for="title" class="form-label">Title</label>
                                        <input type="text" class="form-control" id="title" name="title" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="responseText" class="form-label">Phản hồi</label>
                                        <textarea class="form-control" id="responseText" name="feedbackContent" rows="5" required></textarea>
                                    </div>
                                    <div class="mb-3">
                                        <button type="submit" class="btn btn-success">Gửi phản hồi</button>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
