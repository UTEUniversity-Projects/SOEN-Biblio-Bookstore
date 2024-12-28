package com.biblio.dao.impl;

import com.biblio.dao.IOrderStatusHistoryDAO;
import com.biblio.entity.Order;
import com.biblio.entity.OrderStatusHistory;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class OrderStatusHistoryDAOImpl extends GenericDAOImpl<OrderStatusHistory> implements IOrderStatusHistoryDAO {
    public OrderStatusHistoryDAOImpl() {
        super(OrderStatusHistory.class);
    }

    @Override
    public List<OrderStatusHistory> findByOrderId(Long orderId) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT o ")
                .append("FROM OrderStatusHistory o ")
                .append("WHERE o.order.id = :orderId");

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        return super.findByJPQL(jpql.toString(), params);
    }

    public static void main(String[] args) {
        OrderStatusHistoryDAOImpl dao = new OrderStatusHistoryDAOImpl();
        List<OrderStatusHistory> list = dao.findByOrderId(1L);
        list.sort(Comparator.comparingInt(orderStatusHistory -> orderStatusHistory.getStatus().getPriority()));

        System.out.println(list.size());
    }
}
