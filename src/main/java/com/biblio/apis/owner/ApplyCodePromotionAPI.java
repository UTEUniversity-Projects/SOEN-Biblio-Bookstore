package com.biblio.apis.owner;

import com.biblio.dto.response.ApplyCodePromotionResponse;
import com.biblio.enumeration.EPromotionTemplateType;
import com.biblio.service.IPromotionTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet(urlPatterns = {"/api/owner/promotion/apply-code"})
public class ApplyCodePromotionAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IPromotionTemplateService promotionTemplateService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyCodePromotionAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Tạo đối tượng trả về với Builder pattern
        ApplyCodePromotionResponse applyCodePromotionResponse = ApplyCodePromotionResponse.builder()
                .message("Chưa có thông tin")
                .build();

        try {
            // Lấy mã khuyến mãi từ request thông qua query string
            String code = request.getParameter("code");
            String amount = request.getParameter("amount");
            String type = request.getParameter("type");

            if (code == null || code.isEmpty()) {
                applyCodePromotionResponse.setMessage("Mã khuyến mãi không hợp lệ!");
                writeResponse(response, applyCodePromotionResponse);
                return;
            }

            EPromotionTemplateType promotionTemplateType = "FREESHIP".equals(type) ?
                                                            EPromotionTemplateType.FREESHIP :
                                                            EPromotionTemplateType.VOUCHER;

            // Áp dụng mã khuyến mãi
            applyCodePromotionResponse = promotionTemplateService.applyCodePromotion(code, Double.parseDouble(amount), promotionTemplateType);

        } catch (Exception e) {
            // Ghi log lỗi (có thể thêm Log nếu cần)
            e.printStackTrace();
            applyCodePromotionResponse.setMessage("Đã có lỗi xảy ra khi áp dụng mã khuyến mãi.");
        }

        // Gửi phản hồi về client
        writeResponse(response, applyCodePromotionResponse);
    }



    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
    private void writeResponse(HttpServletResponse response, ApplyCodePromotionResponse applyCodePromotionResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(applyCodePromotionResponse);

        jsonResponse = jsonResponse.replace("\n", "").replace("\r", "");
        response.getWriter().write(jsonResponse);
    }

}


