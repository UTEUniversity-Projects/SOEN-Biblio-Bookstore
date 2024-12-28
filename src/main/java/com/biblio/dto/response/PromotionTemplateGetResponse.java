package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PromotionTemplateGetResponse {
    private Long id;
    private String code;
    private String title;
    private Double discountLimit;
    private Double percentDiscount;
    private Long quantity;
    private String type;
    private String status;
}
