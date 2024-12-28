package com.biblio.controller.customer;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.enumeration.EUserRole;
import com.biblio.service.IAccountService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    private IAccountService accountService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        request.setAttribute("breadcrumb", "Đăng nhập");

        HttpSession session = request.getSession();
        if (session != null) {
            AccountGetResponse account = (AccountGetResponse) session.getAttribute("account");
            if (account != null) {
                String role = account.getRole();
                if (EUserRole.OWNER.toString().equals(role)) {
                    response.sendRedirect(request.getContextPath() + "/owner/ecommerce");
                } else if (EUserRole.STAFF.toString().equals(role)) {
                    response.sendRedirect(request.getContextPath() + "/staff/product-dashboard");
                } else if (EUserRole.CUSTOMER.toString().equals(role)) {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
                return;
            }
        }

        request.getRequestDispatcher("/views/customer/login.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

    }

}
