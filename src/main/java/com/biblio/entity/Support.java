package com.biblio.entity;

import com.biblio.enumeration.ESupportStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "support")
@NoArgsConstructor
@Getter
@Setter
public class Support implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // endregion

    // region Relationships

    @ManyToOne(cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "support")
    private ResponseSupport responseSupport;

    // endregion

}
