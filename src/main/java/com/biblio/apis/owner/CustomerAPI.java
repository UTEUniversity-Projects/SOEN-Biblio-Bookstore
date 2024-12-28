package com.biblio.apis.owner;

import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.service.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
 * Servlet implementation class CustomerAPI
 */
@WebServlet("/api/owner/customer")
public class CustomerAPI extends HttpServlet {
    @Serial
	private static final long serialVersionUID = 1L;
	@Inject
	private ICustomerService customerService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		List<CustomerDetailResponse> list = customerService.findAll();
//
//		// Thiết lập kiểu dữ liệu trả về là JSON
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//
//		// Dùng Jackson ObjectMapper để chuyển đổi đối tượng sang JSON
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.writeValue(response.getWriter(), list);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}