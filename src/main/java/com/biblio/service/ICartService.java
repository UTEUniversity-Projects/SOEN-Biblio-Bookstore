package com.biblio.service;

import com.biblio.dto.request.AddToCartRequest;
import com.biblio.dto.response.CartItemResponse;
import com.biblio.dto.response.CartResponse;

public interface ICartService {
    CartResponse getCartResponseByAccountId(Long accountId);

    CartItemResponse addToCart(AddToCartRequest addToCartRequest);

    Long countCartItemByAccountId(Long accountId);
}
