package com.biblio.service.impl;

import com.biblio.service.IEmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceImpl implements IEmailService {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String USERNAME = "vubaolong1484@gmail.com";
    private static final String PASSWORD = "cezztfksvcvwnibn";

    private Session createEmailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Đảm bảo sử dụng TLS 1.2
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }


    @Override
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        Session session = createEmailSession();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject, "UTF-8");
        message.setContent(body, "text/html; charset=UTF-8");

        Transport.send(message);
    }

    @Override
    public void sendEmailNoRePlay(String toEmail, String subject, String body) throws MessagingException {
        Session session = createEmailSession();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));


        message.setFrom(new InternetAddress("noreply@biblio.com"));
        message.setReplyTo(new Address[] { new InternetAddress("noreply@biblio.com") });
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject, "UTF-8");
        message.setContent(body, "text/html; charset=UTF-8");
        Transport.send(message);
    }


    @Override
    public String getPromotionEmail(String customerName, String promotionDetails) {
        return buildEmailTemplate(
                customerName,
                "🎉 Chương Trình Khuyến Mãi 🎉",
                "<p>Chúng tôi vui mừng thông báo chương trình khuyến mãi mới:</p>" +
                        "<p><strong>" + promotionDetails + "</strong></p>" +
                        "<p>Nhanh tay tham gia để nhận ưu đãi hấp dẫn!</p>"
        );
    }

    @Override
    public String getWelcomeEmail(String customerName) {
        return buildEmailTemplate(
                customerName,
                "Chào Mừng Quý Khách",
                "<p>Cảm ơn bạn đã đăng ký tài khoản với chúng tôi.</p>"
        );
    }

    @Override
    public String getAccountLockEmail(String customerName) {
        return buildEmailTemplate(
                customerName,
                "Tài Khoản Bị Khóa",
                "<p>Tài khoản của bạn đã bị khóa tạm thời.</p>" +
                        "<p>Những phản hồi/khiếu nại vui lòng gửi đến email <a href=\"mailto:biblio@gmail.com\">biblio@gmail.com</a>.</p>"
        );
    }

    @Override
    public String getAccountUnlockEmail(String customerName) {
        return buildEmailTemplate(
                customerName,
                "Tài Khoản Đã Mở Khóa",
                "<p>Tài khoản của bạn đã được mở khóa.</p>"
        );
    }

    @Override
    public String getAccountDeleteEmail(String customerName) {
        return buildEmailTemplate(
                customerName,
                "Tài Khoản Bị Xóa",
                "<p>Tài khoản của bạn đã bị xóa khỏi hệ thống.</p>"
        );
    }

    @Override
    public String getReturnConfirmationEmail(String customerName, String orderId) {
        return buildEmailTemplate(
                customerName,
                "Xác Nhận Hoàn Trả",
                "<p>Yêu cầu hoàn trả cho đơn hàng <strong>#" + orderId + "</strong> đã được chấp thuận.</p>"
        );
    }

    private String buildEmailTemplate(String customerName, String title, String bodyContent) {
        return "<html>" +
                "<body style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6; margin: 0; padding: 0;\">" +
                "<div style=\"max-width: 600px; margin: 20px auto; border: 1px solid #ddd; border-radius: 10px; overflow: hidden;\">" +
                // Header với logo và tiêu đề
                "<div style=\"background-color: #f7f7f7; padding: 20px; text-align: center;\">" +
                "<img src=\"https://lh3.googleusercontent.com/d/1L2YtT3oH7TjTfyPhXVkx3XGJccTrfnQ1\" " +
                "alt=\"Biblio Logo\" style=\"width: 120px; height: auto; margin-bottom: 10px;\"/>" +
                "<h2 style=\"color: #d35400; margin: 0;\">" + title + "</h2>" +
                "</div>" +
                // Nội dung chính
                "<div style=\"padding: 20px;\">" +
                "<p>Kính gửi <strong>" + customerName + "</strong>,</p>" +
                bodyContent +
                "</div>" +
                // Footer
                "<div style=\"background-color: #f7f7f7; padding: 10px; text-align: center; font-size: 14px; color: #888;\">" +
                "<p>Trân trọng,<br><strong>Biblio BookShop</strong></p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

}
