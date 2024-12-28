package com.biblio.service.impl;

import com.biblio.dao.IAuthorDAO;
import com.biblio.dto.request.AuthorCreateRequest;
import com.biblio.dto.request.AuthorDeleteRequest;
import com.biblio.dto.request.AuthorUpdateRequest;
import com.biblio.dto.response.AuthorAnalysisResponse;
import com.biblio.dto.response.AuthorLineResponse;
import com.biblio.dto.response.AuthorProfileResponse;
import com.biblio.entity.Author;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.mapper.AuthorMapper;
import com.biblio.service.IAuthorService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements IAuthorService {
    @Inject
    IAuthorDAO authorDAO;

    @Override
    public List<AuthorLineResponse> getAll() {
        List<AuthorLineResponse> list = new ArrayList<AuthorLineResponse>();
        for (Author author : authorDAO.getEntityAll()) {
            Integer works = authorDAO.countBooksTemplateAll(author.getId());
            Double avgRate = authorDAO.calculateAverageRating(author.getId());
            Double perValueBooksSold = calculateValueBooksSoldGrowth(author.getId());

            list.add(AuthorMapper.toAuthorLineResponse(author, works, avgRate, perValueBooksSold));
        }
        return list;
    }

    @Override
    public AuthorProfileResponse getProfileById(Long id) {
        Author author = authorDAO.getEntityById(id);
        return AuthorMapper.toAuthorProfileResponse(author);
    }

    @Override
    public AuthorAnalysisResponse getAnalysisById(Long id) {
        Author author = authorDAO.getEntityById(id);
        Integer works = authorDAO.countBooksTemplateAll(id);
        Double avgRate = authorDAO.calculateAverageRating(id);
        Integer sales = authorDAO.countOrdersAll(id);
        Double perSales = calculateSaleGrowth(id);
        Integer booksSold = authorDAO.countBooksByStatus(id, EBookMetadataStatus.SOLD);
        Double perBooksSold = calculateBooksSoldGrowth(id);
        Long valueBooksSold = authorDAO.calculateValueBooksSold(id);
        Double perValueBooksSold = calculateValueBooksSoldGrowth(id);

        Integer booksInStock = authorDAO.countBooksByStatus(id, EBookMetadataStatus.IN_STOCK);
        Integer booksCancelled = authorDAO.countBooksByOrderStatus(id, EOrderStatus.CANCELED);
        Integer booksReturned = authorDAO.countBooksByOrderStatus(id, EOrderStatus.REFUNDED);

        Integer salesThisMonth = countSaleThisMonth(id);
        Integer booksThisMonth = countBooksSoldThisMonth((id));
        Long revenueThisMonth = calculateRevenueThisMonth(id);

        Integer ordersCompleted = authorDAO.countOrdersByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Long valueOrdersCompleted = authorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Integer ordersWaiting = authorDAO.countOrdersByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Long valueOrdersWaiting = authorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Integer ordersPacking = authorDAO.countOrdersByStatus(id, EOrderStatus.PACKING);
        Long valueOrderPacking = authorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.PACKING);
        Integer ordersShipping = authorDAO.countOrdersByStatus(id, EOrderStatus.SHIPPING);
        Long valueOrderShipping = authorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.SHIPPING);
        Integer ordersCancelled = authorDAO.countOrdersByStatus(id, EOrderStatus.CANCELED);
        Long valueOrdersCancelled = authorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.CANCELED);
        Integer ordersRequestRefund = authorDAO.countOrdersByStatus(id, EOrderStatus.REQUEST_REFUND);
        Long valueOrdersRequestRefund = authorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REQUEST_REFUND);
        Integer ordersRefunded = authorDAO.countOrdersByStatus(id, EOrderStatus.REFUNDED);
        Long valueOrdersRefunded = authorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REFUNDED);

        List<String> topSubCategory = findTopSubCategory(id, 3);

        return AuthorMapper.toAuthorAnalysisResponse(author, works, avgRate, sales, perSales, booksSold, perBooksSold,
                valueBooksSold, perValueBooksSold, booksInStock, booksCancelled, booksReturned, salesThisMonth, booksThisMonth, revenueThisMonth,
                ordersCompleted, valueOrdersCompleted, ordersWaiting, valueOrdersWaiting, ordersPacking, valueOrderPacking,
                ordersShipping, valueOrderShipping, ordersCancelled, valueOrdersCancelled, ordersRequestRefund, valueOrdersRequestRefund,
                ordersRefunded, valueOrdersRefunded, topSubCategory);
    }

    @Override
    public Author createAuthor(AuthorCreateRequest authorCreateRequest) {
        return authorDAO.createAuthor(AuthorMapper.toAuthorCreate(authorCreateRequest));
    }

    @Override
    public void updateAuthor(AuthorUpdateRequest authorUpdateRequest) {
        Author author = authorDAO.getEntityById(Long.valueOf(authorUpdateRequest.getId()));
        authorDAO.updateAuthor(AuthorMapper.toAuthorUpdate(authorUpdateRequest, author));
    }

    @Override
    public void deleteAuthor(AuthorDeleteRequest authorDeleteRequest) {
        authorDAO.deleteAuthor(Long.valueOf(authorDeleteRequest.getId()));
    }

    @Override
    public Integer countBookTemplate(AuthorDeleteRequest authorDeleteRequest) {
        return authorDAO.countBooksTemplateByStatus(Long.valueOf(authorDeleteRequest.getId()), EBookTemplateStatus.COMING_SOON)
                + authorDAO.countBooksTemplateByStatus(Long.valueOf(authorDeleteRequest.getId()), EBookTemplateStatus.ON_SALE)
                + authorDAO.countBooksTemplateByStatus(Long.valueOf(authorDeleteRequest.getId()), EBookTemplateStatus.OUT_OF_STOCK);
    }

    private Double calculateSaleGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countSaleThisMonth(id);
        Integer lastMonth = authorDAO.countOrdersInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EOrderStatus.COMPLETE_DELIVERY);

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

        return authorDAO.countOrdersInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countBooksSoldThisMonth(id);
        Integer lastMonth = authorDAO.countBooksInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);

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

        return authorDAO.countBooksInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateValueBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Long currentMonth = calculateRevenueThisMonth(id);
        Long lastMonth = authorDAO.calculateValueBooksSoldInRange(id, startOfLastMonth, endOfLastMonth);

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

        return authorDAO.calculateValueBooksSoldInRange(id, startOfThisMonth, endOfThisMonth);
    }

    private List<String> findTopSubCategory(Long id, Integer numberElements) {
        List<String> list = authorDAO.getTopSubCategory(id);

        while (list.size() < numberElements) {
            list.add("");
        }

        return list.subList(0, numberElements);
    }
}
