package com.biblio.filter;

import com.biblio.constants.Constant;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.service.IAccountService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {

    @Inject
    IAccountService accountService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "username")) {
                    String username = cookie.getValue();
                    AccountGetResponse account = accountService.getAccountByUsername(username);
                    if (account != null) {
                        HttpSession session = httpRequest.getSession();
                        session.setAttribute("account", account);
                        session.setMaxInactiveInterval(Math.toIntExact(Constant.SESSION_LIVE_TIME));
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
