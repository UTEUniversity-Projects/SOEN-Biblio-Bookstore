package com.biblio.entity;

import com.biblio.enumeration.EBookAgeRecommend;
import com.biblio.enumeration.EBookCondition;
import com.biblio.enumeration.EBookFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "longtext")
    private String description;

    @Column(name = "selling_price", nullable = false)
    private double sellingPrice;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;

    @Column(name = "edition", nullable = false)
    private int edition;

    @Column(name = "code_ISBN10", nullable = false)
    private String codeISBN10;

    @Column(name = "code_ISBN13", nullable = false)
    private String codeISBN13;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private EBookFormat format;

    @Column(name = "hand_cover", nullable = false)
    private int handcover;

    @Column(name = "length", nullable = false)
    private double length;

    @Column(name = "width", nullable = false)
    private double width;

    @Column(name = "height", nullable = false)
    private double height;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "[condition]")
    private EBookCondition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommended_age", nullable = false)
    private EBookAgeRecommend recommendedAge;

    // endregion

    // region Relationships

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_template_id", nullable = false)
    private BookTemplate bookTemplate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_metadata_id", nullable = false)
    private BookMetadata bookMetadata;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    private Set<OrderItem> orderItems = new HashSet<>();

    // endregion

    // region Methods

    // endregion
}