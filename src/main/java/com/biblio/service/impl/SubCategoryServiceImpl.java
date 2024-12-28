package com.biblio.service.impl;

import com.biblio.dao.ICategoryDAO;
import com.biblio.dao.ISubCategoryDAO;
import com.biblio.dao.ISubCategoryDAO;
import com.biblio.dto.request.SubCategoryCreateRequest;
import com.biblio.dto.request.SubCategoryDeleteRequest;
import com.biblio.dto.request.SubCategoryUpdateRequest;
import com.biblio.dto.request.SubCategoryRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.SubCategory;
import com.biblio.entity.SubCategory;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.mapper.SubCategoryMapper;
import com.biblio.mapper.SubCategoryMapper;
import com.biblio.service.ISubCategoryService;
import com.biblio.service.ISubCategoryService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryServiceImpl implements ISubCategoryService {
    @Inject
    ISubCategoryDAO subCategoryDAO;
    @Inject
    ICategoryDAO categoryDAO;

    @Inject
    SubCategoryMapper subCategoryMapper;

    @Override
    public SubCategory getEntityById(Long id) {
        return subCategoryDAO.getEntityById(id);
    }

    @Override
    public List<SubCategoryLineResponse> getAll() {
        List<SubCategoryLineResponse> list = new ArrayList<SubCategoryLineResponse>();
        for (SubCategory category : subCategoryDAO.getEntityAll()) {
            Integer works = subCategoryDAO.countBooksTemplateAll(category.getId());
            Double avgRate = subCategoryDAO.calculateAverageRating(category.getId());
            Double perValueBooksSold = calculateValueBooksSoldGrowth(category.getId());

            list.add(SubCategoryMapper.toSubCategoryLineResponse(category, works, avgRate, perValueBooksSold));
        }
        return list;
    }

    @Override
    public SubCategoryProfileResponse getProfileById(Long id) {
        SubCategory category = subCategoryDAO.getEntityById(id);
        return SubCategoryMapper.toSubCategoryProfileResponse(category);
    }

    @Override
    public SubCategoryAnalysisResponse getAnalysisById(Long id) {
        SubCategory category = subCategoryDAO.getEntityById(id);
        Integer works = subCategoryDAO.countBooksTemplateAll(id);
        Double avgRate = subCategoryDAO.calculateAverageRating(id);
        Integer sales = subCategoryDAO.countOrdersAll(id);
        Double perSales = calculateSaleGrowth(id);
        Integer booksSold = subCategoryDAO.countBooksByStatus(id, EBookMetadataStatus.SOLD);
        Double perBooksSold = calculateBooksSoldGrowth(id);
        Long valueBooksSold = subCategoryDAO.calculateValueBooksSold(id);
        Double perValueBooksSold = calculateValueBooksSoldGrowth(id);

        Integer booksInStock = subCategoryDAO.countBooksByStatus(id, EBookMetadataStatus.IN_STOCK);
        Integer booksCancelled = subCategoryDAO.countBooksByOrderStatus(id, EOrderStatus.CANCELED);
        Integer booksReturned = subCategoryDAO.countBooksByOrderStatus(id, EOrderStatus.REFUNDED);

        Integer salesThisMonth = countSaleThisMonth(id);
        Integer booksThisMonth = countBooksSoldThisMonth((id));
        Long revenueThisMonth = calculateRevenueThisMonth(id);

        Integer ordersCompleted = subCategoryDAO.countOrdersByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Long valueOrdersCompleted = subCategoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Integer ordersWaiting = subCategoryDAO.countOrdersByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Long valueOrdersWaiting = subCategoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Integer ordersPacking = subCategoryDAO.countOrdersByStatus(id, EOrderStatus.PACKING);
        Long valueOrderPacking = subCategoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.PACKING);
        Integer ordersShipping = subCategoryDAO.countOrdersByStatus(id, EOrderStatus.SHIPPING);
        Long valueOrderShipping = subCategoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.SHIPPING);
        Integer ordersCancelled = subCategoryDAO.countOrdersByStatus(id, EOrderStatus.CANCELED);
        Long valueOrdersCancelled = subCategoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.CANCELED);
        Integer ordersRequestRefund = subCategoryDAO.countOrdersByStatus(id, EOrderStatus.REQUEST_REFUND);
        Long valueOrdersRequestRefund = subCategoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REQUEST_REFUND);
        Integer ordersRefunded = subCategoryDAO.countOrdersByStatus(id, EOrderStatus.REFUNDED);
        Long valueOrdersRefunded = subCategoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REFUNDED);

        List<String> topSubCategory = findTopSubCategory(id, 3);

        return SubCategoryMapper.toSubCategoryAnalysisResponse(category, works, avgRate, sales, perSales, booksSold, perBooksSold,
                valueBooksSold, perValueBooksSold, booksInStock, booksCancelled, booksReturned, salesThisMonth, booksThisMonth, revenueThisMonth,
                ordersCompleted, valueOrdersCompleted, ordersWaiting, valueOrdersWaiting, ordersPacking, valueOrderPacking,
                ordersShipping, valueOrderShipping, ordersCancelled, valueOrdersCancelled, ordersRequestRefund, valueOrdersRequestRefund,
                ordersRefunded, valueOrdersRefunded, topSubCategory);
    }

    @Override
    public SubCategory create(SubCategoryCreateRequest subCategoryCreateRequest) {
        return subCategoryDAO.createSubCategory(subCategoryMapper.toSubCategory(subCategoryCreateRequest));
    }

    @Override
    public void update(SubCategoryUpdateRequest subCategoryUpdateRequest) {
        subCategoryDAO.updateSubCategory(subCategoryMapper.toSubCategory(subCategoryUpdateRequest));
    }

    @Override
    public void delete(SubCategoryDeleteRequest subCategoryDeleteRequest) {
        subCategoryDAO.deleteSubCategory(Long.valueOf(subCategoryDeleteRequest.getId()));
    }

    @Override
    public Integer countBookTemplate(Long id) {
        return subCategoryDAO.countBooksTemplateByStatus(id, EBookTemplateStatus.COMING_SOON)
                + subCategoryDAO.countBooksTemplateByStatus(id, EBookTemplateStatus.ON_SALE)
                + subCategoryDAO.countBooksTemplateByStatus(id, EBookTemplateStatus.OUT_OF_STOCK);
    }

    @Override
    public SubCategoryCreateResponse initCreateSubCategory() {
        return SubCategoryMapper.toSubCategoryCreateResponse(categoryDAO.getEntityAll());
    }

    @Override
    public List<SubCategoryProfileResponse> findAll() {
        List<SubCategoryProfileResponse> list = new ArrayList<SubCategoryProfileResponse>();
        for (SubCategory subSubCategory : subCategoryDAO.findAll()) {
            list.add(SubCategoryMapper.toSubCategoryProfileResponse(subSubCategory));
        }
        return list;
    }

    @Override
    public List<SubCategoryProfileResponse> getAllSubCategoriesById(Long id) {
        List<SubCategoryProfileResponse> list = new ArrayList<SubCategoryProfileResponse>();
        for (SubCategory subSubCategory : subCategoryDAO.findByJPQL(id)) {
            list.add(SubCategoryMapper.toSubCategoryProfileResponse(subSubCategory));
        }
        return list;
    }

    private Double calculateSaleGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countSaleThisMonth(id);
        Integer lastMonth = subCategoryDAO.countOrdersInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EOrderStatus.COMPLETE_DELIVERY);

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

        return subCategoryDAO.countOrdersInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countBooksSoldThisMonth(id);
        Integer lastMonth = subCategoryDAO.countBooksInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);

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

        return subCategoryDAO.countBooksInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateValueBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Long currentMonth = calculateRevenueThisMonth(id);
        Long lastMonth = subCategoryDAO.calculateValueBooksSoldInRange(id, startOfLastMonth, endOfLastMonth);

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

        return subCategoryDAO.calculateValueBooksSoldInRange(id, startOfThisMonth, endOfThisMonth);
    }

    private List<String> findTopSubCategory(Long id, Integer numberElements) {
        List<String> list = subCategoryDAO.getTopSubCategory(id);

        while (list.size() < numberElements) {
            list.add("");
        }

        return list.subList(0, numberElements);
    }

}
