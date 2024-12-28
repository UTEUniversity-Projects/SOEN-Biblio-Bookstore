package com.biblio.service.impl;

import com.biblio.dao.*;
import com.biblio.dao.impl.BookTemplateDAOImpl;
import com.biblio.dao.impl.CustomerDAOImpl;
import com.biblio.dao.impl.NotificationDAOImpl;
import com.biblio.dao.impl.ReviewDAOImpl;
import com.biblio.dto.request.ReviewRequest;
import com.biblio.dto.response.ReviewResponse;
import com.biblio.entity.*;
import com.biblio.enumeration.EOrderHistory;
import com.biblio.mapper.ResponseReviewMapper;
import com.biblio.mapper.ReviewMapper;
import com.biblio.service.IReviewService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;


public class ReviewServiceImpl implements IReviewService {
    @Inject
    private IReviewDAO reviewDAO;

    @Inject
    private ICustomerDAO customerDAO;

    @Inject
    private IBookTemplateDAO bookTemplateDAO;

    @Inject IOrderDAO orderDAO;

    @Inject INotificationDAO notificationDAO;

    // Constructor tiêm phụ thuộc
    public ReviewServiceImpl(IReviewDAO reviewDAO,
                             INotificationDAO notificationDAO) {
        this.reviewDAO = reviewDAO;
        this.notificationDAO = notificationDAO;
    }
    public ReviewServiceImpl(){
        this.reviewDAO = new ReviewDAOImpl();
        this.notificationDAO = new NotificationDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
        this.bookTemplateDAO = new BookTemplateDAOImpl();
    }
    @Override
    public ReviewResponse updateReviewHidden(long reviewId, boolean isHidden) {
        Review review = reviewDAO.findById(reviewId);
        if (review != null) {
            review.setHidden(isHidden);
            review = reviewDAO.update(review);
            ReviewResponse reviewResponse = ReviewMapper.toReviewResponse(review);
            return reviewResponse;
        }
        return null;
    }

    @Transactional
    @Override
    public boolean createReview(ReviewRequest reviewRequest, Long customerId) {
        if (reviewRequest == null || reviewRequest.getContent() == null) {
            throw new IllegalArgumentException("Review không hợp lệ: Tiêu đề hoặc nội dung không được để trống.");
        }

        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer không tồn tại.");
        }

        // Tìm BookTemplate
        BookTemplate bookTemplate = bookTemplateDAO.findById(reviewRequest.getBookTemplateId());
        if (bookTemplate == null) {
            throw new IllegalArgumentException("BookTemplate không tồn tại.");
        }

        // Tạo Review từ request và lưu vào cơ sở dữ liệu
        Order order = orderDAO.findOne(reviewRequest.getOrderId());
        if (order == null) {
            return false;
        }

        if (!order.isStatusHistoryExist(EOrderHistory.REVIEWED)) {
            OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
            orderStatusHistory.setStatus(EOrderHistory.REVIEWED);
            orderStatusHistory.setStatusChangeDate(LocalDateTime.now());
            orderStatusHistory.setOrder(order);
            order.getStatusHistory().add(orderStatusHistory);
            order = orderDAO.update(order);
            if (order == null) {
                return false;
            }
        }
        Review review = ReviewMapper.toEntity(reviewRequest, customer, bookTemplate);
        return reviewDAO.save(review) != null;
    }
}
