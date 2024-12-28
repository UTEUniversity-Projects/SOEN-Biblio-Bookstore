package com.biblio.entity;

import com.biblio.enumeration.EBookMetadataStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_metadata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookMetadata implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "opening_date", nullable = false)
    private LocalDateTime openingDate;

    @Column(name = "import_price", nullable = false)
    private double importPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EBookMetadataStatus status;

    // endregion Attributes

    // region Relationships

    @OneToOne(mappedBy = "bookMetadata", cascade = CascadeType.ALL)
    private Book book;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "book_metadata_tag",
            joinColumns = @JoinColumn(name = "book_metadata_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false)
    )
    private Set<Tag> tags = new HashSet<>();

    // endregion

}
