package com.biblio.controller.customer;

import com.biblio.dto.request.ForgotPasswordRequest;
import com.biblio.dto.request.ResetPasswordRequest;
import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.service.IAccountService;
import com.biblio.service.ICustomerService;
import com.biblio.service.IEmailService;
import com.biblio.utils.BCryptUtil;
import com.biblio.utils.HttpUtil;
import com.biblio.utils.SendMailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IAccountService accountService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPasswordController() {
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
        ResetPasswordRequest resetPasswordRequest = HttpUtil.of(request.getReader()).toModel(ResetPasswordRequest.class);

        Map<String, Object> map = new HashMap<>();

        String username = request.getSession().getAttribute("username").toString();
        resetPasswordRequest.setUsername(username);
        resetPasswordRequest.setNewPassword(BCryptUtil.HashPassword(resetPasswordRequest.getNewPassword()));

        accountService.resetPassword(resetPasswordRequest);
        map.put("code", 200);
        map.put("message", "Đổi mật khẩu thành công !");


        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}
