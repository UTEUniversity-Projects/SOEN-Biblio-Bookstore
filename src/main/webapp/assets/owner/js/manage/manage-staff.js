function changeStatus(staffId, action) {
    const url = `/owner/staff-list`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `action=${action}&id=${staffId}`
    })
        .then(response => response.text())
        .then(data => {
            if (data === 'success') {
                alert(action === 'deactivate' ? 'Tài khoản đã bị vô hiệu hóa' : 'Tài khoản đã được mở khóa');

                const statusElement = document.getElementById(`status-${staffId}`);
                if (statusElement) {
                    statusElement.innerText = (action === 'deactivate') ? 'INACTIVE' : 'ACTIVE';
                }
            } else {
                alert('Có lỗi xảy ra khi thay đổi trạng thái tài khoản');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi kết nối tới server');
        });
}
document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.querySelector(".virtual-data-table tbody");

    if (tableBody) {
        tableBody.addEventListener("click", function (event) {
            const row = event.target.closest(".staff-row");

            if (
                row &&
                !event.target.closest("button") &&
                !event.target.closest(".dropdown-menu")
            ) {
                window.location.href = row.getAttribute("data-href");
            }
        });
    }
});
