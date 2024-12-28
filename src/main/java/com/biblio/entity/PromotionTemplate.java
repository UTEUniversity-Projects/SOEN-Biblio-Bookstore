package com.biblio.entity;

import com.biblio.enumeration.EPromotionTemplateStatus;
import com.biblio.enumeration.EPromotionTemplateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "promotion_template")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionTemplate implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "is_infinite", nullable = false)
    private boolean isInfinite;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EPromotionTemplateType type;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EPromotionTemplateStatus status;

    // endregion

    // region Relationships

    @OneToMany(mappedBy = "promotionTemplate", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Promotion> promotions = new HashSet<>();

    // endregion
}
