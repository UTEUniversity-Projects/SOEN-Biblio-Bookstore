package com.biblio.entity;

import com.biblio.enumeration.EAccountStatus;
import com.biblio.enumeration.EUserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EUserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EAccountStatus status;

    // endregion

    // region Relationships

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private Customer customer;
//
//    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
//    private Staff staff;
//
//    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
//    private Owner owner;

    // endregion

}
