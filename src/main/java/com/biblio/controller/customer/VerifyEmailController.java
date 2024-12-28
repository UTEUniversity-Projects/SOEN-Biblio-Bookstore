package com.biblio.controller.customer;

import com.biblio.dto.request.VerifyEmailRequest;
import com.biblio.service.ICustomerService;
import com.biblio.service.IEmailService;
import com.biblio.utils.HttpUtil;
import com.biblio.utils.SendMailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VerifyEmailController
 */
@WebServlet("/verify-email")
public class VerifyEmailController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICustomerService customerService;

    @Inject
    private IEmailService emailService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyEmailController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setAttribute("breadcrumb", "Xác thực email");
        request.getRequestDispatcher("/views/customer/verify-email.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        VerifyEmailRequest verifyEmailRequest = HttpUtil.of(request.getReader()).toModel(VerifyEmailRequest.class);

        Map<String, Object> map = new HashMap<>();

        boolean isEmailExisted = customerService.isEmailExisted(verifyEmailRequest.getEmail());
        if (isEmailExisted) {
            map.put("code", 400);
            map.put("message", "Email đã tồn tại !");
        } else {
            String optCode = SendMailUtil.generateOTP();
            long otpTimestamp = System.currentTimeMillis();
            request.getSession().setAttribute("otpCode", optCode);
            request.getSession().setAttribute("otpTimestamp", otpTimestamp);
            request.getSession().setAttribute("otpEmail", verifyEmailRequest.getEmail());
            String emailContent = SendMailUtil.generateOtpVerificationEmail(optCode, "Cảm ơn bạn đã đăng ký tài khoản tại Biblio !");
            try {
                emailService.sendEmail(verifyEmailRequest.getEmail(), "Xác thực email của bạn", emailContent);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            map.put("code", 200);
            map.put("message", "Mã OTP đã được gửi đến email của bạn!");
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
