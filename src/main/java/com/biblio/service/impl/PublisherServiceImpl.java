package com.biblio.service.impl;

import com.biblio.dao.IPublisherDAO;
import com.biblio.dto.request.PublisherCreateRequest;
import com.biblio.dto.request.PublisherDeleteRequest;
import com.biblio.dto.request.PublisherUpdateRequest;
import com.biblio.dto.response.PublisherAnalysisResponse;
import com.biblio.dto.response.PublisherLineResponse;
import com.biblio.dto.response.PublisherProfileResponse;
import com.biblio.entity.Publisher;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.mapper.PublisherMapper;
import com.biblio.service.IPublisherService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PublisherServiceImpl implements IPublisherService {
    @Inject
    IPublisherDAO publisherDAO;

    @Override
    public List<PublisherLineResponse> getAll() {
        List<PublisherLineResponse> list = new ArrayList<PublisherLineResponse>();
        for (Publisher publisher : publisherDAO.getEntityAll()) {
            Integer works = publisherDAO.countBooksTemplateAll(publisher.getId());
            Double avgRate = publisherDAO.calculateAverageRating(publisher.getId());
            Double perValueBooksSold = calculateValueBooksSoldGrowth(publisher.getId());

            list.add(PublisherMapper.toPublisherLineResponse(publisher, works, avgRate, perValueBooksSold));
        }
        return list;
    }

    @Override
    public PublisherProfileResponse getProfileById(Long id) {
        Publisher publisher = publisherDAO.getEntityById(id);
        return PublisherMapper.toPublisherProfileResponse(publisher);
    }

    @Override
    public PublisherAnalysisResponse getAnalysisById(Long id) {
        Publisher publisher = publisherDAO.getEntityById(id);
        Integer works = publisherDAO.countBooksTemplateAll(id);
        Double avgRate = publisherDAO.calculateAverageRating(id);
        Integer sales = publisherDAO.countOrdersAll(id);
        Double perSales = calculateSaleGrowth(id);
        Integer booksSold = publisherDAO.countBooksByStatus(id, EBookMetadataStatus.SOLD);
        Double perBooksSold = calculateBooksSoldGrowth(id);
        Long valueBooksSold = publisherDAO.calculateValueBooksSold(id);
        Double perValueBooksSold = calculateValueBooksSoldGrowth(id);

        Integer booksInStock = publisherDAO.countBooksByStatus(id, EBookMetadataStatus.IN_STOCK);
        Integer booksCancelled = publisherDAO.countBooksByOrderStatus(id, EOrderStatus.CANCELED);
        Integer booksReturned = publisherDAO.countBooksByOrderStatus(id, EOrderStatus.REFUNDED);

        Integer salesThisMonth = countSaleThisMonth(id);
        Integer booksThisMonth = countBooksSoldThisMonth((id));
        Long revenueThisMonth = calculateRevenueThisMonth(id);

        Integer ordersCompleted = publisherDAO.countOrdersByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Long valueOrdersCompleted = publisherDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Integer ordersWaiting = publisherDAO.countOrdersByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Long valueOrdersWaiting = publisherDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Integer ordersPacking = publisherDAO.countOrdersByStatus(id, EOrderStatus.PACKING);
        Long valueOrderPacking = publisherDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.PACKING);
        Integer ordersShipping = publisherDAO.countOrdersByStatus(id, EOrderStatus.SHIPPING);
        Long valueOrderShipping = publisherDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.SHIPPING);
        Integer ordersCancelled = publisherDAO.countOrdersByStatus(id, EOrderStatus.CANCELED);
        Long valueOrdersCancelled = publisherDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.CANCELED);
        Integer ordersRequestRefund = publisherDAO.countOrdersByStatus(id, EOrderStatus.REQUEST_REFUND);
        Long valueOrdersRequestRefund = publisherDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REQUEST_REFUND);
        Integer ordersRefunded = publisherDAO.countOrdersByStatus(id, EOrderStatus.REFUNDED);
        Long valueOrdersRefunded = publisherDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REFUNDED);

        List<String> topSubCategory = findTopSubCategory(id, 3);

        return PublisherMapper.toPublisherAnalysisResponse(publisher, works, avgRate, sales, perSales, booksSold, perBooksSold, valueBooksSold, perValueBooksSold, booksInStock, booksCancelled, booksReturned, salesThisMonth, booksThisMonth, revenueThisMonth, ordersCompleted, valueOrdersCompleted, ordersWaiting, valueOrdersWaiting, ordersPacking, valueOrderPacking, ordersShipping, valueOrderShipping, ordersCancelled, valueOrdersCancelled, ordersRequestRefund, valueOrdersRequestRefund, ordersRefunded, valueOrdersRefunded, topSubCategory);
    }

    @Override
    public Publisher createPublisher(PublisherCreateRequest publisherCreateRequest) {
        return publisherDAO.createPublisher(PublisherMapper.toPublisherCreate(publisherCreateRequest));
    }

    @Override
    public void updatePublisher(PublisherUpdateRequest publisherUpdateRequest) {
        Publisher publisher = publisherDAO.getEntityById(Long.valueOf(publisherUpdateRequest.getId()));
        publisherDAO.updatePublisher(PublisherMapper.toPublisherUpdate(publisherUpdateRequest, publisher));
    }

    @Override
    public void deletePublisher(PublisherDeleteRequest publisherDeleteRequest) {
        publisherDAO.deletePublisher(Long.valueOf(publisherDeleteRequest.getId()));
    }

    @Override
    public Integer countBookTemplate(PublisherDeleteRequest publisherDeleteRequest) {
        return publisherDAO.countBooksTemplateByStatus(Long.valueOf(publisherDeleteRequest.getId()), EBookTemplateStatus.COMING_SOON) + publisherDAO.countBooksTemplateByStatus(Long.valueOf(publisherDeleteRequest.getId()), EBookTemplateStatus.ON_SALE) + publisherDAO.countBooksTemplateByStatus(Long.valueOf(publisherDeleteRequest.getId()), EBookTemplateStatus.OUT_OF_STOCK);
    }

    private Double calculateSaleGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countSaleThisMonth(id);
        Integer lastMonth = publisherDAO.countOrdersInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EOrderStatus.COMPLETE_DELIVERY);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }

    private Integer countSaleThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return publisherDAO.countOrdersInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countBooksSoldThisMonth(id);
        Integer lastMonth = publisherDAO.countBooksInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }

    private Integer countBooksSoldThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return publisherDAO.countBooksInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateValueBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Long currentMonth = calculateRevenueThisMonth(id);
        Long lastMonth = publisherDAO.calculateValueBooksSoldInRange(id, startOfLastMonth, endOfLastMonth);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }

    private Long calculateRevenueThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return publisherDAO.calculateValueBooksSoldInRange(id, startOfThisMonth, endOfThisMonth);
    }

    private List<String> findTopSubCategory(Long id, Integer numberElements) {
        List<String> list = publisherDAO.getTopSubCategory(id);

        while (list.size() < numberElements) {
            list.add("");
        }

        return list.subList(0, numberElements);
    }
}