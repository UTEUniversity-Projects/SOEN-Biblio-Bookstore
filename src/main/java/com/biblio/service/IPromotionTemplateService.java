package com.biblio.service;

import com.biblio.dto.request.PromotionTemplateInsertRequest;
import com.biblio.dto.request.PromotionTemplateUpdateRequest;
import com.biblio.dto.response.*;
import com.biblio.enumeration.EPromotionTemplateType;

import java.util.List;

public interface IPromotionTemplateService {
    PromotionTemplateGetDetailsResponse getPromotionTemplateById(Long id);

    PromotionTemplateResponse getPromotionTemplateDetailsById(Long id);

    PromotionTemplateGetDetailsResponse getPromotionTemplateByCode(String code);

    List<PromotionTemplateGetResponse> getAllPromotionTemplates();

    void updatePromotionTemplate(PromotionTemplateUpdateRequest promotionTemplateUpdateRequest);

    PromotionTemplateResponse insertPromotionTemplate(PromotionTemplateInsertRequest promotionTemplateInsertRequest);

    Boolean isCodeExisted(String code);

    Double percentDiscountOfBook(Long bookTemplateId);

    ApplyCodePromotionResponse applyCodePromotion(String code, Double amount, EPromotionTemplateType type);

    Boolean stopPromotionByCode(String code);

    List<DiscountResponse> getAllDiscounts();

    Double percentDiscount(Long bookTemplateId, List<DiscountResponse> discounts);
}
