package com.biblio.apis.staff;


import com.biblio.dto.request.NotificationInsertRequest;
import com.biblio.dto.response.OrderStatusHistoryResponse;
import com.biblio.enumeration.ENotificationType;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.service.ICustomerService;
import com.biblio.service.IOrderService;
import com.biblio.service.IOrderStatusHistoryService;
import com.biblio.service.IReturnBookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serial;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(urlPatterns = {"/api/staff/order/*"})
public class OrderAPI extends HttpServlet {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Inject
    IOrderService orderService;

    @Inject
    IOrderStatusHistoryService orderStatusHistoryService;

    @Inject
    IReturnBookService returnBookService;

    @Inject
    ICustomerService customerService;

    @Serial
    private static final long serialVersionUID = 1L;

    public OrderAPI() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getPathInfo();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();

        try {
            switch (action) {
                case "/get-order-history":
                    handleGetOrderHistory(request, response, mapper);
                    break;

                default:
                    result.put("message", "Không tìm thấy hành động phù hợp!");
                    result.put("type", "error");
                    response.getWriter().write(mapper.writeValueAsString(result));
                    break;
            }
        } catch (Exception e) {
            result.put("message", "Có lỗi xảy ra trong quá trình xử lý yêu cầu.");
            result.put("type", "error");
            response.getWriter().write(mapper.writeValueAsString(result));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getPathInfo();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();

        try {
            switch (action) {
                case "/confirm-order":
                    handleConfirmOrder(request, response, result, mapper);
                    break;

                case "/cancel-order":
                    handleCancelOrder(request, response, result, mapper);
                    break;

                case "/transport-order":
                    handleTransportOrder(request, response, result, mapper);
                    break;

                case "/confirm-refund-order":
                    handleConfirmRefundOrder(request, response, result, mapper);
                    break;

                case "/cancel-refund-order":
                    handleCancelRefundOrder(request, response, result, mapper);
                    break;

                case "/received-order":
                    handleReceivedOrder(request, response, result, mapper);
                    break;

                default:
                    result.put("message", "Không tìm thấy hành động phù hợp!");
                    result.put("type", "error");
                    response.getWriter().write(mapper.writeValueAsString(result));
                    break;
            }
        } catch (Exception e) {
            result.put("message", "Có lỗi xảy ra trong quá trình xử lý yêu cầu.");
            result.put("type", "error");
            response.getWriter().write(mapper.writeValueAsString(result));
        }
    }

    // region Order

    private void handleConfirmOrder(HttpServletRequest request, HttpServletResponse response, Map<String, String> result, ObjectMapper mapper) throws IOException {
        Map<String, Object> jsonMap = mapper.readValue(request.getReader(), Map.class);
        long orderId = Long.parseLong(jsonMap.get("orderId").toString());
        boolean success = orderService.updateStatus(orderId, EOrderStatus.PACKING);

        if (success) {

            NotificationInsertRequest notificationInsertRequest = new NotificationInsertRequest();
            notificationInsertRequest.setContent("Đơn hàng " + orderId + " đã được xác nhận, sản phẩm sẽ được gửi đến bạn.");
            notificationInsertRequest.setTitle("Đơn hàng đã được xác nhận");
            notificationInsertRequest.setType(ENotificationType.ORDER);
            notificationInsertRequest.setHyperLink("/order-detail?orderId=" + orderId);

            customerService.addNewNotification(notificationInsertRequest, orderId);

            result.put("message", "Đơn hàng được xác nhận thành công!");
            result.put("type", "success");
            result.put("statusType", EOrderStatus.PACKING.name());
            result.put("status", EOrderStatus.PACKING.getDescription());
            result.put("statusStyle", EOrderStatus.PACKING.getStatusStyle());

            // Gửi email trong luồng riêng biệt
            executorService.submit(() -> {
                try {
                    sendOrderConfirmationEmail(request, orderId); // Gọi phương thức gửi email
                } catch (IOException e) {
                    e.printStackTrace(); // Log lỗi khi gửi email
                }
            });

        } else {
            result.put("message", "Không thể xác nhận đơn hàng. Vui lòng thử lại!");
            result.put("type", "info");
        }

        response.getWriter().write(mapper.writeValueAsString(result));
    }

