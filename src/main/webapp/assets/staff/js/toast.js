// Toast function
function toast({title = "", message = "", type = "success", duration = 3000}) {
    const main = document.getElementById("toast");
    console.log(`Title: ${title}, Message: ${message}, Type: ${type}, Duration: ${duration}`);
    if (main) {
        const toast = document.createElement("div");

        // Auto remove toast
        const autoRemoveId = setTimeout(function () {
            main.removeChild(toast);
        }, duration + 1000);

        // Remove toast when clicked
        toast.onclick = function (e) {
            if (e.target.closest(".toast__close")) {
                main.removeChild(toast);
                clearTimeout(autoRemoveId);
            }
        };

        const icons = {
            success: "ri-checkbox-circle-line",
            info: "ri-information-line",
            warning: "ri-error-warning-line",
            error: "ri-error-warning-fill",
        };
        const icon = icons[type];
        const delay = (duration / 1000).toFixed(2);

        toast.classList.add("toast", `toast--${type}`);
        toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;

        toast.innerHTML = `
                    <div class="toast__icon">
                        <i class="${icon}"></i>
                    </div>
                    <div class="toast__body">
                        <h3 class="toast__title">${title}</h3>
                        <p class="toast__msg">${message}</p>
                    </div>
                    <div class="toast__close">
                        <i class="ri-close-circle-fill"></i>
                    </div>
                `;
        main.appendChild(toast);
        console.log(main);
    }
}