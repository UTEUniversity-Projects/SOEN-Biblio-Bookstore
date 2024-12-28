package com.biblio.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Author extends ContributorProfile implements Serializable {

    // region Relationships

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "author_book_template",
            joinColumns = @JoinColumn(name = "author_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "book_template_id", nullable = false))
    private Set<BookTemplate> bookTemplates = new HashSet<>();

    // endregion

}
