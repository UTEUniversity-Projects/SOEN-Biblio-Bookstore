package com.biblio.dao.impl;

import com.biblio.dao.IPromotionDAO;
import com.biblio.dao.IResponseReviewDAO;
import com.biblio.entity.Promotion;
import com.biblio.entity.ResponseReview;
import com.biblio.jpaconfig.JpaConfig;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

public class ResponseReviewDAOImpl extends GenericDAOImpl<ResponseReview> implements IResponseReviewDAO {
    public ResponseReviewDAOImpl()  {
        super(ResponseReview.class);
    }

    public ResponseReview save(ResponseReview responseReview) {
        return super.save(responseReview);
    }

    @Override
    public boolean isExist(Long reviewId) {
        String jpql = "SELECT rr FROM ResponseReview rr WHERE rr.review.id = :reviewId";

        Map<String, Object> params = new HashMap<>();
        params.put("reviewId", reviewId);

        return findSingleByJPQL(jpql, params) != null;
    }

    public static void main(String[] args) {
        ResponseReviewDAOImpl dao = new ResponseReviewDAOImpl();
        boolean isExist = dao.isExist(4L);
        System.out.println(isExist);
    }
}
