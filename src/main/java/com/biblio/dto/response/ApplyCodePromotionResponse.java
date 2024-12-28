package com.biblio.dto.response;

import com.biblio.enumeration.EPromotionTemplateType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ApplyCodePromotionResponse {
    private Long promotionId;
    private String promotionCode;
    private Double discountLimit;
    private Double minValueToBeApplied;
    private EPromotionTemplateType type;
    private String message;
}