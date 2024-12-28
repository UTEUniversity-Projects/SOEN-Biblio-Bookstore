package com.biblio.controller.staff;

import com.biblio.dto.response.DiscountResponse;
import com.biblio.dto.response.OrderDetailsManagementResponse;
import com.biblio.dto.response.OrderProductResponse;
import com.biblio.dto.response.ReturnBookManagementResponse;
import com.biblio.enumeration.EOrderStatus;
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

@WebServlet("/staff/order-details")
public class OrderDetailsController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IOrderService orderService;

    @Inject
    IReturnBookService returnBookService;

    @Inject
    IPromotionTemplateService promotionTemplateService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetailsController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Long orderId = Long.parseLong(request.getParameter("id"));
        OrderDetailsManagementResponse orderDetailsResponse = orderService.getOrderDetailsManagementResponse(orderId);

        List<DiscountResponse> discounts = promotionTemplateService.getAllDiscounts();
        for (OrderProductResponse product : orderDetailsResponse.getProducts()) {
            double discount = promotionTemplateService.percentDiscount(product.getBookTemplateId(), discounts);
            product.setDiscountPercent(discount);
            product.calTotalPrice();
        }

        orderDetailsResponse.updateTotalPrice();
        orderDetailsResponse.updateFinalPrice();

        if (orderDetailsResponse.getStatus() == EOrderStatus.REQUEST_REFUND || orderDetailsResponse.getStatus() == EOrderStatus.REFUNDED) {
            ReturnBookManagementResponse returnBook = returnBookService.findReturnBookByOrderId(orderId);
            request.setAttribute("returnBook", returnBook);
        }
        request.setAttribute("order", orderDetailsResponse);
        request.getRequestDispatcher("/views/staff/order-details.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
