package com.biblio.dto.response;

import com.biblio.enumeration.EPromotionTemplateStatus;
import com.biblio.enumeration.EPromotionTemplateType;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class PromotionTemplateResponse {
    private Long id;
    private String code;
    private String createAt;
    private Boolean isInfinite;
    private EPromotionTemplateStatus status;
    private EPromotionTemplateType type;
    private Set<PromotionResponse> promotions = new HashSet<>();
}
