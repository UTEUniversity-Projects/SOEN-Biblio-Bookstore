import { toast } from './toast.js';
import Validator from './validator.js';
import { readImages, uploadImage, uploadImageToDrive } from '../../commons/js/upload-image.js';
import { CUSTOMER_FOLDER } from '../../commons/js/ggDriveFolderId.js';

$(document).ready(() => {

	class Register {
		constructor () {
		}

		async handleRegistration () {

			const registerButton = $('.btn-register');
			const spinner = registerButton.find('.spinner');
			const buttonText = registerButton.find('.button-text');

			registerButton.prop('disabled', true);
			buttonText.addClass('hidden');
			spinner.removeClass('hidden');

			//let avatar = `${contextPath}/assets/customer/img/avatar/img.png`;
			//
			//const postData = await readImages('avatar');
			//if (postData.length > 0) {
			//	const res = await uploadImageToDrive(postData);
			//	console.log(res);
			//	avatar = res[0].link;
			//}
			//console.log(avatar);

			let avatar = await uploadImage('customer', '#avatar');
			if (!avatar) {
				avatar = `${contextPath}/assets/customer/img/avatar/img.png`;
			}
			console.log({ avatar });

			const formData = new FormData(document.getElementById('registerForm'));

			const userData = {
				fullName: formData.get('fullName'),
				email: formData.get('email'),
				phoneNumber: formData.get('phoneNumber'),
				dateOfBirth: formData.get('dateOfBirth'),
				gender: formData.get('gender'),
				username: formData.get('username'),
				password: formData.get('password'),
				province: formData.get('province'),
				district: formData.get('district'),
				village: formData.get('village'),
				detail: formData.get('detail'),
				avatar: avatar
			};

			$.ajax({
				url: `${contextPath}/api/customer/register`,
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(userData),
				success: function (response, status, xhr) {
					console.log(response);
					if (response.code === 200) {
						toast({
							title: 'Đăng ký',
							message: 'Đăng ký thành công',
							type: 'success',
							duration: 3000
						});
						setTimeout(() => {
							window.location.href = `${contextPath}/login`
						}, 1000);
					} else {
						toast({
							title: 'Đăng ký',
							message: 'Thông tin đã tồn tại !',
							type: 'error',
							duration: 3000
						});
						Object.keys(response).forEach(key => {
							if (key !== 'code') {
								$(`#${key}`).next().text(response[key]);
							}
						});
					}
				},
				error: function (xhr, status, error) {
					console.log(error);
					toast({
						title: 'Lỗi',
						message: 'Có lỗi xảy ra',
						type: 'error',
						duration: 3000
					});
				}
			})

		}

		getAddress () {
			const BASE_URL = 'https://web.giaohangtietkiem.vn/api/v1/public/address';
			$('#province').html(`<option selected>Tỉnh thành phố</option>`);

			$.getJSON(`${BASE_URL}/list`, function (province) {
				if (province.success) {
					$.each(province.data, function (key, value) {
						$('#province').append(`<option value="${value.name}" data-id="${value.id}">${value.name}</option>`);
					});

					$('#province').change(function (e) {
						const cityId = $(this).find('option:selected').data('id');
						$.getJSON(`${BASE_URL}/list?parentId=${cityId}&type=3`, function (district) {
							if (district.success) {
								$('#district').html(`<option value="0">Quận Huyện</option>`);
								$('#village').html(`<option value="0">Phường Xã</option>`);

								$.each(district.data, function (key, value) {
									$('#district').append(`<option value="${value.name}" data-id="${value.id}">${value.name}</option>`);
								});
							}
						});
					});

					$('#district').change(function (e) {
						const wardId = $(this).find('option:selected').data('id');
						$('#village').html(`<option value="0">Phường Xã</option>`);

						$.getJSON(`${BASE_URL}/list?parentId=${wardId}&type=1`, function (village) {
							if (village.success) {
								$.each(village.data, function (key, value) {
									$('#village').append(`<option value="${value.name}" data-id="${value.id}">${value.name}</option>`);
								});
							}
						});
					});

					// Uncomment and update the following code if you want to handle more nested levels.
					// $('#ward').change(function (e) {
					// 	const hamletId = $(this).find('option:selected').data('id');
					// 	$('#hamlet').html(`<option value="0">Số nhà | Ấp | Tổ</option>`);

					// 	$.getJSON(`${BASE_URL}/hamlet?parentId=${hamletId}`, function (hamlet) {
					// 		if (hamlet.success) {
					// 			$.each(hamlet.data.hamlet_address, function (key, value) {
					// 				$('#hamlet').append(`<option value="${value.name}" data-id="${value.id}">${value.name}</option>`);
					// 			});
					// 		}
					// 	});
					// });
				}
			});
		}

		showRequiredInput () {
			$('.btn-register').click(() => {
				const formData = new FormData(document.getElementById('registerForm'));

				const userData = {
					fullName: formData.get('fullName'),
					email: formData.get('email'),
					phoneNumber: formData.get('phoneNumber'),
					dateOfBirth: formData.get('dateOfBirth'),
					gender: formData.get('gender'),
					username: formData.get('username'),
					password: formData.get('password'),
					province: formData.get('province'),
					district: formData.get('district'),
					village: formData.get('village'),
					detail: formData.get('detail')
				};
				for (const key in userData) {
					if (userData[key] === null || userData[key] === '' || userData[key] === '0') {
						toast({
							title: 'Thông tin', message: 'Vui lòng điền đầy đủ thông tin', type: 'info', duration: 4000
						});
						break;
					}
				}
			});
		}
	}

	const register = new Register();
	register.getAddress();

	register.showRequiredInput();

	Validator({
		form: '#registerForm',
		formGroupSelector: '.form-group',
		errorSelector: '.form-message',
		rules: [
			Validator.isRequired('#fullName'),
			Validator.isRequired('#email'),
			Validator.isRequired('#phoneNumber'),
			Validator.isRequired('#dob', 'Vui lòng chọn ngày sinh !'),
			Validator.isRequired("#username"),
			Validator.isRequired('#password'),
			Validator.isRequired('#re-password'),
			Validator.isConfirmed('#re-password', function () {
				return document.querySelector('#registerForm #password').value;
			}, 'Mật khẩu nhập lại không chính xác !'),
			Validator.isRequiredSelected('#province', 'Vui lòng chọn Tỉnh thành phố !', 'Tỉnh thành phố'),
			Validator.isRequiredSelected('#district', 'Vui lòng chọn Quận Huyện !', 'Quận Huyện'),
			Validator.isRequiredSelected('#village', 'Vui lòng chọn Phường Xã !', 'Phường Xã'),
			Validator.isRequired('#detail'),
			Validator.isChecked('#check-with-link', 'Vui lòng đồng ý với điều khoản và chính sách của Biblio !'),
			Validator.isEmail('#email', 'Email không đúng định dạng !'),
			Validator.phoneNumber("#phoneNumber", 10, 'Số điện thoại phải bao gồm 10 số và chỉ bao gồm số !'),
			Validator.isAllLowercase("#username", 'Username chỉ bao gồm chữ thường và số !'),
			Validator.minLength('#username', 8),
			Validator.minLength('#password', 8),
		],
		onSubmit: async function (data) {
			await register.handleRegistration();
		}
	});
});

