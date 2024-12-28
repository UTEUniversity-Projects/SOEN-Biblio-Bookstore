package com.biblio.service;

import com.biblio.dto.request.DeleteCartItemRequest;
import com.biblio.dto.request.UpdateCartItemRequest;
import com.biblio.dto.response.CartItemResponse;

public interface ICartItemService {
    CartItemResponse updateCartItem (UpdateCartItemRequest updateCartItemRequest);

    CartItemResponse deleteCartItem (DeleteCartItemRequest deleteCartItemRequest);
}
