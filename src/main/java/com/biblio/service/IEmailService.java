package com.biblio.service;

import javax.mail.MessagingException;

public interface IEmailService {
    void sendEmail(String toEmail, String subject, String body) throws MessagingException;
    void sendEmailNoRePlay(String toEmail, String subject, String body) throws MessagingException;

    public String getPromotionEmail(String customerName, String promotionDetails);//PromotionController
    String getWelcomeEmail(String customerName);//RegisterController
    String getAccountLockEmail(String customerName);//CustomerListController
    String getAccountUnlockEmail(String customerName);//CustomerListController
    String getAccountDeleteEmail(String customerName);
    String getReturnConfirmationEmail(String customerName, String orderId);//OrderDetailsController
    //thanh toán thành công, thông tin phải chi tiết, load thông tin quan trọng của đơn
    //chương trình khuyến mãi:
    //tuỳ thuộc vào owner có gửi về k , viết theo API code sẵn, k được tự động gửi -> contact Lợi
    // hoàn thành vào thứ 5
}
