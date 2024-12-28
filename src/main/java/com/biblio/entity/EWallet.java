package com.biblio.entity;

import com.biblio.enumeration.EWalletProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ewallet")
@NoArgsConstructor
@Getter
@Setter
public class EWallet extends Payment implements Serializable {

    // region Attributes

    @Column(name = "wallet_id", nullable = false)
    private String walletId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private EWalletProvider provider;

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

    // endregion
}
