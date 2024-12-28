// Main function to control the action based on action type
function rowAction(itemId, action) {
    switch (action) {
        case "delete":
            confirmDelete(itemId);
            break;
        case "view":
            executeViewOrUpdateAction(itemId, "view");
            break;
        case "update":
            executeViewOrUpdateAction(itemId, "update");
            break;
        default:
            console.error(`Unsupported action: ${action}`);
    }
}

// Handles delete action with confirmation popup
function confirmDelete(itemId) {
    const popup = document.getElementById("delete-popup");
    popup.style.display = "flex";

    // Confirm delete
    document.getElementById("confirm-delete").onclick = () => {
        popup.style.display = "none";
        deleteActionProcess(itemId);
    };

    // Cancel delete
    document.getElementById("cancel-delete").onclick = () => {
        popup.style.display = "none";
    };
}

// Check before executes delete action using APIs
function deleteActionProcess(itemId) {
    fetch(`${contextPath}/api/owner/product/delete`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id: itemId
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.can === "true") {
                executeDeleteAction(itemId);
            } else {
                showToast("Không thể xóa vì có " + data.amount + " sản phẩm cùng sản phẩm", "Cảnh báo", "warning");
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            showToast("Có lỗi xảy ra khi xóa!", "Lỗi", "error");
        });
}

// Executes delete action using HTTP DELETE
function executeDeleteAction(itemId) {
    fetch(`${contextPath}/owner/product/delete`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id: itemId
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.status === "success") {
                const row = document.getElementById(`row-${itemId}`);
                if (row) {
                    row.remove();
                }
                showToast("Xóa sản phẩm thành công!", "Thành công", "success");
            } else {
                showToast("Xóa sản phẩm thất bại!", "Lỗi", "error");
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            showToast("Có lỗi xảy ra khi xóa sản phẩm!", "Lỗi", "error");
        });
}

// Handles view or update action and redirects based on server response
function executeViewOrUpdateAction(itemId, action) {
    fetch(`${contextPath}/session/owner/product/set-info`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            productId: itemId,
            productAction: action,
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.status === "success") {
                window.location.href = `${contextPath}/owner/product/${action}`;
            } else {
                showToast("Đã xảy ra lỗi khi chuyển hướng!", "Lỗi", "error");
            }
        })
        .catch((error) => console.error("Error:", error));
}

// Displays a popup with an optional auto-hide feature
function showPopup(popupId, autoHideDuration) {
    const popup = document.getElementById(popupId);
    popup.style.display = "flex";

    if (autoHideDuration) {
        const autoHideTimeout = setTimeout(() => {
            fadeOutPopup(popup);
        }, autoHideDuration);

        const closeButton = popup.querySelector(".popup-close-button");
        if (closeButton) {
            closeButton.onclick = () => {
                clearTimeout(autoHideTimeout);
                fadeOutPopup(popup);
            };
        }
    }
}

// Handles popup fade-out animation and hides it
function fadeOutPopup(popupElement, callback) {
    popupElement.classList.add("fade-out");
    setTimeout(() => {
        popupElement.style.display = "none";
        popupElement.classList.remove("fade-out");
        if (callback) callback();
    }, 500);
}
function showToast(message, title = "Thông báo", type = "info", duration = 3000) {
    toast({
        title: title,
        message: message,
        type: type,
        duration: duration
    });
}

// Function to handle table row click events
function initializeTableRowClickHandler() {
    const tableBody = document.querySelector(".item-data-table tbody");

    if (tableBody) {
        tableBody.addEventListener("click", function (event) {
            const row = event.target.closest(".item-row");

            if (row && !event.target.closest("button") && !event.target.closest(".dropdown-menu")) {
                const productId = row.getAttribute("data-id");
                rowAction(productId, "view");
            }
        });
    }
}

// Function to initialize Lottie animations
function initializeLottieAnimations() {
    const animations = [
        { containerId: 'lottie-danger', jsonPath: '/assets/owner/json/danger.json' },
        { containerId: 'lottie-success', jsonPath: '/assets/owner/json/success.json' },
        { containerId: 'lottie-fail', jsonPath: '/assets/owner/json/fail.json' },
    ];

    animations.forEach(animation => {
        lottie.loadAnimation({
            container: document.getElementById(animation.containerId), // Animation container
            renderer: 'svg', // Render type: SVG
            loop: true, // Loop the animation
            autoplay: true, // Start automatically
            path: `${contextPath}${animation.jsonPath}` // Path to the animation JSON file
        });
    });
}

// Wait for the DOM content to be fully loaded
document.addEventListener("DOMContentLoaded", function () {
    initializeTableRowClickHandler();
    initializeLottieAnimations();
});

