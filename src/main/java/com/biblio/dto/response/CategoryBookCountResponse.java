package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryBookCountResponse {
    private Long id;
    private String categoryName;
    private long bookCount;
}
