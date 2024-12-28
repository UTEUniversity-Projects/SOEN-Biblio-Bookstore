package com.biblio.entity;

import com.biblio.enumeration.EOrderHistory;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.enumeration.EPaymentType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "[order]")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "vat", nullable = false)
    private double vat;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EOrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private EPaymentType paymentType;

    // endregion

    // region Relationships

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_id", referencedColumnName = "id")
    private Shipping shipping;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private BankTransfer bankTransfer;
//
//    @OneToOne(mappedBy = "order")
//    private Cash cash;
//
//    @OneToOne(mappedBy = "order")
//    private EWallet wallet;

    @OneToOne(mappedBy = "order")
    private ReturnBook returnBook;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_promotion",
            joinColumns = @JoinColumn(name = "order_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "promotion_id", nullable = false))
    private Set<Promotion> promotions = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderStatusHistory> statusHistory = new HashSet<>();

    // endregion

    // region Methods

    public double calTotalPrice() {
        double totalPrice = 0.0;
        for (OrderItem item : orderItems) {
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
        double totalPrice = calTotalPrice() + shipping.getShippingFee();
        double totalDiscount = calculateTotalDiscount();

        return Math.max(0, totalPrice - totalDiscount);
    }

    public boolean isStatusHistoryExist(EOrderHistory status) {
        return statusHistory.stream()
                .anyMatch(statusHistoryItem -> statusHistoryItem.getStatus() == status);
    }

    // endregion
}