package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLineResponse {
    Long id;
    String imageUrl;
    String title;
    String publisher;
    Double perValueBooksSold;
    String booksSold;
    String avgRate;
    String sellingPrice;
    String statusStyle;
    String statusDisplay;
}
