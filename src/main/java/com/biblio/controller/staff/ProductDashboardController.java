package com.biblio.controller.staff;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.BookManagementResponse;
import com.biblio.dto.response.StaffResponse;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.IStaffService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet("/staff/product-dashboard")
public class ProductDashboardController extends HttpServlet {
    @Inject
    IBookTemplateService bookTemplateService;

    @Inject
    IStaffService staffService;

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDashboardController() {
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

        StaffResponse staff = staffService.getStaffByUsername(account.getUsername().trim());

        List<BookManagementResponse> books = bookTemplateService.getAllBookManagementResponse();

        request.setAttribute("books", books);
        request.setAttribute("staff", staff);

        request.getRequestDispatcher("/views/staff/product-dashboard.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
