package com.biblio.service.impl;

import com.biblio.dao.IPromotionDAO;
import com.biblio.dto.request.PromotionInsertRequest;
import com.biblio.dto.request.PromotionUpdateRequest;
import com.biblio.dto.response.PromotionGetResponse;
import com.biblio.entity.Promotion;
import com.biblio.mapper.PromotionMapper;
import com.biblio.service.IPromotionService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PromotionServiceImpl implements IPromotionService {
    @Inject
    IPromotionDAO promotionDAO;
    @Override
    public List<PromotionGetResponse> getAllPromotions() {
        List<PromotionGetResponse> list = new ArrayList<PromotionGetResponse>();
        List<Promotion> promotions = promotionDAO.findAll();

        return list;
    }

    @Override
    public PromotionGetResponse getPromotionById(Long id) {
       // return PromotionMapper.toPromotionGetResponse(promotionDAO.findById(id));
        return new PromotionGetResponse();
    }

    @Override
    public void insertPromotion(PromotionInsertRequest promotionInsertRequest) {
        Promotion promotion = promotionDAO.save(PromotionMapper.toPromotion(promotionInsertRequest));

    }

    @Override
    public void updatePromotion(PromotionUpdateRequest promotionUpdateRequest) {
        Promotion promotion = promotionDAO.update(PromotionMapper.toPromotion(promotionUpdateRequest));
    }

    @Override
    public Boolean isCodeExisted(String code) {
        return promotionDAO.existsByCode(code);
    }

    @Override
    public Promotion findPromotionById(long promotionId) {
        return promotionDAO.findById(promotionId);
    }
}
