package com.biblio.apis.customer;

import com.biblio.dto.request.CheckoutRequest;
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

@WebServlet("/api/customer/checkout")
public class CheckoutAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    public CheckoutAPI() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        request.getSession().removeAttribute("checkoutResponse");
        ObjectMapper objectMapper = new ObjectMapper();
        CheckoutRequest checkoutRequest = objectMapper.readValue(request.getReader(), CheckoutRequest.class);
        request.getSession().setAttribute("checkoutRequest", checkoutRequest);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Giỏ hàng đã được gửi đi!");

        response.getWriter().append(objectMapper.writeValueAsString(responseMap));
    }
}
