package com.biblio.service;

import com.biblio.dto.response.OrderStatusHistoryResponse;

import java.util.List;

public interface IOrderStatusHistoryService {
    List<OrderStatusHistoryResponse> getByOrderId(Long orderId);
}
