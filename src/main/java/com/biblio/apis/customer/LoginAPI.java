package com.biblio.apis.customer;

import com.biblio.constants.Constant;
import com.biblio.dto.request.LoginRequest;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.service.IAccountService;
import com.biblio.utils.BCryptUtil;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Servlet implementation class LoginAPI
 */
@WebServlet("/api/login")
public class LoginAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IAccountService accountService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");

        LoginRequest loginRequest = HttpUtil.of(request.getReader()).toModel(LoginRequest.class);

        boolean isUsernameExisted = accountService.isUsernameExisted(loginRequest.getUsername().trim());

        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (!isUsernameExisted) {
            map.put("status", "error");
            map.put("username", "Username không tồn tại !");
        } else {
            AccountGetResponse account = accountService.getAccountByUsername(loginRequest.getUsername().trim());
            if (!BCryptUtil.CheckPassword(loginRequest.getPassword(), account.getPassword())) {
                map.put("status", "error");
                map.put("password", "Password không chính xác !");
            } else {
                map.put("status", "success");
                map.put("data", account);
                map.put("remember", Objects.equals(loginRequest.getRememberMe(), "1"));

                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                session.setMaxInactiveInterval(Math.toIntExact(Constant.SESSION_LIVE_TIME));

                if (Objects.equals(loginRequest.getRememberMe(), "1")) {
                    Cookie username = new Cookie("username", account.getUsername());

                    username.setMaxAge(Math.toIntExact(Constant.COOKIE_LIVE_TIME));

                    username.setHttpOnly(true);

                    username.setPath("/");

                    response.addCookie(username);
                }

            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }

}
