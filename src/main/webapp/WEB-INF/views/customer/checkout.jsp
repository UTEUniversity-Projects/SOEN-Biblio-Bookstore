<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    .disabled-input {
        background-color: #f0f0f0;
        border: 1px solid #ccc;
        color: #999;
        cursor: not-allowed;
    }

    .disabled-button {
        background-color: #ddd;
        border: 1px solid #ccc;
        color: #999;
        cursor: not-allowed;
    }
</style>

<section class="max-w-[1200px] mx-auto mt-5">
    <h3
            class="text-3xl uppercase font-bold bg-[#f5f5f5] px-4 py-2 rounded text-[#26a397]"
    >
        Đặt hàng
    </h3>
    <div class="content-container">
        <div class="w-full mt-5">
            <div class="flex bg-[#f5f5f5] px-2 py-2">
                <div class="col-sm-6">
                    <p class="text-md font-medium text-center text-[#26a397]">Sách</p>
                </div>
                <div class="col-sm-2 text-center">
                    <p class="text-md font-medium text-[#26a397]">Đơn giá</p>
                </div>
                <div class="col-sm-2 text-center">
                    <p class="text-md font-medium text-[#26a397]">Số lượng</p>
                </div>
                <div class="col-sm-2 text-center">
                    <p class="text-md font-medium text-[#26a397]">Thành tiền</p>
                </div>
            </div>
            <div class="bg-[#f5f5f5] rounded">
                <c:forEach var="item" items="${checkoutResponse.items}">
                    <a
                            class="book-item flex rounded items-center hover:bg-gray-200 p-2 transition-all duration-300"
                            href="${pageContext.request.contextPath}/book?id=${item.bookTemplateId}"
                    >
                        <div class="col-sm-6">
                            <div class="flex items-center gap-x-5 mr-5">
                                <div class="w-[100px] h-[100px] flex-shrink-0">
                                    <img
                                            class="w-full h-full object-cover"
                                            src="${pageContext.request.contextPath}${item.imagePath}"
                                            alt="Product"
                                    />
                                </div>
                                <div style="margin-left: 25px">
                                    <span style="font-weight: 500">${item.title}</span>
                                    <p class="discount-percent w-fit my-[5px]">${item.discountPercent}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-2 text-center">
                            <p class="price price-value">${item.sellingPrice}</p>
                        </div>
                        <div class="col-sm-2 text-center">
                            <span class="quantity">${item.quantity}</span>
                        </div>
                        <div class="col-sm-2 text-center">
                            <p class="total price-value">${item.totalPrice}</p>
                        </div>
                    </a>
                </c:forEach>
            </div>
            <div class="mt-5 bg-[#f5f5f5] px-4 py-2 rounded">
                <div class="w-full">
                    <h3 class="text-2xl font-bold text-[#26a397]">Thông tin giao hàng</h3>
                </div>
                <div class="w-full flex justify-between items-center">
                    <div style="margin-top: 10px" class="information">
                        <span class="fullname font-bold text-[16px] mr-2">
                            ${checkoutResponse.customer.fullName} | ${checkoutResponse.customer.phoneNumber}
                        </span>
                        <div>
                            <span class="address" data-address-id="${checkoutResponse.shipping.address.id}">
                                ${checkoutResponse.shipping.address.fullAddress}
                            </span>
                        </div>
                    </div>
                    <button
                            class="change-address text-white py-1 px-2 bg-[#26a397] rounded hover:bg-[#63b597] transition-all duration-300 ml-auto"
                    >
                        Thay đổi
                    </button>
                </div>
            </div>
            <div
                    class="mt-5 bg-[#f5f5f5] flex justify-between items-center px-10 py-2 rounded"
            >
                <div class="col-sm-6">
                    <div class="flex gap-x-5 items-center">
                        <svg
                                fill="none"
                                viewBox="0 -2 23 22"
                                class="fill-[#26a397] h-[50px] w-[50px]"
                        >
                            <g filter="url(#voucher-filter0_d)">
                                <mask id="a" fill="#fff">
                                    <path
                                            fill-rule="evenodd"
                                            clip-rule="evenodd"
                                            d="M1 2h18v2.32a1.5 1.5 0 000 2.75v.65a1.5 1.5 0 000 2.75v.65a1.5 1.5 0 000 2.75V16H1v-2.12a1.5 1.5 0 000-2.75v-.65a1.5 1.5 0 000-2.75v-.65a1.5 1.5 0 000-2.75V2z"
                                    ></path>
                                </mask>
                                <path
                                        d="M19 2h1V1h-1v1zM1 2V1H0v1h1zm18 2.32l.4.92.6-.26v-.66h-1zm0 2.75h1v-.65l-.6-.26-.4.91zm0 .65l.4.92.6-.26v-.66h-1zm0 2.75h1v-.65l-.6-.26-.4.91zm0 .65l.4.92.6-.26v-.66h-1zm0 2.75h1v-.65l-.6-.26-.4.91zM19 16v1h1v-1h-1zM1 16H0v1h1v-1zm0-2.12l-.4-.92-.6.26v.66h1zm0-2.75H0v.65l.6.26.4-.91zm0-.65l-.4-.92-.6.26v.66h1zm0-2.75H0v.65l.6.26.4-.91zm0-.65l-.4-.92-.6.26v.66h1zm0-2.75H0v.65l.6.26.4-.91zM19 1H1v2h18V1zm1 3.32V2h-2v2.32h2zm-.9 1.38c0-.2.12-.38.3-.46l-.8-1.83a2.5 2.5 0 00-1.5 2.29h2zm.3.46a.5.5 0 01-.3-.46h-2c0 1.03.62 1.9 1.5 2.3l.8-1.84zm.6 1.56v-.65h-2v.65h2zm-.9 1.38c0-.2.12-.38.3-.46l-.8-1.83a2.5 2.5 0 00-1.5 2.29h2zm.3.46a.5.5 0 01-.3-.46h-2c0 1.03.62 1.9 1.5 2.3l.8-1.84zm.6 1.56v-.65h-2v.65h2zm-.9 1.38c0-.2.12-.38.3-.46l-.8-1.83a2.5 2.5 0 00-1.5 2.29h2zm.3.46a.5.5 0 01-.3-.46h-2c0 1.03.62 1.9 1.5 2.3l.8-1.84zM20 16v-2.13h-2V16h2zM1 17h18v-2H1v2zm-1-3.12V16h2v-2.12H0zm1.4.91a2.5 2.5 0 001.5-2.29h-2a.5.5 0 01-.3.46l.8 1.83zm1.5-2.29a2.5 2.5 0 00-1.5-2.3l-.8 1.84c.18.08.3.26.3.46h2zM0 10.48v.65h2v-.65H0zM.9 9.1a.5.5 0 01-.3.46l.8 1.83A2.5 2.5 0 002.9 9.1h-2zm-.3-.46c.18.08.3.26.3.46h2a2.5 2.5 0 00-1.5-2.3L.6 8.65zM0 7.08v.65h2v-.65H0zM.9 5.7a.5.5 0 01-.3.46l.8 1.83A2.5 2.5 0 002.9 5.7h-2zm-.3-.46c.18.08.3.26.3.46h2a2.5 2.5 0 00-1.5-2.3L.6 5.25zM0 2v2.33h2V2H0z"
                                        mask="url(#a)"
                                ></path>
                            </g>
                            <path
                                    clip-rule="evenodd"
                                    d="M6.49 14.18h.86v-1.6h-.86v1.6zM6.49 11.18h.86v-1.6h-.86v1.6zM6.49 8.18h.86v-1.6h-.86v1.6zM6.49 5.18h.86v-1.6h-.86v1.6z"
                            ></path>
                            <defs>
                                <filter
                                        id="voucher-filter0_d"
                                        x="0"
                                        y="1"
                                        width="20"
                                        height="16"
                                        filterUnits="userSpaceOnUse"
                                        color-interpolation-filters="sRGB"
                                >
                                    <feFlood
                                            flood-opacity="0"
                                            result="BackgroundImageFix"
                                    ></feFlood>
                                    <feColorMatrix
                                            in="SourceAlpha"
                                            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
                                    ></feColorMatrix>
                                    <feOffset></feOffset>
                                    <feGaussianBlur stdDeviation=".5"></feGaussianBlur>
                                    <feColorMatrix
                                            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.09 0"
                                    ></feColorMatrix>
                                    <feBlend
                                            in2="BackgroundImageFix"
                                            result="effect1_dropShadow"
                                    ></feBlend>
                                    <feBlend
                                            in="SourceGraphic"
                                            in2="effect1_dropShadow"
                                            result="shape"
                                    ></feBlend>
                                </filter>
                            </defs>
                        </svg>
                        <span>Biblio Voucher</span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="flex items-center ml-[50px] justify-end gap-x-[30px] mt-5">
                        <c:set var="hasFreestip" value="false"/>
                        <c:set var="freeshipCode" value=""/>

                        <c:forEach var="promotion" items="${checkoutResponse.promotions}">
                            <c:if test="${promotion.promotionType == 'FREESHIP'}">
                                <c:set var="hasFreestip" value="true"/>
                                <c:set var="freeshipCode" value="${promotion}"/>
                            </c:if>
                        </c:forEach>

                        <c:if test="${not hasFreestip}">
                            <input type="text" id="freeship" placeholder="Nhập mã freeship...."
                                  class="px-2 py-2 rounded bg-white focus:ring-blue-500 focus:border-blue-500 border-1 border-gray-300 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px]">
                            <button id="applyFreeship"
                                    class="px-4 py-2 rounded bg-[#26a397] hover:bg-[#63b597] transition-all duration-300 text-white text-md">
                                Áp dụng
                            </button>
                        </c:if>

                        <c:if test="${hasFreestip}">
                            <input type="text" id="freeship" placeholder="Nhập mã freeship...."
                                   class="px-2 py-2 rounded bg-white focus:ring-blue-500 focus:border-blue-500 border-1 border-gray-300 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px] disabled-input"
                                   value="${freeshipCode.code}" readonly/>
                            <button id="applyFreeship"
                                    class="px-4 py-2 rounded bg-[#26a397] hover:bg-[#63b597] transition-all duration-300 text-white text-md disabled-button"
                                    disabled>
                                Áp dụng
                            </button>
                        </c:if>
                    </div>
                    <div class="flex items-center ml-[50px] justify-end gap-x-[30px] mt-5">
                        <c:set var="hasVoucher" value="false"/>
                        <c:set var="voucherCode" value=""/>

                        <c:forEach var="promotion" items="${checkoutResponse.promotions}">
                            <c:if test="${promotion.promotionType == 'VOUCHER'}">
                                <c:set var="hasVoucher" value="true"/>
                                <c:set var="voucherCode" value="${promotion}"/>
                            </c:if>
                        </c:forEach>

                        <c:if test="${not hasVoucher}">
                            <input type="text" id="voucher" placeholder="Nhập mã voucher...."
                                   class="px-2 py-2 rounded bg-white focus:ring-blue-500 focus:border-blue-500 border-1 border-gray-300 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px]">
                            <button id="applyVoucher"
                                    class="px-4 py-2 rounded bg-[#26a397] hover:bg-[#63b597] transition-all duration-300 text-white text-md">
                                Áp dụng
                            </button>
                        </c:if>

                        <c:if test="${hasVoucher}">
                            <input type="text" id="voucher" placeholder="Nhập mã voucher...."
                                   class="px-2 py-2 rounded bg-white focus:ring-blue-500 focus:border-blue-500 border-1 border-gray-300 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px] disabled-input"
                                   value="${voucherCode.code}" readonly/>
                            <button id="applyVoucher"
                                    class="px-4 py-2 rounded bg-[#26a397] hover:bg-[#63b597] transition-all duration-300 text-white text-md disabled-button"
                                    disabled>
                                Áp dụng
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="mt-5 bg-[#f5f5f5] rounded px-4 py-4 mb-5">
                <h3 class="text-xl text-[#26a397] font-medium">
                    Phương thức thanh toán
                </h3>
                <div class="flex items-center mx-auto justify-evenly h-10 mt-5 mb-5">
                    <div
                            class="payment-method px-4 py-2 text-white hover:bg-gray-50 rounded cursor-pointer bg-white transition-all duration-300 h-full"
                    >
                        <img
                                class="w-full h-full object-cover scale-[1.2]"
                                src="https://res.cloudinary.com/taskmanagereaglob123/image/upload/v1641970995/VietQR.46a78cbb_utwzzh.png"
                        />
                    </div>
                    <div
                            class="payment-method px-4 py-2 text-white hover:bg-[#a50164] rounded cursor-pointer bg-[#a50164] transition-all duration-300 h-full"
                    >
                        <img
                                class="w-full h-full object-cover scale-[1.5]"
                                src="https://static.ybox.vn/2022/4/4/1650508432111-tut.png"
                        />
                    </div>
                    <div
                            class="payment-method px-4 py-2 text-white rounded cursor-pointer transition-all duration-300 h-full"
                    >
                        <img
                                class="w-full h-full object-cover scale-[1]"
                                src="https://cdn.haitrieu.com/wp-content/uploads/2022/10/Logo-VNPAY-QR.png"
                        />
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div>
        <div class="mb-2 w-[30%] ml-auto">
            <label for="note" class="block text-md font-medium text-gray-900 ml-2">Lời nhắn: </label>
            <textarea id="note" rows="4"
                      class="block p-2.5 w-full text-md text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
                      placeholder="Nhập lời nhắn..."></textarea>
        </div>
        <div class="summary-sub-total w-[30%] ml-auto mb-5">
            <table class="table summary-table">
                <h3 class="text-2xl font-bold text-[#26a397]">Tóm tắt đơn hàng</h3>
                <tbody>
                <tr>
                    <td>Tổng tiền sách:</td>
                    <td class="price-value"
                        data-price="${checkoutResponse.totalPrice}">${checkoutResponse.totalPrice}</td>
                </tr>
                <tr>
                    <td>Phí vận chuyển:</td>
                    <td id="price-freeship-amount" class="price-value"
                        data-price="${checkoutResponse.shipping.shippingFee}">${checkoutResponse.shipping.shippingFee}</td>
                </tr>

                <tr>
                    <td>Giảm giá phí vận chuyển:</td>
                    <c:if test="${hasFreestip}">
                        <td id="price-freeship" class="price-value minus-value"
                            data-price="${freeshipCode.discountAmount}">
                                ${freeshipCode.discountAmount}
                        </td>
                    </c:if>
                    <c:if test="${not hasFreestip}">
                        <td id="price-freeship" class="price-value minus-value"
                            data-price="0">
                            0
                        </td>
                    </c:if>
                </tr>
                <tr>
                <tr>
                    <td>Voucher giảm giá:</td>
                    <c:if test="${hasVoucher}">
                        <td id="price-voucher" class="price-value minus-value"
                            data-price="${voucherCode.discountAmount}">
                                ${voucherCode.discountAmount}
                        </td>
                    </c:if>
                    <c:if test="${not hasVoucher}">
                        <td id="price-voucher" class="price-value minus-value"
                            data-price="0">
                            0
                        </td>
                    </c:if>
                </tr>
                <tr>
                    <td>Phương thức thanh toán:</td>
                    <td><span class="payment-type"></span></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div style="width: 100%; height: 1px; background-color: #ccc; margin-top: 10px;"></div>
                    </td>
                </tr>
                <tr>
                    <td>Tổng tiền:</td>
                    <td id="price-total" class="price-value"
                        data-price="${checkoutResponse.finalPrice}">${checkoutResponse.finalPrice}</td>
                </tr>
                </tbody>
            </table>
            <div class="flex justify-end">
                <button
                        style="margin: 10px 0;"
                        class="btn-create-order px-4 py-2 rounded bg-[#26a397] hover:bg-[#63b597] transition-all duration-300 text-white text-md w-[50%]"
                >
                    Đặt hàng
                </button>
            </div>
        </div>
    </div>
