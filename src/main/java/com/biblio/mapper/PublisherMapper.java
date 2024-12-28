package com.biblio.mapper;

import com.biblio.constants.StoredFileConstants;
import com.biblio.dto.request.PublisherCreateRequest;
import com.biblio.dto.request.PublisherUpdateRequest;
import com.biblio.dto.response.PublisherAnalysisResponse;
import com.biblio.dto.response.PublisherLineResponse;
import com.biblio.dto.response.PublisherProfileResponse;
import com.biblio.entity.Publisher;
import com.biblio.utils.FormatterUtil;

import java.time.LocalDateTime;
import java.util.List;

public class PublisherMapper {

    // Before DTO Request to Entity

    public static Publisher toPublisherCreate(PublisherCreateRequest publisherCreateRequest) {
        return Publisher.builder()
                .name(publisherCreateRequest.getName())
                .avatar(publisherCreateRequest.getAvatar())
                .introduction(publisherCreateRequest.getIntroduction())
                .joinAt(LocalDateTime.now())
                .build();
    }

    public static Publisher toPublisherUpdate(PublisherUpdateRequest publisherUpdateRequest, Publisher publisher) {
        publisher.setName(publisherUpdateRequest.getName());
        publisher.setAvatar(publisherUpdateRequest.getAvatar());
        publisher.setIntroduction(publisherUpdateRequest.getIntroduction());
        publisher.setJoinAt(publisher.getJoinAt());

        return publisher;
    }

    // End DTO Request to Entity

    // Before Entity to DTO Response

    public static PublisherProfileResponse toPublisherProfileResponse(Publisher publisher) {
        return PublisherProfileResponse.builder()
                .id(publisher.getId().toString())
                .name(publisher.getName())
                .avatar(publisher.getAvatar())
                .introduction(publisher.getIntroduction())
                .joinAt(FormatterUtil.toDateTimeString(publisher.getJoinAt()))
                .build();
    }

    public static PublisherLineResponse toPublisherLineResponse(Publisher publisher, Integer works, Double avgRate, Double perValueBooksSold) {
        return PublisherLineResponse.builder()
                .id(publisher.getId().toString())
                .name(publisher.getName())
                .avatar(StoredFileConstants.PUBLISHER_DEFAULT_AVA)
                .introduction(FormatterUtil.description(publisher.getIntroduction(), 150))
                .joinAt(FormatterUtil.toDateTimeString(publisher.getJoinAt()))
                .works(works.toString())
                .avgRate(FormatterUtil.rating(avgRate))
                .perValueBooksSold(Double.parseDouble(FormatterUtil.percent(perValueBooksSold)))
                .build();
    }

    public static PublisherAnalysisResponse toPublisherAnalysisResponse(
            Publisher publisher,
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
        return PublisherAnalysisResponse.builder()
                .id(publisher.getId().toString())
                .name(publisher.getName())
                .avatar(publisher.getAvatar())
                .introduction(publisher.getIntroduction())
                .joinAt(FormatterUtil.toDateTimeString(publisher.getJoinAt()))
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
