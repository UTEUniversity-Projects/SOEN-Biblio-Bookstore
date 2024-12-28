package com.biblio.controller.customer;

import com.biblio.dto.request.ReturnBookRequest;
import com.biblio.dto.request.ReturnOrderRequest;
import com.biblio.dto.request.ReviewRequest;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.OrderCustomerResponse;
import com.biblio.entity.ReturnBook;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.enumeration.EReasonReturn;
import com.biblio.service.IOrderService;
import com.biblio.service.IReturnBookService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class HomeController
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15)   // 15 MB
@WebServlet("/return-order")
public class ReturnOrderController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    private IReturnBookService returnBookService;
    @Inject
    private IOrderService orderService;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AccountGetResponse account = (AccountGetResponse) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("/login");
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/order");
            return;
        }

        Long orderId = Long.parseLong(orderIdParam);
        OrderCustomerResponse orderDetail = orderService.findOrderByIdCustomer(orderId);

        if (orderDetail == null) {
            System.out.println("Order not found for ID: " + orderId);
            response.sendRedirect(request.getContextPath() + "/order");
            return;
        }

        request.setAttribute("orderDetail", orderDetail);

        request.setAttribute("EReasonReturn", EReasonReturn.values());

        // Truyền thông tin breadcrumb vào JSP
        request.setAttribute("breadcrumb", "Hoàn trả sách");

        request.getSession().setAttribute("orderId", orderId);

        // Chuyển tiếp yêu cầu đến JSP
        request.getRequestDispatcher("/views/customer/return-order.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountGetResponse account = (AccountGetResponse) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("/login");
            return;
        }
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ReturnOrderRequest returnOrderRequest = HttpUtil.of(request.getReader()).toModel(ReturnOrderRequest.class);

        String message;
        String type;

        boolean success = returnBookService.saveReturnOrder(returnOrderRequest);

        if (success) {
            message = "Yêu cầu của bạn đã được gửi thành công!";
            type = "success";
        } else {
            message = "Đơn hàng đã quá thời gian hoàn trả!";
            type = "info";
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        result.put("type", type);
        response.getWriter().write(mapper.writeValueAsString(result));
    }

}
