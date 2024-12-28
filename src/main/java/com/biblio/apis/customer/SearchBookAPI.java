package com.biblio.apis.customer;

import com.biblio.dto.request.SearchBookRequest;
import com.biblio.dto.response.BookCardResponse;
import com.biblio.dto.response.CategoryBookCountResponse;
import com.biblio.service.IBookService;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.ICategoryService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class SearchBookAPI
 */
@WebServlet("/api/customer/search-book")
public class SearchBookAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IBookTemplateService bookTemplateService;

    @Inject
    private ICategoryService categoryService;

    @Inject
    private IBookService bookService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBookAPI() {
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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        SearchBookRequest req = HttpUtil.of(request.getReader()).toModel(SearchBookRequest.class);
        List<CategoryBookCountResponse> categories = categoryService.getBookQuantityPerCategory(req);

        List<BookCardResponse> book = bookTemplateService.getBookTemplateByCriteria(req);
        Long bookCount = bookTemplateService.getBookTemplateQuantityByCriteria(req);
        Long minPrice = bookService.getMinBookPrice();
        Long maxPrice = bookService.getMaxBookPrice();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        map.put("books", book);
        map.put("quantity", bookCount);
        map.put("category", categories);
        map.put("minPrice", minPrice);
        map.put("maxPrice", maxPrice);
        response.getWriter().append(mapper.writeValueAsString(map));
    }

}
