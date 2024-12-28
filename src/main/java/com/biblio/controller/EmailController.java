package com.biblio.controller;

import com.biblio.service.IEmailService;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/email")
public class EmailController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(EmailController.class.getName());

    @Inject
    private IEmailService emailService; // Sử dụng Dependency Injection

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String customerEmail = request.getParameter("customerEmail");
        String customerName = request.getParameter("customerName");

        // Kiểm tra đầu vào cơ bản
        if (action == null || customerEmail == null || customerName == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Missing required parameters: action, customerEmail, or customerName.");
            return;
        }

        try {
            // Xử lý các hành động dựa trên action
            String subject = null;
            String emailBody = null;

            switch (action) {

                case "welcomeUser":
                    subject = "Chào mừng bạn đến với cửa hàng";
                    emailBody = emailService.getWelcomeEmail(customerName);
                    break;

                case "deactivate":
                    subject = "Tài khoản của bạn đã bị khóa";
                    emailBody = emailService.getAccountLockEmail(customerName);
                    break;

                case "activate":
                    subject = "Tài khoản của bạn đã được mở khóa";
                    emailBody = emailService.getAccountUnlockEmail(customerName);
                    break;

                case "deleteAccount":
                    subject = "Tài khoản của bạn đã bị xóa";
                    emailBody = emailService.getAccountDeleteEmail(customerName);
                    break;

                case "returnConfirmation":
                    String orderId = request.getParameter("orderId");
                    if (orderId == null) {
                        throw new IllegalArgumentException("Missing required parameter: orderId for returnConfirmation.");
                    }
                    subject = "Xác nhận hoàn tiền";
                    emailBody = emailService.getReturnConfirmationEmail(customerName, orderId);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().println("Invalid action: " + action);
                    return;
            }

            // Gửi email
            emailService.sendEmail(customerEmail, subject, emailBody);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Email sent successfully for action: " + action);

        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "Failed to send email.", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Failed to send email: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid input.", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(e.getMessage());
        }
    }
}
