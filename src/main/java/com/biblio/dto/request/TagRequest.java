package com.biblio.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagRequest {
    String id;
    String name;
    String shortScript;
    String fullScript;
    String status;
}
