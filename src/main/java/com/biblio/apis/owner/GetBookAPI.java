package com.biblio.apis.owner;
import com.biblio.dto.response.BookTemplatePromotionResponse;
import com.biblio.dto.response.CategoryResponse;
import com.biblio.dto.response.SubCategoryResponse;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.ICategoryService;
import com.biblio.service.ISubCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serial;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetCategoriesAPI
 */
@WebServlet("/owner/promotion/get-book")
public class GetBookAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IBookTemplateService bookTemplateService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookTemplatePromotionResponse> allBooks = bookTemplateService.getAllBookBookTemplatePromotionResponse();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(allBooks));

    }



    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}