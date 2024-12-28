package com.biblio.controller.customer;

import com.biblio.dto.request.ForgotPasswordRequest;
import com.biblio.dto.request.VerifyEmailRequest;
import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.dto.response.CustomerResponse;
import com.biblio.service.IAccountService;
import com.biblio.service.IAddressService;
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
 * Servlet implementation class ForgotController
 */
@WebServlet("/forgot")
public class ForgotController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICustomerService customerService;

    @Inject
    private IEmailService emailService;

    @Inject
    private IAccountService accountService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setAttribute("breadcrumb", "Quên mật khẩu");
        request.getRequestDispatcher("/views/customer/forgot.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");

        ForgotPasswordRequest forgotPasswordRequest = HttpUtil.of(request.getReader()).toModel(ForgotPasswordRequest.class);
        String username = forgotPasswordRequest.getUsername();
        if (username == null) {
            username = (String) request.getSession().getAttribute("username");
        }

        Map<String, Object> map = new HashMap<>();

        boolean isUsernameExisted = accountService.isUsernameExisted(username);
        if (!isUsernameExisted) {
            map.put("code", 400);
            map.put("message", "Username không tồn tại !");
        } else {
            String optCode = SendMailUtil.generateOTP();
            long otpTimestamp = System.currentTimeMillis();
            request.getSession().setAttribute("otpCode", optCode);
            request.getSession().setAttribute("otpTimestamp", otpTimestamp);
            request.getSession().setAttribute("username", username);
            CustomerDetailResponse customerDetailResponse = customerService.getCustomerDetailByUsername(username);
            String emailContent = SendMailUtil.generateOtpVerificationEmail(optCode, "Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại Biblio Bookshop.");
            try {
                emailService.sendEmail(customerDetailResponse.getEmail(), "Xác thực email của bạn", emailContent);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            map.put("code", 200);
            map.put("message", "Mã OTP đã được gửi đến email " + customerDetailResponse.getEmail() +" của bạn!");
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
