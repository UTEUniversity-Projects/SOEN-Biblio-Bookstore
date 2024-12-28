package com.biblio.mapper;

import com.biblio.dto.request.PromotionInsertRequest;
import com.biblio.dto.request.PromotionTemplateInsertRequest;
import com.biblio.dto.request.PromotionTemplateUpdateRequest;
import com.biblio.dto.request.PromotionUpdateRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.Promotion;
import com.biblio.entity.PromotionTarget;
import com.biblio.entity.PromotionTemplate;
import com.biblio.enumeration.EPromotionStatus;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionTemplateMapper {
    public static PromotionTemplateGetResponse toPromotionTemplateGetResponse(List<Promotion> promotions) {
        Promotion promotion = findRelevantPromotion(promotions);

        PromotionTemplateGetResponse response = new PromotionTemplateGetResponse();

        mapCommonFields(response, promotion, promotions);

        response.setDiscountLimit(promotion.getDiscountLimit());
        response.setPercentDiscount(promotion.getPercentDiscount());

        return response;
    }

    public static PromotionTemplateGetDetailsResponse toPromotionTemplateGetDetailsResponse(List<Promotion> promotions) {
        Promotion promotion = findRelevantPromotion(promotions);

        PromotionTemplateGetDetailsResponse response = new PromotionTemplateGetDetailsResponse();

        mapCommonFields(response, promotion, promotions);

        response.setDescription(promotion.getDescription());
        response.setEffectiveDate(promotion.getEffectiveDate().toString());
        response.setExpirationDate(promotion.getExpirationDate().toString());

        response.setDiscountLimit(promotion.getDiscountLimit());
        response.setPercentDiscount(promotion.getPercentDiscount());
        response.setMinValueToApplied(promotion.getMinValueToBeApplied());

        for (PromotionTarget promotionTarget : promotion.getPromotionTargets()) {
            PromotionTargetResponse promotionTargetResponse = new PromotionTargetResponse();
            promotionTargetResponse.setType(promotionTarget.getType().toString());
            promotionTargetResponse.setApplicableObjectId(promotionTarget.getApplicableObjectId());
            response.getPromotionTargetResponse().add(promotionTargetResponse);
        }

        return response;
    }

    public static PromotionTemplate toPromotionTemplate(PromotionTemplateInsertRequest promotionTemplateInsertRequest) {
        PromotionTemplate promotionTemplate = new PromotionTemplate();

        promotionTemplate.setCode(promotionTemplateInsertRequest.getCode());
        promotionTemplate.setType(promotionTemplateInsertRequest.getType());
        promotionTemplate.setStatus(promotionTemplateInsertRequest.getStatus());
        promotionTemplate.setInfinite(promotionTemplateInsertRequest.getIsInfinite());
        promotionTemplate.setCreatedAt(LocalDateTime.now());

        for (PromotionInsertRequest promotionInsertRequest : promotionTemplateInsertRequest.getPromotionInsertRequests()) {
            Promotion promotion = new Promotion();
            promotion = PromotionMapper.toPromotion(promotionInsertRequest);
            promotion.setPromotionTemplate(promotionTemplate);
            promotionTemplate.getPromotions().add(promotion);
        }

        return promotionTemplate;
    }

    public static PromotionTemplate toPromotionTemplate(PromotionTemplateUpdateRequest promotionTemplateUpdateRequest) {
        PromotionTemplate promotionTemplate = new PromotionTemplate();

        promotionTemplate.setId(promotionTemplateUpdateRequest.getId());
        promotionTemplate.setCode(promotionTemplateUpdateRequest.getCode());
        promotionTemplate.setType(promotionTemplateUpdateRequest.getType());
        promotionTemplate.setStatus(promotionTemplateUpdateRequest.getStatus());
        promotionTemplate.setInfinite(promotionTemplateUpdateRequest.isInfinite());
        promotionTemplate.setCreatedAt(LocalDateTime.parse(promotionTemplateUpdateRequest.getCreateAt()));

        for (PromotionUpdateRequest promotionUpdateRequest : promotionTemplateUpdateRequest.getPromotionUpdates()) {
            Promotion promotion = new Promotion();
            promotion = PromotionMapper.toPromotion(promotionUpdateRequest);
            promotion.setPromotionTemplate(promotionTemplate);
            promotionTemplate.getPromotions().add(promotion);
        }

        return promotionTemplate;
    }
    public static PromotionTemplateResponse toPromotionTemplateResponse (PromotionTemplate promotionTemplate) {
        PromotionTemplateResponse promotionTemplateResponse = new PromotionTemplateResponse();

        promotionTemplateResponse.setId(promotionTemplate.getId());

        promotionTemplateResponse.setCode(promotionTemplate.getCode());
        promotionTemplateResponse.setIsInfinite(promotionTemplate.isInfinite());
        promotionTemplateResponse.setCreateAt(promotionTemplate.getCreatedAt().toString());
        promotionTemplateResponse.setStatus(promotionTemplate.getStatus());
        promotionTemplateResponse.setType(promotionTemplate.getType());

        for (Promotion promotion : promotionTemplate.getPromotions()) {
            PromotionResponse promotionResponse = new PromotionResponse();
            promotionResponse = PromotionMapper.toPromotionResponse(promotion);
            promotionTemplateResponse.getPromotions().add(promotionResponse);
        }
        return promotionTemplateResponse;
    }

    // Tìm promotion phù hợp
    private static Promotion findRelevantPromotion(List<Promotion> promotions) {
        return promotions.stream()
                .filter(pro -> pro.getStatus() == EPromotionStatus.NOT_USE)
                .findFirst()
                .orElseGet(() -> promotions.stream()
                        .filter(pro -> pro.getStatus() == EPromotionStatus.USED)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No promotion found with NOT_USE or USED status"))
                );
    }

    private static void mapCommonFields(Object response, Promotion promotion, List<Promotion> promotions) {
        if (response instanceof PromotionTemplateGetResponse) {
            PromotionTemplateGetResponse res = (PromotionTemplateGetResponse) response;
            res.setId(promotion.getPromotionTemplate().getId());
            res.setCode(promotion.getPromotionTemplate().getCode());
            res.setTitle(promotion.getTitle());
            res.setType(promotion.getPromotionTemplate().getType().toString());
            res.setStatus(promotion.getPromotionTemplate().getStatus().toString());
            res.setQuantity(getQuantity(promotion, promotions));
        } else if (response instanceof PromotionTemplateGetDetailsResponse) {
            PromotionTemplateGetDetailsResponse res = (PromotionTemplateGetDetailsResponse) response;
            res.setId(promotion.getPromotionTemplate().getId());
            res.setCode(promotion.getPromotionTemplate().getCode());
            res.setTitle(promotion.getTitle());
            res.setType(promotion.getPromotionTemplate().getType().toString());
            res.setStatus(promotion.getPromotionTemplate().getStatus().toString());
            res.setQuantity(getQuantity(promotion, promotions));
        }
    }

    private static Long getQuantity(Promotion promotion, List<Promotion> promotions) {
        return !promotion.getPromotionTemplate().isInfinite()
                ? promotions.stream().filter(pro -> pro.getStatus() == EPromotionStatus.NOT_USE).count()
                : -1L;
    }


}
