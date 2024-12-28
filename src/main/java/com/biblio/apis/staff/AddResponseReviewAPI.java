package com.biblio.apis.staff;

import com.biblio.dto.request.ResponseReviewRequest;
import com.biblio.entity.ResponseReview;
import com.biblio.service.IResponseReviewService;
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

@WebServlet(urlPatterns = {"/api/staff/response-review/add"})
public class AddResponseReviewAPI extends HttpServlet {
    @Inject
    private IResponseReviewService responseReviewService;
    @Serial
    private static final long serialVersionUID = 1L;

    public AddResponseReviewAPI() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        ResponseReviewRequest reviewRequest = HttpUtil.of(request.getReader()).toModel(ResponseReviewRequest.class);

        String message;
        String type;
        if (responseReviewService.isExistResponseReview(reviewRequest.getReviewId())) {
            message = "Đánh giá đã được phản hồi vui lòng làm mới lại trình duyệt!";
            type = "warning";
        } else {
            ResponseReview responseReview = responseReviewService.insertResponseReview(reviewRequest);
            if (responseReview != null) {
                message = "Phản hồi của bạn đã được gửi thành công!";
                type = "success";
            } else {
                message = "Có lỗi xảy ra khi gửi phản hồi. Vui lòng thử lại!";
                type = "error";
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        result.put("type", type);
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
