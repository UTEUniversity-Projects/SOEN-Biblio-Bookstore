<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Cart -->
<section class="section-cart padding-t-100">
    <div class="container-xl">
        <div class="row">
            <div class="col-12">
                <div
                        class="cr-cart-content"
                        data-aos="fade-up"
                        data-aos-duration="2000"
                        data-aos-delay="400"
                >
                    <div class="row">
                        <c:choose>
                            <c:when test="${empty cart.cartItems}">
                                <div class="message-container">
                                    <img src="https://cdn-icons-png.flaticon.com/512/2762/2762885.png" alt="">
                                    <p>Giỏ hàng của bạn đang trống</p>
                                    <a href="${pageContext.request.contextPath}/home">
                                        <button class="cr-button">Mua ngay</button>
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <form action="#">
                                    <div class="cr-table-content">
                                        <table>
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>Sách</th>
                                                <th>Giá</th>
                                                <th class="text-center">Số lượng</th>
                                                <th>Tổng</th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="cartItem" items="${cart.cartItems}">
                                                <tr data-cart-item-id="${cartItem.id}" data-product-id="${cartItem.bookId}">
                                                    <td class="cr-cart-checkbox">
                                                        <input type="checkbox" class="product-checkbox" />
                                                    </td>
                                                    <td class="cr-cart-name">
                                                        <a href="${pageContext.request.contextPath}/book?id=${cartItem.bookId}">
                                                            <img
                                                                    src="${pageContext.request.contextPath}${cartItem.imageUrl}"
                                                                    alt="product-1"
                                                                    class="cr-cart-img"
                                                            />
                                                                ${cartItem.title}
                                                        </a>
                                                    </td>
                                                    <td class="cr-cart-price">
                                                        <c:choose>
                                                            <c:when test="${cartItem.sellingPrice == cartItem.salePrice}">
                                                                <span class="new-price price-value">${cartItem.sellingPrice}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="new-price price-value">${cartItem.salePrice}</span>
                                                                <span class="old-price price-value">${cartItem.sellingPrice}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="cr-cart-qty">
                                                        <div class="cart-qty-plus-minus">
                                                            <button type="button" class="minus">-</button>
                                                            <input
                                                                    type="text"
                                                                    value="${cartItem.quantity}"
                                                                    minlength="1"
                                                                    maxlength="20"
                                                                    class="quantity"
                                                            />
                                                            <button type="button" class="plus">+</button>
                                                        </div>
                                                    </td>
                                                    <td class="cr-cart-subtotal price-value">${cartItem.subTotal}</td>
                                                    <td class="cr-cart-remove">
                                                        <a href="javascript:void(0)" class="remove-item">
                                                            <i class="ri-delete-bin-line"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="cr-cart-summary">
                                        <div class="summary-sub-total">
                                            <table class="table summary-table">
                                                <tbody>
                                                <tr>
                                                    <td>Tổng tiền :</td>
                                                    <td class="total pice-value">0</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="cr-cart-update-bottom">
                                                <a href="${pageContext.request.contextPath}/home" class="cr-btn-secondary">Tiếp tục mua sách</a>

                                                <a id="btn-checkout" class="cr-button cursor-pointer">Thanh toán</a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Popular products -->
<section
        class="section-popular-products padding-tb-100"
        data-aos="fade-up"
        data-aos-duration="2000"
        data-aos-delay="400"
