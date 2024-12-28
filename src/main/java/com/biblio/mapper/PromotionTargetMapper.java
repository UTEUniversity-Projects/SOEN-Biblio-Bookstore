package com.biblio.mapper;

import com.biblio.dto.request.PromotionTargetInsertRequest;
import com.biblio.dto.request.PromotionTargetUpdateRequest;
import com.biblio.dto.response.PromotionTargetResponse;
import com.biblio.entity.PromotionTarget;

public class PromotionTargetMapper {
    public static PromotionTargetResponse toPromotionTargetResponse(PromotionTarget promotionTarget) {
        PromotionTargetResponse promotionTargetResponse = new PromotionTargetResponse();
        promotionTargetResponse.setId(promotionTarget.getId());
        promotionTargetResponse.setApplicableObjectId(promotionTarget.getApplicableObjectId());
        promotionTargetResponse.setType(promotionTarget.getType().toString());
        return promotionTargetResponse;
    }

    public static PromotionTarget toPromotionTarget(PromotionTargetInsertRequest promotionTargetInsertRequest) {
        PromotionTarget promotionTarget = new PromotionTarget();
        promotionTarget.setApplicableObjectId(promotionTargetInsertRequest.getApplicableObjectId());
        promotionTarget.setType(promotionTargetInsertRequest.getType());
        return promotionTarget;
    }

    public static PromotionTarget toPromotionTarget(PromotionTargetUpdateRequest promotionTargetUpdateRequest) {
        PromotionTarget promotionTarget = new PromotionTarget();
        promotionTarget.setId(promotionTargetUpdateRequest.getId());
        promotionTarget.setApplicableObjectId(promotionTargetUpdateRequest.getApplicableObjectId());
        promotionTarget.setType(promotionTargetUpdateRequest.getType());
        return promotionTarget;
    }
}
