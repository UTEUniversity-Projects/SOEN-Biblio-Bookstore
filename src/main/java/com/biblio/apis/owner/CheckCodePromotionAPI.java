package com.biblio.apis.owner;

import com.biblio.service.IPromotionService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/owner/promotion/check-code"})
public class CheckCodePromotionAPI extends HttpServlet {
    @Inject
    private IPromotionService promotionService;

    public CheckCodePromotionAPI() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc code từ JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = (ObjectNode) mapper.readTree(request.getReader());
        String code = jsonNode.get("code").asText().trim();

        boolean isCodeExisted = promotionService.isCodeExisted(code);

        Map<String, Object> map = new HashMap<>();
        if (isCodeExisted) {
            map.put("isCodeExisted", true);
        } else {
            map.put("isCodeExisted", false);
        }

        // Gửi JSON response
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
