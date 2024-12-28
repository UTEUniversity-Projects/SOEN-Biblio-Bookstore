function toggleItems(orderId = null) {
    // Lấy phần tử nút toggle
    const toggleText = orderId
        ? document.getElementById(`toggle-text-${orderId}`)
        : document.getElementById("toggle-text");

    // Lấy tất cả các mục cần hiển thị/ẩn
    const items = orderId
        ? document.querySelectorAll(`.order-item[data-order-id='${orderId}'] .my-hidden, .order-item[data-order-id='${orderId}'] .my-visible`)
        : document.querySelectorAll(".my-hidden, .my-visible");

    // Kiểm tra trạng thái ẩn/hiện
    const isHidden = Array.from(items).every(item => item.classList.contains("my-hidden"));

    // Thay đổi trạng thái của các mục
    items.forEach(item => {
        item.classList.toggle("my-hidden", !isHidden);
        item.classList.toggle("my-visible", isHidden);
    });

    // Cập nhật nội dung nút toggle
    toggleText.textContent = isHidden ? "Thu gọn ▲" : "Xem thêm ▼";
}
