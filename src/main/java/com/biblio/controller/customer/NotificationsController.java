package com.biblio.controller.customer;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.NotificationGetResponse;
import com.biblio.service.ICustomerService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/notifications")
public class NotificationsController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    ICustomerService customerService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationsController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        AccountGetResponse account = (session != null) ? (AccountGetResponse) session.getAttribute("account") : null;

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long customerId = customerService.getCustomerDetailByUsername(account.getUsername()).getId();
        List<NotificationGetResponse> notificationGetResponse = customerService.getAllNotificationByCustomerId(customerId);

        request.setAttribute("breadcrumb", "Danh sách thông báo");
        request.setAttribute("notificationGetResponse", notificationGetResponse);

        request.getRequestDispatcher("/views/customer/notification.jsp").forward(request, response);
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
