package com.biblio.mapper;

import com.biblio.dao.impl.ReviewDAOImpl;
import com.biblio.dto.request.ResponseReviewRequest;
import com.biblio.entity.ResponseReview;
import com.biblio.entity.Review;

import java.time.LocalDateTime;

public class ResponseReviewMapper {
    private static final ReviewDAOImpl reviewDAO = new ReviewDAOImpl();
    // region DTOtoEntity

    public static ResponseReview toResponseReview(ResponseReviewRequest responseReviewRequest) {
        ResponseReview responseReview = new ResponseReview();
        responseReview.setContent(responseReviewRequest.getContent());
        responseReview.setCreatedAt(LocalDateTime.now());
        responseReview.setCreatedBy("Staff");
        return responseReview;
    }

    // endregion
}
