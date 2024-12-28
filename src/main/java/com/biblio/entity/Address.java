package com.biblio.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class
Address implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nation", nullable = false)
    private String nation;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "village", nullable = false)
    private String village;

    @Column(name = "detail", nullable = false)
    private String detail;

    // endregion

    // region Relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Shipping> shippings = new HashSet<>();

    // endregion

    public String getFullAddress() {
        StringBuilder fullAddress = new StringBuilder();

        if (detail != null && !detail.isEmpty()) {
            fullAddress.append(detail).append(", ");
        }
        if (village != null && !village.isEmpty()) {
            fullAddress.append(village).append(", ");
        }
        if (district != null && !district.isEmpty()) {
            fullAddress.append(district).append(", ");
        }
        if (province != null && !province.isEmpty()) {
            fullAddress.append(province).append(", ");
        }
        if (nation != null && !nation.isEmpty()) {
            fullAddress.append(nation);
        }

        if (!fullAddress.isEmpty() && fullAddress.charAt(fullAddress.length() - 2) == ',') {
            fullAddress.setLength(fullAddress.length() - 2);
        }

        return fullAddress.toString();
    }
}
