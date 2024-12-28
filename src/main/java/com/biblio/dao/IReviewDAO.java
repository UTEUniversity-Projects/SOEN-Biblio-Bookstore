package com.biblio.dao;

import com.biblio.entity.Author;
import com.biblio.entity.BookTemplate;
import com.biblio.entity.Review;

import java.util.List;

public interface IReviewDAO extends IGenericDAO<Review> {
    Review findById(Long id);

    List<Review> findByBookTemplate(BookTemplate bookTemplate);

    Review update(Review review);

    Review save(Review review);
}
