package com.biblio.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySidebarResponse {
    private Long id;
    private String name;
}
