package com.biblio.controller.customer;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.service.ICustomerService;

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
 * Servlet implementation class UserInformationController
 */
@WebServlet("/user-information")
public class UserInformationController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    private ICustomerService customerService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInformationController() {
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
        CustomerDetailResponse customer = customerService.getCustomerDetailByUsername(account.getUsername());
        System.out.println(customer.toString());
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/views/customer/user-information.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
