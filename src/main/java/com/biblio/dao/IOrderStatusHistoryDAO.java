package com.biblio.dao;

import com.biblio.entity.OrderStatusHistory;

import java.util.List;

public interface IOrderStatusHistoryDAO {
    List<OrderStatusHistory> findByOrderId(Long orderId);
}
