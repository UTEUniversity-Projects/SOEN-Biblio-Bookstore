package com.biblio.dao.impl;

import com.biblio.dao.IReviewDAO;
import com.biblio.entity.BookTemplate;
import com.biblio.entity.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewDAOImpl extends GenericDAOImpl<Review> implements IReviewDAO {
    public ReviewDAOImpl() {
        super(Review.class);
    }

    @Override
    public Review findById(Long id) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT r ")
                .append("FROM Review r ")
                .append("JOIN FETCH r.customer c ")
                .append("WHERE r.id = :id");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return super.findSingleByJPQL(jpql.toString(), params);
    }

    @Override
    public List<Review> findByBookTemplate(BookTemplate bookTemplate) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT r ")
                .append("FROM Review r ")
                .append("JOIN r.bookTemplate bt ")
                .append("LEFT JOIN FETCH r.responseReview rr ")
                .append("JOIN FETCH r.customer c ")
                .append("WHERE bt = :bookTemplate");

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplate", bookTemplate);
        return super.findByJPQL(jpql.toString(), params);
    }

    @Override
    public Review update(Review review) {
        return super.update(review);
    }
    @Override
    public Review save(Review review) { return super.save(review); }

    public static void main(String[] args) {
        BookTemplateDAOImpl bookTemplateDAO = new BookTemplateDAOImpl();
        BookTemplate bookTemplate = bookTemplateDAO.findById(1L);
        ReviewDAOImpl dao = new ReviewDAOImpl();
        List<Review> reviews = dao.findByBookTemplate(bookTemplate);
        System.out.println(reviews.size());
    }
}
