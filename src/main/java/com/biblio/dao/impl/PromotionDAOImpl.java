package com.biblio.dao.impl;

import com.biblio.dao.IPromotionDAO;
import com.biblio.entity.Promotion;
import com.biblio.entity.PromotionTarget;
import com.biblio.enumeration.EPromotionStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionDAOImpl extends GenericDAOImpl<Promotion> implements IPromotionDAO {

    public PromotionDAOImpl() {
        super(Promotion.class);
    }

    @Override
    public List<Promotion> findAll() {
        return super.findAll();
    }

    @Override
    public List<Promotion> findAllByIdAndStatus(Long id, EPromotionStatus status) {
        String jpql = "SELECT p FROM Promotion p WHERE p.promotionTemplate.id = :id AND p.status = :status";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status", status);
        return super.findByJPQL(jpql, params);
    }
    public List<Promotion> findAllById(Long id) {
        String jpql = "SELECT p FROM Promotion p WHERE p.promotionTemplate.id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return super.findByJPQL(jpql, params);
    }


    public Promotion findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Promotion save(Promotion promotion) {
        return super.save(promotion);
    }

    @Override
    public Promotion update(Promotion promotion) {
         return super.update(promotion);
    }

    @Override
    public Promotion findLastPromotion() {
        String jpql = "SELECT p FROM Promotion p ORDER BY p.id DESC";
        Map<String, Object> params = new HashMap<>();
        return super.findSingleByJPQL(jpql, params);
    }

    @Override
    public boolean existsByCode(String code) {
        String jpql = "SELECT p FROM Promotion p WHERE p.code = :code";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        return super.findSingleByJPQL(jpql, params) != null;
    }

    public static void main(String[] args) {
        PromotionDAOImpl dao = new PromotionDAOImpl();
        Promotion promotion = dao.findById(100L);
        Promotion promotion2 = new Promotion();
        promotion2.setStatus(promotion.getStatus());
        promotion2.setPromotionTemplate(promotion.getPromotionTemplate());
        promotion2.setTitle(promotion.getTitle());
        promotion2.setDescription(promotion.getDescription());
        promotion2.setMinValueToBeApplied(promotion.getMinValueToBeApplied());
        promotion2.setDiscountLimit(promotion.getDiscountLimit());
        promotion2.setPercentDiscount(promotion.getPercentDiscount());
        promotion2.setEffectiveDate(promotion.getEffectiveDate());
        promotion2.setExpirationDate(promotion.getExpirationDate());
        promotion2.setPromotionTemplate(promotion.getPromotionTemplate());

        for (PromotionTarget promotionTarget : promotion.getPromotionTargets()) {
            PromotionTarget promotionTarget2 = new PromotionTarget();
            promotionTarget2.setType(promotionTarget.getType());
            promotionTarget2.setApplicableObjectId(promotionTarget.getApplicableObjectId());
            promotionTarget2.setPromotion(promotionTarget.getPromotion());

            promotion2.getPromotionTargets().add(promotionTarget2);
        }

        Promotion promotion3 = dao.save(promotion2);
        //System.out.println(promotion3.toString());
//        List<Promotion> list = dao.findAllByIdAndStatus(1L, EPromotionStatus.USED);
//        for (Promotion promotion : list) {
//            System.out.println(promotion.getTitle());
//        }

    }

}
