import { debounce } from '../../commons/js/debounce.js';
import { toast } from './toast.js';
import { formatCurrencyVND } from '../../commons/js/format-currency.js';

$(document).ready(function () {
	// Add
	$(document).on('click', '.add-to-cart-btn', function () {
		var $this = $(this);
		$this.prop('disabled', true);
		const bookId = $(this).closest('.cr-product-card, .modal, .section-product').data("book-id");
		const quantity = $(this).data("quantity") || $(this).closest(".cr-add-card").find(".cr-qty-main .quantity").val();

		console.log({
			bookId: bookId,
			quantity: quantity
		});
		$.ajax({
			url: `${contextPath}/api/customer/add-cart-item`,
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				bookTemplateId: bookId,
				quantity: quantity
			}),
			success: function (response) {
				if (response.code === 200) {
					toast({
						title: 'Thông báo',
						message: `Thêm ${quantity} sản phẩm thành công !`,
						type: 'success',
						duration: 1000
					});
					addTooCartAnimation();
					countCartItem();
				} else {
					setTimeout(() => {
						window.location.href = `${contextPath}/login`;
					});
				}
			},
			error: function (xhr, status, error) {
				console.error('Error: ', xhr.responseText);
			},
			complete: function () {
				$this.prop('disabled', false);
			}
		});
	});
	// Load
	$('#view-cart-btn').on('click', function () {

		const cartItemsContainer = $('.crcart-pro-items');
		$('.cart-loading').removeClass('hidden');
		$('.cr-cart-top').addClass('hidden');
		$('.view-cart').addClass('hidden');

		$.ajax({
			url: `${contextPath}/api/customer/load-cart-sidebar`,
			type: 'GET',
			success: function (response) {

				cartItemsContainer.empty();

				if (response.cart && response.cart.cartItems && response.cart.cartItems.length > 0) {
					response.cart.cartItems.forEach(cartItem => {
						const itemHTML = `
                         <li data-cart-item-id="${cartItem.id}">
                             <a href="${contextPath}/book?id=${cartItem.bookId}" class="crside_pro_img">
                                 <img src="${contextPath}${cartItem.imageUrl}" alt="${cartItem.title}" />
                             </a>
                             <div class="cr-pro-content">
                                 <a href="${contextPath}/book?id=${cartItem.bookId}" class="cart_pro_title">
                                     ${cartItem.title}
                                 </a>
                                 <span class="cart-price">
                               		${cartItem.salePrice === cartItem.sellingPrice
									? `<span class="new-price price-value">${formatCurrencyVND(cartItem.sellingPrice)}</span>`
									: `<span class="new-price price-value">${formatCurrencyVND(cartItem.salePrice)}</span>
			   						<span class="old-price price-value">${formatCurrencyVND(cartItem.sellingPrice)}</span>`}
                                 </span>
                                 <div class="cr-cart-qty">
                                     <div class="cart-qty-plus-minus">
                                         <button type="button" class="minus">-</button>
                                         <input
                                             type="text"
                                             value="${cartItem.quantity}"
                                             class="quantity"
                                             minlength="1"
                                             maxlength="20"
                                         />
                                         <button type="button" class="plus">+</button>
                                     </div>
                                 </div>
                                 <a href="javascript:void(0)" class="remove-item">×</a>
                             </div>
                         </li>
                     `;
						cartItemsContainer.append(itemHTML);
					});
					$('.view-cart').removeClass('hidden');
				} else {
					cartItemsContainer.append(`<div class="message-container mt-[50%]">
                                    <img src="https://cdn-icons-png.flaticon.com/512/2762/2762885.png" alt="">
                                    <p>Giỏ hàng của bạn đang trống</p>
                                    <a href="home">
                                        <button class="cr-button">Mua ngay</button>
                                    </a>
                                </div>`);
				}
			},
			error: function (xhr, status, error) {

			},
			complete: function () {
				$('.cart-loading').addClass('hidden');
				$('.cr-cart-top').removeClass('hidden');
			}
		});
	});
	// Update
	$(document).on('change', '.cr-cart-qty .quantity', debounce(function () {
		const $input = $(this);
		const newQuantity = $input.val();
		const $row = $input.closest('tr, li');
		const cartItemId = $row.data('cart-item-id');
		console.log(cartItemId);
		console.log(newQuantity);

		$.ajax({
			url: `${contextPath}/api/customer/update-cart-item`,
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				cartItemId: cartItemId,
				quantity: newQuantity
			}),
			success: function (response) {
				$row.find('.cr-cart-subtotal').text(formatCurrencyVND(response.cartItem.subTotal));
				calTotal();
			},
			error: function (xhr, status, error) {
				console.error('Error: ', xhr.responseText);
			}
		});
	}, 500));
	// Delete
	$(document).on('click', '.remove-item', function () {
		const cartItemId = $(this).closest('li, tr').data('cart-item-id');
		const item = $(this).closest('li, tr');
		const container = $(this).closest('.crcart-pro-items, .row');
		const parent = item.closest('tbody, ul');
		console.log(cartItemId);
		$.ajax({
			url: `${contextPath}/api/customer/delete-cart-item`,
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				cartItemId: cartItemId
			}),
			success: function (response) {
				if (parent.children().length === 1) {
					container.empty();
					container.append('<div class="message-container mt-[50%]">\n' +
						'                                    <img src="https://cdn-icons-png.flaticon.com/512/2762/2762885.png" alt="">\n' +
						'                                    <p>Giỏ hàng của bạn đang trống</p>\n' +
						'                                    <a href="home">\n' +
						'                                        <button class="cr-button">Mua ngay</button>\n' +
						'                                    </a>\n' +
						'                                </div>');
					$('.view-cart').addClass('hidden');
				} else {
					item.remove();
				}
				toast({
					title: 'Thông báo',
					message: 'Xoá sản phẩm thành công !',
					type: 'success',
					duration: 1000
				});
				countCartItem();
			},
			error: function (xhr, status, error) {
				console.error('Error: ', xhr.responseText);
			}
		});
	});
	countCartItem();
});

