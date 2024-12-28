package com.biblio.dao.impl;

import com.biblio.dao.IOrderItemDAO;
import com.biblio.entity.OrderItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemDAOImpl extends GenericDAOImpl<OrderItem> implements IOrderItemDAO {

    public OrderItemDAOImpl(Class<OrderItem> entityClass) {
        super(entityClass);
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        String jpql = "SELECT li FROM OrderItem li WHERE li.order.id = :orderId";

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        return super.findByJPQL(jpql, params);
    }
}