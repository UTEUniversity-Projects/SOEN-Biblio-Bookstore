package com.biblio.utils;

import java.util.Random;

public class SendMailUtil {
    public static String generateOTP() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String generateOtpVerificationEmail(String otpCode, String intro) {
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<html><body>");
        emailContent.append("<p>Chào bạn,</p>");
        emailContent.append("<p>").append(intro).append("</p>");
        emailContent.append("<p><strong>Mã OTP của bạn là:</strong> <span style=\"font-size: 18px; font-weight: bold; color: #4CAF50;\">")
                .append(otpCode).append("</span></p>");
        emailContent.append("<p>Mã này có hiệu lực trong <strong>2 phút</strong>. Vui lòng không chia sẻ mã này với bất kỳ ai.</p>");
        emailContent.append("<hr>");
        emailContent.append("<p>Nếu cần hỗ trợ thêm, vui lòng liên hệ với chúng tôi qua email <a href=\"mailto:support@biblio.com\">support@biblio.com</a>.</p>");
        emailContent.append("<p>Trân trọng,<br>Biblio Bookshop</p>");
        emailContent.append("</body></html>");

        return emailContent.toString();
    }
}
