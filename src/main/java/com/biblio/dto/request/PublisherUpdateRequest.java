package com.biblio.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherUpdateRequest {
    String id;
    String name;
    String avatar;
    String introduction;
    String joinAt;
}
