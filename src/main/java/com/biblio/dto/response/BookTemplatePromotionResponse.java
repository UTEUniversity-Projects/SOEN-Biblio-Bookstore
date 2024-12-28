package com.biblio.dto.response;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BookTemplatePromotionResponse {
    private Long id;
    private String title;
    private Long subCategoryId;
}
