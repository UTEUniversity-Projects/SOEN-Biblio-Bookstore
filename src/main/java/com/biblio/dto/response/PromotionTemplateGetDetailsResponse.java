package com.biblio.dto.response;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PromotionTemplateGetDetailsResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private Double discountLimit;
    private Double percentDiscount;
    private Double minValueToApplied;

    private String effectiveDate;
    private String expirationDate;

    private Long quantity;
    private String type;
    private String status;

    private Set<PromotionTargetResponse> promotionTargetResponse = new HashSet<>();
}
