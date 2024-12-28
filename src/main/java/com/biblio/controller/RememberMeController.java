package com.biblio.controller;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.service.IAccountService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Serial;

/**
 * Servlet implementation class RememberMeController
 */
@WebServlet("/remember")
public class RememberMeController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IAccountService accountService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RememberMeController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        HttpSession session = request.getSession(false);
        AccountGetResponse account = (session != null) ? (AccountGetResponse) session.getAttribute("account") : null;

        if (account == null) {
            Cookie[] cookies = request.getCookies();
            String username = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        username = cookie.getValue();
                    }
                }

                if (username != null) {
                    account = accountService.getAccountByUsername(username);
                    if (account != null && session != null) {
                        session.setAttribute("account", account);
                    }
                }
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
