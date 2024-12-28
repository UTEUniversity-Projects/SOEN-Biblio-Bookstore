package com.biblio.dao.impl;

import com.biblio.dao.ICartDAO;
import com.biblio.entity.Cart;
import com.biblio.enumeration.EBookMetadataStatus;

import java.util.HashMap;
import java.util.Map;

public class CartDAOImpl extends GenericDAOImpl<Cart> implements ICartDAO {
    public CartDAOImpl() {
        super(Cart.class);
    }

    @Override
    public Cart addCart(Cart cart) {
        return super.save(cart);
    }

    @Override
    public Cart findByAccountId(Long accountId) {
        String jpql = "SELECT cart FROM Cart cart "
                + "LEFT JOIN FETCH cart.cartItems items "
                + "JOIN FETCH cart.customer customer "
                + "JOIN FETCH customer.account account "
                + "LEFT JOIN FETCH items.bookTemplate bookTemplate "
                + "LEFT JOIN FETCH bookTemplate.books books "
                + "LEFT JOIN FETCH bookTemplate.mediaFiles mediaFiles "
                + "WHERE account.id = :accountId";

        Map<String, Object> params = new HashMap<>();
        params.put("accountId", accountId);

        return super.findSingleByJPQL(jpql, params);
    }

    @Override
    public Cart findById(Long cartId) {
        String jpql = "SELECT c FROM Cart c " +
                "LEFT JOIN FETCH c.cartItems ci " +
                "WHERE c.id = :cartId";
        Map<String, Object> params = Map.of("cartId", cartId);
        return super.findSingleByJPQL(jpql, params);
    }

    @Override
    public Long countByAccountId(Long accountId) {
        String jpql = "SELECT COUNT(ci) FROM CartItem ci " +
                "JOIN ci.cart c " +
                "JOIN c.customer cust " +
                "JOIN cust.account a " +
                "WHERE a.id = :accountId";

        Map<String, Object> params = new HashMap<>();
        params.put("accountId", accountId);

        return super.countByJPQL(jpql, params);
    }

    public static void main(String[] args) {
        CartDAOImpl cartDAO = new CartDAOImpl();
        Cart cart = cartDAO.findById(1L);
        System.out.println(cart.getCartItems().size());
    }
}
