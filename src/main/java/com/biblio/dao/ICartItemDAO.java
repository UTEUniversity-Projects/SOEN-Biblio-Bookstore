package com.biblio.dao;

import com.biblio.entity.CartItem;

public interface ICartItemDAO {
    CartItem getCartItemById(Long id);
    CartItem addCartItem(CartItem cartItem);
    CartItem updateCartItem(CartItem cartItem);
    CartItem deleteCartItem(Long id);
}
