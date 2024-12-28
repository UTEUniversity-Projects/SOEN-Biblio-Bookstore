package com.biblio.apis.customer;

import com.biblio.dto.response.CheckOutResponse;
import com.biblio.dto.response.PromotionOrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/customer/promotion/add")
public class AddPromotionAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public AddPromotionAPI() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CheckOutResponse checkOutResponse = (CheckOutResponse) request.getSession().getAttribute("checkoutResponse");
        if (checkOutResponse == null) {
            response.sendRedirect("/cart");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        PromotionOrderResponse promotionOrderResponse = objectMapper.readValue(request.getInputStream(), PromotionOrderResponse.class);

        checkOutResponse.getPromotions().add(promotionOrderResponse);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Thêm khuyến mãi thành công!");

        response.getWriter().append(objectMapper.writeValueAsString(responseMap));
        request.getSession().setAttribute("checkoutResponse", checkOutResponse);
    }
}
