package com.biblio.mapper;

import com.biblio.dto.request.AuthorCreateRequest;
import com.biblio.dto.request.AuthorUpdateRequest;
import com.biblio.dto.response.AuthorAnalysisResponse;
import com.biblio.dto.response.AuthorLineResponse;
import com.biblio.dto.response.AuthorProfileResponse;
import com.biblio.entity.Author;
import com.biblio.utils.FormatterUtil;

import java.time.LocalDateTime;
import java.util.List;

public class AuthorMapper {

    // Before DTO Request to Entity

    public static Author toAuthorCreate(AuthorCreateRequest authorCreateRequest) {
        return Author.builder()
                .name(authorCreateRequest.getName())
                .avatar(authorCreateRequest.getAvatar())
                .introduction(authorCreateRequest.getIntroduction())
                .joinAt(LocalDateTime.now())
                .build();
    }

    public static Author toAuthorUpdate(AuthorUpdateRequest authorUpdateRequest, Author author) {
        author.setName(authorUpdateRequest.getName());
        author.setAvatar(authorUpdateRequest.getAvatar());
        author.setIntroduction(authorUpdateRequest.getIntroduction());
        author.setJoinAt(author.getJoinAt());

        return author;
    }

    // End DTO Request to Entity

    // Before Entity to DTO Response

    public static AuthorProfileResponse toAuthorProfileResponse(Author author) {
        return AuthorProfileResponse.builder()
                .id(author.getId().toString())
                .name(author.getName())
                .avatar(author.getAvatar())
                .introduction(author.getIntroduction())
                .joinAt(FormatterUtil.toDateTimeString(author.getJoinAt()))
                .build();
    }

    public static AuthorLineResponse toAuthorLineResponse(Author author, Integer works, Double avgRate, Double perValueBooksSold) {
        return AuthorLineResponse.builder()
                .id(author.getId().toString())
                .name(author.getName())
                .avatar(author.getAvatar())
                .introduction(FormatterUtil.description(author.getIntroduction(), 150))
                .joinAt(FormatterUtil.toDateTimeString(author.getJoinAt()))
                .works(works.toString())
                .avgRate(FormatterUtil.rating(avgRate))
                .perValueBooksSold(Double.parseDouble(FormatterUtil.percent(perValueBooksSold)))
                .build();
    }

    public static AuthorAnalysisResponse toAuthorAnalysisResponse(
            Author author,
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
        return AuthorAnalysisResponse.builder()
                .id(author.getId().toString())
                .name(author.getName())
                .avatar(author.getAvatar())
                .introduction(author.getIntroduction())
                .joinAt(FormatterUtil.toDateTimeString(author.getJoinAt()))
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
