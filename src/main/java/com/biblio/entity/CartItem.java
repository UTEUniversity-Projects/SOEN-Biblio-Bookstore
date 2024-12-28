package com.biblio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_template_id", nullable = false)
    private BookTemplate bookTemplate;

    @Column(name = "quantity")
    private Long  quantity;

    // region Methods
    public double calculateSubTotal() {
        return quantity * bookTemplate.getBooks().iterator().next().getSellingPrice();
    }
    // endregion
}
