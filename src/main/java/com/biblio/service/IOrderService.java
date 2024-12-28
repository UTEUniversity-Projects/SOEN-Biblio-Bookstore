package com.biblio.service;

import com.biblio.dto.request.CreateOrderRequest;
import com.biblio.dto.response.*;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {
  
    OrderDetailsManagementResponse getOrderDetailsManagementResponse(Long id);

    List<OrderManagementResponse> getAllOrderManagementResponse();

    boolean updateStatus(Long id, EOrderStatus status);

    Long countOrderAtTime(LocalDateTime start, LocalDateTime end);

    Double revenueOrderAtTime(LocalDateTime start, LocalDateTime end);
  
    List<RevenueResponse> revenueStatistics(LocalDateTime start, LocalDateTime end);

    List<OrderDetailsManagementResponse> getOrderCustomerByStatus(Long customerId, String status);

    List<CountBookSoldResponse> topBookSelling(LocalDateTime start, LocalDateTime end);
  
    List<OrderDetailsManagementResponse> getAllOrderCustomerResponse(Long customerId);

    OrderCustomerResponse findOrderById(Long orderId);
  
    OrderCustomerResponse findOrderByIdCustomer(Long orderId);

    List<CountOrderOfCustomerResponse> rateReturnPurchase(LocalDateTime start, LocalDateTime end);

    Long createOrder(CreateOrderRequest request);

    List<OrderReturnAtTimeResponse> rateReturn(LocalDateTime start, LocalDateTime end);

}
