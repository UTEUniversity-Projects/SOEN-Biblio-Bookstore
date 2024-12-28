package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorProfileResponse {
    String id;
    String name;
    String avatar;
    String introduction;
    String joinAt;
}
