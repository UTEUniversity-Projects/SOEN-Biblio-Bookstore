package com.biblio.controller.owner;

import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.service.ICustomerService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/owner/customer-profile")
public class CustomerProfileController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    private ICustomerService customerService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerProfileController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String id = request.getParameter("id");
        CustomerDetailResponse customerDetailResponse = customerService.findById(Long.parseLong(id));
        request.setAttribute("customer", customerDetailResponse);
        request.getRequestDispatcher("/views/owner/customer-profile.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