function countCartItem () {
	$.ajax({
		url: `${contextPath}/api/customer/count-cart-item`,
		type: 'GET',
		success: function (response) {
			const $cart = $('.cart');
			$cart.find('.cart-item-count').text(response.count);
		},
		error: function (xhr, status, error) {
			console.error('Error: ', xhr.responseText);
		}
	});
}

function addTooCartAnimation () {
	const cart = $('.cart i');
	setTimeout(() => {
		cart.addClass('shake');
		setTimeout(() => {
			cart.removeClass('shake');
		}, 1000);
	}, 1000);
}

$(document).on('change', '.cr-table-content .cr-cart-checkbox, .cr-table-content .quantity', function() {
	let total = 0;
	$('.cr-table-content tbody tr').each(function() {
		const $checkBox = $(this).find('.cr-cart-checkbox input[type="checkbox"]');
		const $priceElement = $(this).find('.new-price');
		const price = parseFloat($priceElement.text().replace(/[^\d]/g, ''));
		const $quantityElement = $(this).find('.quantity');
		const quantity = parseInt($quantityElement.val(), 10);
		const subTotal = price * quantity;
		if ($checkBox.is(':checked')) {
			total += subTotal;
		}
	});

	$('.cr-cart-summary .total').text(formatCurrencyVND(total));
});

$(document).ready(function() {
	$("#btn-checkout").click(function () {
		const selectedItems = [];
		$(".product-checkbox:checked").each(function () {
			const productId = $(this).closest("tr").data("product-id");
			const quantity = $(this).closest("tr").find(".quantity").val();

			selectedItems.push({
				productId: productId,
				quantity: quantity
			});
		});

		if (selectedItems.length > 0) {
			console.log(selectedItems);
			$.ajax({
				url: `${contextPath}/api/customer/checkout`,
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({ items: selectedItems }),
				success: function (response) {
					window.location.href = `${contextPath}/checkout`;
				},
				error: function (xhr, status, error) {
					alert("Có lỗi xảy ra. Vui lòng thử lại!");
				}
			});
		} else {
			alert("Vui lòng chọn ít nhất một sản phẩm để thanh toán!");
		}
	});
});

$(document).ready(function () {
	$('#btn-checkout').click(function () {
		const selectedItems = [];
		$('.product-checkbox:checked').each(function () {
			const productId = $(this).closest('tr').data('product-id');
			const quantity = $(this).closest('tr').find('.quantity').val();

			selectedItems.push({
				productId: productId,
				quantity: quantity
			});
		});

		if (selectedItems.length > 0) {
			console.log(selectedItems);
			$.ajax({
				url: `${contextPath}/api/customer/checkout`,
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({ items: selectedItems }),
				success: function (response) {
					window.location.href = `${contextPath}/checkout`;
				},
				error: function (xhr, status, error) {
					alert('Có lỗi xảy ra. Vui lòng thử lại!');
				}
			});
		} else {
			alert('Vui lòng chọn ít nhất một sản phẩm để thanh toán!');
		}
	});
});

