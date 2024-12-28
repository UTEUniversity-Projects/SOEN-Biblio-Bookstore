<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <div class="container-xl">
        <div class="row">
            <div class="col-lg-12">
                <div class="top-header">
                    <a href="${pageContext.request.contextPath}/home" class="cr-logo">
                        <img src="${pageContext.request.contextPath}/assets/commons/img/logo/biblio.png" alt="logo"
                             class="logo"/>
                    </a>
                    <form class="cr-search" action="search" method="GET" id="search-book">
                        <input
                                class="search-input"
                                type="text"
                                placeholder="Tìm kiếm sách..."
                                name="title"
                                value="${title}"
                        />
                        <button class="btn-search search-btn" type="submit">
                            <i class="ri-search-line"></i>
                        </button>
                        <span class="form-message text-[16px] text-red-500"></span>
                    </form>
                    <div class="cr-right-bar">
                        <ul class="my-0">
                            <li class="relative account">
                                <a
                                        class="nav-link dropdown-toggle cr-right-bar-item"
                                        href="javascript:void(0)"
                                >
                                    <i class="ri-user-3-line"></i>
                                    <span>Tài khoản</span>
                                </a>
                                <ul
                                        class="account-list transition-all duration-300 z-[9] absolute top-[35px] right-[20px] bg-white shadow-[rgba(149,_157,_165,_0.5)_0px_-3px_24px] rounded w-[200px]"
                                >
                                    <c:choose>
                                        <c:when test="${account == null}">
                                            <li
                                                    class="hover:bg-gray-100 hover:text-black transition-all duration-200"
                                            >
                                                <a class="block px-5 py-2" href="login"
                                                >Đăng nhập</a
                                                >
                                            </li>

                                            <li
                                                    class="hover:bg-gray-100 hover:text-black transition-all duration-200"
                                            >
                                                <a class="block px-5 py-2" href="verify-email"
                                                >Đăng ký</a
                                                >
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li
                                                    class="hover:bg-gray-100 hover:text-black transition-all duration-200"
                                            >
                                                <a class="block px-5 py-2" href="user-information"
                                                >Thông tin cá nhân</a
                                                >
                                            </li>
                                            <li
                                                    class="hover:bg-gray-100 hover:text-black transition-all duration-200"
                                            >
                                                <a class="block px-5 py-2" href="order">
                                                    <span>Đơn hàng của tôi</span>
                                                </a>
                                            </li>
                                            <li
                                                    class="hover:bg-gray-100 hover:text-black transition-all duration-200"
                                            >
                                                <a class="block px-5 py-2" href="logout"
                                                >Đăng xuất</a
                                                >
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </li>
                        </ul>
                        <div class="notification">
                            <a href="${pageContext.request.contextPath}/notifications" class="cr-right-bar-item relative">
                                <i class="ri-notification-3-line"></i>
                                <span>Thông báo</span>
                                <p class="notification-count absolute top-[-10px] left-[10px] rounded-full bg-black text-white inline-block w-4 h-4 text-[10px] text-center">0</p>
                            </a>
                            <div class="notification-content ${account == null ? 'flex items-center justify-center flex-col py-5' : ''} transition-all duration-300">
                                <c:choose>
                                    <c:when test="${account != null}">
<%--                                        <div class="text-right px-4 py-2">--%>
<%--                                            <h3 class="notification-header text-gray-500">Thông báo mới nhận</h3>--%>
<%--                                        </div>--%>
                                        <ul class="notification-body">

                                        </ul>
                                        <a class="notification-view-all inline-block py-2" href="${pageContext.request.contextPath}/notifications">Xem tất cả</a>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Đăng nhập để xem thông báo !</p>
                                        <div class="w-[200px] h-[200px]">
                                            <img class="w-full h-full object-cover" src="https://static.vecteezy.com/system/resources/previews/014/814/039/non_2x/a-well-designed-flat-icon-of-no-notification-yet-vector.jpg">
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>


                        <div class="cart">
                            <a
                                    href="javascript:void(0)"
                                    class="cr-right-bar-item Shopping-toggle relative"
                                    id="view-cart-btn"
                            >
                                <i class="ri-shopping-cart-line"></i>
                                <span>Giỏ hàng</span>
                                <p class="cart-item-count absolute top-[-10px] left-[10px] rounded-full bg-black text-white inline-block w-4 h-4 text-[10px] text-center">
                                    0</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<script
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"
        integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/search-book.js" type="module"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/notifications.js" type="module"></script>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    $(document).ready(function() {
        $.ajax({
            url: `${pageContext.request.contextPath}/api/customer/notification/get`,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                if (response && response.length > 0) {
                    let notificationList = '';

                    $.each(response, function(index, notification) {
                        const title = notification.title || "No title";
                        const content = notification.content || "No content";
                        const sentTime = notification.sentTime || "N/A";
                        const hyperlink = "${pageContext.request.contextPath}" + notification.hyperLink || "#";
                        const isRead = notification.status === "VIEWED";

                        const li = $("<li>").attr("data-id", notification.id).addClass("px-4 py-2 notification-item");
                        li.html(`
                            <a href="#" class="notification-link">
                                <div class="flex gap-2 items-center">
                                    <div class="flex-1">
                                        <h4 class="w-full mb-1 font-medium title"></h4>
                                        <p class="time"></p>
                                        <p class="message"></p>
                                    </div>
                                </div>
                            </a>
                        `);

                        li.find(".title").text(title);
                        li.find(".time").text(sentTime);
                        li.find(".message").text(content);

                        if (!isRead) {
                            li.css("background-color", "#f0f8ff");
                            li.css("font-weight", "bold");
                        }

                        li.find(".notification-link").attr("href", hyperlink);

                        $('.notification-body').append(li);
                    });

                    $('.notification-count').text(response.length);
                } else {
                    $('.notification-body').html('<li>Không có thông báo nào.</li>');
                }
            },
            error: function(xhr, status, error) {
                console.error("Lỗi khi tải thông báo:", error);
            }
        });

        // Click event for notification item
        $(".notification-body").on("click", ".notification-item", function(e) {
            const notificationId = $(this).data("id");
            const link = $(this).find(".notification-link").attr("href");
            console.log("h");

            console.log(notificationId);

            // Send AJAX request to update notification status
            $.ajax({
                url: `${pageContext.request.contextPath}/api/customer/notification/update`,
                type: "POST",
                data: { notificationId: notificationId },
                success: function(response) {
                    console.log("Trạng thái thông báo đã được cập nhật!");
                    window.location.href = link;  // Redirect to the notification link after updating the status
                },
                error: function(xhr, status, error) {
                    console.error("Lỗi khi cập nhật trạng thái thông báo:", error);
                }
            });
        });
    });
</script>



