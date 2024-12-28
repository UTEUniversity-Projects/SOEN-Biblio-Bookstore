package com.biblio.apis.staff;

import com.biblio.dto.request.NotificationInsertRequest;
import com.biblio.service.IStaffService;
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

@WebServlet("/api/staff/notification/add")
public class AddNewNotificationAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IStaffService staffService;

    public AddNewNotificationAPI() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        NotificationInsertRequest notificationInsertRequest = HttpUtil.of(request.getReader()).toModel(NotificationInsertRequest.class);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Thêm thông báo
            staffService.addNewNotification(notificationInsertRequest);

            // Trả về thông báo thành công
            map.put("status", "success");
            map.put("message", "Thêm thông báo thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về thông báo lỗi
            map.put("status", "error");
            map.put("message", "Lỗi khi thêm thông báo.");
        }

        // Gửi phản hồi về client dưới dạng JSON
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}

