package com.biblio.dao.impl;

import com.biblio.dao.IPromotionTemplateDAO;
import com.biblio.entity.PromotionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionTemplateDAOImpl extends GenericDAOImpl<PromotionTemplate> implements IPromotionTemplateDAO {
    public PromotionTemplateDAOImpl() {
        super(PromotionTemplate.class);
    }

    @Override
    public List<PromotionTemplate> findAll() {
        return super.findAll();
    }

    @Override
    public PromotionTemplate findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<PromotionTemplate> findByJPQL() {
        String jpql = "SELECT p FROM PromotionTemplate p ORDER BY p.createdAt DESC";
        Map<String, Object> params = new HashMap<>();
        return super.findByJPQL(jpql, params);
    }

    @Override
    public PromotionTemplate findSingleByJPQL(String code) {
        String jpql = "SELECT p FROM PromotionTemplate p WHERE p.code = :code";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        return super.findSingleByJPQL(jpql, params);
    }

    @Override
    public PromotionTemplate save(PromotionTemplate promotionTemplate) {
        return super.save(promotionTemplate);
    }

    @Override
    public PromotionTemplate update(PromotionTemplate promotionTemplate) {
        return super.update(promotionTemplate);
    }

    @Override
    public boolean existsByCode(String code) {
        String jpql = "SELECT p FROM PromotionTemplate p WHERE p.code = :code";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        return super.findSingleByJPQL(jpql, params) != null;
    }

    public static void main(String[] args) {
        PromotionTemplateDAOImpl dao = new PromotionTemplateDAOImpl();
        PromotionTemplate promotionTemplate = dao.findSingleByJPQL("VOUCHER_1");
        System.out.println(promotionTemplate);
    }
}
