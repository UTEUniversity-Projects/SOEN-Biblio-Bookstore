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
        if (!Number.isInteger(Number(input.value)) || Number(input.value) < 1) {
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
        // const errorSpan = input.closest('.col-12').querySelector('.' + input.name + 'Error');
      //  const errorSpan = input.closest('.col-12').querySelector(`.${input.name}Error`);
        // Bỏ qua kiểm tra 'quantity' nếu 'unlimited' được chọn
        if (input.name === "quantity") {
            const unlimitedCheckbox = input.closest('.col-12').querySelector('input[name="unlimited"]');
            if (unlimitedCheckbox && unlimitedCheckbox.checked) {
                // errorSpan.textContent = "";
                return;
            }
        }

        // Kiểm tra nếu input rỗng
        if (input.value.trim() === "") {
            //errorSpan.textContent = "Mục này không được để trống.";
            if (input.type === "search") return
            isValid = false;
            // return;
        }

        // Kiểm tra số nguyên dương cho các trường 'discountLimit', 'minValueApplied', và 'quantity'
        if (["discountLimit", "minValueApplied", "quantity"].includes(input.name)) {
            if (!Number.isInteger(Number(input.value)) || Number(input.value) < 1) {
                // errorSpan.textContent = "Giá trị không được là số âm.";
                isValid = false;
                // return;
            } else {
                // errorSpan.textContent = "";
            }
        }

        // Kiểm tra 'percentDiscount' phải trong khoảng từ 1 đến 100
        if (input.name === "percentDiscount") {
            const value = Number(input.value);
            if (value < 1 || value > 100) {
                // errorSpan.textContent = "Giá trị phải nằm trong khoảng từ 1% đến 100%.";
                isValid = false;
                // return;
            } else {
                // errorSpan.textContent = "";
            }
        }
    });
    return isValid;
}


