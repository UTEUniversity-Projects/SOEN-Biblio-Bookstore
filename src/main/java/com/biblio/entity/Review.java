package com.biblio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@NoArgsConstructor
@Getter
@Setter
public class Review implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "ready_to_introduce", nullable = false, columnDefinition = "bit")
    private boolean readyToIntroduce;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // endregion

    // region Relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_template_id", nullable = false)
    private BookTemplate bookTemplate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "review")
    private ResponseReview responseReview;

    // endregion
}
