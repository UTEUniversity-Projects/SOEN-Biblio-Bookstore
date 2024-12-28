package com.biblio.service;

import com.biblio.dto.request.ReviewRequest;
import com.biblio.dto.response.ReviewResponse;
import com.biblio.entity.Review;

public interface IReviewService {
    ReviewResponse updateReviewHidden(long reviewId, boolean isHidden);

    boolean createReview(ReviewRequest review, Long customerId);
}
