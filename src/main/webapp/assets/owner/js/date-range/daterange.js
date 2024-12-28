$(function () {
    var start = moment().subtract(29, "days");
    var end = moment();

    function animateNumber(element, startValue, endValue, duration, isCurrency = false) {
        $({ count: startValue }).animate(
            { count: endValue },
            {
                duration: duration,
                easing: "swing",
                step: function (now) {
                    const formattedValue = isCurrency
                        ? Math.floor(now).toLocaleString("vi-VN") + " vnđ" // Định dạng tiền tệ
                        : Math.floor(now); // Hiển thị số bình thường
                    $(element).text(formattedValue);
                },
                complete: function () {
                    const finalValue = isCurrency
                        ? endValue.toLocaleString("vi-VN") + " vnđ"
                        : endValue;
                    $(element).text(finalValue); // Đảm bảo giá trị cuối cùng chính xác
                },
            }
        );
    }



    function updateCounts(start, end) {
        // AJAX call to load data
        $.ajax({
            url: "/owner/ecommerce/summary-report",
            type: "GET",
            data: {
                startTime: start.format("YYYY-MM-DDTHH:mm:ss"),
                endTime: end.format("YYYY-MM-DDTHH:mm:ss"),
            },
            success: function (response) {
                // Animate numbers for each metric
                animateNumber("#customer-count", parseInt($("#customer-count").text()), response.customerCount || 0, 1000, false); // Số bình thường
                animateNumber("#order-count", parseInt($("#order-count").text()), response.orderCount || 0, 1000, false); // Số bình thường
                animateNumber("#revenue-count", parseFloat($("#revenue-count").text().replace(/\D/g, "")), response.venueOrder || 0, 1000, true); // Định dạng vnđ
                animateNumber("#expense-count", parseFloat($("#expense-count").text().replace(/\D/g, "")), response.expense || 0, 1000, true); // Định dạng vnđ
            },
            error: function (xhr, status, error) {
                console.error("Error loading data:", error);
            },
        });
    }




    function cb(start, end, element) {
        $(element).find("span").html(start.format("MMM D, YYYY") + " - " + end.format("MMM D, YYYY"));
        updateCounts(start, end); // Trigger data update
    }

    $("#date-summary-report").daterangepicker(
        {
            startDate: start,
            endDate: end,
            ranges: {
                "Hôm nay": [moment(), moment()],
                "Hôm qua": [moment().subtract(1, "days"), moment().subtract(1, "days")],
                "7 ngày trước": [moment().subtract(6, "days"), moment()],
                "30 ngày trước": [moment().subtract(29, "days"), moment()],
            },
        },
        function (start, end) {
            cb(start, end, $("#date-summary-report"));
        }
    );

    // Load data immediately when the page loads
    cb(start, end, $("#date-summary-report"));
    updateCounts(start, end); // Load counts immediately
});







