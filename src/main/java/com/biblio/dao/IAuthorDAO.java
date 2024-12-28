package com.biblio.dao;

import com.biblio.dto.request.AuthorCreateRequest;
import com.biblio.dto.request.AuthorDeleteRequest;
import com.biblio.dto.request.AuthorUpdateRequest;
import com.biblio.entity.Author;
import com.biblio.entity.BookTemplate;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IAuthorDAO {
    Author getEntityById(Long authorId);

    List<Author> getEntityAll();

    List<Author> findByBookTemplate(BookTemplate bookTemplate);

    List<String> getTopSubCategory(Long authorId);

    Author createAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Long id);

    Integer countBooksTemplateAll(Long authorId);

    Integer countBooksTemplateByStatus(Long authorId, EBookTemplateStatus status);

    Integer countBooksByStatus(Long authorId, EBookMetadataStatus status);

    Integer countBooksByOrderStatus(Long authorId, EOrderStatus orderStatus);

    Integer countBooksInRangeByStatus(Long authorId, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus);

    Integer countOrdersAll(Long authorId);

    Integer countOrdersByStatus(Long authorId, EOrderStatus status);

    Integer countOrdersInRangeByStatus(Long authorId, LocalDateTime from, LocalDateTime to, EOrderStatus status);

    Long calculateValueBooksSold(Long authorId);

    Long calculateValueBooksSoldInRange(Long authorId, LocalDateTime from, LocalDateTime to);

    Long calculateValueOrdersSoldByStatus(Long authorId, EOrderStatus status);

    Double calculateAverageRating(Long authorId);
}
