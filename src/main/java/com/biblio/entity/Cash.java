package com.biblio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cash")
@NoArgsConstructor
@Getter
@Setter
public class Cash extends Payment implements Serializable {

    // region Attributes

    @Column(name = "cash_received", nullable = false)
    private double cashReceived;

    @Column(name = "[change]", nullable = false)
    private double change;

    // endregion

    // region Relationships

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // endregion

    //  region Methods

    @Override
    public void processPayment() {

    }

    // endregion
}
