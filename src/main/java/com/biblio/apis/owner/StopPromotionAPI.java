package com.biblio.apis.owner;

import com.biblio.service.IPromotionTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/api/owner/promotion/stop-promotion"})
public class StopPromotionAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IPromotionTemplateService promotionTemplateService;

    public StopPromotionAPI() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu JSON từ body
        StringBuilder jsonInput = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jsonInput.append(line);
        }

        // Chuyển JSON thành đối tượng Java
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestData = objectMapper.readValue(jsonInput.toString(), Map.class);

        String code = requestData.get("code");  // Lấy code từ JSON

        if (code == null || code.trim().isEmpty()) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "Mã chương trình khuyến mãi không hợp lệ.");

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(responseMap);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(jsonResponse);
            return;
        }

        try {
            boolean isSuccess = promotionTemplateService.stopPromotionByCode(code);

            Map<String, Object> responseMap = new HashMap<>();

            if (isSuccess) {
                responseMap.put("message", "Dừng chương trình khuyến mãi thành công.");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                responseMap.put("message", "Không tìm thấy chương trình khuyến mãi với mã này.");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(responseMap);

            response.getWriter().write(jsonResponse);

        } catch (Exception e) {

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "Đã có lỗi xảy ra, vui lòng thử lại sau.");

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(responseMap);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(jsonResponse);
            e.printStackTrace();
        }
    }
}




