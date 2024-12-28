package com.biblio.dto.request;

import com.biblio.dto.request.PromotionTargetInsertRequest;
import com.biblio.enumeration.EPromotionStatus;
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
public class PromotionTemplateInsertRequest {
    private String code;
    private Boolean isInfinite;
    private EPromotionTemplateStatus status;
    private EPromotionTemplateType type;


    private Set<PromotionInsertRequest> promotionInsertRequests = new HashSet<>();
}
