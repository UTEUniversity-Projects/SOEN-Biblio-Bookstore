package com.biblio.apis.customer;

import com.biblio.dto.response.*;
import com.biblio.entity.Cart;
import com.biblio.entity.CartItem;
import com.biblio.service.ICartService;
import com.biblio.service.IPromotionService;
import com.biblio.service.IPromotionTemplateService;
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
import java.util.List;
import java.util.Map;

@WebServlet("/api/customer/load-cart-sidebar")
public class LoadCartSidebarAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICartService cartService;

    @Inject
    private IPromotionTemplateService promotionTemplateService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadCartSidebarAPI() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        HttpSession session = request.getSession();
        AccountGetResponse account = (AccountGetResponse) session.getAttribute("account");
        if (account == null) return;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        List<DiscountResponse> discounts = promotionTemplateService.getAllDiscounts();

        CartResponse cart = cartService.getCartResponseByAccountId(account.getId());
        for (CartItemResponse cartItem : cart.getCartItems()) {
            cartItem.setSalePrice(cartItem.getSellingPrice() - (promotionTemplateService.percentDiscount(cartItem.getBookId(), discounts) / 100) * cartItem.getSellingPrice());
        }
//        for (CartItemResponse cartItem : cart.getCartItems()) {
//            cartItem.setSalePrice(cartItem.getSellingPrice() - (promotionTemplateService.percentDiscountOfBook(cartItem.getBookId()) / 100) * cartItem.getSellingPrice());
//        }

        map.put("cart", cart);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse) (HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
