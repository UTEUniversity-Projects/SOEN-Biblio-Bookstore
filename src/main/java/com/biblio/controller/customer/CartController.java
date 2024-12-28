package com.biblio.controller.customer;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.CartItemResponse;
import com.biblio.dto.response.CartResponse;
import com.biblio.dto.response.DiscountResponse;
import com.biblio.service.ICartService;
import com.biblio.service.IPromotionTemplateService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/cart")
public class CartController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ICartService cartService;

    @Inject
    private IPromotionTemplateService promotionTemplateService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        HttpSession session = request.getSession();
        AccountGetResponse account = (AccountGetResponse) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        CartResponse cart = cartService.getCartResponseByAccountId(account.getId());
        List<DiscountResponse> discounts = promotionTemplateService.getAllDiscounts();
        for (CartItemResponse cartItem : cart.getCartItems()) {
            cartItem.setSalePrice(cartItem.getSellingPrice() - (promotionTemplateService.percentDiscount(cartItem.getBookId(), discounts) / 100) * cartItem.getSellingPrice());
        }

//        for (CartItemResponse cartItem : cart.getCartItems()) {
//            cartItem.setSalePrice(cartItem.getSellingPrice() - (promotionTemplateService.percentDiscountOfBook(cartItem.getBookId()) / 100) * cartItem.getSellingPrice());
//        }

        request.setAttribute("breadcrumb", "Giỏ hàng");
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/views/customer/cart.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
