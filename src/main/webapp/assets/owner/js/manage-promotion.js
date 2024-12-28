document.addEventListener("DOMContentLoaded", function () {
    const tables = document.querySelectorAll(".promotion-data-table");

    tables.forEach((table) => {
        const tableBody = table.querySelector("tbody");

        // Đảm bảo `tableBody` không phải là null
        if (tableBody) {
            tableBody.addEventListener("click", function (event) {
                const row = event.target.closest(".promotion-row");

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
});
