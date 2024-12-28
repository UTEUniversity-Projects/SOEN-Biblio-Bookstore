package com.biblio.controller.customer;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.ICartService;
import com.biblio.service.ICategoryService;

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
 * Servlet implementation class HomeController
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICategoryService categoryService;

    @Inject
    private IBookTemplateService bookTemplateService;

    @Inject
    private ICartService cartService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
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

//        List<CategorySidebarResponse> categories = categoryService.getAllCategorySidebarResponse();
//        List<BookCardResponse> books = bookTemplateService.getAllBookCardResponse();
//        request.setAttribute("categories", categories);
//        request.setAttribute("books", books);

        request.setAttribute("account", account);

        request.getRequestDispatcher("/views/customer/home.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