>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="mb-30">
                    <div class="cr-banner">
                        <h2>Các cuốn sách nổi tiếng</h2>
                    </div>
                    <div class="cr-banner-sub-title">
                        <p>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
                            do eiusmod tempor incididunt ut labore et viverra maecenas
                            accumsan lacus vel facilisis.
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="cr-popular-product">
                    <div class="slick-slide">
                        <div class="cr-product-card">
                            <div class="cr-product-image">
                                <div class="cr-image-inner zoom-image-hover">
                                    <img src="${pageContext.request.contextPath}/assets/customer/img/product/9.jpg" alt="product-1" />
                                </div>
                                <div class="cr-side-view">
                                    <a href="javascript:void(0)" class="wishlist">
                                        <i class="ri-heart-line"></i>
                                    </a>
                                    <a
                                            class="model-oraganic-product"
                                            data-bs-toggle="modal"
                                            href="#quickview"
                                            role="button"
                                    >
                                        <i class="ri-eye-line"></i>
                                    </a>
                                </div>
                                <a class="cr-shopping-bag" href="javascript:void(0)">
                                    <i class="ri-shopping-bag-line"></i>
                                </a>
                            </div>
                            <div class="cr-product-details">
                                <div class="cr-brand">
                                    <a href="shop-left-sidebar.html">Snacks</a>
                                    <div class="cr-star">
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-line"></i>
                                        <p>(4.5)</p>
                                    </div>
                                </div>
                                <a href="product" class="title"
                                >Best snakes with hazel nut mix pack 200gm</a
                                >
                                <p class="cr-price">
                                    <span class="new-price">$120.25</span>
                                    <span class="old-price">$123.25</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="slick-slide">
                        <div class="cr-product-card">
                            <div class="cr-product-image">
                                <div class="cr-image-inner zoom-image-hover">
                                    <img src="${pageContext.request.contextPath}/assets/customer/img/product/10.jpg" alt="product-1" />
                                </div>
                                <div class="cr-side-view">
                                    <a href="javascript:void(0)" class="wishlist">
                                        <i class="ri-heart-line"></i>
                                    </a>
                                    <a
                                            class="model-oraganic-product"
                                            data-bs-toggle="modal"
                                            href="#quickview"
                                            role="button"
                                    >
                                        <i class="ri-eye-line"></i>
                                    </a>
                                </div>
                                <a class="cr-shopping-bag" href="javascript:void(0)">
                                    <i class="ri-shopping-bag-line"></i>
                                </a>
                            </div>
                            <div class="cr-product-details">
                                <div class="cr-brand">
                                    <a href="shop-left-sidebar.html">Snacks</a>
                                    <div class="cr-star">
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <p>(5.0)</p>
                                    </div>
                                </div>
                                <a href="product" class="title"
                                >Sweet snakes crunchy nut mix 250gm pack</a
                                >
                                <p class="cr-price">
                                    <span class="new-price">$100.00</span>
                                    <span class="old-price">$110.00</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="slick-slide">
                        <div class="cr-product-card">
                            <div class="cr-product-image">
                                <div class="cr-image-inner zoom-image-hover">
                                    <img src="${pageContext.request.contextPath}/assets/customer/img/product/1.jpg" alt="product-1" />
                                </div>
                                <div class="cr-side-view">
                                    <a href="javascript:void(0)" class="wishlist">
                                        <i class="ri-heart-line"></i>
                                    </a>
                                    <a
                                            class="model-oraganic-product"
                                            data-bs-toggle="modal"
                                            href="#quickview"
                                            role="button"
                                    >
                                        <i class="ri-eye-line"></i>
                                    </a>
                                </div>
                                <a class="cr-shopping-bag" href="javascript:void(0)">
                                    <i class="ri-shopping-bag-line"></i>
                                </a>
                            </div>
                            <div class="cr-product-details">
                                <div class="cr-brand">
                                    <a href="shop-left-sidebar.html">Snacks</a>
                                    <div class="cr-star">
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-line"></i>
                                        <p>(4.5)</p>
                                    </div>
                                </div>
                                <a href="product" class="title"
                                >Best snakes with hazel nut mix pack 200gm</a
                                >
                                <p class="cr-price">
                                    <span class="new-price">$120.25</span>
                                    <span class="old-price">$123.25</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="slick-slide">
                        <div class="cr-product-card">
                            <div class="cr-product-image">
                                <div class="cr-image-inner zoom-image-hover">
                                    <img src="${pageContext.request.contextPath}/assets/customer/img/product/2.jpg" alt="product-1" />
                                </div>
                                <div class="cr-side-view">
                                    <a href="javascript:void(0)" class="wishlist">
                                        <i class="ri-heart-line"></i>
                                    </a>
                                    <a
                                            class="model-oraganic-product"
                                            data-bs-toggle="modal"
                                            href="#quickview"
                                            role="button"
                                    >
                                        <i class="ri-eye-line"></i>
                                    </a>
                                </div>
                                <a class="cr-shopping-bag" href="javascript:void(0)">
                                    <i class="ri-shopping-bag-line"></i>
                                </a>
                            </div>
                            <div class="cr-product-details">
                                <div class="cr-brand">
                                    <a href="shop-left-sidebar.html">Snacks</a>
                                    <div class="cr-star">
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <p>(5.0)</p>
                                    </div>
                                </div>
                                <a href="product" class="title"
                                >Sweet snakes crunchy nut mix 250gm pack</a
                                >
                                <p class="cr-price">
                                    <span class="new-price">$100.00</span>
                                    <span class="old-price">$110.00</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="slick-slide">
                        <div class="cr-product-card">
                            <div class="cr-product-image">
                                <div class="cr-image-inner zoom-image-hover">
                                    <img src="${pageContext.request.contextPath}/assets/customer/img/product/3.jpg" alt="product-1" />
                                </div>
                                <div class="cr-side-view">
                                    <a href="javascript:void(0)" class="wishlist">
                                        <i class="ri-heart-line"></i>
                                    </a>
                                    <a
                                            class="model-oraganic-product"
                                            data-bs-toggle="modal"
                                            href="#quickview"
                                            role="button"
                                    >
                                        <i class="ri-eye-line"></i>
                                    </a>
                                </div>
                                <a class="cr-shopping-bag" href="javascript:void(0)">
                                    <i class="ri-shopping-bag-line"></i>
                                </a>
                            </div>
                            <div class="cr-product-details">
                                <div class="cr-brand">
                                    <a href="shop-left-sidebar.html">Snacks</a>
                                    <div class="cr-star">
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <i class="ri-star-fill"></i>
                                        <p>(5.0)</p>
                                    </div>
                                </div>
                                <a href="product" class="title"
                                >Sweet snakes crunchy nut mix 250gm pack</a
                                >
                                <p class="cr-price">
                                    <span class="new-price">$100.00</span>
                                    <span class="old-price">$110.00</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>