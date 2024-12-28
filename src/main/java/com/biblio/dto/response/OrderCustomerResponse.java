package com.biblio.dto.response;

import com.biblio.entity.Order;
import com.biblio.entity.OrderItem;
import com.biblio.entity.Promotion;
import com.biblio.enumeration.EOrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Getter
@Setter
public class OrderCustomerResponse {
    private long id;
    private String note;
    private String orderDate;
    private String paymentType;
    private EOrderStatus status;
    private Double vat;
    private Long customerId;
    private Long shippingId;
    private Double totalPrice;
    private Set<BookResponse> book = new HashSet<>();
    private String customerName;
    private Set<OrderItem> lineItems;
    private Set<Promotion> promotions = new HashSet<>();
    private Order order;
    private String address;
    private String email;
    private List<OrderProductResponse> products;
    private Double finalPrice;
    private Double totalDiscount;
    private String publisherName;
    private ShippingResponse shipping;

    public OrderCustomerResponse(Long id, String note, LocalDateTime orderDate, String publisherName, EOrderStatus status, OrderProductResponse products, Double totalPrice, Double finalPrice ) {
        this.id = id;
        this.note = note;
        this.orderDate = String.valueOf(orderDate);
        this.status = status;
        this.totalPrice = totalPrice;
        this.products = Collections.singletonList(products);
        this.finalPrice = finalPrice;
        this.publisherName = publisherName;
    }
    public OrderCustomerResponse(Long id, String note, LocalDateTime orderDate, String paymentType, EOrderStatus status, Double vat, Long customerId, Long shippingId) {
        this.id = id;
        this.note = note;
        this.orderDate = String.valueOf(orderDate);
        this.paymentType = paymentType;
        this.status = status;
        this.vat = vat;
        this.customerId = customerId;
        this.shippingId = shippingId;
    }

    public OrderCustomerResponse(long id, String note, String orderDate, String paymentType, EOrderStatus status, Double vat, Long customerId, Long shippingId, Double totalPrice, Set<BookResponse> book) {
        this.id = id;
        this.note = note;
        this.orderDate = orderDate;
        this.paymentType = paymentType;
        this.status = status;
        this.vat = vat;
        this.customerId = customerId;
        this.shippingId = shippingId;
        this.totalPrice = totalPrice;
        this.book = book;
    }

    public double calTotalPrice() {
        double totalPrice = 0.0;
        for (OrderItem item : lineItems) {
            totalPrice += item.calPriceItem();
        }
        BigDecimal roundedTotal = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
        return roundedTotal.doubleValue();
    }

    public double calculateTotalDiscount() {
        double remainingPrice = calTotalPrice();
        double totalDiscount = 0;

        for (Promotion promotion : promotions) {
            double discount = promotion.calculateDiscount(remainingPrice);
            totalDiscount += discount;
            remainingPrice -= discount;
        }

        return totalDiscount;
    }

    public double getFinalPrice() {
        double totalPrice = calTotalPrice() + order.getShipping().getShippingFee();
        double totalDiscount = calculateTotalDiscount();

        return Math.max(0, totalPrice - totalDiscount);
    }
}
