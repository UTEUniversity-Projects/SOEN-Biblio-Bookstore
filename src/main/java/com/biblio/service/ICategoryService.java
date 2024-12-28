package com.biblio.service;

import com.biblio.dto.request.CategoryCreateRequest;
import com.biblio.dto.request.CategoryDeleteRequest;
import com.biblio.dto.request.CategoryUpdateRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.Category;
import com.biblio.dto.request.SearchBookRequest;
import com.biblio.dto.response.CategoryBookCountResponse;
import com.biblio.dto.response.CategoryResponse;
import com.biblio.dto.response.CategorySidebarResponse;

import java.util.List;

public interface ICategoryService {
    Category getEntityById(Long id);
    List<Category> getAllEntities();
    List<CategoryLineResponse> getAll();
    CategoryProfileResponse getProfileById(Long id);
    CategoryAnalysisResponse getAnalysisById(Long id);
    Category create(CategoryCreateRequest categoryRequest);
    void update(CategoryUpdateRequest categoryRequest);
    void delete(CategoryDeleteRequest categoryDeleteRequest);
    Integer countBookTemplate(Long id);

    List<CategorySidebarResponse> getAllCategorySidebarResponse();
    List<CategoryProfileResponse> getAllCategories();
    CategoryResponse findById(Long id);
    List<CategoryBookCountResponse> getBookQuantityPerCategory(SearchBookRequest request);
}
