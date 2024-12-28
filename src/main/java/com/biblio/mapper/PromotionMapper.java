package com.biblio.mapper;

import com.biblio.dto.request.PromotionInsertRequest;
import com.biblio.dto.request.PromotionTargetInsertRequest;
import com.biblio.dto.request.PromotionTargetUpdateRequest;
import com.biblio.dto.request.PromotionUpdateRequest;
import com.biblio.dto.response.PromotionGetResponse;
import com.biblio.dto.response.PromotionResponse;
import com.biblio.dto.response.PromotionTargetResponse;
import com.biblio.entity.Promotion;
import com.biblio.entity.PromotionTarget;
import com.biblio.enumeration.EPromotionStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PromotionMapper {
    // region Entity to DTO

    public static PromotionGetResponse toPromotionGetResponse(List<Promotion> promotions) {
        Promotion promotion = promotions.get(0);
        PromotionGetResponse promotionGetResponse = new PromotionGetResponse();
        promotionGetResponse.setCode(promotion.getPromotionTemplate().getCode());
        promotionGetResponse.setTitle(promotion.getTitle());
        promotionGetResponse.setType(promotion.getPromotionTemplate().getType().toString());

        Long count = (Long) promotions.stream()
                .filter(pro -> pro.getStatus() == EPromotionStatus.NOT_USE)
                .count();
        promotionGetResponse.setQuantity(count);
        return promotionGetResponse;
    }

    public static PromotionResponse toPromotionResponse(Promotion promotion) {
        PromotionResponse promotionRespone = new PromotionResponse();

        promotionRespone.setId(promotion.getId());
        promotionRespone.setTitle(promotion.getTitle());
        promotionRespone.setDescription(promotion.getDescription());

        promotionRespone.setPercentDiscount(promotion.getPercentDiscount());
        promotionRespone.setMinValueApplied(promotion.getMinValueToBeApplied());
        promotionRespone.setDiscountLimit(promotion.getDiscountLimit());

        promotionRespone.setEffectiveDate(promotion.getEffectiveDate().toString());
        promotionRespone.setExpirationDate(promotion.getExpirationDate().toString());

        promotionRespone.setStatus(promotion.getStatus());

        for (PromotionTarget promotionTarget : promotion.getPromotionTargets()) {
            PromotionTargetResponse promotionTargetResponse = new PromotionTargetResponse();
            promotionTargetResponse = PromotionTargetMapper.toPromotionTargetResponse(promotionTarget);
            promotionRespone.getPromotionTargets().add(promotionTargetResponse);
        }

        return promotionRespone;
    }

    // endregion

    // region DTO to Entity

    public static Promotion toPromotion(PromotionInsertRequest promotionInsertRequest) {
        Promotion promotion = new Promotion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        promotion.setTitle(promotionInsertRequest.getTitle());
        promotion.setDescription(promotionInsertRequest.getDescription());

        promotion.setPercentDiscount(promotionInsertRequest.getPercentDiscount());
        promotion.setDiscountLimit(promotionInsertRequest.getDiscountLimit());
        promotion.setMinValueToBeApplied(promotionInsertRequest.getMinValueApplied());

        promotion.setStatus(promotionInsertRequest.getStatus());

        promotion.setEffectiveDate(LocalDateTime.parse(promotionInsertRequest.getEffectiveDate(), formatter));
        promotion.setExpirationDate(LocalDateTime.parse(promotionInsertRequest.getExpirationDate(), formatter));

        for (PromotionTargetInsertRequest promotionTargetInsertRequest: promotionInsertRequest.getPromotionTargets()) {
            PromotionTarget promotionTarget = PromotionTargetMapper.toPromotionTarget(promotionTargetInsertRequest);
            promotionTarget.setPromotion(promotion);
            promotion.getPromotionTargets().add(promotionTarget);
        }
        return promotion;
    }

    public static Promotion toPromotion(PromotionUpdateRequest promotionUpdateRequest) {

        Promotion promotion = new Promotion();

        promotion.setId(promotionUpdateRequest.getId());
        promotion.setTitle(promotionUpdateRequest.getTitle());
        promotion.setDescription(promotionUpdateRequest.getDescription());

        promotion.setPercentDiscount(promotionUpdateRequest.getPercentDiscount());
        promotion.setDiscountLimit(promotionUpdateRequest.getDiscountLimit());
        promotion.setMinValueToBeApplied(promotionUpdateRequest.getMinValueApplied());

        promotion.setStatus(promotionUpdateRequest.getStatus());

        promotion.setEffectiveDate(LocalDateTime.parse(promotionUpdateRequest.getEffectiveDate()));

        promotion.setExpirationDate(LocalDateTime.parse(promotionUpdateRequest.getExpirationDate()));

        System.out.println(promotion.getExpirationDate());

        for (PromotionTargetUpdateRequest promotionTargetUpdateRequest: promotionUpdateRequest.getPromotionTargets()) {
            PromotionTarget promotionTarget = PromotionTargetMapper.toPromotionTarget(promotionTargetUpdateRequest);
            promotionTarget.setPromotion(promotion);
            promotion.getPromotionTargets().add(promotionTarget);
        }
        return promotion;
    }

    // endregion

    // region Functions

    public static String convertDateTime(String inputDateTime) {
        try {
            // Định dạng đầu vào
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Định dạng đầu ra
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

            // Chuyển chuỗi đầu vào thành LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);

            // Trừ 1 giờ
            LocalDateTime updatedDateTime = dateTime.minusHours(1);

            // Trả về chuỗi theo định dạng đầu ra
            return updatedDateTime.format(outputFormatter);
        } catch (Exception e) {
            // Xử lý lỗi và trả về chuỗi mặc định trong trường hợp lỗi
            e.printStackTrace();
            return "Invalid date format";
        }
    }

    // endregion
}
