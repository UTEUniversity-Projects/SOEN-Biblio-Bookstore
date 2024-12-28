package com.biblio.dto.request;

import com.biblio.entity.Category;
import com.biblio.enumeration.EClassificationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreateRequest {
    String name = "";
    String shortScript = "";
    String fullScript = "";
    EClassificationStatus status = EClassificationStatus.ACTIVE;
}
