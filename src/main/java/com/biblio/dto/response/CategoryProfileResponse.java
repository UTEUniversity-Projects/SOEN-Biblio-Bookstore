package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryProfileResponse {
    String id;
    String name;
    String avatar;
    String shortScript;
    String fullScript;
    String status;
}
