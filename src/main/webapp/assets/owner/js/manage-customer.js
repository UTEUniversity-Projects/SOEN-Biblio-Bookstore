// Function to show confirmation modal and set customer ID
function showConfirmationModal(customerId, action) {
    // Set the customer ID to the hidden input field
    document.querySelector('.review-id').value = customerId;

    // Modify the modal message based on the action (activate or deactivate)
    const modalTitle = document.querySelector('#hideReviewModalLabel');
    const confirmButton = document.querySelector('#confirmHideReview');

    if (action === 'deactivate') {
        modalTitle.textContent = "Bạn có chắc muốn khóa tài khoản này không?";
    } else {
        modalTitle.textContent = "Bạn có chắc muốn mở khóa tài khoản này không?";
    }

    // Set the action to be performed when the confirm button is clicked
    confirmButton.onclick = function() {
        // Call the changeStatus function
        changeStatus(customerId, action);
        $('#hideReviewModal').modal('hide');
    };

    // Show the modal
    $('#hideReviewModal').modal('show');
}

// Function to handle status change via AJAX
function changeStatus(customerId, action) {
    const url = `${contextPath}/owner/customer-list`;

    $.ajax({
        url: url,
        type: 'POST',
        data: {
            action: action,
            id: customerId
        },
        success: function (data) {
            if (data === 'success') {
                const message = action === 'deactivate' ? 'Tài khoản đã bị vô hiệu hóa' : 'Tài khoản đã được mở khóa';
                toast({
                    title: "Thành công!",
                    message: message,
                    type: "success",
                    duration: 1000,
                });

                // Cập nhật trạng thái hiển thị
                const statusElement = document.getElementById(`status-${customerId}`);
                if (statusElement) {
                    statusElement.innerText = (action === 'deactivate') ? 'INACTIVE' : 'ACTIVE';
                }

                // Cập nhật nội dung dropdown-menu
                const dropdownMenu = statusElement.closest('tr').querySelector('.dropdown-menu');
                if (dropdownMenu) {
                    if (action === 'deactivate') {
                        dropdownMenu.innerHTML = `<a class="dropdown-item pop-positioned-timeout" href="#" onclick="showConfirmationModal(${customerId}, 'activate'); return false;">Mở khóa tài khoản</a>`;
                    } else {
                        dropdownMenu.innerHTML = `<a class="dropdown-item pop-positioned-timeout" href="#" onclick="showConfirmationModal(${customerId}, 'deactivate'); return false;">Vô hiệu hóa tài khoản</a>`;
                    }
                }
            } else {
                toast({
                    title: "Thất bại!",
                    message: "Có lỗi xảy ra khi thay đổi trạng thái tài khoản.",
                    type: "error",
                    duration: 1000,
                });
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            toast({
                title: "Thất bại!",
                message: "Có lỗi xảy ra khi kết nối tới server.",
                type: "error",
                duration: 1000,
            });
        }
    });
}


document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.querySelector("#customer-data-table tbody");
    if (tableBody) {
        tableBody.addEventListener("click", function (event) {
            const row = event.target.closest(".customer-row");

            if (
                row &&
                !event.target.closest("button") &&
                !event.target.closest(".dropdown-menu")
            ) {
                const href = row.getAttribute("data-href");
                window.location.href = href;
            }
        });
    }
});


