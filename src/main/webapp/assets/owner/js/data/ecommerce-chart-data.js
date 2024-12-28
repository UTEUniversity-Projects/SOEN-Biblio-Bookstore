// /* ====== Chart ====== */
//
//
//
// (function ($) {
//     "use strict";
//     // function newrevenueChart() {
//     //     var options = {
//     //         chart: {
//     //             height: 365,
//     //             type: 'line',
//     //             stacked: false,
//     //             foreColor: '#373d3f',
//     //             sparkline: {
//     //                 enabled: !1
//     //             },
//     //             dropShadow: {
//     //                 enabled: true,
//     //                 enabledOnSeries: undefined,
//     //                 top: 5,
//     //                 left: 5,
//     //                 blur: 3,
//     //                 color: '#000',
//     //                 opacity: 0.1
//     //             },
//     //             toolbar: {
//     //                 show: !1
//     //             }
//     //         },
//     //         dataLabels: {
//     //             enabled: !1
//     //         },
//     //         series: [
//     //             {
//     //                 name: 'Revenue',
//     //                 data: [25, 65, 42, 52, 14, 32, 54, 12, 24, 63, 24],
//     //             }, {
//     //                 name: 'Orders',
//     //                 data: [15, 65 ,45, 44, 65, 85, 23, 74, 53, 65, 75],
//     //             }, {
//     //                 name: 'Expence',
//     //                 data: [12, 31, 40, 39, 54, 74, 20, 48, 50, 25, 43],
//     //             },
//     //         ],
//     //         plotOptions: {
//     //             bar: {
//     //               horizontal: false,
//     //               columnWidth: '20%',
//     //             }
//     //           },
//     //           stroke: {
//     //             width: [2, 2, 2],
//     //             curve: "smooth",
//     //         },
//     //         fill: {
//     //             opacity: [1, 1, 1],
//     //             gradient: {
//     //                 inverseColors: false,
//     //                 shade: 'light',
//     //                 type: "vertical",
//     //                 opacityFrom: .45,
//     //                 opacityTo: .05,
//     //                 stops: [50, 100, 100, 100]
//     //             }
//     //         },
//     //         colors: ["#3f51b5", "#50d1f8", "#5caf90"],
//     //         xaxis: {
//     //             categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//     //             axisTicks: {
//     //                 show: !1
//     //             },
//     //             axisBorder: {
//     //                 show: !1
//     //             }
//     //         },
//     //         yaxis: {
//     //             labels: {
//     //                 formatter: function (e) {
//     //                     return e + "k"
//     //                 },
//     //                 offsetX: -15
//     //             }
//     //         },
//     //         legend: {
//     //             show: !0,
//     //             horizontalAlign: "center",
//     //             offsetX: 0,
//     //             offsetY: -5,
//     //             markers: {
//     //                 width: 15,
//     //                 height: 10,
//     //                 radius: 6
//     //             },
//     //             itemMargin: {
//     //                 horizontal: 10,
//     //                 vertical: 0
//     //             }
//     //         },
//     //         grid: {
//     //             show: !1,
//     //             xaxis: {
//     //                 lines: {
//     //                     show: !1
//     //                 }
//     //             },
//     //             yaxis: {
//     //                 lines: {
//     //                     show: !1
//     //                 }
//     //             },
//     //             padding: {
//     //                 top: 0,
//     //                 right: -2,
//     //                 bottom: 15,
//     //                 left: 0
//     //             },
//     //         },
//     //         responsive: [{
//     //             breakpoint: 480,
//     //             options: {
//     //                 chart: {
//     //                     height: '250px',
//     //                 },
//     //                 yaxis: {
//     //                     show: false,
//     //                 },
//     //             }
//     //         }]
//     //     };
//     //     var newrevenueChart = new ApexCharts(document.querySelector("#newrevenueChart"), options);
//     //     newrevenueChart.render();
//     // }
//     // function newcampaignsChart() {
//     //     var options = {
//     //         series: [44, 55, 67],
//     //         chart: {
//     //         height: 350,
//     //         type: 'radialBar',
//     //       },
//     //       plotOptions: {
//     //         radialBar: {
//     //           dataLabels: {
//     //             name: {
//     //               fontSize: '22px',
//     //             },
//     //             value: {
//     //               fontSize: '16px',
//     //             },
//     //             total: {
//     //               show: true,
//     //               label: 'Total',
//     //               formatter: function (w) {
//     //                 return 249
//     //               }
//     //             }
//     //           }
//     //         }
//     //       },
//     //       labels: ['Social', 'Referral', 'Organic'],
//     //       colors: ["#3f51b5", "#50d1f8", "#5caf90"],
//     //       };
//
//     //     var newcampaignsChart = new ApexCharts(document.querySelector("#newcampaignsChart"), options);
//     //     newcampaignsChart.render();
//     // }
//
//     function revenueLineChart() {
//         var options = {
//             series: [{
//                 name: 'Doanh thu',
//                 data: [23000000, 12000000, 23000000, 22000000, 15000000, 42000000, 31000000, 27000000, 45000000, 28000000, 37000000]
//             }],
//             chart: {
//                 height: 365,
//                 type: 'line',
//                 stacked: false,
//                 foreColor: '#373d3f',
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: [2],
//                 curve: 'smooth'
//             },
//             plotOptions: {
//                 bar: {
//                     horizontal: !1,
//                     columnWidth: 10,
//                     borderRadius: 0
//                 }
//             },
//             fill: {
//                 opacity: [1],
//                 gradient: {
//                     inverseColors: false,
//                     shade: 'light',
//                     type: "vertical",
//                     opacityFrom: .45,
//                     opacityTo: .05,
//                     stops: [50, 100, 100, 100]
//                 }
//             },
//             colors: ["#5f6af5"],
//             xaxis: {
//                 categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//                 axisTicks: {
//                     show: !1
//                 },
//                 axisBorder: {
//                     show: !1
//                 }
//             },
//             legend: {
//                 show: !0,
//                 horizontalAlign: "center",
//                 offsetX: 0,
//                 offsetY: -5,
//                 markers: {
//                     width: 15,
//                     height: 10,
//                     radius: 6
//                 },
//                 itemMargin: {
//                     horizontal: 10,
//                     vertical: 0
//                 }
//             },
//             grid: {
//                 show: !1,
//                 xaxis: {
//                     lines: {
//                         show: !1
//                     }
//                 },
//                 yaxis: {
//                     lines: {
//                         show: !1
//                     }
//                 },
//                 padding: {
//                     top: 0,
//                     right: -2,
//                     bottom: 15,
//                     left: 0
//                 },
//             },
//             yaxis: {
//                 labels: {
//                     formatter: function (e) {
//                         return e + " vnđ"
//                     },
//                     show: !0
//                 }
//             }
//         };
//         var revenueLineChart = new ApexCharts(document.querySelector("#revenueLineChart"), options);
//         revenueLineChart.render();
//     }
//     // function revenueBarChart() {
//     //     var options = {
//     //         chart: {
//     //             type: "bar",
//     //             height: 300,
//     //             stacked: !0,
//     //             sparkline: {
//     //                 enabled: !1
//     //             },
//     //             dropShadow: {
//     //                 enabled: true,
//     //                 enabledOnSeries: undefined,
//     //                 top: 5,
//     //                 left: 5,
//     //                 blur: 3,
//     //                 color: '#000',
//     //                 opacity: 0.1
//     //             },
//     //             toolbar: {
//     //                 show: !1
//     //             }
//     //         },
//     //         stroke: {
//     //             width: 0
//     //         },
//     //         dataLabels: {
//     //             enabled: !1
//     //         },
//     //         series: [{
//     //             name: "Doanh thu",
//     //             data: [3522000, 7562962, 8762457, 2527895, 1098762, 1236478, 1924538, 1739856, 1783467, 9802745, 1239921, 6549922]
//     //         }],
//     //         plotOptions: {
//     //             bar: {
//     //                 horizontal: !1,
//     //                 columnWidth: 25,
//     //                 borderRadius: 0
//     //             }
//     //         },
//     //         markers: {
//     //             size: 4,
//     //             hover: {
//     //                 size: 6
//     //             }
//     //         },
//     //         xaxis: {
//     //             categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//     //             axisBorder: {
//     //                 show: !0
//     //             },
//     //             axisTicks: {
//     //                 show: !0
//     //             },
//     //             labels: {
//     //                 show: !0
//     //             }
//     //         },
//     //         yaxis: {
//     //             labels: {
//     //                 formatter: function (e) {
//     //                     return e + " vnđ"
//     //                 },
//     //                 show: !0
//     //             }
//     //         },
//     //         colors: ["#333", "rgba(255, 79, 126, 0.5)"],
//     //     };
//     //     var revenueBarChart = new ApexCharts(document.querySelector("#revenueBarChart"), options);
//     //     revenueBarChart.render();
//     // }
//
//     function orderBarChart() {
//         var options = {
//             chart: {
//                 type: "bar",
//                 height: 300,
//                 stacked: !0,
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: 0
//             },
//             dataLabels: {
//                 enabled: !1
//             },
//             series: [{
//                 name: "Đơn hàng",
//                 data: [112, 233, 65, 234, 355, 233, 424, 357, 362, 352, 623, 553]
//             }],
//             plotOptions: {
//                 bar: {
//                     horizontal: !1,
//                     columnWidth: 25,
//                     borderRadius: 0
//                 }
//             },
//             markers: {
//                 size: 4,
//                 hover: {
//                     size: 6
//                 }
//             },
//             xaxis: {
//                 categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//                 axisBorder: {
//                     show: !0
//                 },
//                 axisTicks: {
//                     show: !0
//                 },
//                 labels: {
//                     show: !0
//                 }
//             },
//             yaxis: {
//                 labels: {
//                     formatter: function (e) {
//                         return e
//                     },
//                     show: !0
//                 }
//             },
//             colors: ["#33FFFF", "rgba(255, 79, 126, 0.5)"],
//         };
//         var orderBarChart = new ApexCharts(document.querySelector("#orderBarChart"), options);
//         orderBarChart.render();
//     }
//     function categoryOderBarChart() {
//         var options = {
//             chart: {
//                 type: "bar",
//                 height: 300,
//                 stacked: !0,
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: 0
//             },
//             dataLabels: {
//                 enabled: !1
//             },
//             series: [{
//                 name: "Doanh thu",
//                 data: [3522000, 7562962, 8762457, 2527895, 1098762, 1236478, 1924538, 1739856, 1783467, 9802745, 1239921, 6549922]
//             }],
//             plotOptions: {
//                 bar: {
//                     horizontal: !1,
//                     columnWidth: 25,
//                     borderRadius: 0
//                 }
//             },
//             markers: {
//                 size: 4,
//                 hover: {
//                     size: 6
//                 }
//             },
//             xaxis: {
//                 categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//                 axisBorder: {
//                     show: !0
//                 },
//                 axisTicks: {
//                     show: !0
//                 },
//                 labels: {
//                     show: !0
//                 }
//             },
//             yaxis: {
//                 labels: {
//                     formatter: function (e) {
//                         return e + " vnđ"
//                     },
//                     show: !0
//                 }
//             },
//             colors: ["#333", "rgba(255, 79, 126, 0.5)"],
//         };
//         var categoryOderBarChart = new ApexCharts(document.querySelector("#categoryOderBarChart"), options);
//         categoryOderBarChart.render();
//     }
//     function topTenBookSellingBarChart() {
//         var options = {
//             chart: {
//                 type: "bar",
//                 height: 300,
//                 stacked: !0,
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: 0
//             },
//             dataLabels: {
//                 enabled: !1
//             },
//             series: [{
//                 name: "Số lượng",
//                 data: [133, 144, 223, 323, 455, 522, 534, 589, 622, 645]
//             }],
//             plotOptions: {
//                 bar: {
//                     horizontal: !1,
//                     columnWidth: 25,
//                     borderRadius: 0
//                 }
//             },
//             markers: {
//                 size: 4,
//                 hover: {
//                     size: 6
//                 }
//             },
//             xaxis: {
//                 categories: ["Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính"],
//                 axisBorder: {
//                     show: !0
//                 },
//                 axisTicks: {
//                     show: !0
//                 },
//                 labels: {
//                     show: !0
//                 }
//             },
//             yaxis: {
//                 labels: {
//                     formatter: function (e) {
//                         return e
//                     },
//                     show: !0
//                 }
//             },
//             colors: ["#333", "rgba(255, 79, 126, 0.5)"],
//         };
//         var topTenBookSellingBarChart = new ApexCharts(document.querySelector("#topTenBookSellingBarChart"), options);
//         topTenBookSellingBarChart.render();
//     }
//     function revenueOfCategoryBarChart() {
//         var options = {
//             chart: {
//                 type: "bar",
//                 height: 300,
//                 stacked: !0,
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: 0
//             },
//             dataLabels: {
//                 enabled: !1
//             },
//             series: [{
//                 name: "Doanh thu",
//                 data: [3522000, 7562962, 8762457, 2527895, 1098762, 1236478, 1924538, 1739856, 1783467, 9802745, 1239921, 6549922]
//             }],
//             plotOptions: {
//                 bar: {
//                     horizontal: !1,
//                     columnWidth: 25,
//                     borderRadius: 0
//                 }
//             },
//             markers: {
//                 size: 4,
//                 hover: {
//                     size: 6
//                 }
//             },
//             xaxis: {
//                 categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//                 axisBorder: {
//                     show: !0
//                 },
//                 axisTicks: {
//                     show: !0
//                 },
//                 labels: {
//                     show: !0
//                 }
//             },
//             yaxis: {
//                 labels: {
//                     formatter: function (e) {
//                         return e + " vnđ"
//                     },
//                     show: !0
//                 }
//             },
//             colors: ["#333", "rgba(255, 79, 126, 0.5)"],
//         };
//         var revenueOfCategoryBarChart = new ApexCharts(document.querySelector("#revenueOfCategoryBarChart"), options);
//         revenueOfCategoryBarChart.render();
//     }
//     function revenueOfCategoryPieChart() {
//         var options = {
//             series: [12234143, 2213124, 72123421, 8824322, 12134222, 2524510, 12278723],
//             chart: {
//                 height: 280,
//                 type: 'pie',
//             },
//             plotOptions: {
//                 radialBar: {
//                     offsetY: 0,
//                     startAngle: 0,
//                     endAngle: 360,
//                     hollow: {
//                         margin: 0,
//                         padding: 0,
//                         size: '60%',
//                         background: 'transparent',
//                         image: undefined,
//                     },
//                     dataLabels: {
//                         name: {
//                             show: true,
//                         },
//                         value: {
//                             show: true,
//                         }
//                     }
//                 }
//             },
//             //colors: ['#5f6af5', '#ff4f7f', '#2bbb93'],
//             labels: ['Tiểu thuyết', 'Văn học', 'Sách Cổ', 'Cổ tích', 'Kinh tế', 'Kỹ năng sống', 'Mục khác'],
//             legend: {
//                 show: true,
//                 showForSingleSeries: false,
//                 showForNullSeries: true,
//                 showForZeroSeries: true,
//                 position: 'bottom',
//                 horizontalAlign: 'center',
//                 floating: false,
//                 fontSize: '14px',
//                 fontFamily: 'Helvetica, Arial',
//                 fontWeight: 400,
//                 formatter: undefined,
//                 inverseOrder: false,
//                 width: undefined,
//                 height: undefined,
//                 tooltipHoverFormatter: undefined,
//                 customLegendItems: [],
//                 offsetX: 0,
//                 offsetY: 0,
//                 markers: {
//                     width: 15,
//                     height: 10,
//                     radius: 6
//                 },
//                 labels: {
//                     colors: undefined,
//                     useSeriesColors: false
//                 },
//             },
//             responsive: [{
//                 breakpoint: 480,
//                 options: {
//                     legend: {
//                         show: false
//                     }
//                 }
//             }]
//         };
//
//         var revenueOfCategoryPieChart = new ApexCharts(document.querySelector("#revenueOfCategoryPieChart"), options);
//         revenueOfCategoryPieChart.render();
//     }
//     function newCustomerBarChart() {
//         var options = {
//             chart: {
//                 type: "bar",
//                 height: 300,
//                 stacked: !0,
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: 0
//             },
//             dataLabels: {
//                 enabled: !1
//             },
//             series: [{
//                 name: "Khách hàng mới",
//                 data: [133, 144, 223, 323, 455, 522, 534, 589, 622, 645]
//             }],
//             plotOptions: {
//                 bar: {
//                     horizontal: !1,
//                     columnWidth: 25,
//                     borderRadius: 0
//                 }
//             },
//             markers: {
//                 size: 4,
//                 hover: {
//                     size: 6
//                 }
//             },
//             xaxis: {
//                 categories: ["Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính", "Đắc Nhân Tâm", "Giới Định Tính"],
//                 axisBorder: {
//                     show: !0
//                 },
//                 axisTicks: {
//                     show: !0
//                 },
//                 labels: {
//                     show: !0
//                 }
//             },
//             yaxis: {
//                 labels: {
//                     formatter: function (e) {
//                         return e
//                     },
//                     show: !0
//                 }
//             },
//             colors: ["#333", "rgba(255, 79, 126, 0.5)"],
//         };
//         var newCustomerBarChart = new ApexCharts(document.querySelector("#newCustomerBarChart"), options);
//         newCustomerBarChart.render();
//     }
//     function refundOderBarChart() {
//         var options = {
//             chart: {
//                 type: "bar",
//                 height: 300,
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: 0
//             },
//             dataLabels: {
//                 enabled: !1
//             },
//             series: [{
//                 name: "Đơn hàng thành công",
//                 data: [1070, 2250, 1565, 4560, 2850, 5658, 2311, 6454, 4324, 1231, 1222, 5413]
//             }, {
//                 name: "Đơn hàng bị hoàn trả",
//                 data: [50, 21, 12, 60, 23, 25, 12, 55, 11, 44, 56, 12]
//             }],
//             plotOptions: {
//                 bar: {
//                     horizontal: !0,
//                     columnWidth: 10,
//                     borderRadius: 0
//                 }
//             },
//             markers: {
//                 size: 4,
//                 hover: {
//                     size: 6
//                 }
//             },
//             legend: {
//                 show: !0,
//                 horizontalAlign: "center",
//                 offsetX: 0,
//                 offsetY: -5,
//                 markers: {
//                     width: 15,
//                     height: 10,
//                     radius: 6
//                 },
//                 itemMargin: {
//                     horizontal: 10,
//                     vertical: 0
//                 }
//             },
//             xaxis: {
//                 categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//                 axisBorder: {
//                     show: !1
//                 },
//                 axisTicks: {
//                     show: !0
//                 },
//                 labels: {
//                     show: !0
//                 }
//             },
//             grid: {
//                 show: !1,
//                 xaxis: {
//                     lines: {
//                         show: !1
//                     }
//                 },
//                 yaxis: {
//                     lines: {
//                         show: !1
//                     }
//                 },
//                 padding: {
//                     top: 0,
//                     right: -2,
//                     bottom: 15,
//                     left: 0
//                 },
//             },
//             yaxis: {
//                 labels: {
//                     show: !0
//                 }
//             },
//             colors: ["#3F51B5", "#F23E5C"],
//         };
//         var refundOderBarChart = new ApexCharts(document.querySelector("#refundOderBarChart"), options);
//         refundOderBarChart.render();
//     }
//     function cancelOderBarChart() {
//         var options = {
//             chart: {
//                 type: "bar",
//                 height: 300,
//                 sparkline: {
//                     enabled: !1
//                 },
//                 dropShadow: {
//                     enabled: true,
//                     enabledOnSeries: undefined,
//                     top: 5,
//                     left: 5,
//                     blur: 3,
//                     color: '#000',
//                     opacity: 0.1
//                 },
//                 toolbar: {
//                     show: !1
//                 }
//             },
//             stroke: {
//                 width: 0
//             },
//             dataLabels: {
//                 enabled: !1
//             },
//             series: [{
//                 name: "Đơn hàng thành công",
//                 data: [1070, 2250, 1565, 4560, 2850, 5658, 2311, 6454, 4324, 1231, 1222, 5413]
//             }, {
//                 name: "Đơn hàng bị hủy",
//                 data: [50, 21, 12, 60, 23, 25, 12, 55, 11, 44, 56, 12]
//             }],
//             plotOptions: {
//                 bar: {
//                     horizontal: !0,
//                     columnWidth: 10,
//                     borderRadius: 0
//                 }
//             },
//             markers: {
//                 size: 4,
//                 hover: {
//                     size: 6
//                 }
//             },
//             legend: {
//                 show: !0,
//                 horizontalAlign: "center",
//                 offsetX: 0,
//                 offsetY: -5,
//                 markers: {
//                     width: 15,
//                     height: 10,
//                     radius: 6
//                 },
//                 itemMargin: {
//                     horizontal: 10,
//                     vertical: 0
//                 }
//             },
//             xaxis: {
//                 categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//                 axisBorder: {
//                     show: !1
//                 },
//                 axisTicks: {
//                     show: !0
//                 },
//                 labels: {
//                     show: !0
//                 }
//             },
//             grid: {
//                 show: !1,
//                 xaxis: {
//                     lines: {
//                         show: !1
//                     }
//                 },
//                 yaxis: {
//                     lines: {
//                         show: !1
//                     }
//                 },
//                 padding: {
//                     top: 0,
//                     right: -2,
//                     bottom: 15,
//                     left: 0
//                 },
//             },
//             yaxis: {
//                 labels: {
//                     show: !0
//                 }
//             },
//             colors: ["#3F51B5", "#F23E5C"],
//         };
//         var cancelOderBarChart = new ApexCharts(document.querySelector("#cancelOderBarChart"), options);
//         cancelOderBarChart.render();
//     }
//     function reasonRefundOderBarChart() {
//         var options = {
//             series: [12, 22, 12, 43, 12, 33, 12],
//             chart: {
//                 height: 280,
//                 type: 'pie',
//             },
//             plotOptions: {
//                 radialBar: {
//                     offsetY: 0,
//                     startAngle: 0,
//                     endAngle: 360,
//                     hollow: {
//                         margin: 0,
//                         padding: 0,
//                         size: '60%',
//                         background: 'transparent',
//                         image: undefined,
//                     },
//                     dataLabels: {
//                         name: {
//                             show: true,
//                         },
//                         value: {
//                             show: true,
//                         }
//                     }
//                 }
//             },
//             //colors: ['#5f6af5', '#ff4f7f', '#2bbb93'],
//             labels: ['Sách bị bẩn', 'Sách bị lỗi', 'Sách giao sai', 'Đặt nhầm sách', 'Không thích nữa', 'Hết tiền rồi', 'Mục khác'],
//             legend: {
//                 show: true,
//                 showForSingleSeries: false,
//                 showForNullSeries: true,
//                 showForZeroSeries: true,
//                 position: 'bottom',
//                 horizontalAlign: 'center',
//                 floating: false,
//                 fontSize: '14px',
//                 fontFamily: 'Helvetica, Arial',
//                 fontWeight: 400,
//                 formatter: undefined,
//                 inverseOrder: false,
//                 width: undefined,
//                 height: undefined,
//                 tooltipHoverFormatter: undefined,
//                 customLegendItems: [],
//                 offsetX: 0,
//                 offsetY: 0,
//                 markers: {
//                     width: 15,
//                     height: 10,
//                     radius: 6
//                 },
//                 labels: {
//                     colors: undefined,
//                     useSeriesColors: false
//                 },
//             },
//             responsive: [{
//                 breakpoint: 480,
//                 options: {
//                     legend: {
//                         show: false
//                     }
//                 }
//             }]
//         };
//
//         var reasonRefundOderBarChart = new ApexCharts(document.querySelector("#reasonRefundOderBarChart"), options);
//         reasonRefundOderBarChart.render();
//     }
//     function reasonCancelOderBarChart() {
//         var options = {
//             series: [12234143, 2213124, 72123421, 8824322, 12134222, 2524510, 12278723],
//             chart: {
//                 height: 280,
//                 type: 'pie',
//             },
//             plotOptions: {
//                 radialBar: {
//                     offsetY: 0,
//                     startAngle: 0,
//                     endAngle: 360,
//                     hollow: {
//                         margin: 0,
//                         padding: 0,
//                         size: '60%',
//                         background: 'transparent',
//                         image: undefined,
//                     },
//                     dataLabels: {
//                         name: {
//                             show: true,
//                         },
//                         value: {
//                             show: true,
//                         }
//                     }
//                 }
//             },
//             //colors: ['#5f6af5', '#ff4f7f', '#2bbb93'],
//             labels: ['Sách bị bẩn', 'Sách bị lỗi', 'Sách giao sai', 'Đặt nhầm sách', 'Không thích nữa', 'Hết tiền rồi', 'Mục khác'],
//             legend: {
//                 show: true,
//                 showForSingleSeries: false,
//                 showForNullSeries: true,
//                 showForZeroSeries: true,
//                 position: 'bottom',
//                 horizontalAlign: 'center',
//                 floating: false,
//                 fontSize: '14px',
//                 fontFamily: 'Helvetica, Arial',
//                 fontWeight: 400,
//                 formatter: undefined,
//                 inverseOrder: false,
//                 width: undefined,
//                 height: undefined,
//                 tooltipHoverFormatter: undefined,
//                 customLegendItems: [],
//                 offsetX: 0,
//                 offsetY: 0,
//                 markers: {
//                     width: 15,
//                     height: 10,
//                     radius: 6
//                 },
//                 labels: {
//                     colors: undefined,
//                     useSeriesColors: false
//                 },
//             },
//             responsive: [{
//                 breakpoint: 480,
//                 options: {
//                     legend: {
//                         show: false
//                     }
//                 }
//             }]
//         };
//
//         var reasonCancelOderBarChart = new ApexCharts(document.querySelector("#reasonCancelOderBarChart"), options);
//         reasonCancelOderBarChart.render();
//     }
//
//     jQuery(window).on('load', function () {
//
//         revenueLineChart();
//         revenueBarChart();
//
//         orderBarChart();
//         categoryOderBarChart();
//
//         topTenBookSellingBarChart();
//
//         revenueOfCategoryPieChart();
//         revenueOfCategoryBarChart();
//
//         newCustomerBarChart();
//
//         refundOderBarChart();
//         cancelOderBarChart();
//         reasonRefundOderBarChart();
//         reasonCancelOderBarChart();
//     });
//
// })(jQuery);
//
//
//
//
