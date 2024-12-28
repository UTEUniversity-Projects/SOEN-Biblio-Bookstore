package com.biblio.controller;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.enumeration.EUserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;

/**
 * Servlet implementation class WaitingController
 */
@WebServlet("/waiting")
public class WaitingController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WaitingController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        HttpSession session = request.getSession();

        AccountGetResponse account = (AccountGetResponse) session.getAttribute("account");
        String role = account.getRole();

        if (EUserRole.OWNER.toString().equals(role)) {
            response.sendRedirect(request.getContextPath() + "/owner/ecommerce");
        } else if (EUserRole.STAFF.toString().equals(role)) {
            response.sendRedirect(request.getContextPath() + "/staff/product-dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
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
