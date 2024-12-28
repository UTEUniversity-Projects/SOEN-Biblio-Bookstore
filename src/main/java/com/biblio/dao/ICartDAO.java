package com.biblio.dao;

import com.biblio.entity.Cart;

public interface ICartDAO {
    Cart addCart(Cart cart);

    Cart findByAccountId(Long accountId);

    Cart findById(Long cartId);

    Long countByAccountId(Long accountId);
}
