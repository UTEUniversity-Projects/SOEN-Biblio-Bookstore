package com.biblio.controller.owner;

import com.biblio.dto.response.BookManagementResponse;
import com.biblio.service.IBookService;
import com.biblio.service.IBookTemplateService;

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

@WebServlet("/owner/product-list")
public class ProductListController extends HttpServlet {
    @Inject
    IBookTemplateService bookTemplateService;
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductListController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("account"));

        List<BookManagementResponse> books = bookTemplateService.getAllBookManagementResponse();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/views/owner/product-list.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
