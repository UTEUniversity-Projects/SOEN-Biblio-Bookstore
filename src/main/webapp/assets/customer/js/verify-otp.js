import { toast } from "./toast.js";
$(document).ready(() => {

    class VerifyOTP {
        constructor(props) {

        }

        verifyOTP() {
            $(".btn-verify-otp").click(function (e) {
                e.preventDefault();

                const otp = $("#otp");
                const otpCode = otp.val().trim();

                otp.next(".error-message").remove();

                if (otpCode.length === 0) {
                    otp.parent().append(
                        "<div class=\"error-message text-[16px] text-red-500\">Trường này không được để trống</div>"
                    );
                    return;
                }

                if (!/^\d{6}$/.test(otpCode)) {
                    otp.parent().append(
                        "<div class=\"error-message text-[16px] text-red-500\">Mã OTP phải là 6 chữ số</div>"
                    );
                    return;
                }

                $.ajax({
                    url: `${contextPath}/verify-otp`,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({ otpCode: otpCode }),
                    success: function (response) {
                        if (response.code === 200) {
                            toast({
                                title: "Thông báo",
                                message: response.message,
                                type: "success",
                                duration: 1000,
                            });
                            setTimeout(() => {
                                window.location.href = `${contextPath}/register`;
                            }, 1000);
                        } else {
                            toast({
                                title: "Thông báo",
                                message: response.message,
                                type: "error",
                                duration: 1000,
                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: ", xhr.responseText);
                    }
                });
            });
        }

    }

    const verifyOTP = new VerifyOTP();
    verifyOTP.verifyOTP();

});