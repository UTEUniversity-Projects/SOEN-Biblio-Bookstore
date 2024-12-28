package com.biblio.mapper;

import com.biblio.dto.response.CartItemResponse;
import com.biblio.dto.response.CartResponse;
import com.biblio.entity.Cart;
import com.biblio.entity.CartItem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> cartItemResponse = cart.getCartItems()
                .stream()
                .sorted(Comparator.comparing(CartItem::getId))
                .map(CartItemMapper::toCartItemResponse)
                .collect(Collectors.toList());

        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cartItemResponse)
                .totalBookPrice(cart.getTotalBookPrice())
                .build();
    }
}
