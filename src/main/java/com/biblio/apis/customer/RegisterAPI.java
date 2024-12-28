package com.biblio.apis.customer;

import com.biblio.dto.request.CustomerRegisterRequest;
import com.biblio.dto.response.CustomerRegisterResponse;
import com.biblio.service.IAccountService;
import com.biblio.service.ICustomerService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class RegisterAPI
 */
@WebServlet("/api/customer/register")
public class RegisterAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICustomerService customerService;

    @Inject
    private IAccountService accountService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");

        CustomerRegisterRequest customerRegisterRequest = HttpUtil.of(request.getReader()).toModel(CustomerRegisterRequest.class);
        Map<String, Object> map = new HashMap<>();

        boolean isUsernameExisted = accountService.isUsernameExisted(customerRegisterRequest.getUsername().trim());
        boolean isEmailExisted = customerService.isEmailExisted(customerRegisterRequest.getEmail().trim());
        boolean isPhoneNumberExisted = customerService.isPhoneNumberExisted(customerRegisterRequest.getPhoneNumber().trim());

        if (isUsernameExisted || isEmailExisted || isPhoneNumberExisted) {
            map.put("code", 400);
            if (isUsernameExisted) {
                map.put("username", "Username đã tồn tại !");
            }
            if (isEmailExisted) {
                map.put("email", "Email đã tồn tại !");
            }
            if (isPhoneNumberExisted) {
                map.put("phoneNumber", "Số điện thoại đã tồn tại !");
            }
        } else {
            map.put("code", 200);
            map.put("message", "Đăng ký thành công");
            CustomerRegisterResponse customerRegisterResponse = customerService.addCustomer(customerRegisterRequest);
            map.put("customer", customerRegisterResponse);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));

    }

}
