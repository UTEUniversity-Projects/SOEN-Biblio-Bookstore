package com.biblio.service.impl;

import com.biblio.dao.ICartItemDAO;
import com.biblio.dto.request.DeleteCartItemRequest;
import com.biblio.dto.request.UpdateCartItemRequest;
import com.biblio.dto.response.CartItemResponse;
import com.biblio.entity.CartItem;
import com.biblio.mapper.CartItemMapper;
import com.biblio.service.ICartItemService;

import javax.inject.Inject;

public class CartItemServiceImpl implements ICartItemService {
    @Inject
    private ICartItemDAO cartItemDAO;

    @Override
    public CartItemResponse updateCartItem(UpdateCartItemRequest updateCartItemRequest) {
        CartItem cartItem = cartItemDAO.getCartItemById(updateCartItemRequest.getCartItemId());
        cartItem.setQuantity(updateCartItemRequest.getQuantity());
        return CartItemMapper.toCartItemResponse(cartItemDAO.updateCartItem(cartItem));
    }

    @Override
    public CartItemResponse deleteCartItem(DeleteCartItemRequest deleteCartItemRequest) {
        return CartItemMapper.toCartItemResponse(cartItemDAO.deleteCartItem(deleteCartItemRequest.getCartItemId()));
    }
}
