(function ($) {
    'use strict';

    $(window).on('load', function () {
        $('#cr-overlay').fadeOut('slow');
    });

    $(document).ready(function () {
        'use strict';
        AOS.init({
            once: true,


        });

        /* Product grid & column */
        $('.gridRow').on('click', function () {
            $('.col-100').addClass('col-size');
            $('.col-50').addClass('col-size');
        });

        $('.gridCol').on('click', function () {
            $('.col-100').removeClass('col-size');
            $('.col-50').removeClass('col-size');
        });

        $('.cr-toggle a').on('click', function () {
            $('a').removeClass('active-grid');
            $(this).addClass('active-grid');
        });

        /* Minus and Plus Quantity */
        $('.minus').on('click', function () {
            var $input = $(this).parent().find('input');
            var count = parseInt($input.val()) - 1;
            count = count < 1 ? 1 : count;
            $input.val(count);

            $input.change();
            return false;
        });


        $('.plus').on('click', function () {
            var $input = $(this).parent().find('input');
            $input.val(parseInt($input.val()) + 1);
            $input.change();
            return false;
        });


        /* Onclick Remove Products */
        $('.cr-remove-product').on('click', function () {
            $(this).parent().parent().parent().parent('.cr-product-box').remove();
            var wish_product_count = $('.cr-product-box').length;
            if (wish_product_count == 0) {
                $('.section-wishlist').html(
                    '<p class="cr-wishlist-msg">Your wishlist is empty!</p>'
                );
                $('.section-compare').html(
                    '<p class="cr-wishlist-msg">Your compare list is empty!</p>'
                );
            }
        });

        /* Stickey headre on scroll &&  Menu Fixed On Scroll Active */
        var doc = document.documentElement;
        var w = window;

        var crPrevScroll = w.scrollY || doc.scrollTop;
        var crCurScroll;
        var crDirection = 0;
        var crPrevDirection = 0;

        var checkScroll = function () {
            crCurScroll = w.scrollY || doc.scrollTop;
            if (crCurScroll > crPrevScroll) {
                //scrolled up
                crDirection = 2;
            } else if (crCurScroll < crPrevScroll) {
                //scrolled down
                crDirection = 1;
            }

            if (crDirection !== crPrevDirection) {
                toggleHeader(crDirection, crCurScroll);
            }

            crPrevScroll = crCurScroll;
        };

        var toggleHeader = function (crDirection, crCurScroll) {
            if (crDirection === 2 && crCurScroll > -46) {
                crPrevDirection = crDirection;
                $('#cr-main-menu-desk').addClass('menu_fixed_up');
            } else if (crDirection === 1) {
                crPrevDirection = crDirection;
                $('#cr-main-menu-desk').addClass('menu_fixed');
                $('#cr-main-menu-desk').removeClass('menu_fixed_up');
            }
        };

        // $(window).on('scroll', function () {
        //   var distance = $('.next, .section-breadcrumb').offset().top,
        //     $window = $(window);

        //   if ($window.scrollTop() <= distance + 5) {
        //     $('#cr-main-menu-desk').removeClass('menu_fixed');
        //   } else {
        //     checkScroll();
        //   }
        // });

        /* Service Slider */
        new Swiper('.cr-service-slider', {
            loop: true,
            slidesPerView: 4,
            paginationClickable: true,
            spaceBetween: 24,
            breakpoints: {
                1399: {
                    slidesPerView: 4,
                    spaceBetween: 24,
                },
                1028: {
                    slidesPerView: 3,
                    spaceBetween: 24,
                },
                480: {
                    slidesPerView: 2,
                    spaceBetween: 24,
                },
                0: {
                    slidesPerView: 1,
                    spaceBetween: 10,
                },
            },
        });

        /* Popular Slider */
        $('.cr-popular-product').slick({
            infinite: true,
            dots: false,
            arrows: false,
            slidesToShow: 5,
            slidesToScroll: 1,
            autoplay: true,
            autoplaySpeed: 3000,
            responsive: [
                {
                    breakpoint: 1400,
                    settings: {
                        slidesToShow: 4,
                        infinite: true,
                    },
                },
                {
                    breakpoint: 1200,
                    settings: {
                        slidesToShow: 3,
                        infinite: true,
                    },
                },
                {
                    breakpoint: 992,
                    settings: {
                        slidesToShow: 2,
                        infinite: true,
                    },
                },
                {
                    breakpoint: 481,
                    settings: {
                        slidesToShow: 1,
                    },
                },
            ],
        });

        /* Blog Slider */
        new Swiper('.cr-blog-slider', {
            loop: true,
            slidesPerView: 3,
            paginationClickable: true,
            spaceBetween: 24,
            breakpoints: {
                1600: {
                    slidesPerView: 4,
                    spaceBetween: 24,
                },
                991: {
                    slidesPerView: 3,
                    spaceBetween: 24,
                },
                576: {
                    slidesPerView: 2,
                    spaceBetween: 24,
                },
                0: {
                    slidesPerView: 1,
                    spaceBetween: 10,
                },
            },
        });

        /* Testimonials Slider */
        new Swiper('.cr-testimonial-slider', {
            loop: true,
            slidesPerView: 3,
            paginationClickable: true,
            spaceBetween: 24,
            breakpoints: {
                1028: {
                    slidesPerView: 3,
                    spaceBetween: 24,
                },
                576: {
                    slidesPerView: 2,
                    spaceBetween: 24,
                },
                0: {
                    slidesPerView: 1,
                    spaceBetween: 10,
                },
            },
        });

        /* Banner Slider */
        new Swiper('.cr-banner-slider', {
            loop: true,
            slidesPerView: 2,
            paginationClickable: true,
            spaceBetween: 24,
            autoplay: true,
            breakpoints: {
                1200: {
                    slidesPerView: 3,
                    spaceBetween: 24,
                },
                768: {
                    slidesPerView: 2,
                    spaceBetween: 24,
                },
                0: {
                    slidesPerView: 1,
                    spaceBetween: 10,
                },
            },
        });

        /* Product Slider */
        $('.cr-twocolumns-product').slick({
            infinite: true,
            dots: false,
            arrows: false,
            slidesToShow: 3,
            slidesToScroll: 1,
            autoplay: true,
            autoplaySpeed: 3000,
            responsive: [
                {
                    breakpoint: 1400,
                    settings: {
                        slidesToShow: 2,
                    },
                },
                {
                    breakpoint: 481,
                    settings: {
                        slidesToShow: 1,
                    },
                },
            ],
        });

        /* tablist-swiper */
        var swiper = new Swiper('.tablist-swiper', {
            direction: 'vertical',
            slidesPerView: 6,
        });

        /* Insta slider  */
        new Swiper('.cr-insta-slider', {
            speed: 500,
            spaceBetween: 12,
            autoplay: false,
            disableOnInteraction: true,
            loop: true,
            slidesPerView: 4,
            allowTouchMove: true,
            centeredSlides: false,
            breakpoints: {
                576: {
                    slidesPerView: 5,
                },
                768: {
                    slidesPerView: 6,
                },
                992: {
                    slidesPerView: 8,
                },
                1200: {
                    slidesPerView: 4,
                },
                1400: {
                    slidesPerView: 5,
                },
            },
        });
    });

    /*--------------------- Wishlist notify js ---------------------- */
    $('.wishlist').on('click', function () {
        $('.cr-wish-notify').remove();
        $('.cr-compare-notify').remove();
        $('.cr-cart-notify').remove();

        var isWishlist = $(this).hasClass('active');
        if (isWishlist) {
            $(this).removeClass('active');
            $('footer').after(
                '<div class="cr-wish-notify"><p class="wish-note">Xóa sản phẩm khỏi <a href="wishlist.html"> Yêu thích</a> thành công!</p></div>'
            );
        } else {
            $(this).addClass('active');
            $('footer').after(
                '<div class="cr-wish-notify"><p class="wish-note">Thêm sản phẩm vào <a href="wishlist.html"> Yêu thích</a> thành công!</p></div>'
            );
        }

        setTimeout(function () {
            $('.cr-wish-notify').fadeOut();
        }, 2000);
    });

    /*--------------------- Add to cart button notify js ---------------------- */
    $('.cr-shopping-bag').on('click', function () {
        $('.cr-wish-notify').remove();
        $('.cr-compare-notify').remove();
        $('.cr-cart-notify').remove();

        var isAddtocart = $(this).hasClass('active');
        if (isAddtocart) {
            $(this).removeClass('active');
            $('footer').after(
                '<div class="cr-cart-notify"><p class="compare-note">Remove product in <a href="cart.html"> Cart</a> Successfully!</p></div>'
            );
        } else {
            $(this).addClass('active');
            $('footer').after(
                '<div class="cr-cart-notify"><p class="compare-note">Add product in <a href="cart.html"> Cart</a> Successfully!</p></div>'
            );
        }
        setTimeout(function () {
            $('.cr-cart-notify').fadeOut();
        }, 2000);
    });

    /* Slider room details */
    $('.slider-for').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        fade: true,
        asNavFor: '.slider-nav',
    });
    $('.slider-nav').slick({
        slidesToShow: 5,
        slidesToScroll: 1,
        arrows: false,
        asNavFor: '.slider-for',
        focusOnSelect: true,
        responsive: [
            {
                breakpoint: 1200,
                settings: {
                    slidesToShow: 4,
                },
            },
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 5,
                },
            },
            {
                breakpoint: 420,
                settings: {
                    slidesToShow: 4,
                },
            },
        ],
    });

    /* cart */
    $('.Shopping-toggle').on('click', function (e) {
        e.preventDefault();
        $('.cr-cart-view').addClass('cr-cart-view-active');
        $('.cr-cart-overlay').fadeIn();
    });

    $('.close-cart, .cr-cart-overlay').on('click', function () {
        $('.cr-cart-view').removeClass('cr-cart-view-active');
        $('.cr-cart-overlay').fadeOut();
    });

    /* Banner section ( Home Page ) */
    new Swiper('.cr-slider', {
        loop: true,
        centeredSlides: true,
        speed: 2000,
        effect: 'slide',
        parallax: true,
        autoplay: {
            delay: 10000,
            disableOnInteraction: false,
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    });

    /* Product Image Zoom */
    $('.zoom-image-hover').zoom();

    /* Range Slider */
    $(function () {
        $('#slider-range').slider({
            range: true,
            min: 20,
            max: 300,
            values: [0, 250],
            slide: function (event, ui) {
                $('#amount').val('$' + ui.values[0] + ' - $' + ui.values[1]);
            },
        });
        $('#amount').val(
            '$' +
            $('#slider-range').slider('values', 0) +
            ' - $' +
            $('#slider-range').slider('values', 1)
        );
    });

    /* Tab to top */
    // $(window).scroll(function () {
    //     if ($(this).scrollTop() > 50) {
    //         $('.back-to-top').fadeIn();
    //     } else {
    //         $('.back-to-top').fadeOut();
    //     }
    //
    // });

    /* mobaile menu slider */
    // $('.navbar-toggler').on('click', function () {
    //     $('.cr-sidebar-overlay').fadeIn();
    //     $('.cr-mobile-menu').addClass('cr-menu-open');
    // });
    //
    // $('.cr-sidebar-overlay, .cr-close').on('click', function () {
    //     $('.cr-sidebar-overlay').fadeOut();
    //     $('.cr-mobile-menu').removeClass('cr-menu-open');
    // });
    //
    // $('.drop-list > a').on('click', function () {
    //     $('.sub-menu').slideUp(600);
    //     if ($(this).parent().hasClass('active')) {
    //         $('.drop-list').removeClass('active');
    //         $(this).parent().removeClass('active');
    //     } else {
    //         $('.drop-list').removeClass('active');
    //         $(this).next('.sub-menu').slideDown(600);
    //         $(this).parent().addClass('active');
    //     }
    // });

    /* Footer Toggle  */
    $(document).ready(function () {
        $('.cr-footer-links').addClass('active-drop-footer');

        $('.cr-sub-title').append(
            "<div class='cr-heading-res'><i class='ri-arrow-down-s-line' aria-hidden='true'></i></div>"
        );

        $('.cr-sub-title .cr-heading-res').on('click', function () {
            var $this = $(this)
                .closest('.footer-top .col-sm-12')
                .find('.cr-footer-dropdown');
            $this.slideToggle('slow');
            $('.cr-footer-dropdown').not($this).slideUp('slow');
        });
    });

    /* Counter */
    $('.elementor-counter-number').each(function () {
        var size = $(this).text().split('.')[1]
            ? $(this).text().split('.')[1].length
            : 0;
        $(this)
            .prop('Counter', 0)
            .animate(
                {
                    Counter: $(this).text(),
                },
                {
                    duration: 5000,
                    step: function (func) {
                        $(this).text(parseFloat(func).toFixed(size));
                    },
                }
            );
    });

    /* Deal */
    function makeTimer() {
        var endTime = new Date('29 December 2024 9:56:00 GMT+01:00');
        endTime = Date.parse(endTime) / 1000;
        var now = new Date();
        now = Date.parse(now) / 1000;
        var timeLeft = endTime - now;
        var days = Math.floor(timeLeft / 86400);
        var hours = Math.floor((timeLeft - days * 86400) / 3600);
        var minutes = Math.floor((timeLeft - days * 86400 - hours * 3600) / 60);
        var seconds = Math.floor(
            timeLeft - days * 86400 - hours * 3600 - minutes * 60
        );
        if (hours < '10') {
            hours = '0' + hours;
        }
        if (minutes < '10') {
            minutes = '0' + minutes;
        }
        if (seconds < '10') {
            seconds = '0' + seconds;
        }
        $('#days').html(days);
        $('#hours').html(hours);
        $('#minutes').html(minutes);
        $('#seconds').html(seconds);
    }

    setInterval(function () {
        makeTimer();
    }, 1000);

    /* Products-page model */
    $('.model-oraganic-product').on('click', function () {
        $('.cr-model-overlay').fadeIn();
        $('.cr-model').fadeIn();
    });

    $('.cr-close-model, .cr-model-overlay').on('click', function () {
        $('.cr-model-overlay').fadeOut();
        $('.cr-model').fadeOut();
    });

    /* Shop-side-view */
    $('.shop_side_view').on('click', function (e) {
        e.preventDefault();
        $('.cr-shop-leftside').addClass('cr-shop-leftside-active');
        $('.filter-sidebar-overlay').fadeIn();
    });

    $('.filter-sidebar-overlay, .close-shop-leftside').on('click', function () {
        $('.cr-shop-leftside').removeClass('cr-shop-leftside-active');
        $('.filter-sidebar-overlay').fadeOut();
    });

    /* Potfolio */
    $('.cr-product-tabs ul li').on('click', function () {
        $('ul li').removeClass('active');
        $(this).addClass('active');
    });

    /* Potfolio for Mixit up */
    var portfolioContent = $('.product-content');
    portfolioContent.mixItUp();

    /* Sidebar Tools */
    $('.btn-cr-tool').on('click', function (e) {
        e.preventDefault();
        $('.cr-tool').addClass('cr-tool-active');
        $('.cr-tool-overlay').fadeIn();
        $('.btn-cr-tool').fadeOut();
    });

    $('.close-tools, .cr-tool-overlay').on('click', function () {
        $('.cr-tool').removeClass('cr-tool-active');
        $('.cr-tool-overlay').fadeOut();
        $('.btn-cr-tool').fadeIn();
    });

    $('.cr-color li').on('click', function () {
        $('li').removeClass('active-colors');
        $(this).addClass('active-colors');
    });

    /* color show */
    $('.c1').on('click', function () {
        $('#add_class').remove();
    });
    $('.c2').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-1.css" id="add_class">'
        );
    });
    $('.c3').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-2.css" id="add_class">'
        );
    });
    $('.c4').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-3.css" id="add_class">'
        );
    });
    $('.c5').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-4.css" id="add_class">'
        );
    });
    $('.c6').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-5.css" id="add_class">'
        );
    });
    $('.c7').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-6.css" id="add_class">'
        );
    });
    $('.c8').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-7.css" id="add_class">'
        );
    });
    $('.c9').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-8.css" id="add_class">'
        );
    });
    $('.c10').on('click', function () {
        $('#add_class').remove();
        $('head').append(
            '<link rel="stylesheet" href="assets/css/color-9.css" id="add_class">'
        );
    });

    $('.search-btn').click(function (event) {
        var inputVal = $('.search-input').val();
        if (inputVal.trim() === '') {
            alert('Vui lòng nhập thông tin tìm kiếm');
            event.preventDefault();
        }
    });

    const orderStatusTabs = $('li[data-tab]');
    const orderList = $('div[data-tab]');

    orderStatusTabs.on('click', function () {
        const tab = $(this).data('tab');

        orderStatusTabs.removeClass('active');
        $(this).addClass('active');

        orderList.each(function () {
            if (tab === 'all' || $(this).data('tab') === tab) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

    // console.log($)
    [...document.querySelectorAll('.price-value')].forEach(el => {

        const formattedValue = formatCurrencyVND(el.textContent.trim());
        if (el.classList.contains('minus-value')) {
            el.innerHTML = `-${formattedValue}`;
        } else {
            el.innerHTML = `${formattedValue}`;
        }
    });

    function formatCurrencyVND (value) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        }).format(value);
    }

})(jQuery);