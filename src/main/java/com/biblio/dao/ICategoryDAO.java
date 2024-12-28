package com.biblio.dao;

import com.biblio.dto.request.CategoryCreateRequest;
import com.biblio.dto.request.CategoryDeleteRequest;
import com.biblio.dto.request.CategoryUpdateRequest;
import com.biblio.dto.request.SearchBookRequest;
import com.biblio.dto.response.CategoryBookCountResponse;
import com.biblio.entity.Category;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ICategoryDAO {
    Category getEntityById(Long categoryId);
    List<Category> getEntityAll();
    List<String> getTopSubCategory(Long categoryId);
    Category create(CategoryCreateRequest categoryCreateRequest);
    void update(CategoryUpdateRequest categoryUpdateRequest);
    void delete(CategoryDeleteRequest categoryDeleteRequest);
    Integer countBooksTemplateAll(Long categoryId);
    Integer countBooksTemplateByStatus(Long categoryId, EBookTemplateStatus status);
    Integer countBooksByStatus(Long categoryId, EBookMetadataStatus status);
    Integer countBooksByOrderStatus(Long categoryId, EOrderStatus orderStatus);
    Integer countBooksInRangeByStatus(Long categoryId, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus);
    Integer countOrdersAll(Long categoryId);
    Integer countOrdersByStatus(Long categoryId, EOrderStatus status);
    Integer countOrdersInRangeByStatus(Long categoryId, LocalDateTime from, LocalDateTime to, EOrderStatus status);
    Long calculateValueBooksSold(Long categoryId);
    Long calculateValueBooksSoldInRange(Long categoryId, LocalDateTime from, LocalDateTime to);
    Long calculateValueOrdersSoldByStatus(Long categoryId, EOrderStatus status);
    Double calculateAverageRating(Long categoryId);

    List<CategoryBookCountResponse> countBookPerCategory(SearchBookRequest request);

}
