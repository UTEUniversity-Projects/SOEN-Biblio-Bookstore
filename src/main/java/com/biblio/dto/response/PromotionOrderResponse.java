package com.biblio.dto.response;

import com.biblio.enumeration.EPromotionTemplateType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PromotionOrderResponse {
    private Long id;
    private String code;
    private EPromotionTemplateType promotionType;
    private double discountAmount;
}