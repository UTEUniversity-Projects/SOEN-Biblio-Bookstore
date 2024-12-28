export function toast({
						  title = "",
						  message = "",
						  type = "info",
						  duration = 3000,
					  }) {
	const main = document.getElementById("toast");
	if (main) {
		const toast = document.createElement("div");

		const autoRemoveId = setTimeout(() => {
			main.removeChild(toast);
		}, duration + 1000);

		toast.onclick = (e) => {
			if (e.target.closest(".toast__close")) {
				clearTimeout(autoRemoveId);
				main.removeChild(toast);
			}
		};

		const delay = duration / 1000;
		const icons = {
			success: `<i class="fa-solid fa-circle-check"></i>`,
			info: `<i class="fa-solid fa-circle-info"></i>`,
			warning: `<i class="fa-solid fa-triangle-exclamation"></i>`,
			error: `<i class="fa-solid fa-circle-exclamation"></i>`,
		};

		toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;

		toast.classList.add("toast", `toast--${type}`);
		toast.innerHTML = `<div class="toast__icon">
               ${icons[type]}
            </div>
            <div class="toast__body">
               <h3 class="toast__title">${title}</h3>
               <p class="toast__message">
                  ${message}
               </p>
            </div>
            <div class="toast__close">
               <i class="fa-solid fa-xmark"></i>
            </div>`;
		main.appendChild(toast);
	}
}