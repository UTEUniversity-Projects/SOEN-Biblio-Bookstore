package com.biblio.service;

import com.biblio.dto.request.SubCategoryCreateRequest;
import com.biblio.dto.request.SubCategoryDeleteRequest;
import com.biblio.dto.request.SubCategoryUpdateRequest;
import com.biblio.dto.response.SubCategoryAnalysisResponse;
import com.biblio.dto.response.SubCategoryCreateResponse;
import com.biblio.dto.response.SubCategoryLineResponse;
import com.biblio.dto.response.SubCategoryProfileResponse;
import com.biblio.entity.SubCategory;

import java.util.List;

public interface ISubCategoryService {
    SubCategory getEntityById(Long id);
    List<SubCategoryLineResponse> getAll();
    SubCategoryProfileResponse getProfileById(Long id);
    SubCategoryAnalysisResponse getAnalysisById(Long id);
    SubCategory create(SubCategoryCreateRequest categoryRequest);
    void update(SubCategoryUpdateRequest categoryRequest);
    void delete(SubCategoryDeleteRequest categoryDeleteRequest);
    Integer countBookTemplate(Long id);
    SubCategoryCreateResponse initCreateSubCategory();

    List<SubCategoryProfileResponse> findAll();
    List<SubCategoryProfileResponse> getAllSubCategoriesById(Long id);
}
