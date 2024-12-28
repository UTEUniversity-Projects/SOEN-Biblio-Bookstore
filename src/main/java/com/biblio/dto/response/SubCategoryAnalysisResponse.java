package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategoryAnalysisResponse {
    String id;
    String name;
    String avatar;
    String shortScript;
    String fullScript;
    String status;
    String category;
    String works;
    String avgRate;
    String sales;
    Double perSales;
    String booksSold;
    Double perBooksSold;
    String valueBooksSold;
    Double perValueBooksSold;

    String booksInStock;
    String booksCancelled;
    String booksReturned;

    String salesThisMonth;
    String booksThisMonth;
    String revenueThisMonth;

    String ordersCompleted;
    String valueOrdersCompleted;
    String ordersWaiting;
    String valueOrdersWaiting;
    String orderPacking;
    String valueOrderPacking;
    String orderShipping;
    String valueOrderShipping;
    String ordersCancelled;
    String valueOrdersCancelled;
    String ordersRequestRefund;
    String valueOrdersRequestRefund;
    String ordersRefunded;
    String valueOrdersRefunded;

    List<String> topSubCategory;
}
