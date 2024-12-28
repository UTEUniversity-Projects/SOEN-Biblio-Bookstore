package com.biblio.entity;

import com.biblio.enumeration.EPaymentCurrency;
import com.biblio.enumeration.EPaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class Payment implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = "amount", nullable = false)
    protected double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected EPaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    protected EPaymentCurrency currency;

    // endregion

    // region Abstract Methods

    public abstract void processPayment();

    // endregion
}
