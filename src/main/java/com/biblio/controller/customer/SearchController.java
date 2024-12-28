package com.biblio.controller.customer;

import com.biblio.dto.request.SearchBookRequest;
import com.biblio.dto.response.BookCardResponse;
import com.biblio.dto.response.CategoryBookCountResponse;
import com.biblio.service.IBookService;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.ICategoryService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet(urlPatterns = {"/search", "/book-list"})
public class SearchController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IBookService bookService;

    @Inject
    private IBookTemplateService bookTemplateService;

    @Inject
    private ICategoryService categoryService;

    public SearchController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        Long categoryId = parseLongParameter(request.getParameter("categoryId"));
        String sortBy = request.getParameter("sortBy");
        Integer perPage = parseIntParameter(request.getParameter("perPage"), 6); // Default to 6 if not provided
        Integer pageNumber = parseIntParameter(request.getParameter("pageNumber"), 1); // Default to 1 if not provided
        Long minPrice = parseLongParameter(request.getParameter("minPrice"), 0L); // Default to 0 if not provided
        Long maxPrice = parseLongParameter(request.getParameter("maxPrice"), Long.MAX_VALUE); // Default to Long.MAX_VALUE if not provided
        String condition = request.getParameter("condition");
        String format = request.getParameter("format");
        Double reviewRate = parseDoubleParameter(request.getParameter("reviewRate"), 0.0); // Default to 0.0 if not provided

        SearchBookRequest searchBookRequest = SearchBookRequest.builder()
                .title(title)
                .categoryId(categoryId)
                .sortBy(sortBy)
                .perPage(perPage)
                .pageNumber(pageNumber)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .condition(condition)
                .format(format)
                .reviewRate(reviewRate)
                .build();

        List<CategoryBookCountResponse> categories = categoryService.getBookQuantityPerCategory(searchBookRequest);
        List<BookCardResponse> books = bookTemplateService.getBookTemplateByCriteria(searchBookRequest);
        Long bookCount = bookTemplateService.getBookTemplateQuantityByCriteria(searchBookRequest);
        Long totalBook = bookTemplateService.getTotalBookTemplateQuantity();
        Long minBookPrice = bookService.getMinBookPrice();
        Long maxBookPrice = bookService.getMaxBookPrice();

        int totalPages = (int) (bookCount / perPage);
        int leftPage = (int) (bookCount % perPage);

        if (leftPage > 0) {
            totalPages++;
        }

        request.setAttribute("page", pageNumber);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("books", books);
        request.setAttribute("breadcrumb", "Tìm kiếm sách");
        request.setAttribute("title", title);
        request.setAttribute("bookCount", bookCount);
        request.setAttribute("totalBook", totalBook);
        request.setAttribute("minPrice", minBookPrice);
        request.setAttribute("maxPrice", maxBookPrice);

        request.getRequestDispatcher("/views/customer/search.jsp").forward(request, response);
    }

    private Long parseLongParameter(String param) {
        try {
            return param != null ? Long.parseLong(param) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Long parseLongParameter(String param, Long defaultValue) {
        try {
            return param != null ? Long.parseLong(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Integer parseIntParameter(String param, Integer defaultValue) {
        try {
            return param != null ? Integer.parseInt(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Double parseDoubleParameter(String param, Double defaultValue) {
        try {
            return param != null ? Double.parseDouble(param) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
