(function ($) {
    "use strict";

    /*----------------------------- Site Loader & Popup --------------------*/
    $(window).on("load", function () {
        $("#cr-overlay").fadeOut("slow");
    });

    $(document).ready(function () {
        $("body").attr("data-cr-mode", "light");

        /*========== Tooltips ===========*/
        $("[title]").attr("data-bs-toggle", "tooltip");
        $("[title]").tooltip();

        /*========== Sidebar ===========*/
        // mobileAndTabletCheck
        window.mobileAndTabletCheck = function () {
            let check = false;
            (function (a) {
                if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) check = true;
            })(navigator.userAgent || navigator.vendor || window.opera);
            return check;
        };

        function winSizeWidth() {
            var width = $(window).width();
            width = isMobTab ? isMobTab : width + 17;
            return width;
        }

        var currentActiveTab = localStorage.getItem("currentActiveTab") ?? null;
        var currentActiveSubTab = localStorage.getItem("currentActiveSubTab") ?? null;
        var currentSubLink = localStorage.getItem("currentSubLink") ?? null;

        var isMobTab = mobileAndTabletCheck();
        var screenSize = winSizeWidth();

        var sSize = {
            min: 575, max: 991,
        };

        function hideShowSidebar(el, activeEl, className, type) {
            if (sSize.max > screenSize) {
                if (sSize.min >= screenSize) {
                    $(el).show();
                    $(activeEl).addClass(className);
                } else {
                    if ($(".wrapper").hasClass("sb-default")) {
                        if (type == "click") {
                            $(el).show();
                            $(activeEl).addClass(className);
                        } else {
                            $(el).hide();
                            $(activeEl).removeClass(className);
                        }
                    }

                    if ($(".wrapper").hasClass("sb-collapse")) {
                        if (type == "resize" || type == "click") {
                            $(el).hide();
                            $(activeEl).removeClass(className);
                        } else {
                            $(el).show();
                            $(activeEl).addClass(className);
                        }
                    }
                }
            } else {
                if ($(".wrapper").hasClass("sb-default")) {
                    $(el).show();
                    $(activeEl).addClass(className);
                }

                if ($(".wrapper").hasClass("sb-collapse")) {
                    if (type == "mouseenter") {
                        $(el).show();
                        $(activeEl).addClass(className);
                    } else {
                        $(el).hide();
                        $(activeEl).removeClass(className);
                    }
                }
            }
        }

        function sidebarActiveTabs(type = "") {
            screenSize = winSizeWidth();
            $(".cr-sb-drop").hide();
            $(".cr-sb-subdrop.condense").hide();

            if (currentActiveTab != "") {
                var currentActiveEle = $(`span.condense:contains('${currentActiveTab}')`).filter(function () {
                    return $(this).text() === currentActiveTab;
                });
                // $(currentActiveEle).show();
                var activeEl = $(currentActiveEle).parents(".cr-sb-item");
                var dropEl = $(currentActiveEle)
                    .parents(".cr-sb-item")
                    .find(".cr-sb-drop");
                hideShowSidebar(dropEl, activeEl, "load-active", type);
            }

            if (currentActiveSubTab != "") {
                var currentSubTabActiveEle = $(`span.condense:contains('${currentActiveSubTab}')`).filter(function () {
                    return $(this).text() === currentActiveSubTab;
                });
                $(currentSubTabActiveEle)
                    .parents(".sb-subdrop-item")
                    .find(".cr-sb-subdrop.condense")
                    .show();
                var activeEl = $(currentSubTabActiveEle).parents(".sb-subdrop-item");
                var dropEl = $(currentSubTabActiveEle)
                    .parents(".sb-subdrop-item")
                    .find(".cr-sb-subdrop");
                hideShowSidebar(dropEl, activeEl, "load-sub-active", type);
            }

            if (currentSubLink != "") {
                var currentSubActiveEle = $(`a.cr-page-link:contains('${currentSubLink}')`).filter(function () {
                    return $(this).text() === currentSubLink;
                });
                $(currentSubActiveEle).addClass("active-link");
                var activeEl = $(currentSubActiveEle).parents(".cr-sb-item");
                var dropEl = $(currentSubActiveEle).parents(".cr-sb-drop");
                hideShowSidebar(dropEl, activeEl, "load-active", type);
            }
        }

        var newURL = window.location.pathname;
        var newURL = newURL.replace("/", "");
        $(".cr-sb-drop").hide();

        if (sSize.max > screenSize) {
            if (sSize.min >= screenSize) {
                $(".condense:not(.cr-sb-drop)").hide();
            } else {
                $(".wrapper").toggleClass("sb-collapse sb-default");

                $(".condense:not(.cr-sb-drop)").hide();
            }
        }
        if ($(".wrapper").hasClass("sb-default")) {
            $(".cr-sb-drop").hide();
            $("a.cr-page-link").filter(`[href='${newURL}']`).parent().parent().show();
            $("a.cr-page-link")
                .filter(`[href='${newURL}']`)
                .parent()
                .parent()
                .parent()
                .addClass("load-active");
            $("a.cr-page-link").filter(`[href='${newURL}']`).addClass("active-link");

            var currentActiveLnk = $("a.cr-page-link").filter(`[href='${newURL}']`);

            if (currentActiveLnk.length > 0) {
                setfxPagelink($(currentActiveLnk));
            }

            var lastURL = localStorage.getItem("URL");

            sidebarActiveTabs();

            localStorage.setItem("URL", newURL);
        }

        $(".cr-drop-toggle").on("click", function (e) {
            var senderElement = e.target;

            if ($(senderElement).hasClass("cr-sub-drop-toggle")) return;
            if ($(senderElement).hasClass("cr-page-link")) return;
            if ($(senderElement).hasClass("condense") && $(senderElement).parents(".cr-sub-drop-toggle").length > 0) return;

            var parent = $(this).parents(".sb-drop-item");
            currentActiveTab = $(parent).find(".cr-drop-toggle span.condense").text();

            if ($(parent).hasClass("load-active")) {
                $(parent).find(".cr-sb-drop").slideUp();
                $(parent).removeClass("load-active");
                currentSubLink = currentActiveSubTab = currentActiveTab = "";
                localStorage.setItem("currentActiveTab", "");
                localStorage.setItem("currentActiveSubTab", "");
                localStorage.setItem("currentSubLink", "");
            } else {
                $(".load-active").removeClass("load-active");
                $(".cr-sb-drop").hide();
                $(parent).addClass("load-active");
                $(parent).find(".cr-sb-drop").slideDown();
                localStorage.setItem("currentActiveTab", currentActiveTab);
                currentSubLink = "";
                localStorage.setItem("currentSubLink", "");
            }
        });

        $(".cr-sub-drop-toggle").on("click", function (e) {
            var senderElement = e.target;

            var parent = $(this).parents(".sb-subdrop-item");
            currentActiveSubTab = $(parent)
                .find(".cr-sub-drop-toggle span.condense")
                .text();

            if ($(parent).hasClass("load-sub-active")) {
                $(parent).find(".cr-sb-subdrop").slideUp();
                $(parent).removeClass("load-sub-active");
                currentActiveSubTab = currentSubLink = "";
                localStorage.setItem("currentActiveSubTab", "");
                localStorage.setItem("currentSubLink", "");
            } else {
                $(".load-sub-active").removeClass("load-sub-active");
                $(".cr-sb-subdrop").hide();
                $(parent).addClass("load-sub-active");
                $(parent).find(".cr-sb-subdrop").slideDown();
                localStorage.setItem("currentActiveSubTab", currentActiveSubTab);
            }
        });

        function setfxPagelink(_this) {
            $(".active-link").removeClass("active-link");

            currentSubLink = $(_this).text();

            if (currentSubLink != "") {
                localStorage.setItem("currentSubLink", currentSubLink);
            }

            $(_this).addClass("active-link");

            // sb-drop-item
            const mainParentHas = $(_this).parents(".sb-drop-item");

            if (mainParentHas) {
                // $(subParentHas).
                currentActiveTab = $(mainParentHas)
                    .find(".cr-drop-toggle span.condense")
                    .text();

                localStorage.setItem("currentActiveTab", currentActiveTab);
            }

            // Sub Parent Item
            const subParentHas = $(_this).parents(".sb-subdrop-item");
            if (subParentHas) {
                // $(subParentHas).
                currentActiveSubTab = $(subParentHas)
                    .find(".cr-sub-drop-toggle span.condense")
                    .text();

                localStorage.setItem("currentActiveSubTab", currentActiveSubTab);
            }
        }

        $(".cr-page-link").on("click", function (e) {
            setfxPagelink($(this));
        });

        $(window).resize(function (e) {
            screenSize = winSizeWidth();
            if (sSize.max >= screenSize) {
                // console.log('Under sSize.max');
                if ($(".wrapper").hasClass("sb-default")) {
                    // console.log('Outer sSize.max >>>>>> set sb-default');
                    $(".cr-sidebar-overlay").fadeOut();

                    $(".wrapper").removeClass("sb-default").addClass("sb-collapse");

                    $(".condense:not(.cr-sb-drop)").hide();
                    sidebarActiveTabs(e.type);
                }
            }
            if (sSize.max < screenSize || sSize.min >= screenSize) {
                // console.log('Outer sSize.max or under sSize.min');

                if ($(".wrapper").hasClass("sb-collapse")) {
                    // console.log('Outer sSize.max >>>>>> set sb-collapse');
                    $(".cr-sidebar-overlay").fadeOut();

                    $(".wrapper").removeClass("sb-collapse").addClass("sb-default");
                    $(".condense:not(.cr-sb-drop)").show();
                    sidebarActiveTabs(e.type);
                }
            }
        });

        $(".cr-sidebar-overlay").on("click", function (e) {
            $(".cr-sidebar-overlay").fadeOut();

            $(".wrapper").toggleClass("sb-collapse sb-default");

            $(".condense:not(.cr-sb-drop)").hide();

            sidebarActiveTabs(e.type);
        });

        // On click Toggle sidebar collapse
        $(".cr-toggle-sidebar").on("click", function (e) {
            screenSize = winSizeWidth();
            if (sSize.max > screenSize) {
                $(".cr-sidebar-overlay").fadeIn();
                // $(".wrapper").removeClass('sb-collapse').addClass('sb-default')
            }
            $(".wrapper").toggleClass("sb-collapse sb-default");
            $(this).toggleClass("active");
            // debugger;;
            if ($(".wrapper").hasClass("sb-default")) {
                $(".condense").show();
                $(".cr-sb-drop").hide();

                sidebarActiveTabs(e.type);
            } else {
                if (sSize.max < screenSize) {
                    $(".condense:not(.cr-sb-drop)").hide();
                } else {
                    $(".condense:not(.cr-sb-drop)").show();
                    $(".condense.cr-sb-drop").hide();
                }
                sidebarActiveTabs(e.type);
            }
        });
        $(".cr-sidebar, .sb-collapse").on("mouseenter", function (e) {
            screenSize = winSizeWidth();
            if (sSize.max < screenSize) {
                if (!$(".wrapper").hasClass("sb-default")) {
                    $(".condense:not(.cr-sb-drop)").show();
                }
                sidebarActiveTabs(e.type);
            }
        });

        $(".cr-sidebar").on("mouseleave", function (e) {
            screenSize = winSizeWidth();
            if (sSize.max < screenSize) {
                if (!$(".wrapper").hasClass("sb-default")) {
                    $(".condense:not(.cr-sb-drop)").hide();
                }
                sidebarActiveTabs(e.type);
            }
        });

        /*========== Header tools ===========*/
        $(".cr-screen.full").on("click", function () {
            $(this).css("display", "none");
            $(".cr-screen.reset").css("display", "flex");

            // current working methods
            if (document.documentElement.requestFullscreen) {
                document.documentElement.requestFullscreen();
            } else if (document.documentElement.msRequestFullscreen) {
                document.documentElement.msRequestFullscreen();
            } else if (document.documentElement.mozRequestFullScreen) {
                document.documentElement.mozRequestFullScreen();
            } else if (document.documentElement.webkitRequestFullscreen) {
                document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
            }
        });
        $(".cr-screen.reset").on("click", function () {
            $(this).css("display", "none");
            $(".cr-screen.full").css("display", "flex");
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitExitFullscreen) {
                document.webkitExitFullscreen();
            }
        });

        $(".cr-notify").on("click", function () {
            $(".cr-notify-bar").addClass("cr-notify-bar-open");
            $(".cr-notify-bar-overlay").fadeIn();
        });
        $(".cr-notify-bar-overlay, .close-notify").on("click", function () {
            $(".cr-notify-bar").removeClass("cr-notify-bar-open");
            $(".cr-notify-bar-overlay").fadeOut();
        });

        $(".open-search").on("click", function () {
            $(".cr-search").fadeIn();
        });

        /* Footer year */
        var date = new Date().getFullYear();

        document.getElementById("copyright_year").innerHTML = date;

        /*========== Search Remix icon page ===========*/
        $("[data-search-icon]").on("keyup", function () {
            var searchVal = $(this).val().toLowerCase();
            // console.log(searchVal);
            var filterItems = $("[data-search-item]");
            var filterItemsText = $("[data-search-item]").text().toLowerCase();
            var a = $("[data-search-item]:contains(" + searchVal + ")");
            // console.log(a);
            if (searchVal != "") {
                filterItems.closest(".remix-unicode-icon").addClass("hide");
                $("[data-search-item]:contains(" + searchVal + ")")
                    .closest(".remix-unicode-icon")
                    .removeClass("hide");
            } else {
                filterItems.closest(".remix-unicode-icon").removeClass("hide");
            }
        });

        /*========== Search Material icon page ===========*/
        $("[data-search-material]").on("keyup", function () {
            var searchVal = $(this).val().toLowerCase();
            // console.log(searchVal);
            var filterItems = $(".material-search-item");
            var filterItemsText = $(".material-search-item").text().toLowerCase();
            var a = $(".material-search-item:contains(" + searchVal + ")");
            // console.log(a);
            if (searchVal != "") {
                filterItems.closest(".material-icon-item").addClass("hide");
                $(".material-search-item:contains(" + searchVal + ")")
                    .closest(".material-icon-item")
                    .removeClass("hide");
            } else {
                filterItems.closest(".material-icon-item").removeClass("hide");
            }
        });
    });

    $(document).ready(function () {
        function initializeDataTable(selector, lengthMenu, nonOrderableCols = [], dom) {
            const responsiveDataTable = $(selector);

            if (!responsiveDataTable.find('thead').length || !responsiveDataTable.find('tbody').length) {
                return null;
            }

            if (responsiveDataTable.length !== 0) {
                    return responsiveDataTable.DataTable({
                        aLengthMenu: lengthMenu,
                        pageLength: 5,
                        columnDefs: nonOrderableCols.length ? [{orderable: false, targets: nonOrderableCols}] : [],
                        order: [],
                        dom: dom,
                    });
            }
            return null;
        }

        function setupFilter(table) {
            $(".dropdown-item").on("click", function () {
                var selectedStatus = $(this).data("value");
                var selectedText = $(this).text().trim();
                $("#selected-text").text(selectedText);

                $.fn.dataTable.ext.search = [];

                $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
                    var status = $(table.row(dataIndex).node())
                        .find("td[data-status]")
                        .data("status");
                    console.log(status);
                    return selectedStatus === "ALL" || status === selectedStatus;
                });

                table.draw();
            });
        }

        $(".order-row").on("click", function () {
            var href = $(this).data("href");
            if (href) {
                window.location.href = href;
            }
        });

        $(".product-row").on("click", function () {
            var href = $(this).data("href");
            if (href) {
                window.location.href = href;
            }
        });

        // Product table
        var productTable = initializeDataTable(
            "#product-data-table",
            [[5, 10, 20, 50, -1], [5, 10, 20, 50, "All"],],
            [0, 6],
            '<"row justify-content-between top-information"lf>rt<"row justify-content-between bottom-information"ip><"clear">');
        if (productTable) setupFilter(productTable);

        // Order table
        var orderTable = initializeDataTable(
            "#order-data-table",
            [[5, 10, 20, 50, -1], [5, 10, 20, 50, "All"],],
            [],
            '<"row justify-content-between top-information"lf>rt<"row justify-content-between bottom-information"ip><"clear">');
        if (orderTable) setupFilter(orderTable);

        // Order products table
        var orderProductTable = initializeDataTable(
            "#order-product-data-table",
            [[5, 10, 20, 50, -1], [5, 10, 20, 50, "All"],],
            [0],
            'rt<"clear">');
        // if (orderProductTable) setupFilter(orderProductTable);

        // Order return products table
        var orderReturnProductTable = initializeDataTable(
            "#order-product-return-data-table",
            [[5, 10, 20, 50, -1], [5, 10, 20, 50, "All"],],
            [0],
            'rt<"clear">');
        // if (orderReturnProductTable) setupFilter(orderReturnProductTable);

        // Support customer table
        var supportCusTable = initializeDataTable(
            "#support-cus-list",
            [[5, 10, 20, 50, -1], [5, 10, 20, 50, "All"],],
            [4],
            '<"row justify-content-between top-information"l>rt<"row justify-content-between bottom-information"ip><"clear">');
        if (supportCusTable) setupFilter(supportCusTable);
    });

    /*========== On click card zoom (full screen) ===========*/
    $(".cr-full-card").on("click", function () {
        $(this).hide();
        $(this)
            .parent(".header-tools")
            .append('<a href="javascript:void(0)" class="m-l-10 cr-full-card-close"><i class="ri-close-fill"></i></a>');
        $(this).closest(".cr-card").parent().toggleClass("cr-full-screen");
        $(this)
            .closest(".cr-card")
            .parent()
            .parent()
            .append('<div class="cr-card-overlay"></div>');
    });
    $("body").on("click", ".cr-card-overlay, .cr-full-card-close", function () {
        $(".cr-card").find(".cr-full-card-close").remove();
        $(".cr-card").find(".cr-full-card").show();
        $(".cr-card").parent().removeClass("cr-full-screen");
        $(".cr-card-overlay").remove();
    });

    /*========== Upload image preview ===========*/
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $("#imagePreview").css("background-image", "url(" + e.target.result + ")");
                $("#imagePreview").hide();
                $("#imagePreview").fadeIn(650);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#imageUpload").change(function () {
        readURL(this);
    });

    /*======== Product Image Change on Upload ========*/
    $("body").on("change", ".cr-image-upload", function (e) {
        var lkthislk = $(this);

        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var ec_image_preview = lkthislk
                    .parent()
                    .parent()
                    .children(".cr-preview")
                    .find(".cr-image-preview")
                    .attr("src", e.target.result);

                ec_image_preview.hide();
                ec_image_preview.fadeIn(650);
            };
            reader.readAsDataURL(this.files[0]);
        }
    });

    /*======== Ripple button animation ========*/
    $(".ripple-btn, .ripple-default-btn").click(function (e) {
        // Create a ripple element
        var ripple = $('<span class="ripple"></span>');

        // Append the ripple element to the button
        $(this).append(ripple);

        // Position the ripple element at the click position
        ripple.css({
            top: e.pageY - $(this).offset().top, left: e.pageX - $(this).offset().left,
        });

        // Remove the ripple element after the animation completes
        setTimeout(function () {
            ripple.remove();
        }, 1000);
    });

    /*======== Chat sidebar (chatapp page) ========*/
    $(".cr-btn-chat").click(function (e) {
        $("#chat_sidebar").addClass("open-chat-list");
        $(".chat-sidebar-overlay").fadeIn();
    });
    $(".chat-sidebar-overlay, .close-chat-list").click(function (e) {
        $("#chat_sidebar").removeClass("open-chat-list");
        $(".chat-sidebar-overlay").fadeOut();
    });

    /*========== Tools Sidebar ===========*/
    $(".cr-tools-sidebar-toggle").on("click", function () {
        $(".cr-tools-sidebar").addClass("open-tools");
        $(".cr-tools-sidebar-overlay").fadeIn();
        $(".cr-tools-sidebar-toggle").hide();
    });
    $(".cr-tools-sidebar-overlay, .close-tools").on("click", function () {
        $(".cr-tools-sidebar").removeClass("open-tools");
        $(".cr-tools-sidebar-overlay").fadeOut();
        $(".cr-tools-sidebar-toggle").fadeIn();
    });

    /*========== Tools Sidebar ===========*/
    // Header
    $(".cr-tools-item.header").on("click", function () {
        var headerModes = $(this).attr("data-header-mode");
        if (headerModes == "light") {
            $(".cr-header").attr("data-header-mode-tool", "light");
        } else if (headerModes == "dark") {
            $(".cr-header").attr("data-header-mode-tool", "dark");
        }
        $(this)
            .parents(".cr-tools-info")
            .find(".cr-tools-item.header")
            .removeClass("active");
        $(this).addClass("active");
    });
    // Sidebar
    $(".cr-tools-item.sidebar").on("click", function () {
        var sidebarModes = $(this).attr("data-sidebar-mode-tool");
        if (sidebarModes == "light") {
            $(".cr-sidebar").attr("data-mode", "light");
        } else if (sidebarModes == "dark") {
            $(".cr-sidebar").attr("data-mode", "dark");
        } else if (sidebarModes == "bg-1") {
            $(".cr-sidebar").attr("data-mode", "bg-1");
        } else if (sidebarModes == "bg-2") {
            $(".cr-sidebar").attr("data-mode", "bg-2");
        } else if (sidebarModes == "bg-3") {
            $(".cr-sidebar").attr("data-mode", "bg-3");
        } else if (sidebarModes == "bg-4") {
            $(".cr-sidebar").attr("data-mode", "bg-4");
        }
        $(this)
            .parents(".cr-tools-info")
            .find(".cr-tools-item.sidebar")
            .removeClass("active");
        $(this).addClass("active");
    });
    // Backgrounds
    $(".cr-tools-item.bg").on("click", function () {
        var bgModes = $(this).attr("data-bg-mode");
        if (bgModes == "default") {
            $("#mainBg").remove();
        } else if (bgModes == "bg-1") {
            $("#mainBg").remove();
            $("link[href='assets/css/style.css']").after("<link id='mainBg' href='assets/css/bg-1.css' rel='stylesheet'>");
        } else if (bgModes == "bg-2") {
            $("#mainBg").remove();
            $("link[href='assets/css/style.css']").after("<link id='mainBg' href='assets/css/bg-2.css' rel='stylesheet'>");
        } else if (bgModes == "bg-3") {
            $("#mainBg").remove();
            $("link[href='assets/css/style.css']").after("<link id='mainBg' href='assets/css/bg-3.css' rel='stylesheet'>");
        } else if (bgModes == "bg-4") {
            $("#mainBg").remove();
            $("link[href='assets/css/style.css']").after("<link id='mainBg' href='assets/css/bg-4.css' rel='stylesheet'>");
        } else if (bgModes == "bg-5") {
            $("#mainBg").remove();
            $("link[href='assets/css/style.css']").after("<link id='mainBg' href='assets/css/bg-5.css' rel='stylesheet'>");
        }
        $(this)
            .parents(".cr-tools-info")
            .find(".cr-tools-item.bg")
            .removeClass("active");
        $(this).addClass("active");
    });
})(jQuery);

document.querySelectorAll('.price-value').forEach(el => {
    const rawValue = el.textContent.trim();

    const value = parseFloat(rawValue);

    if (!isNaN(value)) {
        const formattedValue = value.toLocaleString('vi-VN');
        if (el.classList.contains('minus-value')) {
            el.innerHTML = `-${formattedValue}<span class="currency-symbol">₫</span>`;
        } else {
            el.innerHTML = `${formattedValue}<span class="currency-symbol">₫</span>`;
        }
    }
});