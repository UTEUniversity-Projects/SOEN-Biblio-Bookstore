import { toast } from './toast.js';

$(document).ready(() => {

	class VerifyEmail {
		constructor (props) {

		}

		verifyEmail () {

			$('.btn-verify').click(function (e) {
				e.preventDefault();

				const email = $('input[type=\'email\']');
				const emailValue = email.val().trim();

				email.next('.error-message').remove();

				if (emailValue.length === 0) {
					email.parent().append('<div class="error-message text-[16px] text-red-500">Trường này không được để trống</div>');
					return;
				}

				const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
				if (!emailRegex.test(emailValue)) {
					email.parent().append('<div class="error-message text-[16px] text-red-500">Email không đúng định dạng</div>');
					return;
				}

				const verifyButton = $('.btn-verify');
				const spinner = verifyButton.find('.spinner');
				const buttonText = verifyButton.find('.button-text');

				verifyButton.prop('disabled', true);
				buttonText.addClass('hidden');
				spinner.removeClass('hidden');

				$.ajax({
					url: `${contextPath}/verify-email`,
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify({
						email: emailValue
					}),
					success: function (response) {
						if (response.code === 200) {
							toast({
								title: 'Thông báo',
								message: response.message,
								type: 'success',
								duration: 1000
							});
							setTimeout(() => {
								window.location.href = `${contextPath}/verify-otp`;
							}, 1000);
						} else {
							toast({
								title: 'Thông báo',
								message: response.message,
								type: 'error',
								duration: 1000
							});
						}
					},
					error: function (xhr, status, error) {
						console.error('Error: ', xhr.responseText);
					},
					complete: function () {
						verifyButton.prop('disabled', false);
						buttonText.removeClass('hidden');
						spinner.addClass('hidden');
					}
				});

			});
		}

	}

	const verifyEmail = new VerifyEmail();
	verifyEmail.verifyEmail();

});