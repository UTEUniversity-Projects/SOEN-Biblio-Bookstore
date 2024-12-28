package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherLineResponse {
    String id;
    String name;
    String avatar;
    String introduction;
    String joinAt;
    String works;
    String avgRate;
    Double perValueBooksSold;
}
