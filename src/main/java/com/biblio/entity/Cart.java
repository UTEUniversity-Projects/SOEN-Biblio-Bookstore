package com.biblio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@Getter
@Setter
public class Cart implements Serializable {

    //region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //endregion

    // region Relationships

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // endregion

    // region Methods
    public double getTotalBookPrice() {
        return cartItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getBookTemplate().getBooks().iterator().next().getSellingPrice())
                .sum();
    }
    // endregion

}
