package com.biblio.service.impl;

import com.biblio.dao.IOrderStatusHistoryDAO;
import com.biblio.dto.response.OrderStatusHistoryResponse;
import com.biblio.entity.OrderStatusHistory;
import com.biblio.mapper.OrderStatusHistoryMapper;
import com.biblio.service.IOrderStatusHistoryService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderStatusHistoryServiceImpl implements IOrderStatusHistoryService {
    @Inject
    IOrderStatusHistoryDAO orderStatusHistoryDAO;

    @Override
    public List<OrderStatusHistoryResponse> getByOrderId(Long orderId) {
       List<OrderStatusHistory> orderStatusHistories = orderStatusHistoryDAO.findByOrderId(orderId);
       orderStatusHistories.sort(Comparator.comparingInt(orderStatusHistory -> orderStatusHistory.getStatus().getPriority()));
       List<OrderStatusHistoryResponse> orderStatusHistoryResponses = new ArrayList<OrderStatusHistoryResponse>();
       for (OrderStatusHistory orderStatusHistory : orderStatusHistories) {
           orderStatusHistoryResponses.add(OrderStatusHistoryMapper.toOrderStatusHistoryResponse(orderStatusHistory));
       }
       return orderStatusHistoryResponses;
    }
}
