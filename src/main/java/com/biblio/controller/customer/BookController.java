package com.biblio.controller.customer;

import com.biblio.dto.response.BookDetailsResponse;
import com.biblio.dto.response.DiscountResponse;
import com.biblio.service.IBookService;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.IPromotionTemplateService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/book")
public class BookController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IBookTemplateService bookTemplateService;

    @Inject
    private IPromotionTemplateService promotionTemplateService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Long id = Long.parseLong(request.getParameter("id"));
        BookDetailsResponse book = bookTemplateService.getBookDetailsResponse(id);
        List<DiscountResponse> discounts = promotionTemplateService.getAllDiscounts();
        book.setDiscount(promotionTemplateService.percentDiscount(book.getId(), discounts));
        request.setAttribute("book", book);
        request.setAttribute("breadcrumb", "Chi tiết sách");
        request.getRequestDispatcher("/views/customer/book.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
