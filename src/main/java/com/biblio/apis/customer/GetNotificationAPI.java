package com.biblio.apis.customer;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.NotificationGetResponse;
import com.biblio.service.ICustomerService;
import com.biblio.service.IStaffService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/api/customer/notification/get"})
public class GetNotificationAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    ICustomerService customerService;

    public GetNotificationAPI() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        AccountGetResponse account = (session != null) ? (AccountGetResponse) session.getAttribute("account") : null;

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long customerId = customerService.getCustomerDetailByUsername(account.getUsername()).getId();
        List<NotificationGetResponse> notificationGetResponse = customerService.getAllNotificationByCustomerId(customerId);
        writeResponse(response, notificationGetResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void writeResponse(HttpServletResponse response, Object responseObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseObject);
        response.getWriter().write(jsonResponse);
    }
}
