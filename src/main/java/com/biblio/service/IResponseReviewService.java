package com.biblio.service;

import com.biblio.dto.request.ResponseReviewRequest;
import com.biblio.entity.ResponseReview;

public interface IResponseReviewService {
    ResponseReview insertResponseReview(ResponseReviewRequest responseReviewRequest);

    boolean isExistResponseReview(long idReview);
}
