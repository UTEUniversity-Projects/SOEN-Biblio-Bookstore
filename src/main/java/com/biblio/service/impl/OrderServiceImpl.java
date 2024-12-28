package com.biblio.service.impl;

import com.biblio.dao.IOrderDAO;
import com.biblio.dao.IReturnBookDAO;
import com.biblio.dao.impl.BankTransferDAOImpl;
import com.biblio.dao.impl.EWalletDAOImpl;
import com.biblio.dto.request.CreateOrderRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.*;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EOrderHistory;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.mapper.BookMapper;
import com.biblio.mapper.OrderMapper;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.IOrderService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class OrderServiceImpl implements IOrderService {
    @Inject
    IOrderDAO orderDAO;

    @Inject
    IBookTemplateService bookTemplateService;

    @Inject
    EWalletDAOImpl walletDAO;

    @Inject
    BankTransferDAOImpl bankTransferDAO;

    @Inject
    IReturnBookDAO returnBookDAO;

    @Override
    public OrderDetailsManagementResponse getOrderDetailsManagementResponse(Long id) {
        Order order = orderDAO.findOneForDetailsManagement(id);
        return OrderMapper.mapToOrderDetailsManagementResponse(order);
    }

    @Override
    public List<OrderManagementResponse> getAllOrderManagementResponse() {
        List<Order> orders = orderDAO.findAllForManagement();
        orders.sort(Comparator.comparing((Order o) -> o.getStatus().getPriority()).reversed().thenComparing(Order::getOrderDate, Comparator.reverseOrder()));
        List<OrderManagementResponse> orderManagementResponse = new ArrayList<>();
        for (Order order : orders) {
            orderManagementResponse.add(OrderMapper.mapToOrderManagementResponse(order));
        }
        return orderManagementResponse;
    }

    @Override
    public boolean updateStatus(Long id, EOrderStatus status) {
        Order order = orderDAO.findOne(id);
        if (order == null || order.getStatus().equals(status)) {
            return false;
        }
        if (status == EOrderStatus.CANCELED) {
            for (OrderItem orderItem : order.getOrderItems()) {
                for (Book book : orderItem.getBooks()) {
                    book.getBookMetadata().setStatus(EBookMetadataStatus.IN_STOCK);
                }
                Book book = orderItem.getBooks().iterator().next();
                boolean success = bookTemplateService.verifyBookTemplateQuantity(book.getBookTemplate().getId());
                if (!success) {
                    return false;
                }
            }
        } else if (order.getStatus() == EOrderStatus.WAITING_CONFIRMATION ||
                order.getStatus() == EOrderStatus.PACKING ||
                order.getStatus() == EOrderStatus.SHIPPING) {
            order.getStatusHistory().add(updateOrderHistory(order, status));
        }
        order.setStatus(status);

        return orderDAO.update(order) != null;
    }

    public OrderStatusHistory updateOrderHistory(Order order, EOrderStatus status) {
        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setOrder(order);
        orderStatusHistory.setStatusChangeDate(LocalDateTime.now());

        if (status == EOrderStatus.PACKING) {
            orderStatusHistory.setStatus(EOrderHistory.CONFIRMED);
        } else if (status == EOrderStatus.SHIPPING) {
            orderStatusHistory.setStatus(EOrderHistory.WAITING_FOR_SHIPPING);
        } else if (status == EOrderStatus.COMPLETE_DELIVERY) {
            orderStatusHistory.setStatus(EOrderHistory.COMPLETED);
        }

        return orderStatusHistory;
    }

    @Override
    public Long countOrderAtTime(LocalDateTime start, LocalDateTime end) {
        List<Order> list = orderDAO.findAll();
        Long count = 0L;

        for (Order order : list) {
            LocalDateTime orderDate = order.getOrderDate();
            if ((orderDate.isEqual(start) || orderDate.isAfter(start)) && (orderDate.isEqual(end) || orderDate.isBefore(end)) && EOrderStatus.COMPLETE_DELIVERY.equals(order.getStatus())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Double revenueOrderAtTime(LocalDateTime start, LocalDateTime end) {
        Double venue = 0.0;
        List<Order> list = orderDAO.findAllForManagement();
        for (Order order : list) {
            LocalDateTime orderDate = order.getOrderDate();
            if ((orderDate.isEqual(start) || orderDate.isAfter(start)) &&
                    (orderDate.isEqual(end) || orderDate.isBefore(end)) &&
                    EOrderStatus.COMPLETE_DELIVERY.equals(order.getStatus())) {

                BankTransfer bankTransfer = bankTransferDAO.findByOrderId(order.getId());

                venue += bankTransfer.getAmount();
            }
        }
        return venue;
    }

    @Override
    public List<RevenueResponse> revenueStatistics(LocalDateTime start, LocalDateTime end) {
        List<RevenueResponse> revenueResponse = new ArrayList<>();
        List<Order> orders = orderDAO.findAllForManagement();

        for (Order order : orders) {
            LocalDateTime orderDate = order.getOrderDate();
            if ((orderDate.isEqual(start) || orderDate.isAfter(start)) && (orderDate.isEqual(end) || orderDate.isBefore(end)) && EOrderStatus.COMPLETE_DELIVERY.equals(order.getStatus())) {
                revenueResponse.add(OrderMapper.toRevenueResponse(order));
            }
        }

        // Tạo danh sách ngày từ start đến end với revenue mặc định là 0.0
        List<RevenueResponse> consolidatedRevenue = new ArrayList<>();
        LocalDate currentDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        while (!currentDate.isAfter(endDate)) {
            RevenueResponse consolidated = new RevenueResponse();
            consolidated.setDate(currentDate.atStartOfDay());
            consolidated.setRevenue(0.0);
            consolidatedRevenue.add(consolidated);
            currentDate = currentDate.plusDays(1);
        }
        // Duyệt qua revenueResponse và cộng revenue nếu ngày trùng
        for (RevenueResponse response : revenueResponse) {// Tổng revenue từ revenueResponse
            for (RevenueResponse consolidated : consolidatedRevenue) {
                if (response.getDate().toLocalDate().isEqual(consolidated.getDate().toLocalDate())) {
                    consolidated.setRevenue(consolidated.getRevenue() + response.getRevenue());
                    break;
                }
            }
        }

        // Sắp xếp danh sách theo ngày (nếu cần, nhưng thực tế danh sách đã theo thứ tự ngày ban đầu)
        consolidatedRevenue.sort(Comparator.comparing(RevenueResponse::getDate));

        return consolidatedRevenue;
    }

    @Override
    public OrderCustomerResponse findOrderById(Long orderId) {
        return orderDAO.findById(orderId);
    }

    @Override
    public OrderCustomerResponse findOrderByIdCustomer(Long orderId) {
        Order order = orderDAO.findByIdCustomer(orderId);
        return OrderMapper.toOrderCustomerResponse(order);
    }

    @Override
    public List<OrderDetailsManagementResponse> getAllOrderCustomerResponse(Long customerId) {
        List<Order> orders = orderDAO.findAllOrderForCustomer(customerId);
        orders.sort(Comparator.comparing(Order::getOrderDate).reversed());
        List<OrderDetailsManagementResponse> orderCustomerResponse = new ArrayList<>();
        for (Order order : orders) {
            if (order == null) {
                System.out.println("Null order found in orders list");
                continue;
            }
            System.out.println("Mapping order with ID: " + order.getId());

            OrderDetailsManagementResponse response = OrderMapper.mapToOrderDetailsManagementResponse(order);

            if (response == null) {
                System.out.println("Mapper returned null for order ID: " + order.getId());
            } else {
                System.out.println("Mapped response: " + response);
                response.setFinalPrice(order.getBankTransfer().getAmount());
                orderCustomerResponse.add(response);
            }
        }

        return orderCustomerResponse;
    }

    @Override
    public List<OrderDetailsManagementResponse> getOrderCustomerByStatus(Long customerId, String status) {
        List<Order> orders = orderDAO.findAllOrderForCustomer(customerId);
        orders.sort(Comparator.comparing(Order::getOrderDate).reversed());
        if (orders.isEmpty()) {
            System.out.println("Null order found in orders list");
            return null;
        }
        List<OrderDetailsManagementResponse> filteredOrderResponses = new ArrayList<>();

        if ("all".equalsIgnoreCase(status)) {
            for (Order order : orders) {
                if (order == null) {
                    System.out.println("Null order found in orders list");
                    continue;
                }

                OrderDetailsManagementResponse response = OrderMapper.mapToOrderDetailsManagementResponse(order);
                response.setFinalPrice(order.getBankTransfer().getAmount());
                if (response == null) {
                    System.out.println("Mapper returned null for order ID: " + order.getId());
                } else {
                    filteredOrderResponses.add(response);
                }
            }
        } else {
            try {
                EOrderStatus orderStatus = EOrderStatus.valueOf(status);
                Set<EOrderStatus> statusesToFilter = new HashSet<>();
                if (orderStatus == EOrderStatus.WAITING_CONFIRMATION) {
                    statusesToFilter.add(EOrderStatus.WAITING_CONFIRMATION);
                    statusesToFilter.add(EOrderStatus.REQUEST_REFUND);
                    statusesToFilter.add(EOrderStatus.PACKING);
                } else if (orderStatus == EOrderStatus.CANCELED) {
                    statusesToFilter.add(EOrderStatus.CANCELED);
                    statusesToFilter.add(EOrderStatus.REFUNDED);
                } else {
                    statusesToFilter.add(orderStatus);
                }
                for (Order order : orders) {
                    if (order == null) {
                        System.out.println("Null order found in orders list");
                        continue;
                    }

                    if (order.getStatus() != null && statusesToFilter.contains(order.getStatus())) {
                        OrderDetailsManagementResponse response = OrderMapper.mapToOrderDetailsManagementResponse(order);
                        response.setFinalPrice(order.getBankTransfer().getAmount());
                        if (response == null) {
                            System.out.println("Mapper returned null for order ID: " + order.getId());
                        } else {
                            filteredOrderResponses.add(response);
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status: " + status);
            }
        }

        return filteredOrderResponses;
    }

    public List<CountBookSoldResponse> topBookSelling(LocalDateTime start, LocalDateTime end) {
        List<BookSoldResponse> ListBookSold = new ArrayList<>();
        List<Order> list = orderDAO.findAllForManagement();
        for (Order order : list) {
            Order orderTmp = orderDAO.findOneForDetailsManagement(order.getId());
            LocalDateTime orderDate = orderTmp.getOrderDate();
            if ((orderDate.isEqual(start) || orderDate.isAfter(start)) && (orderDate.isEqual(end) || orderDate.isBefore(end)) && EOrderStatus.COMPLETE_DELIVERY.equals(orderTmp.getStatus())) {
                for (OrderItem orderItem : orderTmp.getOrderItems()) {
                    for (Book book : orderItem.getBooks()) {
                        ListBookSold.add(BookMapper.toBookSoldResponse(book));
                    }
                }
            }
        }
        List<CountBookSoldResponse> countBookSoldResponse = BookMapper.toCountBookSoldResponse(ListBookSold);
        countBookSoldResponse.sort(Comparator.comparingLong(CountBookSoldResponse::getCountSold).reversed());
        return countBookSoldResponse;

    }

    @Override
    public List<CountOrderOfCustomerResponse> rateReturnPurchase(LocalDateTime start, LocalDateTime end) {
        List<OrderOfCustomerResponse> orderOfCustomerResponse = new ArrayList<>();
        List<CountOrderOfCustomerResponse> countOrderOfCustomerResponses = new ArrayList<>();
        List<Order> list = orderDAO.findAllForManagement();

        for (Order order : list) {
            Order orderTmp = orderDAO.findOneForDetailsManagement(order.getId());
            LocalDateTime orderDate = orderTmp.getOrderDate();
            if ((orderDate.isEqual(start) || orderDate.isAfter(start)) && (orderDate.isEqual(end) || orderDate.isBefore(end)) && EOrderStatus.COMPLETE_DELIVERY.equals(orderTmp.getStatus())) {
                orderOfCustomerResponse.add(OrderMapper.toOrderOfCustomerResponse(orderTmp));
            }
        }

        Map<Long, CountOrderOfCustomerResponse> customerOrderCountMap = new HashMap<>();
        for (OrderOfCustomerResponse order : orderOfCustomerResponse) {
            Long customerId = order.getCustomerId();
            String customerName = order.getCustomerName();

            if (customerOrderCountMap.containsKey(customerId)) {
                CountOrderOfCustomerResponse response = customerOrderCountMap.get(customerId);
                response.setCountOrders(response.getCountOrders() + 1);
            } else {
                customerOrderCountMap.put(customerId, CountOrderOfCustomerResponse.builder().customerId(customerId).customerName(customerName).countOrders(1L).build());
            }
        }

        countOrderOfCustomerResponses.addAll(customerOrderCountMap.values());
        return countOrderOfCustomerResponses;
    }

    @Override
    public Long createOrder(CreateOrderRequest request) {
        return orderDAO.save(request).getId();
    }
  
    @Override
    public List<OrderReturnAtTimeResponse> rateReturn(LocalDateTime start, LocalDateTime end) {
        List<OrderReturnAtTimeResponse> orderReturnAtTimeResponses = new ArrayList<>();
        List<Order> list = orderDAO.findAllForManagement();

        for (Order order : list) {
            Order orderTmp = orderDAO.findOneForDetailsManagement(order.getId());
            LocalDateTime orderDate = orderTmp.getOrderDate();

            if ((orderDate.isEqual(start) || orderDate.isAfter(start)) && (orderDate.isEqual(end) || orderDate.isBefore(end)) && (EOrderStatus.COMPLETE_DELIVERY.equals(orderTmp.getStatus()) || EOrderStatus.REQUEST_REFUND.equals(orderTmp.getStatus()) || EOrderStatus.REFUNDED.equals(orderTmp.getStatus()))) {
                OrderReturnAtTimeResponse orderReturnAtTimeResponse = new OrderReturnAtTimeResponse();
                orderReturnAtTimeResponse.setOrderId(order.getId());
                ReturnBook returnBook = returnBookDAO.findByOrderId(order.getId());
                if (returnBook != null) {
                    orderReturnAtTimeResponse.setIsReturned(true);
                    orderReturnAtTimeResponse.setReturnReason(returnBook.getReason());
                }
                orderReturnAtTimeResponses.add(orderReturnAtTimeResponse);
            }
        }
        return orderReturnAtTimeResponses;
    }

    public static void main(String[] args) {
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        boolean res = orderService.updateStatus(3L, EOrderStatus.PACKING);
//        System.out.println(res);

    }

}
