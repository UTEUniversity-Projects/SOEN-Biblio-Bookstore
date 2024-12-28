package com.biblio.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherRequest {
    String id;
    String name;
    String avatar;
    String introduction;
    String joinAt;
}
