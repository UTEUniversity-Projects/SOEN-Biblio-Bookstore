$(document).ready(function () {

    // revenue
    var startOfRevenue = moment().subtract(29, "days");
    var endOfRevenue = moment();
    var chartTypeRevenue = "area"; // Biến lưu loại biểu đồ mặc định
    var revenueChart;

    // new customer
    var startOfNewCustomer = moment().subtract(29, "days");
    var endOfNewCustomer = moment();
    var chartTypeNewCustomer = "area";
    var newCustomerChart;

    // top product sold
    var startOfTopProductSold = moment().subtract(29, "days");
    var endOfTopProductSold = moment();
    var topProductSoldChart;

    // RateRepeatPurchase
    var startOfRepeatPurchase = moment().subtract(29, "days");
    var endOfRepeatPurchase = moment();
    var topRepeatPurchaseChart;
    var topRepeatPurchaseDonutChart;

    //Reason return order
    var startOfReturnOrder = moment().subtract(29, "days");
    var endOfReturnOrder = moment();
    var reasonReturnBarChart;

    function animateNumber(element, startValue, endValue, duration, isCurrency = false) {
        $({count: startValue}).animate(
            {count: endValue},
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

    // Biến lưu instance biểu đồ

    // Hàm vẽ biểu đồ

    // function drawRevenueChart(chartId, categories, revenues, type) {
    //     if (revenueChart) {
    //         revenueChart.destroy();
    //     }
    //     const options = {
    //         series: [
    //             {
    //                 name: "Doanh thu",
    //                 data: revenues,
    //             },
    //         ],
    //         chart: {
    //             height: 500,
    //             type: type, // Sử dụng loại biểu đồ được truyền vào
    //             toolbar: {show: false},
    //         },
    //         stroke: {width: [2], curve: "smooth"}, // Chỉ áp dụng cho line chart
    //         colors: ["#8e44ad"],
    //         xaxis: {
    //             categories: categories,
    //             axisTicks: {show: false},
    //             axisBorder: {show: false},
    //         },
    //         yaxis: {
    //             labels: {
    //                 formatter: value =>
    //                     value.toLocaleString("vi-VN") + " vnđ",
    //             },
    //         },
    //         dataLabels: {
    //             enabled: false, // Không hiển thị số trong cột nếu là bar chart
    //         },
    //     };
    //
    //     revenueChart = new ApexCharts(document.querySelector(`#${chartId}`), options);
    //     revenueChart.render();
    // }
    function drawRevenueChart(chartId, categories, revenues, type) {
        if (revenueChart) {
            revenueChart.destroy();
        }
        const options = {
            series: [
                {
                    name: "Doanh thu",
                    data: revenues,
                },
            ],
            chart: {
                height: 500,
                type: type, // Thay đổi từ 'line' thành 'area'
                toolbar: {show: false},
            },
            stroke: {
                width: [2],
                curve: "smooth", // Chỉ áp dụng cho biểu đồ area
            },
            colors: ["#8e44ad"],
            fill: {
                type: 'gradient', // Sử dụng gradient
                gradient: {
                    shade: 'light', // Đặt gradient sáng
                    type: 'vertical', // Gradient theo chiều dọc
                    gradientToColors: ['#8e44ad'], // Màu chuyển tiếp
                    stops: [0, 90, 100], // Điều chỉnh độ dốc của gradient
                },
            },
            xaxis: {
                categories: categories,
                axisTicks: {show: false},
                axisBorder: {show: false},
            },
            yaxis: {
                labels: {
                    formatter: value =>
                        value.toLocaleString("vi-VN") + " vnđ",
                },
            },
            dataLabels: {
                enabled: false, // Không hiển thị số trong cột nếu là bar chart
            },
        };

        revenueChart = new ApexCharts(document.querySelector(`#${chartId}`), options);
        revenueChart.render();
    }

    function drawNewCustomerChart(chartId, categories, count, type = "line") {
        if (newCustomerChart) {
            newCustomerChart.destroy();
        }
        const options = {
            series: [
                {
                    name: "Khách hàng",
                    data: count,
                },
            ],
            chart: {
                height: 500,
                type: type, // Sử dụng loại biểu đồ được truyền vào
                toolbar: {show: false},
            },
            stroke: {width: [2], curve: "smooth"}, // Chỉ áp dụng cho area chart
            colors: ["#f9a12c"],
            fill: {
                type: 'gradient', // Sử dụng gradient
                gradient: {
                    shade: 'light', // Đặt gradient sáng
                    type: 'vertical', // Gradient theo chiều dọc
                    gradientToColors: ['#f9a12c'], // Màu chuyển tiếp
                    stops: [0, 90, 100], // Điều chỉnh độ dốc của gradient
                },
            },
            xaxis: {
                categories: categories,
                axisTicks: {show: false},
                axisBorder: {show: false},
            },
            yaxis: {
                labels: {
                    formatter: value => value.toLocaleString("vi-VN") + " khách hàng",
                },
            },
            dataLabels: {
                enabled: false, // Không hiển thị số trong cột nếu là bar chart
            },
        };

        newCustomerChart = new ApexCharts(document.querySelector(`#${chartId}`), options);
        newCustomerChart.render();
    }

    function drawTopProductSoldChart(chartId, categories, seriesData) {
        if (topProductSoldChart) {
            topProductSoldChart.destroy(); // Hủy biểu đồ cũ nếu tồn tại
        }

        const options = {
            series: seriesData, // Dữ liệu dạng series (danh sách các cột)
            chart: {
                type: 'bar', // Loại biểu đồ cột
                height: 500, // Chiều cao biểu đồ
                stacked: true, // Hiển thị cột dạng stacked
            },
            plotOptions: {
                bar: {
                    horizontal: false, // Cột dọc
                    columnWidth: '30%', // Độ rộng của cột
                    endingShape: 'rounded', // Đầu cột bo tròn
                },
            },
            dataLabels: {
                enabled: false, // Ẩn nhãn dữ liệu trên cột
            },
            colors: ['#5f6af5', '#ff4f7f', '#1ecab8', '#f9a12c', '#0fbcf9', '#8e44ad', '#2ecc71', '#e74c3c', '#34495e', '#f39c12'], // Màu sắc
            xaxis: {
                categories: categories, // Tên các danh mục
                labels: {
                    formatter: function (val) {
                        return val.length > 20 ? val.slice(0, 20) + '...' : val; // Rút gọn tên nếu dài
                    },
                },
            },
            yaxis: {
                labels: {
                    formatter: function (val) {
                        return val + ' sản phẩm'; // Đơn vị trục Y
                    },
                },
            },
            fill: {
                opacity: 1, // Độ mờ của cột
            },
            tooltip: {
                y: {
                    formatter: function (val) {
                        return val + ' sản phẩm'; // Hiển thị tooltip chi tiết
                    },
                },
            },
            legend: {
                show: false, // Ẩn chú thích
            },
        };

        // Khởi tạo biểu đồ mới
        topProductSoldChart = new ApexCharts(document.querySelector(chartId), options);
        topProductSoldChart.render(); // Vẽ biểu đồ
    }

    function drawRateRepeatPurchaseChart(chartId, categories, seriesData) {
        // Hủy biểu đồ cũ nếu tồn tại
        if (topRepeatPurchaseChart) {
            topRepeatPurchaseChart.destroy();
        }

        var options = {
            series: seriesData,
            chart: {
                type: 'bar',
                height: 400,
                stacked: true
            },
            plotOptions: {
                bar: {
                    horizontal: false,
                    columnWidth: '20%',
                    endingShape: 'rounded'
                },
            },
            dataLabels: {
                enabled: false,
            },
            stroke: {
                show: true,
                width: 2,
                colors: ['transparent']
            },
            colors: ['#5f6af5', '#ff4f7f'], // Màu sắc
            xaxis: {
                categories: categories,
                labels: {
                    formatter: function (val) {
                        return val + " khách hàng";
                    }
                }
            },
            yaxis: {
                labels: {
                    formatter: function (val) {
                        return val; // Đơn vị cho trục Y
                    }
                }
            },
            fill: {
                opacity: 1
            },
            tooltip: {
                y: {
                    formatter: function (val) {
                        return val + " khách hàng"; // Hiển thị chi tiết trong tooltip
                    }
                }
            },
            legend: {
                show: false // Tắt chú thích
            }
        };

        topRepeatPurchaseChart = new ApexCharts(document.querySelector(chartId), options);
        topRepeatPurchaseChart.render();
    }

    function drawRateRepeatPurchaseDonutChart(chartId, singleOrderRate, multipleOrderRate) {
        // Hủy biểu đồ donut cũ nếu tồn tại
        if (topRepeatPurchaseDonutChart) {
            topRepeatPurchaseDonutChart.destroy();
        }

        var options = {
            series: [singleOrderRate, multipleOrderRate],
            chart: {
                height: 280,
                type: 'donut',
            },
            plotOptions: {
                pie: {
                    donut: {
                        size: '40%',
                    }
                }
            },
            colors: ['#5f6af5', '#ff4f7f'],
            labels: ['Khách hàng mua nhiều đơn', 'Khách hàng mua một đơn'],
            legend: {
                show: true,
                position: 'bottom',
                horizontalAlign: 'center',
                fontSize: '14px',
                fontFamily: 'Helvetica, Arial',
                markers: {
                    width: 30,
                    height: 10,
                    radius: 6
                }
            },
            responsive: [{
                breakpoint: 480,
                options: {
                    legend: {
                        show: false
                    }
                }
            }]
        };

        topRepeatPurchaseDonutChart = new ApexCharts(document.querySelector(chartId), options);
        topRepeatPurchaseDonutChart.render();
    }

    function drawReasonReturnOrderChart(chartId, categories, seriesData) {
        if (reasonReturnBarChart) {
            reasonReturnBarChart.destroy();
        }

        const options = {
            series: seriesData,
            chart: {
                type: 'bar',
                height: 500,
                stacked: false,
            },
            plotOptions: {
                bar: {
                    horizontal: false,
                    columnWidth: '30%',
                    endingShape: 'rounded',
                },
            },
            dataLabels: {
                enabled: false,
            },
            colors: ['#5f6af5', '#ff4f7f', '#1ecab8', '#f9a12c'],
            xaxis: {
                categories: categories,
                labels: {
                    formatter: function (val) {
                        return val.length > 20 ? val.slice(0, 20) + '...' : val;
                    },
                },
            },
            yaxis: {
                labels: {
                    formatter: function (val) {
                        return val;
                    },
                },
            },
            fill: {
                opacity: 1,
            },
            tooltip: {
                y: {
                    formatter: function (val) {
                        return val;
                    },
                },
            },
            legend: {
                show: false,
            },
        };

        reasonReturnBarChart = new ApexCharts(document.querySelector(chartId), options);
        reasonReturnBarChart.render();
    }


    // Hàm AJAX lấy dữ liệu
    function fetchData(url, start, end, callback) {
        $.ajax({
            url: url,
            type: "GET",
            data: {
                startTime: start.format("YYYY-MM-DDTHH:mm:ss"),
                endTime: end.format("YYYY-MM-DDTHH:mm:ss"),
            },
            success: function (response) {
                callback(null, response);
            },
            error: function (xhr, status, error) {
                console.error("Error fetching revenue data:", error);
                callback(error, null);
            },
        });
    }

    function fetchDataNoTime(url, callback) {
        $.ajax({
            url: url,
            type: "GET",
            success: function (response) {
                callback(null, response);
            },
            error: function (xhr, status, error) {
                console.error("Error fetching data:", error);
                callback(error, null);
            },
        });
    }


    // Hàm cập nhật dữ liệu biểu đồ
    function updateRevenueChart(start, end) {
        $("#date-list-revenue span").html(
            start.format("YYYY-MM-DD") + " - " + end.format("YYYY-MM-DD")
        );

        const totalDays = end.diff(start, "days") + 1;

        fetchData(`${contextPath}/owner/ecommerce/list-revenue`, start, end, function (error, response) {
            if (error) {
                console.error("Error fetching data:", error);
                return;
            }

            const revenueList = response.revenueList;
            let processedData = [];

            if (totalDays <= 15) {
                processedData = revenueList.map(item => ({
                    date: moment(item.date).format("DD-MM"),
                    revenue: item.revenue,
                }));
            } else {
                const groupSize = Math.ceil(totalDays / 15);
                let currentIndex = 0;

                while (currentIndex < totalDays) {
                    const group = revenueList.slice(currentIndex, currentIndex + groupSize);
                    const totalRevenue = group.reduce((sum, item) => sum + item.revenue, 0);
                    processedData.push({
                        date: `${moment(group[0].date).format("DD-MM")} - ${
                            moment(group[group.length - 1].date).format("DD-MM")
                        }`,
                        revenue: Math.round(totalRevenue),
                    });
                    currentIndex += groupSize;
                }
            }

            const categories = processedData.map(item => item.date);
            const revenues = processedData.map(item => item.revenue);
            let totalRevenue = 0;
            processedData.forEach(item => {
                totalRevenue += item.revenue;
            });
            animateNumber("#chartSumRevenue", parseFloat($("#chartSumRevenue").text().replace(/\D/g, "")), totalRevenue || 0, 1000, true); // Định dạng vnđ

            // Vẽ biểu đồ với loại hiện tại (line hoặc bar)
            if (chartTypeRevenue === "area") {
                drawRevenueChart("revenueLineChart", categories, revenues, chartTypeRevenue)
            } else (
                drawRevenueChart("revenueBarChart", categories, revenues, chartTypeRevenue)
            )
        });
    }

    function updateNewCustomerChart(start, end) {
        $("#date-list-new-customer span").html(
            start.format("YYYY-MM-DD") + " - " + end.format("YYYY-MM-DD")
        );
        const totalDays = end.diff(start, "days") + 1;

        fetchData(`${contextPath}/owner/ecommerce/list-count-new-customer`, start, end, function (error, response) {
            if (error) {
                console.error("Error fetching data:", error);
                return;
            }

            const countList = response.countCustomerJoinList;
            let processedData = [];

            if (totalDays <= 15) {
                processedData = countList.map(item => ({
                    joinAt: moment(item.joinAt).format("DD-MM"),
                    count: item.count,
                }));
            } else {
                const groupSize = Math.ceil(totalDays / 15);
                let currentIndex = 0;

                while (currentIndex < totalDays) {
                    const group = countList.slice(currentIndex, currentIndex + groupSize);
                    const totalList = group.reduce((sum, item) => sum + item.count, 0);
                    processedData.push({
                        joinAt: `${moment(group[0].joinAt).format("DD-MM")} - ${
                            moment(group[group.length - 1].joinAt).format("DD-MM")
                        }`,
                        count: Math.round(totalList),
                    });
                    currentIndex += groupSize;
                }
            }

            const categories = processedData.map(item => item.joinAt);
            const count = processedData.map(item => item.count);
            const totalCount = count.reduce((sum, value) => sum + value, 0);
            console.log(totalCount);
            animateNumber("#chartCountNewCustomer", parseInt($("#chartCountNewCustomer").text()), totalCount || 0, 1000, false);

            if (chartTypeNewCustomer === "line") {
                drawNewCustomerChart("newCustomerLineChart", categories, count, chartTypeNewCustomer)
            } else (
                drawNewCustomerChart("newCustomerBarChart", categories, count, chartTypeNewCustomer)
            )
        });

    }

    function updateTopProductSoldChart(start, end) {
        $("#date-top-product span").html(
            start.format("YYYY-MM-DD") + " - " + end.format("YYYY-MM-DD")
        );

        fetchData(`${contextPath}/owner/ecommerce/count-book-sold-at-time`, start, end, function (error, response) {
            if (error) {
                console.error("Error fetching data:", error);
                return;
            }

            const topProductSold = response.countBookSoldList;
            let processedData = [];

            if (topProductSold.size <= 10) {
                processedData = topProductSold.map(item => ({
                    title: item.title,
                    count: item.count,
                }));
            } else {
                // Lấy tối đa 10 sản phẩm
                const limitedTopProductSold = topProductSold.slice(0, 10);
                processedData = limitedTopProductSold.map(item => ({
                    title: item.title,
                    count: item.count,
                }));
            }

            const categories = processedData.map(item => item.title);

            const seriesData = processedData.map((item, index) => {
                const data = Array(processedData.length).fill(0); // Mảng giá trị toàn 0
                data[index] = item.count; // Đặt giá trị count tại đúng vị trí
                return {
                    name: 'lượt bán',
                    data: data,
                };
            });

            const totalCount = topProductSold.reduce((sum, item) => sum + item.count, 0);
            const bestSeller = topProductSold.length > 0 ? topProductSold[0].title : null;
            document.getElementById("bestSeller").innerText = bestSeller;
            animateNumber("#countBookSold", parseInt($("#countBookSold").text()), totalCount || 0, 1000, false);
            drawTopProductSoldChart("#topProductSoldBarChart", categories, seriesData);
        });

    }

    function updateProductSoldTable() {
        fetchDataNoTime(`${contextPath}/owner/ecommerce/count-book-sold-all-time`, function (error, response) {
            if (error) {
                console.error("Error fetching data:", error);
                return;
            }

            // Lấy dữ liệu từ API
            const listBookSoldAllTime = response.countBookSoldAllTime;

            // Lấy đối tượng DataTable
            const responsiveDataTable = $("#list_product_sold_in_stock_statistical").DataTable();

            // Xóa dữ liệu cũ
            responsiveDataTable.clear();

            // Duyệt qua dữ liệu và thêm dòng mới
            listBookSoldAllTime.forEach(book => {
                const rowNode = responsiveDataTable.row.add([
                    `<img class="cat-thumb" src="${book.img}" alt="Book Image">
                 <span class="name">${book.title}</span>`,
                    `<span class="cat">
                <a href="#">${book.category}</a>
                 </span>`,
                    book.countSold,
                    book.countInStock
                ]).node(); // Lấy node của dòng vừa thêm

                // rowNode.setAttribute("data-href", `/owner/product-details?id=${book.id}`); // Gán thuộc tính data-href
                rowNode.addEventListener("click", function (event) {
                    if (
                        !event.target.closest("button") &&
                        !event.target.closest(".dropdown-menu")
                    ) {
                        // const href = this.getAttribute("data-href");
                        const href = `/owner/product-details?id=${book.id}`
                        window.location.href = href;
                    }
                });
            });

            // Cập nhật DataTable
            responsiveDataTable.draw();
        });
    }

    function updateRateRepeatPurchaseChart(start, end) {
        $("#date-rate-repeat-purchase span").html(
            start.format("YYYY-MM-DD") + " - " + end.format("YYYY-MM-DD")
        );

        // Gọi API để lấy dữ liệu
        fetchData(`${contextPath}/owner/ecommerce/count-order-of-customer-at-time`, start, end, function (error, response) {
            if (error) {
                console.error("Error fetching data:", error);
                return;
            }

            const countOrderOfCustomerList = response.countOrderOfCustomerList;
            let singleOrderCount = 0; // Số khách hàng mua 1 đơn
            let multipleOrderCount = 0; // Số khách hàng mua nhiều hơn 1 đơn
            let totalOrders = 0; // Tổng số đơn hàng

            // Tính toán từ dữ liệu API
            countOrderOfCustomerList.forEach(item => {
                totalOrders += item.count; // Tổng số đơn hàng
                if (item.count === 1) {
                    singleOrderCount++;
                } else if (item.count > 1) {
                    multipleOrderCount++;
                }
            });

            const totalCustomers = singleOrderCount + multipleOrderCount; // Tổng số khách hàng

            animateNumber("#countCustomerPurchaseAtTime", parseInt($("#countCustomerPurchaseAtTime").text()), totalCustomers || 0, 1000, false);
            animateNumber("#countOrderAtTime", parseInt($("#countOrderAtTime").text()), totalOrders || 0, 1000, false);
            animateNumber("#countCustomerPurchaseOneTimeAtTime", parseInt($("#countCustomerPurchaseOneTimeAtTime").text()), singleOrderCount || 0, 1000, false);
            animateNumber("#countCustomerPurchaseThanOneTimeAtTime", parseInt($("#countCustomerPurchaseThanOneTimeAtTime").text()), multipleOrderCount || 0, 1000, false);

            // Chuẩn bị dữ liệu cho biểu đồ
            const categories = [
                'Khách hàng mua nhiều đơn hàng',
                'Khách hàng mua một đơn hàng'
            ];
            const seriesData = [
                {name: 'Khách hàng mua 1 đơn', data: [0, singleOrderCount]},
                {name: 'Khách hàng mua từ 2 đơn', data: [multipleOrderCount, 0]}
            ];

            // Vẽ biểu đồ cột
            drawRateRepeatPurchaseChart("#countCustomerPurchaseBarChart", categories, seriesData);
            // Vẽ biểu đồ donut
            drawRateRepeatPurchaseDonutChart(
                "#donutChartRepeatPurchaseRate",
                (singleOrderCount / totalCustomers) * 100,
                (multipleOrderCount / totalCustomers) * 100
            );
        });
    }

    function updateReasonReturnOrderBarChart(start, end) {
        $("#date-return-order span").html(
            start.format("YYYY-MM-DD") + " - " + end.format("YYYY-MM-DD")
        );
        fetchData(`${contextPath}/api/owner/ecommerce/get-list-return-order-at-time`, start, end, function (error, response) {
            if (error) {
                console.error("Error fetching data:", error);
                return;
            }

            const orderReturnAtTimeList = response.orderReturnAtTimeList;
            const reasonCounts = response.reasonCounts;

            let successOrderCount = 0;
            let returnOrderCount = 0;
            let total = 0;

            // Tính toán từ dữ liệu API
            orderReturnAtTimeList.forEach(item => {
                total++;
                if (item.reasonReturn !== null) {
                    returnOrderCount++;
                }
            });
            successOrderCount = total - returnOrderCount;

            const rateReturn = (returnOrderCount / total) * 100;
            const roundedRateReturn = isNaN(rateReturn) || rateReturn == null ? 0 : Math.round(rateReturn * 100) / 100;



            animateNumber("#countOrderSuccess", parseInt($("#countOrderSuccess").text()), successOrderCount || 0, 1000, false);
            animateNumber("#countOrderReturn", parseInt($("#countOrderReturn").text()), returnOrderCount || 0, 1000, false);
            animateNumber("#rateOrderReturn", parseInt($("#rateOrderReturn").text()), roundedRateReturn + " %" || 0 + " %", 1000, false);

            const categories = ['Hư hỏng', 'Không giống mô tả', 'Hàng giả', 'Không có nhu cầu nữa'];
            const seriesData = [{
                name: 'Số đơn hàng trả lại',
                data: [
                    reasonCounts.DAMAGED || 0,
                    reasonCounts.NOT_AS_DESCRIBED || 0,
                    reasonCounts.FAKE || 0,
                    reasonCounts.NO_NEEDED || 0
                ]
            }];

            // Vẽ biểu đồ
            drawReasonReturnOrderChart("#reasonReturnOrderBarChart", categories, seriesData);
        });
    }


    // Xử lý sự kiện thay đổi loại biểu đồ
    $(document).on("click", "#barChartRevenueIcon", function () {
        chartTypeRevenue = "bar";
        const dateRangePicker = $("#date-list-revenue").data("daterangepicker");
        const selectedStart = dateRangePicker.startDate; // Lấy thời gian bắt đầu đã chọn
        const selectedEnd = dateRangePicker.endDate; // Lấy thời gian kết thúc đã chọn
        updateRevenueChart(selectedStart, selectedEnd);
    });

    $(document).on("click", "#lineChartRevenueIcon", function () {
        chartTypeRevenue = "area";
        const dateRangePicker = $("#date-list-revenue").data("daterangepicker");
        const selectedStart = dateRangePicker.startDate; // Lấy thời gian bắt đầu đã chọn
        const selectedEnd = dateRangePicker.endDate; // Lấy thời gian kết thúc đã chọn
        updateRevenueChart(selectedStart, selectedEnd);
    });


    $(document).on("click", "#barChartNewCustomerIcon", function () {
        chartTypeNewCustomer = "bar";
        const dateRangePicker = $("#date-list-new-customer").data("daterangepicker");
        const selectedStart = dateRangePicker.startDate; // Lấy thời gian bắt đầu đã chọn
        const selectedEnd = dateRangePicker.endDate; // Lấy thời gian kết thúc đã chọn
        updateNewCustomerChart(selectedStart, selectedEnd);
    });

    $(document).on("click", "#lineChartNewCustomerIcon", function () {
        chartTypeNewCustomer = "area";
        const dateRangePicker = $("#date-list-new-customer").data("daterangepicker");
        const selectedStart = dateRangePicker.startDate; // Lấy thời gian bắt đầu đã chọn
        const selectedEnd = dateRangePicker.endDate; // Lấy thời gian kết thúc đã chọn
        updateNewCustomerChart(selectedStart, selectedEnd);
    });


    // Khởi tạo Daterangepicker
    function initializeDateRangePicker(elementId, start, end, callback) {
        $(`#${elementId}`).daterangepicker(
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
                // Gọi callback với id và khoảng thời gian đã chọn
                callback(elementId, start, end);
            }
        );
    }

// Hàm callback được sử dụng cho từng `id`
    function onDateRangeChange(elementId, start, end) {
        // Thực hiện logic theo từng `id` (ví dụ: cập nhật dữ liệu biểu đồ)
        if (elementId === "date-list-revenue") {
            updateRevenueChart(start, end);
        }
        if (elementId === "date-list-new-customer") {
            updateNewCustomerChart(start, end);
        }
        if (elementId === "date-top-product") {
            updateTopProductSoldChart(start, end);
        }
        if (elementId === "date-rate-repeat-purchase") {
            updateRateRepeatPurchaseChart(start, end);
        }
        if (elementId === "date-return-order") {
            updateReasonReturnOrderBarChart(start, end)
        }
        // Có thể thêm logic cho các `id` khác ở đây
    }

// Khởi tạo `Daterangepicker` cho `#date-list-revenue`
    initializeDateRangePicker("date-list-revenue", startOfRevenue, endOfRevenue, onDateRangeChange);
    initializeDateRangePicker("date-list-new-customer", startOfNewCustomer, endOfNewCustomer, onDateRangeChange);
    initializeDateRangePicker("date-top-product", startOfTopProductSold, endOfTopProductSold, onDateRangeChange);
    initializeDateRangePicker("date-rate-repeat-purchase", startOfRepeatPurchase, endOfRepeatPurchase, onDateRangeChange);
    initializeDateRangePicker("date-return-order", startOfReturnOrder, endOfReturnOrder, onDateRangeChange);


    // Gọi lần đầu tiên khi trang tải
    updateRevenueChart(startOfRevenue, endOfRevenue);
    updateNewCustomerChart(startOfNewCustomer, endOfNewCustomer);
    updateTopProductSoldChart(startOfTopProductSold, endOfTopProductSold);
    updateProductSoldTable();
    updateRateRepeatPurchaseChart(startOfRepeatPurchase, endOfRepeatPurchase)
    updateReasonReturnOrderBarChart(startOfReturnOrder, endOfReturnOrder)
});


// table
$(document).ready(function () {
    var responsiveDataTable = $("#list_product_sold_in_stock_statistical");

    if (responsiveDataTable.length !== 0) {
        responsiveDataTable.DataTable({
            "aLengthMenu": [[5, 20, 30, 50, 75, -1], [5, 20, 30, 50, 75, "All"]],
            "pageLength": 5,
            "paging": true,
            "searching": true,
            "info": true,
            "responsive": true,
            "autoWidth": false,
            "dom": '<"row justify-content-between top-information"lf>rt<"row justify-content-between bottom-information"ip><"clear">',
            "order": [[2, "desc"]], // Sắp xếp mặc định cột 2 (index 1) giảm dần
            "columnDefs": [
                {
                    "orderable": true,  // Bật sắp xếp
                    "targets": [2, 3]   // Chỉ bật sắp xếp cho cột 2 và 3
                },
                {
                    "orderable": false, // Tắt sắp xếp
                    "targets": [0, 2]   // Cột # và cột cuối
                }
            ],
            "language": {
                "lengthMenu": "Hiển thị _MENU_ dòng mỗi trang",
                "zeroRecords": "Không tìm thấy dữ liệu",
                "info": "Hiển thị từ _START_ đến _END_ của _TOTAL_ dòng",
                "infoEmpty": "Không có dữ liệu để hiển thị",
                "infoFiltered": "(lọc từ tổng số _MAX_ dòng)",
                "search": "Tìm kiếm:",
                "paginate": {
                    "previous": "Trước",
                    "next": "Sau"
                }
            }
        });
    }

});




