package com.biblio.apis.customer;

import com.biblio.dto.request.CreateOrderRequest;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.CheckOutResponse;
import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.dto.response.PromotionOrderResponse;
import com.biblio.service.ICustomerService;
import com.biblio.service.IOrderService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class CreateOrderAPI
 */
@WebServlet("/api/customer/order/create")
public class CreateOrderAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICustomerService customerService;

    @Inject
    IOrderService orderService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateOrderAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");

        CreateOrderRequest createOrderRequest = HttpUtil.of(request.getReader()).toModel(CreateOrderRequest.class);

        AccountGetResponse account = (AccountGetResponse) request.getSession(false).getAttribute("account");

        CheckOutResponse checkout = (CheckOutResponse) request.getSession().getAttribute("checkoutResponse");

        List<Long> promotions = new ArrayList<>();

        for (PromotionOrderResponse p : checkout.getPromotions()) {
            promotions.add(p.getId());
        }

        createOrderRequest.setPromotions(promotions);

        CustomerDetailResponse customer = customerService.getCustomerDetailByUsername(account.getUsername().trim());
        createOrderRequest.setCustomer(customer);
        Long orderId = orderService.createOrder(createOrderRequest);


        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        map.put("orderId", orderId);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));

    }

}
