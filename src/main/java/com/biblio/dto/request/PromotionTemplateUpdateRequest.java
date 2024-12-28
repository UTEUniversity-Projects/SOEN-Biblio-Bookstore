package com.biblio.dto.request;

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
public class PromotionTemplateUpdateRequest {
    private Long id;
    private String code;
    private String createAt;
    private boolean isInfinite;
    private EPromotionTemplateStatus status;
    private EPromotionTemplateType type;
    private Set<PromotionUpdateRequest> promotionUpdates = new HashSet<>();

}
