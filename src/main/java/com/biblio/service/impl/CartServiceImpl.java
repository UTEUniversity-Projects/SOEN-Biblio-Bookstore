package com.biblio.service.impl;

import com.biblio.dao.IBookTemplateDAO;
import com.biblio.dao.ICartDAO;
import com.biblio.dao.ICartItemDAO;
import com.biblio.dto.request.AddToCartRequest;
import com.biblio.dto.response.CartItemResponse;
import com.biblio.dto.response.CartResponse;
import com.biblio.entity.BookTemplate;
import com.biblio.entity.Cart;
import com.biblio.entity.CartItem;
import com.biblio.mapper.CartItemMapper;
import com.biblio.mapper.CartMapper;
import com.biblio.service.ICartService;

import javax.inject.Inject;

public class CartServiceImpl implements ICartService {

    @Inject
    private ICartDAO cartDAO;

    @Inject
    private IBookTemplateDAO bookTemplateDAO;

    @Inject
    private ICartItemDAO cartItemDAO;

    @Override
    public CartResponse getCartResponseByAccountId(Long accountId) {

        Cart cart = cartDAO.findByAccountId(accountId);
        return CartMapper.toCartResponse(cart);
    }

    @Override
    public CartItemResponse addToCart(AddToCartRequest request) {
        Cart cart = cartDAO.findByAccountId(request.getAccountId());

        BookTemplate bookTemplate = bookTemplateDAO.findById(request.getBookTemplateId());

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBookTemplate().getId().equals(request.getBookTemplateId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            return CartItemMapper.toCartItemResponse(cartItemDAO.updateCartItem(existingItem));
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setBookTemplate(bookTemplate);
            newItem.setQuantity(request.getQuantity());
            return CartItemMapper.toCartItemResponse(cartItemDAO.addCartItem(newItem));
        }
    }

    @Override
    public Long countCartItemByAccountId(Long accountId) {
        return cartDAO.countByAccountId(accountId);
    }
}
