package com.biblio.dto.request;

import com.biblio.enumeration.EClassificationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryUpdateRequest {
    String id = "";
    String name = "";
    String shortScript = "";
    String fullScript = "";
    EClassificationStatus status = EClassificationStatus.ACTIVE;
}
