function validateInput(input) {
    const errorSpan = input.closest('.col-12').querySelector('.' + input.name + 'Error');

    // Kiểm tra nếu input rỗng
    if (input.value.trim() === "") {
        errorSpan.textContent = "Mục này không được để trống.";
        return;
    } else {
        errorSpan.textContent = ""; // Xóa lỗi khi nhập hợp lệ
    }

    // Kiểm tra số nguyên dương cho các trường 'discountLimit', 'minValueApplied', và 'quantity'
    if (["discountLimit", "minValueApplied", "quantity"].includes(input.name)) {
        if (!Number.isInteger(Number(input.value)) || Number(input.value) < 0) {
            errorSpan.textContent = "Giá trị không được là số âm.";
            return;
        } else {
            errorSpan.textContent = "";
        }
    }

    // Kiểm tra 'percentDiscount' phải trong khoảng từ 0 đến 100
    if (input.name === "percentDiscount") {
        const value = Number(input.value);
        if (value < 1 || value > 100) {
            errorSpan.textContent = "Giá trị phải nằm trong khoảng từ 1% đến 100%.";
            return;
        } else {
            errorSpan.textContent = "";
        }
    }
}

// Áp dụng sự kiện 'input' cho tất cả các input để xóa lỗi khi người dùng bắt đầu nhập
document.querySelectorAll('.promotionForm .form-control').forEach(input => {
    input.addEventListener('input', () => validateInput(input));
});

// Sự kiện cho checkbox "Không giới hạn"
document.querySelectorAll('.promotionForm input[name="unlimited"]').forEach(checkbox => {
    checkbox.addEventListener('change', function () {
        const quantityError = checkbox.closest('.col-12').querySelector('.quantityError');
        if (checkbox.checked) {
            quantityError.textContent = ""; // Xóa lỗi khi chọn "Không giới hạn"
        }
    });
});

function validateForm(form) {
    let isValid = true;
    const inputs = form.querySelectorAll('.form-control');
    const codeInput = form.querySelector('input[name="code"]');
    const codeValue = codeInput ? codeInput.value.trim() : "";

    const selectElement = form.querySelector('#select-object-discount');
    const selectErrorSpan = form.querySelector('.selectOjectError');
    if (selectElement && selectElement.value === "- Chọn đối tượng áp dụng -") {
        // selectErrorSpan.textContent = "Bạn cần chọn một đối tượng áp dụng.";
        isValid = false;
    } else if (selectErrorSpan) {
        selectErrorSpan.textContent = "";
    }

    inputs.forEach(input => {

        //  const errorSpan = input.closest('.col-12').querySelector(`.${input.name}Error`);
        // Bỏ qua kiểm tra 'quantity' nếu 'unlimited' được chọn
        if (input.name === "quantity") {
            const errorSpan = input.closest('.col-12').querySelector('.' + input.name + 'Error');
            const unlimitedCheckbox = input.closest('.col-12').querySelector('input[name="unlimited"]');
            if (unlimitedCheckbox && unlimitedCheckbox.checked) {
                errorSpan.textContent = "";
                return;
            }
        }

        // Kiểm tra nếu input rỗng
        if (input.value.trim() === "") {
            if (input.type === "search") return
            const errorSpan = input.closest('.col-12').querySelector('.' + input.name + 'Error');
            errorSpan.textContent = "Mục này không được để trống.";
            if (input.type === "search") return
            isValid = false;
            // return;
        }

        // Kiểm tra số nguyên dương cho các trường 'discountLimit', 'minValueApplied', và 'quantity'
        if (["discountLimit", "minValueApplied", "quantity"].includes(input.name)) {
            const errorSpan = input.closest('.col-12').querySelector('.' + input.name + 'Error');

            if (!Number.isInteger(Number(input.value)) || Number(input.value) < 1) {
                errorSpan.textContent = "Giá trị không được là số âm.";
                isValid = false;
                // return;
            } else {
                errorSpan.textContent = "";
            }
        }

        // Kiểm tra 'percentDiscount' phải trong khoảng từ 1 đến 100
        if (input.name === "percentDiscount") {
            const errorSpan = input.closest('.col-12').querySelector('.' + input.name + 'Error');
            const value = Number(input.value);
            if (value < 1 || value > 100) {
                errorSpan.textContent = "Giá trị phải nằm trong khoảng từ 1% đến 100%.";
                isValid = false;
                return;
            } else {
                errorSpan.textContent = "";
            }
        }
    });
    return isValid;
}

document.querySelectorAll('.promotionForm').forEach(form => {
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Ngăn chặn submit mặc định

        const submitButton = form.querySelector(".btn-voucher");
        const isEditing = submitButton.dataset.editing === "true"; // Kiểm tra trạng thái của nút

        if (!isEditing) {
            // Nếu không ở trạng thái chỉnh sửa, chuyển sang trạng thái chỉnh sửa
            toggleForm(submitButton);
        } else if (!validateForm(form)) {
            // Nếu đang chỉnh sửa nhưng form không hợp lệ
            toast({
                title: "Thất bại!",
                message: 'Vui lòng điền đầy đủ thông tin',
                type: "warning",
                duration: 3000,
            });
        } else {
            toast({
                title: "Thành Công!",
                message: 'Chỉnh sửa thành công',
                type: "success",
                duration: 3000,
            });
            // Submit form nếu hợp lệ
            form.submit();
        }
    });
});

