package com.biblio.mapper;

import com.biblio.constants.StoredFileConstants;
import com.biblio.dto.request.CategoryCreateRequest;
import com.biblio.dto.request.CategoryUpdateRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.Category;
import com.biblio.utils.FormatterUtil;

import java.util.List;

public class CategoryMapper {

    // Before DTO Request to Entity

    public static Category toCategory(CategoryCreateRequest categoryCreateRequest) {
        return Category.builder()
                .name(categoryCreateRequest.getName())
                .shortScript(categoryCreateRequest.getShortScript())
                .fullScript(categoryCreateRequest.getFullScript())
                .status(categoryCreateRequest.getStatus())
                .build();
    }

    public static Category toCategory(CategoryUpdateRequest categoryUpdateRequest) {
        return Category.builder()
                .id(Long.valueOf(categoryUpdateRequest.getId()))
                .name(categoryUpdateRequest.getName())
                .shortScript(categoryUpdateRequest.getShortScript())
                .fullScript(categoryUpdateRequest.getFullScript())
                .status(categoryUpdateRequest.getStatus())
                .build();
    }

    // End DTO Request to Entity

    // Before Entity to DTO Response

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .shortScript(category.getShortScript())
                .fullScript(category.getFullScript())
                .status(category.getStatus().toString())
                .build();
    }

    public static CategoryProfileResponse toCategoryProfileResponse(Category category) {
        return CategoryProfileResponse.builder()
                .id(category.getId().toString())
                .avatar(StoredFileConstants.CATEGORY_DEFAULT_AVA)
                .name(category.getName())
                .shortScript(category.getShortScript())
                .fullScript(category.getFullScript())
                .status(category.getStatus().name())
                .build();
    }

    public static CategoryLineResponse toCategoryLineResponse(Category category, Integer works, Double avgRate, Double perValueBooksSold) {
        return CategoryLineResponse.builder()
                .id(category.getId().toString())
                .avatar(StoredFileConstants.CATEGORY_DEFAULT_AVA)
                .name(category.getName())
                .shortScript(FormatterUtil.description(category.getShortScript(), 50))
                .fullScript(FormatterUtil.description(category.getFullScript(), 150))
                .status(category.getStatus().name())
                .works(works.toString())
                .avgRate(FormatterUtil.rating(avgRate))
                .perValueBooksSold(Double.parseDouble(FormatterUtil.percent(perValueBooksSold)))
                .build();
    }

    public static CategoryAnalysisResponse toCategoryAnalysisResponse(
            Category category,
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
            List<String> topSubCategory
    ) {
        return CategoryAnalysisResponse.builder()
                .id(category.getId().toString())
                .avatar(StoredFileConstants.CATEGORY_DEFAULT_AVA)
                .name(category.getName())
                .shortScript(category.getShortScript())
                .fullScript(category.getFullScript())
                .status(category.getStatus().name())
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
                .topSubCategory(topSubCategory)
                .build();
    }

    public static CategorySidebarResponse toCategorySidebarResponse(Category category) {
        return CategorySidebarResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    // End Entity to DTO Response

}
