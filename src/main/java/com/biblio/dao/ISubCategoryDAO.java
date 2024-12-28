package com.biblio.dao;

import com.biblio.entity.Category;
import com.biblio.entity.SubCategory;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ISubCategoryDAO {
    SubCategory getEntityById(Long id);
    List<SubCategory> getEntityAll();
    List<String> getTopSubCategory(Long id);
    SubCategory createSubCategory(SubCategory subCategory);
    void updateSubCategory(SubCategory subCategory);
    void deleteSubCategory(Long id);
    Integer countBooksTemplateAll(Long id);
    Integer countBooksTemplateByStatus(Long id, EBookTemplateStatus status);
    Integer countBooksByStatus(Long id, EBookMetadataStatus status);
    Integer countBooksByOrderStatus(Long id, EOrderStatus orderStatus);
    Integer countBooksInRangeByStatus(Long id, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus);
    Integer countOrdersAll(Long id);
    Integer countOrdersByStatus(Long id, EOrderStatus status);
    Integer countOrdersInRangeByStatus(Long id, LocalDateTime from, LocalDateTime to, EOrderStatus status);
    Long calculateValueBooksSold(Long id);
    Long calculateValueBooksSoldInRange(Long id, LocalDateTime from, LocalDateTime to);
    Long calculateValueOrdersSoldByStatus(Long id, EOrderStatus status);
    Double calculateAverageRating(Long id);

    List<SubCategory> findAll();
    List<SubCategory> findByJPQL(Long subCategory);
}
