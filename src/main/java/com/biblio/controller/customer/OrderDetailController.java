package com.biblio.controller.customer;

import com.biblio.dto.response.*;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.service.ICustomerService;
import com.biblio.service.IOrderService;
import com.biblio.service.IPromotionTemplateService;
import com.biblio.service.IReturnBookService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/order-detail")
public class OrderDetailController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IOrderService orderService;

    @Inject
    IReturnBookService returnBookService;

    @Inject
    IPromotionTemplateService promotionTemplateService;

    @Inject
    private ICustomerService customerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
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
        OrderDetailsManagementResponse orderDetail = orderService.getOrderDetailsManagementResponse(orderId);
        if (orderDetail == null) {
            System.out.println("Order not found for ID: " + orderId);
            response.sendRedirect(request.getContextPath() + "/order");
            return;
        }

        List<DiscountResponse> discounts = promotionTemplateService.getAllDiscounts();
        for (OrderProductResponse product : orderDetail.getProducts()) {
            double discount = promotionTemplateService.percentDiscount(product.getBookTemplateId(), discounts);
            product.setDiscountPercent(discount);
            product.calTotalPrice();
        }

//        for (OrderProductResponse product : orderDetail.getProducts()) {
//            double discount = promotionTemplateService.percentDiscountOfBook(product.getBookTemplateId());
//            product.setDiscountPercent(discount);
//            product.calTotalPrice();
//        }
        orderDetail.updateTotalPrice();
        orderDetail.updateFinalPrice();

        if (orderDetail.getStatus() == EOrderStatus.REQUEST_REFUND || orderDetail.getStatus() == EOrderStatus.REFUNDED) {
            ReturnBookManagementResponse returnBook = returnBookService.findReturnBookByOrderId(orderId);
            request.setAttribute("returnBook", returnBook);
        }
        request.setAttribute("orderDetail", orderDetail);
        request.getRequestDispatcher("/views/customer/order-detail.jsp").forward(request, response);

//        OrderCustomerResponse orderDetail = orderService.findOrderByIdCustomer(orderId);
//
//        if (orderDetail == null) {
//            System.out.println("Order not found for ID: " + orderId);
//            response.sendRedirect(request.getContextPath() + "/order");
//            return;
//        }
//
//
//        // Truyền thông tin đơn hàng vào JSP
//        request.setAttribute("orderDetail", orderDetail);
//
//        // Forward request to JSP view
//        request.getRequestDispatcher("/views/customer/order-detail.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