</section>
<div class="modal-address hidden z-[999]">
    <div class="modal-address-overlay absolute inset-0 bg-black/20"></div>
    <div class="modal-address-content absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-white rounded px-4 py-2">
        <h3 class="text-center font-semibold text-xl mb-2">Thay đổi địa chỉ</h3>
        <hr/>
        <div class="py-2">
            <c:forEach var="address" items="${checkoutResponse.customer.addresses}">
                <div class="cursor-pointer">
                    <input type="radio"
                           class="mr-2 cursor-pointer" ${checkoutResponse.shipping.address.fullAddress == address.getFullAddress() ? 'checked' : ''}
                           name="address-id" id="${address.id}">
                    <label class="cursor-pointer" for="${address.id}">${address.getFullAddress()}</label>
                </div>
            </c:forEach>
        </div>
        <div class="flex justify-end">
            <button class="btn-choose-address btn btn-success rounded">Chọn</button>
        </div>
    </div>
</div>

<div class="checkout">
    <div class="checkout-overlay bg-black/20"></div>
    <div class="checkout-qr h-[400px] rounded-lg overflow-hidden transition-all ease-linear duration-300">
        <img class="w-full h-full object-cover" src="" alt="">
    </div>
    <div class="checkout-loading hidden absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 z-[1000]">
        <div class="checkout-loading">
            <div class="book-wrapper">
                <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="white"
                        viewBox="0 0 126 75"
                        class="book"
                >
                    <rect
                            stroke-width="5"
                            stroke="#e05452"
                            rx="7.5"
                            height="70"
                            width="121"
                            y="2.5"
                            x="2.5"
                    ></rect>
                    <line
                            stroke-width="5"
                            stroke="#e05452"
                            y2="75"
                            x2="63.5"
                            x1="63.5"
                    ></line>
                    <path
                            stroke-linecap="round"
                            stroke-width="4"
                            stroke="#c18949"
                            d="M25 20H50"
                    ></path>
                    <path
                            stroke-linecap="round"
                            stroke-width="4"
                            stroke="#c18949"
                            d="M101 20H76"
                    ></path>
                    <path
                            stroke-linecap="round"
                            stroke-width="4"
                            stroke="#c18949"
                            d="M16 30L50 30"
                    ></path>
                    <path
                            stroke-linecap="round"
                            stroke-width="4"
                            stroke="#c18949"
                            d="M110 30L76 30"
                    ></path>
                </svg>

                <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="#ffffff74"
                        viewBox="0 0 65 75"
                        class="book-page"
                >
                    <path
                            stroke-linecap="round"
                            stroke-width="4"
                            stroke="#c18949"
                            d="M40 20H15"
                    ></path>
                    <path
                            stroke-linecap="round"
                            stroke-width="4"
                            stroke="#c18949"
                            d="M49 30L15 30"
                    ></path>
                    <path
                            stroke-width="5"
                            stroke="#e05452"
                            d="M2.5 2.5H55C59.1421 2.5 62.5 5.85786 62.5 10V65C62.5 69.1421 59.1421 72.5 55 72.5H2.5V2.5Z"
                    ></path>
                </svg>
            </div>
        </div>

    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/commons/js/format-discount-percent.js"></script>
<script src="${pageContext.request.contextPath}/assets/customer/js/checkout.js" type="module"></script>
