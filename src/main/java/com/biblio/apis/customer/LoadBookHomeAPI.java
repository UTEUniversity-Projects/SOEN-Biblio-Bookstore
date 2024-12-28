package com.biblio.apis.customer;

import com.biblio.dto.response.BookCardResponse;
import com.biblio.dto.response.CartItemResponse;
import com.biblio.dto.response.CartResponse;
import com.biblio.dto.response.DiscountResponse;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.IPromotionTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class LoadBookHomeAPI
 */
@WebServlet("/api/customer/load-book-home")
public class LoadBookHomeAPI extends HttpServlet {

	@Inject
	private IBookTemplateService bookTemplateService;

	@Inject
	private IPromotionTemplateService promotionTemplateService;

    @Serial
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadBookHomeAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<BookCardResponse> books = bookTemplateService.getPopularBookCard();

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();

		List<DiscountResponse> discounts = promotionTemplateService.getAllDiscounts();

		for (BookCardResponse book : books) {
			book.setSalePrice((book.getSellingPrice() - (promotionTemplateService.percentDiscount(book.getId(),discounts) / 100) * book.getSellingPrice()));
		}
//		for (BookCardResponse book : books) {
//			book.setSalePrice((book.getSellingPrice() - (promotionTemplateService.percentDiscountOfBook(book.getId()) / 100) * book.getSellingPrice()));
//		}

		map.put("books", books);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(map));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
