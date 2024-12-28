package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategoryLineResponse {
    String id;
    String name;
    String avatar;
    String shortScript;
    String fullScript;
    String status;
    String category;
    String works;
    String avgRate;
    Double perValueBooksSold;
}
