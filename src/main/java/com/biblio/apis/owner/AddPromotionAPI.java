package com.biblio.apis.owner;

import com.biblio.dto.request.PromotionTemplateInsertRequest;
import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.dto.response.PromotionTemplateResponse;
import com.biblio.service.ICustomerService;
import com.biblio.service.IEmailService;
import com.biblio.service.IPromotionTemplateService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(urlPatterns = {"/owner/promotion/add"})
public class AddPromotionAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IPromotionTemplateService promotionTemplateService;

    @Inject
    private IEmailService emailService;

    @Inject
    private ICustomerService customerService;

    // Tạo một thread pool với ExecutorService
    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // Số luồng có thể thay đổi tùy vào nhu cầu

    public AddPromotionAPI() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Parse JSON request to PromotionInsertRequest model
        PromotionTemplateInsertRequest promotionTemplateInsertRequest = HttpUtil.of(request.getReader()).toModel(PromotionTemplateInsertRequest.class);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Boolean isExitsCode = promotionTemplateService.isCodeExisted(promotionTemplateInsertRequest.getCode());
            if (isExitsCode) {
                map.put("isCodeExisted", true);
                map.put("message", "Code is existed");
            } else {
                PromotionTemplateResponse promotionTemplateResponse = promotionTemplateService.insertPromotionTemplate(promotionTemplateInsertRequest);
                map.put("isCodeExisted", false);
                map.put("message", "Promotion added successfully");

                // Gửi email bất đồng bộ cho từng khách hàng
                for (Long i = 1L; i <= 7; i++) {
                    final Long customerId = i; // Lưu giá trị customerId để sử dụng trong lambda expression
                    executorService.submit(() -> {
                        try {
                            CustomerDetailResponse customer = customerService.findById(customerId);
                            String emailContent = generatePromotionEmail(promotionTemplateResponse, customer, request);
                            String subject = "Chương trình khuyến mãi: " + promotionTemplateResponse.getPromotions().iterator().next().getTitle();
                            emailService.sendEmailNoRePlay(customer.getEmail(), subject, emailContent);
                        } catch (Exception e) {
                            e.printStackTrace(); // Log lỗi nếu gửi email thất bại
                        }
                    });
                }
            }
        } catch (Exception e) {
            map.put("isCodeExisted", false); // Không kiểm tra mã nên vẫn là false
            map.put("message", "Failed to add promotion: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        // Send JSON response
        response.getWriter().write(mapper.writeValueAsString(map));
    }

    @Override
    public void destroy() {
        // Đảm bảo rằng thread pool sẽ được dừng khi servlet bị hủy
        executorService.shutdown();
        super.destroy();
    }

    public String generatePromotionEmail(PromotionTemplateResponse promotionTemplateResponse, CustomerDetailResponse customer, HttpServletRequest request) {
        // ... Mã sinh email (không thay đổi)
        StringBuilder emailContent = new StringBuilder();

        // Định dạng ngày giờ mới
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        try {
            // Lấy chuỗi ngày hiệu lực và ngày hết hạn từ response
            String effectiveDateString = promotionTemplateResponse.getPromotions().iterator().next().getEffectiveDate();
            String expirationDateString = promotionTemplateResponse.getPromotions().iterator().next().getExpirationDate();

            // Chuyển đổi chuỗi thành LocalDateTime
            LocalDateTime effectiveDate = LocalDateTime.parse(effectiveDateString, formatter);
            LocalDateTime expirationDate = LocalDateTime.parse(expirationDateString, formatter);

            // Chuyển đổi thành múi giờ Việt Nam
            effectiveDate = effectiveDate.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
            expirationDate = expirationDate.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();

            // Định dạng ngày giờ thành "dd-MM-yyyy HH:mm"
            String effectiveDateFormatted = effectiveDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            String expirationDateFormatted = expirationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            // Định dạng tiền tệ (VND)
            DecimalFormat currencyFormat = new DecimalFormat("#,###");
            String discountLimit = currencyFormat.format(promotionTemplateResponse.getPromotions().iterator().next().getDiscountLimit()) + " VND";
            String minValueApplied = currencyFormat.format(promotionTemplateResponse.getPromotions().iterator().next().getMinValueApplied()) + " VND";

            emailContent.append("<html><body style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6;\">");
            emailContent.append("<div style=\"max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 10px; padding: 20px; background-color: #f9f9f9;\">");

            // Header với logo
            emailContent.append("<div style=\"text-align: center; margin-bottom: 20px;\">");
            emailContent.append("<img src=\"https://lh3.googleusercontent.com/d/1L2YtT3oH7TjTfyPhXVkx3XGJccTrfnQ1\" alt=\"Biblio Logo\" style=\"width: 150px; height: auto; margin-bottom: 10px;\"/>");
            emailContent.append("<h1 style=\"color: #d35400; margin: 0;\">🎉 Chương Trình Khuyến Mãi 🎉</h1>");
            emailContent.append("</div>");

            // Nội dung chính
            emailContent.append("<p>Chào <strong>").append(customer.getFullName()).append("</strong>,</p>");
            emailContent.append("<p>Chúng tôi rất vui được giới thiệu chương trình khuyến mãi mới đặc biệt dành cho bạn!</p>");

            // Khuyến mãi chi tiết
            emailContent.append("<div style=\"background-color: #fff3cd; padding: 15px; border-radius: 5px; border-left: 5px solid #ffc107;\">");
            emailContent.append("<p><b>Tiêu đề:</b> ").append(promotionTemplateResponse.getPromotions().iterator().next().getTitle()).append("</p>");
            emailContent.append("<p><b>Loại:</b> ").append(promotionTemplateResponse.getType()).append("</p>");
            emailContent.append("<p><b>Mã khuyến mãi:</b> <span style=\"color: #e74c3c; font-size: 18px;\">").append(promotionTemplateResponse.getCode()).append("</span></p>");
            emailContent.append("<p><b>Mô tả:</b> ").append(promotionTemplateResponse.getPromotions().iterator().next().getDescription()).append("</p>");
            emailContent.append("<p><b>Thời gian hiệu lực:</b> từ <span style=\"color: #27ae60;\">").append(effectiveDateFormatted)
                    .append("</span> đến <span style=\"color: #e74c3c;\">").append(expirationDateFormatted).append("</span></p>");
            if (promotionTemplateResponse.getPromotions().iterator().next().getDiscountLimit() > 0) {
                emailContent.append("<p><b>Giảm tối đa:</b> ").append(discountLimit).append("</p>");
            }
            if (promotionTemplateResponse.getPromotions().iterator().next().getMinValueApplied() > 0) {
                emailContent.append("<p><b>Áp dụng cho đơn hàng từ:</b> ").append(minValueApplied).append("</p>");
            }
            emailContent.append("</div>");

            // Lời kết
            emailContent.append("<p style=\"text-align: center; margin-top: 20px;\">Hãy nhanh tay sử dụng mã khuyến mãi này trước khi hết hạn!</p>");
            emailContent.append("<hr style=\"border: none; border-top: 1px solid #ddd; margin: 20px 0;\">");
            emailContent.append("<p style=\"text-align: center;\">Cảm ơn quý khách đã luôn tin tưởng và đồng hành cùng <strong>Biblio Bookshop</strong>.</p>");
            emailContent.append("<p style=\"text-align: center; font-weight: bold; color: #d35400;\">Biblio Bookshop</p>");

            emailContent.append("</div>");
            emailContent.append("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi khi ngày giờ không hợp lệ
        }

        return emailContent.toString();
    }
}
