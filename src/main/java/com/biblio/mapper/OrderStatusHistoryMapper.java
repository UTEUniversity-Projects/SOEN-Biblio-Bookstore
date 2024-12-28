package com.biblio.mapper;

import com.biblio.dto.response.OrderStatusHistoryResponse;
import com.biblio.entity.OrderStatusHistory;

import static com.biblio.utils.DateTimeUtil.formatDateTime;

public class OrderStatusHistoryMapper {
    public static OrderStatusHistoryResponse toOrderStatusHistoryResponse(OrderStatusHistory orderStatusHistory) {
        return OrderStatusHistoryResponse.builder()
                .status(orderStatusHistory.getStatus())
                .date(formatDateTime(orderStatusHistory.getStatusChangeDate(), "HH:mm dd-MM-yyyy"))
                .build();
    }
}