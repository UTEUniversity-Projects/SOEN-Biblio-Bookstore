package com.biblio.apis.owner;
import com.biblio.dto.response.CategoryProfileResponse;
import com.biblio.dto.response.CategoryResponse;
import com.biblio.service.ICategoryService;
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
@WebServlet("/owner/promotion/get-categories")
public class GetCategoriesAPI extends HttpServlet {
    @Serial
	private static final long serialVersionUID = 1L;

	@Inject
	ICategoryService categoryService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCategoriesAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CategoryProfileResponse> categoryResponse = categoryService.getAllCategories();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(categoryResponse));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}