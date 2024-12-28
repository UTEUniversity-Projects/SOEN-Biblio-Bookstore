package com.biblio.dao;

import com.biblio.entity.Promotion;
import com.biblio.entity.ResponseReview;

public interface IResponseReviewDAO extends IGenericDAO<ResponseReview> {
    ResponseReview save(ResponseReview responseReview);

    boolean isExist(Long reviewId);
}
