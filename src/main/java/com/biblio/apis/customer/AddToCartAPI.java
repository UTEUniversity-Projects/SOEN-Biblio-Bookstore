package com.biblio.apis.customer;

import com.biblio.dto.request.AddToCartRequest;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.CartItemResponse;
import com.biblio.service.ICartService;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class SearchBookAPI
 */

@WebServlet("/api/customer/add-cart-item")
public class AddToCartAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICartService cartService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartAPI() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse) (HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        AccountGetResponse account = (AccountGetResponse) request.getSession().getAttribute("account");

        Map<String, Object> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (account == null) {
            result.put("code", 400);
        } else {
            AddToCartRequest addToCartRequest = HttpUtil.of(request.getReader()).toModel(AddToCartRequest.class);
            addToCartRequest.setAccountId(account.getId());
            CartItemResponse cartItem = cartService.addToCart(addToCartRequest);
            result.put("code", 200);
            result.put("cartItem", cartItem);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