document.addEventListener("DOMContentLoaded", () => {
    const avatarInput = document.querySelector("#avatar");

    const uploadImages = async (dir, inputSelectors) => {
        try {
            const formData = new FormData();
            const imageLinks = [];

            for (let selector of inputSelectors) {
                const fileInput = document.querySelector(selector);
                const files = fileInput?.files;

                if (files && files.length > 0) {
                    const fileName = `${Date.now()}-${files[0].name}`;
                    formData.append("files", files[0]);
                    formData.append("dir", dir);
                    formData.append("fileName", fileName);
                    formData.append("typeAction", "nonContextPath");

                    const response = await fetch(`${contextPath}/upload`, {
                        method: "POST",
                        body: formData
                    });

                    if (response.ok) {
                        const result = await response.json();
                        imageLinks.push(result.imageLinks[0]);
                    } else {
                        console.error("Upload failed for", fileName, response.statusText);
                        imageLinks.push(null);
                    }
                }
            }

            return imageLinks.filter(link => link !== null);
        } catch (error) {
            console.error("Error uploading files:", error);
            return [];
        }
    };

    const handleCreateProduct = async (event) => {
        event.preventDefault();

        try {
            const errors = validateForm("productCreateForm");
            if (errors.length > 0) {
                showToast(errors[0], "Lỗi", "error");
                return;
            }

            const form = document.getElementById("productCreateForm");
            const formData = new FormData(form);

            const mainImage = await uploadImages("product", ["#imgMain"]);
            const thumbnails = await uploadImages("product", [
                "#thumbUpload01",
                "#thumbUpload02",
                "#thumbUpload03",
                "#thumbUpload04",
                "#thumbUpload05",
                "#thumbUpload06",
            ]);

            if (!mainImage || mainImage.length === 0) {
                showToast("Ảnh chính không được để trống!", "Lỗi", "error");
                return;
            }

            formData.append("mainImage", mainImage[0] || "/images/anonymous/product.jpg");

            if (thumbnails && thumbnails.length > 0) {
                thumbnails.forEach((thumbnail) => {
                    formData.append("thumbnails", thumbnail);
                });
            }

            const authorIds = Array.from(document.querySelectorAll('input[name="authorIds"]:checked'))
                .map(input => input.value);
            const translatorIds = Array.from(document.querySelectorAll('input[name="translatorIds"]:checked'))
                .map(input => input.value);
            const languageCodes = Array.from(document.querySelectorAll('input[name="languageCodes"]:checked'))
                .map(input => input.value);

            authorIds.forEach(authorId => formData.append("authorIds", authorId));
            translatorIds.forEach(translatorId => formData.append("translatorIds", translatorId));
            languageCodes.forEach(languageCode => formData.append("languageCodes", languageCode));

            const description = editor.root.innerHTML.trim();
            formData.append("description", description);

            const productData = {};
            formData.forEach((value, key) => {
                if (key === "thumbnails" || key === "authorIds" || key === "translatorIds" || key === "languageCodes") {
                    if (!productData[key]) {
                        productData[key] = [];
                    }
                    productData[key].push(value);
                } else {
                    productData[key] = value;
                }
            });

            const response = await fetch(`${contextPath}/owner/product/create`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(productData),
            });

            const result = await response.json();

            if (result.status === "success") {
                showToast("Thêm sản phẩm thành công!", "Thành công", "success", 2000);
                setTimeout(() => {
                    rowAction(result.id, "view");
                }, 1000);
            } else {
                showToast("Thêm sản phẩm thất bại!", "Lỗi", "error");
                console.error("Create failed:", result.message || response.statusText);
            }
        } catch (error) {
            showToast("Đã xảy ra lỗi khi thêm sản phẩm!", "Lỗi", "error");
            console.error("Error during the product creation process:", error);
        }
    };

    const handleUpdateProduct = async (event) => {
        event.preventDefault();

        try {
            // Kiểm tra form
            const errors = validateForm("productUpdateForm");
            if (errors.length > 0) {
                showToast(errors[0], "Lỗi", "error");
                return;
            }

            const form = document.getElementById("productUpdateForm");
            const formData = new FormData(form);

            let mainImage = formData.get("mainImage");

            const mainImageUploads = await uploadImages("product", ["#imgMain"]);
            if (mainImageUploads?.[0]) {
                mainImage = mainImageUploads[0];
            }

            if (!mainImage) {
                showToast("Ảnh chính là bắt buộc!", "Lỗi", "error");
                return; 
            }

            formData.set("mainImage", mainImage);

            const thumbnailSelectors = [
                "#thumbUpload01",
                "#thumbUpload02",
                "#thumbUpload03",
                "#thumbUpload04",
                "#thumbUpload05",
                "#thumbUpload06",
            ];

            const existingThumbnails = thumbnailSelectors.map(selector => formData.get(selector) || null);
            const uploadedThumbnails = await uploadImages("product", thumbnailSelectors);

            // Kết hợp giá trị hiện có và giá trị upload
            const finalThumbnails = existingThumbnails.map((thumb, index) => uploadedThumbnails?.[index] || thumb).filter(Boolean);

            // Reset và thêm thumbnails vào formData
            formData.delete("thumbnails");
            finalThumbnails.forEach(thumbnail => formData.append("thumbnails", thumbnail));

            // Xử lý các trường khác
            const authorIds = Array.from(document.querySelectorAll('input[name="authorIds"]:checked'))
                .map(input => input.value);
            const translatorIds = Array.from(document.querySelectorAll('input[name="translatorIds"]:checked'))
                .map(input => input.value);
            const languageCodes = Array.from(document.querySelectorAll('input[name="languageCodes"]:checked'))
                .map(input => input.value);

            authorIds.forEach(authorId => formData.append("authorIds", authorId));
            translatorIds.forEach(translatorId => formData.append("translatorIds", translatorId));
            languageCodes.forEach(languageCode => formData.append("languageCodes", languageCode));

            const description = editor.root.innerHTML.trim();
            formData.append("description", description);

            // Chuyển formData thành productData
            const productData = {};
            formData.forEach((value, key) => {
                if (["thumbnails", "authorIds", "translatorIds", "languageCodes"].includes(key)) {
                    if (!productData[key]) {
                        productData[key] = [];
                    }
                    productData[key].push(value);
                } else {
                    productData[key] = value;
                }
            });

            // Gửi yêu cầu cập nhật sản phẩm
            const response = await fetch(`${contextPath}/owner/product/update`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(productData),
            });

            const result = await response.json();

            if (result.status === "success") {
                showToast("Cập nhật sản phẩm thành công!", "Thành công", "success", 1000);
                setTimeout(() => {
                    rowAction(result.id, "view");
                }, 1000);
            } else {
                showToast("Cập nhật sản phẩm thất bại!", "Lỗi", "error");
                console.error("Create failed:", result.message || response.statusText);
            }
        } catch (error) {
            showToast("Đã xảy ra lỗi khi cập nhật sản phẩm!", "Lỗi", "error");
            console.error("Error during the product update process:", error);
        }
    };

    const createButton = document.getElementById("createButton");
    if (createButton != null) createButton.addEventListener("click", handleCreateProduct);

    const updateButton = document.getElementById("updateButton");
    if (updateButton != null) updateButton.addEventListener("click", handleUpdateProduct);

    const validateForm = (formId) => {
        const errors = [];

        const formData = new FormData(document.getElementById(formId));

        if (!formData.get("title")) {
            errors.push("Tên sản phẩm không được để trống!");
        }
        if (!formData.get("sellingPrice") || !/^\d+$/.test(formData.get("sellingPrice"))) {
            errors.push("Giá bán phải là số nguyên và không được để trống!");
        }
        if (!formData.get("quantity") || !/^\d+$/.test(formData.get("quantity"))) {
            errors.push("Số lượng phải là số nguyên và không được để trống!");
        }
        if (!formData.get("authorIds")) {
            errors.push("Bạn phải chọn ít nhất một tác giả!");
        }
        if (!formData.get("subCategoryId")) {
            errors.push("Bạn phải chọn thể loại!");
        }
        if (!formData.get("conditionCode")) {
            errors.push("Tình trạng sản phẩm không được để trống!");
        }
        if (!formData.get("publisherId")) {
            errors.push("Nhà xuất bản không được để trống!");
        }
        if (!formData.get("formatCode")) {
            errors.push("Định dạng không được để trống!");
        }
        if (!formData.get("publicationDate")) {
            errors.push("Ngày xuất bản không được để trống!");
        }
        if (!formData.get("languageCodes")) {
            errors.push("Ngôn ngữ không được để trống!");
        }
        if (!formData.get("ageRecommendCode")) {
            errors.push("Độ tuổi khuyến nghị không được để trống!");
        }
        if (!formData.get("codeISBN10")) {
            errors.push("Mã ISBN10 không được để trống!");
        }
        if (!formData.get("codeISBN13")) {
            errors.push("Mã ISBN13 không được để trống!");
        }
        if (!formData.get("edition")) {
            errors.push("Tái bản không được để trống!");
        }
        if (!formData.get("hardcover") || !/^\d+$/.test(formData.get("hardcover"))) {
            errors.push("Số trang phải là số nguyên và không được để trống!");
        }
        if (!formData.get("length") || !/^\d+(\.\d+)?$/.test(formData.get("length"))) {
            errors.push("Chiều dài phải là số và không được để trống!");
        }
        if (!formData.get("width") || !/^\d+(\.\d+)?$/.test(formData.get("width"))) {
            errors.push("Chiều rộng phải là số và không được để trống!");
        }
        if (!formData.get("height") || !/^\d+(\.\d+)?$/.test(formData.get("height"))) {
            errors.push("Chiều cao phải là số và không được để trống!");
        }
        if (!formData.get("weight") || !/^\d+(\.\d+)?$/.test(formData.get("weight"))) {
            errors.push("Khối lượng phải là số và không được để trống!");
        }

        const description = editor.root.innerHTML.trim();
        if (!description || description === "<p><br></p>") {
            errors.push("Mô tả sản phẩm không được để trống!");
        }

        return errors;
    };
});

