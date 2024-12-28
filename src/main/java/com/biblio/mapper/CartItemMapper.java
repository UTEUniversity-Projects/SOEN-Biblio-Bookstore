package com.biblio.mapper;

import com.biblio.dto.response.CartItemResponse;
import com.biblio.entity.Book;
import com.biblio.entity.CartItem;

public class CartItemMapper {

    public static CartItemResponse toCartItemResponse(CartItem cartItem) {
        Book book = cartItem.getBookTemplate().getBooks().iterator().next();
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .bookId(cartItem.getBookTemplate().getId())
                .title(book.getTitle())
                .imageUrl(cartItem.getBookTemplate().getMediaFiles().get(0).getStoredCode())
                .sellingPrice(book.getSellingPrice())
                .quantity(cartItem.getQuantity())
                .subTotal(cartItem.calculateSubTotal())
                .build();
    }
}
