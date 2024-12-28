class UpdateInformation {
	constructor() {}

	updateInformation() {
		$(".save-button").click(function (event) {
			event.preventDefault();

			$('.form-group').removeClass('has-error');
			$('.error-message').remove();

			let isValid = true;

			// Kiểm tra các trường input không được để trống
			$('.form-group')
				.slice(0, -1)
				.each(function () {
					let input = $(this).find('input');
					if (input.attr('type') !== 'radio' && input.val() === '') {
						isValid = false;
						$(this).addClass('has-error');

						if ($(this).find('.error-message').length === 0) {
							input.after(
								'<div class="error-message text-[16px] text-red-500">Trường này không được để trống.</div>'
							);
						}
					}
				});

			// Kiểm tra định dạng email
			const email = $('input[type="email"]').val();
			const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			if (email && !emailPattern.test(email)) {
				isValid = false;
				$('input[type="email"]').closest('.form-group').addClass('has-error');
				$('input[type="email"]').after(
					'<div class="error-message text-[16px] text-red-500">Email không hợp lệ.</div>'
				);
			}

			// Kiểm tra mật khẩu khớp
			const password = $('input[type="password"]').eq(0).val();
			const confirmPassword = $('input[type="password"]').eq(1).val();
			if (password !== confirmPassword) {
				isValid = false;
				$('input[type="password"]')
					.eq(1)
					.closest('.form-group')
					.addClass('has-error');
				$('input[type="password"]')
					.eq(1)
					.after(
						'<div class="error-message text-[16px] text-red-500">Mật khẩu không khớp.</div>'
					);
			}

			// Kiểm tra giới tính đã chọn
			const gender = $("input[type='radio']:checked");
			if (gender.length === 0) {
				isValid = false;
				const genderUl = $("input[type='radio']").closest('ul');
				genderUl.after(
					'<div class="error-message text-[16px] text-red-500">Vui lòng chọn giới tính.</div>'
				);
			}

			// Nếu hợp lệ, gửi yêu cầu đến server
			if (isValid) {
				// Thu thập dữ liệu từ form
				const formData = {
					fullName: $('#fullName').val(),
					gender: $("input[type='radio']:checked").val(),
					dateOfBirth: $('#dateOfBirth').val(),
				};

				// Gửi dữ liệu qua AJAX
				$.ajax({
					url: '/api/updateCustomer', // URL endpoint để xử lý
					method: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(formData),
					success: function (response) {
						if (response.success) {
							// Hiển thị thông báo thành công
							toast({
								title: "Cập nhật thông tin",
								message: "Cập nhật thông tin cá nhân thành công!",
								type: "success",
							});

							setTimeout(() => {
								window.location.href = "user-information";
							}, 1000);
						} else {
							// Hiển thị thông báo thất bại
							toast({
								title: "Cập nhật thông tin",
								message: "Cập nhật thông tin không thành công. Vui lòng thử lại.",
								type: "error",
							});
						}
					},
					error: function () {
						// Hiển thị thông báo lỗi khi server không phản hồi
						toast({
							title: "Lỗi hệ thống",
							message: "Không thể kết nối đến server. Vui lòng thử lại sau.",
							type: "error",
						});
					},
				});
			}
		});
	}

	enableEditMode() {
		document.getElementById('edit-button').addEventListener('click', function () {
			// Hiển thị nút lưu và ẩn nút chỉnh sửa
			document.getElementById('edit-button').classList.add('hidden');
			document.getElementById('save-button').classList.remove('hidden');

			// Đảm bảo rằng các trường này không thể bị thay đổi thông qua JavaScript
			document.getElementById('readonlyEmail').setAttribute('readonly', 'readonly');
			document.getElementById('readonlyEmail').setAttribute('disabled', 'disabled');

			document.getElementById('readonlyUsername').setAttribute('readonly', 'readonly');
			document.getElementById('readonlyUsername').setAttribute('disabled', 'disabled');

			document.getElementById('readonlyPhoneNumber').setAttribute('readonly', 'readonly');
			document.getElementById('readonlyPhoneNumber').setAttribute('disabled', 'disabled');

			// Đảm bảo không thể thay đổi qua các sự kiện khác
			$('#readonlyField').on('input', function (e) {
				e.preventDefault(); // Ngăn ngừa thay đổi
			});
			// Hiển thị các trường nhập liệu đã bị ẩn
			document.querySelectorAll('.form-group.hidden').forEach(element => {
				element.classList.remove('hidden');
			});

			// Bật chế độ chỉnh sửa cho các ô input
			document.querySelectorAll('.form-control').forEach(input => {
				input.removeAttribute('readonly'); // Cho phép nhập liệu
				input.classList.remove('pointer-events-none'); // Kích hoạt trường nhập
			});
		});
	}
}

// Khởi tạo và gọi các phương thức
const updateInformation = new UpdateInformation();
updateInformation.updateInformation();
updateInformation.enableEditMode();
