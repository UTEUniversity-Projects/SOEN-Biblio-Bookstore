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
  fetch(`${contextPath}/api/owner/category/delete`, {
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
          showToast("Không thể xóa vì có " + data.amount + " sản phẩm cùng danh mục", "Cảnh báo", "warning");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        showToast("Có lỗi xảy ra khi xóa danh mục!", "Lỗi", "error");
      });
}

// Executes delete action using HTTP DELETE
function executeDeleteAction(itemId) {
  fetch(`${contextPath}/owner/category/delete`, {
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
          showToast("Xóa danh mục thành công!", "Thành công", "success");
        } else {
          showToast("Xóa danh mục thất bại!", "Lỗi", "error");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        showToast("Có lỗi xảy ra khi xóa danh mục!", "Lỗi", "error");
      });
}

// Handles view or update action and redirects based on server response
function executeViewOrUpdateAction(itemId, action) {
  fetch(`${contextPath}/session/owner/category/set-info`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      categoryId: itemId,
      categoryAction: action,
    }),
  })
      .then((response) => response.json())
      .then((data) => {
        if (data.status === "success") {
          window.location.href = `${contextPath}/owner/category/${action}`;
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
        const categoryId = row.getAttribute("data-id");
        rowAction(categoryId, "view");
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

  const handleCreateCategory = async (event) => {
    event.preventDefault();

    try {
      const formData = new FormData(document.getElementById("categoryCreateForm"));

      const fullScript = editor.root.innerHTML.trim();
      if (!formData.get("name")
          || !formData.get("shortScript")
          || !fullScript || fullScript === "<p><br></p>") {
        showToast("Vui lòng điền đầy đủ thông tin!", "Lỗi", "error");
        return;
      }

      const categoryData = {
        id: formData.get("id"),
        name: formData.get("name"),
        shortScript: formData.get("shortScript"),
        fullScript: fullScript
      };

      const response = await fetch(`${contextPath}/owner/category/create`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(categoryData)
      });

      const result = await response.json();

      if (result.status === "success") {
        showToast("Thêm danh mục thành công!", "Thành công", "success", 2000);
        setTimeout(() => {
          rowAction(result.id, 'view');
        }, 1000);
      } else {
        showToast("Thêm danh mục thất bại!", "Lỗi", "error");
        console.error("Create failed:", result.message || response.statusText);
      }
    } catch (error) {
      showToast("Đã xảy ra lỗi khi thêm danh mục!", "Lỗi", "error");
      console.error("Error during the category process:", error);
    }
  };

  const handleUpdateCategory = async (event) => {
    event.preventDefault();

    try {
      const formData = new FormData(document.getElementById("categoryUpdateForm"));

      const fullScript = editor.root.innerHTML.trim();
      if (!formData.get("name")
          || !formData.get("shortScript")
          || !fullScript || fullScript === "<p><br></p>") {
        showToast("Vui lòng điền đầy đủ thông tin!", "Lỗi", "error");
        return;
      }

      const categoryData = {
        id: formData.get("id"),
        name: formData.get("name"),
        shortScript: formData.get("shortScript"),
        fullScript: fullScript
      };

      const response = await fetch(`${contextPath}/owner/category/update`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(categoryData)
      });

      const result = await response.json();

      if (result.status === "success") {
        showToast("Cập nhật danh mục thành công!", "Thành công", "success");
        setTimeout(() => {
          rowAction(categoryData.id, "view");
        }, 1000);
      } else {
        showToast("Cập nhật danh mục thất bại!", "Lỗi", "error");
        console.error("Update failed:", result.message || response.statusText);
      }
    } catch (error) {
      console.error("Error during the category update process:", error);
      showToast("Đã xảy ra lỗi khi cập nhật danh mục!", "Lỗi", "error");
    }
  };

  const createButton = document.getElementById("createButton");
  if (createButton != null) createButton.addEventListener("click", handleCreateCategory);

  const updateButton = document.getElementById("updateButton");
  if (updateButton != null) updateButton.addEventListener("click", handleUpdateCategory);
});

