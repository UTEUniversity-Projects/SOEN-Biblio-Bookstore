import { toast } from '../../commons/js/toast.js';
import { formatCurrencyVND } from '../../commons/js/format-currency.js';

function disablesInputAndButton (input, applyButton) {
	input.disabled = true;
	input.classList.add('disabled-input');
	applyButton.disabled = true;
	applyButton.classList.add('disabled-button');
}

function applyPromotionCode (code, type, input, applyButton) {
	const totalPriceElement = $('#price-total');
	let totalPrice = parseFloat(totalPriceElement.attr('data-price'));
	console.log(totalPrice);
	$.ajax({
			url: `${contextPath}/api/owner/promotion/apply-code`,
			type: 'GET',
			data: {
				code: code,
				amount: totalPrice,
				type: type
			},
			dataType: 'json',
			success: function (data) {
				toast({
					title: 'Thông báo',
					message: data.message,
					type: data.promotionId !== null ? 'success' : 'info',
					duration: 3000
				});

				if (data.promotionId !== null) {
					const promotionData = {
						promotionId: data.promotionId,
						promotionCode: data.promotionCode,
						discountLimit: data.discountLimit,
						minValueToBeApplied: data.minValueToBeApplied,
						type: data.type,
						message: data.message
					};

					console.log(data);

					if (data.type === 'FREESHIP') {
						const freeshipElement = $('#price-freeship');
						const freeshipAmountElement = $('#price-freeship-amount');
						let freeshipAmount = parseFloat(freeshipAmountElement.attr('data-price'));
						if (promotionData.discountLimit > freeshipAmount) {
							promotionData.discountLimit = freeshipAmount;
						}
						freeshipElement.text(`-${formatCurrencyVND(promotionData.discountLimit)}`);
						freeshipElement.attr('data-price', promotionData.discountLimit);
						freeshipElement.attr('data-promotion-id', promotionData.promotionId);
					}

					if (data.type === 'VOUCHER') {
						const voucherElement = $('#price-voucher');
						voucherElement.text(`-${formatCurrencyVND(promotionData.discountLimit)}`);
						voucherElement.attr('data-price', promotionData.discountLimit);
						voucherElement.attr('data-promotion-id', promotionData.promotionId);
					}

					totalPrice -= promotionData.discountLimit;

					totalPriceElement.text(formatCurrencyVND(totalPrice.toFixed(2)));
					totalPriceElement.attr('data-price', totalPrice.toFixed(2));

					$.ajax({
						url: `${contextPath}/api/customer/promotion/add`,
						type: 'POST',
						contentType: 'application/json',
						data: JSON.stringify({
							id: promotionData.promotionId,
							code: promotionData.promotionCode,
							promotionType: promotionData.type,
							discountAmount: promotionData.discountLimit
						}),
						success: function () {
							console.log(data);
							console.log('Promotion updated in session.');
						},
						error: function (xhr, status, error) {
							console.error('Failed to update promotion in session:', error);
						}
					});

					disablesInputAndButton(input, applyButton);
				}
			},
			error: function (xhr, status, error) {
				console.error('Có lỗi xảy ra khi gọi API:', error);
				toast({
					title: 'Lỗi',
					message: 'Đã có lỗi xảy ra khi áp dụng mã khuyến mãi.',
					type: 'error',
					duration: 3000
				});
			}
		}
	);
}

