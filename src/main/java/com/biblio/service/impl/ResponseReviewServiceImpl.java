package com.biblio.service.impl;

import com.biblio.dao.IResponseReviewDAO;
import com.biblio.dao.IReviewDAO;
import com.biblio.dto.request.ResponseReviewRequest;
import com.biblio.entity.ResponseReview;
import com.biblio.entity.Review;
import com.biblio.mapper.ResponseReviewMapper;
import com.biblio.service.IResponseReviewService;

import javax.inject.Inject;

public class ResponseReviewServiceImpl implements IResponseReviewService {
    @Inject
    private IResponseReviewDAO responseReviewDAO;

    @Inject
    private IReviewDAO reviewDAO;

    @Override
    public ResponseReview insertResponseReview(ResponseReviewRequest responseReviewRequest) {
        ResponseReview responseReview = ResponseReviewMapper.toResponseReview(responseReviewRequest);
        Review review = reviewDAO.findById(responseReviewRequest.getReviewId());
        responseReview.setReview(review);
        return responseReviewDAO.save(responseReview);
    }

    @Override
    public boolean isExistResponseReview(long idReview) {
        return responseReviewDAO.isExist(idReview);
    }
}