// Hàm toggleForm để xử lý chuyển trạng thái nút và form
function toggleForm(editButton) {
    const form = editButton.closest("form");

    const cancelButton = form.querySelector(".cancel-btn");

    const isEditing = editButton.dataset.editing === "true";

    const inputs = form.querySelectorAll("input, textarea, select");
    const table = form.querySelector(".table_item_to_discount"); // Tìm table trong form (nếu có)

    if (isEditing) {
        // Khóa form
        inputs.forEach(input => {
            if (input.name !== "code") {
                input.disabled = true;
            }
        });

        // Vô hiệu hóa bảng nếu có
        if (table) {
            table.classList.add("table-disabled");
        }

        editButton.innerText = "Chỉnh sửa";
        // editButton.dataset.editing = "false";
    } else {
        // Mở khóa form
        inputs.forEach(input => {
            if (input.name !== "code") {
                input.disabled = false;
            }
            if (input.name === "quantity" && input.value === "") {
                input.disabled = true;
            }
        });

        // Mở khóa bảng nếu có
        if (table) {
            table.classList.remove("table-disabled");
        }

        editButton.innerText = "Lưu";
        editButton.dataset.editing = "true";
        cancelButton.style.display = "inline-block";
    }
}


function cancelEdit(cancelButton) {
    // Reload lại trang
    location.reload();
}






$(document).ready(function () {
    const table = $(".table_item_to_discount").DataTable({
        aLengthMenu: [[10, 30, -1], [10, 30, "All"]],
        pageLength: 10,
        ordering: false,
        dom: '<"row justify-content-between top-information"lf>rt<"row justify-content-between bottom-information"ip><"clear">',
    });

    function fetchData(url, params = {}, onSuccess, onError = 'Đã xảy ra lỗi khi gọi API.') {
        $.ajax({
            url,
            type: 'GET',
            data: params,
            success: response => onSuccess?.(response),
            error: () => alert(onError),
        });
    }

    $('#select-object-discount').change(function () {
        const selectedValue = $(this).val();
        resetSelections();

        if (selectedValue === "1") handleCase1();
        else if (selectedValue === "2") handleCase2();
        else if (selectedValue === "3") handleCase3();
    });

    function resetSelections() {
        table.clear().draw();
    }

    function handleCase1() {
        fetchData(`${contextPath}/owner/promotion/get-book`, {}, books => {
            populateTable(books, 'Không có sách nào!');
        }, 'Không thể tải danh sách sách!');
    }

    function handleCase2() {
        fetchData(`${contextPath}/owner/promotion/get-categories`, {}, categories => {
            populateTable(categories, 'Không có danh mục nào!');
        }, 'Không thể tải danh sách danh mục!');
    }

    function handleCase3() {
        fetchData(`${contextPath}/owner/promotion/get-subcategories`, {}, subcategories => {
            populateTable(subcategories, 'Không có danh mục con nào!');
        }, 'Không thể tải danh mục con!');
    }

    function populateTable(data, emptyMessage) {
        table.clear();

        if (data?.length) {
            data.forEach(item => {
                const isChecked = selectedIds.includes(parseInt(item.id)) ? 'checked' : '';
                table.row.add([
                    `<input type="checkbox" class="row-checkbox" name="selectedItems" value="${item.id}" ${isChecked} />`,
                    `#${item.id}`,
                    `${item.name || item.title}`
                ]);
            });
        } else {
            table.row.add(['', '', emptyMessage]);
        }

        table.draw();

        // Thêm sự kiện click vào mỗi hàng sau khi bảng được vẽ
        $('.table_item_to_discount tbody').on('click', 'tr', function (e) {
            // Nếu nhấn trực tiếp vào checkbox, không làm gì cả
            if ($(e.target).is('.row-checkbox')) return;

            const checkbox = $(this).find('.row-checkbox');
            checkbox.prop('checked', !checkbox.prop('checked'));
        });
    }
    // Gọi hàm xử lý theo lựa chọn mặc định khi load trang
    const defaultValue = $('#select-object-discount').val();
    if (defaultValue === "1") handleCase1();
    else if (defaultValue === "2") handleCase2();
    else if (defaultValue === "3") handleCase3();
});


function stopPromotion() {
    // Lấy giá trị mã khuyến mãi từ input có name="code"
    var code = document.querySelector('input[name="code"]').value;
    console.log(code);
    if (!code) {
        alert('Mã khuyến mãi không hợp lệ.');
        return;
    }

    // Mở modal xác nhận
    var myModal = new bootstrap.Modal(document.getElementById('hideReviewModal'));
    myModal.show();

    // Lưu giá trị mã khuyến mãi vào modal (nếu cần thiết)
    document.querySelector('.review-id').value = code;
}

function confirmStopPromotion() {
    // Lấy mã khuyến mãi đã lưu trong modal
    var code = document.querySelector('.review-id').value;

    // Gửi yêu cầu AJAX đến API
    $.ajax({
        url: `${contextPath}/api/owner/promotion/stop-promotion`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ code: code }),  // Dữ liệu gửi đi là mã khuyến mãi
        success: function(response) {
            toast({
                title: "Thành Công!",
                message: 'Dừng khuyến mãi thành công',
                type: "success",
                duration: 3000,
            });
            if (response.message.includes("thành công")) {
                $('.stop-promotion-btn').hide();
                $('.cr-btn-primary').hide();
            }
        },
        error: function(xhr, status, error) {
            var errMsg = xhr.responseJSON ? xhr.responseJSON.message : "Đã có lỗi xảy ra, vui lòng thử lại.";
            alert(errMsg);
            toast({
                title: "Thất bại",
                message: 'Đã có lỗi xảy ra, vui lòng thử lại.',
                type: "warning",
                duration: 3000,
            });
        }
    });

    // Đóng modal sau khi gửi yêu cầu
    var myModal = bootstrap.Modal.getInstance(document.getElementById('hideReviewModal'));
    myModal.hide();
}








