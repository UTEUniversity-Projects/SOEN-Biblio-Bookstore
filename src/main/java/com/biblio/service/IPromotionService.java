package com.biblio.service;

import com.biblio.dto.request.PromotionInsertRequest;
import com.biblio.dto.request.PromotionUpdateRequest;
import com.biblio.dto.response.PromotionGetResponse;
import com.biblio.entity.Promotion;

import java.util.List;

public interface IPromotionService {
    List<PromotionGetResponse> getAllPromotions();
    PromotionGetResponse getPromotionById(Long id);
    void insertPromotion(PromotionInsertRequest promotionInsertRequest);
    void updatePromotion(PromotionUpdateRequest promotionUpdateRequest);
    Boolean isCodeExisted(String code);

    public Promotion findPromotionById(long promotionId);
}
