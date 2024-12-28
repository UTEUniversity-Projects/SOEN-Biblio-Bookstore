package com.biblio.dto.response;

import com.biblio.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategoryProfileResponse {
    String id;
    String name;
    String avatar;
    String shortScript;
    String fullScript;
    String status;
    Category category;
}
