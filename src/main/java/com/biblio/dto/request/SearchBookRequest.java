package com.biblio.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchBookRequest {
    String title = "";
    Long categoryId = null;
    String sortBy = null;
    int perPage;
    int pageNumber = 1;
    long minPrice = 0;
    long maxPrice = Long.MAX_VALUE;
    String condition = null;
    String format = null;
    double reviewRate = 0;
}