    private void handleCancelOrder(HttpServletRequest request, HttpServletResponse response, Map<String, String> result, ObjectMapper mapper) throws IOException {
        Map<String, Object> jsonMap = mapper.readValue(request.getReader(), Map.class);
        long orderId = Long.parseLong(jsonMap.get("orderId").toString());
        String cancelContent = jsonMap.get("content").toString();

        boolean success = orderService.updateStatus(orderId, EOrderStatus.CANCELED);
        if (success) {
            NotificationInsertRequest notificationInsertRequest = new NotificationInsertRequest();
            notificationInsertRequest.setContent("Đơn hàng " + orderId + " của bạn đã bị hủy, shop sẽ hoàn tiền lại nhanh nhất đến bạn.");
            notificationInsertRequest.setTitle("Đơn hàng bị hủy");
            notificationInsertRequest.setType(ENotificationType.ORDER);
            notificationInsertRequest.setHyperLink("/order-detail?orderId=" + orderId);

            customerService.addNewNotification(notificationInsertRequest, orderId);

            result.put("message", "Đơn hàng được hủy thành công!");
            result.put("type", "success");
            result.put("statusType", EOrderStatus.CANCELED.name());
            result.put("status", EOrderStatus.CANCELED.getDescription());
            result.put("statusStyle", EOrderStatus.CANCELED.getStatusStyle());
            executorService.submit(() -> {
                try {
                    sendCancelOrderEmail(request,orderId,cancelContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            result.put("message", "Không thể hủy đơn hàng. Vui lòng thử lại!");
            result.put("type", "info");
        }
        response.getWriter().write(mapper.writeValueAsString(result));
    }

    private void handleTransportOrder(HttpServletRequest request, HttpServletResponse response, Map<String, String> result, ObjectMapper mapper) throws IOException {
        Map<String, Object> jsonMap = mapper.readValue(request.getReader(), Map.class);
        long orderId = Long.parseLong(jsonMap.get("orderId").toString());
        boolean success = orderService.updateStatus(orderId, EOrderStatus.SHIPPING);
        if (success) {

            NotificationInsertRequest notificationInsertRequest = new NotificationInsertRequest();
            notificationInsertRequest.setContent("Đơn hàng " + orderId + " đã giao đến bạn");
            notificationInsertRequest.setTitle("Giao hàng thành công");
            notificationInsertRequest.setType(ENotificationType.ORDER);
            notificationInsertRequest.setHyperLink("/order-detail?orderId=" + orderId);

            customerService.addNewNotification(notificationInsertRequest, orderId);

            result.put("message", "Đơn hàng được chuyển đến đơn vị vận chuyển thành công!");
            result.put("type", "success");
            result.put("statusType", EOrderStatus.SHIPPING.name());
            result.put("status", EOrderStatus.SHIPPING.getDescription());
            result.put("statusStyle", EOrderStatus.SHIPPING.getStatusStyle());
        } else {
            result.put("message", "Không thể xác nhận đơn hàng. Vui lòng thử lại!");
            result.put("type", "info");
        }
        response.getWriter().write(mapper.writeValueAsString(result));
    }

    private void handleConfirmRefundOrder(HttpServletRequest request, HttpServletResponse response, Map<String, String> result, ObjectMapper mapper) throws IOException {
        Map<String, Object> jsonMap = mapper.readValue(request.getReader(), Map.class);
        long orderId = Long.parseLong(jsonMap.get("orderId").toString());
        long returnBookId = Long.parseLong(jsonMap.get("returnBookId").toString());
        boolean success = orderService.updateStatus(orderId, EOrderStatus.REFUNDED) &&
                returnBookService.update(returnBookId);
        if (success) {

            NotificationInsertRequest notificationInsertRequest = new NotificationInsertRequest();
            notificationInsertRequest.setContent("Đơn hàng " + orderId + " đủ điều kiện để hoàn trả");
            notificationInsertRequest.setTitle("Hoàn trả đơn hàng thành công");
            notificationInsertRequest.setType(ENotificationType.ORDER);
            notificationInsertRequest.setHyperLink("/order-detail?orderId=" + orderId);

            customerService.addNewNotification(notificationInsertRequest, orderId);

            result.put("message", "Đơn hàng được xác nhận thành công!");
            result.put("type", "success");
            result.put("statusType", EOrderStatus.REFUNDED.name());
            result.put("status", EOrderStatus.REFUNDED.getDescription());
            result.put("statusStyle", EOrderStatus.REFUNDED.getStatusStyle());
            executorService.submit(() -> {
                try {
                    sendConfirmRefundOrderEmail(request,orderId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        } else {
            result.put("message", "Không thể xác nhận hoàn trả đơn hàng. Vui lòng thử lại!");
            result.put("type", "info");
        }
        response.getWriter().write(mapper.writeValueAsString(result));
    }

    private void handleCancelRefundOrder(HttpServletRequest request, HttpServletResponse response, Map<String, String> result, ObjectMapper mapper) throws IOException {
        Map<String, Object> jsonMap = mapper.readValue(request.getReader(), Map.class);
        long orderId = Long.parseLong(jsonMap.get("orderId").toString());
        String content = jsonMap.get("content").toString();
        boolean success = orderService.updateStatus(orderId, EOrderStatus.COMPLETE_DELIVERY);
        if (success) {
            NotificationInsertRequest notificationInsertRequest = new NotificationInsertRequest();
            notificationInsertRequest.setContent("Đơn hàng " + orderId + " không đủ điều kiện hoàn trả.");
            notificationInsertRequest.setTitle("Từ chối hoàn trả");
            notificationInsertRequest.setType(ENotificationType.ORDER);
            notificationInsertRequest.setHyperLink("/order-detail?orderId=" + orderId);

            customerService.addNewNotification(notificationInsertRequest, orderId);

            result.put("message", "Từ chối hoàn trả thành công!");
            result.put("type", "success");
            result.put("statusType", EOrderStatus.COMPLETE_DELIVERY.name());
            result.put("status", EOrderStatus.COMPLETE_DELIVERY.getDescription());
            result.put("statusStyle", EOrderStatus.COMPLETE_DELIVERY.getStatusStyle());

            executorService.submit(() -> {
                try {
                    sendCancelRefundOrderEmail(request, orderId, content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } else {
            result.put("message", "Không thể từ chối hoàn trả. Vui lòng thử lại!");
            result.put("type", "info");
        }
        response.getWriter().write(mapper.writeValueAsString(result));
    }

    private void handleReceivedOrder(HttpServletRequest request, HttpServletResponse response, Map<String, String> result, ObjectMapper mapper) throws IOException {
        Map<String, Object> jsonMap = mapper.readValue(request.getReader(), Map.class);
        long orderId = Long.parseLong(jsonMap.get("orderId").toString());
        boolean success = orderService.updateStatus(orderId, EOrderStatus.COMPLETE_DELIVERY);
        if (success) {
            result.put("message", "Đơn hàng được nhận thành công!");
            result.put("type", "success");
            result.put("statusType", EOrderStatus.COMPLETE_DELIVERY.name());
            result.put("status", EOrderStatus.COMPLETE_DELIVERY.getDescription());
            result.put("statusStyle", EOrderStatus.COMPLETE_DELIVERY.getStatusStyle());
        } else {
            result.put("message", "Không thể xác nhận đơn hàng. Vui lòng thử lại!");
            result.put("type", "info");
        }

        response.getWriter().write(mapper.writeValueAsString(result));
    }

    private void handleGetOrderHistory(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper) throws IOException {
        String orderIdStr = request.getParameter("orderId");

        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing orderId parameter\"}");
            return;
        }

        Long orderId = null;
        try {
            orderId = Long.parseLong(orderIdStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid orderId format\"}");
            return;
        }

        List<OrderStatusHistoryResponse> orderHistories = orderStatusHistoryService.getByOrderId(orderId);

        List<Map<String, Object>> steps = new ArrayList<>();
        for (OrderStatusHistoryResponse history : orderHistories) {
            Map<String, Object> step = new HashMap<>();
            step.put("status", history.getStatus());
            step.put("date", history.getDate());
            steps.add(step);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("steps", steps);
        response.getWriter().write(mapper.writeValueAsString(responseMap));
    }

    // endregion

    // region Email

    private void sendOrderConfirmationEmail(HttpServletRequest request, Long orderId) throws IOException {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String apiUrl = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            apiUrl += ":" + serverPort;
        }
        apiUrl += contextPath + "/staff/email/order-confirmation";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Gửi thông tin orderId qua body của request
            String params = "orderId=" + orderId /*+ "&finalPrice=" + finalPrice*/;
            try (OutputStream os = connection.getOutputStream()) {
                os.write(params.getBytes());
                os.flush();
            }

            // Kiểm tra phản hồi từ API
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpServletResponse.SC_OK) {
                throw new IOException("Gửi email thất bại! Mã lỗi: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void sendCancelOrderEmail(HttpServletRequest request, Long orderId, String cancelContent) throws IOException {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String apiUrl = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            apiUrl += ":" + serverPort;
        }
        apiUrl += contextPath + "/staff/email/order-cancellation";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Gửi thông tin orderId qua body của request
            String params = "orderId=" + orderId + "&cancelContent=" + cancelContent;
            try (OutputStream os = connection.getOutputStream()) {
                os.write(params.getBytes());
                os.flush();
            }

            // Kiểm tra phản hồi từ API
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpServletResponse.SC_OK) {
                throw new IOException("Gửi email hủy đơn hàng thất bại! Mã lỗi: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void sendConfirmRefundOrderEmail(HttpServletRequest request, Long orderId) throws IOException {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String apiUrl = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            apiUrl += ":" + serverPort;
        }
        apiUrl += contextPath + "/staff/email/refund-confirmation";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Gửi thông tin orderId qua body của request
            String params = "orderId=" + orderId;
            try (OutputStream os = connection.getOutputStream()) {
                os.write(params.getBytes());
                os.flush();
            }

            // Kiểm tra phản hồi từ API
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpServletResponse.SC_OK) {
                throw new IOException("Gửi email xác nhận hoàn trả thất bại! Mã lỗi: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void sendCancelRefundOrderEmail(HttpServletRequest request, Long orderId, String cancelContent) throws IOException {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String apiUrl = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            apiUrl += ":" + serverPort;
        }
        apiUrl += contextPath + "/staff/email/refund-cancellation";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Gửi thông tin orderId và nội dung hủy qua body của request
            String params = "orderId=" + orderId + "&cancelContent=" + cancelContent;
            try (OutputStream os = connection.getOutputStream()) {
                os.write(params.getBytes());
                os.flush();
            }

            // Kiểm tra phản hồi từ API
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpServletResponse.SC_OK) {
                throw new IOException("Gửi email từ chối hoàn trả thất bại! Mã lỗi: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    // endregion

}


