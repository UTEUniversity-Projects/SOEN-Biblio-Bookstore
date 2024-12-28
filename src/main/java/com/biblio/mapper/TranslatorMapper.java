package com.biblio.mapper;

import com.biblio.dto.request.TranslatorCreateRequest;
import com.biblio.dto.request.TranslatorUpdateRequest;
import com.biblio.dto.response.TranslatorAnalysisResponse;
import com.biblio.dto.response.TranslatorLineResponse;
import com.biblio.dto.response.TranslatorProfileResponse;
import com.biblio.entity.Translator;
import com.biblio.utils.FormatterUtil;

import java.time.LocalDateTime;
import java.util.List;

public class TranslatorMapper {

    // Before DTO Request to Entity

    public static Translator toTranslatorCreate(TranslatorCreateRequest translatorCreateRequest) {
        return Translator.builder()
                .name(translatorCreateRequest.getName())
                .avatar(translatorCreateRequest.getAvatar())
                .introduction(translatorCreateRequest.getIntroduction())
                .joinAt(LocalDateTime.now())
                .build();
    }

    public static Translator toTranslatorUpdate(TranslatorUpdateRequest translatorUpdateRequest, Translator translator) {
        translator.setName(translatorUpdateRequest.getName());
        translator.setAvatar(translatorUpdateRequest.getAvatar());
        translator.setIntroduction(translatorUpdateRequest.getIntroduction());
        translator.setJoinAt(translator.getJoinAt());

        return translator;
    }

    // End DTO Request to Entity

    // Before Entity to DTO Response

    public static TranslatorProfileResponse toTranslatorProfileResponse(Translator translator) {
        return TranslatorProfileResponse.builder()
                .id(translator.getId().toString())
                .name(translator.getName())
                .avatar(translator.getAvatar())
                .introduction(translator.getIntroduction())
                .joinAt(FormatterUtil.toDateTimeString(translator.getJoinAt()))
                .build();
    }

    public static TranslatorLineResponse toTranslatorLineResponse(Translator translator, Integer works, Double avgRate, Double perValueBooksSold) {
        return TranslatorLineResponse.builder()
                .id(translator.getId().toString())
                .name(translator.getName())
                .avatar(translator.getAvatar())
                .introduction(FormatterUtil.description(translator.getIntroduction(), 150))
                .joinAt(FormatterUtil.toDateTimeString(translator.getJoinAt()))
                .works(works.toString())
                .avgRate(FormatterUtil.rating(avgRate))
                .perValueBooksSold(Double.parseDouble(FormatterUtil.percent(perValueBooksSold)))
                .build();
    }

    public static TranslatorAnalysisResponse toTranslatorAnalysisResponse(
            Translator translator,
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
        return TranslatorAnalysisResponse.builder()
                .id(translator.getId().toString())
                .name(translator.getName())
                .avatar(translator.getAvatar())
                .introduction(translator.getIntroduction())
                .joinAt(FormatterUtil.toDateTimeString(translator.getJoinAt()))
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

    // End Entity to DTO Response

}
