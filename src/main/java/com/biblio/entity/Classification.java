package com.biblio.entity;

import com.biblio.enumeration.EClassificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Classification implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "short_script", nullable = false)
    protected String shortScript;

    @Column(name = "full_script", nullable = false)
    protected String fullScript;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected EClassificationStatus status;

    // endregion

}
