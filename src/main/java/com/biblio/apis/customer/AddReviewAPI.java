package com.biblio.apis.customer;

import com.biblio.dto.request.ResponseReviewRequest;
import com.biblio.dto.request.ReviewRequest;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.service.ICustomerService;
import com.biblio.service.IReviewService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/customer/review/add")
public class AddReviewAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICustomerService customerService;

    @Inject
    private IReviewService reviewService;

    public AddReviewAPI() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountGetResponse account = (AccountGetResponse) request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("/login");
            return;
        }
        CustomerDetailResponse customer = customerService.getCustomerDetailByUsername(account.getUsername());
        if (customer == null) {
            response.sendRedirect("/login");
            return;
        }
        Long customerId = customer.getId();

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ReviewRequest reviewRequest = HttpUtil.of(request.getReader()).toModel(ReviewRequest.class);

        boolean success = reviewService.createReview(reviewRequest, customerId);

        String message;
        String type;

        if (success) {
            message = "Đánh giá của bạn đã được gửi thành công!";
            type = "success";
        } else {
            message = "Có lỗi xảy ra khi gửi phản hồi. Vui lòng thử lại!";
            type = "error";
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        result.put("type", type);
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
