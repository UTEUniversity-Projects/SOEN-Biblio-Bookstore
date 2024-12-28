<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Notify sidebar -->
<div class="cr-notify-bar-overlay"></div>
<div class="cr-notify-bar">
    <div class="cr-bar-title">
        <h6>Notifications<span class="label" id="notification-count">0</span></h6>
        <a href="javascript:void(0)" class="close-notify">
            <i class="ri-close-line"></i>
        </a>
    </div>
    <div class="cr-bar-content">
<%--        <ul class="nav nav-tabs" id="myTab" role="tablist">--%>
<%--            <li class="nav-item" role="presentation">--%>
<%--                <button--%>
<%--                        class="nav-link active"--%>
<%--                        id="alert-tab"--%>
<%--                        data-bs-toggle="tab"--%>
<%--                        data-bs-target="#alert"--%>
<%--                        type="button"--%>
<%--                        role="tab"--%>
<%--                        aria-controls="alert"--%>
<%--                        aria-selected="true"--%>
<%--                >--%>
<%--                    Alert--%>
<%--                </button>--%>
<%--            </li>--%>
<%--        </ul>--%>
        <div class="tab-content" id="myTabContent">
            <div
                    class="tab-pane fade show active"
                    id="alert"
                    role="tabpanel"
                    aria-labelledby="alert-tab"
            >
                <div class="cr-alert-list">
                    <ul id="notification-list">
                        <!-- Các thông báo sẽ được thêm vào đây -->
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Thêm thư viện jQuery 3.6.4 từ CDN -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<script>
    // Hàm gọi API và hiển thị thông báo bằng AJAX
    function fetchNotifications() {
        console.log("Fetching notifications...");

        $.ajax({
            url: `${contextPath}/api/staff/notification/get`,  // URL đến API
            method: "GET",                       // Phương thức HTTP
            dataType: "json",                    // Định dạng dữ liệu trả về (JSON)
            success: function (notifications) {
                console.log("Notifications received:", notifications);

                // Kiểm tra nếu không có dữ liệu trả về từ API
                if (!notifications || notifications.length === 0) {
                    console.log("No notifications available.");
                    $("#notification-list").empty().append("<li>No notifications available</li>");
                    $("#notification-count").text(0);
                    return;
                }

                const notificationList = $("#notification-list");
                const notificationCount = $("#notification-count");

                // Xóa danh sách thông báo cũ
                notificationList.empty();

                // Cập nhật số lượng thông báo
                notificationCount.text(notifications.length);

                // Duyệt qua các thông báo và thêm vào UI
                notifications.forEach(notification => {
                    console.log("Processing notification:", notification);

                    // Kiểm tra và thay thế các giá trị null/undefined với giá trị mặc định
                    const title = notification.title || "No title";
                    const content = notification.content || "No content";
                    const sentTime = notification.sentTime || "N/A"; // Kiểm tra sentTime
                    const hyperlink = "${pageContext.request.contextPath}" + notification.hyperLink || "#"; // Đảm bảo rằng có link
                    const isRead = notification.status === "VIEWED";

                    console.log("Title:", title);
                    console.log("Content:", content);
                    console.log("SentTime:", sentTime);

                    const li = $("<li>").attr("data-id", notification.id);
                    li.html(`
                    <a href="#" class="notification-link">
                        <div class="icon cr-alert">
                            <i class="ri-alarm-warning-line"></i>
                        </div>
                        <div class="detail">
                            <div class="title"></div> <!-- Gắn text sau -->
                            <p class="time"></p> <!-- Gắn text sau -->
                            <p class="message"></p> <!-- Gắn text sau -->
                        </div>
                    </a>
                `);

                    // Gán giá trị vào các thẻ cụ thể
                    li.find(".title").text(title);
                    li.find(".time").text(content);
                    li.find(".message").text(sentTime);

                    if (!isRead) {
                        li.css("background-color", "#f0f8ff");
                        li.css("font-weight", "bold");
                    }

                    // Gán giá trị vào các thẻ cụ thể
                    li.find(".notification-link").attr("href", hyperlink);

                    notificationList.append(li);
                });

                // Thêm sự kiện click cho từng thông báo
                $("li[data-id]").click(function(e) {
                    e.preventDefault();

                    const notificationId = $(this).attr("data-id");
                    const link = $(this).find(".notification-link").attr("href");

                    $.ajax({
                        url: `${contextPath}/api/staff/notification/update`,
                        method: "POST",
                        data: { notificationId: notificationId },
                        success: function(response) {
                            console.log("Notification status updated successfully.");
                            window.location.href = link;
                        },
                        error: function(xhr, status, error) {
                            console.error("Error updating notification status: ", error);
                            alert("Lỗi khi cập nhật trạng thái thông báo!");
                        }
                    });
                });
            },
            error: function (xhr, status, error) {
                console.error("Error fetching notifications: ", error);
                alert("Lỗi khi tải thông báo!");
            }
        });
    }

    $(document).ready(function () {
        console.log("Page loaded. Calling fetchNotifications()");
        fetchNotifications();
    });

</script>







