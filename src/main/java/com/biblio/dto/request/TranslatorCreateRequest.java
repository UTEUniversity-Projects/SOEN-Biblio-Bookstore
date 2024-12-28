package com.biblio.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranslatorCreateRequest {
    String name;
    String avatar;
    String introduction;
    String joinAt;
}
