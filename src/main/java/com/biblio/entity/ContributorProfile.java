package com.biblio.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ContributorProfile implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "introduction", nullable = false, columnDefinition = "longtext")
    protected String introduction;

    @Column(name = "join_at", nullable = false)
    protected LocalDateTime joinAt;

    @Column(name = "avatar", nullable = false)
    private String avatar;

    // endregion
}
