package com.biblio.mapper;

import com.biblio.constants.StoredFileConstants;
import com.biblio.dao.ICategoryDAO;
import com.biblio.dto.request.SubCategoryCreateRequest;
import com.biblio.dto.request.SubCategoryUpdateRequest;
import com.biblio.dto.response.SubCategoryAnalysisResponse;
import com.biblio.dto.response.SubCategoryCreateResponse;
import com.biblio.dto.response.SubCategoryLineResponse;
import com.biblio.dto.response.SubCategoryProfileResponse;
import com.biblio.entity.Category;
import com.biblio.entity.SubCategory;
import com.biblio.service.ICategoryService;
import com.biblio.utils.FormatterUtil;

import javax.inject.Inject;
import java.util.List;

public class SubCategoryMapper {

    @Inject
    ICategoryService categoryService;

    // Before DTO Request to Entity

    public SubCategory toSubCategory(SubCategoryCreateRequest subCategoryCreateRequest) {
        return SubCategory.builder()
                .name(subCategoryCreateRequest.getName())
                .shortScript(subCategoryCreateRequest.getShortScript())
                .fullScript(subCategoryCreateRequest.getFullScript())
                .status(subCategoryCreateRequest.getStatus())
                .category(categoryService.getEntityById(Long.valueOf(subCategoryCreateRequest.getCategoryId())))
                .build();
    }

    public SubCategory toSubCategory(SubCategoryUpdateRequest subCategoryUpdateRequest) {
        return SubCategory.builder()
                .id(Long.valueOf(subCategoryUpdateRequest.getId()))
                .name(subCategoryUpdateRequest.getName())
                .shortScript(subCategoryUpdateRequest.getShortScript())
                .fullScript(subCategoryUpdateRequest.getFullScript())
                .status(subCategoryUpdateRequest.getStatus())
                .category(categoryService.getEntityById(Long.valueOf(subCategoryUpdateRequest.getCategoryId())))
                .build();
    }

    // End DTO Request to Entity

    // Before Entity to DTO Response

    public static SubCategoryCreateResponse toSubCategoryCreateResponse(List<Category> categories) {
        return SubCategoryCreateResponse.builder()
                .categories(categories)
                .build();
    }

    public static SubCategoryProfileResponse toSubCategoryProfileResponse(SubCategory subCategory) {
        return SubCategoryProfileResponse.builder()
                .id(subCategory.getId().toString())
                .avatar(StoredFileConstants.SUB_CATEGORY_DEFAULT_AVA)
                .name(subCategory.getName())
                .shortScript(subCategory.getShortScript())
                .fullScript(subCategory.getFullScript())
                .status(subCategory.getStatus().name())
                .category(subCategory.getCategory())
                .build();
    }

    public static SubCategoryLineResponse toSubCategoryLineResponse(SubCategory subCategory, Integer works, Double avgRate, Double perValueBooksSold) {
        return SubCategoryLineResponse.builder()
                .id(subCategory.getId().toString())
                .avatar(StoredFileConstants.SUB_CATEGORY_DEFAULT_AVA)
                .name(subCategory.getName())
                .shortScript(FormatterUtil.description(subCategory.getShortScript(), 50))
                .fullScript(FormatterUtil.description(subCategory.getFullScript(), 150))
                .status(subCategory.getStatus().name())
                .category(subCategory.getCategory().getName())
                .works(works.toString())
                .avgRate(FormatterUtil.rating(avgRate))
                .perValueBooksSold(Double.parseDouble(FormatterUtil.percent(perValueBooksSold)))
                .build();
    }

    public static SubCategoryAnalysisResponse toSubCategoryAnalysisResponse(
            SubCategory subCategory,
            Integer works,
            Double avgRate,
            Integer sales,
            Double perSales,
            Integer booksSold,
            Double perBooksSold,
            Long valueBooksSold,
            Double perValueBooksSold,
            Integer booksInStock,
            Integer booksCancelled,
            Integer booksReturned,
            Integer salesThisMonth,
            Integer booksThisMonth,
            Long revenueThisMonth,
            Integer ordersCompleted,
            Long valueOrdersCompleted,
            Integer ordersWaiting,
            Long valueOrdersWaiting,
            Integer orderPacking,
            Long valueOrderPacking,
            Integer orderShipping,
            Long valueOrderShipping,
            Integer ordersCancelled,
            Long valueOrdersCancelled,
            Integer ordersRequestRefund,
            Long valueOrdersRequestRefund,
            Integer ordersRefunded,
            Long valueOrdersRefunded,
            List<String> topSubSubCategory
    ) {
        return SubCategoryAnalysisResponse.builder()
                .id(subCategory.getId().toString())
                .avatar(StoredFileConstants.SUB_CATEGORY_DEFAULT_AVA)
                .name(subCategory.getName())
                .shortScript(subCategory.getShortScript())
                .fullScript(subCategory.getFullScript())
                .status(subCategory.getStatus().name())
                .category(subCategory.getCategory().getName())
                .works(works.toString())
                .avgRate(FormatterUtil.rating(avgRate))
                .sales(sales.toString())
                .perSales(Double.parseDouble(FormatterUtil.percent(perSales)))
                .booksSold(FormatterUtil.dotNumber(Long.valueOf(booksSold)))
                .perBooksSold(Double.parseDouble(FormatterUtil.percent(perBooksSold)))
                .valueBooksSold(FormatterUtil.commaNumber(valueBooksSold))
                .perValueBooksSold(Double.parseDouble(FormatterUtil.percent(perValueBooksSold)))
                .booksInStock(booksInStock.toString())
                .booksCancelled(booksCancelled.toString())
                .booksReturned(booksReturned.toString())
                .salesThisMonth(salesThisMonth.toString())
                .booksThisMonth(booksThisMonth.toString())
                .revenueThisMonth(FormatterUtil.commaNumber(revenueThisMonth))
                .ordersCompleted(ordersCompleted.toString())
                .valueOrdersCompleted(FormatterUtil.commaNumber(valueOrdersCompleted))
                .ordersWaiting(ordersWaiting.toString())
                .valueOrdersWaiting(FormatterUtil.commaNumber(valueOrdersWaiting))
                .orderPacking(orderPacking.toString())
                .valueOrderPacking(FormatterUtil.commaNumber(valueOrderPacking))
                .orderShipping(orderShipping.toString())
                .valueOrderShipping(FormatterUtil.commaNumber(valueOrderShipping))
                .ordersCancelled(ordersCancelled.toString())
                .valueOrdersCancelled(FormatterUtil.commaNumber(valueOrdersCancelled))
                .ordersRequestRefund(ordersRequestRefund.toString())
                .valueOrdersRequestRefund(FormatterUtil.commaNumber(valueOrdersRequestRefund))
                .ordersRefunded(ordersRefunded.toString())
                .valueOrdersRefunded(FormatterUtil.commaNumber(valueOrdersRefunded))
                .topSubCategory(topSubSubCategory)
                .build();
    }

    // End Entity to DTO Response

}
