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
    fetch(`${contextPath}/api/owner/author/delete`, {
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
                showToast("Không thể xóa vì có " + data.amount + " sản phẩm cùng tác giả", "Cảnh báo", "warning");
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            showToast("Có lỗi xảy ra khi xóa!", "Lỗi", "error");
        });
}

// Executes delete action using HTTP DELETE
function executeDeleteAction(itemId) {
    fetch(`${contextPath}/owner/author/delete`, {
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
                showToast("Xóa tác giả thành công!", "Thành công", "success");
            } else {
                showToast("Xóa tác giả thất bại!", "Lỗi", "error");
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            showToast("Có lỗi xảy ra khi xóa tác giả!", "Lỗi", "error");
        });
}

// Handles view or update action and redirects based on server response
function executeViewOrUpdateAction(itemId, action) {
    fetch(`${contextPath}/session/owner/author/set-info`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            authorId: itemId,
            authorAction: action,
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.status === "success") {
                window.location.href = `${contextPath}/owner/author/${action}`;
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
                const authorId = row.getAttribute("data-id");
                rowAction(authorId, "view");
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

    const uploadImage = async (dir, inputSelector) => {
        try {
            const fileInput = document.querySelector(inputSelector);
            const files = fileInput?.files;
            if (!files || files.length === 0) return null;

            const formData = new FormData();
            formData.append("files", files[0]);
            formData.append("dir", dir);
            formData.append("fileName", `${Date.now()}-${files[0].name}`);
            formData.append("typeAction", "nonContextPath");

            const response = await fetch(`${contextPath}/upload`, {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                const result = await response.json();
                return result.imageLinks[0];
            } else {
                console.error("Upload failed:", response.statusText);
                return null;
            }
        } catch (error) {
            console.error("Error uploading file:", error);
            return null;
        }
    };

    const handleCreateAuthor = async (event) => {
        event.preventDefault();

        try {
            const form = document.getElementById("authorCreateForm");
            const formData = new FormData(form);

            const introduction = editor.root.innerHTML.trim();
            if (!formData.get("name") || !introduction || introduction === "<p><br></p>") {
                showToast("Tên tác giả và giới thiệu không được để trống!", "Lỗi", "error");
                return;
            }

            let avatar = await uploadImage("author", "#avatar");
            avatar = avatar || "/images/anonymous/author.jpg";

            formData.append("avatar", avatar);
            formData.append("introduction", introduction);
            const authorData = Object.fromEntries(formData.entries());

            const response = await fetch(`${contextPath}/owner/author/create`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(authorData)
            });

            const result = await response.json();

            if (result.status === "success") {
                showToast("Thêm tác giả thành công!", "Thành công", "success", 2000);
                setTimeout(() => {
                    rowAction(result.id, 'view');
                }, 1000);
            } else {
                showToast("Thêm tác giả thất bại!", "Lỗi", "error");
                console.error("Create failed:", result.message || response.statusText);
            }
        } catch (error) {
            showToast("Đã xảy ra lỗi khi thêm tác giả!", "Lỗi", "error");
            console.error("Error during the author process:", error);
        }
    };

    const handleUpdateAuthor = async (event) => {
        event.preventDefault();

        try {
            const formData = new FormData(document.getElementById("authorUpdateForm"));
            const name = formData.get("name");
            const introduction = editor.root.innerHTML.trim();
            let avatar = formData.get("originAvatar");

            if (!name || !introduction || introduction === "<p><br></p>") {
                showToast("Tên tác giả và giới thiệu không được để trống!", "Lỗi", "error");
                return;
            }

            const avatarInput = document.getElementById("avatar");
            if (avatarInput.files && avatarInput.files.length > 0) {
                avatar = await uploadImage("author", "#avatar");
                if (!avatar) {
                    showToast("Không thể upload hình ảnh, vui lòng thử lại!", "Lỗi", "error");
                    return;
                }
            }

            const authorData = {
                id: formData.get("id"),
                name: name,
                avatar: avatar,
                introduction: introduction,
                joinAt: formData.get("joinAt")
            };

            const response = await fetch(`${contextPath}/owner/author/update`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(authorData)
            });

            const result = await response.json();

            if (result.status === "success") {
                showToast("Cập nhật tác giả thành công!", "Thành công", "success");
                setTimeout(() => {
                    rowAction(authorData.id, "view");
                }, 1000);
            } else {
                showToast("Cập nhật tác giả thất bại!", "Lỗi", "error");
                console.error("Update failed:", result.message || response.statusText);
            }
        } catch (error) {
            console.error("Error during the author update process:", error);
            showToast("Đã xảy ra lỗi khi cập nhật tác giả!", "Lỗi", "error");
        }
    };

    const createButton = document.getElementById("createButton");
    if (createButton != null) createButton.addEventListener("click", handleCreateAuthor);

    const updateButton = document.getElementById("updateButton");
    if (updateButton != null) updateButton.addEventListener("click", handleUpdateAuthor);
});

