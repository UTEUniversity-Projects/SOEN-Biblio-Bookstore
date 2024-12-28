package com.biblio.dao;

import com.biblio.entity.OrderItem;

import java.util.List;

public interface IOrderItemDAO {

    List<OrderItem> findByOrderId(Long orderId);

}
