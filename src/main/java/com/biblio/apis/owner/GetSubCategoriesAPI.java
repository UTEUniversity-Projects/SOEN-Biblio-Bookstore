package com.biblio.apis.owner;
import com.biblio.dto.response.CategoryResponse;
import com.biblio.dto.response.SubCategoryLineResponse;
import com.biblio.dto.response.SubCategoryProfileResponse;
import com.biblio.dto.response.SubCategoryResponse;
import com.biblio.service.ICategoryService;
import com.biblio.service.ISubCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serial;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetCategoriesAPI
 */
@WebServlet("/owner/promotion/get-subcategories")
public class GetSubCategoriesAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    ISubCategoryService subCategoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSubCategoriesAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String categoryId = request.getParameter("categoryId");
        //List<SubCategoryResponse> subCategoryResponse = subCategoryService.getAllSubCategoriesById(Long.parseLong(categoryId));
        List<SubCategoryProfileResponse> subCategoryProfileResponse = subCategoryService.findAll();
        // Thiết lập kiểu nội dung là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Trả về JSON
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(subCategoryProfileResponse));
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}