function parseDateRange(input) {
    const [start, end] = input.split(" - ");

    function formatDate(dateString) {
        const [monthDay, time, period] = dateString.split(" ");
        const [month, day, year] = monthDay.split("/");
        const [hour, minute] = time.split(":");

        let hours = parseInt(hour, 10);
        if (period === "PM" && hours !== 12) hours += 12;
        if (period === "AM" && hours === 12) hours = 0;

        // Tạo đối tượng Date
        const date = new Date();
        date.setFullYear(parseInt(year, 10));
        date.setMonth(parseInt(month, 10) - 1);
        date.setDate(parseInt(day, 10));
        date.setHours(hours);
        date.setMinutes(parseInt(minute, 10));
        date.setSeconds(0);
        date.setMilliseconds(0);

        // Định dạng thành chuỗi theo yêu cầu
        const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}:00.000000`;
        return formattedDate;
    }

    const effectiveDate = formatDate(start);
    const expirationDate = formatDate(end);

    return { effectiveDate, expirationDate };
}


document.querySelectorAll('.promotionForm').forEach(form => {
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        if (!validateForm(form)) {
            toast({
                title: "Thất bại!",
                message: 'Vui lòng điền đầy đủ thông tin',
                type: "warning",
                duration: 3000,
            });
        } else {
            const formData = new FormData(form);
            const data = {};

            // Lấy giá trị từ form và xử lý
            formData.forEach((value, key) => {
                if (key === "datetimes") {
                    const { effectiveDate, expirationDate } = parseDateRange(value);
                    data["effectiveDate"] = effectiveDate;
                    data["expirationDate"] = expirationDate;

                    const effectiveDateObj = new Date(effectiveDate);
                    const currentDate = new Date();
                    data["status"] = effectiveDateObj > currentDate ? "COMING_SOON" : "EFFECTIVE";
                } else {
                    if (key === "DataTables_Table_0_length") return;
                    else data[key] = value.trim();
                }
            });

            const formType = data["formType"];
            let isInfinite = false;
            let quantity = 0;
            const promotionInsertRequests = [];

            // Xử lý từng loại form
            switch (formType) {
                case "addDiscount":
                    isInfinite = true; // Luôn luôn là true cho addDiscount
                    promotionInsertRequests.push(
                        createPromotionRequestTypeDiscount(data, ".table_item_to_discount")
                    );
                    data["type"] = "DISCOUNT";
                    break;

                case "addVoucher":
                case "addFreeShip":
                    // Xử lý checkbox unlimited và quantity
                    const unlimitedCheckbox = form.querySelector('input[name="unlimited"]');
                    quantity = parseInt(data["quantity"], 10);
                    isInfinite = unlimitedCheckbox.checked || isNaN(quantity) || quantity <= 0;

                    if (isInfinite) quantity = 1; // Nếu là vô hạn, đặt quantity = 1

                    // Gọi hàm xử lý tùy theo formType
                    if (formType === "addVoucher") {
                        handleAddVoucher(data, quantity, promotionInsertRequests);
                    } else if (formType === "addFreeShip") {
                        handleAddFreeShip(data, quantity, promotionInsertRequests);
                    }
                    break;

                default:
                    console.error("Loại form không hợp lệ:", formType);
                    return;
            }

            const responseData = {
                code: data["code"],
                isInfinite: isInfinite,
                status: data["status"],
                type: data["type"],
                promotionInsertRequests: promotionInsertRequests
            };

            // Gửi request AJAX
            sendPromotionRequest(responseData, form);
        }
    });
});

// Hàm xử lý formType "addVoucher"
function handleAddVoucher(data, quantity, promotionInsertRequests) {
    data["type"] = "VOUCHER";
    data["percentDiscount"] = 100; // Giảm giá 100%

    for (let i = 0; i < quantity; i++) {
        promotionInsertRequests.push(createPromotionRequest(data));
    }
}

// Hàm xử lý formType "addFreeShip"
function handleAddFreeShip(data, quantity, promotionInsertRequests) {
    data["type"] = "FREESHIP";
    data["percentDiscount"] = 100; // Miễn phí vận chuyển
    data["minValueApplied"] = 0.0; // Giá trị tối thiểu áp dụng là 0

    for (let i = 0; i < quantity; i++) {
        promotionInsertRequests.push(createPromotionRequest(data));
    }
}

// Hàm tạo một request
function createPromotionRequest(data) {
    return {
        title: data["title"] || "Default Title",
        description: data["description"] || "Default Description",
        percentDiscount: parseFloat(data["percentDiscount"]) || 0.0,
        discountLimit: parseFloat(data["discountLimit"]) || 0.0,
        minValueApplied: parseFloat(data["minValueApplied"]) || 0.0,
        effectiveDate: data["effectiveDate"],
        expirationDate: data["expirationDate"],
        status: "NOT_USE",
        promotionTargets: [
            {
                applicableObjectId: -1,
                type: "WHOLE"
            }
        ]
    };
}

function createPromotionRequestTypeDiscount(data, tableSelector) {
    // Thu thập các checkbox được chọn trong bảng
    const selectedCheckboxes = Array.from(
        document.querySelectorAll(`${tableSelector} .row-checkbox:checked`)
    );

    // Lấy giá trị của selectObject từ <select>
    const selectObjectValue = document.querySelector('#select-object-discount').value;

    // Map giá trị value của selectObject thành type
    let promotionTargets = [];
    if (selectedCheckboxes.length > 0) {
        promotionTargets = selectedCheckboxes.map((checkbox) => ({
            applicableObjectId: parseInt(checkbox.value),
            type:
                selectObjectValue === "1"
                    ? "BOOK"
                    : selectObjectValue === "2"
                        ? "CATEGORY"
                        : selectObjectValue === "3"
                            ? "SUBCATEGORY"
                            : "WHOLE",
        }));
    } else {
        // Mặc định là WHOLE nếu không có checkbox nào được chọn
        promotionTargets = [
            {
                applicableObjectId: -1,
                type: "WHOLE",
            },
        ];
    }

    // Tạo đối tượng request
    return {
        title: data["title"] || "Default Title",
        description: data["description"] || "Default Description",
        percentDiscount: parseFloat(data["percentDiscount"]) || 0.0,
        discountLimit: parseFloat(data["discountLimit"]) || 0.0,
        minValueApplied: parseFloat(data["minValueApplied"]) || 0.0,
        effectiveDate: data["effectiveDate"],
        expirationDate: data["expirationDate"],
        status: "NOT_USE",
        promotionTargets,
    };
}


// Hàm gửi request AJAX
function sendPromotionRequest(responseData, form) {
    $.ajax({
        url: `${contextPath}/owner/promotion/add`,
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(responseData),
        success: function (result) {
            toast({
                title: result.isCodeExisted ? "Thất bại!" : "Thành công!",
                message: result.isCodeExisted ? 'Mã khuyến mãi đã tồn tại.' : 'Khuyến mãi đã được thêm thành công!',
                type: result.isCodeExisted ? "warning" : "success",
                duration: 3000,
            });
            if (!result.isCodeExisted) {
                form.reset();
                form.querySelector('input[name="quantity"]').disabled = false;
            }
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi gọi API:", error);
            toast({
                title: "Lỗi!",
                message: 'Đã xảy ra lỗi trong quá trình thêm khuyến mãi.',
                type: "error",
                duration: 3000,
            });
        }
    });
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
            data.forEach(item => table.row.add([
                `<input type="checkbox" class="row-checkbox" name="selectedItems_${item.id}" value="${item.id}" />`,
                `#${item.id}`,
                `${item.name || item.title}`
            ]));
        } else {
            table.row.add(['', '', emptyMessage]);
        }
        table.draw();
    }
});



















