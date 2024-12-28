package com.biblio.apis.customer;

import com.biblio.dto.request.LoadRelatedBooksRequest;
import com.biblio.dto.response.BookCardResponse;
import com.biblio.dto.response.DiscountResponse;
import com.biblio.service.IBookService;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.IPromotionTemplateService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoadRelatedBooksAPI
 */
@WebServlet("/api/customer/load-related-books")
public class LoadRelatedBooksAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IBookTemplateService bookTemplateService;

    @Inject
    private IPromotionTemplateService promotionTemplateService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadRelatedBooksAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");

        LoadRelatedBooksRequest loadRelatedBooksRequest = HttpUtil.of(request.getReader()).toModel(LoadRelatedBooksRequest.class);

        Map<String, Object> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        List<BookCardResponse> books = bookTemplateService.getRelatedBooks(loadRelatedBooksRequest);

        List<DiscountResponse> discounts = promotionTemplateService.getAllDiscounts();

        for (BookCardResponse book : books) {
            book.setSalePrice((book.getSellingPrice() - (promotionTemplateService.percentDiscount(book.getId(), discounts) / 100) * book.getSellingPrice()));
        }

//        for (BookCardResponse book : books) {
//            book.setSalePrice((book.getSellingPrice() - (promotionTemplateService.percentDiscountOfBook(book.getId()) / 100) * book.getSellingPrice()));
//        }

        result.put("books", books);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(result));
    }

}