package com.biblio.dao;

import com.biblio.dto.request.CreateOrderRequest;
import com.biblio.dto.response.OrderCustomerResponse;
import com.biblio.entity.Order;

import java.util.List;

public interface IOrderDAO {

    Order findOne(Long id);

    List<Order> findAll();

    List<Order> findAllForManagement();

    Order findOneForDetailsManagement(Long id);

    List<Order> findByJPQL(Long customerId);

    OrderCustomerResponse findById(Long id);

    List<Order> findAllOrderForCustomer(Long customerId);

    Order findByIdCustomer(Long id);

    Order update(Order order);

    boolean cancelOrder(Long id);

    Order save(CreateOrderRequest request);

}
