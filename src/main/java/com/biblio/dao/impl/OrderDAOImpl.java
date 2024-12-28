package com.biblio.dao.impl;

import com.biblio.dao.IBookDAO;
import com.biblio.dao.IOrderDAO;
import com.biblio.dto.request.CheckoutItemRequest;
import com.biblio.dto.request.CreateOrderRequest;
import com.biblio.dto.response.AddressResponse;
import com.biblio.dto.response.OrderCustomerResponse;
import com.biblio.entity.*;
import com.biblio.enumeration.*;
import com.biblio.jpaconfig.JpaConfig;
import com.biblio.mapper.AddressMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderDAOImpl extends GenericDAOImpl<Order> implements IOrderDAO {
    private final EntityManager entityManager = JpaConfig.getEntityManager();

    public OrderDAOImpl() {
        super(Order.class);
    }

    @Override
    public Order findOne(Long id) {
        return super.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return super.findAll();
    }

    @Override
    public List<Order> findAllForManagement() {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT DISTINCT o ")
                .append("FROM Order o ")
                .append("JOIN FETCH o.customer c ")
                .append("JOIN FETCH o.shipping s");

        return super.findAll(jpql.toString());
    }

    @Override
    public Order findOneForDetailsManagement(Long id) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT DISTINCT o ")
                .append("FROM Order o ")
                .append("JOIN FETCH o.customer c ")
                .append("JOIN FETCH c.account ac ")
                .append("JOIN FETCH o.shipping s ")
                .append("JOIN FETCH s.address ad ")
                .append("JOIN FETCH o.orderItems oi ")
                .append("JOIN FETCH oi.books b ")
                .append("JOIN FETCH b.bookTemplate bt ")
                .append("JOIN FETCH bt.mediaFiles m ")
                .append("LEFT JOIN FETCH o.promotions p ")
                .append("WHERE o.id = :id");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return super.findSingleByJPQL(jpql.toString(), params);
    }

    @Override
    public List<Order> findByJPQL(Long customerId) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT o ")
                .append("FROM Order o ")
                .append("WHERE o.customer.id = :customerId");

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        return super.findByJPQL(jpql.toString(), params);
    }

    @Override
    public List<Order> findAllOrderForCustomer(Long customerId) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT o ")
                .append("FROM Order o ")
                .append("JOIN FETCH o.customer c ")
                .append("JOIN FETCH c.account ac ")
                .append("JOIN FETCH o.shipping s ")
                .append("JOIN FETCH s.address ad ")
                .append("JOIN FETCH o.orderItems oi ")
                .append("JOIN FETCH oi.books b ")
                .append("JOIN FETCH b.bookTemplate bt ")
                .append("JOIN FETCH bt.mediaFiles m ")
                .append("JOIN FETCH bt.authors at ")
                .append("JOIN FETCH bt.publisher pl ")
                .append("LEFT JOIN FETCH o.promotions p ")
                .append("WHERE c.id = :customerId");

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        return super.findByJPQL(jpql.toString(), params);
    }

    @Override
    public OrderCustomerResponse findById(Long id) {
        // Query to fetch the order details
        String query = "SELECT o FROM Order o LEFT JOIN FETCH o.customer c LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH o.shipping s WHERE o.id = :id";

        try {
            // Execute the query
            Order order = entityManager.createQuery(query, Order.class)
                    .setParameter("id", id)
                    .getSingleResult();

            // Map Order entity to OrderCustomerResponse
            return OrderCustomerResponse.builder()
                    .id(order.getId())
                    .note(order.getNote())
                    .orderDate(String.valueOf(order.getOrderDate()))
                    .paymentType(order.getPaymentType().toString())
                    .status(order.getStatus())
                    .vat(order.getVat())
                    .customerId(order.getCustomer().getId())
                    .customerName(order.getCustomer().getFullName())
                    .shippingId(order.getShipping() != null ? order.getShipping().getId() : null)
                    .lineItems(order.getOrderItems())
                    .address(order.getShipping().getAddress().getFullAddress())
                    .email(order.getCustomer().getEmailAddress())
                    .build();
        } catch (NoResultException e) {
            // Handle case when no order is found
            throw new EntityNotFoundException("Order with ID " + id + " not found.");
        }
    }

    @Override
    public Order findByIdCustomer(Long id) {
        // Query to fetch the order details
        String query = "SELECT o FROM Order o LEFT JOIN FETCH o.customer c LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH o.shipping s WHERE o.id = :id";

        try {
            // Execute the query
            Order order = entityManager.createQuery(query, Order.class)
                    .setParameter("id", id)
                    .getSingleResult();

            // Trả về đối tượng Order
            return order;
        } catch (NoResultException e) {
            // Handle case when no order is found
            throw new EntityNotFoundException("Order with ID " + id + " not found.");
        }
    }

    @Override
    public Order update(Order order) {
        return super.update(order);
    }

    @Override
    public boolean cancelOrder(Long id) {
        Order order = findOne(id);
        if (order == null) {
            return false;
        }
        if (!order.getStatus().equals(EOrderStatus.WAITING_CONFIRMATION)) {
            return false;
        }
        order.setStatus(EOrderStatus.CANCELED);

        for (OrderItem orderItem : order.getOrderItems()) {
            for (Book book : orderItem.getBooks()) {
                book.getBookMetadata().setStatus(EBookMetadataStatus.IN_STOCK);
            }
        }
        super.update(order);
        return true;
    }

    @Override
    public Order save(CreateOrderRequest request) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(request.getCreatedAt(), inputFormatter);

        Set<AddressResponse> addresses = request.getCustomer().getAddresses();
        Optional<AddressResponse> addressOpt = addresses.stream()
                .filter(address -> address.getId().equals(request.getAddressId()))
                .findFirst();

        Address address = addressOpt
                .map(AddressMapper::toAddress)
                .orElse(null);

        Customer customer = new CustomerDAOImpl().findById(request.getCustomer().getId());

        Set<Promotion> promotions = new HashSet<>();

        for (Long id : request.getPromotions()) {
            Promotion promotion = new PromotionDAOImpl().findById(id);
            promotions.add(promotion);
        }

        BankTransfer bankTransfer = BankTransfer.builder()
                .createdAt(dateTime)
                .amount(request.getAmount())
                .status(EPaymentStatus.COMPLETED)
                .currency(EPaymentCurrency.VND)
                .bankAccountNumber(request.getBankAccountNumber())
                .bankName(request.getBankName())
                .transactionId(request.getTransactionId())
                .build();

        Shipping shipping = Shipping.builder()
                .address(address)
                .shippingFee(request.getShippingFee())
                .shippingUnit("Giao hàng tiết kiệm")
                .build();

        OrderStatusHistory orderStatusHistory = OrderStatusHistory.builder()
                .status(EOrderHistory.PLACED)
                .statusChangeDate(LocalDateTime.now())
                .build();

        Order order = Order.builder()
                .note(request.getNote())
                .vat(request.getVat())
                .orderDate(LocalDateTime.now())
                .status(EOrderStatus.WAITING_CONFIRMATION)
                .paymentType(EPaymentType.valueOf(request.getPaymentType()))
                .build();

        for (Promotion p : promotions) {
            p.getOrders().add(order);
        }

        order.setPromotions(promotions);

        bankTransfer.setOrder(order);
        shipping.setOrder(order);
        orderStatusHistory.setOrder(order);

        Set<OrderStatusHistory> orderStatusHistories = new HashSet<>();
        orderStatusHistories.add(orderStatusHistory);

        order.setStatusHistory(orderStatusHistories);
        order.setBankTransfer(bankTransfer);
        order.setShipping(shipping);
        order.setCustomer(customer);

        Set<OrderItem> orderItems = new HashSet<>();
        IBookDAO bookDAO = new BookDAOImpl();

        List<CheckoutItemRequest> checkoutItemRequests = request.getItems();

        for (CheckoutItemRequest item : checkoutItemRequests) {
            List<Book> books = bookDAO.findByBookTemplateIdAndQuantity(item);

            for (Book book : books) {
                BookMetadata bookMetadata = book.getBookMetadata();
                bookMetadata.setBook(book);
                bookMetadata.setStatus(EBookMetadataStatus.SOLD);
                new BookMetadataDAOImpl().update(bookMetadata);
            }

            OrderItem orderItem = OrderItem.builder()
                    .books(new HashSet<>(books))
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        return super.save(order);
    }

    public static void main(String[] args) {
        OrderDAOImpl dao = new OrderDAOImpl();
        Order order = dao.findOne(1L);
        System.out.println(order);
//        for (Order order : orders) {
//            System.out.println(order.getId());
//        }
//        for (OrderItem orderItem : order.getOrderItems()) {
//            System.out.println("Order Item ID: " + orderItem.getId());
//
//            if (!orderItem.getBooks().isEmpty()) {
//                Book singleBook = orderItem.getBooks().iterator().next();
//                System.out.println("  - Title of one book: " + singleBook.getTitle());
//                System.out.println("  - Quantity: " + orderItem.getBooks().size());
//            } else {
//                System.out.println("  - No books in this OrderItem");
//            }
//        }

    }
}
