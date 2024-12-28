import {toast} from './toast.js';

/**
 * Hủy đơn hàng bằng cách gửi yêu cầu đến servlet
 * @param {Long} orderId - ID của đơn hàng cần hủy
 */

async function cancelOrder(orderId) {
    // Hiển thị modal
    const cancelModal = new bootstrap.Modal(document.getElementById('cancelOrderModal'));
    cancelModal.show();

    // Xử lý khi người dùng nhấn nút "Xác nhận"
    const confirmButton = document.getElementById('confirmCancelOrder');
    confirmButton.onclick = async () => {
        try {
            const response = await fetch(`${contextPath}/api/customer/cancelOrder`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `orderId=${orderId}`
            });

            const data = await response.json();
            console.log(data);

            if (data.success) {
                // Thông báo thành công bằng toast
                toast({
                    title: "Thành công",
                    message: "Hủy đơn hàng thành công!",
                    type: "success",
                    duration: 3000,
                });
                setTimeout(() => {
                    location.reload(); // Tải lại trang để cập nhật thông tin đơn hàng
                }, 1000);
            } else {
                // Thông báo lỗi bằng toast
                toast({
                    title: "Lỗi",
                    message: "Không thể hủy đơn hàng. Vui lòng thử lại.",
                    type: "error",
                    duration: 3000,
                });
            }
        } catch (error) {
            console.error("Error:", error);
            // Thông báo lỗi hệ thống bằng toast
            toast({
                title: "Lỗi hệ thống",
                message: "Có lỗi xảy ra khi hủy đơn hàng. Vui lòng thử lại sau.",
                type: "error",
                duration: 3000,
            });
        }

        // Ẩn modal sau khi xử lý xong
        cancelModal.hide();
    };
}


$(document).ready(() => {

    const btnCancelOrder = document.querySelector(".btn-cancel-order");
    console.log(btnCancelOrder);
    btnCancelOrder.addEventListener("click", () => {

        const orderId = btnCancelOrder.dataset.orderId;
        cancelOrder(orderId);
    });

});
