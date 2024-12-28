package com.biblio.dao;

import com.biblio.entity.Publisher;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IPublisherDAO {
    Publisher getEntityById(Long publisherId);

    List<Publisher> getEntityAll();

    List<String> getTopSubCategory(Long publisherId);

    Publisher createPublisher(Publisher publisher);

    void updatePublisher(Publisher publisher);

    void deletePublisher(Long id);

    Integer countBooksTemplateAll(Long publisherId);

    Integer countBooksTemplateByStatus(Long publisherId, EBookTemplateStatus status);

    Integer countBooksByStatus(Long publisherId, EBookMetadataStatus status);

    Integer countBooksByOrderStatus(Long publisherId, EOrderStatus orderStatus);

    Integer countBooksInRangeByStatus(Long publisherId, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus);

    Integer countOrdersAll(Long publisherId);

    Integer countOrdersByStatus(Long publisherId, EOrderStatus status);

    Integer countOrdersInRangeByStatus(Long publisherId, LocalDateTime from, LocalDateTime to, EOrderStatus status);

    Long calculateValueBooksSold(Long publisherId);

    Long calculateValueBooksSoldInRange(Long publisherId, LocalDateTime from, LocalDateTime to);

    Long calculateValueOrdersSoldByStatus(Long publisherId, EOrderStatus status);

    Double calculateAverageRating(Long publisherId);
}