$(document).ready(() => {
	const paymentMethods = document.querySelectorAll('.payment-method');
	const checkout = document.querySelector('.checkout');
	const overlay = document.querySelector('.checkout-overlay');
	const btnChangeAddress = document.querySelector('.change-address');

	const paymentType = ['Chuyển khoản', 'Momo', 'VNPAY'];
	const PAYMENT_TYPE = ['BANKING', 'CASH', 'EWALLET'];

	const freeshipInput = document.getElementById('freeship');
	const voucherInput = document.getElementById('voucher');
	const applyFreeshipButton = document.getElementById('applyFreeship');
	const applyVoucherButton = document.getElementById('applyVoucher');
	const btnOrder = document.querySelector('.btn-create-order');

	const applyFreeship = () => {
		const freeshipCode = freeshipInput.value.trim();
		if (freeshipCode) {
			console.log(freeshipCode);
			applyPromotionCode(freeshipCode, 'FREESHIP', freeshipInput, applyFreeshipButton);
		} else {
			toast({
				title: 'Thông báo',
				message: 'Vui lòng nhập mã freeship',
				type: 'info',
				duration: 3000
			});
		}
	}

	const applyVoucher = () => {
		const voucherCode = voucherInput.value.trim();
		if (voucherCode) {
			applyPromotionCode(voucherCode, 'VOUCHER', voucherInput, applyVoucherButton);
		} else {
			toast({
				title: 'Thông báo',
				message: 'Vui lòng nhập mã voucher',
				type: 'info',
				duration: 3000
			});
		}
	}

	applyFreeshipButton.addEventListener('click', applyFreeship);

	applyVoucherButton.addEventListener('click', applyVoucher);

	btnChangeAddress.addEventListener('click', function () {
		$('.modal-address').addClass('fixed inset-0').removeClass('hidden');
	});

	$('.modal-address-overlay').click(() => {
		$('.modal-address').removeClass('fixed inset-0').addClass('hidden');
	});

	$('.btn-choose-address').click(() => {
		const id = $('input[name=\'address-id\']:checked').attr('id');
		const choseAddress = $(`label[for='${id}']`).text().trim();
		$('.modal-address').removeClass('fixed inset-0').addClass('hidden');
		$('.address').text(choseAddress).attr('data-address-id', id);
	});

	const checkoutQR = checkout.querySelector('img');

	const MY_BANK = {
		BANK_ID: 'MB',
		ACCOUNT_NO: '8383352888888',
		ACCOUNT_NAME: 'TRANG KIM LOI'
	};


	let timeOut;
	let interval;
	let isSuccess = false;

	const generateQR = (amount, text) => {
		return `https://img.vietqr.io/image/${MY_BANK.BANK_ID}-${MY_BANK.ACCOUNT_NO}-compact2.png?amount=${amount}&addInfo=${text}&accountName=${MY_BANK.ACCOUNT_NAME}`;
	};

	paymentMethods.forEach((method, index) => {
		method.addEventListener('click', () => {
			if ($('.payment-type').text().trim() === '' || $('.payment-type').text().trim() !== PAYMENT_TYPE[index]) {
				$('.payment-type').text(paymentType[index]).attr('data-payment-type', PAYMENT_TYPE[index]);
				toast({
					message: `Đã chọn phương thức thanh toán ${paymentType[index]} !`,
					type: 'success',
					duration: 1000
				});

			}

		});
	});

	const getProductInfor = () => {
		const products = [];

		$('.book-item').each(function (index, item) {
			const $item = $(item);
			products.push({
				productId: $item.attr('href').split('=')[1],
				quantity: $item.find('.quantity').text().trim()
			});
		});

		return products;
	};

	btnOrder.addEventListener('click', () => {
		if ($('.payment-type').text().trim().length === 0) {
			toast({
				title: 'Thanh toán',
				message: 'Vui lòng chọn phương thức thanh toán'
			});
			return;
		}


		const text = `CHUYEN KHOAN DON HANG`;
		const amount = $("#price-total")[0].dataset.price;

		checkout.classList.add('active');
		checkoutQR.src = '';
		const qrImageSrc = generateQR(amount, text);
		$('.checkout-loading').removeClass('hidden');
		checkoutQR.onload = () => $('.checkout-loading').addClass('hidden');
		checkoutQR.src = qrImageSrc;
		isSuccess = false;

		timeOut = setTimeout(() => {
			interval = setInterval(() => {
				checkPaid(amount, text);
			}, 3000);
		}, 3000);

	});

	overlay.addEventListener('click', () => {
		checkout.classList.remove('active');
		if (timeOut) {
			clearTimeout(timeOut);
			clearInterval(interval);
		}
		isSuccess = false;
	});

	async function checkPaid (amount, text) {
		if (!isSuccess) {
			try {
				const response = await fetch('https://script.google.com/macros/s/AKfycbzDcjdyyF8jBuq4FVdme-76-x8DGrtMGuL4vuJlQcf2h_XeyJ3bMdDeilEGBhql9039YA/exec');
				const data = await response.json();
				const lastPaid = data.data[data.data.length - 1];
				const lastPrice = lastPaid['Giá trị'];
				const lastContent = lastPaid['Mô tả'];
				const bankAccountNumber = lastPaid['Số tài khoản đối ứng'];
				const bankName = lastPaid['Tên NH tài khoản đối ứng'];
				const transactionId = lastPaid['Mã GD'];
				const createdAt = lastPaid['Ngày diễn ra'];
				if (lastPrice >= amount && lastContent.includes(text)) {
					isSuccess = true;
					clearTimeout(timeOut);
					clearInterval(interval);
					const data = {
						addressId: $('.address')[0].dataset.addressId,
						note: $('#note').val().trim(),
						paymentType: $('.payment-type')[0].dataset.paymentType,
						items: getProductInfor(),
						amount: amount,
						bankAccountNumber: bankAccountNumber,
						bankName: bankName,
						transactionId: transactionId,
						createdAt: createdAt,
						shippingFee: parseInt($('#price-freeship-amount')[0].dataset.price)
					};

					$.ajax({
						url: `${contextPath}/api/customer/order/create`,
						type: 'POST',
						contentType: 'application/json',
						data: JSON.stringify(data),
						success: function (response) {
							toast({
								title: 'Thanh toán',
								message: 'Thanh toán thành công',
								type: 'success',
								duration: 2000
							});

							setTimeout(() => {
								window.location.href = `${contextPath}/order-detail?orderId=${response.orderId}`;

								const notificationData = {
									title: 'Đơn hàng mới',
									content: `Có một đơn hàng mới đang chờ xác nhận. order id là ${response.orderId}`,
									type: 'ORDER',
									hyperLink: `/staff/order-details?id=${response.orderId}`
								};

								$.ajax({
									url: `${contextPath}/api/staff/notification/add`,
									type: 'POST',
									contentType: 'application/json',
									data: JSON.stringify(notificationData),
									success: function (notificationResponse) {
										console.log('Thông báo đã được thêm thành công:', notificationResponse);
									},
									error: function (xhr, error) {
										console.log('Lỗi khi thêm thông báo:', error);
									}
								});
							}, 1000);
						},
						error: function (xhr, error) {
							console.log(error);
						}
					});


				} else {
					toast({
						message: 'Đang chờ thanh toán'
					});
				}
			} catch (error) {
				console.log(error);
			}
		}
	}
});
