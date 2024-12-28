package com.biblio.controller.owner;

import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.dto.response.CustomerGetListResponse;
import com.biblio.service.ICustomerService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet("/owner/customer-list")
public class CustomerListController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICustomerService customerService;

    private static final ExecutorService emailExecutor = Executors.newSingleThreadExecutor(); // Thread pool cho việc gửi email

    public CustomerListController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CustomerGetListResponse> list = customerService.findAll();
        request.setAttribute("customers", list);
        request.getRequestDispatcher("/views/owner/customer-list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if (action != null && id != null) {
            try {
                long customerId = Long.parseLong(id);
                CustomerDetailResponse customer = customerService.findById(customerId);
                if ("deactivate".equals(action)) {
                    customerService.deactivateCustomer(customerId);
                    sendEmailAsync(request, action, customer.getEmail(), customer.getFullName());
                    response.getWriter().write("success");
                } else if ("activate".equals(action)) {
                    customerService.activateCustomer(customerId);
                    sendEmailAsync(request, action, customer.getEmail(), customer.getFullName());
                    response.getWriter().write("success");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("error");
            }
            return;
        }

        doGet(request, response);
    }

    private void sendEmailAsync(HttpServletRequest request, String action, String customerEmail, String customerName) {
        emailExecutor.submit(() -> {
            try {
                sendEmailRequest(request, action, customerEmail, customerName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendEmailRequest(HttpServletRequest request, String action, String customerEmail, String customerName) throws IOException {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String apiUrl = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            apiUrl += ":" + serverPort;
        }
        apiUrl += contextPath + "/email";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"))) {
            String data = "action=" + URLEncoder.encode(action, "UTF-8") +
                    "&customerEmail=" + URLEncoder.encode(customerEmail, "UTF-8") +
                    "&customerName=" + URLEncoder.encode(customerName, "UTF-8");
            out.write(data);
            out.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Email sent successfully.");
        } else {
            System.err.println("Failed to send email: " + connection.getResponseMessage());
        }

        connection.disconnect();
    }

    @Override
    public void destroy() {
        emailExecutor.shutdown(); // Đảm bảo thread pool được tắt khi servlet bị hủy
        super.destroy();
    }
}
