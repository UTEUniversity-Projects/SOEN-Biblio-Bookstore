package com.biblio.entity;

import com.biblio.enumeration.EOrderHistory;
import com.biblio.enumeration.EOrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EOrderHistory status;

    @Column(name = "status_change_date", nullable = false)
    private LocalDateTime statusChangeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
