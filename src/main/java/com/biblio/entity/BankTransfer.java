package com.biblio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bank_transfer")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class BankTransfer extends Payment implements Serializable {

    // region Attributes

    @Column(name = "bank_account_number", nullable = false)
    private String bankAccountNumber;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    // endregion

    // region Relationships

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // endregion

    // region Methods

    @Override
    public void processPayment() {

    }

    // endregion Methods
}
