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
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/customer/count-cart-item")
public class CountCartItemAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICartService cartService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CountCartItemAPI() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        AccountGetResponse account = (AccountGetResponse) request.getSession().getAttribute("account");
        if (account == null) {
            return;
        }

        Long count = cartService.countCartItemByAccountId(account.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
//        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse) (HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
